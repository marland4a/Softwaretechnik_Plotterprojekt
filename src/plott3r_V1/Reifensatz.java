package plott3r_V1;

import java.util.Arrays;
import java.util.List;

public class Reifensatz implements IUebersetzung {
	private List<Reifen> reifen;

	public Reifensatz(Reifen... reifen) {
		this.reifen = Arrays.asList(reifen);
	}

	@Override
	public double getUebersetzungsverhaeltnis() {
		// TODO: Implementierung
		return 0;
	}

	@Override
	public boolean isAntriebsUmkehrung() {
		// TODO: Implementierung
		return false;
	}
}
