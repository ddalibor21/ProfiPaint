package sk.tsystems.d3d.profipaint.editor.core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import sk.tsystems.d3d.profipaint.drawer.Drawer;
import sk.tsystems.d3d.profipaint.geometric.GeoType;
import sk.tsystems.d3d.profipaint.geometric.Geometric;

public class DrawPanel extends MouseInteractionPanel implements DrawFace {
	private static final long serialVersionUID = 7161667598637528610L;

	private List<Geometric> geometrics;
	private List<Geometric> selection;
	private Geometric newGeo;
	private Geometric selected;
	private double pdX;
	private double pdY;
	private Shape sizer;

	public DrawPanel(int width, int height) {
		super();
		geometrics = new ArrayList<>();
		selection = new ArrayList<>();
		setMinimumSize(new Dimension(width, height));
	}

	@Override
	public List<Geometric> getGeometrics() {
		return geometrics;
	}

	@Override
	public List<Geometric> getSelection() {
		return selection;
	}

	@Override
	public void paint(Graphics g) {
		draw(Graphics2D.class.cast(g));
	}

	private void draw(Graphics2D g) {
		Drawer.draw(geometrics, g, this, Color.white);
		if (selected != null) {
			Rectangle2D rectSel = new Rectangle2D.Double(selected.getPosition().getX(), selected.getPosition().getY(),
					selected.getWidth(), selected.getHeight());
			g.setColor(Color.red);
			g.draw(rectSel);
			sizer = new Rectangle2D.Double(selected.getPosition().getX() + selected.getWidth() - 3,
					selected.getPosition().getY() + selected.getHeight() - 3, 6, 6);

			g.fill(sizer);
		}
	}

	private void cancelNewGeo() {
		if (isNewGeo())
			geometrics.remove(newGeo);

		newGeoAdded();
	}

	private void newGeoAdded() {
		newGeo = null;
	}

	private boolean isNewGeo() {
		return newGeo != null;
	}

	@Override
	public void addGeometric(GeoType type) {
		if (type == null) {
			cancelNewGeo();
			return;
		}

		newGeo = new Geometric(type, new Point2D.Double(10, 10));
		geometrics.add(newGeo);
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		Point2D.Double pt = new Point2D.Double(e.getX(), e.getY());
		if (isNewGeo()) {
			newGeo.setPosition(pt);
			repaint();
			return;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// add new geo into paint
		if (isNewGeo() && e.getClickCount() == 1) {
			if (e.getButton() == MouseEvent.BUTTON3)
				cancelNewGeo();

			newGeoAdded();

			repaint();
			return;
		}

		// select geo
		selected = null;
		for (Geometric ge : geometrics) {
			if (ge.getPosition().x <= e.getX() && ge.getPosition().x + ge.getWidth() >= e.getX()
					&& ge.getPosition().y <= e.getY() && ge.getPosition().y + ge.getHeight() >= e.getY()) {
				selected = ge;
				repaint();
				break;
			}
		}

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if(selected!=null && sizer.contains(e.getPoint())) {
			selected.setWidth(e.getX()-selected.getPosition().getX());
			selected.setHeight(e.getY()-selected.getPosition().getY());
			repaint();
			return;
		}
		
		if (selected != null) {
			if (pdX < 0) {
				pdX = e.getX() - selected.getPosition().getX();
				pdY = e.getY() - selected.getPosition().getY();
			}
			selected.setPosition(new Point2D.Double(e.getX() - pdX, e.getY() - pdY));
			repaint();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		pdX = -1;
		pdY = -1;
	}

	private Point2D.Double p2p(Point p) {
		return new Point2D.Double(p.getX(), p.getY());
	}

}
