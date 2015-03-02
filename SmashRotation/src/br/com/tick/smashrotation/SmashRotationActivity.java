package br.com.tick.smashrotation;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import br.com.tick.smashrotation.bo.SmashRotationBO;
import br.com.tick.smashrotation.domain.Player;
import br.com.tick.smashrotation.fragment.ActionsDialogFragment;
import br.com.tick.smashrotation.fragment.SmashRotationFragment;
import br.com.tick.smashrotation.fragment.StartRotationFragment;
import br.com.tick.smashrotation.listener.ISmashRotation;

public class SmashRotationActivity extends Activity implements ISmashRotation {
	
	// Put them in order to understand the app flow.
	private SmashRotationFragment smashRotationFragment;
	private StartRotationFragment startRotationFragment;
	private ActionsDialogFragment actionsdialogFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_smash_rotation);

		if (savedInstanceState == null) {
			smashRotationFragment = new SmashRotationFragment();
			smashRotationFragment.setData();
			getFragmentManager().beginTransaction().replace(R.id.container, smashRotationFragment).commit();
		}

		SmashRotationBO.getInstance(getApplicationContext());
		// Proving that the singleton design patters works and its gonna be used
		// through the application.
		for (Player player : SmashRotationBO.getInstance(this).generateStaticPlayers()) {
			System.out.println("Nome -> " + player.getName());
			System.out.println("Wins -> " + player.getWins() + "\n");
		}
	}

	@Override
	public void showRotationScreen(List<Player> listOfPlayers) {
		// Inflate rotation fragment.
		startRotationFragment = new StartRotationFragment();
		startRotationFragment.setListOfPlayers(listOfPlayers);
		startRotationFragment.setNewContest(SmashRotationBO.getInstance(this).getContest());
		getFragmentManager().beginTransaction().replace(R.id.container, startRotationFragment).addToBackStack(startRotationFragment.getClass().toString()).commit();
	}

	@Override
	public void showActionsDialog() {
		actionsdialogFragment = new ActionsDialogFragment();
		actionsdialogFragment.setData(SmashRotationBO.getInstance(this).getChosenPlayer());
		getFragmentManager().beginTransaction().add(R.id.holder_dialog, actionsdialogFragment).addToBackStack(actionsdialogFragment.getClass().toString()).commit();

	}

	@Override
	public void receiveAction(int action, Player player) {
		startRotationFragment.receiveAction(action, player);
		
	}

	@Override
	public void popBackStack() {
		getFragmentManager().popBackStack();
		
	}

}
