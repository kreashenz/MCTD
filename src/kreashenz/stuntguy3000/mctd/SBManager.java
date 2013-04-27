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
		File file = new File("plugins/MinecraftTD/players" + p.getName() + ".yml");
		YamlConfiguration aa = YamlConfiguration.loadConfiguration(file);
		return aa.getInt("Kills");
	}

	public int getDeaths(Player p){
		File file = new File("plugins/MinecraftTD/players" + p.getName() + ".yml");
		YamlConfiguration aa = YamlConfiguration.loadConfiguration(file);
		return aa.getInt("Deaths");
	}

	public int getTokens(Player p){
		File file = new File("plugins/MinecraftTD/players" + p.getName() + ".yml");
		YamlConfiguration aa = YamlConfiguration.loadConfiguration(file);
		return aa.getInt("Points");
<<<<<<< HEAD
=======
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

	public void setWave(final Player p, int Waves){
		new BukkitRunnable() {
			@Override
			public void run() {
				setWave(p, plugin.getConfig().getInt(p.getName() + ".wave") +1);
			}
		}.runTaskLater(plugin, plugin.getConfig().getInt("Time-Between-Each-Round")*20);
		plugin.getConfig().set(p.getName() + ".wave", Waves +1);
		plugin.saveConfig();
>>>>>>> b953a5352d6cc9605b5a8315d9cce3ccad0deb91
	}

	/*
	public void setRedTeam(Player p){
<<<<<<< HEAD
		plugin.teams.isOnRed.add(p.getName());
	}

	public void setBlueTeam(Player p){
		plugin.teams.isOnBlue.add(p.getName());
	}

}
=======
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getNewScoreboard();
		Objective objective = board.registerNewObjective("§0[§4Red§0]§7", "§0[§4Red§0]§7");
		if(plugin.teams.playerIsOnRed(p)){
			objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
		}
		p.setScoreboard(board);
	}

	public void setBlueTeam(Player p){
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getNewScoreboard();
		Objective objective = board.registerNewObjective("§0[§1Blue§0]§7", "§0[§1Blue§0]§7");
		if(plugin.teams.playerIsOnBlue(p)){
			objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
		}
		p.setScoreboard(board);
	}
	 */
}
>>>>>>> b953a5352d6cc9605b5a8315d9cce3ccad0deb91
