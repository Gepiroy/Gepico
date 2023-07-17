package obj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity;
import org.bukkit.entity.Creature;
import org.bukkit.entity.LivingEntity;

import BuildSystem.BuildCategory;
import BuildSystem.BuildManager;
import BuildSystem.Building;
import BuildSystem.WorkBuilding;
import Gepico.PawnManager;
import Gepico.main;
import WCutils.GepUtil;
import net.minecraft.server.v1_16_R3.EntityCreature;

public class Pawn {
	static Random r = main.r;
	public String name;
	public UUID id;
	public Creature cr;
	public EntityCreature e;
	
	public Location moveTo;
	public LivingEntity target;
	public Building where;
	public WorkBuilding workplace;
	public Building home;
	
	public HashMap<String, Integer> timers = new HashMap<>();
	
	
	
	public String goingTo;
	
	public float health = 50;
	public float money = 3;
	public float age = 18;
	
	public int education = 0;//1 - школа, 2 - колл.
	
	public Pawn(Creature en){
		this.cr=en;
		this.id=UUID.randomUUID();
		this.name=ChatColor.values()[r.nextInt(ChatColor.values().length)]+"Женякас";
		this.e=((EntityCreature) ((CraftEntity) en).getHandle());
	}
	
	public Pawn(Conf conf, String where){
		name=conf.getString(where+".name");
		id=conf.getUUID(where+".id");
		cr=(Creature) Bukkit.getEntity(conf.getUUID(where+".eid"));
		
		health = (float) conf.getDouble(where+".id");
		money = (float) conf.getDouble(where+".money");
		age = (float) conf.getDouble(where+".age");
	}
	
	public void save(Conf conf, String where){
		conf.set(where+".name", name);
		conf.set(where+".id", id.toString());
		if(cr!=null)conf.set(where+".eid", cr.getUniqueId().toString());
		
		conf.set(where+".health", health);
		conf.set(where+".money", money);
		conf.set(where+".age", age);
	}
	
	public void tick(){
		Think();
		Do();
	}
	
	public double dist(Location l){
		return cr.getLocation().distance(l);
	}
	
	void Think(){
		if(cr!=null){
			if(moveTo!=null){
				if(dist(moveTo)<=2){
					moveTo=null;
					if(goingTo.equals("work")){
						despawn();
						timers.put("work", 200);
					}
				}
			}
			if(workplace==null){
				for(Building b:BuildManager.buildings){
					if(b.binf.category==BuildCategory.Work){
						workplace=(WorkBuilding) b;
						timers.put("noWork", 20);
						break;
					}
				}
			}
			if(goingTo==null){
				for(Building b:BuildManager.buildings){
					if(b.binf.category==BuildCategory.Living){
						moveTo=b.startTile.center();
						goingTo="home";
						break;
					}
				}
			}
		}
		for(String st:new ArrayList<>(timers.keySet())){
			if(GepUtil.HashMapReplacer(timers, st, -1, true, false)){
				if(st.equals("work")){
					spawn(workplace.startTile.center());
					timers.put("noWork", 200);
				}else if(st.equals("noWork")){
					moveTo=workplace.startTile.center();
					goingTo="work";
				}
			}
		}
	}
	
	void Do(){
		if(cr!=null){
			if(target!=null){
				cr.setTarget(target);
				return;
			}else if(cr.getTarget()!=null){
				cr.setTarget(null);
			}
			if(moveTo!=null){
				move(moveTo);
			}
		}else{
			if(timers.containsKey("work")){
			}
		}
	}
	
	public void spawn(Location l){
		cr = PawnManager.spawnBasic(l);
		this.e = ((EntityCreature) ((CraftEntity) cr).getHandle());
	}
	
	public void despawn(){
		cr.remove();
		cr=null;
		e=null;
		goingTo=null;
		moveTo=null;
	}
	
	public void move(Location l){
		e.getNavigation().a(e.getNavigation().a(l.getX(), l.getY(), l.getZ(), 1), 1);
	}
}
