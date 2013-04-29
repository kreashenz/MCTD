package kreashenz.stuntguy3000.mctd.spawns;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TeamSpawns {

	private List<Spawn> spawns;
	private Random rnd;

	public TeamSpawns() {
		spawns = new ArrayList<Spawn>();
		rnd = new Random();
	}

	public List<Spawn> getSpawns() {
		return spawns;
	}

	public Spawn getRandomSpawn() {
		return spawns.get(rnd.nextInt(spawns.size()));
	}

	public void addSpawn(Spawn spawn) {
		spawns.add(spawn);
	}

	public void addSpawn(Spawn[] spawns) {
		for (Spawn spawn : spawns) {
			addSpawn(spawn);
		}
	}

	public boolean removeSpawn(Spawn spawn) {
		return spawns.remove(spawn);
	}

	public Spawn removeSpawn(int index) {
		return spawns.remove(index);
	}

}
