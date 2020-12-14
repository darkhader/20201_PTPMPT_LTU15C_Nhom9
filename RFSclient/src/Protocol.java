import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;

interface Protocol extends Remote{
    
    public String getDefaultDirectoryPath() throws RemoteException;
    
    public File[] readDirectory(String directoryName) throws RemoteException;
    
    public void createDirectory(String directoryName) throws RemoteException;
    
    public void deleteFile(String name) throws RemoteException;
    
    public void deleteDirectory(String name) throws RemoteException;
    
    public void createFile(String name) throws RemoteException;
    
    public void rename(String name, String nameNew) throws RemoteException;
    
    public boolean isDirectory(File file) throws RemoteException;
    
    public boolean isFile(File file) throws RemoteException;
    
    public long getLength(File file) throws RemoteException;
    
    public long getLastModifiedDate(File file) throws RemoteException;
}
