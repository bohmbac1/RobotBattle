package gui;

import entities.Robot;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import listeners.MakeChooseImageFrameListener;
import listeners.MakeCreateImageFrameListener;

/**
 * Created with IntelliJ IDEA.
 * User: stantonbohmbach
 * Date: 8/12/13
 * Time: 9:57 AM
 * To change this template use File | Settings | File Templates.
 */
public class CreateRobotFrame extends LayeredFrame {
    private JPanel chooseAppearancePanel;
    private ChooseImageFrame chooseImageFrame;
    private String fileName;
    private JButton chooseImageButton;
    private MakeImageFrame makeImageFrame;
    private JButton makeImageButton;
    private JLabel robotImageLabel;
    private boolean isRobotImageByFile;
    private BufferedImage robotImage;
    private int robotHeight;
    private int robotWidth;
    private Color robotColor;
    
    private JPanel chooseMovementPanel;
    private JPanel chooseAttackPanel;
    private JPanel okPanel;

    public CreateRobotFrame(LayeredFrame parent) {
    	super(parent);
    	
        frame = new JFrame("Robot Battle Creator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setUpChooseAppearancePanel();
        setUpChooseMovementPanel();
        setUpChooseAttackPanel();
        setUpOkPanel();

        frame.add(chooseAppearancePanel, BorderLayout.NORTH);
        frame.add(chooseMovementPanel);
        frame.add(chooseAttackPanel);
        frame.add(okPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
        frame.pack();
    }

    private void setUpOkPanel() {
		okPanel = new JPanel();
	}

	private void setUpChooseAppearancePanel() {
    	chooseAppearancePanel = new JPanel(new GridBagLayout());
    	GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);
        c.gridx = 0;
        c.gridy = 0;
        chooseImageButton = new JButton("Choose Existing Image");
        chooseImageButton.addActionListener(new MakeChooseImageFrameListener(this));
        chooseAppearancePanel.add(chooseImageButton, c);
        
        makeImageButton = new JButton("Make Robot by dimensions and color.");
        makeImageButton.addActionListener(new MakeCreateImageFrameListener(this));
        c.gridy++;
        chooseAppearancePanel.add(makeImageButton, c);
        
    	robotImageLabel = new JLabel();
    	c.gridy++;
    	chooseAppearancePanel.add(robotImageLabel, c);
    }

    private void setUpChooseMovementPanel() {
    	chooseMovementPanel = new JPanel(new GridBagLayout());
    }

    private void setUpChooseAttackPanel() {
    	chooseAttackPanel = new JPanel(new GridBagLayout());
    }
    
    public void setRobotHeight(int height) {
    	robotHeight = height;
    }
    
    public void setRobotWidth(int width) {
    	robotWidth = width;
    }
    
    public void setRobotColor(Color color) {
    	robotColor = color;
    }
    
    public void isRobotImageByFile(boolean is) {
    	isRobotImageByFile = is;
    }
    
    public void resetRobotImage() {
    	if (isRobotImageByFile) {
    		try {
				robotImage = ImageIO.read(new File(fileName));
			} catch (IOException e) {
				alert("the file " + fileName + " is not a valid image");
				return;
			}
    	}
    	else {
    		robotImage = Robot.makeImage(robotWidth, robotHeight, robotColor);
    	}
    	
    	ImageIcon icon = new ImageIcon(robotImage);
    	robotImageLabel.setIcon(icon);
    	chooseAppearancePanel.revalidate();
    	chooseAppearancePanel.repaint();
    	frame.pack();
    }
    
    public void setRobotImageFileName(String name) {
    	fileName = name;
    	resetRobotImage();
    }
}
