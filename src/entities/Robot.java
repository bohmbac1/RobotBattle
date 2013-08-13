package entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.imageio.ImageIO;

import math.PolygonUtils;
import math.Tuple;

/**
 * 
 * @author Stanton Bohmbach
 *
 */
public class Robot {
	public static final long FIRST_TIME_UPDATED = Long.MIN_VALUE;

	private int width;
	private int height;
	private int color = Color.red.getRGB();

	private String name;
	private volatile int health;

	private int xPos;
	private int yPos;
	private float intermediateXPos;
	private float intermediateYPos;
	private Point motion;
	private float directionFacing;
	private long lastTimeUpdated;

	private Object positionLock = new Object(); // used to lock when reading and writing position, motion, and directionFacing

	private BufferedImage image;

	private SortedMap<Integer, Attack> knownAttacks;
	private List<Attack> knownAttackList;
	private Attack attackPerforming;

	private Object knownAttackLock = new Object(); // used to lock when reading and writing from the list of known attacks
	private Object performingAttackLock = new Object(); // used to lock when editing the attack that is being performed

	/**
	 * Make a new robot by providing all the information.
	 * 
	 * @param name
	 * @param health
	 * @param x
	 * @param y
	 * @param vec
	 */
	public Robot(String name, int health, int x, int y, Point vec, float direction, int width, int height) {
		setUpBasicImage(width, height);
		setUpRobot(name, health, x, y, vec, direction);
	}
	
	/**
	 * Make a robot whose image is provided by a file name.
	 * 
	 * @param name
	 * @param health
	 * @param x
	 * @param y
	 * @param vec
	 * @param direction
	 * @param imageFileName
	 */
	public Robot(String name, int health, int x, int y, Point vec, float direction, String imageFileName) {
		try {
			image = ImageIO.read(new File(imageFileName));
		} catch (IOException e) {
			System.out.println("Could not make robot " + name + " because " + imageFileName + " does not exist.");
		}
		
		setUpRobot(name, health, x, y, vec, direction);
	}

	/**
	 * Set up the robot. The image must already be initialized.
	 * @param name
	 * @param health
	 * @param x
	 * @param y
	 * @param vec
	 * @param direction
	 */
	private void setUpRobot(String name, int health, int x, int y, Point vec, float direction) {
		width = image.getWidth();
		height = image.getHeight();
		this.name = name;
		this.health = health;
		xPos = x;
		yPos = y;
		intermediateXPos = x;
		intermediateYPos = y;
		motion = vec;
		lastTimeUpdated = FIRST_TIME_UPDATED;
		directionFacing = direction;
		knownAttacks = new TreeMap<Integer, Attack>(new IntegerComparator());
		attackPerforming = null;
		knownAttackList = new ArrayList<Attack>();
	}

