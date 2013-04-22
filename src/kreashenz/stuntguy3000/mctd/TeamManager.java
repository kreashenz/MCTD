package kreashenz.stuntguy3000.mctd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class TeamManager {

	public MCTD plugin;
	public TeamManager(MCTD plugin)
	{
		this.plugin = plugin;
	}

	public List<String> isOnBlue = new ArrayList<String>();
	public List<String> isOnRed = new ArrayList<String>();

	public HashMap<Object, Object> gameOnline = new HashMap<Object, Object>();
	public HashMap<Object, Object> gameOffline = new HashMap<Object, Object>();

	public boolean playerIsOnRed(Player p){
		return isOnRed.contains(p.getName());
	}
	public boolean playerIsOnBlue(Player p){
		return isOnBlue.contains(p.getName());
	}
	public void setIsNotPlaying(Player p){
		if(isOnRed.contains(p.getName())){
			isOnRed.remove(p.getName());
		}
		if(isOnBlue.contains(p.getName())){
			isOnBlue.remove(p.getName());
		}
	}
	public boolean playerIsNotPlaying(Player p){
		if (playerIsOnRed(p) || playerIsOnBlue(p))
		{
			return false;
		}
		return true;
	}
	public int getWave(Player p,int wave){
		return wave;
	}
	public boolean entityIsOnRed(LivingEntity ent){
		if(ent.getCustomName().equals("§0[§4RED§0]")){
			return false;
		}
		return isOnRed.add(ent.getCustomName());
	}
	public boolean entityIsOnBlue(LivingEntity ent){
		if(ent.getCustomName().equals("§0[§1BLUE§0]")){
			return false;
		}
		return isOnBlue.add(ent.getCustomName());
	}

}
