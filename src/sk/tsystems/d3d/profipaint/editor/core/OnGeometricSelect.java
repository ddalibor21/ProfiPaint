package sk.tsystems.d3d.profipaint.editor.core;

import sk.tsystems.d3d.profipaint.geometric.Geometric;

public interface OnGeometricSelect {
	/**
	 * Event is called after selection change of geometric object in editor;
	 * @param selected {@link Geometric} if selected otherwise <code>null</code>
	 */
	void onGeometricSelect(Geometric selected); 
}
