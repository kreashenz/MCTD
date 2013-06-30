package kreashenz.stuntguy3000.mctd.teams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import kreashenz.stuntguy3000.mctd.MCTD;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class TeamManager implements ITeam {

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
		ScoreboardManager m = Bukkit.getScoreboardManager();
		Scoreboard b = m.getNewScoreboard();
		Team team = b.registerNewTeam("Red");
		Team t = b.registerNewTeam("Blue");
		t.removePlayer(p);
		team.removePlayer(p);
	}

	public void setRedTeam(Player p){
		isOnRed.add(p.getName());
		ScoreboardManager m = Bukkit.getScoreboardManager();
		Scoreboard b = m.getNewScoreboard();
		Team team = b.registerNewTeam("Red");
		team.setCanSeeFriendlyInvisibles(true);
		team.setAllowFriendlyFire(true);
		team.addPlayer(p);
		p.getInventory().setHelmet(new ItemStack(Material.LEATHER_HELMET, 1));
		p.getInventory().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE, 1));
		p.getInventory().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS, 1));
		p.getInventory().setBoots(new ItemStack(Material.LEATHER_BOOTS, 1));
		p.setItemInHand(new ItemStack(Material.STONE_SWORD, 1));
	}

	public void setBlueTeam(Player p){
		isOnBlue.add(p.getName());
		ScoreboardManager m = Bukkit.getScoreboardManager();
		Scoreboard b = m.getNewScoreboard();
		Team team = b.registerNewTeam("Blue");
		team.setCanSeeFriendlyInvisibles(true);
		team.setAllowFriendlyFire(true);
		team.addPlayer(p);
		p.getInventory().setHelmet(new ItemStack(Material.LEATHER_HELMET, 1));
		p.getInventory().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE, 1));
		p.getInventory().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS, 1));
		p.getInventory().setBoots(new ItemStack(Material.LEATHER_BOOTS, 1));
		p.setItemInHand(new ItemStack(Material.STONE_SWORD, 1));
	}

	public void TpSpawn(Player p){
		if(playerIsOnRed(p)){
			double x = plugin.getConfig().getDouble("Red.spawn.x");
			double y = plugin.getConfig().getDouble("Red.spawn.y");
			double z = plugin.getConfig().getDouble("Red.spawn.z");
			double yaw = plugin.getConfig().getDouble("Red.spawn.yaw");
			double pitch = plugin.getConfig().getDouble("Red.spawn.pitch");
			String world = plugin.getConfig().getString("Red.spawn.world");
			Location loc = p.getLocation();
			if(Bukkit.getWorld(world) == null){
				p.sendMessage("§cThe world set for the §4Red §cteam spawn, isn't available.");
			} else {
				loc.setX(x);
				loc.setY(y);
				loc.setZ(z);
				loc.setYaw((float)yaw);
				loc.setPitch((float)pitch);
				loc.setWorld(Bukkit.getWorld(world));
				p.teleport(new Location(Bukkit.getWorld(world), x, y, z, (float)yaw, (float)pitch));
			}
		}
		if(playerIsOnBlue(p)){
			double x = plugin.getConfig().getDouble("Blue.spawn.x");
			double y = plugin.getConfig().getDouble("Blue.spawn.y");
			double z = plugin.getConfig().getDouble("Blue.spawn.z");
			double yaw = plugin.getConfig().getDouble("Blue.spawn.yaw");
			double pitch = plugin.getConfig().getDouble("Blue.spawn.pitch");
			String world = plugin.getConfig().getString("Blue.spawn.world");
			Location loc = p.getLocation();
			if(Bukkit.getWorld(world) == null){
				p.sendMessage("§cThe world set for the §4Red §cteam spawn, isn't available.");
			} else {
				loc.setX(x);
				loc.setY(y);
				loc.setZ(z);
				loc.setYaw((float)yaw);
				loc.setPitch((float)pitch);
				loc.setWorld(Bukkit.getWorld(world));
				p.teleport(new Location(Bukkit.getWorld(world), x, y, z, (float)yaw, (float)pitch));
			}
		}
		if(playerIsNotPlaying(p)){
			
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

	public String getTeam(Player p){
		if(playerIsOnBlue(p)){
			// ?? what the fuck do i need to put here?
		}
		if(playerIsOnRed(p)){
			// same thing?!
		}
		if(playerIsNotPlaying(p)){
			// ?DSA?DSA?DSA?DSA{DSA!?#?@!@!
		}
		return ""; // no idea what to return.. 
	}

}
