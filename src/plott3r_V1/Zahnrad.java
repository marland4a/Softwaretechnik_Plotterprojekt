package plott3r_V1;

public class Zahnrad {

	public static final int ANZAHL_ZAEHNE_GROSS = 36;
	public static final int ANZAHL_ZAEHNE_KLEIN = 12;
	public static final int ANZAHL_ZAEHNE_MITTEL = 24;

	private int zaehne;

	public Zahnrad(int zaehne) {
		this.setZaehne(zaehne);
	}

	public int getZaehne() {
		return zaehne;
	}

	public void setZaehne(int zaehne) {
		if (zaehne < 1)
			throw new RuntimeException("Die Anzahl der Zähne muss größer als 1 sein");
		this.zaehne = zaehne;
	}

}
