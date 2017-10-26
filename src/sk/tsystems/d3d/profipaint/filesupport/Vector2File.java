package sk.tsystems.d3d.profipaint.filesupport;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import sk.tsystems.d3d.profipaint.geometric.GeometricCointainer;

public class Vector2File {

	private static final String path = "C:\\Users\\student\\eclipse-workspace\\save.ser\\";

	public void saveFile(GeometricCointainer geoCon) {

		try {
			FileOutputStream fileOut = new FileOutputStream(path);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(geoCon);
			out.close();
			fileOut.close();

		} catch (IOException i) {
			i.printStackTrace();
		}

	}

	public GeometricCointainer loadFile() throws MyException {
		GeometricCointainer geoCon = null;

		try {
			FileInputStream fileIn = new FileInputStream(path);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			geoCon = (GeometricCointainer) in.readObject();
			in.close();
			fileIn.close();

		} catch (IOException i) {
			i.printStackTrace();
			throw new MyException("Deserializing failed", i);
		} catch (ClassNotFoundException c) {
			c.printStackTrace();

		}

		return geoCon;

	}

}
