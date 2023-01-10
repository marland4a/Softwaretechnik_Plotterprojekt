package plott3r_V1_solved;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.Port;
import lejos.robotics.RegulatedMotor;

public class Motor {

	private Einbaurichtung einbaurichtung;

	private RegulatedMotor regulatedMotor;

	public Motor(Port port, Einbaurichtung einbaurichtung) {
		regulatedMotor = new EV3LargeRegulatedMotor(port);
		this.einbaurichtung = einbaurichtung;
	}

	public void backward() {
		if (einbaurichtung == Einbaurichtung.UMGEKEHRT) {
			this.regulatedMotor.forward();
			return;
		}
		this.regulatedMotor.backward();
	}

	public void endSynchronization() {
		this.regulatedMotor.endSynchronization();
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		regulatedMotor.close();
	}

	public void forward() {
		if (einbaurichtung == Einbaurichtung.UMGEKEHRT) {
			this.regulatedMotor.backward();
			return;
		}
		this.regulatedMotor.forward();
	}

	protected Einbaurichtung getEinbaurichtung() {
		return einbaurichtung;
	}

	public int getTachoCount() {
		return this.regulatedMotor.getTachoCount();
	}

	public void resetTachoCount() {
		this.regulatedMotor.resetTachoCount();
	}

	public void rotate(int grad) {
		this.regulatedMotor.rotate(grad, false);
	}

	public void rotateTo(int limitAngle) {
		this.regulatedMotor.rotateTo(limitAngle);

	}

	public void setSpeed(int speed) {
		this.regulatedMotor.setSpeed(speed);
	}

	public void startSynchronization() {
		this.regulatedMotor.startSynchronization();
	}

	public void stop() {
		regulatedMotor.stop();
	}

	public void synchronizeWith(Motor motor) {
		this.regulatedMotor.synchronizeWith(new RegulatedMotor[] { motor.regulatedMotor });

	}

	public void waitComplete() {
		this.regulatedMotor.waitComplete();
	}

}
