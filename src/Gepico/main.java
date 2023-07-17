package Gepico;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import BuildSystem.BuildManager;
import BuildSystem.Buildings;
import BuildSystem.RoadManager;
import WCutils.GepUtil;
import WCutils.WEutil;
import cmd.CmdManager;
import obj.PlayerInfo;
import obj.ThingBuffer;
import obj.Tile;

public class main extends JavaPlugin{
	public static main instance;
	public static Random r = new Random();
	CmdManager cm;
	
	public static ThingBuffer buff = new ThingBuffer();
	
	public void onEnable(){
		instance = this;
		cm = new CmdManager();
		cm.setup();
		WEutil.setup();
		Tile.init();
		Buildings.init();
		BuildManager.init();
		PawnManager.init();
		Bukkit.getPluginManager().registerEvents(new Events(), this);
		Bukkit.getPluginManager().registerEvents(new GUI(), this);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){
			int secRate=0;
			public void run(){
				secRate++;
				if(secRate>=20){
					secRate=0;
					for(Player p:Bukkit.getOnlinePlayers()){
						PlayerInfo pi=Events.plist.get(p.getName());
						for(String st:new ArrayList<>(pi.timers.keySet())){
							if(GepUtil.HashMapReplacer(pi.timers, st, -1, true, false)){
								
							}
						}
						int y = p.getLocation().getBlockY();
						if(y<=15){
							p.setFlySpeed(0.1f);
						}else if(y>15&&y<=30){
							p.setFlySpeed(0.15f);
						}else{
							p.setFlySpeed(0.2f);
						}
					}
				}
				PawnManager.tick();
				RoadManager.tick();
				if(secRate%4==0){
					for(Player p:Bukkit.getOnlinePlayers()){
						Tile t = lookAt(p);
						if(t==null)continue;
						PlayerInfo pi = Events.plist.get(p.getName());
						if(pi.building==null)drawTiles(p, t.center().add(0, 0.15, 0), Particle.CRIT, 1, 1);
						else drawTiles(p, t.center().add(0, 0.15, 0), Particle.CRIT_MAGIC, 1, 1);
					}
				}
			}
		}, 1, 1);
		for(Player p:Bukkit.getOnlinePlayers()){
			Events.doJoin(p);
		}
	}
	public void onDisable(){
		for(Player p:Bukkit.getOnlinePlayers()){
			Events.doLeave(p);
		}
		BuildManager.saveBuildings();
		PawnManager.save();
	}
	
	public static final int Y=4;
	public static final int tileSize=7;
	
	public Tile lookAt(Player p){
		Location loc = p.getEyeLocation().clone();
		Vector vec = loc.toVector();
		Vector v1 = loc.getDirection().normalize().multiply(0.75);
		int l=0;
		int dist=50;
		vec.add(v1);
		for (; l < dist; vec.add(v1)) {
	        l += 1;
	        if(vec.getY()<=Y){
	        	return new Tile(vec.toLocation(p.getWorld()));
	        }
	    }
		return null;
	}
	
	public void drawTiles(Player p, Location c, Particle part, int dx, int dz){
		p.spawnParticle(part, c.clone().add(tileSize*dx-tileSize*0.5, 0, 0), 2, 0, 0, 1.5f*dz, 0);
		p.spawnParticle(part, c.clone().add(-tileSize*0.5, 0, 0), 2, 0, 0, 1.5f*dz, 0);
		p.spawnParticle(part, c.clone().add(0, 0, tileSize*dz-tileSize*0.5), 2, 1.5f*dx, 0, 0, 0);
		p.spawnParticle(part, c.clone().add(0, 0, -tileSize*0.5), 2, 1.5f*dx, 0, 0, 0);
		
		p.spawnParticle(part, c.clone().add(tileSize*dx-tileSize*0.5, 0, tileSize*dz-tileSize*0.5), 1, 0, 0, 0, 0);
		p.spawnParticle(part, c.clone().add(-tileSize*0.5, 0, tileSize*dz-tileSize*0.5), 1, 0, 0, 0, 0);
		p.spawnParticle(part, c.clone().add(tileSize*dx-tileSize*0.5, 0, -tileSize*0.5), 1, 0, 0, 0, 0);
		p.spawnParticle(part, c.clone().add(-tileSize*0.5, 0, -tileSize*0.5), 1, 0, 0, 0, 0);
	}
}
