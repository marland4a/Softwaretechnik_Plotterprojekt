package plott3r_V1_solved;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.utility.Delay;
import positions.Position2D;
import positions.Position3D;
import util.Plott3rLCD;

public class Roboter {
	
	private final float XACHSE_MIN = -136.0f;
	private final float YACHSE_MAX = 250.0f;
	
	/* Draw a GCode file with relative coordinates and exit at EOF */
	public void drawGcode(String filename) throws InterruptedException {
		util.GcodeParser parser = new util.GcodeParser(filename);
		positions.Position3D bewegung = new positions.Position3D(0, 0, false);
		/*do {
			nextPos = parser.getPosition();
			if(nextPos == null) {
				return;
			}*/
		for(positions.Position3D nextPos: parser.getAllPositions()) {
			//nextPos.setX(nextPos.getX() / -100.0);
			//nextPos.setY(nextPos.getY() / -100.0);
			if(!Float.isNaN(nextPos.getX())) {
				//nextPos.setX(prevPos.getX());
				bewegung.setX(nextPos.getX());
			}
			//bewegung.setX(nextPos.getX() - prevPos.getX());
			if(!Float.isNaN(nextPos.getY())) {
				//nextPos.setY(prevPos.getY());
				bewegung.setY(this.YACHSE_MAX - nextPos.getY());
			}
			//bewegung.setY(nextPos.getY() - prevPos.getY());
			bewegung.setZ(nextPos.isZ());
			
			/*System.out.println(bewegung.getX());
			System.out.println(bewegung.getY());
			System.out.println();*/
			
			this.moveToPosition(bewegung, 50);
			//LCD.drawInt((int)nextPos.getX(), 0, 0);
			//LCD.drawInt((int)nextPos.getY(), 0, 1);
			//Delay.msDelay(1000);
			//prevPos = nextPos;
		//} while(nextPos != null);
		}
	}
	
	/* Display image on display */
	private void drawDisplay(String filename) {
		
	}
	
