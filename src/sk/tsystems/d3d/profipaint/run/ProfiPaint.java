package sk.tsystems.d3d.profipaint.run;

import javax.swing.JFrame;

public class ProfiPaint extends JFrame {
	private static final long serialVersionUID = 6639228443591986333L;

	public ProfiPaint() {
		super("Profi Paint");

		setSize(900, 600);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new ProfiPaint().setVisible(true);
			}
		});
	}

}
