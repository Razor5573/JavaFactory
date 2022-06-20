package factory;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class View implements java.util.Observer {
    private Observer observered;

    private ArrayList<JLabel> firstColumn;
    private ArrayList<JLabel> secondColumn;
    private ArrayList<JProgressBar> thirdColumn;


    View() {
        firstColumn = new ArrayList<>();
        secondColumn = new ArrayList<>();
        thirdColumn = new ArrayList<>();
    }

    public void setObserver(Observer observer) {
        this.observered = observer;
    }

    public void startSwing() {
        JFrame frame = new JFrame();
        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        frame.setLayout(grid);
        frame.setTitle("Factory");
        GridBagLayout layout = new GridBagLayout();
        frame.setLayout(layout);


        firstColumn.add(new JLabel("Bodies"));
        firstColumn.add(new JLabel("Engines"));
        firstColumn.add(new JLabel("Accessories"));
        /*for (int i = 0; i < observered.getMap().size(); i++) {
            firstColumn.add(new JLabel("Worker " + i));
        }*/
        int j = 0;
        for (Map.Entry<String, Integer> worker : observered.getMap().entrySet()) {
            firstColumn.add(new JLabel(worker.getKey()));
            j++;
        }
        firstColumn.add(new JLabel("Cars"));
        firstColumn.add(new JLabel("Sold cars"));

        secondColumn.add(new JLabel(observered.getBodyStorageCurrent() + "/" + observered.getBodyStorageMax()));
        secondColumn.add(new JLabel(observered.getEngineStorageCurrent() + "/" + observered.getEngineStorageMax()));
        secondColumn.add(new JLabel(observered.getAccessoryStorageCurrent() + "/" + observered.getAccessoryStorageMax()));
        for (int i = 0; i < observered.getMap().size(); i++) {
            secondColumn.add(new JLabel("Sleeping..."));
        }
        secondColumn.add(new JLabel(observered.getCarStorageCurrent() + "/" + observered.getCarStorageMax()));
        secondColumn.add(new JLabel(Integer.toString(observered.getCarsSold())));

        thirdColumn.add(new JProgressBar(0, observered.getBodyStorageMax()));
        thirdColumn.add(new JProgressBar(0, observered.getEngineStorageMax()));
        thirdColumn.add(new JProgressBar(0, observered.getAccessoryStorageMax()));
        thirdColumn.add(new JProgressBar(0, observered.getCarStorageMax()));

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.LINE_START;
        //gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.1;
        gbc.weighty = 1;
        gbc.gridx = 0;
        for (int i = 0; i < firstColumn.size(); i++) {
            gbc.gridy = i;
            frame.add(firstColumn.get(i), gbc);
        }

        gbc.gridx = 1;
        for (int i = 0; i < secondColumn.size(); i++) {
            gbc.gridy = i;
            frame.add(secondColumn.get(i), gbc);
        }
        gbc.weightx = 4;
        gbc.gridx = 2;
        gbc.ipady = 20;
        gbc.ipadx = 400;
        gbc.anchor = GridBagConstraints.EAST;
        for (int i = 0; i < 3; i++) {
            gbc.gridy = i;
            frame.add(thirdColumn.get(i), gbc);
        }
        gbc.gridy = 3 + observered.getMap().size();
        frame.add(thirdColumn.get(3), gbc);


        frame.setSize(800, 600);
        frame.setPreferredSize(frame.getSize());
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }


    @Override
    public void update(Observable o, Object arg) {
        synchronized (this) {
            secondColumn.get(0).setText(observered.getBodyStorageCurrent() + "/" + observered.getBodyStorageMax());
            secondColumn.get(1).setText(observered.getEngineStorageCurrent() + "/" + observered.getEngineStorageMax());
            secondColumn.get(2).setText(observered.getAccessoryStorageCurrent() + "/" + observered.getAccessoryStorageMax());
            secondColumn.get(3 + observered.getMap().size()).setText(observered.getCarStorageCurrent() + "/" + observered.getCarStorageMax());
            secondColumn.get(4 + observered.getMap().size()).setText(Integer.toString(observered.getCarsSold()));
            thirdColumn.get(0).setValue(observered.getBodyStorageCurrent());
            thirdColumn.get(1).setValue(observered.getEngineStorageCurrent());
            thirdColumn.get(2).setValue(observered.getAccessoryStorageCurrent());
            thirdColumn.get(3).setValue(observered.getCarStorageCurrent());
            int i = 0;
            for (Map.Entry<String, Integer> worker : observered.getMap().entrySet()) {
                switch (worker.getValue()) {
                    case 0:
                        secondColumn.get(3 + i).setText("Sleeping...");
                        break;
                    case 1:
                        secondColumn.get(3 + i).setText("Waiting for details");
                        break;
                    case 2:
                        secondColumn.get(3 + i).setText("Working");
                        break;
                }
                i++;
            }
        }
    }
}
