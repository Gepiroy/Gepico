package BuildSystem;

import obj.Conf;
import obj.Tile;
import obj.xz;

public abstract class Building {
	public Tile startTile;
	public BuildInfo binf;
	public final String id;
	public int tickPriority=0;
	
	public Building(String id, Tile startTile, BuildInfo binf){
		this.id=id;
		this.startTile = startTile;
		this.binf=binf;
	}
	
	public Building(Conf conf, String where){
		this.id=conf.getString(where+".id");
		startTile = new Tile(conf, where+".startTile");
		binf=new BuildInfo(BuildManager.findById(id).binf);
		binf.pos.rot=conf.getInt(where+".rot");
	}
	
	public Building(Tile startTile, Building b){
		this.id=b.id;
		this.startTile = startTile;
		this.binf=b.binf;
	}
	
	public abstract void tickDay();
	
	public Building priority(int priority){
		this.tickPriority=priority;
		return this;
	}
	
	public void save(Conf conf, String where){
		startTile.save(conf, where+".startTile");
		conf.set(where+".id", id);
		conf.set(where+".rot", binf.pos.rot);
	}
	
	public xz startPos(){
		return new xz(startTile.x+binf.pos.zero.x, startTile.z+binf.pos.zero.z);
	}
}
