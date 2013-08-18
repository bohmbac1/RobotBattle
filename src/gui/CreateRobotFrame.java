package gui;

import entities.Robot;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: stantonbohmbach
 * Date: 8/12/13
 * Time: 9:57 AM
 * To change this template use File | Settings | File Templates.
 */
public class CreateRobotFrame {
    private static JFrame frame;
    private static JPanel chooseAppearancePanel;
    private static JPanel chooseMovementPanel;
    private static JPanel chooseAttackPanel;

    public CreateRobotFrame(List<Robot> chosenRobots) {
        frame = new JFrame("Robot Battle Creator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setUpChooseAppearancePanel();
        setUpChooseMovementPanel();
        setUpChooseAttackPanel();

        frame.add(chooseAppearancePanel, BorderLayout.NORTH);
        frame.add(chooseMovementPanel);
        frame.add(chooseAttackPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
        frame.pack();
    }

    private void setUpChooseAppearancePanel() {

    }

    private void setUpChooseMovementPanel() {

    }

    private void setUpChooseAttackPanel() {

    }

}
