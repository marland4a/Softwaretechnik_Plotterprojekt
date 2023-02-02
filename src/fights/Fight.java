package fights;

import java.util.Random;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import positions.Position2D;

public class Fight {
	/* Settings */
	private Pokemon spielerPokemon = null;
	private Pokemon gegnerPokemon = null;
	private plott3r_V1_solved.Roboter roboter;
	private LcdImages lcdimages;

	public Fight(plott3r_V1_solved.Roboter roboter) {
		this.roboter = roboter;
		this.lcdimages = new LcdImages();
	}

	/* Start fight */
	public void start() throws InterruptedException {
		// Display start screen (Auswahlmenü)
		this.printStartScreen();

		// Select pokemons (falls noch nicht geschehen)
		if (this.spielerPokemon == null) {
			this.selectPokemon();
		}
		this.gegnerPokemon = Pokemon.getRandom();

		// Draw fight scene
		// this.drawFightScene(); // Disabled for testing

		// Fight
		Attack spieler_attack, gegner_attack;
		int spieler_damage, gegner_damage;
		while (this.spielerPokemon.getLife() > 0 && this.gegnerPokemon.getLife() > 0) {
			// Attacke + Schaden
			spieler_attack = this.selectAttack(); // Spieler: Attacke auswählen
			gegner_attack = Pokemon.getAttack(new Random().nextInt(4)); // Gegner: Zufällige Attacke
			spieler_damage = spieler_attack.getDamage();
			gegner_damage = gegner_attack.getDamage();
			if (spieler_damage < 0) { // Negativer Schaden = Gegner blocken/schwächen
				gegner_damage += spieler_damage; // Verringere Gegnerschaden
			}
			if (gegner_damage < 0) {
				spieler_damage += gegner_damage; // Verringere Spielerschaden
				gegner_damage = 0; // Blocken macht dem Gegner keinen Schaden
			}
			if (spieler_damage < 0) { // Blocken macht dem Gegner keinen Schaden
				spieler_damage = 0;
			}
			// Schaden anwenden
			this.spielerPokemon.reduceLife(gegner_damage);
			this.gegnerPokemon.reduceLife(spieler_damage);
			// Ergebnis anzeigen
			this.printAttackResults();
		}

		// Ende: Ergebnis anzeigen
		LCD.clear();
		this.printBorder();
		if (this.spielerPokemon.getLife() > this.gegnerPokemon.getLife()) {
			LCD.drawString(this.spielerPokemon.getName(), 3, 2);
			LCD.drawString("hat gewonnen!", 3, 3);
		} else if (this.gegnerPokemon.getLife() > this.spielerPokemon.getLife()) {
			LCD.drawString(this.gegnerPokemon.getName(), 3, 2);
			LCD.drawString("hat gewonnen!", 3, 3);
		} else {
			LCD.drawString("Unentschieden!", 2, 3);
		}
	}

	/* Rahmen am LCD anzeigen */
	private void printBorder() {
		// Image border = this.lcdimages.getBorderImg();
		// this.glcd.drawRegion(border, 0, 0, border.getWidth(), border.getHeight(), 0,
		// this.glcd.getWidth(), this.glcd.getHeight(), 0);
		byte[] border = this.lcdimages.getBorder();
		LCD.bitBlt(border, 178, 128, 0, 0, 0, 0, 178, 128, LCD.ROP_ORINVERTED);
	}

	/* Initiales Auswahlmenü */
	private void printStartScreen() {
		boolean exit = false;
		while (!exit) {
			LCD.clear();
			this.printBorder();
			// Draw options
			int option = 0;
			LCD.drawString("> Fight", 3, 2);
			LCD.drawString("Item", 5, 3);
			LCD.drawString("Pokemon", 5, 4);
			LCD.drawString("Run", 5, 5);
			// Get selection (UP/DOWN, Bestätigen mit Enter)
			int button;
			while (true) {
				button = Button.waitForAnyPress();
				if (button == Button.ID_ENTER) {
					break;
				} else if (button == Button.ID_DOWN) {
					LCD.drawChar(' ', 3, option + 2);
					option++;
					if (option > 3) {
						option = 0;
					}
					LCD.drawChar('>', 3, option + 2);
				} else if (button == Button.ID_UP) {
					LCD.drawChar(' ', 3, option + 2);
					option--;
					if (option < 0) {
						option = 3;
					}
					LCD.drawChar('>', 3, option + 2);
				}
			}
			// Optionen auswerten
			switch (option) {
			case 0: // Fight
				exit = true;
				break;
			case 1: // Item (aktuell nicht implementiert)
				LCD.clear();
				this.printBorder();
				LCD.drawString("Du hast", 5, 2);
				LCD.drawString("noch keine", 3, 3);
				LCD.drawString("Items", 6, 4);
				Button.waitForAnyPress(); // Auf Bestätigung warten
				exit = false;
				break;
			case 2: // Pokemon
				this.selectPokemon();
				break;
			case 3: // Run
				LCD.clear();
				this.printBorder();
				LCD.drawString("Du hast", 5, 2);
				LCD.drawString("aufgegeben.", 3, 3);
				LCD.drawString("Schade!", 5, 4);
				Button.waitForAnyPress(); // Auf Bestätigung warten
				System.exit(0);
				break;
			}
		}
	}

