package Buildings;

import java.util.HashMap;

import BuildSystem.WorkBuilding;
import obj.Thing;
import obj.Tile;

public class Port extends WorkBuilding{

	public Port(Tile startTile, WorkBuilding f) {
		super(startTile, f);
	}
	
	public HashMap<Thing, Integer> storage = new HashMap<>();
	
	public void add(Thing t, int am){
		if(storage.containsKey(t))storage.replace(t, storage.get(t)+am);
		else storage.put(t, am);
	}

	@Override
	public void tickDay() {
		// TODO Auto-generated method stub
		
	}
}
