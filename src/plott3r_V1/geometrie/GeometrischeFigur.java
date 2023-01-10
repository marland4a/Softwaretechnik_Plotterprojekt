package plott3r_V1.geometrie;

import java.util.List;

import positions.Position2D;
import util.Einheitskreis;

public abstract class GeometrischeFigur {

	private Position2D mittelpunkt;

	private List<Position2D> positions = null;

	private int radius;

	private int rotation = 0;

	public GeometrischeFigur(Position2D mittelpunkt, int radius) {
		super();
		this.mittelpunkt = mittelpunkt;
		this.radius = radius;
	}

	public GeometrischeFigur(Position2D mittelpunkt, int radius, int rotation) {
		this(mittelpunkt, radius);
		this.rotation = rotation;
	}

	protected abstract List<Position2D> calculatePositions();

	public Position2D getMittelpunkt() {
		return mittelpunkt;
	}

	public final List<Position2D> getPositions() {
		if (this.positions == null) {
			this.positions = this.calculatePositions();
			this.rotate();
		}
		return this.positions;
	}

	public int getRadius() {
		return radius;
	}

	public int getRotation() {
		return rotation;
	}

	protected final void rotate() {
		for (Position2D pos : this.getPositions()) {
			// Auf 0 verschieben
			pos.setX(pos.getX() - this.getMittelpunkt().getX());
			pos.setY(pos.getY() - this.getMittelpunkt().getY());
			// Rotation
			pos = Einheitskreis.berechnePositionAufKreis(pos, this.rotation);
			// Zurück schieben auf Mittelpunkt
			pos.setX(pos.getX() + this.getMittelpunkt().getX());
			pos.setY(pos.getY() + this.getMittelpunkt().getY());
		}

	}

}
