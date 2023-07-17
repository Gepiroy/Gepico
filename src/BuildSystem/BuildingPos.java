package BuildSystem;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Particle;

import WCutils.TextUtil;
import WCutils.WEutil;
import obj.Conf;
import obj.Tile;
import obj.xz;

public class BuildingPos {
	public xz zero = new xz(-1, 0);
	public xz ds = new xz(3, 2);
	public int rot=0;
	
	
	public BuildingPos(xz zero, xz ds){
		this.zero = zero;
		this.ds = ds;
	}
	
	public void rotate(){
		xz[] dots = new xz[4];
		dots[0] = new xz(zero).rot();
		dots[1] = new xz(zero).add(ds.x-1, 0).rot();
		dots[2] = new xz(zero).add(ds.x-1, ds.z-1).rot();
		dots[3] = new xz(zero).add(0, ds.z-1).rot();
		zero = new xz(10, 10);
		for(xz dot:dots){
			if(dot.x<=zero.x&&dot.z<=zero.z)zero=dot;
		}
		xz olds = new xz(ds);
		ds.x=olds.z;
		ds.z=olds.x;
		rot++;
		if(rot>3)rot=0;
	}
	
	public void rotTo(int rot){
		while(this.rot!=rot){
			rotate();
			TextUtil.sdebug("rotated+1 ("+this.rot+")");
		}
	}
	
	public BuildingPos(Conf conf, String where){
		zero = new xz(conf, where+".zero");
		ds = new xz(conf, where+".ds");
		rot = conf.getInt(where+".rot");
	}
	
	public void save(Conf conf, String where){
		zero.save(conf, where+".zero");
		ds.save(conf, where+".ds");
		conf.set(where+".rot", rot);
	}
	
	public List<Tile> everyMyTile(Tile c){
		List<Tile> ret = new ArrayList<>();
		for(int x=c.x+zero.x;x<c.x+zero.x+ds.x;x++){
			for(int z=c.z+zero.z;z<c.z+zero.z+ds.z;z++){
				Tile t2 = new Tile(x, z);
				ret.add(t2);
			}
		}
		return ret;
	}
	
	public boolean canBuild(Tile tc){
		List<Tile> tiles = everyMyTile(tc);
		for(Tile t:tiles){
			if(BuildManager.findBuildingOnTile(t)!=null){
				TextUtil.sdebug("can't build (problems with tile ["+(t.x-tc.x)+" "+(t.z-tc.z)+"])");
				return false;
			}
		}
		return true;
	}
	
	public void build(Tile tc, String schem){
		Location c = tc.center();
		c.getWorld().spawnParticle(Particle.CLOUD, c, 100, 2, 2, 2, 0.05);
		WEutil.loadSchematic(c, schem, rot);
	}
}
