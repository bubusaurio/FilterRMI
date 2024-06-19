package FilterRMI;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.swing.*;
import java.util.Arrays;

public class FilterClient extends JFrame {
    private FilterService filterService;
    public boolean isChecked = false;
    public static double seqtime = 0;
    public static double fjtime = 0;
    public static double extime = 0;


    public FilterClient() {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1100);
            filterService = (FilterService) registry.lookup("FilteringService");
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Filtering Client");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel arraySizeLabel = new JLabel("Array Size:");
        final JTextField arraySizeField = new JTextField();
        JTextArea arrayArea = new JTextArea();
        JLabel filterLetterLabel = new JLabel("Filter Letter:");
        JTextField filterLetterField = new JTextField();
        arrayArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(arrayArea);
        JButton sendArrayButton = new JButton("Send Array");
        JButton getSortedArraySeqButton = new JButton("Get Sequential Filtered Array");
        JButton getSortedArrayFjButton = new JButton("Get ForkJoin Filtered Array");
        JButton getSortedArrayEsButton = new JButton("Get ExecutorService Filtered Array");
        JButton clear = new JButton("Clear");
        JLabel timeLabel = new JLabel("Filtered Time:");
        JLabel time2Label = new JLabel("Filtered Time:");
        JLabel time3Label = new JLabel("Filtered Time:");

        arraySizeLabel.setBounds(50, 20, 100, 30);
        arraySizeField.setBounds(120, 20, 100, 30);
        filterLetterLabel.setBounds(250, 20, 100, 30);
        filterLetterField.setBounds(350, 20, 100, 30);
        scrollPane.setBounds(50, 60, 300, 100);
        sendArrayButton.setBounds(50, 170, 150, 30);
        getSortedArraySeqButton.setBounds(50, 210, 300, 30);
        getSortedArrayFjButton.setBounds(50, 250, 300, 30);
        getSortedArrayEsButton.setBounds(50, 290, 300, 30);
        clear.setBounds(200, 170,150,30);
        timeLabel.setBounds(50, 330, 300, 30);
        time2Label.setBounds(50, 360, 300, 30);
        time3Label.setBounds(50, 390, 300, 30);

        add(arraySizeLabel);
        add(arraySizeField);
        add(filterLetterField);
        add(filterLetterLabel);
        add(scrollPane);
        add(sendArrayButton);
        add(getSortedArraySeqButton);
        add(getSortedArrayFjButton);
        add(getSortedArrayEsButton);
        add(clear);
        add(timeLabel);
        add(time2Label);
        add(time3Label);

        sendArrayButton.addActionListener(e -> {
            int size = Integer.parseInt(arraySizeField.getText());
            String[] array = RandomWordGenerator.generateRandomWordsArray(size);
            try {
                filterService.sendArray(array);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            arrayArea.setText(Arrays.toString(array));
        });

        getSortedArraySeqButton.addActionListener(e -> {
            char letter = filterLetterField.getText().charAt(0);
            try {
                //do {
                    for(int i=0;i<2;i++){
                        long startTime = System.nanoTime();
                        String[] sortedArray = filterService.getFilteredArraySequential(letter);
                        int size = sortedArray.length;
                        long endTime = System.nanoTime();
                        long duration = endTime - startTime;
                        double time = duration / 10000.0;
                        seqtime = time;
                        //check(size);
                        timeLabel.setText("Sequential filtered time: " + String.format("%.9f", time) + " ms");
                        arrayArea.setText(Arrays.toString(sortedArray));
                    }
                //} while (!isChecked);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        getSortedArrayFjButton.addActionListener(e -> {
            char letter = filterLetterField.getText().charAt(0);
            try {
                //do {
                    for(int i = 0; i<10;i++){
                        long startTime = System.nanoTime();
                        String[] sortedArray = filterService.getFilteredArrayForkJoin(letter);
                        int size = sortedArray.length;
                        long endTime = System.nanoTime();
                        long duration = endTime - startTime;
                        double time = duration / 10000.0;
                        fjtime = time;
                        //check(size);
                        time2Label.setText("ForkJoin filtered time: " + String.format("%.9f", time) + " ms");
                        arrayArea.setText(Arrays.toString(sortedArray));
                    }

                //} while (!isChecked);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        getSortedArrayEsButton.addActionListener(e -> {
            char letter = filterLetterField.getText().charAt(0);
            try {
                //do {
                    for(int i = 0;i<10;i++){
                        long startTime = System.nanoTime();
                        String[] sortedArray = filterService.getFilteredArrayExecutorService(letter);
                        int size = sortedArray.length;
                        long endTime = System.nanoTime();
                        long duration = endTime - startTime;
                        double time = duration / 10000.0;
                        extime = time;
                        //check(size);
                        time3Label.setText("ExecutorService filtered time: " + String.format("%.9f", time) + " ms");
                        arrayArea.setText(Arrays.toString(sortedArray));
                    }
                //} while (!isChecked);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        clear.addActionListener(e ->{
            seqtime = 0;
            fjtime = 0;
            extime = 0;
            time2Label.setText("");
            time3Label.setText("");
            timeLabel.setText("");
            arrayArea.setText("");
        });
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FilterClient client = new FilterClient();
            client.setVisible(true);
        });
    }
}
