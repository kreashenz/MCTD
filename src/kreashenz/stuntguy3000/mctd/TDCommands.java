package kreashenz.stuntguy3000.mctd;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class TDCommands implements CommandExecutor {

	public MCTD plugin;
	public TDCommands(MCTD plugin){this.plugin=plugin;}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, final String[] args){
		FileConfiguration config = plugin.getConfig();
		TeamManager a = plugin.teams;
		if(cmd.getName().equalsIgnoreCase("td")){
			if(sender instanceof Player){
				final Player p = (Player)sender;
				if(args.length == 0){
					plugin.sendHelp(p);
				}
				if(args.length == 1){
					if(args[0].equalsIgnoreCase("points")){
						p.sendMessage("§aYou have §9" + Points.getPoints(p) + " §apoints!");
					}
					if(args[0].equalsIgnoreCase("setspawn")){
						p.sendMessage("§cUsage : §f/td setspawn <blue | red>");
					}
					if(args[0].equalsIgnoreCase("reload")){
						plugin.reloadConfig();
						p.sendMessage("§aConfig successfully reloaded!");
					}
					if(args[0].equalsIgnoreCase("join")){
						p.sendMessage("§cUsage : §f/td join <red | blue>");
					}
				}
				if(args.length == 2){
					if(args[0].equalsIgnoreCase("points")){
						Player t = Bukkit.getPlayer(args[1]);
						if(t != null && t.isOnline()){
							p.sendMessage("§9" + t.getName() + "§a has §9" + Points.getPoints(t) + " §apoints!");
						} else p.sendMessage("§cThat player isn't online.");
					}
					if(args[0].equalsIgnoreCase("team")){
						String[] leave = { "leave", "quit" };
						for(String quit : leave)
							if(args[1].equalsIgnoreCase(quit)){
								if(plugin.teams.playerIsOnBlue(p) || plugin.teams.playerIsOnRed(p)){
									plugin.teams.setIsNotPlaying(p);
								} else p.sendMessage("§cYou must be on a team to leave it!");
							}
					}
					if(args[0].equalsIgnoreCase("join")){
						if(args[1].equalsIgnoreCase("blue")){
							if(a.playerIsNotPlaying(p) && !a.playerIsOnRed(p)){
								a.isOnBlue.add(p.getName());
								p.sendMessage("§aJoined the §bblue §ateam!");
//								plugin.sb.setBlueTeam(p);
							} else p.sendMessage("§cYou have already chosen your team.");
						}
						if(args[1].equalsIgnoreCase("red")){
							if(a.playerIsNotPlaying(p) && !a.playerIsOnBlue(p)){
								a.isOnRed.add(p.getName());
								p.sendMessage("§aJoined the §4red §ateam!");
//								plugin.sb.setRedTeam(p);
							} else p.sendMessage("§cYou have already chosen your team.");
						}
						if(!(args[1].equalsIgnoreCase("red") || args[1].equalsIgnoreCase("blue"))){
							p.sendMessage("§cYou can only join the §4Red §cor §bBlue §cteams!");
						}
					}
					if(args[0].equalsIgnoreCase("delete")){
						if(config.contains(args[1])){
							config.set(args[1].toLowerCase(), null);
							plugin.saveConfig();
						} else p.sendMessage("§cArena §6" + args[1] + "§c does not exist.");
					}
					if(args[0].equalsIgnoreCase("create")){
						if(!config.contains(args[1])){
							config.set(args[1].toLowerCase(), p.getLocation());
							plugin.saveConfig();
						} else p.sendMessage("§cArena §6" + args[1] + "§c already exists!");
					}
					if(args[0].equalsIgnoreCase("start")){
						if(config.contains(args[1])){
							p.sendMessage("and now for a stupid ass countdown..");
						} else p.sendMessage("§cArena §6" + args[1] + "§c does not exist.");
					}
					if(args[0].equalsIgnoreCase("end")){
						if(config.contains(args[1])){
							new BukkitRunnable(){
								@Override
								public void run(){
									Bukkit.broadcastMessage("§cRound ending for map : §a" + 
											args[1].toLowerCase() + "§c in §a5 §cseconds!");
								}
							}.runTaskLater(plugin, 1*20);

							new BukkitRunnable(){
								@Override
								public void run(){
									Bukkit.broadcastMessage("§cRound ending for map : §a" +
											args[1].toLowerCase() + "§c in §a4 §cseconds!");
								}
							}.runTaskLater(plugin, 2*20);

							new BukkitRunnable(){
								@Override
								public void run(){
									Bukkit.broadcastMessage("§cRound ending for map : §a" + 
											args[1].toLowerCase() + "§c in §a3 §cseconds!");
								}
							}.runTaskLater(plugin, 3*20);

							new BukkitRunnable(){
								@Override
								public void run(){
									Bukkit.broadcastMessage("§cRound ending for map : §a" + 
											args[1].toLowerCase() + "§c in §a2 §cseconds!");
								}
							}.runTaskLater(plugin, 4*20);

							new BukkitRunnable(){
								@Override
								public void run(){
									Bukkit.broadcastMessage("§cRound ending for map : §a" + 
											args[1].toLowerCase() + "§c in §a1 §csecond!");
								}
							}.runTaskLater(plugin, 5*20);
							new BukkitRunnable(){
								@Override
								public void run(){
									Bukkit.broadcastMessage("§cRound ending for map : §a" + 
											args[1].toLowerCase() + " §cin §anow§c!");
									// stop the round
								}
							}.runTaskLater(plugin, 6*20);							
						} else p.sendMessage("§cArena §6" + args[1] + "§c does not exist.");
					}
					if(args[0].equalsIgnoreCase("buy")){
						String[] skeleton = { "skeleton", "skele", "ske" };
						for(String skele : skeleton)
							if(args[1].equalsIgnoreCase(skele)){
								if(Points.hasEnough(p, config.getDouble("Towers.skeleton.price"))){
									p.getInventory().addItem(new ItemStack(Material.MONSTER_EGG, 1, (short)51));
									p.sendMessage("§aYou have successfully bought an Arrow Skeleton! Use it wisely, only 1 use!");
								} else p.sendMessage("§6You do not have §c" + config.getDouble("Towers.skeleton.price") + "§6 points!");
							}
						String[] zombie = { "zombie", "zomb" };
						for(String zomb : zombie)
							if(args[1].equalsIgnoreCase(zomb)){
								if(Points.hasEnough(p, config.getDouble("Towers.zombie.price"))){
									p.getInventory().addItem(new ItemStack(Material.MONSTER_EGG, 1, (short) 54));
									p.sendMessage("§aYou have successfully bought a Brainless Smasher! Use it wisely, only 1 use!");
								} else p.sendMessage("§6You do not have §c" + config.getDouble("Tower.zombie.price") + "§6 points!");
							}
					}
				}
				if(args.length == 3){
					if(args[0].equalsIgnoreCase("setspawn")){
						if(args[1].equalsIgnoreCase("blue")){
							for(String arenas : plugin.getConfig().getConfigurationSection("Spawns").getKeys(false))
								args[2] = arenas;
							if(config.contains(args[2])){								double x = p.getLocation().getX();
								double y = p.getLocation().getY();
								double z = p.getLocation().getZ();
								double yaw = p.getLocation().getYaw();
								double pitch = p.getLocation().getPitch();
								World world = p.getWorld();
								config.set("Blue." + args[2] + ".spawn.x", Double.valueOf(x));
								config.set("Blue." + args[2] + ".spawn.y", Double.valueOf(y));
								config.set("Blue." + args[2] + ".spawn.z", Double.valueOf(z));
								config.set("Blue." + args[2] + ".spawn.yaw", Double.valueOf(yaw));
								config.set("Blue." + args[2] + ".spawn..pitch", Double.valueOf(pitch));
								config.set("Blue." + args[2] + ".spawn.world", world.getName());
								p.sendMessage("§aSpawn location for §bBlue §ateam is set!");							}
						}
						if(args[1].equalsIgnoreCase("red")){
							for(String arenas : plugin.getConfig().getConfigurationSection("Spawns").getKeys(false))
								args[2] = arenas;
							if(config.contains(args[2])){
								double x = p.getLocation().getX();
								double y = p.getLocation().getY();
								double z = p.getLocation().getZ();
								double yaw = p.getLocation().getYaw();
								double pitch = p.getLocation().getPitch();
								World world = p.getWorld();
								config.set("Red." + args[2] + ".spawn.x", Double.valueOf(x));
								config.set("Red." + args[2] + ".spawn.y", Double.valueOf(y));
								config.set("Red." + args[2] + ".spawn.z", Double.valueOf(z));
								config.set("Red." + args[2] + ".spawn.yaw", Double.valueOf(yaw));
								config.set("Red." + args[2] + ".spawn.pitch", Double.valueOf(pitch));
								config.set("Red." + args[2] + ".spawn.world", world.getName());
								p.sendMessage("§aSpawn location for §4Red §ateam is set!");
							}
						}
					}
				}
				if(args.length == 4){
					if(args[0].equalsIgnoreCase("points")){
						if(args[1].equalsIgnoreCase("add")){
							Player t = Bukkit.getPlayer(args[2]);
							if(Points.isDouble(args[3])){
								double i = Double.parseDouble(args[3]);
								Points.addPoints(t, i);
								p.sendMessage("§9" + t.getName() + " §ahas §9" 
										+ Points.getPoints(t) + "§a points!");
							} else p.sendMessage("§cThe amount must be a number!");
						}
						if(args[1].equalsIgnoreCase("take")){
							Player t = Bukkit.getPlayer(args[2]);
							if(Points.isDouble(args[3])){
								double i = Double.parseDouble(args[3]);
								Points.takePoints(t, i);
								p.sendMessage("§9" + t.getName() + " §ahas §9" 
										+ Points.getPoints(t) + "§a points!");
							} else p.sendMessage("§cThe amount must be a number!");
						}
						if(args[1].equalsIgnoreCase("set")){
							Player t = Bukkit.getPlayer(args[2]);
							if(Points.isDouble(args[3])){
								double i = Double.parseDouble(args[3]);
								Points.setPoints(t, i);
								p.sendMessage("§9" + t.getName() + " §ahas §9" 
										+ Points.getPoints(t) + "§a points!");
							} else p.sendMessage("§cThe amount must be a number!");
						}
						if(args[1].equalsIgnoreCase("show")){
							Player t = Bukkit.getPlayer(args[2]);
							p.sendMessage("§9" + t.getName() + "§a has §9"
									+ Points.getPoints(t) + "§a points!");
						}
					}
				}
			}
		}
		return true;
	}
}