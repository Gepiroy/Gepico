package Gepico;

import org.bukkit.entity.Player;

import Buildings.Port;
import WCutils.TextUtil;
import obj.Thing;

public class Team {
	public Player owner;
	public float money;
	public Port port;
	
	public Team(Player p){
		owner=p;
	}
	
	float toSell=1000;
	public void tick(){
		toSell--;
		if(toSell<=0){
			toSell+=1000;
			sellThingsInPort();
		}
	}
	
	public void sellThingsInPort(){
		float before=money;
		for(Thing t:port.storage.keySet()){
			money+=port.storage.get(t)*t.price;
			port.storage.remove(t);
		}
		TextUtil.mes(owner, "&2Порт", "&2+&a"+(money-before)+"&2$");
	}
}
