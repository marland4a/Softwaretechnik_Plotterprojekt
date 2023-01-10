package plott3r_V1.geometrie;

import java.util.ArrayList;
import java.util.List;

import positions.Position2D;

public class Linie extends GeometrischeFigur {

	public Linie(Position2D mittelpunkt, int radius) {
		super(mittelpunkt, radius);
	}

	public Linie(Position2D mittelpunkt, int radius, int rotation) {
		super(mittelpunkt, radius, rotation);
	}

	@Override
	protected List<Position2D> calculatePositions() {
		List<Position2D> ret = new ArrayList<>();
		ret.add(new Position2D(this.getMittelpunkt().getX(), this.getMittelpunkt().getY() + this.getRadius()));
		ret.add(new Position2D(this.getMittelpunkt().getX(), this.getMittelpunkt().getY() - this.getRadius()));
		return ret;
	}

}
