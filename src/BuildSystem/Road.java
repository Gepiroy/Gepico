package BuildSystem;

import java.util.ArrayList;
import java.util.List;

import obj.Tile;

public class Road {
	public final Tile from, to;
	public final boolean xr;
	public boolean locked = false;
	
	public Road(Tile p1, Tile p2){
		int dx=p2.x-p1.x;
		int dz=p2.z-p1.z;
		xr=dx!=0;
		if(dx>=0&&dz>=0){
			from=p1;
			to=p2;
		}else{
			to=p1;
			from=p2;
		}
		
	}
	
	public List<Tile> everyMyTile(){
		List<Tile> ret = new ArrayList<>();
		if(xr)for(int x=from.x;x<=to.x;x++){
			ret.add(new Tile(x, from.z));
		}else for(int z=from.z;z<=to.z;z++){
			ret.add(new Tile(from.x, z));
		}
		//TextUtil.debug("everyTile: "+ret);
		return ret;
	}
	
	public boolean isInMe(Tile t){
		if(xr)return t.x>=from.x&&t.x<=to.x&&t.z==to.z;
		else return t.z>=from.z&&t.z<=to.z&&t.x==to.x;
	}
	
	public boolean isMyEnd(Tile t){
		return t.equals(from)||t.equals(to);
	}
	
	public Tile crossing(Road r){
		Tile c = new Tile(0, 0);
		if(!xr){
			c.x=from.x;
			c.z=r.from.z;
		}else{
			c.z=from.z;
			c.x=r.from.x;
		}
		if(isInMe(c)&&r.isInMe(c))return c;
		return null;
	}
	
	/**
	 * Длина пути включая конец, но не включая начало.
	 * @return
	 */
	public int length(){
		return to.x-from.x+to.z-from.z;
	}
	
	@Override
	public String toString(){
		return "["+from+"-"+to+"]";
	}
}
