package sk.tsystems.d3d.profipaint.geometric;

import java.awt.Color;
import java.awt.Font;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Point2D.Double;
import java.io.Serializable;

public class Geometric implements Serializable {
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

	private transient Shape shape;
	
	public Geometric(GeoType type, Double position) {
		super();
		this.type = type;
		this.position = position;
		this.width = 50;
		this.height = 50;
		switch(this.type) {
	    	case OVAL:
	  	    	shape = new Ellipse2D.Double(this.getPosition().getX(), this.getPosition().getY(),this.getWidth(), this.getHeight());
	  		    break;
		    case RECTANGLE:
		  		shape =new Rectangle2D.Double(this.getPosition().getX(), this.getPosition().getY(),this.getWidth(), this.getHeight());
		  		break;
		  	case LINE:
		  		shape =new Line2D.Double(this.getPosition().getX(), this.getPosition().getY(),this.getPosition().getX()+this.getWidth(),this.getPosition().getY()+ this.getHeight());
		  		break;
		  	case TEXT:
		  		shape = null;
		  		break;
		  	default:
		  		shape = null;
		  		break;	
		}
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

	public Shape getShape() {
		return shape;
	}

}
