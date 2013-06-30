package kreashenz.stuntguy3000.mctd.teams;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public interface ITeam {

	public void setRedTeam(Player p);

	public void setBlueTeam(Player p);

	public void setIsNotPlaying(Player p);

	public String getTeam(Player p);
	
	public boolean playerIsOnRed(Player p);
	
	public boolean playerIsOnBlue(Player p);
	
	public boolean playerIsNotPlaying(Player p);
	
	public boolean entityIsOnRed(Entity ent);
	
	public boolean entityIsOnBlue(Entity ent);
}
