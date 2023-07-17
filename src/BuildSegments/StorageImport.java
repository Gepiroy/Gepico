package BuildSegments;


import BuildSystem.RoadManager;
import BuildSystem.StorageSlot;
import obj.Tile;

public class StorageImport {
	public Tile t;
	public StorageSlot assign;
	
	public StorageImport(Tile t, StorageSlot assign){
		this.t=t;
		this.assign=assign;
		RoadManager.importers.add(this);
	}
}
