package Gepico;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import BuildSystem.BuildCategory;
import BuildSystem.BuildManager;
import BuildSystem.Building;
import BuildSystem.Road;
import BuildSystem.RoadManager;
import WCutils.GepUtil;
import WCutils.TextUtil;
import invsUtil.Inv;
import invsUtil.InvEvents;
import invsUtil.Invs;
import obj.PlayerInfo;
import obj.Tile;

public class Events implements Listener{
	public static HashMap<String,PlayerInfo> plist=new HashMap<>();
	@EventHandler
	public void join(PlayerJoinEvent e){
		Player p=e.getPlayer();
		doJoin(p);
	}
	public static void doJoin(Player p){
		plist.put(p.getName(), new PlayerInfo(p.getName()));
	}
	
	@EventHandler
	public void leave(PlayerQuitEvent e){
		Player p=e.getPlayer();
		doLeave(p);
	}
	public static void doLeave(Player p){
		plist.remove(p.getName());
	}
	
	@EventHandler
	public void interact(PlayerInteractEvent e){
		if (e.getHand()!=null&&!e.getHand().equals(EquipmentSlot.HAND)){return;}
		Player p=e.getPlayer();
		PlayerInfo pi = plist.get(p.getName());
		ItemStack hitem=p.getInventory().getItemInMainHand();
		Tile look = main.instance.lookAt(p);
		if(hitem.getType()==Material.FERMENTED_SPIDER_EYE)Invs.open(p, InvEvents.example);
		if(hitem.getType()==Material.NETHERITE_AXE){
			Building b = BuildManager.findBuildingOnTile(look);
			if(b!=null)BuildManager.destroy(b);
		}
		if(hitem.getType()==Material.NETHERITE_SHOVEL){
			if(pi.building==null){
				Invs.open(p, InvEvents.allBuilds);
			}else{
				if(BuildManager.createByBuilding(look, pi.building).binf.pos.canBuild(look))return;
				int rot = (int) ((p.getEyeLocation().getYaw()+180+45)/90)+2;
				rot%=4;
				if(rot==1)rot=3;
				else if(rot==3)rot=1;
				Building b = BuildManager.createByBuilding(look, pi.building);
				b.binf.pos.rotTo(rot);
				b.binf.pos.build(look, b.id);
				BuildManager.buildings.add(b);
				pi.building=null;
			}
		}
		if(hitem.getType()==Material.NETHERITE_HOE){
			if(BuildManager.findBuildingOnTile(look)!=null){
				TextUtil.mes(p, TextUtil.plugin, "Здесь уже что-то есть.");
				return;
			}
			if(e.getAction()==Action.LEFT_CLICK_AIR){
				pi.lastRoad=null;
				return;
			}
			if(pi.lastRoad==null){
				RoadManager.roadPoints.add(look);
				pi.lastRoad = look;
			}else{
				if(pi.lastRoad.x!=look.x&&pi.lastRoad.z!=look.z){
					TextUtil.mes(p, TextUtil.plugin, "Дороги должны быть ровными.");
					return;
				}
				Road road = new Road(pi.lastRoad, look);
				for(Tile t:road.everyMyTile()){
					if(BuildManager.findBuildingOnTile(t)!=null){
						TextUtil.mes(p, TextUtil.plugin, "На пути дороги что-то есть.");
						return;
					}
				}
				if(pi.lastRoad.equals(look)){
					return;
				}
				
				List<Tile> newPoints = new ArrayList<>();
				newPoints.add(road.from);
				newPoints.add(road.to);
				for(Road r:new ArrayList<>(RoadManager.roads)){
					Tile c = r.crossing(road);
					if(c!=null){
						if(!GepUtil.contains(newPoints, c))newPoints.add(c);
						if(!r.isMyEnd(c)){
							if(road.xr==r.xr){
								TextUtil.mes(p, TextUtil.plugin, "Как угодно, но только не так.");
								return;
							}
						}
					}
				}
				for(Road r:new ArrayList<>(RoadManager.roads)){
					Tile c = r.crossing(road);
					if(c!=null){
						if(!r.isMyEnd(c)){
							RoadManager.roads.remove(r);
							RoadManager.roads.add(new Road(r.from, c));
							RoadManager.roads.add(new Road(r.to, c));
						}
					}
				}
				Collections.sort(newPoints, Tile.compTiles);
				Tile prev=null;
				for(Tile c:newPoints){
					if(!GepUtil.contains(RoadManager.roadPoints, c))RoadManager.roadPoints.add(c);
					if(prev!=null){
						RoadManager.roads.add(new Road(prev, c));
					}
					prev=c;
				}
				
				RoadManager.roadPoints.add(look);
				RoadManager.roads.add(road);
				pi.lastRoad = look;
			}
		}
		else if(hitem.getType()==Material.AIR){
			Building b = BuildManager.findBuildingOnTile(look);
			if(b!=null){
				if(b.binf.category==BuildCategory.Work){
					pi.selectedBuilding=b;
					//Invs.open(p, InvEvents.FarmGUI);
				}
			}
		}
	}
	
	@EventHandler
	public void combust(EntityCombustEvent e){
		if(e.getEventName().equals("EntityCombustEvent"))e.setCancelled(true);
	}
	
	@EventHandler
	public void GUI(InventoryClickEvent e){
		if(e.getClickedInventory() != null) {
			for(Inv inv:InvEvents.invs){
				if(inv.title(e.getView().getTitle())){
					Invs.event(e);
					return;
				}
			}
		}
	}
}
