package positions;

/**
 * Beschreibt eine Position in mm im Koordinatensystem.
 */
public class Position3D extends Position2D {

	private boolean z;

	// NEU: Add
	public Position3D() {
		super();
		this.z = false;
	}

	public Position3D(float x, float y, boolean z) {
		super(x, y);
		this.z = z;
	}

	public Position3D(Position2D position2d, boolean z) {
		this(position2d.getX(), position2d.getY(), z);
	}

	public boolean isZ() {
		return z;
	}

	// NEU: Was missing
	public void setZ(boolean z) {
		this.z = z;
	}
	
	// NEU: Additions"operator" hinzufügen
	public Position3D add(Position3D pos) {
		//this.setX(x + pos.getX());
		//this.setY(y + pos.getY());
		boolean z = false;
		if(this.isZ() || pos.isZ()) {
			z = true;
		}
		pos = new Position3D(this.getX() + pos.getX(), this.getY() + pos.getY(), z); 
		return pos;
	}
}
