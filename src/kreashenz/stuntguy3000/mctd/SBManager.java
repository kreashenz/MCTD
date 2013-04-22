package kreashenz.stuntguy3000.mctd;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class SBManager {

	public MCTD plugin;
	public SBManager(MCTD plugin){this.plugin=plugin;}

	public void setScoreboard(Player p){
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getNewScoreboard();

		Objective objective = board.registerNewObjective("test", "dummy");

		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		objective.setDisplayName("       §b§lYour Stats       ");

		Score kills = objective.getScore(Bukkit.getOfflinePlayer("§aKills§7:§c"));
		kills.setScore(getKills(p));

		Score deaths = objective.getScore(Bukkit.getOfflinePlayer("§aDeaths§7:§c"));
		deaths.setScore(getDeaths(p));

		Score wave = objective.getScore(Bukkit.getOfflinePlayer("§aWave§7:§c"));
		wave.setScore(getWave(p));

		Score tokens = objective.getScore(Bukkit.getOfflinePlayer("§aPoints§7:§c"));
		tokens.setScore(getTokens(p));

		p.setScoreboard(board);
	}

	public int getKills(Player p){
		File file = new File("plugins/MinecraftTD/players" + p.getName() + ".yml");
		YamlConfiguration aa = YamlConfiguration.loadConfiguration(file);
		return aa.getInt("Kills");
	}

	public int getDeaths(Player p){
		File file = new File("plugins/MinecraftTD/players" + p.getName() + ".yml");
		YamlConfiguration aa = YamlConfiguration.loadConfiguration(file);
		return aa.getInt("Deaths");
	}

	public int getWave(Player p){
		return plugin.getConfig().getInt(p.getName() + ".wave");
	}

	public int getTokens(Player p){
		File file = new File("plugins/MinecraftTD/players" + p.getName() + ".yml");
		YamlConfiguration aa = YamlConfiguration.loadConfiguration(file);
		return aa.getInt("Points.points");
	}

	public void setKills(Player p, int Kills){
		plugin.getConfig().set(p.getName() + ".kills", Kills);
		plugin.saveConfig();
	}

	public void setDeaths(Player p, int Deaths){
		File file = new File("plugins/MinecraftTD/players/" + p.getName() + ".yml");
		YamlConfiguration a = YamlConfiguration.loadConfiguration(file);
		a.set(p.getName() + ".deaths", Deaths);
		try {
			a.save(file);
		} catch (Exception e){e.printStackTrace();}
	}

	public void setTokens(Player p, int Tokens){
		plugin.getConfig().set(p.getName() + ".points", Tokens);
		plugin.saveConfig();
	}

	public void setWave(final Player p, int Waves){
		new BukkitRunnable() {
			@Override
			public void run() {
				setWave(p, plugin.getConfig().getInt(p.getName() + ".wave") +1);
			}
		}.runTaskLater(plugin, plugin.getConfig().getInt("Time-Between-Each-Round")*20);
		plugin.getConfig().set(p.getName() + ".wave", Waves +1);
		plugin.saveConfig();
	}

	public void setRedTeam(Player p){
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getNewScoreboard();
		Objective objective = board.registerNewObjective("§0[§4Red§0]§7", "dummy");
		if(plugin.teams.playerIsOnRed(p)){
			objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
		}
		p.setScoreboard(board);
	}

	public void setBlueTeam(Player p){
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getNewScoreboard();
		Objective objective = board.registerNewObjective("§0[§1Blue§0]§7", "dummy");
		if(plugin.teams.playerIsOnBlue(p)){
			objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
		}
	}
}
