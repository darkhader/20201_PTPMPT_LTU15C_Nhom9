import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RemoteProtocol implements Protocol{
    
    @Override
    public String getDefaultDirectoryPath() throws RemoteException {
       return System.getProperty("user.home") + "/RFS";

    }
    
    @Override
    public File[] readDirectory(String directoryName) throws RemoteException {
        File file = null;
        File[] fileList = null;
        
        try {
            file = new File(directoryName);
            fileList = file.listFiles();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        
        return fileList;
    }
     
    @Override
    public void createDirectory(String directoryName) throws RemoteException {
        File directory = new File(directoryName);
        
        try{
            directory.mkdir();
        } 
        catch(SecurityException se){
            se.printStackTrace();
        } 
    }

    @Override
    public void deleteFile(String name) throws RemoteException {
        File fileName = new File(name);
        
        try {
            fileName.delete();
        }
        catch(SecurityException se){
            se.printStackTrace();
        }
    }

    
    @Override
    public void deleteDirectory(String name) throws RemoteException {
        File directory = new File(name);
        File[] files = directory.listFiles();
        
        if (files != null) { 
            for (File f:files) {
                if (f.isDirectory()) {
                    deleteDirectory(f.getAbsolutePath());
                } 
                else {
                    f.delete();
                }
            }
        }
        directory.delete();
    }

 @Override
    public boolean isDirectory(File file) throws RemoteException {
        return file.isDirectory();
    }

    @Override
    public boolean isFile(File file) throws RemoteException {
        return file.isFile();
    }

    @Override
    public long getLength(File file) throws RemoteException {
        return file.length();
    }

    @Override
    public long getLastModifiedDate(File file) throws RemoteException {
        return file.lastModified();
    }
}