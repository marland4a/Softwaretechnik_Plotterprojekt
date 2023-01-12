package positions;

import util.MathHelper;

public class Position2D {

	private float x, y;

	// NEU: Add empty constructor
	public Position2D() {
		this.x = Float.NaN;
		this.y = Float.NaN;
	}

	public Position2D(float x, float y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public boolean equals(Object obj) {
		if (this.getXRounded() == ((Position2D) obj).getXRounded()
				&& this.getYRounded() == ((Position2D) obj).getYRounded())
			return true;

		return false;
	}

	public float getX() {
		return x;
	}

	private float getXRounded() {
		return round(this.getX());
	}

	public float getY() {
		return y;
	}

	private float getYRounded() {
		return round(this.getY());
	}

	private float round(float d) {
		return (float) MathHelper.round(d, 2);
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return this.getXRounded() + " : " + this.getYRounded();
	}
}
