package plott3r_V1_solved;

import lejos.hardware.port.Port;
import lejos.utility.Delay;

public class DualPositionAchse extends Achse {

	private boolean aktiv;

	public DualPositionAchse(Sensor sensor, Port port, Einbaurichtung einbaurichtung, Reifen letzteEinheit, IUebersetzung... uebersetzungsEinheiten) {
		super(sensor, port, einbaurichtung, letzteEinheit, uebersetzungsEinheiten);
		this.aktiv = false;
	}

	public void aktiviere() {
		if (this.aktiv)
			return;
		this.aktiv = true;
		this.getMotor().rotate(-90);
		Delay.msDelay(500);
	}

	public void deaktiviere() {
		if (!this.aktiv)
			return;
		this.aktiv = false;
		this.getMotor().rotate(90);
		Delay.msDelay(500);
	}

	public boolean isAktiv() {
		return aktiv;
	}

	public void rotate(int grad) {
		this.getMotor().rotate(grad);
	}
}
