package kreashenz.stuntguy3000.mctd.waves;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class WaveEventListener implements Listener {
	
	private Wave wave;
	
	public WaveEventListener(Wave wave) {
		this.wave = wave;
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onWaveMobDeath(EntityDeathEvent event) {
		
	}
	
}
