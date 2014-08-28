import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIServerInterface extends Remote {
    void registerRMIClient(RMIClientInterface RMIClient) throws RemoteException;

    void broadcastUpdate(String update) throws RemoteException;

    void subscribe(String publisher, String clientName) throws RemoteException;

    void unsubscribe(int PID) throws RemoteException;

    void publish(String PID, String item) throws RemoteException;

    void update(int PID, String items) throws RemoteException;

    void delete(int PID) throws RemoteException;

    void showPublications(String name) throws RemoteException;

    void accountStatus(String name) throws RemoteException;

    void myPublication(String name) throws RemoteException;
}
