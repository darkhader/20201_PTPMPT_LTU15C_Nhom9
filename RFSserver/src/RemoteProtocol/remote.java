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