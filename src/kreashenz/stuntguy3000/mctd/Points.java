package kreashenz.stuntguy3000.mctd;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class Points {

	private Points() {
<<<<<<< HEAD
		// why do this?? i dont understand why to leave it empty.
	}

	public static void addPoints(Player p, double points) {
		if (p == null)
=======
		// TODO: Do not instantiate
	}

	public static void addPoints(Player p, double points) {
		if (isNull(p))
>>>>>>> b953a5352d6cc9605b5a8315d9cce3ccad0deb91
			return;
		File aa = new File("plugins/MinecraftTD/players/" + p.getName().toLowerCase() + ".yml");
		FileConfiguration a = YamlConfiguration.loadConfiguration(aa);
		a.set("Points.points", Double.valueOf(a.getDouble("Points.points") + points));
		try {
			a.save(aa);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void takePoints(Player p, double points) {
<<<<<<< HEAD
		if (p == null)
=======
		if (isNull(p))
>>>>>>> b953a5352d6cc9605b5a8315d9cce3ccad0deb91
			return;
		File aa = new File("plugins/MinecraftTD/players/" + p.getName().toLowerCase() + ".yml");
		FileConfiguration a = YamlConfiguration.loadConfiguration(aa);
		a.set("Points.points", Double.valueOf(a.getDouble("Points.points") - points));
		try {
			a.save(aa);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setPoints(Player p, double points) {
<<<<<<< HEAD
		if (p == null)
=======
		if (isNull(p))
>>>>>>> b953a5352d6cc9605b5a8315d9cce3ccad0deb91
			return;
		File aa = new File("plugins/MinecraftTD/players/" + p.getName().toLowerCase() + ".yml");
		FileConfiguration a = YamlConfiguration.loadConfiguration(aa);
		a.set("Points.points", Double.valueOf(points));
		try {
			a.save(aa);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void addPoints(String name, double points) {
		Player player = Bukkit.getPlayer(name);
		if (player == null) {
			// TODO: Error message stuff...
			return;
		}
		addPoints(player, points);
	}

	public static void takePoints(String name, double points) {
		Player player = Bukkit.getPlayer(name);
		if (player == null) {
			// TODO: Error message stuff...
			return;
		}
		takePoints(player, points);
	}

	public static void setPoints(String name, double points) {
		Player player = Bukkit.getPlayer(name);
		if (player == null) {
			// TODO: Error message stuff...
			return;
		}
		setPoints(player, points);
	}

	public static Double getPoints(Player p) {
<<<<<<< HEAD
		if (p == null)
=======
		if (isNull(p))
>>>>>>> b953a5352d6cc9605b5a8315d9cce3ccad0deb91
			return Double.valueOf(0.0D);
		File aa = new File("plugins/MinecraftTD/players/" + p.getName().toLowerCase() + ".yml");
		FileConfiguration a = YamlConfiguration.loadConfiguration(aa);
		return Double.valueOf(Math.round(a.getDouble("Points.points") * 100.0D) / 100.0D);
	}

	public static Double getPoints(String name) {
		Player player = Bukkit.getPlayer(name);
		if (player == null) {
			// TODO: Error message stuff...
			return Double.valueOf(0.0D);
		}
		return getPoints(player);
	}

	public static boolean hasEnough(Player p, double points) {
<<<<<<< HEAD
		if (p == null)
=======
		if (isNull(p))
>>>>>>> b953a5352d6cc9605b5a8315d9cce3ccad0deb91
			return false;
		return points <= getPoints(p).doubleValue();
	}

	public static void resetPoints(Player p) {
		File aa = new File("plugins/MinecraftTD/players/" + p.getName().toLowerCase() + ".yml");
		FileConfiguration a = YamlConfiguration.loadConfiguration(aa);
		a.set("Points.points", null);
		try {
			a.save(aa);
		} catch (Exception e) {
			e.printStackTrace();
		}
<<<<<<< HEAD
	}

	public static void resetPoints(String name) {
		Player player = Bukkit.getPlayer(name);
		if (player == null) {
			// what error stuff, how are you gunna send the error message?
=======
	}

	public static void resetPoints(String name) {
		Player player = Bukkit.getPlayer(name);
		if (player == null) {
			// TODO: Error message stuff...
			return;
		}
		resetPoints(player);
	}

	private static boolean isNull(Player p) {
		if (p != null) {
			return false;
>>>>>>> b953a5352d6cc9605b5a8315d9cce3ccad0deb91
		}
		resetPoints(player);
	}

	public static boolean isInt(String sInt) {
		try {
			Integer.parseInt(sInt);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	public static boolean isDouble(String string) {
		try {
			Double.parseDouble(string);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

}