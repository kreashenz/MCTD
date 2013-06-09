package kreashenz.stuntguy3000.mctd;

import java.io.File;

import kreashenz.stuntguy3000.mctd.teams.TeamManager;

import org.kitteh.tag.TagAPI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class MCTD extends JavaPlugin {

	public TeamManager teams = new TeamManager(this);
	public SBManager sb = new SBManager(this);
	public Enums enums = new Enums();
	public Events events = new Events(this);
	public FreezeTask freezeTask = new FreezeTask();

	public String[] blueName = { "blue", "b" };
	public String[] redName = { "red", "r" };

	public void onEnable(){
		getServer().getScheduler().runTaskTimer(this, freezeTask, 0L, 1L);
		getLogger().info("Minecraft Tower Defense v"+ getDescription().getVersion() + " is now loaded!");
		getLogger().info("Minecraft Tower Defense is now loaded!");
		
		registerListeners();
		
		getCommand("td").setExecutor(new TDCommands(this));
		
		infDurability();
		setupTimer();
		
		if(!TagAPI(getServer().getPluginManager())){
			getLogger().severe("Disabling Minecraft TD because TagAPI is needed for this plugin to work.");
			getServer().getPluginManager().disablePlugin(this);
		}
		if(!new File("plugins/MinecraftTD/config.yml").exists()){
			saveResource("config.yml", false);
		}

	}

	private void registerListeners(){
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new Events(this), this);
		pm.registerEvents(new Upgrade(this), this);
	}

	public void sendHelp(Player p){
		if(!p.hasPermission("td.*") || !p.isOp()){
			tell(p, "§6§l================== §9§oMCTD Help§r§6§l ===================");
			tell(p, "§9/td §1: §6Shows this page");
			tell(p, "§9/td join <arena> §1: §6Join your desired arena");
			tell(p, "§9/td start §1: §6Flag yourself as ready.");
			tell(p, "§9/td team <blue | red | leave> §1: §6Join a team, or leave the team");
			tell(p, "§9/td buy <tower> §1: §6Buy the specified tower");
			tell(p, "§9/td list §1: §6List the available towers");
			tell(p, "§6§l================== §9§oMCTD Help§r§6§l ===================");
		} else {
			tell(p, "§6§l================== §9§oMCTD Help§r§6§l ===================");
			tell(p, "§9/td §1: §6Shows this page");
			tell(p, "§9/td setspawn <arena> <blue | red> §1: §6Set the spawn for a team");
			tell(p, "§9/td join <arena> §1: §6Join your desired arena");
			tell(p, "§9/td start §1: §6Flag yourself as ready.");
			tell(p, "§9/td team <blue | red | leave> §1: §6Join a team, or leave the team");
			tell(p, "§9/td buy <tower> §1: §6Buy the specified tower");
			tell(p, "§9/td list §1: §6List the available towers");
			tell(p, "§9/td points <add | take | set | player> §1: §6Add, set, take or check points");
			tell(p, "§6§l================== §9§oMCTD Help§r§6§l ===================");
		}
	}

	private boolean TagAPI(PluginManager pm) {
		Plugin tag = pm.getPlugin("TagAPI");
		if(tag != null && tag instanceof TagAPI){
			getLogger().info("Loaded TagAPI v" + tag.getDescription().getVersion()
					+ " now allowing coloured tags.");
			getLogger().info("Hooked into TagAPI. - Allowing coloured name tags.");
			return true;
		} else {
			getLogger().severe("TagAPI cannot be found. Disabling MCTD.");
			pm.disablePlugin(this);
			return false;
		}
	}

	private void setupTimer(){
		Bukkit.getServer().getScheduler().runTaskTimer(this, new Runnable(){
			@Override
			public void run() {
				for (Player p : Bukkit.getOnlinePlayers()){
					sb.setScoreboard(p);
				}
			}
		}, 0L, 10L);
	}

	@SuppressWarnings("deprecation")
	private void infDurability(){
		for(Player s : Bukkit.getOnlinePlayers()){
			for(ItemStack ia : s.getInventory().getArmorContents())
				ia.setDurability(ia.getType().getMaxDurability());
			s.updateInventory();
		}
	}

	public void sendAll(String message){
		for(Player p : Bukkit.getOnlinePlayers())
			p.sendMessage(ColourUtils.format(message));
	}

	public void tell(Player p, String message){
		if(p == null)return;
		p.sendMessage(ColourUtils.format(message));
	}

	public void tell(CommandSender sender, String message){
		if(sender == null)return;
		sender.sendMessage(ColourUtils.format(message));
	}

	public void error(CommandSender sender, String message){
		if(sender == null)return;
		sender.sendMessage(ColourUtils.format("§4Error : §c" + message));
	}

	public void error(Player p, String message){
		if(p == null)return;
		p.sendMessage(ColourUtils.format("§4Error : §c" + message));
	}

	public static enum ColourUtils {
		BLACK("&0", ChatColor.BLACK.toString()), 
		DARK_BLUE("&1", ChatColor.DARK_BLUE.toString()), 
		DARK_GREEN("&2", ChatColor.DARK_GREEN.toString()), 
		DARK_AQUA("&3", ChatColor.DARK_AQUA.toString()), 
		DARK_RED("&4", ChatColor.DARK_RED.toString()), 
		DARK_PURPLE("&5", ChatColor.DARK_PURPLE.toString()), 
		GOLD("&6", ChatColor.GOLD.toString()), 
		GRAY("&7", ChatColor.GRAY.toString()), 
		DARK_GRAY("&8", ChatColor.DARK_GRAY.toString()), 
		BLUE("&9", ChatColor.BLUE.toString()), 
		GREEN("&a", ChatColor.GREEN.toString()), 
		AQUA("&b", ChatColor.AQUA.toString()), 
		RED("&c", ChatColor.RED.toString()), 
		LIGHT_PURPLE("&d", ChatColor.LIGHT_PURPLE.toString()), 
		YELLOW("&e", ChatColor.YELLOW.toString()), 
		WHITE("&f", ChatColor.WHITE.toString()), 
		MAGIC("&k", ChatColor.MAGIC.toString()), 
		BOLD("&l", ChatColor.BOLD.toString()), 
		STRIKETHROUGH("&m", ChatColor.STRIKETHROUGH.toString()), 
		UNDERLINE("&n", ChatColor.UNDERLINE.toString()), 
		ITALIC("&o", ChatColor.ITALIC.toString()), 
		RESET("&r", ChatColor.RESET.toString());

		private final String input;
		private final String MinecraftColor;

		private ColourUtils(String input, String MinecraftColor) { this.input = input;
		this.MinecraftColor = MinecraftColor; }

		public String getMinecraftColor()
		{
			return this.MinecraftColor;
		}

		public String getInput() {
			return this.input;
		}
		public static String format(String message) {
			String msg = message;
			for (ColourUtils c : values()) {
				msg = msg.replace(c.getInput(), c.getMinecraftColor());
			}
			return msg;
		}
	}
}