package br.com.tick.smashrotation.bo;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import br.com.tick.smashrotation.domain.Player;

public class SmashRotationBO {

	private static SmashRotationBO instance = null;
	private List<Player> listOfPlayers;
	private Player chosenPlayer;

	protected SmashRotationBO(Context context) {
		listOfPlayers = new ArrayList<Player>();
	}

	public static SmashRotationBO getInstance(Context context) {
		if (instance == null) {
			instance = new SmashRotationBO(context);
		}
		return instance;
	}

	public List<Player> generateStaticPlayers() {
		Player currentPlayer = new Player();

		currentPlayer.setName("Tikun");
		currentPlayer.setWins(10);
		currentPlayer.setLosses(5);
		getListOfPlayers().add(currentPlayer);

		currentPlayer = new Player();
		currentPlayer.setName("nRick");
		currentPlayer.setWins(1);
		currentPlayer.setLosses(5000);
		getListOfPlayers().add(currentPlayer);

		currentPlayer = new Player();
		currentPlayer.setName("Lucas");
		currentPlayer.setWins(50);
		currentPlayer.setLosses(10);
		getListOfPlayers().add(currentPlayer);

		currentPlayer = new Player();
		currentPlayer.setName("Fabio");
		currentPlayer.setWins(20);
		currentPlayer.setLosses(10);
		getListOfPlayers().add(currentPlayer);

		currentPlayer = new Player();
		currentPlayer.setName("Ramon");
		currentPlayer.setWins(10);
		currentPlayer.setLosses(2);
		getListOfPlayers().add(currentPlayer);

		currentPlayer = new Player();
		currentPlayer.setName("Rendom");
		currentPlayer.setWins(0);
		currentPlayer.setLosses(2);
		getListOfPlayers().add(currentPlayer);

		return getListOfPlayers();
	}

	public List<Player> getListOfPlayers() {
		return listOfPlayers;
	}

	public void setListOfPlayers(List<Player> listOfPlayers) {
		this.listOfPlayers = listOfPlayers;
	}

	public Player getChosenPlayer() {
		return chosenPlayer;
	}

	public void setChosenPlayer(Player chosenPlayer) {
		this.chosenPlayer = chosenPlayer;
	}

}
