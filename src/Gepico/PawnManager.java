package Gepico;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Creature;

import BuildSystem.BuildManager;
import BuildSystem.Building;
import obj.Conf;
import obj.EntityPawn;
import obj.Pawn;

public class PawnManager {
	
	public static List<Pawn> pawns = new ArrayList<>();
	
	public static void init(){
		load();
	}
	static int rate=0;
	public static void tick(){
		rate++;
		if(rate%4==0){
			for(Pawn p:pawns){
				if(p.moveTo==null){
					for(Building b:BuildManager.buildings){
						if(b.id.equals("farm")){
							p.moveTo=b.startTile.center();
							break;
						}
					}
				}
				p.tick();
			}
		}
	}
	
	public static void spawnPawn(Location l){
		Creature c = spawnBasic(l);
		pawns.add(new Pawn(c));
	}
	
	public static Creature spawnBasic(Location l){
		l.getWorld().spawnParticle(Particle.SMOKE_LARGE, l.clone().add(0, 1, 0), 10, 0.2, 0.5, 0.2, 0.025);
		EntityPawn s=new EntityPawn(l.getWorld());
		Creature z=s.spawn(l);
		z.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(200);
		z.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.5);
		z.setRemoveWhenFarAway(false);
		return z;
	}
	
	public static void save(){
		Conf conf = new Conf(main.instance.getDataFolder()+"/pawns.yml");
		conf.set("a", null);
		int i=0;
		for(Pawn p:pawns){
			p.save(conf, "a."+i);
			i++;
		}
		conf.save();
	}
	
	public static void load(){
		Conf conf = new Conf(main.instance.getDataFolder()+"/pawns.yml");
		for(String st:conf.getKeys("a")){
			pawns.add(new Pawn(conf, "a."+st));
		}
	}
}
