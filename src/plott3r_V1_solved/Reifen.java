package plott3r_V1_solved;

public class Reifen {
	private float durchmesser;

	public Reifen(float durchmesser) {
		this.durchmesser = durchmesser;
	}

	public float getDurchmesser() {
		return durchmesser;
	}

	public float getUmfang() {
		return this.durchmesser * (float) Math.PI;
	}
}
