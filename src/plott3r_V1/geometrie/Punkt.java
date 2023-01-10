package plott3r_V1.geometrie;

import java.util.ArrayList;
import java.util.List;

import positions.Position2D;

public class Punkt extends GeometrischeFigur {

	public Punkt(Position2D mittelpunkt, int radius) {
		super(mittelpunkt, radius);
	}

	@Override
	protected List<Position2D> calculatePositions() {
		List<Position2D> ret = new ArrayList<>();
		ret.add(this.getMittelpunkt());
		return ret;
	}

}
