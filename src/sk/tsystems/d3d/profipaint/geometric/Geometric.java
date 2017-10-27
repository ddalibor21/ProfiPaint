package sk.tsystems.d3d.profipaint.geometric;

import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class Geometric implements Serializable, Cloneable {
	private static final long serialVersionUID = -7387597717386063055L;

	private GeoType type;
	private Point2D.Double position;
	private double width;
	private double height;
	private Color fill;
	private Color border;
	private Color textColor;
	private double borderSize;
	private String text;
	private Font font;

	public Geometric(GeoType type, Double position) {
		super();
		this.type = type;
		this.position = position;
		this.width = 50;
		this.height = 50;

	}

	public GeoType getType() {
		return type;
	}

	public void setType(GeoType type) {
		this.type = type;
	}

	public Point2D.Double getPosition() {
		return position;
	}

	public void setPosition(Point2D.Double position) {
		this.position = position;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public Color getFill() {
		return fill;
	}

	public void setFill(Color fill) {
		this.fill = fill;
	}

	public Color getBorder() {
		return border;
	}

	public void setBorder(Color border) {
		this.border = border;
	}

	public double getBorderSize() {
		return borderSize;
	}

	public void setBorderSize(double borderSize) {
		this.borderSize = borderSize;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public Color getTextColor() {
		return textColor;
	}

	public void setTextColor(Color textColor) {
		this.textColor = textColor;
	}

	@Override
	public Object clone() {
		Geometric cg = new Geometric(type, position);

		for (Field f : Geometric.class.getDeclaredFields()) {
			if ((f.getModifiers() & Modifier.FINAL) != 0)
				continue;

			f.setAccessible(true);
			try {
				Object val = f.get(this);
				f.set(cg, val);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new RuntimeException(e);
			}

		}

		return cg;
	}

}
