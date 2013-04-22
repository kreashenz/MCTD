package kreashenz.stuntguy3000.mctd;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class MCTD extends JavaPlugin {

	TeamManager teams = new TeamManager(this);
	SBManager sb = new SBManager(this);
	Enums enums = new Enums();
	Events events = new Events(this);

	FreezeTask freezeTask = new FreezeTask();

	public void onEnable(){
		getServer().getScheduler().runTaskTimer(this, freezeTask, 0L, 1L);
		getLogger().info("Minecraft Tower Defense is now loaded!");
		registerListeners();
		getCommand("td").setExecutor(new TDCommands(this));
		setupTimer();
		if(!(new File("plugins/MinecraftTD/config.yml")).exists()){
			saveResource("config.yml", false);
		}
	}

	public void registerListeners(){
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new Events(this), this);
		pm.registerEvents(new Upgrade(this), this);
	}

	public void sendHelp(Player p){
		if(!p.hasPermission("td.*")){
			// send them the NON op commands.
		} else {
			p.sendMessage("§2+----------------------------------------------------");
			p.sendMessage("§2|| §a/td setspawn <blue | red>");
			p.sendMessage("§2|| §a/td join <blue | red>");
			p.sendMessage("§2|| §a/td delete <arena name>");
			p.sendMessage("§2|| §a/td start <arena name>");
			p.sendMessage("§2|| §a/td setstart <arena name>");
			p.sendMessage("§2|| §a/td setend <arena name>");
			p.sendMessage("§2|| §a/td reload");
			p.sendMessage("§2+----------------------------------------------------");

		}
	}

	public void setupTimer(){
		Bukkit.getServer().getScheduler().runTaskTimer(this, new Runnable(){
			@Override
			public void run() {
				for (Player p : Bukkit.getOnlinePlayers()){
					sb.setScoreboard(p);
				}
			}
		}, 0L, 20L);
	}

}