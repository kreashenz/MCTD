package kreashenz.stuntguy3000.mctd;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

public class FreezeTask implements Runnable {

	private Map<Entity, Location> frozenMobs = new HashMap<Entity, Location>();

	@Override
	public void run() {
		for(Entry<Entity, Location> current : frozenMobs.entrySet()) {
			current.getKey().teleport(current.getValue());
		}
	}

	public void addMob(Entity mob, Location loc) {
		frozenMobs.put(mob, loc);
	}

	public void removeMob(Entity mob) {
		if(frozenMobs.containsKey(mob)) {
			frozenMobs.remove(mob);
		}
	}

}