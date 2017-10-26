package sk.tsystems.d3d.profipaint.run;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.ScrollPane;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import sk.tsystems.d3d.profipaint.editor.PaintMenu;
import sk.tsystems.d3d.profipaint.editor.PaintPanel;
import sk.tsystems.d3d.profipaint.editor.core.DrawFace;
import sk.tsystems.d3d.profipaint.editor.core.DrawPanel;
import sk.tsystems.d3d.profipaint.geometric.GeoType;

import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.DefaultComboBoxModel;

public class ProfiPaint extends JFrame {
	private static final long serialVersionUID = 6639228443591986333L;

	public ProfiPaint() {
		super("Profi Paint");

		setJMenuBar(new PaintMenu());

		PaintPanel paintPanel = new PaintPanel();
		setContentPane(paintPanel);

		setSize(900, 600);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JToolBar toolBar = new JToolBar();
		paintPanel.add(toolBar, BorderLayout.NORTH);
		
		JButton btnSelect = new JButton("Select");
		toolBar.add(btnSelect);
		
		JButton btnRotateRight = new JButton("Rotate right");
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
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"Black", "White", "Red", "Green", "Blue", "Yellow", "Gray"}));
		toolBar.add(comboBox_1);
		
		JLabel lblBackgroundColor = new JLabel("Background color:  ");
		toolBar.add(lblBackgroundColor);
		
		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setModel(new DefaultComboBoxModel(new String[] {"Black", "White", "Red", "Green", "Blue", "Yellow", "Gray"}));
		toolBar.add(comboBox_2);
		
		//ScrollPane sc = new ScrollPane();
		//sc.add(new DrawPanel(8000, 4500), BorderLayout.CENTER);
		DrawPanel drp = new DrawPanel(8000, 4500); 
		paintPanel.add(drp, BorderLayout.CENTER);
		//paintPanel.add(sc, BorderLayout.CENTER);
		comboBox.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				DrawFace df = drp;
				df.addGeometric(GeoType.class.cast(e.getItem()));
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

}
