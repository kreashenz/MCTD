package kreashenz.stuntguy3000.mctd;

import java.io.File;

import net.minecraft.server.v1_5_R2.Packet205ClientCommand;

import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.craftbukkit.v1_5_R2.entity.CraftPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Events implements Listener {

	MCTD plugin;
	Events(MCTD plugin){this.plugin=plugin;}
	File config;
	FileConfiguration pConfig;

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e){
		config = new File("plugins/MinecraftTD/players/" + e.getPlayer().getName().toLowerCase() + ".yml");
		pConfig = YamlConfiguration.loadConfiguration(config);
		pConfig.set("Kills", "0");
		pConfig.set("Deaths", "0");
		pConfig.set("Points", plugin.getConfig().getInt("Points-To-Start-With"));
		try {
			pConfig.save(config);
		} catch (Exception ex){ex.printStackTrace();}

		plugin.sb.setScoreboard(e.getPlayer());
		if(plugin.teams.isOnBlue.contains(e.getPlayer().getName())){
			plugin.teams.isOnBlue.remove(e.getPlayer().getName());
		}
		if(plugin.teams.isOnRed.contains(e.getPlayer().getName())){
			plugin.teams.isOnRed.remove(e.getPlayer().getName());
		}
	}

	@EventHandler
	public void onPlayerAutoRespawn(PlayerDeathEvent e){
		FileConfiguration a = plugin.getConfig();
		Player p = e.getEntity().getPlayer();
		Packet205ClientCommand packet = new Packet205ClientCommand();
		packet.a = 1;
		((CraftPlayer) p).getHandle().playerConnection.a(packet);
		if(plugin.teams.playerIsOnBlue(p)){
			if(a.getString("Blue.spawn") == null){
				p.sendMessage("§cThere is no spawn location for the §bBlue§c team.");
			} else {
				double x = a.getDouble("Blue.spawn.x");
				double y = a.getDouble("Blue.spawn.y");
				double z = a.getDouble("Blue.spawn.z");
				double yaw = a.getDouble("Blue.spawn.yaw"); // KANE if you see this, im turning my
				double pitch = a.getDouble("Blue.spawn.pitch"); // server into a build server.
				String world = a.getString("Blue.spawn.world"); // node24.minecrafted.net:25590
				Location loc = p.getLocation();

				if(Bukkit.getWorld(world) == null){
					p.sendMessage("§cWorld doesn't exist for the §bblue §cteam spawn.");
				} else {
					loc.setX(x);
					loc.setY(y);
					loc.setZ(z);
					loc.setYaw((float)yaw);
					loc.setPitch((float)pitch);
					loc.setWorld(Bukkit.getWorld(world));
					p.teleport(loc);
				}
			}
		}
		if(plugin.teams.playerIsOnRed(p)){
			if(a.getString("Red.spawn") == null){
				p.sendMessage("§cThere is no spawn location for the §4Red §cteam.");
			} else {
				double x = a.getDouble("Red.spawn.x");
				double y = a.getDouble("Red.spawn.y");
				double z = a.getDouble("Red.spawn.z");
				double yaw = a.getDouble("Red.spawn.yaw");
				double pitch = a.getDouble("Red.spawn.pitch");
				String world = a.getString("Red.spawn.world");
				Location loc = p.getLocation();

				if(Bukkit.getWorld(world) == null){
					p.sendMessage("§cWorld doesn't exist for the §4Red §cteam spawn.");
				} else {
					loc.setX(x);
					loc.setY(y);
					loc.setZ(z);
					loc.setYaw((float)yaw);
					loc.setPitch((float)pitch);
					loc.setWorld(Bukkit.getWorld(world));
					p.teleport(loc);
				}
			}
		}
		if(plugin.teams.playerIsNotPlaying(p)){
			Location loc = p.getWorld().getSpawnLocation();
			p.teleport(loc);
		}
		File file = new File("plugins/MinecraftTD/players/" + p.getName() + ".yml");
		YamlConfiguration b = YamlConfiguration.loadConfiguration(file);
		Player k = p.getKiller();
		File afile = new File("plugins/MinecraftTD/players/" + k.getName() + ".yml");
		YamlConfiguration c = YamlConfiguration.loadConfiguration(afile);
		b.set("Deaths", b.getInt("Deaths") +1);
		c.set("Kills", c.getInt("Kills") +1);
		try {
			b.save(file);
			c.save(afile);
		} catch(Exception ex){ex.printStackTrace();}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onTeamMemeberTriesToHurtAnotherTeamMember(EntityDamageByEntityEvent e) {
		Entity entityDamager = e.getDamager();
		Entity entityDamaged = e.getEntity();
		if (entityDamager instanceof Player && entityDamaged instanceof Player){
			Player Damager = (Player) entityDamager;
			Player damaged = (Player) entityDamaged;
			if(plugin.teams.playerIsOnBlue(Damager) && plugin.teams.playerIsOnBlue(damaged)){
				e.setCancelled(true);
			}
			if(plugin.teams.playerIsOnRed(Damager) && plugin.teams.playerIsOnRed(damaged)){
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onTeamPlaceMob(PlayerInteractEvent e){
		Player p = e.getPlayer();
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK && p.getItemInHand() != null){
			if (p.getItemInHand().getType() == Material.MONSTER_EGG){
				if(plugin.teams.playerIsOnBlue(p)){
					e.setCancelled(true);
					Skeleton b = p.getWorld().spawn(p.getLocation(), Skeleton.class);
					b.setCustomName("§0[§1BLUE§0]");
					b.setCustomNameVisible(true);
					plugin.freezeTask.addMob(b, b.getLocation());
				}
				if(plugin.teams.playerIsOnRed(p)){
					e.setCancelled(true);
					Skeleton b = p.getWorld().spawn(p.getLocation(), Skeleton.class);
					b.setCustomName("§0[§4RED§0]");
					b.setCustomNameVisible(true);
					plugin.freezeTask.addMob(b, b.getLocation());
				}
			}
		}
	}

	@EventHandler
	public void playerPlaceMobEgg(CreatureSpawnEvent e){
		if(e.getSpawnReason() == SpawnReason.SPAWNER_EGG || e.getSpawnReason() == SpawnReason.EGG){
			e.setCancelled(true);
		} else {
			e.setCancelled(false);
		}
	}

	@EventHandler
	public void playerChatAsTeam(AsyncPlayerChatEvent e){
		Player p = e.getPlayer();
		if(plugin.teams.playerIsOnBlue(p)){
			e.setMessage(ChatColor.translateAlternateColorCodes('&', e.getMessage()));
			p.setDisplayName("§9" + p.getName() + "§r");
		}
		if(plugin.teams.playerIsOnRed(p)){
			e.setMessage(ChatColor.translateAlternateColorCodes('&', e.getMessage()));
			p.setDisplayName("§4" + p.getName() + "§r");
		}
		if(plugin.teams.playerIsNotPlaying(p)){
			e.setMessage(e.getMessage());
		}
	}

	@EventHandler
	public void onPlayerHitsBarbedWire(PlayerMoveEvent e){
		Player p = e.getPlayer();
		if(p.getLocation().getBlock().getType() == Material.IRON_FENCE)p.damage(1);
		if(p.getLocation().add(0,-1,0).getBlock().getType() == Material.IRON_FENCE)p.damage(1);
	}

	@EventHandler
	public void onCannonShoot(BlockDispenseEvent e){
		Block b = e.getBlock();
		ItemStack a = new ItemStack(Material.SNOW_BALL);
		if(b != null){
			Inventory dInv = ((Dispenser)b.getState()).getInventory();
			if(dInv.contains(a)){
				dInv.clear();
			}
			a.setAmount(64);
			dInv.addItem(new ItemStack(a));
		}
	}

}
