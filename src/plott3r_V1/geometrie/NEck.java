package plott3r_V1.geometrie;

import java.util.ArrayList;
import java.util.List;

import positions.Position2D;
import util.Einheitskreis;

public class NEck extends GeometrischeFigur {

	private int anzahlEcken;

	public NEck(Position2D mittelpunkt, int radius, int anzahlEcken) {
		super(mittelpunkt, radius);
		this.anzahlEcken = anzahlEcken;
	}

	@Override
	protected List<Position2D> calculatePositions() {
		List<Position2D> ret = new ArrayList<>();

		double gradProEcke = 360.0d / anzahlEcken;
		for (int i = 1; i <= anzahlEcken; i++) {
			ret.add(Einheitskreis.berechnePositionAufKreis(i * gradProEcke + (gradProEcke / 2), this.getRadius()));
		}

		ret.add(ret.get(0));
		return ret;
	}

}
