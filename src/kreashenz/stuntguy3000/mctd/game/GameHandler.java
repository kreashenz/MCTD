package kreashenz.stuntguy3000.mctd.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import kreashenz.stuntguy3000.mctd.MCTD;

import org.bukkit.entity.Player;

public class GameHandler {

	public List<String> inGame = new ArrayList<String>();

	private int minPlayers = 2;
	private int blueScore = 0;
	private int redScore = 0;

	private Random ran = new Random();

	private MCTD plugin;
	public GameHandler(MCTD plugin){this.plugin = plugin;}

	public void startGame(){
		boolean isStartable = getPlayers().size() >= minPlayers;
		if(isStartable){
			// start that game
		}
	}
	
	public void setTeamWhoWin(){
		if(blueScore > redScore /*&& time is over*/ ){
			// blue team wins
		}
		if(redScore > blueScore /*&& time is over*/){
			// red team wins
		}
		if(redScore == blueScore || blueScore == redScore){
			// draw!!
		}
	}

	public List<Player> getPlayers(){
		List<Player> players = new ArrayList<Player>();
		for(Player p : players){
			players.add(p);
		}
		return players;
	}
}