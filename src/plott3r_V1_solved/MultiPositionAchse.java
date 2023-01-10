package plott3r_V1_solved;

import lejos.hardware.port.Port;

public class MultiPositionAchse extends Achse {

	public MultiPositionAchse(Sensor sensor, Port port, Einbaurichtung einbaurichtung, Reifen letzteEinheit, IUebersetzung... uebersetzungsEinheiten) {
		super(sensor, port, einbaurichtung, letzteEinheit, uebersetzungsEinheiten);
	}

	public void backward() {
		this.getMotor().backward();
	}

	public void backward(long timeInMillis) throws InterruptedException {
		this.backward();
		Thread.sleep(timeInMillis);
		this.stop();
	}

	public void forward() {
		this.getMotor().forward();
	}

	public void forward(long timeInMillis) throws InterruptedException {
		this.forward();
		Thread.sleep(timeInMillis);
		this.stop();
	}

	public double getPositionFromTachoCount() {
		final double gearWheelRatio = this.getUebersetzungsverhaeltnis();
		final double umfang = this.antriebsEinheit.getUmfang();
		final int tachoCount = this.getTachoCount();
		double mm = (tachoCount * umfang) / (gearWheelRatio * 360);
		if (this.getMotor().getEinbaurichtung() == Einbaurichtung.UMGEKEHRT)
			mm = mm * -1;
		return mm;
	}

	public int getTachoCount() {
		return this.getMotor().getTachoCount();
	}

	public void resetTachoCount() {
		this.getMotor().resetTachoCount();
	}

	public void rotateMm(double mm) {
		int gradMotor = this.berechneGradAusMm(mm);
		if (this.getMotor().getEinbaurichtung() == Einbaurichtung.UMGEKEHRT)
			gradMotor *= -1;
		this.getMotor().rotate(gradMotor);
	}

}
