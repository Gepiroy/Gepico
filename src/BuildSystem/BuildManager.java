package BuildSystem;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;

import Gepico.main;
import obj.Conf;
import obj.Thing;
import obj.Tile;
import obj.xz;
import WCutils.TextUtil;
import WCutils.WEutil;

public class BuildManager {
	public static List<Building> blist = new ArrayList<>();
	
	public static List<Building> buildings = new ArrayList<>();
	
	
	public static void init(){
		blist.add(new Building("outhouse", null, new BuildInfo(
				BuildCategory.Living,
				"&6Загородный дом",
				Material.ACACIA_TRAPDOOR,
				new String[]{"&fMade in Sanya."},
				new BuildingPos(new xz(0, 0), new xz(1, 2)))){
					@Override
					public void tickDay() {
						
					}
		});
		blist.add(new WorkBuilding("farm", null, new BuildInfo(
				BuildCategory.Work,
				"&6Ферма",
				Material.WHEAT,
				new String[]{"&fКлепает мяско"},
				new BuildingPos(new xz(-1, -1), new xz(4, 4))),
				6, 0, 4){
					@Override
					public void tickDay() {
						int create = work.pawns.size()*50;
						main.buff.add(Thing.Meat, create);
					}
			
		});
		blist.add(new WorkBuilding("alco", null, new BuildInfo(
				BuildCategory.Work,
				"&dСпиртзавод",
				Material.GLASS_BOTTLE,
				new String[]{"&fКлепает бухло"},
				new BuildingPos(new xz(-1, -1), new xz(4, 4))),
				6, 0, 6){
					@Override
					public void tickDay() {
						int max = work.pawns.size()*100;
						int create = (int)(Math.min(main.buff.get(Thing.Sugar)/2, max)*0.5);
						main.buff.add(Thing.Alco, create);
						main.buff.take(Thing.Alco, create*2);
					}
			
		}.priority(1));
		loadBuildings();
	}
	
	public static void sec(){
		
	}
	
	public static List<WorkBuilding> workbuildings(){
		List<WorkBuilding> ret = new ArrayList<>();
		for(Building b:buildings){
			if(b instanceof WorkBuilding){
				ret.add((WorkBuilding) b);
			}
		}
		return ret;
	}
	
	/*public static List<WorkBuilding> findActualImporters(Thing t){
		List<WorkBuilding> ret=new ArrayList<>();
		for(WorkBuilding b:BuildManager.workbuildings()){
			for(StorageSlot s:b.storage.wantsToImport()){
				if(s.thing==t){
					ret.add(b);
					break;
				}
			}
		}
		return ret;
	}*/
	
	public static void saveBuildings(){
		Conf conf = new Conf(main.instance.getDataFolder()+"/Buildings.yml");
		conf.set("a", null);
		int i=0;
		for(Building b:buildings){
			b.save(conf, "a."+i);
			i++;
		}
		conf.save();
	}
	
	public static void loadBuildings(){
		Conf conf = new Conf(main.instance.getDataFolder()+"/Buildings.yml");
		for(String st:conf.getKeys("a")){
			
			buildings.add(createByConf(conf, "a."+st));
		}
	}
	
	public static void destroy(Building b){
		for(Tile t:b.binf.pos.everyMyTile(b.startTile)){
			Location c = t.center();
				c.getWorld().spawnParticle(Particle.SMOKE_LARGE, c, 100, 2, 0.5, 2, 0.05);
				WEutil.loadSchematic(c, "void");
		}
		buildings.remove(b);
	}
	
	public static Building findBuildingOnTile(Tile t){
		for(Building b:buildings){
			xz zero = b.startPos();
			if(diap(zero.x, zero.x+b.binf.pos.ds.x, t.x) && diap(zero.z, zero.z+b.binf.pos.ds.z, t.z)){
				return b;
			}
		}
		return null;
	}
	
	static boolean diap(int from, int to, int i){
		return (i>=from&&i<to);
	}
	
	public static Building findById(String id){
		for(Building b:blist){
			if(b.id.equals(id))return b;
		}
		TextUtil.sdebug("&cCAN'T FIND BUILDING BY ID &6"+id+"&c!!!");
		return null;
	}
	
	public static Building createByBuilding(Tile t, String id){
		Building b = Buildings.create(id);
		
		b.startTile = t;
		
		return b;
	}
	
	public static Building createByConf(Conf conf, String where){
		Building b = Buildings.create(conf.getString(where+".id"));
		
		b.startTile = new Tile(conf, where+".startTile");
		b.binf.pos.rot = conf.getInt(where+".rot");
		
		return b;
	}
	
	
}
