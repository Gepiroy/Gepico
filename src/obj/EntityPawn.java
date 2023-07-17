package obj;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Creature;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import net.minecraft.server.v1_16_R3.EntitySkeleton;
import net.minecraft.server.v1_16_R3.EntityTypes;

import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;

public class EntityPawn extends EntitySkeleton{
	
    public EntityPawn(World world) {
    	super(EntityTypes.SKELETON, ((CraftWorld) world).getHandle());
    }
    
    @Override
    public void initPathfinder() {
    	//Мы очистили мозги нашему мобу.
    }
    
    public Creature spawn(Location loc) {
        this.setPositionRotation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        this.world.addEntity(this, SpawnReason.CUSTOM);
        return (Creature) this.getBukkitEntity();
    }
    
    
}