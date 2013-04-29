package kreashenz.stuntguy3000.mctd.waves;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.bukkit.entity.EntityType;

public class Wave implements Iterable<EntityType> {
	
	private final List<EntityType> mobList;
	private List<EntityType> aliveMobs;
	private int delay;
	
	public Wave(int delay, EntityType... mobTypes) {
		mobList = Arrays.asList(mobTypes);
		aliveMobs = new ArrayList<EntityType>();
		for (EntityType mob : mobList) {
			aliveMobs.add(mob);
		}
		this.delay = delay;
	}
	
	public int getDelay() {
		return delay;
	}
	
	//When a monster in the wave dies:
	public boolean removeMob(EntityType mob) {
		return aliveMobs.remove(mob);
	}

	@Override
	public Iterator<EntityType> iterator() {
		return new Iterator<EntityType>() {
			
			int index = 0;
			
			@Override
			public boolean hasNext() {
				return index < mobList.size();
			}

			@Override
			public EntityType next() {
				EntityType out = mobList.get(index);
				index++;
				return out;
			}

			@Override
			public void remove() {
				// TODO Auto-generated method stub
				
			}
			
		};
	}
	
}
