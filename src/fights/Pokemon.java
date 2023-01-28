package fights;

import java.util.Random;

public class Pokemon {

	/* Statische Attribute / Funktionen */
	private static Attack[] attacks = { new Attack("Donnerschock", 30, 40), new Attack("Heuler", 10, 20),
			new Attack("Donnerwelle", 10, 25), new Attack("Ruckzuckhieb", 10, 30) };

	private static Pokemon[] pokemons = { new Pokemon("Pikachu", "pikachu.gcode"),
			new Pokemon("Schiggi", "schiggi.gcode"), new Pokemon("test2", "test2.gcode") };

	public static Attack getAttackByName(String name) {
		for (Attack a : attacks) {
			if (a.getName() == name) {
				return a;
			}
		}
		return null;
	}

	public static Pokemon getPokemonByName(String name) {
		for (Pokemon p : pokemons) {
			if (p.getName() == name) {
				return p;
			}
		}
		return null;
	}

	public static Attack getAttack(int nr) {
		return attacks[nr];
	}

	private static Pokemon getPokemon(int nr) {
		return pokemons[nr];
	}

	public static Pokemon getRandom() {
		Random random = new Random();
		int rand = random.nextInt(pokemons.length); // Zufälliges Pokemon wählen
		return Pokemon.getPokemon(rand);
	}

	/* Objektinhalte */
	private String name;
	private String gcode_path;
	private int life;

	public Pokemon(String name, String gcode_path) {
		this.name = name;
		this.gcode_path = gcode_path;
		this.life = 100;
	}

	public String getName() {
		return this.name;
	}

	public String getGcodePath() {
		return this.gcode_path;
	}

	public int getLife() {
		return this.life;
	}

	public int reduceLife(int value) {
		life -= value;
		if (life < 0) {
			life = 0;
		}
		return life;
	}
}
