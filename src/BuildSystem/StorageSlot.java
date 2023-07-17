package BuildSystem;

import obj.Conf;
import obj.Thing;

public class StorageSlot {
	public Thing thing;
	public int max;
	public int have=0;
	public boolean export=true;
	public int cooldown=0;
	
	public StorageSlot(Thing thing, int max){
		this.thing=thing;
		this.max=max;
	}
	
	public StorageSlot setImport(){
		export=false;
		return this;
	}
	
	public int add(int am){
		if(am>max-have){
			am=max-have;
		}
		have+=am;
		return am;
	}
	
	public int take(int am){
		if(am>have){
			am=have;
		}
		have-=am;
		return am;
	}
	
	public void save(Conf conf, String where){
		conf.set(where+".have", have);
	}
}
