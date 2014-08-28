import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class RMIClientDriver {
    public static String serverName = "localhost";
    public static final int registryPortNumber = 1099;
    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        System.out.println("Please input your name:");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();

        RMIServerInterface RMIServer = (RMIServerInterface) Naming.lookup("//" + serverName + ":" + registryPortNumber + "/localhost");
        new Thread(new RMIClient(name, RMIServer)).start();
    }
}
