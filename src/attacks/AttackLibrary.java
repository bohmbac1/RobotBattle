package attacks;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entities.Attack;

public class AttackLibrary {
	public static List<Attack> attacks;
	public static Map<String, Attack> attacksByName;
	
	public AttackLibrary() {
		attacks = new ArrayList<Attack>();
		attacksByName = new HashMap<String, Attack>();
		Attack chainsaw = new Attack("chainsaw", Color.GRAY, new ChainsawAttackPosition(), (float)2.5, (float)70);
		attacks.add(chainsaw);
		Attack fire = new Attack("blowtorch", Color.ORANGE, new BlowtorchAttackPosition(), (float)4, (float)35);
		attacks.add(fire);
		Attack electricField = new Attack("electric field", Color.CYAN, new ElectricFieldAttackPosition(), (float) 2, (float) 100);
		attacks.add(electricField);
		
		for (Attack a : attacks) {
			attacksByName.put(a.getName(), a);
		}
	}
	
	/**
	 * Return a copy of a random attack.
	 * @return
	 */
	public Attack randomAttack() {
		int attackIndex = (int) (Math.random() * attacks.size());
		System.out.println("attack index = " + attackIndex + ", name = " + attacks.get(attackIndex).getName());
		return new Attack(attacks.get(attackIndex));
	}

	/**
	 * Return a copy of the attack that is marked by this name.
	 * @param attackName
	 * @return
	 */
	public Attack attackByName(String attackName) {
		return new Attack(attacksByName.get(attackName));
	}
}
