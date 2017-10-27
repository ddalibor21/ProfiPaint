package sk.tsystems.d3d.profipaint.filesupport;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import sk.tsystems.d3d.profipaint.drawer.Drawer;
import sk.tsystems.d3d.profipaint.geometric.GeometricCointainer;

public class ExportPNG {

	public static void exportPNG(File out, GeometricCointainer geom) {
		try {
			BufferedImage bi = new BufferedImage(geom.getCanvasWidth(), geom.getCanvasHeight(),
					BufferedImage.TYPE_INT_ARGB);
			Drawer.draw(geom.getListoFGeometrics(), bi.createGraphics(), geom.getCanvasWidth(), geom.getCanvasHeight(),
					geom.getBgColor());
			ImageIO.write(bi, "PNG", out);

		} catch (IOException ie) {
			ie.printStackTrace();
		}
	}

}
