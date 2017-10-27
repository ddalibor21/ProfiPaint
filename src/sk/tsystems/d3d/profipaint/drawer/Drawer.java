package sk.tsystems.d3d.profipaint.drawer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

import javax.swing.JPanel;

import sk.tsystems.d3d.profipaint.geometric.Geometric;

public class Drawer {

	// tot by malo kreslit na grapgics2d
	public static void draw(List<Geometric> geometrics, Graphics2D g, JPanel panel, Color background) {
		g.setColor(background);
		g.fillRect(0, 0, panel.getWidth(), panel.getHeight());
		Stroke oldStroke = g.getStroke();
		Font oldFont = g.getFont();
		Shape shape;

		for (Geometric geo : geometrics) {
			switch (geo.getType()) {
			case RECTANGLE:
				shape = new Rectangle2D.Double(geo.getPosition().getX(), geo.getPosition().getY(), geo.getWidth(),
						geo.getHeight());
				if (geo.getFill() != null)
					g.setColor(geo.getFill());
				else
					g.setColor(Color.WHITE);
				g.fill(shape);

				if (geo.getBorder() != null)
					g.setColor(geo.getBorder());
				else
					g.setColor(Color.BLACK);
				oldStroke = g.getStroke();
				if (geo.getBorderSize() > 0)
					g.setStroke((Stroke) new BasicStroke((int) geo.getBorderSize()));
				g.draw(shape);
				g.setStroke(oldStroke);
				break;
			case OVAL:
				shape = new Ellipse2D.Double(geo.getPosition().getX(), geo.getPosition().getY(), geo.getWidth(),
						geo.getHeight());
				if (geo.getFill() != null)
					g.setColor(geo.getFill());
				else
					g.setColor(Color.WHITE);
				g.fill(shape);

				if (geo.getBorder() != null)
					g.setColor(geo.getBorder());
				else
					g.setColor(Color.BLACK);
				oldStroke = g.getStroke();
				if (geo.getBorderSize() > 0)
					g.setStroke((Stroke) new BasicStroke((int) geo.getBorderSize()));

				g.draw(shape);
				g.setStroke(oldStroke);
				break;
			case LINE:
				shape = new Line2D.Double(geo.getPosition().getX(), geo.getPosition().getY(),
						geo.getPosition().getX() + geo.getWidth(), geo.getPosition().getY() + geo.getHeight());
				/*
				 * if(geo.getFill()!=null) g.setColor(geo.getFill()); else
				 * g.setColor(Color.WHITE); g.fill(new Line2D.Double(geo.getPosition().getX(),
				 * geo.getPosition().getY(),geo.getWidth(), geo.getHeight()));
				 */
				if (geo.getBorder() != null)
					g.setColor(geo.getBorder());
				else
					g.setColor(Color.BLACK);
				oldStroke = g.getStroke();
				if (geo.getBorderSize() > 0)
					g.setStroke((Stroke) new BasicStroke((int) geo.getBorderSize()));
				g.draw(shape);
				g.setStroke(oldStroke);
				break;
			case TEXT:
				if (geo.getText() != null) {
					if (geo.getTextColor() != null)
						g.setColor(geo.getTextColor());
					else
						g.setColor(Color.BLACK);
					oldFont = g.getFont();
					if (geo.getFont() != null)
						g.setFont(geo.getFont());
					
					Rectangle2D dims = g.getFontMetrics().getStringBounds(geo.getText(), g);
					geo.setWidth(dims.getWidth());
					geo.setHeight(dims.getHeight());
					
					g.drawString(geo.getText(), (int) geo.getPosition().getX(), (int) (geo.getPosition().getY()+dims.getHeight()));

					g.setFont(oldFont);

				}
				break;
			default:
				System.out.println("Unknown type");
				break;

			}

		}
	}
}
