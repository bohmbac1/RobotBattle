package entities;

import java.awt.Color;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;

import attacks.AttackPosition;

public class Attack {
	private String name;
	private Color color;
	private AttackPosition determineAttackPosition;
	private float damage;
	private float timeLasting;
	public volatile float timeElapsed;
	
	private volatile List<Robot> robotsHit;
	
	public Attack(String name, Color color, AttackPosition ap, float time, float damage) {
		this.name = name;
		this.color = color;
		determineAttackPosition = ap;
		this.timeLasting = time;
		this.damage = damage;
		timeElapsed = 0;
		robotsHit = new ArrayList<Robot>();
	}
	
	public Attack(Attack attack) {
		name = attack.getName();
		color = attack.getColor();
		determineAttackPosition = attack.getDetermineAttackPosition();
		timeLasting = attack.getTimeLasting();
		damage = attack.getDamage();
		timeElapsed = 0;
		robotsHit = new ArrayList<Robot>();
	}
	
	public List<Robot> getRobotsHit() {
		return robotsHit;
	}
	
	public boolean hasAlreadyHitRobot(Robot r) {
		return robotsHit.contains(r);
	}
	
	public void hitRobot(Robot r) {
		if (hasAlreadyHitRobot(r))
			return;
		robotsHit.add(r);
	}
	
	public AttackPosition getDetermineAttackPosition() {
		return determineAttackPosition;
	}
	
	public Polygon determineAttackPosition(Robot robot) {
		return determineAttackPosition.determinePoints(robot, timeElapsed);
	}
	
	public Color getColor() {
		return color;
	}
	
	public float getTimeLasting() {
		return timeLasting;
	}
	
	public String getName() {
		return name;
	}
	
	public float getDamage() {
		return damage;
	}
	
	public float timeRemaining() {
		return timeLasting - timeElapsed;
	}
}
