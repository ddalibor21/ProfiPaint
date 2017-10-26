package sk.tsystems.d3d.profipaint.geometric;

import java.io.Serializable;
import java.util.List;

public class GeometricCointainer implements Serializable {

	private int canvasWidth;
	private int canvasHeight;
	private List<Geometric> listoFGeometrics;

	public GeometricCointainer() {
		
	}
	

	public GeometricCointainer(int canvasWidth, int canvasHeight, List<Geometric> listoFGeometrics) {
		super();
		this.canvasWidth = canvasWidth;
		this.canvasHeight = canvasHeight;
		this.listoFGeometrics = listoFGeometrics;
	}



	public int getCanvasWidth() {
		return canvasWidth;
	}

	public int getCanvasHeight() {
		return canvasHeight;
	}

	public List<Geometric> getListoFGeometrics() {
		return listoFGeometrics;
	}

}
