package kreashenz.stuntguy3000.mctd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class TeamManager {

	public MCTD plugin;

	public TeamManager(MCTD plugin) {
		this.plugin = plugin;
	}

	public List<String> isOnBlue = new ArrayList<String>();
	public List<String> isOnRed = new ArrayList<String>();
	public List<Entity> entityIsOnRed = new ArrayList<Entity>();
	public List<Entity> entityIsOnBlue = new ArrayList<Entity>();

	public HashMap<Object, Object> gameOnline = new HashMap<Object, Object>();
	public HashMap<Object, Object> gameOffline = new HashMap<Object, Object>();

	public boolean playerIsOnRed(Player p) {
		return isOnRed.contains(p.getName());
	}

	public boolean playerIsOnBlue(Player p) {
		return isOnBlue.contains(p.getName());
	}

	public void setIsNotPlaying(Player p) {
		if (isOnRed.contains(p.getName())) {
			isOnRed.remove(p.getName());
		}
		if (isOnBlue.contains(p.getName())) {
			isOnBlue.remove(p.getName());
		}
	}

	public boolean playerIsNotPlaying(Player p) {
		return !(playerIsOnRed(p) || playerIsOnBlue(p));
	}

	public boolean entityIsOnRed(Entity ent) {
		return entityIsOnRed.contains(ent);
	}

	public boolean entityIsOnBlue(Entity ent) {
		return entityIsOnBlue.contains(ent);
	}

}
