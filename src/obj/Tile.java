package obj;

import java.util.Comparator;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import Gepico.main;

public class Tile {
	public static World w;
	
	public static void init(){
		w=Bukkit.getWorld("world");
	}
	
	public static Comparator<Tile> compTiles = (t1, t2) -> {
		if(t1.x<t2.x||t1.z<t2.z)return -1;
		if(t1.x==t2.x&&t1.z==t2.z)return 0;
		return 1;
	};
	
	public int x, z;
	public Tile(int x, int z){
		this.x=x;
		this.z=z;
	}
	public Tile(Location l){
		this.x=(int) Math.round(l.getX()/main.tileSize-0.5);
		this.z=(int) Math.round(l.getZ()/main.tileSize-0.5);
	}
	public Tile(Conf conf, String where){
		this.x=conf.getInt(where+".x");
		this.z=conf.getInt(where+".z");
	}
	
	public Location center(){
		return new Location(w, x*main.tileSize+main.tileSize*0.5, main.Y, z*main.tileSize+main.tileSize*0.5);
	}
	
	public void save(Conf conf, String where){
		conf.set(where+".x", x);
		conf.set(where+".z", z);
	}
	
	public double dist(Tile t){
		return Math.hypot(x-t.x, z-t.z);
	}
	/**
	 * Distance for pathfinding. Includes oppopoint, excludes selfpoint.
	 * @param t
	 * @return
	 */
	public int linearDist(Tile t){
		return Math.abs(x-t.x+z-t.z);
	}
	
	@Override
	public String toString(){
		return "&f{&e"+x+" "+z+"&f}";
	}
	
	@Override
	public boolean equals(Object o){
		if(o instanceof Tile){
			Tile t=(Tile) o;
			return (t.x==x&&t.z==z);
		}else return false;
	}
}