	/* Pokemon auswählen */
	private void selectPokemon() {
		LCD.clear();
		this.printBorder();

		// TODO: Auswahlmenü?
		this.spielerPokemon = Pokemon.getRandom(); // Auswahl durch Zufall

		LCD.drawString("Dein Pokemon:", 2, 2);
		LCD.drawString(this.spielerPokemon.getName(), 3, 3);
		LCD.drawString("Dein Gegner:", 2, 4);
		if (this.gegnerPokemon != null) {
			LCD.drawString(this.gegnerPokemon.getName(), 3, 5);
		} else {
			LCD.drawString("???????", 5, 5); // Pokemon ist noch nicht bekannt
		}
		Button.waitForAnyPress();
	}

	/* Fight-Szene (Rahmen) plotten */
	private void drawFightScene() throws InterruptedException {
		// this.roboter.drawGcode("fightscene_empty.gcode)");
		// this.roboter.drawGcode(this.gegnerPokemon.getFile());
		// this.roboter.drawGcode(this.spielerPokemon.getFile());
		this.roboter.drawGcode("EKKIG bereinigt.gcode");
	}

	/* Spieler-Pokemon plotten */
	private void drawSpielerPokemon() throws InterruptedException {
		Position2D position = new Position2D();
		this.roboter.drawGcode(this.spielerPokemon.getGcodePath_front(), position);
	}

	/* Gegner-Pokemon plotten */
	private void drawGegnerPokemon() throws InterruptedException {
		Position2D position = new Position2D();
		this.roboter.drawGcode(this.spielerPokemon.getGcodePath_back(), position);
	}

	/* Nächste Attacke auswählen */
	private Attack selectAttack() {
		int option = 0;
		// Draw options
		LCD.clear();
		this.printBorder();
		LCD.drawString("> " + Pokemon.getAttack(0).getName(), 3, 2); // Optionen anzeigen
		LCD.drawString(Pokemon.getAttack(1).getName(), 5, 3);
		LCD.drawString(Pokemon.getAttack(2).getName(), 5, 4);
		LCD.drawString(Pokemon.getAttack(3).getName(), 5, 5);
		// Get selection (UP/DOWN, Bestätigen mit Enter)
		int button;
		while (true) {
			button = Button.waitForAnyPress();
			if (button == Button.ID_ENTER) {
				break;
			} else if (button == Button.ID_DOWN) {
				LCD.drawChar(' ', 3, option + 2);
				option++;
				if (option > 3) {
					option = 0;
				}
				LCD.drawChar('>', 3, option + 2);
			} else if (option == Button.ID_UP) {
				LCD.drawChar(' ', 3, option + 2);
				option--;
				if (option < 0) {
					option = 3;
				}
				LCD.drawChar('>', 3, option + 2);
			}
		}
		return Pokemon.getAttack(option);
	}
	
	/* Ergebnis der Attacke darstellen */
	private void printAttackResults() {
		String int_str;
		int lcd_pos;
		
		LCD.clear();
		this.printBorder();
		
		this.printAttackResult(this.spielerPokemon, 2);
		this.printAttackResult(this.gegnerPokemon, 4);
		Button.waitForAnyPress(); // Auf Bestätigung für nächsten Durchgang warten
	}
	
	/* Ergebnis für einen Spieler darstellen */
	// lcd_y – Zeile, in der der Name steht. Die Leben stehen in der nächsten Zeile
	private void printAttackResult(Pokemon pokemon, int lcd_y) {
		String int_str;
		// Erste Zeile: "Name:"
		int lcd_x = 3;
		LCD.drawString(pokemon.getName(), lcd_x, lcd_y);
		lcd_x += pokemon.getName().length() + 1;
		LCD.drawString(": ", lcd_x, 2);
		// Zweite Zeile: " Leben (-Damage)"
		lcd_x = 4;
		lcd_y++;
		int_str = String.valueOf(pokemon.getLife());
		LCD.drawString(int_str, lcd_x, lcd_y);
		lcd_x += int_str.length();
		LCD.drawChar('(', lcd_x++, lcd_y);
		LCD.drawChar('-', lcd_x++, lcd_y);
		int_str = String.valueOf(pokemon.getLastDamage());
		LCD.drawString(int_str, lcd_x, lcd_y);
		lcd_x += int_str.length();
		LCD.drawChar(')', lcd_x, lcd_y);
	}
}