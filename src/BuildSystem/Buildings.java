package BuildSystem;

import java.util.HashMap;

import org.bukkit.Material;

import Gepico.main;
import WCutils.TextUtil;
import obj.Thing;
import obj.xz;

public class Buildings {
	private Buildings(){}
	
	private static HashMap<String, BuildingCreator> presets = new HashMap<>();
	
	public static void init(){
		presets.put("farm", new BuildingCreator() {
			@Override
			public Building create() {
				return new WorkBuilding("farm", null, new BuildInfo(
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
						};
			}
		});
		presets.put("plants", new BuildingCreator() {
			@Override
			public Building create() {
				return (WorkBuilding) new WorkBuilding("plants", null, new BuildInfo(
						BuildCategory.Work,
						"&2Плантация",
						Material.GLASS_BOTTLE,
						new String[]{"&fКлепает сахарочек"},
						new BuildingPos(new xz(-1, -1), new xz(4, 4))),
						6, 0, 6){
							@Override
							public void tickDay() {
								int create = work.pawns.size()*50;
								main.buff.add(Thing.Sugar, create);
							}
						}.priority(1);
			}
		});
		presets.put("alco", new BuildingCreator() {
			@Override
			public Building create() {
				return (WorkBuilding) new WorkBuilding("alco", null, new BuildInfo(
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
						}.priority(1);
			}
		});
	}
	
	public static Building create(String id){
		if(!presets.containsKey(id)){
			TextUtil.sdebug("&cTried to create building with id &6"+id+"&c, but this id is not exists in presets.");
			return null;
		}
		return presets.get(id).create();
	}
}
