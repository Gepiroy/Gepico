package WCutils;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryUtil {
	
	
	/** 
	 * @param item - ����������� �������
	 * @param target - �������� ��� ����������
	 * @param mustAddAll - ������� ������ ����������� ��� �������?
	 */
	public static boolean canStack(ItemStack item, ItemStack target, boolean mustAddAll){
		if(target==null||item==null)return false;
		if(!item.getType().equals(target.getType()))return false;
		if(target.getAmount()==target.getMaxStackSize())return false;
		if(item.hasItemMeta()&&target.hasItemMeta()){
			if(!item.getItemMeta().equals(target.getItemMeta()))return false;
		}else if(item.hasItemMeta()||target.hasItemMeta())return false;
		if(mustAddAll&&item.getAmount()+target.getAmount()>item.getType().getMaxStackSize())return false;
		return true;
	}
	/**
	 * ����� ������ ���� ��� ���������� �� ��� ���������
	 * @param inv
	 * @param item - ����������� �������
	 * @param air - �������� ��������� ����?
	 * @return ����� ������� ����� ��� ���������� ��� -1.
	 */
	public static int getBestSlotToStack(Inventory inv, ItemStack item, boolean mustAddAll, boolean air){
		if(item==null)return -1;
		for(int i=0;i<inv.getSize();i++){
			if(canStack(item, inv.getItem(i), mustAddAll))return i;
		}
		if(air)for(int i=0;i<inv.getSize();i++){
			ItemStack it=inv.getItem(i);
			if(it==null)TextUtil.debug("null item at "+i);
			if(it==null||it.getType().equals(Material.AIR))return i;
		}
		return -1;
	}
	/**
	 * ����� ������ ���� ��� ���������� � ���. ������ ������
	 * @param inv
	 * @param slots - ����� ��� ��������
	 * @param item - ���������� �������
	 * @param air - �������� ��������� ����?
	 * @return ����� ������� ����� ��� ���������� ��� -1.
	 */
	public static int getBestSlotToStack(Inventory inv, int[] slots, ItemStack item, boolean mustAddAll, boolean air){
		if(item==null)return -1;
		for(int i:slots){
			if(canStack(item, inv.getItem(i), mustAddAll))return i;
		}
		if(air)for(int i:slots){
			if(inv.getItem(i).getType().equals(Material.AIR))return i;
		}
		return -1;
	}
	
	public static boolean haveItem(Player p, Material mat, int am){
		if(countItem(p, mat)>=am)return true;
		return false;
	}
	public static int countItem(Player p, Material mat){
		int am=0;
		for(ItemStack item:p.getInventory()){
			if(item!=null&&item.getType().equals(mat)){
				am+=item.getAmount();
			}
		}
		return am;
	}
	public static void remItems(Player p, Material mat, String name, int am){
		for(ItemStack item:p.getInventory()){
			if(item!=null&&item.getType().equals(mat)){
				if(name!=null){
					if(!item.hasItemMeta()||item.getItemMeta().getDisplayName().contains(name))continue;
				}
				if(item.getAmount()<=am){
					am-=item.getAmount();
					item.setAmount(0);
				}
				else{
					item.setAmount(item.getAmount()-am);
					return;
				}
			}
		}
	}
}
