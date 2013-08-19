package gui;

import entities.Arena;
import entities.Robot;
import libraries.RobotLibrary;

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
 * 
 * 
 * The JFrame is for selecting robots. It gives an options of robots and allows the 
 * user to select some of them to put into the battle.
 */
public class SelectRobotFrame extends LayeredFrame {
	private RobotLibrary robotLibrary;

    private JPanel panel;
    private List<JCheckBox> robotChecks;
    private List<JTextField> robotCounts;
    private JButton selectButton;

    public SelectRobotFrame(MainFrame parent) {
    	super(parent);
    	
    	this.robotLibrary = new RobotLibrary(((MainFrame)parent).getArena());

        robotChecks = new ArrayList<JCheckBox>();
        robotCounts = new ArrayList<JTextField>();

        frame = new JFrame("Select Robot");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setUpPanel();

        frame.add(panel);
        frame.setVisible(true);
        frame.pack();
    }

    /**
     * Set up the panel for what it does!
     */
    private void setUpPanel() {
        panel = new JPanel(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);
        c.gridx = 0;
        c.gridy = 0;

        for (String robotName : robotLibrary.getRobotNames()) {
        	c.gridx = 0;
            JCheckBox checkbox = new JCheckBox(robotName);
            robotChecks.add(checkbox);
            panel.add(checkbox, c);
            JTextField numberArea = new JTextField("1");
            numberArea.setVisible(false);
            robotCounts.add(numberArea);
            c.gridx++;
            panel.add(numberArea, c);
            c.gridy++;
        }

        selectButton = new JButton("OK");
        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                for (int i = 0; i < robotChecks.size(); i++) {
                	JCheckBox box = robotChecks.get(i);
                	
                    if (box.isSelected()) {
                        addRobotToChosenRobots(box.getText());
                    }
                }

                frame.setVisible(false);
                frame.dispose();
            }

			private void addRobotToChosenRobots(String robotName) {
				Robot robot = robotLibrary.getRobotByName(robotName);
				((MainFrame)parent).addRobot(robot);
				for(Thread thread : robotLibrary.getThreadsForRobot(robot)) {
					System.out.println("adding thread for robot : " + robotName);
					((MainFrame)parent).addThread(thread);
				}
			}
        });
        
        panel.add(selectButton);
    }

}
