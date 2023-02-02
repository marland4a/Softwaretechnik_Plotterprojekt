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
		int damage_delta = this.damage_max - this.damage_min;
		int damage;
		if(damage_delta > 0) {
			damage = this.damage_min + rand.nextInt(damage_delta);
		}
		else if(damage_delta == 0) {
			damage = this.damage_max;
		}
		else {
			damage = this.damage_min - rand.nextInt(-1 * damage_delta);
		}
		return damage;
	}
}
