package kreashenz.stuntguy3000.mctd.spawns;

import java.util.Random;

import kreashenz.stuntguy3000.mctd.spawns.exceptions.BoxPointsDifferentWorldsException;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;

public class Spawn {

	private Box bounds;
	private World world;

	public Spawn(Location a, Location b) throws BoxPointsDifferentWorldsException {
		bounds = new Box(a, b);
		world = a.getWorld();
	}

	public Spawn(World world, double ax, double ay, double az, double bx, double by, double bz) throws BoxPointsDifferentWorldsException {
		this(new Location(world, ax, ay, az), new Location(world, bx, by, bz));
	}

	public Monster spawnMob(EntityType mobType) {
		Random rnd = new Random();
		Location a = bounds.getA();
		Location b = bounds.getB();
		double randX = rnd.nextDouble() * a.getX() + (b.getX() - a.getX());
		double randY = rnd.nextDouble() * a.getY() + (b.getY() - a.getY());
		double randZ = rnd.nextDouble() * a.getZ() + (b.getZ() - a.getZ());
		Location randomInBounds = new Location(world, randX, randY, randZ);

		Entity ent = world.spawnEntity(randomInBounds, mobType);
		if (ent instanceof Monster) {
			return (Monster) ent;
		}
		ent.remove();
		return null;
	}

	@SuppressWarnings("unused")
	private class Box {
		private Location a, b;
		private World world;

		protected Box(Location a, Location b) throws BoxPointsDifferentWorldsException {
			if (a.getWorld() != b.getWorld()) {
				throw new BoxPointsDifferentWorldsException("Unable to create box. Locations are in two different worlds.");
			}
			this.a = a;
			this.b = b;
			world = a.getWorld();
		}
		// unused
		protected Box(World world, double ax, double ay, double az, double bx, double by, double bz) throws BoxPointsDifferentWorldsException {
			this(new Location(world, ax, ay, az), new Location(world, bx, by, bz));
		}

		protected Location getA() {
			return a;
		}

		protected Location getB() {
			return b;
		}
		// unused
		protected World getWorld() {
			return world;
		}

		protected boolean isInBox(Location l) {
			if (l.getWorld() != world) {
				return false;
			}
			return l.toVector().isInAABB(a.toVector(), b.toVector());
		}
		// unused
		protected boolean isInBox(World world, double x, double y, double z) {
			return isInBox(new Location(world, x, y, z));
		}
	}

}
