package obj;

import java.util.HashMap;

public class Needs{
	private HashMap<Need, Double> needs = new HashMap<>();
	
	public Needs(){
		for(Need n:Need.values()){
			needs.put(n, 50.0);
		}
	}
	
	public double get(Need n){
		return needs.get(n);
	}
	
	public double add(Need n, int add){
		double set = needs.get(n);
		set+=add;
		needs.replace(n, set);
		return set;
	}
}
