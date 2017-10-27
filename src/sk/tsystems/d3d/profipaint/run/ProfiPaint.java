package sk.tsystems.d3d.profipaint.run;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileNameExtensionFilter;

import sk.tsystems.d3d.profipaint.editor.PaintPanel;
import sk.tsystems.d3d.profipaint.editor.core.DrawFace;
import sk.tsystems.d3d.profipaint.editor.core.DrawPanel;
import sk.tsystems.d3d.profipaint.editor.core.OnGeometricSelect;
import sk.tsystems.d3d.profipaint.editor.menu.MenuClick;
import sk.tsystems.d3d.profipaint.editor.menu.MenuItem;
import sk.tsystems.d3d.profipaint.editor.menu.PaintMenu;
import sk.tsystems.d3d.profipaint.filesupport.ExportPNG;
import sk.tsystems.d3d.profipaint.filesupport.Vector2File;
import sk.tsystems.d3d.profipaint.geometric.GeoType;
import sk.tsystems.d3d.profipaint.geometric.Geometric;
import sk.tsystems.d3d.profipaint.geometric.GeometricCointainer;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JToggleButton;

public class ProfiPaint extends JFrame implements MenuClick, OnGeometricSelect {
	private static final long serialVersionUID = 6639228443591986333L;

	private DrawFace df;
	PaintPanel paintPanel;
	private static final Color shapeColor = Color.BLACK;
	private static final Color backgroundColor = Color.WHITE;
	private Geometric selected;
	private JButton btnShapeColorFill;
	private JButton bntBackgroundColor;

	protected JToggleButton tglbtnColorizeonclick;

	public ProfiPaint() {
		super("Profi Paint");

		PaintMenu pm = new PaintMenu();
		setJMenuBar(pm);
		pm.setOnMenuClick(this);

		paintPanel = new PaintPanel();
		setContentPane(paintPanel);

		setSize(900, 600);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));

		JToolBar toolBar = new JToolBar();
		paintPanel.add(toolBar, BorderLayout.NORTH);

		JButton btnSelect = new JButton();
		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnSelect.setIcon(new ImageIcon(ProfiPaint.class.getResource("/Icons/SelectIcon.png")));
		toolBar.add(btnSelect);

		JButton btnRotateRight = new JButton("Rotate right");
		btnRotateRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		toolBar.add(btnRotateRight);

		JButton btnRotateLeft = new JButton("Rotate left");
		toolBar.add(btnRotateLeft);

		JLabel lblShapes = new JLabel("Shapes: ");
		toolBar.add(lblShapes);

		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(GeoType.values()));
		toolBar.add(comboBox);

		JLabel lblColor = new JLabel("Color: ");
		toolBar.add(lblColor);

		btnShapeColorFill = new JButton();
		btnShapeColorFill.setEnabled(false);
		btnShapeColorFill.setBackground(shapeColor);

		toolBar.add(btnShapeColorFill);

		btnShapeColorFill.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				paintColor(btnShapeColorFill);

			}
		});

		JLabel lblBackgroundColor = new JLabel("Background color:  ");
		toolBar.add(lblBackgroundColor);

		bntBackgroundColor = new JButton();
		bntBackgroundColor.setBackground(backgroundColor);

		toolBar.add(bntBackgroundColor);

		bntBackgroundColor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				paintColor(bntBackgroundColor);

			}
		});

		tglbtnColorizeonclick = new JToggleButton("ColorizeOnClick");
		tglbtnColorizeonclick.setEnabled(false);
		toolBar.add(tglbtnColorizeonclick);

		// ScrollPane sc = new ScrollPane();
		// sc.add(new DrawPanel(8000, 4500), BorderLayout.CENTER);
		DrawPanel drp = new DrawPanel(8000, 4500);
		paintPanel.add(drp, BorderLayout.CENTER);
		df = drp;
		df.setOnSelect(this);

		// paintPanel.add(sc, BorderLayout.CENTER);
		comboBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					DrawFace df = drp;
					df.addGeometric(GeoType.class.cast(e.getItem()));
				}
			}
		});

	}

	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new ProfiPaint().setVisible(true);
			}
		});
	}

	@Override
	public void menuClick(MenuItem clicked) {
		switch (clicked) {
		case EXIT:
			break;
		case OPEN:
		case SAVE:
			saveOpen(clicked);
			break;

		case EXPORT:
			exportPNG();
			break;

		default:
			throw new RuntimeException("Unimplemnted menu click " + clicked);
		}
	}

	private void exportPNG() {
		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter fifi = new FileNameExtensionFilter("*.png", "png");
		fileChooser.addChoosableFileFilter(fifi);
		fileChooser.setFileFilter(fifi);

		if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
			ExportPNG.exportPNG(fileChooser.getSelectedFile(), getCurrentContainer());
		}

	}

	private GeometricCointainer getCurrentContainer() {
		GeometricCointainer gc = new GeometricCointainer(paintPanel.getWidth(), paintPanel.getHeight(),
				df.getGeometrics());
		gc.setBgColor(paintPanel.getBackground());
		return gc;
	}

	private void saveOpen(MenuItem i) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("*.ser", "ser"));

		File file;

		if (MenuItem.SAVE.equals(i) && fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
			GeometricCointainer geoCon = getCurrentContainer();
			Vector2File.saveFile(geoCon, file.getAbsolutePath());
		}

		if (MenuItem.OPEN.equals(i) && fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();

			GeometricCointainer con = Vector2File.loadFile(file.getAbsolutePath());
			df.getGeometrics().clear();
			df.getGeometrics().addAll(con.getListoFGeometrics());
			df.setBackground(con.getBgColor());
			df.repaint();
		}

	}

	@Override
	public void onGeometricSelect(Geometric selected) {
		this.selected = selected;
		boolean selectionItemsEnable = selected != null;
		tglbtnColorizeonclick.setEnabled(selectionItemsEnable);

		btnShapeColorFill.setEnabled(selectionItemsEnable);
		bntBackgroundColor.setEnabled(!selectionItemsEnable);

		if (selectionItemsEnable) {

			if (tglbtnColorizeonclick.isSelected())
				selected.setFill(shapeColor);

		}

	}

	private void paintColor(JButton btn) {
		Color newColor = JColorChooser.showDialog(null, "Choose a color", shapeColor);
		if (selected == null) {
			df.setBackground(newColor);
		} else if (selected != null) {
			selected.setFill(newColor);
		}

		btn.setBackground(newColor);
		df.repaint();
	}

}
