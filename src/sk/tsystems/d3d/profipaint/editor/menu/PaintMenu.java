package sk.tsystems.d3d.profipaint.editor.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class PaintMenu extends JMenuBar implements ActionListener {
	private static final long serialVersionUID = -212492803161495829L;

	private MenuClick onMenuClick;

	public PaintMenu() {

		JMenu mnFile = new JMenu("File");
		add(mnFile);

		JMenuItem mntmNew = new JMenuItem("New");
		mnFile.add(mntmNew);
		
		mnFile.addSeparator();

		JMenuItem mntmOpen = new JMenuItem("Open");
		mnFile.add(mntmOpen);

		JMenuItem mntmSave = new JMenuItem("Save");
		mnFile.add(mntmSave);
		mnFile.addSeparator();
		JMenuItem mntmExport = new JMenuItem("Export PNG");
		mnFile.add(mntmExport);

		menuItemPref(mntmOpen, MenuItem.OPEN);
		menuItemPref(mntmSave, MenuItem.SAVE);
		menuItemPref(mntmExport, MenuItem.EXPORT);
	}

	private void menuItemPref(JMenuItem item, MenuItem type) {
		item.setName(type.name());
		item.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		MenuItem clicked = MenuItem.valueOf(JMenuItem.class.cast(e.getSource()).getName());
		if (onMenuClick != null)
			onMenuClick.menuClick(clicked);
		else
			System.out.println(clicked);
	}

	public void setOnMenuClick(MenuClick onMenuClick) {
		this.onMenuClick = onMenuClick;
	}

}
