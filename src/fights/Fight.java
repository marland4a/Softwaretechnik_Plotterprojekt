package fights;
import lejos.hardware.lcd.*;
import lejos.hardware.Button;
import java.util.Random;
import lejos.hardware.lcd.GraphicsLCD;


public class Fight {
	/* Settings */
	private Pokemon myPokemon;
	private Pokemon yourPokemon;
	private plott3r_V1_solved.Roboter roboter;
	
	public Fight(plott3r_V1_solved.Roboter roboter) {
		this.roboter = roboter;
	}
	
	/* Start fight */
	public void start() throws InterruptedException {
		// Display start screen
		this.drawStartScreen();
		// Select pokemons
		this.selectPokemons();
		// Draw fight scene
		this.drawFightScene();
		// Fight
		int my_option = 0;
		int your_option = 0;
		while(this.myPokemon.getLife() > 0 && this.yourPokemon.getLife() > 0) {
			my_option = this.drawAttackScreen();
			your_option = new Random().nextInt(4);
			this.myPokemon.reduceLife(Pokemon.optionToDamage(your_option));
			this.yourPokemon.reduceLife(Pokemon.optionToDamage(my_option));
			LCD.clear();
			this.drawBorder();
			LCD.drawString(this.myPokemon.getName(), 3, 2);
			LCD.drawString(": ", this.myPokemon.getName().length() + 3+1, 2);
			LCD.drawInt(this.myPokemon.getLife(), 3, 3);
			LCD.drawString(this.yourPokemon.getName(), 3, 4);
			LCD.drawString(": ", this.yourPokemon.getName().length() + 3+1, 4);
			LCD.drawInt(this.yourPokemon.getLife(), 3, 5);
			Button.waitForAnyPress();
		}
		// Ende
		LCD.clear();
		LCD.drawString("Fight", 10, 1);
		LCD.drawString("Item", 10, 3);
		this.drawBorder();
		if(this.myPokemon.getLife() > this.yourPokemon.getLife()) {
			LCD.drawString(this.myPokemon.getName(), 3, 2);
			LCD.drawString("hat gewonnen!", 3, 3);
		}
		else if(this.yourPokemon.getLife() > this.myPokemon.getLife()) {
			LCD.drawString(this.yourPokemon.getName(), 3, 2);
			LCD.drawString("hat gewonnen!", 3, 3);
		}
		else {
			LCD.drawString("Unentschieden!", 2, 3);
		}
	}
	
	private void drawBorder() {
		
	}
	
	private void drawStartScreen() {
		LCD.clear();
		this.drawBorder();
	}
	
	private void selectPokemons() {
		LCD.clear();
		this.drawBorder();
		
		this.myPokemon = Pokemon.getRandom();
		this.yourPokemon = Pokemon.getRandom();
	}
	
	private void drawFightScene() throws InterruptedException {
		//this.roboter.drawGcode("fightscene_empty.gcode)");
		//this.roboter.drawGcode(this.yourPokemon.getFile());
		//this.roboter.drawGcode(this.myPokemon.getFile());
		this.roboter.drawGcode("EKKIG bereinigt.gcode");
	}
	
	private int drawAttackScreen() {
		int option = 0;
		// Draw options
		LCD.clear();
		this.drawBorder();
		LCD.drawString("Attack 1", 5, 3);
		LCD.drawString("Attack 2", 5, 4);
		LCD.drawString("Attack 3", 5, 5);
		LCD.drawString("Attack 4", 5, 6);
		// Get selection (UP/DOWN, Bestätigen mit Enter)
		int button;
		while(true) {
			button = Button.waitForAnyPress();
			if(button == Button.ID_ENTER) {
				break;
			}
			else if(button == Button.ID_DOWN) {
				option++;
				if(option >= 3) {
					option = 2;
				}
			}
			else if(option == Button.ID_UP) {
				option--;
				if(option < 0) {
					option = 0;
				}
			}
		}
		return option;
	}
}
