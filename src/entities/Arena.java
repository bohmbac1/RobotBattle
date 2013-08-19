package entities;

import math.PolygonUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class Arena {
    private final int width;
    private final int height;
    private List<Robot> aliveRobots;
    private List<Robot> deadRobots;
    private ArenaTile[][] tiles; // first dimension represents x coordinate, second dimension represents y coordinate

    // true when aliveRobots are in action
    private boolean inMotion;

    private File imageFile;
    private String fileName;

    public Arena(int w, int h, String fileName) throws IOException {
        this.fileName = fileName;
        width = w;
        height = h;
        aliveRobots = new ArrayList<Robot>();
        deadRobots = new ArrayList<Robot>();
        //tiles = new ArenaTile[width][height];

        inMotion = false;
        setUpImageFile();
    }

    /**
     * Make the BufferedImage that is the arena
     *
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
        if (r.getHealth() > 0)
            aliveRobots.add(r);
        else
            deadRobots.add(r);
        //updateOccupiedTiles(r);
    }

    public synchronized void updateOccupiedTiles(Robot r) {
        Polygon bounds = r.getRobotPolygon();
        List<Point> occupiedPoints = PolygonUtils.getPointsInPolygon(bounds);
        for (Point point : occupiedPoints) {
            if (this.containsPoint(point)) {
            	if (tiles == null) System.out.println("tiles is null");
            	System.out.println("tiles.length = " + tiles.length);
            	System.out.println("tiles.height = " + tiles[489].length);
            	System.out.println("(" + point.x + "," + point.y + ")");
            	System.out.println("arenaTile = " + tiles[489][0]);
                tiles[point.x][point.y].addRobot(r);
            }
        }
    }

    public synchronized void removeRobot(Robot r) {
        try {
            if (r.getHealth() > 0) {
                aliveRobots.remove(r);
                deadRobots.add(r);
            }
        } catch (NullPointerException e) {
            System.out.println("Exception removing a robot that was null");
        }
    }

    public synchronized List<Robot> getAliveRobots() {
        return aliveRobots;
    }

    public synchronized void startSimulation() {
        inMotion = true;
        System.out.println("simulation started");
    }

    public synchronized void stopSimulation() {
        inMotion = false;
    }

    /**
     * Constantly update the alive aliveRobots and their positions.
     * For each robot, update it's position, then bounce it off the walls.
     * Next if it is colliding with aliveRobots, they must bounce off each other.
     * If the robot is attacking, it will finally damage all the other aliveRobots in its attack range.
     *
     * @throws InterruptedException
     */
    public synchronized void moveRobots() throws InterruptedException {
        if (inMotion) {
            for (int i = 0; i < aliveRobots.size(); i++) {
                synchronized (aliveRobots.get(i)) {
                    Robot r = aliveRobots.get(i);
                    if (r.getHealth() <= 0) {
                        this.removeRobot(r);
                        continue;
                    }
                    updateRobotPosition(r);
                    //collideOffRobots(r);
                    damageRobotsInAttackRange(r);
                }
            }
        } else {
            Thread.sleep(100);
        }
    }

    /**
     * Move the robot to its new spot in the arena. This includes bouncing it off walls.
     * @param r
     */
    public synchronized void updateRobotPosition(Robot r) {
        //takeRobotAndAttackOffTiles(r);

        r.updatePosition();
        bounceRobotOffWalls(r);

        //putRobotAndAttackOnTiles(r);
    }

    /**
     * Label the tiles that this robot is on to contain the robot.
     * Label the tiles that this robot is attacking to contain the attack.
     * @param r
     */
    private void putRobotAndAttackOnTiles(Robot r) {
        List<Point> currentlyOccupied = r.getPointsOccupied();
        for (Point point : currentlyOccupied) {
            if (this.containsPoint(point)) {
                tiles[point.x][point.y].addRobot(r);
            }
        }

        List<Point> attackPoints = r.attackPoints();
        for (Point point : attackPoints) {
            if (this.containsPoint(point)) {
                tiles[point.x][point.y].addAttack(r.getAttackPerforming());

            }
        }
    }

    /**
     * For the tiles that the robot is on, take the robot off those tiles.
     * For the tiles that the robot is attacking, take the attack off those tiles.
     * @param r
     */
    private void takeRobotAndAttackOffTiles(Robot r) {
        List<Point> occupying = r.getPointsOccupied();
        for (Point point : occupying) {
            if (this.containsPoint(point)) {
                tiles[point.x][point.y].removeRobot(r);
            }
        }

        List<Point> attackingPoints = r.attackPoints();
        for (Point point : attackingPoints) {
            if (this.containsPoint(point)) {
                tiles[point.x][point.y].removeAttack(r.getAttackPerforming());
            }
        }
    }

    /**
     * Find all the robots that are being attacked by this robot, and damage them accordingly.
     * @param robot
     */
    public synchronized void damageRobotsInAttackRange(Robot robot) {
        Attack attack = robot.getAttackPerforming();

        if (attack == null)
            return;

        List<Point> attackPoints = robot.attackPoints();

        for (Robot r : aliveRobots) {
            if (r == robot)
                continue;

            for (Point p : attackPoints) {
                if (r.containsPoint(p)) {
                    r.isAttackedBy(attack);
                    break;
                }
            }

            if (r.getHealth() <= 0) {
                r.stopMotion();
                aliveRobots.remove(r);
                deadRobots.add(r);
            }
        }
    }

    /**
     * Check every other robot. If this robot is colliding with them, then make sure they bounce off each other.
     *
     * @param robot
     */
    public void collideOffRobots(Robot robot) {
        for (Robot r : aliveRobots) {
            if (r == robot)
                continue;

            //if (robot.isCollidingWithRobot(r)) {
            //robot.bounceOffRobot(r);
            //}
        }
    }

    /**
     * If the robot can bounce off a wall, it will change direction and bounce off the wall.
     *
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
