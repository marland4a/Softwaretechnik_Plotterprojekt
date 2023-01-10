package plott3r_V1_solved;

import java.util.Arrays;
import java.util.List;

public class Reifensatz implements IUebersetzung {
	private List<Reifen> reifen;

	public Reifensatz(Reifen... reifen) {
		this.reifen = Arrays.asList(reifen);
	}

	@Override
	public double getUebersetzungsverhaeltnis() {
		return this.reifen.get(this.reifen.size() - 1).getDurchmesser() / this.reifen.get(0).getDurchmesser();
	}

	@Override
	public boolean isAntriebsUmkehrung() {
		return this.reifen.size() % 2 == 0;
	}
}
