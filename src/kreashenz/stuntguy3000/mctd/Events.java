package kreashenz.stuntguy3000.mctd;

import java.io.File;

import net.minecraft.server.v1_5_R2.Packet205ClientCommand;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_5_R2.entity.CraftPlayer;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.kitteh.tag.PlayerReceiveNameTagEvent;

public class Events implements Listener {

	public MCTD plugin;
	public Events(MCTD plugin){this.plugin=plugin;}
	public File config;
	public FileConfiguration pConfig;
	public String blueName = "§0[§1Blue§0]";
	public String redName = "§0[§4Red§0]";

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerNameTagChange(PlayerReceiveNameTagEvent e){
		Player p = e.getPlayer();
		if(plugin.teams.playerIsOnBlue(p)){
			e.setTag("§9" + p.getName());
		}
		if(plugin.teams.playerIsOnRed(p)){
			e.setTag("§4" + p.getName());
		}
		if(plugin.teams.playerIsNotPlaying(p)){
			e.setTag("§f" + p.getName());
		}
	}
	
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
				plugin.error(p, "There is no spawn location for the §bBlue§c team.");
			} else {
				double x = a.getDouble("Blue.spawn.x");
				double y = a.getDouble("Blue.spawn.y");
				double z = a.getDouble("Blue.spawn.z");
				double yaw = a.getDouble("Blue.spawn.yaw");
				double pitch = a.getDouble("Blue.spawn.pitch");
				String world = a.getString("Blue.spawn.world");
				Location loc = p.getLocation();

				if(Bukkit.getWorld(world) == null){
					plugin.error(p, "World doesn't exist for the §bblue §cteam spawn.");
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
				plugin.error(p, "There is no spawn location for the §4Red §cteam.");
			} else {
				double x = a.getDouble("Red.spawn.x");
				double y = a.getDouble("Red.spawn.y");
				double z = a.getDouble("Red.spawn.z");
				double yaw = a.getDouble("Red.spawn.yaw");
				double pitch = a.getDouble("Red.spawn.pitch");
				String world = a.getString("Red.spawn.world");
				Location loc = p.getLocation();

				if(Bukkit.getWorld(world) == null){
					plugin.error(p, "§cWorld doesn't exist for the §4Red §cteam spawn.");
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
			if(plugin.teams.playerIsOnBlue(Damager) && plugin.teams.playerIsOnBlue(damaged)
					|| plugin.teams.playerIsOnRed(Damager) && plugin.teams.playerIsOnRed(damaged)){
				e.setCancelled(true);
			}
		}
		if(entityDamager instanceof Skeleton && entityDamaged instanceof Skeleton
				|| entityDamager instanceof Zombie && entityDamaged instanceof Zombie){
			if(plugin.teams.entityIsOnBlue(entityDamager) && plugin.teams.entityIsOnBlue(entityDamaged)
					|| plugin.teams.entityIsOnRed(entityDamager) && plugin.teams.entityIsOnRed(entityDamaged)){
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onTeamPlaceMob(PlayerInteractEvent e){
		Player p = e.getPlayer();
		ItemStack IinH = p.getItemInHand();
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK && IinH != null){
			if (IinH.getType() == Material.MONSTER_EGG){
				Location l = p.getLocation();
				Location loc = new Location(l.getWorld(), l.getBlockX(), l.getBlockY(), l.getBlockZ());
				if(IinH.getData().getData() == 51){
					e.setCancelled(true);
					Skeleton b = p.getWorld().spawn(loc, Skeleton.class);
					if(plugin.teams.playerIsOnBlue(p)){
						b.setCustomName(blueName);
						plugin.teams.entityIsOnBlue.add(b);
					} else {
						b.setCustomName(redName);
						plugin.teams.entityIsOnRed.add(b);
					}
					b.setMaxHealth(b.getMaxHealth()*4);
//					Location l = new Location(l.getWorld(), l.getX(), l.getWorld().getMaxHeight(), l.getZ());
					b.setCustomName("§0[§1BLUE§0]");
					b.setCustomNameVisible(true);
					b.getEquipment().setItemInHand(new ItemStack(Material.BOW));
					plugin.freezeTask.addMob(b, b.getLocation());
				}
				if(IinH.getData().getData() == 54){
					e.setCancelled(true);
					Zombie b = p.getWorld().spawn(loc, Zombie.class);
					if(plugin.teams.playerIsOnBlue(p)){
						b.setCustomName(blueName);
					} else {
						b.setCustomName(redName);
					}
					b.setMaxHealth(b.getMaxHealth()*5);
					b.setCustomNameVisible(true);
					b.getEquipment().setItemInHand(new ItemStack(Material.WOOD_SWORD));
					//Location loc = new Location(l.getWorld(), l.getBlockX(), l.getWorld().getMaxHeight(), l.getBlockZ());
					//                    Location location = new Location(user.getWorld(), topX, user.getWorld().getMaxHeight(), topZ);
					b.setCustomName("§0[§4RED§0]");
					b.setCustomNameVisible(true);
					b.getEquipment().setItemInHand(new ItemStack(Material.BOW));
					plugin.freezeTask.addMob(b, b.getLocation());
				}
			}
		}
	}

	@EventHandler
	public void onEntityDamage(EntityDamageEvent e){
		if(e.getEntity() instanceof Silverfish){
			for(DamageCause damage : DamageCause.values())
				if(e.getEntity().getLastDamageCause().getCause() == damage){
					e.setCancelled(true);
				} else {
					e.setCancelled(true);
				}
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
			p.setDisplayName("§r" + p.getName());
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

	@EventHandler
	public void onCannonPlace(BlockPlaceEvent e){
		Block block = e.getBlock();
		if(block.getType() == Material.DISPENSER){
			Bat mob = block.getWorld().spawn(block.getLocation().add(0,0,0), Bat.class);
			if(plugin.teams.playerIsOnBlue(e.getPlayer())){
				mob.setCustomName(blueName);
			} 
			if(plugin.teams.playerIsOnRed(e.getPlayer())){
				mob.setCustomName(redName);
			} else {
				mob.setCustomNameVisible(false);
			}
			mob.setCustomNameVisible(true);
			plugin.freezeTask.addMob(mob, mob.getLocation());
		}
	}
}
