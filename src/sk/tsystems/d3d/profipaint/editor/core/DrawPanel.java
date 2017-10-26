package sk.tsystems.d3d.profipaint.editor.core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
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
		// super.paint(g);
		draw(Graphics2D.class.cast(g));
	}

	private void draw(Graphics2D g) {
		Drawer.draw(geometrics, g, this, Color.white);
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
		}
		
		// select geo
	}

}
