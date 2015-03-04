package br.com.tick.smashrotation.bo;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import br.com.tick.smashrotation.domain.Contest;
import br.com.tick.smashrotation.domain.Player;

public class SmashRotationBO {

	private static SmashRotationBO instance = null;
	private List<Player> listOfPlayers;
	private Player chosenPlayer;
	private Contest contest;

	protected SmashRotationBO(Context context) {
		listOfPlayers = new ArrayList<Player>();
		contest = new Contest();
	}

	public static SmashRotationBO getInstance(Context context) {
		if (instance == null) {
			instance = new SmashRotationBO(context);
		}
		return instance;
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

	public Contest getContest() {
		return contest;
	}

	public void setContest(Contest contest) {
		this.contest = contest;
	}
	
}
