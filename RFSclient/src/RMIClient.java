import java.io.File;
import static java.lang.System.exit;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class RMIClient extends javax.swing.JFrame {
    private final Registry registry;
    private final Protocol p;
    private File[] files;
    private File currentDir;
    private final File defaultDir;
    private final String address;

    public RMIClient() throws RemoteException, NotBoundException {
        address = JOptionPane.showInputDialog(new JFrame(), "Enter the server IP address:", "Remote File System Browser", JOptionPane.QUESTION_MESSAGE);
        if (address == null) exit(0);
        registry = LocateRegistry.getRegistry(address);
        
        p = (Protocol)registry.lookup("myProtocol");
        files = p.readDirectory(p.getDefaultDirectoryPath());
        currentDir = new File(p.getDefaultDirectoryPath());
        defaultDir = currentDir;
    }

    private String getDateString(long milliseconds) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return sdf.format(new Date(milliseconds));
    }

    private String formatSize(long bytes) {
        if (bytes < 1000) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(1000));
        String pre = "kMGTPE".charAt(exp-1) + "";
        return String.format("%.1f %sB", bytes / Math.pow(1000, exp), pre);
    }

    private void openFolderButtonMouseClicked(java.awt.event.MouseEvent evt) {
        if (!openFolderButton.isEnabled()) return;
        String selectedValue = fileList.getSelectedValue();
        File selectedItem = null;
        for (File file:files)
        {
            selectedItem = file;
            if (file.getName() == null ? selectedValue == null : file.getName().equals(selectedValue)) break;
        }
        
        try {
            files = p.readDirectory(selectedItem.getAbsolutePath());
            currentDir = selectedItem;
            fileList.setModel(refreshModel());
            backButton.setEnabled(true);
        } catch (RemoteException ex) {
            Logger.getLogger(RMIClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void backButtonMouseClicked(java.awt.event.MouseEvent evt) {
        if (!backButton.isEnabled()) return;
        
        else {
            try {
                files = p.readDirectory(currentDir.getParentFile().getAbsolutePath());
                currentDir = currentDir.getParentFile();
                fileList.setModel(refreshModel());
                if (currentDir.equals(defaultDir)) {
                    backButton.setEnabled(false);
        }
            } catch (RemoteException ex) {
                Logger.getLogger(RMIClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void deleteButtonMouseClicked(java.awt.event.MouseEvent evt) {
        if (!deleteButton.isEnabled()) return;
        String selectedValue = fileList.getSelectedValue();
        File selectedItem = null;
        for (File file:files)
        {
            selectedItem = file;
            if (file.getName() == null ? selectedValue == null : file.getName().equals(selectedValue)) break;
        }
        
        try {
            String parent = currentDir.getAbsolutePath();
            
            if (selectedItem.isFile()) {
                p.deleteFile(selectedItem.getAbsolutePath());
            }
            else {
                p.deleteDirectory(selectedItem.getAbsolutePath());
            }
            files = p.readDirectory(parent);
            fileList.setModel(refreshModel());
        } catch (RemoteException ex) {
            Logger.getLogger(RMIClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void fileListValueChanged(javax.swing.event.ListSelectionEvent evt) {
        if (fileList.isSelectionEmpty()) {
            openFolderButton.setEnabled(false);
            renameButton.setEnabled(false);
            deleteButton.setEnabled(false);
        }
        else {
            String selectedValue = fileList.getSelectedValue();
            File selectedItem = null;
            for (File file:files)
            {
                selectedItem = file;
                if (file.getName() == null ? selectedValue == null : file.getName().equals(selectedValue)) break;
            }
            if (selectedItem != null) {
                try {
                    propertiesTable.setModel(refreshProperties());
                } catch (RemoteException ex) {
                    Logger.getLogger(RMIClient.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                try {
                    if (p.isDirectory(selectedItem)) {
                        openFolderButton.setEnabled(true);
                    }
                    else {
                        openFolderButton.setEnabled(false);
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(RMIClient.class.getName()).log(Level.SEVERE, null, ex);
                }
                renameButton.setEnabled(true);
                deleteButton.setEnabled(true);
            }
        }
    }

    private void renameButtonMouseClicked(java.awt.event.MouseEvent evt) {
        if (!renameButton.isEnabled()) return;
        String selectedValue = fileList.getSelectedValue();
        File selectedItem = null;
        for (File file:files)
        {
            selectedItem = file;
            if (file.getName() == null ? selectedValue == null : file.getName().equals(selectedValue)) break;
        }
        
        try {
            String parent = currentDir.getAbsolutePath();
            String fileExtension = "";
            
            int copyNumber = 1;
            if (selectedItem.getName().contains(".") && selectedItem.getName().indexOf(".") != 0) {
                fileExtension = selectedItem.getName().substring(selectedItem.getName().indexOf("."));
            }
            String name = JOptionPane.showInputDialog("Enter the new name:");
            if (name != null && !"".equals(name)) {
                String renamed = parent + "/" + name + fileExtension;
                
                if (!fileExists(parent, name + fileExtension)) p.rename(selectedItem.getAbsolutePath(), renamed);
                 else {
                    while (fileExists(parent, name + " (" + copyNumber + ")" + fileExtension)) {
                        copyNumber++;
                    }
                    
                    renamed = parent + "/" + name + " (" + copyNumber + ")" + fileExtension;
                    p.rename(selectedItem.getAbsolutePath(), renamed);
                }
                files = p.readDirectory(parent);
                fileList.setModel(refreshModel());
            }
        } catch (RemoteException ex) {
            Logger.getLogger(RMIClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void newFileButtonMouseClicked(java.awt.event.MouseEvent evt) {
        if (!newFileButton.isEnabled()) return;
        
        try {
            String parent = currentDir.getAbsolutePath();
            
            int copyNumber = 1;
            String[][] fileExtensions = {{"Microsoft Word Document (.docx)", "Microsoft Excel Spreadsheet (.xlsx)", "Microsoft Powerpoint Presentation (.pptx)", "Text Document (.txt)", "Compressed Archive (.zip)"}, 
                                    {".docx", ".xlsx", ".pptx", ".txt", ".zip"}};
            String fileType = (String) JOptionPane.showInputDialog(new JFrame(), 
                "What type of file would you like to create?",
                "File Type",
                JOptionPane.QUESTION_MESSAGE, 
                null, 
                fileExtensions[0], 
                fileExtensions[0][0]);
            if (fileType != null) {
                int typeIndex = 0;
                for (String type:fileExtensions[0]) {
                    if (type.equals(fileType)) break;
                    typeIndex++;
                }
                
                String fileExt = fileExtensions[1][typeIndex];
                String fileName = JOptionPane.showInputDialog("File name:");
                if (fileName != null) {
                    if (!"".equals(fileName)) {
                        if (!fileExists(parent, fileName + fileExt)) p.createFile(parent + "/" + fileName + fileExt);
                        else {
                           while (fileExists(parent, fileName + " (" + copyNumber + ")" + fileExt)) {
                               copyNumber++;
                           }

                           p.createFile(parent + "/" + fileName + " (" + copyNumber + ")" + fileExt);
                        }
                    }
                    else {
                        if (!fileExists(parent, "New File" + fileExt)) p.createFile(parent + "/" + "New File" + fileExt);
                        else {
                           while (fileExists(parent, "New File" + " (" + copyNumber + ")" + fileExt)) {
                               copyNumber++;
                           }

                           p.createFile(parent + "/" + "New File" + " (" + copyNumber + ")" + fileExt);
                        }
                    }
                    files = p.readDirectory(parent);
                    fileList.setModel(refreshModel());
                }
            }
        } catch (RemoteException ex) {
            Logger.getLogger(RMIClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void newFolderButtonMouseClicked(java.awt.event.MouseEvent evt) {
        if (!newFolderButton.isEnabled()) return;
        
        try {
            String parent = currentDir.getAbsolutePath();
            
            int copyNumber = 1;
            String folderName = JOptionPane.showInputDialog("Folder name:");
            if (folderName != null) {
                if (!"".equals(folderName)) {
                     if (!fileExists(parent, folderName)) p.createDirectory(parent + "/" + folderName);
                     else {
                        while (fileExists(parent, folderName + " (" + copyNumber + ")")) {
                            copyNumber++;
                        }

                        p.createDirectory(parent + "/" + folderName + " (" + copyNumber + ")");
                    }
                }
                else {
                    if (!fileExists(parent, "New Folder")) p.createDirectory(parent + "/" + "New Folder");
                    else {
                        while (fileExists(parent, "New Folder (" + copyNumber + ")")) {
                            copyNumber++;
                        }

                        p.createDirectory(parent + "/" + "New Folder (" + copyNumber + ")");
                    }
                }
            files = p.readDirectory(parent);
            fileList.setModel(refreshModel());
            }
        } catch (RemoteException ex) {
            Logger.getLogger(RMIClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String args[]) throws RemoteException, NotBoundException {

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    if(System.getSecurityManager()==null) {
                        System.setSecurityManager(new SecurityManager());
                    }
                    
                    new RMIClient().setVisible(true);
                } catch (RemoteException | NotBoundException ex) {
                    Logger.getLogger(RMIClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private javax.swing.JButton backButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JList<String> fileList;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JButton newFileButton;
    private javax.swing.JButton newFolderButton;
    private javax.swing.JButton openFolderButton;
    private javax.swing.JLabel propertiesLabel;
    private javax.swing.JTable propertiesTable;
    private javax.swing.JButton renameButton;
    private javax.swing.JLabel titleLabel;
  
}
