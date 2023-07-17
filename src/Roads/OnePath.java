package Roads;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Particle;

import BuildSystem.Road;
import BuildSystem.RoadManager;
import WCutils.GeomUtil;
import WCutils.TextUtil;
import obj.Tile;

public class OnePath {
	
	public Tile target;
	
	public List<Tile> path = new ArrayList<>();
	
	List<PathHead> heads = new ArrayList<>();
	List<PathHead> fins = new ArrayList<>();
	List<Road> endroads = new ArrayList<>();
	
	public int length;
	
	public OnePath(Tile start, Tile target){
		this.target=target;
		for(Road r:RoadManager.roads){
			if(r.isInMe(target)){
				endroads.add(r);
			}
		}
		heads.add(new PathHead(start));
		path();
	}
	
	void path(){
		TextUtil.debug("Start search path. "+heads.get(0).now);
		List<PathHead> newHeads = new ArrayList<>();
		int changed=1;
		int changes=0;
		while(changed>0){
			changes++;
			if(changes>30){
				TextUtil.debug("&cBROKEN! 30 REPEATS!");
				break;
			}
			TextUtil.debug("cycle...");
			changed=0;
			for(PathHead head:new ArrayList<>(heads)){
				for(Road r:RoadManager.roads){
					boolean yes=false;
					for(Tile t:r.everyMyTile()){
						if(t.equals(head.now)){
							yes=true;
						}
					}
					if(yes){
						TextUtil.debug("head found self road.");
						head.aval(r.from);
						head.aval(r.to);
					}
				}
				for(Tile t:head.avaliable){
					changed++;
					PathHead nh = new PathHead(t, head);
					nh.price+=head.now.linearDist(t);
					boolean skip=false;
					for(Road r:RoadManager.findRoadsByTile(t)){
						TextUtil.debug("roadByTile "+t+": "+r);
						if(endroads.contains(r)){
							TextUtil.debug("IT'S ENDROAD! "+endroads);
							nh.price+=target.linearDist(t);
							nh.explored.add(t);
							nh.explored.add(target);
							fins.add(nh);
							skip=true;
							TextUtil.debug("+1 fin.!");
							break;
						}
					}
					if(!skip)newHeads.add(nh);
				}
				heads=new ArrayList<>(newHeads);
				newHeads.clear();
			}
		}
		PathHead winner=null;
		int poorest=99999;
		for(PathHead h:fins){
			if(h.price<poorest){
				poorest = h.price;
				winner = h;
			}
		}
		if(winner==null){
			TextUtil.sdebug("&cCan't find any path! (Vehicle)");
		}else{
			path=winner.explored;
			length=winner.price;
		}
		TextUtil.debug("PATH = "+path);
	}
	
	Tile findMyPathPiece(Tile t){
		for(int i=0;i<path.size()-1;i++){
			Road road = new Road(path.get(i), path.get(i+1));
			if(road.isInMe(t))return path.get(i+1);
		}
		return path.get(0);
	}
	
	public void move(Location loc, double multiply){
		if(path.size()==0)return;
		Location lt=findMyPathPiece(new Tile(loc)).center();
		loc.add(GeomUtil.direction(lt.clone().add(0.01, 0, 0.01), loc).multiply(multiply));
		lt.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, lt, 5, 0.1, 1, 0.1, 0.05);
		if(loc.distance(lt)<=1.5){
			loc=lt;
			lt=null;
			TextUtil.debug("depatched, size="+path.size());
		}
	}
}
