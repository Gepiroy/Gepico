package obj;

import java.util.HashMap;

public class ThingBuffer {
	HashMap<Thing, Integer> stored = new HashMap<>();
	
	public void add(Thing t, int am){
		if(!stored.containsKey(t))stored.put(t, am);
		stored.replace(t, stored.get(t)+am);
	}
	
	public int take(Thing t, int am){
		if(!stored.containsKey(t))return 0;
		int take = am;
		int set = get(t);
		if(set<take)take=set;
		set-=take;
		stored.replace(t, set);
		return take;
	}
	
	public int get(Thing t){
		if(!stored.containsKey(t))return 0;
		return stored.get(t);
	}
}
