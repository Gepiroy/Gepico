package BuildSystem;

import BuildSegments.WorkPlace;
import obj.Conf;
import obj.Tile;

public abstract class WorkBuilding extends Building{
	public WorkPlace work;
	
	public WorkBuilding(String id, Tile startTile, BuildInfo binf, int slots, int edu, int payment) {
		super(id, startTile, binf);
		this.work=new WorkPlace(this, slots, edu, payment);
		
	}
	
	public WorkBuilding(Tile startTile, WorkBuilding f){
		super(startTile, f);
		this.work=new WorkPlace(this, f.work.slots, f.work.education, f.work.payment);
	}
	
	public WorkBuilding(Conf conf, String where){
		super(conf, where);
	}
	
	@Override
	public void save(Conf conf, String where){
		super.save(conf, where);
		
	}
}
