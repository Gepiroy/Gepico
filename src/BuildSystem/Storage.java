package BuildSystem;

import java.util.ArrayList;
import java.util.List;

import WCutils.TextUtil;
import obj.Thing;
import obj.Tile;
import obj.xz;

public class Storage {
	public List<StorageSlot> always = new ArrayList<>();
	
	public List<StorageSlot> wants = new ArrayList<>();
	
	public final xz stor;
	
	
	public Storage(Storage s){
		always = new ArrayList<>(s.always);
		wants = new ArrayList<>(s.wants);
		stor = s.stor;
	}
	
	
	/**
	 * @return сколько НЕ было использовано.
	 */
	public int add(Thing t, int am){
		StorageSlot s = findSlot(t);
		if(s!=null)return s.add(am);
		TextUtil.sdebug("&c[add] На складе нет слотов для "+t);
		return am;
	}
	/**
	 * @return сколько было взято.
	 */
	public int take(Thing t, int am){
		StorageSlot s = findSlot(t);
		if(s!=null)return s.take(am);
		TextUtil.sdebug("&c[take] На складе нет слотов для "+t);
		return 0;
	}
	
	public StorageSlot findSlot(Thing t){
		StorageSlot ret = findSlotIn(t, always);
		if(ret==null)ret = findSlotIn(t, wants);
		return ret;
	}
	
	public List<StorageSlot> wantsToExport(){
		List<StorageSlot> ret = new ArrayList<>();
		for(StorageSlot s:always){
			if(s.export&&s.have>0&&s.cooldown==0)ret.add(s);
		}
		return ret;
	}
	
	public List<StorageSlot> wantsToImport(){
		List<StorageSlot> ret = new ArrayList<>();
		for(StorageSlot s:always){
			if(!s.export&&s.have<s.max)ret.add(s);
		}
		return ret;
	}
	
	public static StorageSlot findSlotIn(Thing t, List<StorageSlot> where){
		for(StorageSlot s:where){
			if(s.thing==t){
				return s;
			}
		}
		return null;
	}
	
	public Tile storageTile(Building b){
		Tile t = new Tile(stor.x, stor.z);
		for(int i=0;i<b.binf.pos.rot;i++){
			int x=t.x, z=t.z;
			t.x=z;
			t.z=-x;
		}
		t.x+=b.startTile.x;
		t.z+=b.startTile.z;
		return t;
	}
}
