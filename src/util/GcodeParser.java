package util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class GcodeParser {
	private String filename;
	private FileInputStream stream;
	private BufferedReader reader;

	/* Open file */
	public GcodeParser(String filename) {
		try {
			stream = new FileInputStream(filename);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		reader = new BufferedReader(new InputStreamReader(stream));
	}

	/* Read next line from file */
	private String readline() {
		String strLine;
		try {
			strLine = reader.readLine();
		} catch (IOException e1) {
			e1.printStackTrace();
			reader.close();
			return null;
		}
		if (strLine == null) {
			// Close if read failed (last Line?)
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return strLine;
	}

	/* Return position of next valid line */
	public positions.Position3D getPosition() {
		String strLine;
		do { // Read until valid line (not empty, no comment)
			strLine = this.readline();
		} while (strLine != null && strLine.length() == 0 && strLine.contains(";"));
		if (strLine == null) {
			return null;
		}
		// Parse if valid
		positions.Position3D position = new positions.Position3D();
		String[] strCoordinates = strLine.split(" ");
		for (String i : strCoordinates) {
			if (i.startsWith("X")) {
				// Ignore first char and parse to double
				float posDouble = Float.parseFloat(i.substring(1));
				position.setX(posDouble);
			} else if (i.startsWith("Y")) {
				float posDouble = Float.parseFloat(i.substring(1));
				position.setY(posDouble);
			} else if (i.startsWith("Z")) {
				float posDouble = Float.parseFloat(i.substring(1));
				position.setZ(posDouble != 0); // Convert to bool (0=false)
			}
		}
		return position;
	}
}
