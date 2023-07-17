package Gepico;

import java.util.HashMap;

import org.bukkit.entity.Player;

public class TeamManager {
	public static HashMap<Player, Team> teams = new HashMap<>();
	
	public void doJoin(Player p){
		if(!teams.containsKey(p))teams.put(p, new Team(p));
	}
	
	public void doLeave(Player p){
		//teams.remove(p);
	}
}
