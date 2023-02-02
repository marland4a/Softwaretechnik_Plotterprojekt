package fights;

import java.util.Random;

public class Pokemon {

	/* Statische Attribute / Funktionen */
	private static Attack[] attacks = { new Attack("Donnerschock", 30, 40), new Attack("Heuler", 10, 20),
			new Attack("Donnerwelle", 10, 25), new Attack("Ruckzuckhieb", 10, 30) };

	private static Pokemon[] pokemons = { new Pokemon("Pikachu", "pikachu.gcode", "pikachu_back.gcode"),
			new Pokemon("Schiggi", "schiggi.gcode", "schiggi_back.gcode"),
			new Pokemon("test2", "test2.gcode", "test2.gcode") };

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

	public static Pokemon getPokemon(int nr) {
		return pokemons[nr];
	}

	public static int getPokemonNumber() {
		return pokemons.length;
	}
	
	public static Pokemon getRandom() {
		Random random = new Random();
		int rand = random.nextInt(pokemons.length); // Zufälliges Pokemon wählen
		return Pokemon.getPokemon(rand);
	}

	/* Objektinhalte */
	private String name;
	private String gcode_path_front; // Dateinamen der gcode Dateien
	private String gcode_path_back;
	private int life;
	private int last_damage;	// Betrag der letzten Reduktion

	public Pokemon(String name, String gcode_path_front, String gcode_path_back) {
		this.name = name;
		this.gcode_path_front = gcode_path_front;
		this.gcode_path_back = gcode_path_back;
		this.life = 100;
		this.last_damage = 0;
	}

	public String getName() {
		return this.name;
	}

	/* Gcode der Vorderseite (Gegner) */
	public String getGcodePath_front() {
		return this.gcode_path_front;
	}

	/* Gcode der Rückseite (Spieler) */
	public String getGcodePath_back() {
		return this.gcode_path_back;
	}

	public int getLife() {
		return this.life;
	}

	public int reduceLife(int value) {
		this.last_damage = value;
		life -= value;
		if (life < 0) {
			life = 0;
		}
		return life;
	}
	
	public int getLastDamage() {
		return this.last_damage;
	}
}
