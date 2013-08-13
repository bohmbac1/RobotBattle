package gui;

import entities.Robot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: stantonbohmbach
 * Date: 8/12/13
 * Time: 9:56 AM
 * To change this template use File | Settings | File Templates.
 */
public class SelectRobotFrame {
    private JFrame frame;
    private JPanel panel;
    private List<JCheckBox> robotChecks;
    private JButton selectButton;

    private List<Robot> possibleRobots;
    private List<Robot> chosenRobots;

    public SelectRobotFrame(List<Robot> possibleRobots, List<Robot> chosenRobots) {
        this.possibleRobots = possibleRobots;
        this.chosenRobots = chosenRobots;

        robotChecks = new ArrayList<JCheckBox>();

        frame = new JFrame("Select Robot");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setUpPanel();

        frame.add(panel);
        frame.setVisible(true);
        frame.pack();
    }

    private void setUpPanel() {
        panel = new JPanel();

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);
        c.gridx = 0;
        c.gridy = 0;

        for (Robot possible : possibleRobots) {
            JCheckBox checkbox = new JCheckBox(possible.getName());
            robotChecks.add(checkbox);
            panel.add(checkbox, c);
            c.gridy++;
        }

        selectButton = new JButton("OK");
        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Robot> toRemove = new ArrayList<Robot>();

                for (JCheckBox box : robotChecks) {
                    if (box.isSelected()) {
                        Robot robot = possibleRobots.get(robotChecks.indexOf(box));
                        chosenRobots.add(robot);
                        toRemove.add(robot);
                    }
                }

                for (Robot r : toRemove) {
                    possibleRobots.remove(r);
                }
                frame.setVisible(false);
                frame.dispose();
            }
        });
    }

}
