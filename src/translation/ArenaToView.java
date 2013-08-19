package translation;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import gui.View;
import math.ImageLibrary;

import entities.Arena;
import entities.Robot;
import gui.View;

/**
 * Translate the information in the arena into the view.
 * Start this after all the robots have been added to the arena.
 * 
 * @author stantonbohmbach
 *
 */
public class ArenaToView {
	private Arena arena;
	private View view;

	public ArenaToView(Arena a, View v) {
		arena = a;
		view = v;
		updateViewRobots();
	}
	
	private void updateViewRobots() {
		view.clearRobots();
		
		synchronized(arena.getAliveRobots()) {
			for (Robot r : arena.getAliveRobots()) {
				view.addRobot(r);
			}
		}
	}

	public BufferedImage displayAttack(Robot r, BufferedImage image) {
		Color attackColor = r.getAttackColor();
		List<Point> attackPoints = r.attackPoints();
		for (Point point : attackPoints) {
			if (arena.containsPoint(point) && !r.containsPoint(point)) {
				image.setRGB(point.x, point.y, attackColor.getRGB());
			}
		}
		return image;
	}

	public Arena getArena() {
		return arena;
	}

	public View getView() {
		return view;
	}

	public void updateView() throws IOException {
		if (arena.getAliveRobots().size() != view.getRobots().size()) {
			updateViewRobots();
		}
		BufferedImage image = ImageIO.read(arena.getImageFile());
		for (Robot r : arena.getAliveRobots()) {
				BufferedImage rotatedRobot = ImageLibrary.rotateImage(r.getImage(), r.getDirectionFacing() - 270);
				image = ImageLibrary.overlayImages(image, rotatedRobot,
						(int) (r.getxPos() - rotatedRobot.getWidth() / 2), 
						(int) (r.getyPos() - rotatedRobot.getHeight() / 2));
				if (r.isAttacking()) {
					image = displayAttack(r, image);
				}
		}

		view.setArenaImage(image);
		System.gc();
	}
}
