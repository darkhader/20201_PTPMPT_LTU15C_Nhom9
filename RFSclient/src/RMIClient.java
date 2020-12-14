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
        address = JOptionPane.showInputDialog(new JFrame(), "Enter the server IP address:",
                
                "Remote File
            System Browser", JOptionPane.QUESTION_MESSAGE);
        if (address == null)

        registry = Loc ateRegistry.getRegistry(address);

        p = (Protocol) registry.lookup("myProtocol");
        files = p.readDirectory(

        defaultDir = currentDir;

        initComponents();
    }

    private String getDateString(long milliseconds) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return sdf.format(new Date(milliseconds));
    }

            
    private String formatSize(long bytes) {
        if (bytes < 1000)  
            return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(1000));
        String pre = "kMGTPE".charAt(exp - 1) + "";
        return String.format("%.1f %sB", bytes / Math.pow(1000, exp), pre);
    }

    private boolean fileExists(String directory, String file) {
        File[] contents = null;
        try {
            contents = p.readDirectory(directory);
        } catch (Remot e Exception ex) {
            Logger.getLogger(RMIClient.class
                getName()).log(Level.SEVERE, null, ex);
        }

            if (item.getName().equals(file))
                return true;
        }

        return false;
    }


        int directoryCount = 0, fileCount = 0;
        String directories = "directories", files = "files";
        File[] contents = null;

        t

        } catch (Remot e Exception ex) {
            Logger.getLogger(RMIClient.class.getName()).log(Level.SEVERE, null, ex);
        }
 (File item : contents) {
            if (p.isDirectory(item)) {
             
         

            }
            
        }
            

            

            return "Empty directory";
        if (directoryCount == 1)
            directories = "directory";
        if (fileCount == 1)
            files = "file";

        return directo r yCount + " " + directories + " and " + fileCount + " " + files;
    }

    private DefaultListModel refreshModel() {
        DefaultListModel model = new DefaultListModel();

        for (File file : files) {
            model.addElement(file.getName());
        }

        return model;   

    private DefaultTableModel refreshProperties() throws RemoteException {
                
        String selectedValue = fileList.getSelectedValue();
        File selectedItem = null;
        for (File file : files) {
            selectedItem = file;

                break;
            
        }
        DefaultTableModel model = new   DefaultTableModel() ;
        model.addColumn("Property");   
        model.addColumn("Value");   


            return model;
        if (p.isDirectory(selectedIte m )) { 
            model.addRow(new String[] { "Type", "Directory" });
            model.addRow(new String[] { " N ame", s
                        lectedItem.getName() }); 
            model.addRow(new String[] { " C ontents", directo
                        yContents(selectedItem) });   
        } 
        else {   
            model.addRow(new String[] { " T ype", "File" }); 
            if (selectedItem.getName().contains(".") && selectedItem.getName().indexOf(".") != 0) {
                model.addRow(new Stri n g[] { "Name", 
         

                        selectedI t em.getName().substring(selectedItem.getName().indexOf(".") + 1).toU ppe

                model.addRow(new String[] { "Name", selectedItem.getName() });
                model.addRow(new String[] { "File Extension", "Unknown" });
            }
            model.addRow(new String[] { "Size", formatSize(p.getLength(selectedItem)) });
        }

        model.addRow(new Object[] { "Last modified", getDateString(p.getLastModifiedDate(selectedItem)) });

        return model;
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        openFolderButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        fileList = new javax.swing.JList<>();
        backButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        titleLabel = new javax.swing.JLabel();
        propertiesLabel = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        propertiesTable = new javax.swing.JTable();
        renameButton = new javax.swing.JButton();
        newFolderButton = new javax.swing.JButton();
        newFileButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        openFolderButton.setText("Open");
        openFolderButton.setEnabled(false);
        openFolderButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                openFolderButtonMouseClicked(evt);
            }
        });

        DefaultListModel listModel = refreshModel();
        fileList.setModel(listModel);
        fileList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                fileListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(fileList);

        backButton.setText("Back");
        backButton.setEnabled(false);
        backButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backButtonMouseClicked(evt);
            }
        });

        deleteButton.setText("Delete");
        deleteButton.setEnabled(false);
        deleteButton.setName("");
        deleteButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deleteButtonMouseClicked(evt);
            }
        });

        titleLabel.setFont(new java.awt.Font("Raleway", 1, 24));
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleLabel.setText("Remote File System ");
        titleLabel.setToolTipText("");

        propertiesLabel.setFont(new java.awt.Font("Raleway", 0, 18));
        propertiesLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        propertiesLabel.setText("Properties ");

        DefaultTableModel tableModel;
        try {
            tableModel = refreshProperties();
            propertiesTable.setModel(tableModel);
        } catch (RemoteException ex) {
            Logger.getLogger(RMIClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        jScrollPane4.setViewportView(propertiesTable);

        renameButton.setText("Rename");
        renameButton.setEnabled(false);
        renameButton.setName("");
        renameButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                renameButtonMouseClicked(evt);
            }
        });

        newFolderButton.setText("New Folder");
        newFolderButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                newFolderButtonMouseClicked(evt);
            }
        });

        newFileButton.setText("New File");
        newFileButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                newFileButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(titleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(newFolderButton)
                                .addGap(18, 18, 18)
                                .addComponent(newFileButton)
                                .addGap(18, 18, 18)
                                .addComponent(openFolderButton, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane1))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(56, 56, 56)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE)
                                    .addComponent(propertiesLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(renameButton)
                                .addGap(18, 18, 18)
                                .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(38, 38, 38)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(propertiesLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(openFolderButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(deleteButton)
                        .addComponent(renameButton)
                        .addComponent(newFolderButton)
                        .addComponent(newFileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(backButton)))
                .addGap(24, 24, 24))
        );

        pack();
    }

    private void openFolderButtonMouseClicked(java.awt.event.MouseEvent evt) {
        if (!openFolderButton.isEnabled())
            return;
        String selectedValue = fileList.getSelectedValue();
        File selectedItem = null;
        for (File file : files) {
            selectedItem = file;
            if (file.getName() == null ? selectedValue == null : file.getName().equals(selectedValue))
                break;
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
        if (!backButton.isEnabled())
            return;

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
        if (!deleteButton.isEnabled())
            return;
        String selectedValue = fileList.getSelectedValue();
        File selectedItem = null;
        for (File file : files) {
            selectedItem = file;
            if (file.getName() == null ? selectedValue == null : file.getName().equals(selectedValue))
                break;
        }

        try {
            String parent = currentDir.getAbsolutePath();

            if (selectedItem.isFile()) {
                p.deleteFile(selectedItem.getAbsolutePath());
            } else {
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
        } else {
            String selectedValue = fileList.getSelectedValue();
            File selectedItem = null;
            for (File file : files) {
                selectedItem = file;
                if (file.getName() == null ? selectedValue == null : file.getName().equals(selectedValue))
                    break;
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
                    } else {
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
        if (!renameButton.isEnabled())
            return;
        String selectedValue = fileList.getSelectedValue();
        File selectedItem = null;
        for (File file : files) {
            selectedItem = file;
            if (file.getName() == null ? selectedValue == null : file.getName().equals(selectedValue))
                break;
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

                if (!fileExists(parent, name + fileExtension))
                    p.rename(selectedItem.getAbsolutePath(), renamed);
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
        if (!newFileButton.isEnabled())
            return;

        try {
            String parent = currentDir.getAbsolutePath();

            
            int copyNumber = 1;
            String[][] fileExtensions = { { "Microsoft Word Document (.docx)", "Microsoft Excel Spreadsheet (.xlsx)",
                    "M i crosof             { ".docx", ".xlsx", ".pptx", ".txt", ".zip" } };
            String fileType = (String) JOptionPane.showInputDialog(new JFrame(),
                    "What type of file would you like to create?", "File Type", JOptionPane.QUESTION_M
                SSAGE, null,
         

                int typeIndex = 0;
                for (String type : fileExtensions[0]) {
                    if (type.equals(fileType))
                        break;
                    typeIndex++;
                }

                String fileExt = fileExtensions[1][typeIndex];
                String fileName = JOptionPane.showInputDialog("File name:");
                if (fileName != null) {
                    if (!"".equals(fileName)) {
                        if (!fileExi
            ts(pare

                        else {
                            while (fileExists(parent, fileName + " (" + copyNumber + ")" + fileExt)) {
                                copyNumber++;
                            }

                            p.createFile(parent + "/" + fileName + " (" + copyNumber + ")" + fileExt);
                        }
                            } else {
                        if (!fileExists(parent, "New File" + fileExt))
                            p.createFile(parent + "/" + "New File" + fileExt);
                        else {
                            while (fileExists(parent, "New File" + " (" + copyNumber + ")" + fileExt)) {
                                copyNumber++;
                            }

                            p.createFi
            e(parent + "/" + "New File" + " (" + copyNumber + ")" + fileExt);
                        }
                    }
                    fi l es = p             fileList.setModel(refreshModel());
                }
            }
                
        }

        }
    }

            void newFolderButtonMouseClicked(java.awt.event.MouseEvent evt) {
        if (!newFolderButton.isEnabled())
            r 
        try {
            String parent = currentDir.getAbsolutePath();

            int copyNumber = 1;
            String folderName = JOptionPane.showInputDialog("Folder name:");
            if (folderName != null) {
                if (!"".equals(folderName)) {
                    if (!fileExists(parent, folderName))
                        p.createDirectory(parent + "/" + folderName);
                    else {
                        while (fileExists(parent, folderName + " (" + copyNumber + ")")) {
                            copyNumber++;
                        }

                      }
                } else {
                    if (!fileExists(parent, "New Folder"))
                        p. c reateD         else {
                        while (fileExists(parent, "New Folder (" + copyNumber + ")")) {
                            copyNumber++;
                    
                        }

                        p.createDirectory(parent + "/" + "New Folder (" + copyNumber + ")");
                    }
                }
                files = p.readDirectory(parent);
                f

                (RemoteException ex) {
            Logger.getLogger(RMIClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    public static void main(String args[]) throws RemoteException, NotBoundException {

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    if (System.getSecurityManager() == null) {
                        System.setSecurityManager(new SecurityManager());
                    }

                    new RMIClient().setVisible(true);
                } catch (RemoteException | NotBoundException ex) {
                    Logger.getLogger(R
            IClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });   

    private javax.swing.JButton backButton;
                
    priva

        ate javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JButton newFil

            javax.swing.JButton openFolderButton;
    private javax.swing.JLabel propertiesLabel;
    private javax.swing.JTable propertiesTable;
    private javax.swing.JButton renameButton;
    private javax.swing.JLabel titleLabel;

}

                
                    
                

                    
            

        

              
                     
                       
                       
                       
                        

                
                            
                            
                                
                            

                             
                            
                            
                                
                            

                            
            

        

            
                    
                        
                     
                        
                
                   

                    

