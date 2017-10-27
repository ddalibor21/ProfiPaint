package sk.tsystems.d3d.profipaint.editor.core.popup;

public enum DrawPopupAction {
	BRING_FRONT("Bring to front"), BRING_BACK("Bring to back",
			true), SEND_FRONT("Send to front"), SEND_BACK("Send to back", true), DUPLICATE("Duplicate", true), ERASE("Erase element");

	private String description;
	private boolean sep;

	private DrawPopupAction(String description, boolean addSeparator) {
		this.description = description;
		this.sep = addSeparator;
	}

	private DrawPopupAction(String description) {
		this(description, false);
	}

	public String getDescription() {
		return description;
	}

	public boolean hasSeparator() {
		return sep;
	}

}
