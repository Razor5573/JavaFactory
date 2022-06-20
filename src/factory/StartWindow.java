package factory;

import javax.swing.*;
import javax.swing.text.html.ImageView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.io.IOException;
import java.util.ArrayList;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class StartWindow {
    public void makeStartWindow(ArrayList<Integer> numbers, ArrayList<Boolean> parameters) {
        parameters.add(false);
        parameters.add(false);
        JFrame frame = new JFrame();
        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        frame.setLayout(grid);
        frame.setTitle("Settings");
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.1;
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(new JLabel("Car storage size"), gbc);
        gbc.gridy = 1;
        frame.add(new JLabel("Engine storage size"), gbc);
        gbc.gridy = 2;
        frame.add(new JLabel("Accessory storage size"), gbc);
        gbc.gridy = 3;
        frame.add(new JLabel("Body storage size"), gbc);
        gbc.gridy = 4;
        frame.add(new JLabel("Dealers"), gbc);
        gbc.gridy = 5;
        frame.add(new JLabel("Workers"), gbc);
        gbc.gridy = 6;
        JCheckBox logging = new JCheckBox("Enable logging");
        frame.add(logging, gbc);
        logging.addItemListener(e -> {
            if ((e.getStateChange() == ItemEvent.SELECTED)) {
                parameters.set(0, true);
            } else {
                parameters.set(0, false);
            }
        });
        gbc.gridy = 7;
        JCheckBox resources = new JCheckBox("Use resources", true);
        resources.addItemListener(e -> {
            if ((e.getStateChange() == ItemEvent.SELECTED)) {
                parameters.set(1, true);
            } else {
                parameters.set(1, false);
            }
        });
        frame.add(resources, gbc);

        gbc.fill = GridBagConstraints.NONE;
        JButton doneButton = new JButton("Done!");
        gbc.weightx = 0.1;
        gbc.gridy = 8;
        gbc.gridx = 0;
        frame.add(doneButton, gbc);
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 100, 10, 10);
        frame.add(new JLabel("Engine supplier speed"), gbc);
        gbc.gridy = 2;
        frame.add(new JLabel("Accessory supplier speed"), gbc);
        gbc.gridy = 3;
        frame.add(new JLabel("Body supplier speed"), gbc);
        gbc.gridy = 4;
        frame.add(new JLabel("Dealers speed"), gbc);
        gbc.gridy = 5;
        frame.add(new JLabel("Workers speed"), gbc);
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 1;
        gbc.ipadx = 30;
        gbc.ipady = 5;
        ArrayList<JTextField> textFields = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            gbc.gridy = i;
            textFields.add(new JTextField("10"));
            textFields.get(i).setEditable(true);
            frame.add(textFields.get(i), gbc);
        }
        gbc.gridx = 3;
        gbc.weightx = 0.1;
        for (int i = 1; i < 6; i++) {
            gbc.gridy = i;
            textFields.add(new JTextField("1000"));
            textFields.get(i + 5).setEditable(true);
            frame.add(textFields.get(i + 5), gbc);
        }

        doneButton.addActionListener(e -> {
            int i = 0;
            for (JTextField field : textFields) {
                String text = field.getText();
                Integer result;
                try {
                    result = Integer.parseInt(text);
                    numbers.add(result);
                    i++;
                } catch (Exception e1) {
                    doneButton.setText("Error!");
                    break;
                }
            }
            if (i == textFields.size()) {
                frame.setVisible(false);
                try {
                    Main.startFactory(numbers, parameters);
                } catch (InterruptedException | IOException e1) {
                    return;
                }
            }
        });

        frame.setSize(550, 600);
        frame.setPreferredSize(frame.getSize());
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

    }
}
