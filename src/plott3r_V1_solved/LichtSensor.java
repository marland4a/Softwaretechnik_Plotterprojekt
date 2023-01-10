package plott3r_V1_solved;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorMode;

public final class LichtSensor extends Sensor<EV3ColorSensor> {

	final float epsilon = 0.02f;
	private float schwellwert;

	public LichtSensor(Port port) {
		super(new EV3ColorSensor(port));
	}

	@Override
	protected SensorMode getSensorMode(EV3ColorSensor sensor) {
		return sensor.getRedMode();
	}

	@Override
	protected boolean isAktiv(float wert) {
		return Math.abs(wert - schwellwert) > epsilon;
	}

	@Override
	public void kalibriere(float wert) {
		this.schwellwert = wert;
	}

}
