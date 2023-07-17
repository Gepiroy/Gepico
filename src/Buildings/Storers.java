package Buildings;

import BuildSystem.BuildInfo;
import BuildSystem.WorkBuilding;
import obj.Tile;

public class Storers extends WorkBuilding{

	public Storers(String id, Tile startTile, BuildInfo binf) {
		super(id, startTile, binf, 0, 0, 0);
	}
	
	public void checkAndRun(){
		/*Storer best = recheck(storage.storageTile(this));
		if(best!=null){
			best.spawnHorse();
			RoadManager.vehicles.add(best);
			best.targetBuilding.phantom.update();
			best.dropBuilding.phantom.update();
		}else{
			//...
		}*/
	}
	
	public static void recheck(Tile from){
		/*List<Storer> avaliableStorers = new ArrayList<>();
		for(WorkBuilding b:BuildManager.workbuildings()){
			OnePath firstPath = new OnePath(from, b.storage.storageTile(b));
			for(StorageSlot s:b.phantom.storage.wantsToExport()){
				for(WorkBuilding imp:BuildManager.findActualImporters(s.thing)){
					OnePath secondPath = new OnePath(b.storage.storageTile(b), imp.storage.storageTile(imp));
					Storer sr = new Storer(from, b, imp, new StorageSlot(s.thing, 500));
					sr.length=firstPath.length+secondPath.length;
					avaliableStorers.add(sr);
				}
			}
		}
		Storer best = null;
		int min = 99999;
		for(Storer sr:avaliableStorers){
			if(sr.length<min){
				best = sr;
				min=sr.length;
			}
		}
		return best;*/
	}

	@Override
	public void tickDay() {
		// TODO Auto-generated method stub
		
	} 
	
}
