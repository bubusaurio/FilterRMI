package FilterRMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FilterService extends Remote {
    void sendArray(String[] array) throws RemoteException;
    String[] getFilteredArraySequential(char letter) throws RemoteException;
    String[] getFilteredArrayForkJoin(char letter) throws RemoteException;
    String[] getFilteredArrayExecutorService(char letter) throws RemoteException;
    void clearArrays() throws RemoteException;
}
