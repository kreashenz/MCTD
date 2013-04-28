package kreashenz.stuntguy3000.mctd;

import org.bukkit.Bukkit;
import org.bukkit.Location;
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

	// TODO : Fix multiple commands.
	/*

     Fix MANY MANY of the non working commands, as /td start, /td end, /td setspawn(s)
     /td create, /td delete.

	 */
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
					if(args[0].equalsIgnoreCase("team")){
						p.sendMessage("§cUsage : §f/td team <red | blue | leave>");
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
								if(a.playerIsOnBlue(p) || a.playerIsOnRed(p)){
									plugin.tell(p, "§aLeft the team!");
									a.setIsNotPlaying(p);
								} else p.sendMessage("§cYou must be on a team to leave it!");
							}
						for(String blue : plugin.blueName)
							if(args[1].equalsIgnoreCase(blue)){
								if(a.playerIsNotPlaying(p) && !a.playerIsOnRed(p)){
									a.isOnBlue.add(p.getName());
									p.sendMessage("§aJoined the §bblue §ateam!");
									p.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
									p.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
									p.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
									p.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
									p.getInventory().setItemInHand(new ItemStack(Material.DIAMOND_SWORD));
									if(plugin.getConfig().getString("Blue.spawn") != null){
										double x = plugin.getConfig().getDouble("Blue.spawn.x");
										double y = plugin.getConfig().getDouble("Blue.spawn.y");
										double z = plugin.getConfig().getDouble("Blue.spawn.z");
										double yaw = plugin.getConfig().getDouble("Blue.spawn.yaw");
										double pitch = plugin.getConfig().getDouble("Blue.spawn.pitch");
										String world = plugin.getConfig().getString("Blue.spawn.world");
										Location loc = p.getLocation();
										if(Bukkit.getWorld(world) == null){
											p.sendMessage("§cThere's no spawn point for §bBlue §cteam!!!");
										} else {
											loc.setX(x);
											loc.setY(y);
											loc.setZ(z);
											loc.setYaw((float)yaw);
											loc.setPitch((float)pitch);
											loc.setWorld(Bukkit.getWorld(world));
											p.teleport(loc);
										}
									} else p.sendMessage("§cThere's no spawn point for §bBlue §cteam!!!");
								} else p.sendMessage("§cYou have already chosen your team.");
							}
						for(String red : plugin.redName)
							if(args[1].equalsIgnoreCase(red)){
								if(a.playerIsNotPlaying(p) && !a.playerIsOnBlue(p)){
									
									a.isOnRed.add(p.getName());
									p.sendMessage("§aJoined the §4red §ateam!");
									p.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
									p.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
									p.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
									p.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
									p.getInventory().setItemInHand(new ItemStack(Material.DIAMOND_SWORD));
									if(plugin.getConfig().getString("Red.spawn") != null){
										double x = plugin.getConfig().getDouble("Red.spawn.x");
										double y = plugin.getConfig().getDouble("Red.spawn.y");
										double z = plugin.getConfig().getDouble("Red.spawn.z");
										double yaw = plugin.getConfig().getDouble("Red.spawn.yaw");
										double pitch = plugin.getConfig().getDouble("Red.spawn.pitch");
										String world = plugin.getConfig().getString("Red.spawn.world");
										Location loc = p.getLocation();
										if(Bukkit.getWorld(world) == null){
											p.sendMessage("§cThere's no spawn point for §4Red §cteam!!!");
										} else {
											loc.setX(x);
											loc.setY(y);
											loc.setZ(z);
											loc.setYaw((float)yaw);
											loc.setPitch((float)pitch);
											loc.setWorld(Bukkit.getWorld(world));
											p.teleport(loc);
										}
									} else p.sendMessage("§cThere's no spawn point for §4Red §cteam!!!");
								} else p.sendMessage("§cYou have already chosen your team.");
							}
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
							new BukkitRunnable(){
								@Override
								public void run(){
									Bukkit.broadcastMessage("§cRound starting for map : §a" + 
											args[1].toLowerCase() + "§c in §a5 §cseconds!");
								}
							}.runTaskLater(plugin, 1*20);

							new BukkitRunnable(){
								@Override
								public void run(){
									Bukkit.broadcastMessage("§cRound starting for map : §a" +
											args[1].toLowerCase() + "§c in §a4 §cseconds!");
								}
							}.runTaskLater(plugin, 2*20);

							new BukkitRunnable(){
								@Override
								public void run(){
									Bukkit.broadcastMessage("§cRound starting for map : §a" + 
											args[1].toLowerCase() + "§c in §a3 §cseconds!");
								}
							}.runTaskLater(plugin, 3*20);

							new BukkitRunnable(){
								@Override
								public void run(){
									Bukkit.broadcastMessage("§cRound starting for map : §a" + 
											args[1].toLowerCase() + "§c in §a2 §cseconds!");
								}
							}.runTaskLater(plugin, 4*20);

							new BukkitRunnable(){
								@Override
								public void run(){
									Bukkit.broadcastMessage("§cRound starting for map : §a" + 
											args[1].toLowerCase() + "§c in §a1 §csecond!");
								}
							}.runTaskLater(plugin, 5*20);
							new BukkitRunnable(){
								@Override
								public void run(){
									Bukkit.broadcastMessage("§cRound starting for map : §a" + 
											args[1].toLowerCase() + " §cin §anow§c!");
									for(Player p : Bukkit.getOnlinePlayers())
										p.setWalkSpeed((float)0.5);
								}
							}.runTaskLater(plugin, 6*20);                            
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
									for(Player p : Bukkit.getOnlinePlayers())
										p.kickPlayer("§cThe game has ended!");
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
				if(args.length == 2){
					if(args[0].equalsIgnoreCase("setspawn")){
						for(String blue : plugin.blueName)
							if(args[1].equalsIgnoreCase(blue)){
								double x = p.getLocation().getX();
								double y = p.getLocation().getY();
								double z = p.getLocation().getZ();
								double yaw = p.getLocation().getYaw();
								double pitch = p.getLocation().getPitch();
								World world = p.getWorld();
								config.set("Blue" /*." + args[2] + */ +".spawn.x", Double.valueOf(x));
								config.set("Blue" /*." + args[2] + */ +".spawn.y", Double.valueOf(y));
								config.set("Blue" /*." + args[2] + */ +".spawn.z", Double.valueOf(z));
								config.set("Blue" /*." + args[2] + */ +".spawn.yaw", Double.valueOf(yaw));
								config.set("Blue" /*." + args[2] + */ +".spawn.pitch", Double.valueOf(pitch));
								config.set("Blue" /*." + args[2] + */ +".spawn.world", world.getName());
								p.sendMessage("§aSpawn location for §bBlue §ateam is set!");							}
						for(String red : plugin.redName)
							if(args[1].equalsIgnoreCase(red)){
								double x = p.getLocation().getX();
								double y = p.getLocation().getY();
								double z = p.getLocation().getZ();
								double yaw = p.getLocation().getYaw();
								double pitch = p.getLocation().getPitch();
								World world = p.getWorld();
								config.set("Red." /*+ args[2] */+ ".spawn.x", Double.valueOf(x));
								config.set("Red." /*+ args[2] */+ ".spawn.y", Double.valueOf(y));
								config.set("Red." /*+ args[2] */+ ".spawn.z", Double.valueOf(z));
								config.set("Red." /*+ args[2] */+ ".spawn.yaw", Double.valueOf(yaw));
								config.set("Red." /*+ args[2] */+ ".spawn.pitch", Double.valueOf(pitch));
								config.set("Red." /*+ args[2] */+ ".spawn.world", world.getName());
								p.sendMessage("§aSpawn location for §4Red §ateam is set!");
							}
					}
				}
				if(args.length == 4){
					if(args[0].equalsIgnoreCase("points")){
						if(args[1].equalsIgnoreCase("add")){
							Player t = Bukkit.getPlayer(args[2]);
							if(t != null && t.isOnline()){
								if(Points.isDouble(args[3])){
									double i = Double.parseDouble(args[3]);
									Points.addPoints(t, i);
									p.sendMessage("§9" + t.getName() + " §anow has §9" 
											+ Points.getPoints(t) + "§a points!");
								} else p.sendMessage("§cThe amount must be a number!");
							} else plugin.error(p, "Player was not found.");
						}
						if(args[1].equalsIgnoreCase("take")){
							Player t = Bukkit.getPlayer(args[2]);
							if(t != null && t.isOnline()){
								if(Points.isDouble(args[3])){
									double i = Double.parseDouble(args[3]);
									Points.takePoints(t, i);
									p.sendMessage("§9" + t.getName() + " §anow has §9" 
											+ Points.getPoints(t) + "§a points!");
								} else plugin.error(p, "The amount must be a number!");
							} else plugin.error(p, "Player was not found.");
						}
						if(args[1].equalsIgnoreCase("set")){
							Player t = Bukkit.getPlayer(args[2]);
							if(t != null && t.isOnline()){
								if(Points.isDouble(args[3])){
									double i = Double.parseDouble(args[3]);
									Points.setPoints(t, i);
									p.sendMessage("§9" + t.getName() + " §a now has §9" 
											+ Points.getPoints(t) + "§a points!");
								} else plugin.error(p, "The amount must be a number!");
							} else plugin.error(p, "Player was not found.");
						}
						if(args[1].equalsIgnoreCase("show")){
							Player t = Bukkit.getPlayer(args[2]);
							if(t != null && t.isOnline()){
								plugin.tell(p, "§9" + t.getName() + "§a has §9"
										+ Points.getPoints(t) + "§a points!");
							} else plugin.error(p, "Player was not found.");
						}
					}
				}
			}
		}
		return true;
	}
}