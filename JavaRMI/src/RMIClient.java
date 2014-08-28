/*
Source from:

Java Programming for Engineers
Julio Sanchez
Maria P. Canton


ISBN: 0849308100
Publisher: CRC Press
*/

import com.google.common.collect.ListMultimap;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class RMIClient extends UnicastRemoteObject implements
        RMIClientInterface, Runnable {
    private RMIServerInterface RMIServer;
    private String name = null;
    private ArrayList<String> subscriptions = new ArrayList<String>();

    protected RMIClient(String name, RMIServerInterface RMIServer)
            throws RemoteException {
        this.name = name;
        this.RMIServer = RMIServer;
        RMIServer.registerRMIClient(this);
    }

    @Override
    public void retrieveUpdate(String update) throws RemoteException {
        System.out.println(update);
    }

    @Override
    public void retrievePublications(ListMultimap<String, String> publications, String clientName) throws RemoteException {
        if (clientName.equals(name)) {
            System.out.println("****************\n" +
                    "**Publications**\n" +
                    "****************\n" +
                    " Publisher:item\n" +
                    "----------------");
            if (publications.isEmpty()) {
                System.out.println("No publication available.");
            } else {
                for (String publisher : publications.keySet()) {
                    List<String> items = publications.get(publisher);
                    System.out.println(publisher + " : " + items);
                }
            }
            System.out.println("----------------");
        }
    }

    @Override
    public void addToSubscription(String publisher, String clientName) throws RemoteException {
        if (clientName.equals(name)) {

            System.out.println("****************\n" +
                    "*Subscriptions**\n" +
                    "***************\n");
            if (!subscriptions.isEmpty()) {
                for (String subscription : subscriptions) {
                    System.out.println(subscription);
                }
            } else System.out.println("Subscriptions List empty.");
            if (subscriptions.isEmpty()) {
                subscriptions.add(publisher);
                System.out.println("Thanks for subscribing to " + publisher + "'s items.");
            } else if (subscriptions.contains(publisher)) {
                System.out.println("You have already subscribed this item.");
            } else {
                subscriptions.add(publisher);
            }
        }
    }

    @Override
    public void retrieveAccountStatus(ListMultimap<String, String> publications, String clientName) throws RemoteException {
        if (clientName.equals(name)) {
            int subNum = 0;
            int pubNum = 0;
            if (!publications.isEmpty()) {
                for (String publisher : publications.keySet()) {
                    if (publisher.equals(name)) {
                        List<String> items = publications.get(publisher);
                        pubNum = items.size();
                        break;
                    }
                }
            }
            if (!subscriptions.isEmpty()) subNum = subscriptions.size();
            System.out.println("|  Publications:" + pubNum + "  Subscriptions:" + subNum + "  |");
        }
    }

    @Override
    public void retrieveMyPublications(ListMultimap<String, String> publications, String clientName)throws RemoteException {
        int count = 0;
        if (clientName.equals(name)) {
            if (publications.isEmpty()) {
                System.out.println("|           No publication          |");
            } else {
                for (String publisher : publications.keySet()) {
                    if (publisher.equals(name)) {

                        List<String> items = publications.get(publisher);
                        for (int i = 0; i < items.size(); i++) {
                            count++;
                            System.out.println("          " + count + ". " + items.get(i));
                        }
                    }
                }
            }
        }
    }

    public static void clearConsole() throws IOException {
        Runtime.getRuntime().exec("cls");
    }

    public void mainMenu() {
        System.out.println("=====================================");
        System.out.println("|   Szu-Han's Subscription System   |");
        System.out.println("|           RegNum:1101956          |");
        System.out.println("=====================================");
        System.out.println("|           Account Status          |");
        try {
            RMIServer.accountStatus(name);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        System.out.println("|                                   |");
        System.out.println("|               Menu                |");
        System.out.println("|   1. Publish/Update/Delete item   |");
        System.out.println("|   2. Subscribe/Unsubscribe item   |");
        System.out.println("|   3. Exit                         |");
        System.out.println("=====================================");
    }

    public void subMenu1() {
        System.out.println("=====================================");
        System.out.println("|   Szu-Han's Subscription System   |");
        System.out.println("|           RegNum:1101956          |");
        System.out.println("=====================================");
        System.out.println("|           Account Status          |");
        try {
            RMIServer.accountStatus(name);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        System.out.println("|                                   |");
        System.out.println("|          My Publications          |");
        /*print my own publication*/
        try {
            RMIServer.myPublication(name);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        System.out.println("|                                   |");
        System.out.println("|        Publish/Update/Delete      |");
        System.out.println("|         1. Publish item           |");
        System.out.println("|         2. Update item            |");
        System.out.println("|         3. Delete item            |");
        System.out.println("|         4. Back                   |");
        System.out.println("=====================================");
    }


    public void run() {

        Scanner scanner = new Scanner(System.in);
        int swValue;

        while (true) {

            mainMenu();

            swValue = Keyin.inInt("Select option:");
            switch (swValue) {

                case 1:
                    loop:
                    while (true) {
                        subMenu1();
                        int options = Keyin.inInt("Select option:");
                        switch (options) {
                            case 1:
                                System.out.println("Input item title:");
                                String item = scanner.nextLine();
                                try {
                                    RMIServer.publish(name, item);
                                } catch (RemoteException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                                break;
                            case 2:
                                System.out.println("option 2 selected");
                                break;
                            case 3:
                                System.out.println("option 3 selected");
                                break;
                            case 4:
                                break loop;
                            default:
                                System.out.println("Invalid Input.");
                                break;
                        }
                    }
                    break;
                case 2:
                    try {
                        RMIServer.showPublications(name);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }

                    System.out.println("Type 1 for subscribe an item\n" +
                            "Type 0 to go back to mean menu");
                    int option = Integer.parseInt(scanner.nextLine());
                    if (option == 1) {
                        System.out.println("Please type publisher that you want to subscribe:");
                        String subPub = scanner.nextLine();
                        try {
                            RMIServer.subscribe(subPub, name);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }

                    break;
                case 3:
                    System.out.println("Thanks for using Szu-Han's subscription system!");
                    System.exit(0);
                default:
                    System.out.println("Invalid Input.");
                    break;

            }
        }
    }


}
