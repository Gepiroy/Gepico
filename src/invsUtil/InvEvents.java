package invsUtil;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import BuildSystem.BuildCategory;
import BuildSystem.BuildManager;
import BuildSystem.Building;
import WCutils.ItemUtil;

public class InvEvents {
	
	public static List<Inv> invs = new ArrayList<>();
	
	
	public static final Inv example = new Inv("&2Жилое") {
		@Override public void displItems(Inventory inv) {
			int i=18;
			for(Building b:BuildManager.blist){
				if(b.binf.category!=BuildCategory.Living)continue;
				inv.setItem(i, ItemUtil.create(b.binf.mat, 1, b.binf.name, b.binf.lore, null, 0));
				i++;
			}
		}
		@Override public void click(InventoryClickEvent e) {
			int i=18;
			for(Building b:BuildManager.blist){
				if(b.binf.category!=BuildCategory.Living)continue;
				if(i==e.getSlot()){
					pi.building=b.id;
					p.closeInventory();
					return;
				}
				i++;
			}
		}
	};
	public static final Inv allBuilds = new Inv("&2Стройка") {
		@Override public void displItems(Inventory inv) {
			int i=18;
			for(Building b:BuildManager.blist){
				inv.setItem(i, ItemUtil.create(b.binf.mat, 1, b.binf.name, b.binf.lore, null, 0));
				i++;
			}
		}
		@Override public void click(InventoryClickEvent e) {
			int i=18;
			for(Building b:BuildManager.blist){
				if(i==e.getSlot()){
					pi.building=b.id;
					p.closeInventory();
					return;
				}
				i++;
			}
		}
	};
	/*public static final Inv FarmGUI = new Inv("&6Ферма") {
		@Override public void displItems(Inventory inv) {
			inv.setItem(0, ItemUtil.create(Material.BEACON, 1, "&bИнфо", pi.selectedBuilding.binf.lore, null, 0));
			FarmBuilding w = (FarmBuilding) pi.selectedBuilding;
			List<String> lore = new ArrayList<>();
			for(StorageSlot s:w.slots){
				lore.add(s.thing.display+"&f: &"+TextUtil.numst(new String[]{"e", "a", "6", "c"}, new double[]{0, 1, s.max*0.8, s.max}, s.have)+s.have+"&7/"+s.max);
			}
			inv.setItem(4, ItemUtil.create(Material.CHEST, 1, "&6Склад", lore, null, 0));
		}
		@Override public void click(InventoryClickEvent e) {
			
		}
	};/*
	public static final Inv mngshdsjkhdfm = new Inv() {
		@Override public void displItems(Inventory inv) {
			
		}
		@Override public void click(InventoryClickEvent e) {
			
		}
	};/*
	public static final Inv mngshdsjkhdfm = new Inv() {
		@Override public void displItems(Inventory inv) {
			
		}
		@Override public void click(InventoryClickEvent e) {
			
		}
	};/*
	public static final Inv mngshdsjkhdfm = new Inv() {
		@Override public void displItems(Inventory inv) {
			
		}
		@Override public void click(InventoryClickEvent e) {
			
		}
	};
	public static final Inv mngshdsjkhdfm = new Inv() {
		@Override public void displItems(Inventory inv) {
			
		}
		@Override public void click(InventoryClickEvent e) {
			
		}
	};
	public static final Inv mngshdsjkhdfm = new Inv() {
		@Override public void displItems(Inventory inv) {
			
		}
		@Override public void click(InventoryClickEvent e) {
			
		}
	};*/
}