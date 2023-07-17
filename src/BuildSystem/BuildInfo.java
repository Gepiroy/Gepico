package BuildSystem;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;

import WCutils.ItemUtil;
import WCutils.TextUtil;
import obj.xz;

public class BuildInfo {
	
	public final BuildCategory category;
	public final String name;
	public final Material mat;
	public final List<String> lore;
	public BuildingPos pos;
	
	public BuildInfo(BuildCategory category, String name, Material mat, Object lore, BuildingPos pos){
		this.category=category;
		this.name=TextUtil.str(name);
		this.mat=mat;
		this.lore=ItemUtil.lore(lore);
		this.pos=pos;
	}
	
	public BuildInfo(BuildInfo binf){
		this.category=binf.category;
		this.name=binf.name;
		this.mat=binf.mat;
		this.lore=new ArrayList<>(binf.lore);
		this.pos=new BuildingPos(new xz(binf.pos.zero), new xz(binf.pos.ds));
	}
}
