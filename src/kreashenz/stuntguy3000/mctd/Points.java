package kreashenz.stuntguy3000.mctd;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class Points {

	public static void addPoints(Player p, double points){
		if(isNull(p)) return;
		File aa = new File("plugins/MinecraftTD/players/" + p.getName().toLowerCase() + ".yml");
		FileConfiguration a = YamlConfiguration.loadConfiguration(aa);
		a.set("Points.points", Double.valueOf(a.getDouble("Points.points") + points));
		try {
			a.save(aa);
		} catch (Exception e){e.printStackTrace();}
	}

	public static void takePoints(Player p, double points){
		if(isNull(p)) return;
		File aa = new File("plugins/MinecraftTD/players/" + p.getName().toLowerCase() + ".yml");
		FileConfiguration a = YamlConfiguration.loadConfiguration(aa);
		a.set("Points.points", Double.valueOf(a.getDouble("Points.points") - points));
		try {
			a.save(aa);
		} catch (Exception e){e.printStackTrace();}
	}

	public static void setPoints(Player p, double points){
		if(isNull(p)) return;
		File aa = new File("plugins/MinecraftTD/players/" + p.getName().toLowerCase() + ".yml");
		FileConfiguration a = YamlConfiguration.loadConfiguration(aa);
		a.set("Points.points", Double.valueOf(points));
		try {
			a.save(aa);
		} catch (Exception e){e.printStackTrace();}
	}

	public static void addByName(String name, double points){
		File bb = new File("plugins/MinecraftTD/players/" + name.toLowerCase() + ".yml");
		FileConfiguration b = YamlConfiguration.loadConfiguration(bb);
		try {
			b.set("Points.points", Double.valueOf(b.getDouble("Points.points") + points));
			try {
				b.save(bb);
			} catch (Exception e){e.printStackTrace();}
		} catch (Exception e){e.printStackTrace();}
	}

	public static void takeByName(String name, double points){
		File bb = new File("plugins/MinecraftTD/players/" + name.toLowerCase() + ".yml");
		FileConfiguration b = YamlConfiguration.loadConfiguration(bb);
		try {
			b.set("Points.points", Double.valueOf(b.getDouble("Points.points") - points));
			try {
				b.save(bb);
			} catch (Exception e){e.printStackTrace();}
		} catch (Exception e){e.printStackTrace();}
	}

	public static void setByName(String name, double points){
		File bb = new File("plugins/MinecraftTD/players/" + name.toLowerCase() + ".yml");
		FileConfiguration b = YamlConfiguration.loadConfiguration(bb);
		try {
			b.set("Points.points", Double.valueOf(points));
			try {
				b.save(bb);
			} catch (Exception e){e.printStackTrace();}
		} catch (Exception ee){ee.printStackTrace();}
	}

	public static Double getPoints(Player p){
		if(isNull(p)) return Double.valueOf(0.0D);
		File aa = new File("plugins/MinecraftTD/players/" + p.getName().toLowerCase() + ".yml");
		FileConfiguration a = YamlConfiguration.loadConfiguration(aa);
		return Double.valueOf(Math.round(a.getDouble("Points.points") * 100.0D) / 100.0D);
	}

	public static Double getPointsByName(String name){
		File bb = new File("plugins/MinecraftTD/players/" + name.toLowerCase() + ".yml");
		FileConfiguration b = YamlConfiguration.loadConfiguration(bb);
		try {
			return Double.valueOf(Math.round(b.getDouble("Points.points") * 100.0D) / 100.0D);
		} catch (Exception e){e.printStackTrace();}
		return Double.valueOf(0.0D);
	}

	public static boolean hasEnough(Player p, double points){
		if(isNull(p)) return false;
		return points <= getPoints(p).doubleValue();
	}

	public static void resetPoints(Player p){
		File aa = new File("plugins/MinecraftTD/players/" + p.getName().toLowerCase() + ".yml");
		FileConfiguration a = YamlConfiguration.loadConfiguration(aa);
		a.set("Points.points", null);
		try {
			a.save(aa);
		} catch (Exception e){e.printStackTrace();}
	}

	public static void resetPointsByName(String name){
		File bb = new File("plugins/MinecraftTD/players/" + name.toLowerCase() + ".yml");
		FileConfiguration b = YamlConfiguration.loadConfiguration(bb);
		b.set("Points.points", null);
		try {
			b.save(bb);
		} catch (Exception e){e.printStackTrace();}
	}

	private static boolean isNull(Player p){
		if(p != null){
			return false;
		}
		return true;
	}

	public static boolean isInt(String sInt)
	{
		try
		{
			Integer.parseInt(sInt);
		}
		catch (NumberFormatException e)
		{
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