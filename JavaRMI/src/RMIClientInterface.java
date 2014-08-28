import com.google.common.collect.ListMultimap;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface RMIClientInterface extends Remote {
    void retrieveUpdate(String update) throws RemoteException;

    void retrievePublications(ListMultimap<String, String> publications, String name)throws RemoteException;

    void addToSubscription(String publisher, String clientName) throws RemoteException;

    void retrieveAccountStatus(ListMultimap<String, String> publications, String name) throws RemoteException;

    void retrieveMyPublications(ListMultimap<String, String> publications, String name)throws RemoteException;
}
