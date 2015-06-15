package br.com.tick.smashrotation.listener;

import java.util.List;

import br.com.tick.smashrotation.domain.Contest;
import br.com.tick.smashrotation.domain.Player;

public interface ISmashRotation {
	
	void showRotationScreen(List<Player> listOfPlayers, int i);
	void showActionsDialog();
	void receiveAction(int action, Player player);
	void showResultFragment(Contest contest);
	void popBackStack();
	void populateWithAddress(String location);
	void showMainScreen();
	void showDialogLineRandom(List<Player> chosenPlayers);
	void showDialogInsertNewPlayer();
	void sendNewChallenger(Player player);

}
