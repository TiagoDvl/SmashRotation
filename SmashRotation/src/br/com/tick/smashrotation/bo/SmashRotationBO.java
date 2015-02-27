package br.com.tick.smashrotation.bo;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import br.com.tick.smashrotation.domain.Player;

public class SmashRotationBO {

	private static SmashRotationBO instance = null;

	protected SmashRotationBO(Context context) {
		// Do Nothing.
	}

	public static SmashRotationBO getInstance(Context context) {
		if (instance == null) {
			instance = new SmashRotationBO(context);
		}
		return instance;
	}
	
	public List<Player> generateStaticPlayers() {
		List<Player> listOfPlayers = new ArrayList<Player>();
		Player currentPlayer = new Player();
		
		currentPlayer.setName("Tikun");
		currentPlayer.setWins(10);
		currentPlayer.setLosses(5);
		listOfPlayers.add(currentPlayer);
		
		currentPlayer.setName("nRick");
		currentPlayer.setWins(1);
		currentPlayer.setLosses(5000);
		listOfPlayers.add(currentPlayer);
		
		currentPlayer.setName("Lucas");
		currentPlayer.setWins(50);
		currentPlayer.setLosses(10);
		listOfPlayers.add(currentPlayer);
		
		currentPlayer.setName("Fabio");
		currentPlayer.setWins(20);
		currentPlayer.setLosses(10);
		listOfPlayers.add(currentPlayer);
		
		currentPlayer.setName("Ramon");
		currentPlayer.setWins(10);
		currentPlayer.setLosses(2);
		listOfPlayers.add(currentPlayer);
		
		currentPlayer.setName("Rendom");
		currentPlayer.setWins(0);
		currentPlayer.setLosses(2);
		listOfPlayers.add(currentPlayer);

		return listOfPlayers;
	}

}
