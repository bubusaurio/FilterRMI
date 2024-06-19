package FilterRMI;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.swing.*;

public class FilterServer extends JFrame {
    private FilterServiceImpl filterService;

    public FilterServer() {
        try {
            filterService = new FilterServiceImpl();
            Registry registry = LocateRegistry.createRegistry(1100);
            registry.rebind("FilteringService", filterService);
            System.out.println("Filtering Service is ready.");

            setTitle("Filtering Server");
            setSize(500, 400);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(null);

            JButton sortSequentialButton = new JButton("Filter Sequential");
            JButton sortForkJoinButton = new JButton("Filter ForkJoin");
            JButton sortExecutorServiceButton = new JButton("Filter ExecutorService");
            JButton clearArrayButton = new JButton("Clear Arrays");
            JLabel timeLabel = new JLabel("Time:");

            sortSequentialButton.setBounds(50, 50, 200, 30);
            sortForkJoinButton.setBounds(50, 100, 200, 30);
            sortExecutorServiceButton.setBounds(50, 150, 200, 30);
            clearArrayButton.setBounds(50, 200, 200, 30);
            timeLabel.setBounds(50, 250, 400, 30);


            add(clearArrayButton);

            sortSequentialButton.addActionListener(e -> {
                long startTime = System.nanoTime();
                //sortingService.getSortedArraySequential()();
                long endTime = System.nanoTime();
                long duration = (endTime - startTime) / 1000000;
                timeLabel.setText("Sequential filter time: " + duration + " ms");
            });

            sortForkJoinButton.addActionListener(e -> {
                long startTime = System.nanoTime();
                //sortingService.getSortedArrayForkJoin()();
                long endTime = System.nanoTime();
                long duration = (endTime - startTime) / 1000000;
                timeLabel.setText("ForkJoin filter time: " + duration + " ms");
            });

            sortExecutorServiceButton.addActionListener(e -> {
                long startTime = System.nanoTime();
                //sortingService.getSortedArrayExecutorService();
                long endTime = System.nanoTime();
                long duration = (endTime - startTime) / 1000000;
                timeLabel.setText("ExecutorService filter time: " + duration + " ms");
            });

            clearArrayButton.addActionListener(e -> {
                try {
                    filterService.clearArrays();
                } catch (RemoteException e1) {
                    e1.printStackTrace();
                }
                timeLabel.setText("Arrays cleared.");
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FilterServer server = new FilterServer();
            server.setVisible(true);
        });
    }
}
