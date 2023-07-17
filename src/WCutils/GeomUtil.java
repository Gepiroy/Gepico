package WCutils;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class GeomUtil {
	public static Vector direction(Location from, Location to){
		return from.toVector().subtract(to.toVector()).normalize();
	}
	
	public static Player getTargetPlayer(final Player player) {
        return getTarget(player, player.getWorld().getPlayers());
    }
 
    public static Entity getTargetEntity(final Entity entity) {
        return getTarget(entity, entity.getWorld().getEntities());
    }
 
    public static <T extends Entity> T getTarget(final Entity entity, final Iterable<T> entities) {
        if(entity==null)return null;
        T target=null;
        final double threshold=1;
        for (final T other: entities) {
            final Vector n = other.getLocation().toVector().subtract(entity.getLocation().toVector());
            if (entity.getLocation().getDirection().normalize().crossProduct(n).lengthSquared() < threshold && n.normalize().dot(entity.getLocation().getDirection().normalize()) >= 0) {
                if (target == null || target.getLocation().distanceSquared(entity.getLocation()) > other.getLocation().distanceSquared(entity.getLocation()))
                    target = other;
            }
        }
        return target;
    }
    
    public static void lineBetweenTwoPoints(Location p1, Location p2, Particle part, float step){
	    double distance = p1.distance(p2);
	    Vector v1 = p1.toVector();
	    Vector v2 = p2.toVector();
	    Vector vector = v2.clone().subtract(v1).normalize().multiply(step);
	    float length = 0;
	    for (; length < distance; v1.add(vector)) {
	    	p1.getWorld().spawnParticle(part, v1.toLocation(p1.getWorld()), 1, 0.1, 0.1, 0.1, 0);
	        length += step;
	    }
	}
}
