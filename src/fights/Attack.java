package fights;

import java.util.Random;

public class Attack {
	private String name;
	private int damage_min;
	private int damage_max;

	public Attack(String name, int damage_min, int damage_max) {
		this.name = name;
		this.damage_min = damage_min;
		this.damage_max = damage_max;
	}

	public String getName() {
		return name;
	}

	/* Get damage in range damage_min - damage_max */
	public int getDamage() {
		Random rand = new Random();
		return this.damage_min + rand.nextInt(this.damage_max - this.damage_min);
	}
}
