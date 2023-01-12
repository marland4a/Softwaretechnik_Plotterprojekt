package plott3r_V1_solved;

import java.util.Arrays;
import java.util.List;

public class Zahnradsatz implements IUebersetzung {

	private List<Zahnrad> zahnraeder;

	// Für bessere Laufzeitperformance:
	// Übersetzungsverhältnis und Antriebsumkehrung bei Instantiierung berechnen
	private float uebersetzungsverhaeltnis;
	private boolean antriebsumkehrung;

	public Zahnradsatz(Zahnrad... zahnraeder) {
		this.zahnraeder = Arrays.asList(zahnraeder);
		this.uebersetzungsverhaeltnis = (float) this.zahnraeder.get(this.zahnraeder.size() - 1).getZaehne()
				/ this.zahnraeder.get(0).getZaehne();
		this.antriebsumkehrung = (this.zahnraeder.size() % 2) == 0;
	}

	@Override
	public float getUebersetzungsverhaeltnis() {
		// return (double)this.zahnraeder.get(this.zahnraeder.size() - 1).getZaehne() /
		// this.zahnraeder.get(0).getZaehne();
		return this.uebersetzungsverhaeltnis;
	}

	@Override
	public boolean isAntriebsUmkehrung() {
		// return this.zahnraeder.size() % 2 == 0;
		return this.antriebsumkehrung;
	}

}
