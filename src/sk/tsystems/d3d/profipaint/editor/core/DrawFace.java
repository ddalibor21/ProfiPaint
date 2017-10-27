package sk.tsystems.d3d.profipaint.editor.core;

import java.awt.Color;
import java.util.List;

import sk.tsystems.d3d.profipaint.geometric.GeoType;
import sk.tsystems.d3d.profipaint.geometric.Geometric;

public interface DrawFace {
	List<Geometric> getGeometrics();
	void addGeometric(GeoType type);

	void setOnSelect(OnGeometricSelect onSelect);
	void repaint();
	Geometric getSelected();	
	void setBackground(Color color);

}
