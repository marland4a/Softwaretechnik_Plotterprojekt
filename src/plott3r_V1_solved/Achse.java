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

	// Für bessere Laufzeitperformance: Vorberechnen
	private float mmZuGrad;

	public Achse(Sensor sensor, Port port, Einbaurichtung einbaurichtung, Reifen antriebsEinheit,
			IUebersetzung... uebersetzungsEinheiten) {
		super();
		this.motor = new Motor(port, einbaurichtung);
		this.sensor = sensor;
		if (uebersetzungsEinheiten != null)
			this.uebersetzungsEinheiten = Arrays.asList(uebersetzungsEinheiten);
		this.antriebsEinheit = antriebsEinheit;

		this.mmZuGrad = this.getUebersetzungsverhaeltnis() * 360 / this.antriebsEinheit.getUmfang();
	}

	protected int berechneGradAusMm(float mm) {
		// grad = mm / Umfang * Uebersetzungsverhaeltnis * 360
		/*
		 * double umdrehungenRad = mm / this.antriebsEinheit.getUmfang(); double
		 * umdrehungenMotor = umdrehungenRad * this.getUebersetzungsverhaeltnis();
		 * double gradMotor = umdrehungenMotor * 360; return (int)
		 * Math.round(gradMotor);
		 */
		return Math.round(mm * mmZuGrad);
	}

	protected Motor getMotor() {
		return this.motor;
	}

	public Sensor getSensor() {
		return sensor;
	}

	protected float getUebersetzungsverhaeltnis() {
		if (uebersetzungsEinheiten.isEmpty())
			return 1;
		return uebersetzungsEinheiten.get(0).getUebersetzungsverhaeltnis();
	}

	public boolean isSensorAktiv() {
		if (sensor == null)
			return true;
		return sensor.isAktiv();
	}

	public void setSpeed(float mmSecond) {
		int gradMotor = this.berechneGradAusMm(mmSecond);
		this.getMotor().setSpeed(gradMotor);
	}

	public void stop() {
		this.motor.stop();
	}

}
