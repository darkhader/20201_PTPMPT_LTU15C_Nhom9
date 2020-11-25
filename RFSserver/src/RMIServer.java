import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
public class RMIServer {

    public static void main(String[] args) throws RemoteException {
        if(System.getSecurityManager()==null) {
            System.setSecurityManager(new SecurityManager());
        }
        RemoteProtocol p = new RemoteProtocol();
        Registry registry = LocateRegistry.createRegistry(1099);
        Protocol pp = (Protocol)UnicastRemoteObject.exportObject((Remote) p,0);
        registry.rebind("myProtocol", (Remote) pp);
        pp.createDirectory(pp.getDefaultDirectoryPath());
    }
}
