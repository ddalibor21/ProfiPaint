package sk.tsystems.d3d.profipaint.editor.core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import sk.tsystems.d3d.profipaint.drawer.Drawer;
import sk.tsystems.d3d.profipaint.editor.core.popup.DrawPopup;
import sk.tsystems.d3d.profipaint.editor.core.popup.DrawPopupAction;
import sk.tsystems.d3d.profipaint.editor.core.popup.DrawPopupClick;
import sk.tsystems.d3d.profipaint.geometric.GeoType;
import sk.tsystems.d3d.profipaint.geometric.Geometric;

public class DrawPanel extends MouseInteractionPanel implements DrawFace, DrawPopupClick {
	private static final long serialVersionUID = 7161667598637528610L;

	private List<Geometric> geometrics;
	private Geometric newGeo;
	private Geometric selected;
	private double pdX;
	private double pdY;
	private Shape sizer;
	private OnGeometricSelect onSelect;
	private DrawMode mode;
	private DrawPopup popup;

	public DrawPanel(int width, int height) {
		super();
		setBackground(Color.WHITE);
		geometrics = new ArrayList<>();
		mode = DrawMode.NONE;
		setMinimumSize(new Dimension(width, height));
		popup = new DrawPopup(this);
	}

	@Override
	public List<Geometric> getGeometrics() {
		return geometrics;
	}

	@Override
	public void paint(Graphics g) {
		draw(Graphics2D.class.cast(g));
	}

	private void draw(Graphics2D g) {
		Drawer.draw(geometrics, g, this, getBackground());

		for(Geometric ge: geometrics) {
			Rectangle2D rectSel = new Rectangle2D.Double(ge.getPosition().getX(), ge.getPosition().getY(),
					ge.getWidth(), ge.getHeight());

			g.setColor(Color.PINK);
			g.draw(rectSel);
		}
		
		if (selected != null) {
			Rectangle2D rectSel = new Rectangle2D.Double(selected.getPosition().getX(), selected.getPosition().getY(),
					selected.getWidth(), selected.getHeight());

			g.setColor(getInvertColor(selected.getBorder()));
			g.draw(rectSel);

			sizer = new Rectangle2D.Double(selected.getPosition().getX() + selected.getWidth() - 3,
					selected.getPosition().getY() + selected.getHeight() - 3, 6, 6);
			g.fill(sizer);
		}
	}

	private Color getInvertColor(Color c) {
		if (c == null)
			c = Color.BLACK;
		float[] hsv = new float[3];
		Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), hsv);
		hsv[0] = (hsv[0] + 180) % 360;
		return Color.getHSBColor(hsv[0], hsv[1], hsv[2]);
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
		selectionChange(null);
		cancelNewGeo();

		if (type == null)
			return;

		newGeo = new Geometric(type, new Point2D.Double(10, 10));
		geometrics.add(newGeo);
		if(GeoType.TEXT.equals(type))
			newGeo.setText("Yupii");
		
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
		Geometric selected = null;
		for (Geometric ge : geometrics) {
			if (ge.getPosition().x <= e.getX() && ge.getPosition().x + ge.getWidth() >= e.getX()
					&& ge.getPosition().y <= e.getY() && ge.getPosition().y + ge.getHeight() >= e.getY()) {
				selected = ge;
			}
		}

		selectionChange(selected);

		if (selected != null && e.getButton() == MouseEvent.BUTTON3) {
			popup.show((JPanel) e.getSource(), e.getX(), e.getY());
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (DrawMode.RESIZE.equals(mode)) {
			selected.setWidth(e.getX() - selected.getPosition().getX());
			selected.setHeight(e.getY() - selected.getPosition().getY());
			repaint();
			return;
		}

		if (DrawMode.MOVE.equals(mode)) {
			selected.setPosition(new Point2D.Double(e.getX() - pdX, e.getY() - pdY));
			repaint();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() != MouseEvent.BUTTON1)
			return;

		if (selected != null) {
			if (sizer.contains(e.getPoint()))
				mode = DrawMode.RESIZE;
			else {
				mode = DrawMode.MOVE;
				pdX = e.getX() - selected.getPosition().getX();
				pdY = e.getY() - selected.getPosition().getY();
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mode = DrawMode.NONE;
	}

	private void selectionChange(Geometric selected) {
		if (this.selected != null && this.selected.equals(selected))
			return;

		this.selected = selected;

		repaint();

		if (onSelect != null)
			onSelect.onGeometricSelect(selected);
	}

	@Override
	public void setOnSelect(OnGeometricSelect onSelect) {
		this.onSelect = onSelect;
	}

	@Override
	public Geometric getSelected() {
		return selected;
	}

	@Override
	public void onItemClick(DrawPopupAction item) {
		switch (item) {
		case BRING_BACK:
			bring(-1);
			break;
		case BRING_FRONT:
			bring(1);
			break;
		case ERASE:
			geometrics.remove(selected);
			selectionChange(null);
			break;
		case SEND_BACK:
			bring(geometrics.indexOf(selected), 0);
			break;
		case SEND_FRONT:
			bring(geometrics.indexOf(selected), geometrics.size() - 1);
			break;
		default:
			throw new UnsupportedOperationException(item.name());
		}
	}

	private void bring(int idx) {
		int index = geometrics.indexOf(selected);
		bring(index, index + idx);
	}

	private void bring(int oldindex, int newindex) {
		if (newindex < 0 || newindex >= geometrics.size())
			return;

		geometrics.remove(oldindex);
		geometrics.add(newindex, selected);
		repaint();
	}

}
