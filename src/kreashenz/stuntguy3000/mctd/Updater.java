package kreashenz.stuntguy3000.mctd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Updater {
	String dlLink = ""; //Need to figure out how to get most recent bukkitdev thing
	String versionLink = ""; //Get version somehow
	
	boolean isReadyToUpdate = false;
	
	private MCTD plugin;
	
	public Updater(MCTD plugin) {
		this.plugin = plugin;
	}
	
	public void update() {
		new BukkitRunnable() {
    		public void run() {
    			double oldVersion = Double.parseDouble(plugin.getDescription().getVersion());
    			String path = getFilePath();
    			
    			try {
    				URL url = new URL(versionLink);
    				URLConnection con = url.openConnection();
    				InputStreamReader isr = new InputStreamReader(con.getInputStream());
    				BufferedReader reader = new BufferedReader(isr);
    				double newVersion = getVersionFromString(reader.readLine());
    				reader.close();
    				
    				if (!isReadyToUpdate)
    				{
    					if(newVersion > oldVersion) {
    						url = new URL(dlLink);
    						con = url.openConnection();
    						InputStream in = con.getInputStream();
    						FileOutputStream out = new FileOutputStream(path);
    						byte[] buffer = new byte[1024];
    						int size = 0;
    						while((size = in.read(buffer)) != -1) {
    							out.write(buffer, 0, size);
    						}
    						
    						out.close();
    						in.close();
    						plugin.getLogger().log(Level.INFO, "Succesfully updated MCTD to v" + newVersion);
    						plugin.getLogger().log(Level.INFO, "Reload/restart server to enable changes");
    						
    						isReadyToUpdate = true;
    						
    						for (Player target : Bukkit.getOnlinePlayers())
    						{
    							if (target.hasPermission("mctd.updateMessages"))
    							{
    								target.sendMessage("§9[MCTD] §aMCTD is ready to be updated!");
    								target.sendMessage("§9[MCTD] §aTo update, please restart or reload the server.");
    						    }
    						}
    					}
    				}
    					
    			} catch(IOException e) {
    				plugin.getLogger().log(Level.SEVERE, "Failed to auto-update", e);
    			}
    		}
		}.runTaskLaterAsynchronously(plugin, 0);
	}
	
	private String getFilePath() {
		if(plugin instanceof JavaPlugin) {
			try {
				Method method = JavaPlugin.class.getDeclaredMethod("getFile");
				boolean wasAccessible = method.isAccessible();
				method.setAccessible(true);
				File file = (File) method.invoke(plugin);
				method.setAccessible(wasAccessible);
				
				return file.getPath();
			} catch(Exception e) {
				return "plugins" + File.separator + plugin.getName();
			}
		} else {
			return "plugins" + File.separator + plugin.getName();
		}
	}
	
	private double getVersionFromString(String from) {
		return Double.parseDouble(from);
	}

	public void forceUpdate() {
		new BukkitRunnable() {
    		public void run() {
    			String path = getFilePath();
		
    			try {
    				URL url = new URL(versionLink);
    				URLConnection con = url.openConnection();
    				InputStreamReader isr = new InputStreamReader(con.getInputStream());
    				BufferedReader reader = new BufferedReader(isr);
    				double newVersion = getVersionFromString(reader.readLine());
    				reader.close();
			
    				url = new URL(dlLink);
    				con = url.openConnection();
    				InputStream in = con.getInputStream();
    				FileOutputStream out = new FileOutputStream(path);
    				byte[] buffer = new byte[1024];
    				int size = 0;
    				while((size = in.read(buffer)) != -1) {
    					out.write(buffer, 0, size);
    				}
			
    				out.close();
    				in.close();
    				plugin.getLogger().log(Level.INFO, "Succesfully FORCE updated MCTD to v" + newVersion);
    				plugin.getLogger().log(Level.INFO, "Reload/restart server to enable changes");
					
    				for (Player target : Bukkit.getOnlinePlayers())
    				{
    					if (target.hasPermission("MCTD.updateMessages"))
    					{
    						target.sendMessage("§9[MCTD] MCTD has been force updated!");
    						target.sendMessage("§9[MCTD] Please restart or reload the server.");
    					}
    				}	
    			} catch(IOException e) {
    				plugin.getLogger().log(Level.SEVERE, "Failed to auto-update", e);
    			}
    		}
		}.runTaskLaterAsynchronously(plugin, 0);
	}
}
