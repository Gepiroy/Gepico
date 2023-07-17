package Roads;

import java.util.ArrayList;
import java.util.List;

import WCutils.TextUtil;
import obj.Tile;

public class PathHead {
	public List<Tile> explored = new ArrayList<>();
	public List<Tile> avaliable = new ArrayList<>();
	
	public int price = 0;
	
	public Tile now;
	
	public PathHead(Tile now){
		this.now=now;
	}
	
	public PathHead(Tile now, PathHead h){
		this.now=now;
		for(Tile t:h.explored){
			explored.add(t);
			TextUtil.debug("Added to explored: "+t);
		}
		explored.add(h.now);
		TextUtil.debug("explored: "+explored);
		price = h.price;
	}
	
	void aval(Tile t){
		boolean can=true;
		for(Tile t2:explored){
			if(t2.equals(t)){
				can=false;
				break;
			}
		}
		if(can)for(Tile t2:avaliable){
			if(t2.equals(t)){
				can=false;
				break;
			}
		}
		
		if(can){
			avaliable.add(t);
			TextUtil.debug("aval.");
		}else{
			TextUtil.debug("can't aval...");
		}
		
	}
}
