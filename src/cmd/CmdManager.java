package cmd;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import BuildSystem.RoadManager;
import Gepico.Events;
import Gepico.PawnManager;
import Gepico.main;
import Roads.Vehicle;
import WCutils.TextUtil;
import obj.PlayerInfo;
import obj.Tile;

public class CmdManager implements CommandExecutor{
	
	public List<ArgReactor> cmds = new ArrayList<>();
	static Random r = main.r;
	
	static final String onlyOp="Эта команда предназначена для разрабов. Просто играй! Быть разрабом - &cочень тяжёлый труд&f!";
	
	public CmdManager(){
		cmds.add(new ArgReactor("gc", "Основная команда Gepico.") {
			@Override public void react(Player p) {
				sublist(p);
			}
		}.fillNext(new ArgReactor[]{
				new ArgReactor("pawn", "создать пешку") {
					@Override public void react(Player p) {
						PawnManager.spawnPawn(p.getLocation());
					}
				},
				new ArgReactor("veh", "создать машину, едущую в рандомную точку дороги.") {
					@Override public void react(Player p) {
						RoadManager.vehicles.add(new Vehicle(new Tile(p.getLocation()), RoadManager.roadPoints.get(r.nextInt(RoadManager.roadPoints.size()))));
					}
				}
			}
		));
	}
	
	public void setup() {
		for(ArgReactor ar:cmds){
			main.instance.getCommand(ar.arg).setExecutor(this);
		}
    }
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)){
			sender.sendMessage("Не, разрабу не лень добавлять проверку на игрока.");
			return true;
		}
		Player p=(Player) sender;
		PlayerInfo pi = Events.plist.get(p.getName());
		pi.cmd.clear();
		StringBuilder pres = new StringBuilder(label+" ");
		TextUtil.debug("cmd="+cmd+"; label="+label+"; args="+args);
		for(ArgReactor c:cmds){
			if(c.arg.equalsIgnoreCase(label)){
				ArgReactor ar = c;
				if(ar.op&&!p.isOp()){
					TextUtil.mes(p, TextUtil.plugin, onlyOp);
					return true;
				}
				int num=0;
				for(String st:args){
					boolean found=false;
					if(ar.next.size()>0){
						found=false;
						for(ArgReactor ar2:ar.next){
							if(ar2.arg.equalsIgnoreCase(st)){
								if(ar2.op&&!p.isOp()){
									TextUtil.mes(p, TextUtil.plugin, onlyOp);
									return true;
								}
								ar=ar2;
								pres.append(ar.arg+" ");
								found=true;
							}
						}
						if(!found){
							ar.sublist(p, "Команда &cне найдена&f. &aПопробуйте&f:", pres.toString());
							break;
						}
					}
					if(!found){
						if(ar.args.size()>num){
							pi.cmd.add(st);
							num++;
						}else if(ar.args.size()==0){
							ar.sublist(p, "Команда &cне найдена&f. &aПопробуйте&f:", pres.toString());
							return true;
						}
					}
				}
				TextUtil.debug("&areacted!");
				ar.react(p);
			}
		}
		return true;
	}
	
}
