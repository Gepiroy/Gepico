package Roads;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.LivingEntity;

import obj.Tile;

public class Vehicle {
	
	public double speed=1;//tiles per sec
	
	public Tile target;
	public LivingEntity en;
	
	public OnePath path;
	
	final Tile start;
	
	public Vehicle(Tile start, Tile target){
		this.target=target;
		path = new OnePath(start, target);
		this.start=start;
	}
	
	public void spawnHorse(){
		Location l = start.center();
		Horse h = (Horse) l.getWorld().spawnEntity(l, EntityType.HORSE);
		h.setAI(false);
		en=h;
	}
	
	public void path(Tile target){
		path = new OnePath(new Tile(en.getLocation()), target);
	}
	
	public void tick(){
		Location loc = en.getLocation();
		path.move(en.getLocation(), 0.3);
		en.teleport(loc);
	}
	
	public void reached(){
		//Other vehicles will do sg with this.
	}
	
	public void kill(){
		en.remove();
	}
}
