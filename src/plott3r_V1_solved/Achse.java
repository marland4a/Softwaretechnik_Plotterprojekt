package plott3r_V1_solved;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lejos.hardware.port.Port;

public abstract class Achse {

	protected Reifen antriebsEinheit;

	private Motor motor;

	private Sensor sensor;

	private List<IUebersetzung> uebersetzungsEinheiten = new ArrayList<>();

	public Achse(Sensor sensor, Port port, Einbaurichtung einbaurichtung, Reifen antriebsEinheit, IUebersetzung... uebersetzungsEinheiten) {
		super();
		this.motor = new Motor(port, einbaurichtung);
		this.sensor = sensor;
		if (uebersetzungsEinheiten != null)
			this.uebersetzungsEinheiten = Arrays.asList(uebersetzungsEinheiten);
		this.antriebsEinheit = antriebsEinheit;
	}

	protected int berechneGradAusMm(double mm) {
		double umdrehungenRad = mm / this.antriebsEinheit.getUmfang();
		double umdrehungenMotor = umdrehungenRad * this.getUebersetzungsverhaeltnis();
		double gradMotor = umdrehungenMotor * 360;
		return (int) Math.round(gradMotor);
	}

	protected Motor getMotor() {
		return this.motor;
	}

	public Sensor getSensor() {
		return sensor;
	}

	protected double getUebersetzungsverhaeltnis() {
		if (uebersetzungsEinheiten.isEmpty())
			return 1;
		return uebersetzungsEinheiten.get(0).getUebersetzungsverhaeltnis();
	}

	public boolean isSensorAktiv() {
		if (sensor == null)
			return true;
		return sensor.isAktiv();
	}

	public void setSpeed(double mmSecond) {
		int gradMotor = this.berechneGradAusMm(mmSecond);
		this.getMotor().setSpeed(gradMotor);
	}

	public void stop() {
		this.motor.stop();
	}

}
