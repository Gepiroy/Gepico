package WCutils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.math.transform.AffineTransform;
import com.sk89q.worldedit.session.ClipboardHolder;

import Gepico.main;

public class WEutil {
	
	static WorldEdit we;
	
	public static void setup(){
		we = ((WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit")).getWorldEdit();
	}
	
	public static void loadSchematic(Location loc, String schName){
		File schematic = new File(main.instance.getDataFolder()+File.separator+"/schematics/"+schName+".schem");
		try(EditSession session = we.newEditSession(new BukkitWorld(loc.getWorld()))){
			Clipboard clipboard;
			ClipboardFormat format = ClipboardFormats.findByFile(schematic);
			try (ClipboardReader reader = format.getReader(new FileInputStream(schematic))) {
			    clipboard = reader.read();
			    Operation operation = new ClipboardHolder(clipboard)
		            .createPaste(session)
		            .to(BlockVector3.at(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()))
		            .build();
			    Operations.complete(operation);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}catch(WorldEditException e){
			e.printStackTrace();
		}
	}
	
	public static void loadSchematic(Location loc, String schName, int rotate){
		File schematic = new File(main.instance.getDataFolder()+File.separator+"/schematics/"+schName+".schem");
		try(EditSession session = we.newEditSession(new BukkitWorld(loc.getWorld()))){
			Clipboard clipboard;
			ClipboardFormat format = ClipboardFormats.findByFile(schematic);
			try (ClipboardReader reader = format.getReader(new FileInputStream(schematic))) {
			    clipboard = reader.read();
			    ClipboardHolder ch = new ClipboardHolder(clipboard);
			    ch.setTransform(new AffineTransform().rotateY(90*rotate));
			    Operation operation = ch
		            .createPaste(session)
		            .to(BlockVector3.at(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()))
		            .build();
			    Operations.complete(operation);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}catch(WorldEditException e){
			e.printStackTrace();
		}
	}
}
