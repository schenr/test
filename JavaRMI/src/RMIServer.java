import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by schenr on 14/02/14.
 */
public class RMIServer extends UnicastRemoteObject implements
		RMIServerInterface {
	private static final long serialVersionUID = 1L;
	private ArrayList<RMIClientInterface> RMIClients;
//	public Map<Integer, String> publications = new HashMap<Integer, String>();
    private ArrayList<Integer> subscriptions = new ArrayList<Integer>();
    private ListMultimap<String, String> publications = ArrayListMultimap.create();

	protected RMIServer() throws RemoteException {
		RMIClients = new ArrayList<RMIClientInterface>();
		//publications = new HashMap<Integer, String>();
	}

	@Override
	public synchronized void registerRMIClient(RMIClientInterface RMIClient)
			throws RemoteException {
		this.RMIClients.add(RMIClient);
        int i = 0;
        while (i < RMIClients.size()) {
            System.out.println(RMIClients.get(i++).getClass());

        }

	}

	@Override
	public synchronized void broadcastUpdate(String update)
			throws RemoteException {
		int i = 0;
		while (i < RMIClients.size()) {

			RMIClients.get(i++).retrieveUpdate(update);

        }
	}

	@Override
	public synchronized void subscribe(String publisher, String clientName) throws RemoteException {
        int i = 0;
        while (i < RMIClients.size()) {

            RMIClients.get(i++).addToSubscription(publisher,clientName);

        }
	}

	@Override
	public synchronized void unsubscribe(int PID) throws RemoteException {
		for (int i = 0; i < subscriptions.size(); i++) {
			if (subscriptions.get(i) == PID) {
				subscriptions.remove(i);
			}
		}
	}

	@Override
	public synchronized void publish(String name, String item) throws RemoteException {
        publications.put(name, item);
	}

	@Override
	public synchronized void update(int PID, String items)
			throws RemoteException {
		//publications.put(PID, items);
	}

	@Override
	public synchronized void delete(int PID) throws RemoteException {
//		publications.remove(PID);
	}

    @Override
    public synchronized void showPublications(String name) throws RemoteException {
        int i = 0;
        while (i < RMIClients.size()) {

            RMIClients.get(i++).retrievePublications(publications,name);

        }
    }

    @Override
    public synchronized void accountStatus(String name) throws RemoteException {
        int i = 0;
        while (i < RMIClients.size()) {

            RMIClients.get(i++).retrieveAccountStatus(publications,name);

        }
    }

    @Override
    public synchronized void myPublication(String name) throws RemoteException {
        int i = 0;
        while (i < RMIClients.size()) {
            RMIClients.get(i++).retrieveMyPublications(publications,name);
        }
    }


}
