package plott3r_V1;

import lejos.hardware.Sound;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.utility.Delay;
import plott3r_V1.geometrie.GeometrischeFigur;
import positions.Position2D;
import positions.Position3D;

public class Roboter {
	public static void main(String args[]) {
		try {
			Roboter roboter = new Roboter();
			Sound.beep();
			roboter.moveToHomePosition();
			roboter.bereitePapierVor();

			Delay.msDelay(1000);
			roboter.entfernePapier();
			roboter.moveToHomePosition();
			Sound.twoBeeps();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Position3D currentPosition;

	private MultiPositionAchse xAchse = new MultiPositionAchse(new TouchSensor(SensorPort.S1), MotorPort.A, Einbaurichtung.UMGEKEHRT, new Reifen(40.0), new Zahnradsatz(new Zahnrad(Zahnrad.ANZAHL_ZAEHNE_MITTEL), new Zahnrad(Zahnrad.ANZAHL_ZAEHNE_MITTEL)));
	private MultiPositionAchse yAchse = new MultiPositionAchse(new LichtSensor(SensorPort.S3), MotorPort.B, Einbaurichtung.UMGEKEHRT, new Reifen(43.2), new Zahnradsatz(new Zahnrad(Zahnrad.ANZAHL_ZAEHNE_KLEIN), new Zahnrad(Zahnrad.ANZAHL_ZAEHNE_GROSS)));
	private DualPositionAchse zAchse = new DualPositionAchse(null, MotorPort.C, Einbaurichtung.REGULAER, null, null);

	public Roboter() {

	}

	private void bereitePapierVor() throws InterruptedException {
		// TODO: Implementierung
	}

	private void entfernePapier() throws InterruptedException {
		zAchse.deaktiviere();
		yAchse.setSpeed(Integer.MAX_VALUE);
		yAchse.backward(2000);
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
		// TODO: Implementierung
	}

	private void moveToPosition(Position2D position2D, int mmSec) throws InterruptedException {
		// TODO: Implementierung
	}

	private void moveToPosition(Position3D position, int mmSec) throws InterruptedException {
		// TODO: Implementierung
	}

	private void resetTachoCounts() {
		this.xAchse.resetTachoCount();
		this.yAchse.resetTachoCount();
		if (xAchse.getTachoCount() != 0 || yAchse.getTachoCount() != 0)
			throw new RuntimeException("Konnte Tachocount nicht zurücksetzen");
	}

	public void stop() {
		xAchse.stop();
		yAchse.stop();
		zAchse.stop();
	}

	public void zeichneGeometrischeFigur(GeometrischeFigur geo, int mmSec) throws InterruptedException {
		this.zAchse.deaktiviere();
		this.moveToPosition(geo.getPositions().get(0), 1000);
		this.zAchse.aktiviere();
		for (Position2D pos : geo.getPositions()) {
			this.moveToPosition(pos, mmSec);
		}
		this.zAchse.deaktiviere();
	}

}