	public static void main(String args[]) {
		try {
			Roboter roboter = new Roboter();
			Sound.beep();
			roboter.moveToHomePosition();
			roboter.bereitePapierVor();
			
			roboter.moveToPosition(new positions.Position2D(10, roboter.YACHSE_MAX), 50);
			//roboter.drawGcode("GanzerKampfbereinigt_shrink.gcode");
			//roboter.drawGcode("test2.gcode");
			//roboter.moveToPosition(new positions.Position3D(50, 0, false), 10);
			//roboter.moveToPosition(new positions.Position3D(50, 50, false), 10);
			//roboter.moveToPosition(new positions.Position3D(0, 50, false), 10);
			
			fights.Fight fight = new fights.Fight(roboter);
			fight.start();

			Delay.msDelay(1000);
			roboter.entfernePapier();
			//roboter.moveToHomePosition();
			roboter.moveToPosition(new positions.Position3D(0, 0, false), 50);
			Sound.twoBeeps();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Position3D currentPosition;

	private MultiPositionAchse xAchse = new MultiPositionAchse(new TouchSensor(SensorPort.S1), MotorPort.A,
			Einbaurichtung.UMGEKEHRT, new Reifen(40.0f),
			new Zahnradsatz(new Zahnrad(Zahnrad.ANZAHL_ZAEHNE_KLEIN), new Zahnrad(Zahnrad.ANZAHL_ZAEHNE_GROSS)));
	private MultiPositionAchse yAchse = new MultiPositionAchse(new LichtSensor(SensorPort.S3), MotorPort.B,
			Einbaurichtung.UMGEKEHRT, new Reifen(43.2f),
			new Zahnradsatz(new Zahnrad(Zahnrad.ANZAHL_ZAEHNE_KLEIN), new Zahnrad(Zahnrad.ANZAHL_ZAEHNE_GROSS)));
	private DualPositionAchse zAchse = new DualPositionAchse(null, MotorPort.C, Einbaurichtung.REGULAER, null, null);

	public Roboter() {

	}

	private void bereitePapierVor() throws InterruptedException {
		Plott3rLCD.drawString("Blatt einlegen und PRESS");
		Button.waitForAnyPress();
		yAchse.setSpeed(200);
		while (!yAchse.isSensorAktiv()) {
			yAchse.forward();
		}
		yAchse.stop();
		// f�hrt die y-Achse etwas weiter zur�ck, sodass mehr Platz vom Papier
		// genutzt werden kann
		//yAchse.backward();
		//Delay.msDelay(230);
		//yAchse.stop();
		yAchse.setSpeed(5);
		while(yAchse.isSensorAktiv()) {
			yAchse.backward();
		}
		yAchse.stop();
		yAchse.resetTachoCount();
		Plott3rLCD.drawString("PRESS Programmstarten");
		Button.waitForAnyPress();
	}

	private void entfernePapier() throws InterruptedException {
		zAchse.deaktiviere();
		yAchse.setSpeed(Integer.MAX_VALUE);
		//yAchse.backward(2000);
		yAchse.forward(2000);
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		System.exit(0);
	}

	public Position3D getCurrentPosition() {
		return this.currentPosition;
	}

	public MultiPositionAchse getXAchse() {
		return this.xAchse;
	}

	public MultiPositionAchse getYAchse() {
		return this.yAchse;
	}

	protected void moveToHomePosition() throws InterruptedException {
		zAchse.deaktiviere();
		xAchse.setSpeed(50);
		while (!xAchse.isSensorAktiv()) {
			xAchse.backward();
		}
		xAchse.stop();
		//xAchse.forward();
		//Delay.msDelay(200);
		//xAchse.stop();
		xAchse.setSpeed(5);
		while(xAchse.isSensorAktiv()) {
			xAchse.forward();
		}
		xAchse.stop();
		this.currentPosition = new Position3D(0, 0, false);
		this.resetTachoCounts();
		this.moveToPosition(new positions.Position2D(0, 0), 20); 
		this.currentPosition = new Position3D(-1*this.XACHSE_MIN, 0, false);
	}

	private void moveToPosition(Position2D position2D, float mmSec) throws InterruptedException {
		this.moveToPosition(new Position3D(position2D, this.zAchse.isAktiv()), mmSec);
	}

	private void moveToPosition(Position3D position, float mmSec) throws InterruptedException {
		if (position.isZ())
			this.zAchse.aktiviere();
		else
			this.zAchse.deaktiviere();

		//float deltaX = currentPosition.getX() - position.getX();
		//float deltaY = currentPosition.getY() - position.getY();
		float deltaX = (-1*this.XACHSE_MIN - position.getX()) - currentPosition.getX();
		float deltaY = position.getY() - currentPosition.getY();
		float hypo = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);

		// float time = hypo / mmSec;
		float freq = mmSec / hypo; // 1/s

		xAchse.getMotor().synchronizeWith(yAchse.getMotor());

		xAchse.setSpeed(deltaX * freq);
		yAchse.setSpeed(deltaY * freq);

		xAchse.getMotor().startSynchronization();

		xAchse.rotateMm(deltaX);
		yAchse.rotateMm(deltaY);

		xAchse.getMotor().endSynchronization();

		xAchse.getMotor().waitComplete();
		yAchse.getMotor().waitComplete();

		this.currentPosition = new Position3D(xAchse.getPositionFromTachoCount(), yAchse.getPositionFromTachoCount(),
				zAchse.isAktiv());
	}

	private void resetTachoCounts() {
		this.xAchse.resetTachoCount();
		this.yAchse.resetTachoCount();
		if (xAchse.getTachoCount() != 0 || yAchse.getTachoCount() != 0)
			throw new RuntimeException("Konnte Tachocount nicht zur�cksetzen");
	}

	public void stop() {
		xAchse.stop();
		yAchse.stop();
		zAchse.stop();
	}

}
