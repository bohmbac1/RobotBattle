package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

import translation.RobotSelectionListener;
import entities.Robot;

public class SelectionMaker {
	private static JFrame frame;
	
	// Robot panel
	private static JPanel robotPanel;
	private static JPanel prexistingRobotPanel;
	private static JPanel makeRobotPanel;
	private static JPanel choseAppearancePanel;
	private static JPanel choseMovementPanel;
	private static JPanel chooseAttackPanel;
	
	private static List<Robot> possibleRobots;
	private static List<Robot> chosenRobots;
	
	private static int width;
	private static int height;

	private JLabel arenaLabel;
	private JLabel arenaWidthLabel;
	private JLabel arenaXLabel;
	private JLabel arenaHeightLabel;
	private JTextArea arenaWidthChooser;
	private JTextArea arenaHeightChooser;

	private ButtonGroup robotMethodGroup;

	private JRadioButton existingRobotButton;

	private JRadioButton createRobotButton;
	
	
	public SelectionMaker() {
		frame = new JFrame("Robot Battle Creator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setUpArenaPanel();
		setUpRobotPanel();
		
		frame.add(robotPanel, BorderLayout.EAST);
		frame.add(robotPanel, BorderLayout.WEST);
		frame.setVisible(true);
		frame.pack();	
			
	}

	public void actionPerformed(ActionEvent e) {
        e.getActionCommand();
    }
	
	private void setUpRobotPanel() {
		robotPanel = new JPanel();
		
		robotMethodGroup = new ButtonGroup();
		existingRobotButton = new JRadioButton("Choose Existing Robot");
		existingRobotButton.setActionCommand(chooseRobotPanel);
	    createRobotButton = new JRadioButton("Create New Robot");
	    createRobotButton.setActionCommand(makeRobotPanel);
	    robotMethodGroup.add(existingRobotButton);
	    robotMethodGroup.add(createRobotButton);
	    
	    

	    JRadioButton catButton = new JRadioButton(catString);
	    catButton.setMnemonic(KeyEvent.VK_C);
	    catButton.setActionCommand(catString);

	    JRadioButton dogButton = new JRadioButton(dogString);
	    dogButton.setMnemonic(KeyEvent.VK_D);
	    dogButton.setActionCommand(dogString);

	    JRadioButton rabbitButton = new JRadioButton(rabbitString);
	    rabbitButton.setMnemonic(KeyEvent.VK_R);
	    rabbitButton.setActionCommand(rabbitString);

	    JRadioButton pigButton = new JRadioButton(pigString);
	    pigButton.setMnemonic(KeyEvent.VK_P);
	    pigButton.setActionCommand(pigString);

	    //Group the radio buttons.
	    ButtonGroup group = new ButtonGroup();
	    group.add(birdButton);
	    group.add(catButton);
	    group.add(dogButton);
	    group.add(rabbitButton);
	    group.add(pigButton);

	    //Register a listener for the radio buttons.
	    birdButton.addActionListener(this);
	    catButton.addActionListener(this);
	    dogButton.addActionListener(this);
	    rabbitButton.addActionListener(this);
	    pigButton.addActionListener(this);
		
		private static JPanel prexistingRobotPanel;
		private static JPanel makeRobotPanel;
		private static JPanel choseAppearancePanel;
		private static JPanel choseMovementPanel;
		private static JPanel chooseAttackPanel;
		
		private static List<Robot> possibleRobots;
		private static List<Robot> chosenRobots;
		
		private static JPanel robotPanel;
		private static int width;
		private static int height;
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10, 10, 10, 10);
		c.gridx = 2;
		c.gridy = 0;
		
	}


	private void setUpArenaPanel() {
		robotMakerPanel = new JPanel();
		
		arenaLabel = new JLabel("Arena");
		arenaWidthLabel = new JLabel("width: ");
		arenaXLabel = new JLabel("x");
		arenaHeightLabel = new JLabel("height: ");
		arenaWidthChooser = new JTextArea();
		arenaHeightChooser = new JTextArea();
		
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10, 10, 10, 10);
		c.gridx = 2;
		c.gridy = 0;
		robotMakerPanel.add(arenaLabel,c);
		c.gridy = 1;
		c.gridx = 0;
		robotMakerPanel.add(arenaWidthLabel,c);
		c.gridx++;
		robotMakerPanel.add(arenaWidthChooser,c);
		c.gridx++;
		robotMakerPanel.add(arenaXLabel,c);
		c.gridx++;
		robotMakerPanel.add(arenaHeightLabel,c);
		c.gridx++;
		robotMakerPanel.add(arenaHeightChooser,c);
	}
		
	public void addRobot(Robot r) {
		robots.add(r);
		populateSelectionPanel();
	}
	
	/**
	 * Put all the robots on the statistics panel
	 */
	private void populateSelectionPanel() {
		System.out.println("populating statistics panel");
		robotSelectionPanel.removeAll();
		
		DefaultComboBoxModel listModel = new DefaultComboBoxModel();
		
		for (int i = 0; i < robots.size(); i++) {
			synchronized (robots.get(i)) {
				Robot r = robots.get(i);
				
				listModel.addElement(r.getName());
			}
		}
		
		robotList = new JList(listModel);
		robotList.addListSelectionListener(new RobotSelectionListener(this));
		robotSelectionPanel.add(robotList);
		robotSelectionPanel.repaint();
		robotSelectionPanel.revalidate();
	}
	
	public void showRobotStatistics() {
		robotMakerPanel.removeAll();
		int robotIndex = robotList.getSelectedIndex();
		
		String name;
		int width, height;
		float direction, health;
		Point motion;
		
		synchronized (robots.get(robotIndex)) {
			Robot r = robots.get(robotIndex);
			name = r.getName();
			width = r.getWidth();
			height = r.getHeight();
			direction = r.getDirectionFacing();
			health = r.getHealth();
			motion = r.getMotion();
		}
		
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10, 10, 10, 10);
		c.gridx = 0;
		c.gridy = 0;
		robotMakerPanel.add(new JLabel(name), c);
		c.gridy++;
		robotMakerPanel.add(new JLabel("Health : " + health), c);
		c.gridx++;
		robotMakerPanel.add(new JLabel("Dimensions : " + width + "x" + height), c);
		c.gridy++;
		c.gridx--;
		robotMakerPanel.add(new JLabel("Direction : " + direction), c);
		c.gridx++;
		robotMakerPanel.add(new JLabel("Motion : <" + motion.x + ", " + motion.y + ">"), c);
		
		robotMakerPanel.repaint();
		robotMakerPanel.revalidate();
	}

	private void updateGamePanel() {
		if (imageIcon == null) {
			imageIcon = new ImageIcon(arenaImage);
		}
		else {
			imageIcon.setImage(arenaImage);
		}

		if (imageLabel == null) {
			imageLabel = new JLabel(imageIcon);
			gamePanel.add(imageLabel);
		}
		else {
			imageLabel.setIcon(imageIcon);
		}
		
		gamePanel.revalidate();
		gamePanel.repaint();
	}
	
	public void setArenaImage(BufferedImage image) {
		arenaImage = image;
	}
	
	public void clearRobots() {
		robots = new ArrayList<Robot>();
	}
	
	public List<Robot> getRobots() {
		return robots;
	}
}
