package obj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

import BuildSystem.Building;

public class PlayerInfo {
	public HashMap<String,Integer> timers=new HashMap<>();
	public HashMap<String,Integer> fastTimers=new HashMap<>();
	public ArrayList<String> bools=new ArrayList<>();
	public HashMap<String,Long> realTimers = new HashMap<>();
    public HashMap<String,Integer> waits = new HashMap<>();
    public String pname;
    public List<String> cmd = new ArrayList<>();
    
    public String building = null;
    public Building selectedBuilding;
    public Tile lastRoad = null;
    
    public PlayerInfo(String p){
    	pname=p;
    }
	public PlayerInfo(FileConfiguration conf, String p){
		pname=p;
		if(conf.contains("timers"))for(String st:conf.getConfigurationSection("timers").getKeys(false)){
			timers.put(st, conf.getInt("timers."+st));
		}
		if(conf.contains("fastTimers"))for(String st:conf.getConfigurationSection("fastTimers").getKeys(false)){
			fastTimers.put(st, conf.getInt("fastTimers."+st));
		}
		if(conf.contains("waits"))for(String st:conf.getConfigurationSection("waits").getKeys(false)){
			waits.put(st, conf.getInt("waits."+st));
		}
		if(conf.contains("bools"))for(String st:conf.getConfigurationSection("bools").getKeys(false)){
			bools.add(st);
		}
		if(conf.contains("realTimers"))for(String st:conf.getConfigurationSection("realTimers").getKeys(false)){
			realTimers.put(st,conf.getLong("realTimers."+st));
		}
	}
	public void save(FileConfiguration conf){
		conf.set("timers",null);
		for(String st:timers.keySet()){
			conf.set("timers."+st,timers.get(st));
		}
		conf.set("fastTimers",null);
		for(String st:fastTimers.keySet()){
			conf.set("fastTimers."+st,fastTimers.get(st));
		}
		conf.set("waits",null);
		for(String st:waits.keySet()){
			conf.set("waits."+st,waits.get(st));
		}
		conf.set("bools",bools);
		conf.set("realTimers",null);
		for(String st:realTimers.keySet()){
			conf.set("realTimers."+st,realTimers.get(st));
		}
	}
    public void setbool(String bool, boolean set){
    	if(set)
    		if(!bools.contains(bool))bools.add(bool);
    	else if(bools.contains(bool))bools.remove(bool);
    }
    public void changeBool(String bool){
    	setbool(bool, !bools.contains(bool));
    }
}
