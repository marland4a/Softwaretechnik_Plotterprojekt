package fights;
import lejos.hardware.lcd.*;
import lejos.utility.Delay;
import lejos.hardware.Button;
import java.util.Random;


public class Fight {
	/* Settings */
	private Pokemon myPokemon;
	private Pokemon yourPokemon;
	private plott3r_V1_solved.Roboter roboter;
	private LcdImages lcdimages;
	private GraphicsLCD glcd = lejos.hardware.BrickFinder.getDefault().getGraphicsLCD();
	
	public Fight(plott3r_V1_solved.Roboter roboter) {
		this.roboter = roboter;
		this.lcdimages = new LcdImages();
	}
	
	/* Start fight */
	public void start() throws InterruptedException {
		// Display start screen
		this.drawStartScreen();
		// Select pokemons
		this.selectPokemons();
		// Draw fight scene
		//this.drawFightScene();
		// Fight
		int my_option, your_option;
		int my_damage, your_damage;
		while(this.myPokemon.getLife() > 0 && this.yourPokemon.getLife() > 0) {
			my_option = this.drawAttackScreen();
			your_option = new Random().nextInt(4);
			my_damage = Pokemon.optionToDamage(my_option);
			your_damage = Pokemon.optionToDamage(my_option);
			if(my_damage < 0) {
				your_damage += my_damage;		// Verringere Gegnerschaden
			}
			if(your_damage < 0) {
				my_damage += your_damage;		// Verringere Spielerschaden
				your_damage = 0;				// Selber keinen Schaden machen
			}
			if(my_damage < 0) {
				my_damage = 0;
			}
			this.myPokemon.reduceLife(your_damage);
			this.yourPokemon.reduceLife(my_damage);
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
		//Image border = this.lcdimages.getBorderImg();
		//this.glcd.drawRegion(border, 0, 0, border.getWidth(), border.getHeight(), 0, this.glcd.getWidth(), this.glcd.getHeight(), 0);
		byte[] border = this.lcdimages.getBorder();
		LCD.bitBlt(border, 178, 128, 0, 0, 0, 0, 178, 128, LCD.ROP_ORINVERTED);
	}
	
	private void drawStartScreen() {
		int option = 0;
		LCD.clear();
		this.drawBorder();
		// Draw options
		LCD.drawString("> Fight", 3, 2);
		LCD.drawString("Item", 5, 3);
		LCD.drawString("Pokemon", 5, 4);
		LCD.drawString("Run", 5, 5);
		// Get selection (UP/DOWN, Bestätigen mit Enter)
		int button;
		while(true) {
			button = Button.waitForAnyPress();
			if(button == Button.ID_ENTER) {
				break;
			}
			else if(button == Button.ID_DOWN) {
				LCD.drawChar(' ', 3, option+2);
				option++;
				if(option > 3) {
					option = 0;
				}
				LCD.drawChar('>', 3, option+2);
			}
			else if(option == Button.ID_UP) {
				LCD.drawChar(' ', 3, option+2);
				option--;
				if(option < 0) {
					option = 3;
				}
				LCD.drawChar('>', 3, option+2);
			}
		}
		// Optionen auswerten
		switch(option) {
			case 0: // Fight
				break;
			case 1: // Item
				LCD.clear();
				this.drawBorder();
				LCD.drawString("Du hast", 5, 2);
				LCD.drawString("noch keine", 3, 3);
				LCD.drawString("Items", 6, 4);
				Button.waitForAnyPress();	// Auf Bestätigung warten
				this.drawStartScreen();
				break;
			case 2: // Pokemon
				this.selectPokemons();
				break;
			case 3: // Run
				LCD.clear();
				this.drawBorder();
				LCD.drawString("Du hast", 5, 2);
				LCD.drawString("aufgegeben", 3, 3);
				LCD.drawString("Schade!", 5, 4);
				Button.waitForAnyPress();	// Auf Bestätigung warten
				System.exit(0);
				break;
		}
	}
	
	private void selectPokemons() {
		LCD.clear();
		this.drawBorder();
		
		this.myPokemon = Pokemon.getRandom();
		this.yourPokemon = Pokemon.getRandom();
		
		LCD.drawString("Dein Pokemon:", 2, 2);
		LCD.drawString(this.myPokemon.getName(), 3, 3);
		LCD.drawString("Dein Gegner:", 2, 4);
		LCD.drawString(this.yourPokemon.getName(), 3, 5);
		Button.waitForAnyPress();
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
		LCD.drawString("> Donnerschock", 3, 2);
		LCD.drawString("Heuler", 5, 3);
		LCD.drawString("Donnerwelle", 5, 4);
		LCD.drawString("Ruckzuckhieb", 5, 5);
		// Get selection (UP/DOWN, Bestätigen mit Enter)
		int button;
		while(true) {
			button = Button.waitForAnyPress();
			if(button == Button.ID_ENTER) {
				break;
			}
			else if(button == Button.ID_DOWN) {
				LCD.drawChar(' ', 3, option+2);
				option++;
				if(option > 3) {
					option = 0;
				}
				LCD.drawChar('>', 3, option+2);
			}
			else if(option == Button.ID_UP) {
				LCD.drawChar(' ', 3, option+2);
				option--;
				if(option < 0) {
					option = 3;
				}
				LCD.drawChar('>', 3, option+2);
			}
		}
		return option;
	}
}
