import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;


public class RMIServerDriver {
    public static String serverName = "localhost";
    public static final int registryPortNumber = 1099;
    public static final String serviceName = "localhost";

    public static void main(String[] args) throws RemoteException, MalformedURLException {
        try {
            LocateRegistry.createRegistry(registryPortNumber);
            System.out.println("Starting a new registry process");
        } catch (RemoteException e) {
            System.out.println("Not starting a new registry process");
        }
        Naming.rebind("//" + serverName + ":" + registryPortNumber + "/" + serviceName, new RMIServer());
        System.out.println("Server: " + serverName + " is connected");

    }
}
