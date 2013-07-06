package entities;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class Arena {
	private final int width;
	private final int height;
	private List<Robot> robots;

	// true when robots are in action
	private boolean inMotion;

	private File imageFile;
	private String fileName;

	public Arena(int w, int h, String fileName) throws IOException {
		this.fileName = fileName;
		width = w;
		height = h;
		robots = new ArrayList<Robot>();

		inMotion = false;
		setUpImageFile();
	}

	/**
	 * Make the BufferedImage that is the arena
	 * @throws IOException 
	 */
	private synchronized void setUpImageFile() throws IOException {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				image.setRGB(i, j, Color.black.getRGB());
			}
		}
		imageFile = new File(fileName);
		ImageIO.write(image, "jpg", imageFile);
	}

	public boolean containsPoint(Point point) {
		return point.x >= 0 && point.x < width && 
				point.y >= 0 && point.y < height;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public synchronized void addRobot(Robot r) {
		robots.add(r);
	}

	public synchronized void removeRobot(Robot r) {
		robots.remove(r);
	}

	public synchronized List<Robot> getRobots() {
		return robots;
	}

	public synchronized void startSimulation() {
		inMotion = true;
		System.out.println("simulation started");
	}

	public synchronized void stopSimulation() {
		inMotion = false;
	}

	/**
	 * Constantly update the robots and their positions.
	 * For each robot, update it's position, then bounce it off the walls.
	 * Next if it is colliding with robots, they must bounce off each other.
	 * If the robot is attacking, it will finally damage all the other robots in its attack range.
	 * 
	 * @throws InterruptedException
	 */
	public synchronized void moveRobots() throws InterruptedException {
		if (inMotion) {
			for (int i = 0; i < robots.size(); i++) {
				synchronized (robots.get(i)) {
					Robot r = robots.get(i);
					if (r.getHealth() <= 0) {
						this.removeRobot(r);
						continue;
					}
					r.updatePosition();
					bounceRobotOffWalls(r);
					//collideOffRobots(r);
					damageRobotsInAttackRange(r);
				}
			}
		}
		else {
			Thread.sleep(100);
		}
	}

	public synchronized void damageRobotsInAttackRange(Robot robot) {
		Attack attack = robot.getAttackPerforming();

		if (attack == null)
			return;

		List<Point> attackPoints = robot.attackPoints();

		for (Robot r : robots) {
			if (r == robot)
				continue;

			for (Point p : attackPoints) {
				if (r.containsPoint(p)) {
					r.isAttackedBy(attack);
					break;
				}
			}
		}
	}

	/**
	 * Check every other robot. If this robot is colliding with them, then make sure they bounce off each other.
	 * @param robot
	 */
	public void collideOffRobots(Robot robot) {
		for (Robot r : robots) {
			if (r == robot)
				continue;

			//if (robot.isCollidingWithRobot(r)) {
			//robot.bounceOffRobot(r);
			//}
		}
	}

	/**
	 * If the robot can bounce off a wall, it will change direction and bounce off the wall.
	 * @param r
	 */
	public synchronized void bounceRobotOffWalls(Robot r) {
		if (r.isPastRightWall(this)) {
			r.bounceOffRightWall(this);
		}
		if (r.isPastLeftWall(this)) {
			r.bounceOffLeftWall(this);
		}
		if (r.isPastTopWall(this)) {
			r.bounceOffTopWall(this);
		}
		if (r.isPastBottomWall(this)) {
			r.bounceOffBottomWall(this);
		}
	}

	public String getFileName() {
		return fileName;
	}

	public File getImageFile() {
		return imageFile;
	}
}
