package util;

import positions.Position2D;

/**
 * Zur Visualisierung: https://www.matheretter.de/rechner/trigonometrie
 * 
 * @author phitschi
 *
 */
public class Einheitskreis {

	/**
	 * F�hrt Berechnungen �ber den Einheitskreis aus. Hierbei wird von einem
	 * Startwinkel von 0� ausgegangen.Es ist darauf zu achten, dass ein positiver
	 * Winkel eine Drehung im Gegenuhrzeigersinn betrachtet.
	 * 
	 * @param winkelInGradImGegenuhrzeigersinn
	 * @param radius
	 * @return
	 */
	public static Position2D berechnePositionAufKreis(double winkelInGradImGegenuhrzeigersinn, float radius) {
		float x = (float) Math.cos(Math.toRadians(winkelInGradImGegenuhrzeigersinn)) * radius;
		float y = (float) Math.sin(Math.toRadians(winkelInGradImGegenuhrzeigersinn)) * radius;
		return new Position2D(x, y);
	}

	/**
	 * F�hrt Berechnungen �ber den Einheitskreis aus. Hier wird von einer gegebenen
	 * Position im Einheitskreis ausgegangen.Es ist darauf zu achten, dass ein
	 * positiver Winkel eine Drehung im Gegenuhrzeigersinn betrachtet.
	 * 
	 * @param winkelInGradImGegenuhrzeigersinn
	 * @param radius
	 * @return
	 */
	public static Position2D berechnePositionAufKreis(Position2D positionImEinheitskreis,
			float drehungInGradImGegenuhrzeigersinn) {
		final float radius = (float) MathHelper.getHypothenuse(new Position2D(0, 0), positionImEinheitskreis);
		double gradDerPositionImEinheitskreis = Math.toDegrees(Math.acos(positionImEinheitskreis.getX() / radius));
		if (positionImEinheitskreis.getY() < 0)
			gradDerPositionImEinheitskreis = 360 - gradDerPositionImEinheitskreis;
		final double gradAbsolut = gradDerPositionImEinheitskreis + drehungInGradImGegenuhrzeigersinn;
		float xBerechnet = (float) Math.cos(Math.toRadians(gradAbsolut)) * radius;
		float yBerechnet = (float) Math.sin(Math.toRadians(gradAbsolut)) * radius;
		return new Position2D(xBerechnet, yBerechnet);
	}

}
