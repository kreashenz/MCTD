package kreashenz.stuntguy3000.mctd;

import java.util.ArrayList;
import java.util.List;

import kreashenz.stuntguy3000.mctd.teams.TeamManager;

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
import org.kitteh.tag.TagAPI;

public class TDCommands implements CommandExecutor {

	public MCTD plugin;
	public TDCommands(MCTD plugin){this.plugin=plugin;}

	public List<String> inGame = new ArrayList<String>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, final String[] args){
		FileConfiguration config = plugin.getConfig();
		TeamManager a = plugin.teams;
		final Player p = (Player)sender;
		if(cmd.getName().equalsIgnoreCase("td")){
			if(sender instanceof Player){
				if(p.hasPermission("td.help")){
					if(args.length == 0){
						plugin.sendHelp(p);
					}
				} else noPerm(p);
				if(args.length == 1){
					if(p.hasPermission("td.points")){
						if(args[0].equalsIgnoreCase("points")){
							p.sendMessage("§aYou have §9" + Points.getPoints(p) + " §apoints!");
						}
					} else noPerm(p);
					if(p.hasPermission("td.setspawn")){
						if(args[0].equalsIgnoreCase("setspawn")){
							p.sendMessage("§cUsage : §f/td setspawn <blue | red>");
						}
					} else noPerm(p);
					if(p.hasPermission("td.reload")){
						if(args[0].equalsIgnoreCase("reload")){
							plugin.reloadConfig();
							p.sendMessage("§aConfig successfully reloaded!");
						}
					} else noPerm(p);
					if(p.hasPermission("td.team")){
						if(args[0].equalsIgnoreCase("team")){
							p.sendMessage("§cUsage : §f/td team <red | blue | leave>");
						}
					} else noPerm(p);
				}
				if(args.length == 2){
					if(p.hasPermission("td.points.others")){
						if(args[0].equalsIgnoreCase("points")){
							Player t = Bukkit.getPlayer(args[1]);
							if(t != null && t.isOnline()){
								p.sendMessage("§9" + t.getName() + "§a has §9" + Points.getPoints(t) + " §apoints!");
							}
						}
					} else noPerm(p);
					if(p.hasPermission("td.team.manage")){
						if(args[0].equalsIgnoreCase("team")){
							String[] leave = { "leave", "quit" };
							for(String quit : leave)
								if(args[1].equalsIgnoreCase(quit)){
									if(plugin.teams.playerIsOnBlue(p) || plugin.teams.playerIsOnRed(p)){
										plugin.teams.setIsNotPlaying(p);
										inGame.remove(p.getName());
										TagAPI.refreshPlayer(p);
									} else p.sendMessage("§cYou must be on a team to leave it!");
								}
							if(args[1].equalsIgnoreCase("blue")){
								if(a.playerIsNotPlaying(p) && !a.playerIsOnRed(p)){
									a.isOnBlue.add(p.getName());
									inGame.add(p.getName());
									p.sendMessage("§aJoined the §bblue §ateam!");
									TagAPI.refreshPlayer(p);
								} else p.sendMessage("§cYou have already chosen your team.");
							}
							if(args[1].equalsIgnoreCase("red")){
								if(a.playerIsNotPlaying(p) && !a.playerIsOnBlue(p)){
									a.isOnRed.add(p.getName());
									inGame.add(p.getName());
									p.sendMessage("§aJoined the §4red §ateam!");
									TagAPI.refreshPlayer(p);
								} else p.sendMessage("§cYou have already chosen your team.");
							}
							if(!(args[1].equalsIgnoreCase("red") || args[1].equalsIgnoreCase("blue"))){
								p.sendMessage("§cYou can only join the §4Red §cor §bBlue §cteams!");
							}
						}
					} else noPerm(p);
					if(p.hasPermission("td.delete")){
						if(args[0].equalsIgnoreCase("delete")){
							if(config.contains(args[1])){
								config.set(args[1].toLowerCase(), null);
								plugin.saveConfig();
							} else p.sendMessage("§cArena §6" + args[1] + "§c does not exist.");
						}
					} else noPerm(p);
					if(p.hasPermission("td.create")){
						if(args[0].equalsIgnoreCase("create")){
							if(!config.contains(args[1])){
								config.set(args[1].toLowerCase(), p.getLocation());
								plugin.saveConfig();
							} else p.sendMessage("§cArena §6" + args[1] + "§c already exists!");
						}
					} else noPerm(p);
					if(p.hasPermission("td.ready")){
						if(args[0].equalsIgnoreCase("ready")){
							if(config.contains(args[1])){
								p.sendMessage("and now for a stupid ass countdown..");
							} else p.sendMessage("§cArena §6" + args[1] + "§c does not exist.");
						}
					} else noPerm(p);
					if(p.hasPermission("td.end")){
						if(args[0].equalsIgnoreCase("end")){
							if(config.contains(args[1])){
								plugin.tell(p, "&apoonany");
								new BukkitRunnable(){
									int time = 60;
									@Override
									public void run() {
										if(time % 15 == 0 || (time % 5 == 0 && time < 15) || (time < 4)){
											for(Player p : Bukkit.getOnlinePlayers())
												if(p != null){
													p.sendMessage("§cGame ending in §a" + time + " §cseconds!");
												}
										}
										time--;
										if(time == 60){
											plugin.sendAll("&cGame ending in &a"+time+"&c seconds.");
										}
										if(time == 30){
											plugin.sendAll("&cGame ending in &a"+time+"&c seconds.");
										}
										if(time == 15){
											plugin.sendAll("&cGame ending in &a"+time+"&c seconds.");
										}
										if(time == 5){
											plugin.sendAll("&cGame ending in &a"+time+"&c seconds.");
										}
										if(time == 4){
											plugin.sendAll("&cGame ending in &a"+time+"&c seconds.");
										}
										if(time == 3){
											plugin.sendAll("&cGame ending in &a"+time+"&c seconds.");
										}
										if(time == 2){
											plugin.sendAll("&cGame ending in &a"+time+"&c seconds.");
										}
										if(time == 1){
											plugin.sendAll("&cGame ending in &a"+time+"&c seconds.");
										}
										if(time == 0){
											plugin.sendAll("&cGame ending in &anow&c.");
											this.cancel();
										}
										this.cancel();
									}
								}.runTaskTimer(plugin, 0L, 20L);
							} else p.sendMessage("§cArena §6" + args[1] + "§c does not exist.");
						}
					} else noPerm(p);
					if(p.hasPermission("td.buy")){
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
				} else noPerm(p);
				if(p.hasPermission("td.setspawn")){
					if(args[0].equalsIgnoreCase("setspawn")){
						if(args[1].equalsIgnoreCase("blue")){
							//for(String arenas : plugin.getConfig().getConfigurationSection("Spawns").getKeys(false))
							//args[2] = arenas;
							//if(config.contains(args[2])){							double x = p.getLocation().getX();
							double y = p.getLocation().getY();
							double z = p.getLocation().getZ();
							double yaw = p.getLocation().getYaw();
							double pitch = p.getLocation().getPitch();
							World world = p.getWorld();
							config.set("Blue"/* + args[2] + */ + ".spawn.x", Double.valueOf(x));
							config.set("Blue"/* + args[2] + */ + ".spawn.y", Double.valueOf(y));
							config.set("Blue"/* + args[2] + */ + ".spawn.z", Double.valueOf(z));
							config.set("Blue"/* + args[2] + */ + ".spawn.yaw", Double.valueOf(yaw));
							config.set("Blue"/* + args[2] + */ + ".spawn.pitch", Double.valueOf(pitch));
							config.set("Blue"/* + args[2] + */ + ".spawn.world", world.getName());
							p.sendMessage("§aSpawn location for §bBlue §ateam is set!");						}
						if(args[1].equalsIgnoreCase("red")){
							//for(String arenas : plugin.getConfig().getConfigurationSection("Spawns").getKeys(false))
							//args[2] = arenas;
							//if(config.contains(args[2])){
							double x = p.getLocation().getX();
							double y = p.getLocation().getY();
							double z = p.getLocation().getZ();
							double yaw = p.getLocation().getYaw();
							double pitch = p.getLocation().getPitch();
							World world = p.getWorld();
							config.set("Red"/* + args[2]*/ + ".spawn.x", Double.valueOf(x));
							config.set("Red"/* + args[2]*/ + ".spawn.y", Double.valueOf(y));
							config.set("Red"/* + args[2]*/ + ".spawn.z", Double.valueOf(z));
							config.set("Red"/* + args[2]*/ + ".spawn.yaw", Double.valueOf(yaw));
							config.set("Red"/* + args[2]*/ + ".spawn.pitch", Double.valueOf(pitch));
							config.set("Red"/* + args[2]*/ + ".spawn.world", world.getName());
							p.sendMessage("§aSpawn location for §4Red §ateam is set!");
						}
						for(String blue : plugin.blueName){
							for(String red : plugin.redName){
								if(!(args[1].equalsIgnoreCase(blue) || args[1].equalsIgnoreCase(red))){
									p.sendMessage("§cYou can only set spawn for the §bBlue §cor §4Red §cteam.");
								}
							}
						}
					}
				} else noPerm(p);
				if(args.length == 4){
					if(p.hasPermission("td.points.manage")){
						if(args[0].equalsIgnoreCase("points")){
							for(Player player : Bukkit.getOnlinePlayers()){
								if(args[1].equalsIgnoreCase("add") && !args[1].equalsIgnoreCase(player.getName())){
									Player t = Bukkit.getPlayer(args[2]);
									if(Points.isDouble(args[3])){
										double i = Double.parseDouble(args[3]);
										Points.addPoints(t, i);
										p.sendMessage("§9" + t.getName() + " §ahas §9" 
												+ Points.getPoints(t) + "§a points!");
									} else p.sendMessage("§cThe amount must be a number!");
								}
								if(args[1].equalsIgnoreCase("take") && !args[1].equalsIgnoreCase(player.getName())){
									Player t = Bukkit.getPlayer(args[2]);
									if(Points.isDouble(args[3])){
										double i = Double.parseDouble(args[3]);
										Points.takePoints(t, i);
										p.sendMessage("§9" + t.getName() + " §ahas §9" 
												+ Points.getPoints(t) + "§a points!");
									} else p.sendMessage("§cThe amount must be a number!");
								}
								if(args[1].equalsIgnoreCase("set") && !args[1].equalsIgnoreCase(player.getName())){
									Player t = Bukkit.getPlayer(args[2]);
									if(Points.isDouble(args[3])){
										double i = Double.parseDouble(args[3]);
										Points.setPoints(t, i);
										p.sendMessage("§9" + t.getName() + " §ahas §9" 
												+ Points.getPoints(t) + "§a points!");
									} else p.sendMessage("§cThe amount must be a number!");
								}
								if(args[1].equalsIgnoreCase("show") && !args[1].equalsIgnoreCase(player.getName())){
									Player t = Bukkit.getPlayer(args[2]);
									if(t == null){
										p.sendMessage("§cThat player can't be found.");
									} else {
										p.sendMessage("§9" + t.getName() + "§a has §9"
												+ Points.getPoints(t) + "§a points!");
									}
								}
							}
						}
					}
				} else noPerm(p);
			} else p.sendMessage("§cYou must be a player to use the TowerDefense commands.");
		}
		return true;
	}

	private void noPerm(Player p){
		p.sendMessage("§cYou do not have permission to use this command.");
	}
}
