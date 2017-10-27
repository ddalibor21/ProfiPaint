package sk.tsystems.d3d.profipaint.editor.core.popup;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class DrawPopup extends JPopupMenu implements ActionListener {
	private static final long serialVersionUID = 402772339882222536L;

	private DrawPopupClick onClick;

	public DrawPopup(DrawPopupClick onItemClick) throws HeadlessException {
		super();
		this.onClick = onItemClick;
		for (DrawPopupAction dpa : DrawPopupAction.values())
			mkPopMenu(dpa);
	}

	private void mkPopMenu(DrawPopupAction dp) {
		JMenuItem me = new JMenuItem(dp.getDescription());
		me.setName(dp.name());
		this.add(me);
		if (dp.hasSeparator())
			this.addSeparator();
		me.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JMenuItem item = JMenuItem.class.cast(e.getSource());
		DrawPopupAction action = DrawPopupAction.valueOf(item.getName());
		if (onClick != null)
			onClick.onItemClick(action);
		else
			System.out.println("Draw popup clicked " + action);
	}

}
