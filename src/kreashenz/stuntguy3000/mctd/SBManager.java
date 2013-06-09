package kreashenz.stuntguy3000.mctd;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
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

		Score tokens = objective.getScore(Bukkit.getOfflinePlayer("§aPoints§7:§c"));
		tokens.setScore(getTokens(p));

		p.setScoreboard(board);
	}

	public int getKills(Player p){
		File file = new File("plugins/MinecraftTD/players/" + p.getName() + ".yml");
		YamlConfiguration aa = YamlConfiguration.loadConfiguration(file);
		if(aa.get("Kills") != null){
			return aa.getInt("Kills");
		} else {
			return 0;
		}
	}

	public int getDeaths(Player p){
		File file = new File("plugins/MinecraftTD/players/" + p.getName() + ".yml");
		YamlConfiguration aa = YamlConfiguration.loadConfiguration(file);
		if(aa.get("Deaths") != null){
			return aa.getInt("Deaths");
		} else {
			return 0;
		}
	}

	public int getTokens(Player p){
		File file = new File("plugins/MinecraftTD/players/" + p.getName() + ".yml");
		YamlConfiguration aa = YamlConfiguration.loadConfiguration(file);
		if(aa.get("Points") != null){
			return aa.getInt("Points");
		} else {
			return 0;
		}
	}

	public void setKills(Player p, int Kills){
		File file = new File("plugins/MinecraftTD/players/" + p.getName() + ".yml");
		YamlConfiguration a = YamlConfiguration.loadConfiguration(file);
		a.set("Kills", Kills);
		try {
			a.save(file);
		} catch (Exception e){e.printStackTrace();}
	}

	public void setDeaths(Player p, int Deaths){
		File file = new File("plugins/MinecraftTD/players/" + p.getName() + ".yml");
		YamlConfiguration a = YamlConfiguration.loadConfiguration(file);
		a.set("Deaths", Deaths);
		try {
			a.save(file);
		} catch (Exception e){e.printStackTrace();}
	}

	public void setTokens(Player p, int Tokens){
		File file = new File("plugins/MinecraftTD/players/" + p.getName() + ".yml");
		YamlConfiguration a = YamlConfiguration.loadConfiguration(file);
		a.set("Points", Tokens);
		try {
			a.save(file);
		} catch (Exception e){e.printStackTrace();}
	}
}
