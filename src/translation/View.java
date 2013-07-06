package translation;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import entities.Robot;


public class View {	
	private static JFrame frame;
	private static JPanel gamePanel;
	private static JLabel imageLabel;
	private ImageIcon imageIcon;
	
	private BufferedImage arenaImage;
	
	private static JPanel robotSelectionPanel;
	private static JPanel statisticsPanel;
	private List<Robot> robots;
	private JList robotList;
	
	public static final int arenaColor = Color.black.getRGB();
	
	public View() {
		frame = new JFrame("Robot Battle");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		robots = new ArrayList<Robot>();
		
		setUpGamePanel();
		setUpSelectionPanel();
		
		statisticsPanel = new JPanel(new GridBagLayout());
		
		frame.add(gamePanel, BorderLayout.WEST);
		frame.add(robotSelectionPanel);
		frame.add(statisticsPanel, BorderLayout.SOUTH);
		frame.setVisible(true);
		frame.pack();		
	}

	private void setUpGamePanel() {
		gamePanel = new JPanel();
	}
	
	public void setUpSelectionPanel() {
		robotSelectionPanel = new JPanel(new GridBagLayout());
		populateSelectionPanel();
	}
	
	public void display() {
		updateGamePanel();
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
		statisticsPanel.removeAll();
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
		statisticsPanel.add(new JLabel(name), c);
		c.gridy++;
		statisticsPanel.add(new JLabel("Health : " + health), c);
		c.gridx++;
		statisticsPanel.add(new JLabel("Dimensions : " + width + "x" + height), c);
		c.gridy++;
		c.gridx--;
		statisticsPanel.add(new JLabel("Direction : " + direction), c);
		c.gridx++;
		statisticsPanel.add(new JLabel("Motion : <" + motion.x + ", " + motion.y + ">"), c);
		
		statisticsPanel.repaint();
		statisticsPanel.revalidate();
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