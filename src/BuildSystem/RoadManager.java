package BuildSystem;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;

import BuildSegments.StorageImport;
import Roads.Vehicle;
import WCutils.GeomUtil;
import obj.Tile;

public class RoadManager {

	public static List<Tile> roadPoints = new ArrayList<>();

	public static List<Road> roads = new ArrayList<>();
	
	public static List<Vehicle> vehicles = new ArrayList<>();
	
	public static List<StorageImport> importers = new ArrayList<>();
	
	public void spawnVehicle(Location l){
		Horse h = (Horse) l.getWorld().spawnEntity(l, EntityType.HORSE);
		h.setAI(false);
		
	}
	
	static int tick = 0;
	public static void tick(){//20/sec.
		tick++;
		for(Vehicle v:vehicles){
			v.tick();
		}
		if(tick%2==0){
			for(Tile t:roadPoints){
				Location c = t.center();
				c.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, c, 1, 0.3, 0.1, 0.3, 0);
			}
			for(Road r:roads){
				GeomUtil.lineBetweenTwoPoints(r.from.center(), r.to.center(), Particle.FIREWORKS_SPARK, 0.5f);
			}
		}
	}
	
	public static List<Road> findRoadsByTile(Tile t){
		List<Road> ret = new ArrayList<>();
		for(Road r:roads){
			if(r.isInMe(t))ret.add(r);
		}
		return ret;
	}
}
