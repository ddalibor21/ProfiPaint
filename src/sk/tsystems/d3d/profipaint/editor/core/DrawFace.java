package sk.tsystems.d3d.profipaint.editor.core;

import java.util.List;

import sk.tsystems.d3d.profipaint.geometric.GeoType;
import sk.tsystems.d3d.profipaint.geometric.Geometric;

public interface DrawFace {
	List<Geometric> getGeometrics();
	List<Geometric> getSelection();
	void addGeometric(GeoType type);
	
	
	
}
