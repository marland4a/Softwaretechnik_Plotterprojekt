package fights;
import java.util.Random;


public class Pokemon {
	
	public static int optionToDamage(int option) {
		// return new Random().nextInt(5); // Test: 0 bis 4 Damage
		Random Rand = new Random();
		switch(option) {
		case 0: 							//Attacke Donnerschock
			return Rand.nextInt(10)+30;
			
		case 1:  							//Attacke Heuler
			return Rand.nextInt(10)+10;
			
		case 2:  							//Attacke Donnerwelle
			return Rand.nextInt(10)+15;
			
		case 3:  							//Attacke Ruckzuckhieb
			return Rand.nextInt(10)+20;
			
		default:
			return 0;
		}
	}
	
	public static Pokemon getPokemon(String name) {
		Pokemon pokemon = null;
		if(name == "Pikachu") {
			pokemon = new Pokemon(name, "pikatchu.gcode");
		}
		else if(name == "Schiggi") {
			pokemon = new Pokemon(name, "schiggi.gcode");
		}
		else if(name == "test2") {
			pokemon = new Pokemon(name, "test2.gcode");
		}
		return pokemon;
	}
	
	private static Pokemon getPokemon(int nr) {
		Pokemon pokemon = null;
		if(nr == 0) {
			pokemon = new Pokemon("Pikachu", "pikatchu.gcode");
		}
		else if(nr == 1) {
			pokemon = new Pokemon("Schiggi", "schiggi.gcode");
		}
		else {
			pokemon = new Pokemon("test2", "test2.gcode");
		}
		return pokemon;
	}
	
	public static Pokemon getRandom() {
		Random random = new Random();
		int rand = random.nextInt(3); // 0 bis 2
		return Pokemon.getPokemon(rand);
	}
	
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
		if(life < 0) {
			life = 0;
		}
		return life;
	}
}
