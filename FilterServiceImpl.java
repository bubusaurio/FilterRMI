package FilterRMI;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class FilterServiceImpl extends UnicastRemoteObject implements FilterService {
    private List<String[]> arraysToSort = new ArrayList<>();

    protected FilterServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public synchronized void sendArray(String[] array) throws RemoteException {
        arraysToSort.add(array);
    }

    @Override
    public synchronized String[] getFilteredArraySequential(char letter) throws RemoteException {
        String[] combinedArray = combineArrays();
        return Sequential.filter(combinedArray,letter);
    }

    @Override
    public synchronized String[] getFilteredArrayForkJoin(char letter) throws RemoteException {
        String[] combinedArray = combineArrays();
        return ForkJoin.filter(combinedArray, letter);
    }

    @Override
    public synchronized String[] getFilteredArrayExecutorService(char letter) throws RemoteException {
        String[] combinedArray = combineArrays();
        return ExecutorServiceFilter.filter(combinedArray, letter);
    }

    @Override
    public synchronized void clearArrays() throws RemoteException {
        arraysToSort.clear();
    }

    private String[] combineArrays() {
        return arraysToSort.stream().flatMap(Arrays::stream).toArray(String[]::new);
    }
}
