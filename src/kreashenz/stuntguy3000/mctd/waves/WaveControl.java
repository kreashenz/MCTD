package kreashenz.stuntguy3000.mctd.waves;

import java.util.Iterator;

import kreashenz.stuntguy3000.mctd.MCTD;
import kreashenz.stuntguy3000.mctd.spawns.TeamSpawns;

import org.bukkit.entity.EntityType;
import org.bukkit.scheduler.BukkitScheduler;

public class WaveControl {

	private int currentWave;
	private boolean isWaveRunning;
	private Wave[] waves;
	private BukkitScheduler scheduler;
	private MCTD plugin;
	private TeamSpawns spawns;
	private int taskId;

	public WaveControl(MCTD plugin, TeamSpawns spawns, Wave... waves) {
		currentWave = 0;
		isWaveRunning = false;
		this.waves = waves;
		this.plugin = plugin;
		this.spawns = spawns;
		scheduler = plugin.getServer().getScheduler();
	}
	
	public boolean isWaveRunning() {
		return isWaveRunning;
	}
	
	public int getCurrentWaveNumber() {
		return currentWave;
	}
	
	public Wave getCurrentWave() {
		return waves[currentWave];
	}

	public void startInitialWave() {
		startWave(currentWave = 0);
	}

	public boolean startNextWave() {
		if (currentWave < waves.length - 1) {
			startWave(++currentWave);
			return true;
		}
		return false;
	}

	public boolean startWave(int wave) {
		if (wave >= waves.length) {
			return false;
		}
		
		Wave waveInstance = waves[wave];
		final Iterator<EntityType> iterator = waveInstance.iterator();
		
		taskId = scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {

			@Override
			public void run() {
				if (iterator.hasNext()) {
					spawns.getRandomSpawn().spawnMob(iterator.next()); //TODO So make a reference of blablabla here.... Maybe store it to a collection of some kind...but how to detect if they died? OH events.
				} else {
					stopCurrentWave();
				}
			}
			
		}, 0, waveInstance.getDelay());
		
		isWaveRunning = true;
		return true;
	}
	
	private void stopCurrentWave() {
		isWaveRunning = false;
		scheduler.cancelTask(taskId);
	}
	
}
