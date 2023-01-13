package plott3r_V1_solved;

import java.util.Arrays;
import java.util.List;

public class Reifensatz implements IUebersetzung {
	private List<Reifen> reifen;

	// Für bessere Laufzeitperformance:
	// Übersetzungsverhältnis und Antriebsumkehrung bei Instantiierung berechnen
	private float uebersetzungsverhaeltnis;
	private boolean antriebsumkehrung;

	public Reifensatz(Reifen... reifen) {
		this.reifen = Arrays.asList(reifen);
		this.uebersetzungsverhaeltnis = (float) (this.reifen.get(this.reifen.size() - 1).getDurchmesser()
				/ this.reifen.get(0).getDurchmesser());
		this.antriebsumkehrung = (this.reifen.size() % 2) == 0;
	}

	@Override
	public float getUebersetzungsverhaeltnis() {
		// return this.reifen.get(this.reifen.size() - 1).getDurchmesser() /
		// this.reifen.get(0).getDurchmesser();
		return this.uebersetzungsverhaeltnis;
	}

	@Override
	public boolean isAntriebsUmkehrung() {
		// return this.reifen.size() % 2 == 0;
		return this.antriebsumkehrung;
	}
}