	/**
	 * Set up the image of the robot
	 */
	private void setUpBasicImage(int width, int height) {
		image = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR); 
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (j < height / 5) {
					image.setRGB(i, j, Color.white.getRGB());
				}
				else {
					image.setRGB(i, j, color);
				}
			}
		}
	}

	/**
	 * Lock the position
	 */
	public void updatePosition() {
		if (lastTimeUpdated == FIRST_TIME_UPDATED) {
			lastTimeUpdated = System.currentTimeMillis();
			return;
		}
		
		synchronized (positionLock) {
			synchronized (performingAttackLock) {
				long currTime = System.currentTimeMillis();
				long changeInTime = currTime - lastTimeUpdated;
				lastTimeUpdated = currTime;
				intermediateXPos += ((float)changeInTime / 1000) * motion.x;
				intermediateYPos += ((float)changeInTime / 1000) * motion.y;

				xPos = (int) Math.round(intermediateXPos);
				yPos = (int) Math.round(intermediateYPos);

				if (isAttacking()) {
					attackPerforming.timeElapsed += (double) changeInTime / 1000;
					if (attackPerforming.timeElapsed > attackPerforming.getTimeLasting()) {
						stopAttacking();
					}
				}
			}
		}
	}

	/**
	 * Find the corners of the robot, depending on what direction it is facing.
	 * @return
	 */
	public List<Point> corners() {
		List<Point> retv = new ArrayList<Point>();
		float hypoteneuse = (float) Math.sqrt(Math.pow((double)width / 2, 2) + Math.pow((double)height / 2, 2));

		synchronized (positionLock) {
			for (float angle : anglesOfCorners()) {
				float xdir = (float) (hypoteneuse * Math.cos(Math.toRadians(angle)));
				float ydir = (float) (hypoteneuse * Math.sin(Math.toRadians(angle)));
				retv.add(new Point (Math.round(xdir + xPos), Math.round(ydir + yPos)));
			}
		}

		return retv;
	}

	/**
	 * Find the point on the robot that is the farthest to the left. It will be a corner.
	 * @return
	 */
	public Point leftmostPoint() {
		Point retv = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);

		synchronized (positionLock) {
			for (Point point : corners()) {
				if (point.x < retv.x) {
					retv = point;
				}
			}
		}
		return retv;
	}

	/**
	 * Find the point on the robot that is the farthest to the right. It will be a corner.
	 * @return
	 */
	public Point rightmostPoint() {
		Point retv = new Point (Integer.MIN_VALUE, Integer.MIN_VALUE);

		synchronized (positionLock) {
			for (Point point : corners()) {
				if (point.x > retv.x) {
					retv = point;
				}
			}
		}

		return retv;
	}

	/**
	 * Find the point on the robot that is the farthest to the top. It will be a corner.
	 * @return
	 */
	public Point topmostPoint() {
		Point retv = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);

		synchronized (positionLock) {
			for (Point point : corners()) {
				if (point.y < retv.y) {
					retv = point;
				}
			}
		}

		return retv;
	}

	/**
	 * Find teh point on the robot that is the farthest to the bottom. It will be a corner.
	 * @return
	 */
	public Point bottommostPoint() {
		Point retv = new Point(Integer.MIN_VALUE, Integer.MIN_VALUE);

		synchronized (positionLock) {
			for (Point point : corners()) {
				if (point.y > retv.y) {
					retv = point;
				}
			}
		}

		return retv;
	}

	/**
	 * 
	 * @return an array of size 4 with the angles that the corners of the robot are with respect to the middle of the robot
	 */
	public float[] anglesOfCorners() {
		float[] retv = new float[4];

		float theta = (float) Math.toDegrees(Math.atan((double) width / height));
		retv[0] = (directionFacing + theta) % 360;
		retv[1] = (directionFacing + 180 - theta) % 360;
		retv[2] = (directionFacing + 180 + theta) % 360;
		retv[3] = (directionFacing - theta) % 360;

		return retv;
	}

	/**
	 * Perform a random attack.
	 * Set a thread to stop the attack when it should be over.
	 */
	public void performRandomAttack() {
		synchronized (knownAttackLock) {
			if (knownAttacks.size() > 0) {
				int attackNum = (int) (Math.random() * knownAttacks.size());

				synchronized (performingAttackLock) {
					attackPerforming = knownAttackList.get(attackNum);
					attackPerforming.timeElapsed = 0;
				}
			}
		}
	}

	/**
	 * Set the attack that this robot is performing
	 * @param attack
	 */
	public void performAttack(Attack attack) {
		synchronized (performingAttackLock) {
			attackPerforming = attack;
			attackPerforming.timeElapsed = 0;
		}
	}

	public void stopAttacking() {
		synchronized (performingAttackLock) {
			attackPerforming.timeElapsed = 0;
			attackPerforming = null;
		}
	}

	/**
	 * Return the image of the robot, rotated for the direction that it is facing.
	 * @return
	 */
	public BufferedImage rotatedImage() {
		int w = getImage().getWidth();  
		int h = getImage().getHeight(); 
		int hypoteneuse = (int) Math.ceil(Math.sqrt(Math.pow(w, 2) + Math.pow(h, 2)));
		BufferedImage dimg = new BufferedImage(hypoteneuse, hypoteneuse, getImage().getType());  
		Graphics2D g = dimg.createGraphics();  

		synchronized (positionLock) {
			g.rotate(Math.toRadians(directionFacing - 270), hypoteneuse/2, hypoteneuse/2);  
		}

		g.drawImage(getImage(), null, (hypoteneuse - w) / 2, (hypoteneuse - h) / 2);  
		return dimg; 
	}

	/**
	 * This method is not synchronized. Shouldn't create an error, but is possible
	 * @return whether or not the robot is performing an attack
	 */
	public boolean isAttacking() {
		return attackPerforming != null;
	}

	public void addAttack(int damage, Attack attack) {
		synchronized (knownAttackLock) {
			knownAttacks.put(damage, attack);
			knownAttackList.add(attack);
		}
	}

	public boolean isPastLeftWall(Arena arena) {
		synchronized (positionLock) {
			return leftmostPoint().x < 0;
		}
	}

	public boolean isPastRightWall(Arena arena) {
		synchronized (positionLock) {
			return rightmostPoint().x > arena.getWidth();
		}
	}

	public boolean isPastTopWall(Arena arena) {
		synchronized (positionLock) {
			return topmostPoint().y < 0;
		}
	}

	public boolean isPastBottomWall(Arena arena) {
		synchronized (positionLock) {
			return bottommostPoint().y > arena.getHeight();
		}
	}

	public void bounceOffLeftWall(Arena arena) {
		synchronized (positionLock) {
			int distPast = Math.round(Math.abs(leftmostPoint().x));
			setXPos(xPos + distPast * 2);
			motion.x *= -1;
		}
	}

	public void bounceOffRightWall(Arena arena) {
		synchronized (positionLock) {
			int distPast = Math.round(Math.abs(rightmostPoint().x - arena.getWidth()));
			setXPos(xPos - distPast * 2);
			motion.x *= -1;
		}
	}

	public void bounceOffTopWall(Arena arena) {
		synchronized (positionLock) {
			int distPast = Math.round(Math.abs(topmostPoint().y));
			setYPos(yPos + distPast * 2);
			motion.y *= -1;
		}
	}

	public void bounceOffBottomWall(Arena arena) {
		synchronized (positionLock) {
			int distPast = Math.round(Math.abs(bottommostPoint().y - arena.getHeight()));
			setYPos(yPos - distPast * 2);
			motion.y *= -1;
		}
	}
	
	/**
	 * Face the center of the arena
	 * @param arena
	 * @return
	 */
	public Robot faceCenter(Arena arena) {
		float theta = arenaCenterDirection(arena);
		directionFacing = theta;
		return this;
	}

	/**
	 * Find the direction of the center of the arena
	 * @param arena
	 * @return
	 */
	public float arenaCenterDirection(Arena arena) {
		int yDiff = (arena.getHeight() - 1) / 2 - yPos;
		int xDiff = (arena.getWidth() - 1) / 2 - xPos;
		
		float theta = (float) Math.toDegrees(Math.atan((double)yDiff / xDiff));
		if (xPos >= arena.getWidth() / 2) {
			theta += 180;
		}
		return theta % 360;
	}

	public String getName() {
		return name;
	}

	public int getxPos() {
		synchronized (positionLock) {
			return xPos;
		}
	}

	public int getyPos() {
		synchronized (positionLock) {
			return yPos;
		}
	}

	public Point getPosition() {
		synchronized (positionLock) {
			return new Point(xPos, yPos);
		}
	}

	public void setColor(Color color) {
		this.color = color.getRGB();
		setUpBasicImage(this.width, this.height);
	}

	public void setColorByInt(int rgb) {
		this.color = rgb;
		setUpBasicImage(this.width, this.height);
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public BufferedImage getImage() {
		return image;
	}

	public float getDirectionFacing() {
		synchronized (positionLock) {
			return directionFacing;
		}
	}
	
	/**
	 * Set the direction the robot is facing
	 * @param direction
	 * @return this
	 */
	public Robot setDirectionFacing(float direction) {
		synchronized (positionLock) {
			directionFacing = direction;
		}
		return this;
	}
	
	/**
	 * Rotate the robot clockwise
	 * @param degrees How many degrees to rotate
	 * @return this
	 */
	public Robot rotateClockwise(float degrees) {
		synchronized (positionLock) {
			directionFacing = (directionFacing + degrees) % 360;
		}
		return this;
	}
	
	/**
	 * Rotate the robot counter-clockwise
	 * @param degrees How many degrees to rotate
	 * @return this
	 */
	public Robot rotateCounterClockwise(float degrees) {
		synchronized (positionLock) {
			directionFacing = (directionFacing - degrees) % 360;
		}
		return this;
	}

	public Attack getAttackPerforming() {
		synchronized (performingAttackLock) {
			return attackPerforming;
		}
	}

	public void setXPos(int x) {
		synchronized (positionLock) {
			xPos = x;
			intermediateXPos = xPos;
		}
	}

	public void setYPos(int y) {
		synchronized (positionLock) {
			yPos = y;
			intermediateYPos = yPos;
		}
	}

	/**
	 * Return the point at the front middle of the robot.
	 * @return
	 */
	public Tuple<Float> frontMiddle() {
		synchronized (positionLock) {
			float yDiff = (float) ((double)height / 2 * Math.sin(Math.toRadians(directionFacing)));
			float xDiff = (float) ((double)height / 2 * Math.cos(Math.toRadians(directionFacing)));

			return new Tuple<Float>(xDiff + xPos, yDiff + yPos);
		}
	}

	/**
	 * Return the front left corner of the robot.
	 * @return
	 */
	public Tuple<Float> frontLeftCorner() {
		float hypoteneuse = (float) Math.sqrt(Math.pow(width / 2, 2) + Math.pow(height / 2, 2));
		float angleDiff = (float) Math.toDegrees(Math.atan((double) width / height));

		synchronized (positionLock) {
			float xDiff = hypoteneuse * (float) Math.cos(Math.toRadians(directionFacing - angleDiff));
			float yDiff = hypoteneuse * (float) Math.sin(Math.toRadians(directionFacing - angleDiff));
			return new Tuple<Float>(xDiff + xPos, yDiff + yPos);
		}
	}

	/**
	 * Return the front right corner of the robot.
	 * @return
	 */
	public Tuple<Float> frontRightCorner() {
		float hypoteneuse = (float) Math.sqrt(Math.pow(width / 2, 2) + Math.pow(height / 2, 2));
		float angleDiff = (float) Math.toDegrees(Math.atan((double) width / (double) height));

		synchronized (positionLock) {
			float xDiff = (float) (hypoteneuse * Math.cos(Math.toRadians(directionFacing + angleDiff)));
			float yDiff = (float) (hypoteneuse * Math.sin(Math.toRadians(directionFacing + angleDiff)));
			return new Tuple<Float>(xDiff + xPos, yDiff + yPos);
		}
	}

	public Tuple<Float> bottomLeftCorner() {
		float hypoteneuse = (float) Math.sqrt(Math.pow(width / 2, 2) + Math.pow(height / 2, 2));

		synchronized (positionLock) {
			float angle = directionFacing - 90 - 45;
			float xDiff = (float) (hypoteneuse * Math.cos(Math.toRadians(angle)));
			float yDiff = (float) (hypoteneuse * Math.sin(Math.toRadians(angle)));

			return new Tuple<Float>(xDiff + xPos, yDiff + yPos);
		}
	}

	public Tuple<Float> bottomRightCorner() {
		float hypoteneuse = (float) Math.sqrt(Math.pow(width / 2, 2) + Math.pow(height / 2, 2));

		synchronized (positionLock) {
			float angle = directionFacing + 90 + 45;
			float xDiff = (float) (hypoteneuse * Math.cos(Math.toRadians(angle)));
			float yDiff = (float) (hypoteneuse * Math.sin(Math.toRadians(angle)));

			return new Tuple<Float>(xDiff + xPos, yDiff + yPos);
		}
	}

	/**
	 * Return true if the robot is facing to the right. Return false if the robot is facing directly up or
	 * directly down.
	 * @return
	 */
	public boolean isFacingRight() {
		synchronized (positionLock) {
			float direction = directionFacing % 360;
			return !(90 <= direction && direction <= 270);
		}
	}

	/**
	 * Get a list of points that the robot is attacking.
	 * @return
	 */
	public List<Point> attackPoints() {
		List<Point> retv = new ArrayList<Point>();

		synchronized (performingAttackLock) {
			if (attackPerforming == null) {
				return retv;
			}

            return PolygonUtils.getPointsInPolygon(attackPerforming.determineAttackPosition(this));
		}
	}

	/**
	 * Determine if the robot occupies this point.
	 * @param p
	 * @return
	 */
	public boolean containsPoint(Point p) {
		synchronized (positionLock) {
			Polygon robotPoly = this.getRobotPolygon();
			return robotPoly.contains(p);
		}
	}

	/**
	 * Return a polygon that is this robot.
	 * @return
	 */
	public Polygon getRobotPolygon() {
		int[] xPoints = new int[4], yPoints = new int[4];

		synchronized (positionLock) {
			List<Point> corners = corners();

			for (int i = 0; i < 4; i++) {
				Point corner = corners.get(i);
				xPoints[i] = corner.x;
				yPoints[i] = corner.y;
			}

			return new Polygon(xPoints, yPoints, 4);
		}
	}

    /**
     * Get a list of the points that the robot is occupying.
     * @return
     */
    public List<Point> getPointsOccupied() {
        return PolygonUtils.getPointsInPolygon(getRobotPolygon());
    }

	public Color getAttackColor() {

		synchronized (performingAttackLock) {
			if (attackPerforming == null) return null;
			return attackPerforming.getColor();
		}
	}

	/**
	 * If the attack has already hit the robot, it will not hit again. If it has not hit yet,
	 * The damage from the attack will be subtracted from this robot's health. It could cause the robot to die.
	 * @param attack
	 */
	public void isAttackedBy(Attack attack) {
		if (attack.hasAlreadyHitRobot(this)) {
			return;
		}

		health -= attack.getDamage();

		attack.hitRobot(this);
	}

	public float getHealth() {
		return health;
	}

	public Point getMotion() {
		return motion;
	}

    /**
     * set the motion to 0. Set lastTimeUpdated = FIRST_TIME_UPDATED
     * so that next time it starts moving, it won't make a huge jump.
     */
    public void stopMotion() {
        synchronized (positionLock) {
            motion = new Point(0, 0);
            lastTimeUpdated = FIRST_TIME_UPDATED;
        }
    }
	
	public Robot setMotion(Point p) {
		synchronized(positionLock) {
			motion = p;
		}
		return this;
	}
}
