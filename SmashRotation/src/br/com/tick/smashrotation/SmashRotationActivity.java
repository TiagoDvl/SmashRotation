package br.com.tick.smashrotation;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import br.com.tick.smashrotation.bo.SmashRotationBO;
import br.com.tick.smashrotation.domain.Contest;
import br.com.tick.smashrotation.domain.Player;
import br.com.tick.smashrotation.fragment.ActionsDialogFragment;
import br.com.tick.smashrotation.fragment.ResultsFragment;
import br.com.tick.smashrotation.fragment.SmashRotationFragment;
import br.com.tick.smashrotation.fragment.StartRotationFragment;
import br.com.tick.smashrotation.listener.ISmashRotation;
import br.com.tick.smashrotation.persistence.Serialization;

public class SmashRotationActivity extends Activity implements ISmashRotation {
	
	// Put them in order to understand the app flow.
	private SmashRotationFragment smashRotationFragment;
	private StartRotationFragment startRotationFragment;
	private ActionsDialogFragment actionsdialogFragment;
	private ResultsFragment resultsFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_smash_rotation);
		SmashRotationBO.getInstance(getApplicationContext());

		if (savedInstanceState == null) {
			smashRotationFragment = new SmashRotationFragment();
			smashRotationFragment.setData();
			getFragmentManager().beginTransaction().replace(R.id.container, smashRotationFragment).commit();
		}

		// Proving that the singleton design patters works and its gonna be used
		// through the application.
//		for (Player player : SmashRotationBO.getInstance(this).generateStaticPlayers()) {
//			System.out.println("Nome -> " + player.getName());
//			System.out.println("Wins -> " + player.getWins() + "\n");
//		}
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

	@Override
	public void showResultFragment(Contest contest) {
		resultsFragment = new ResultsFragment();
		resultsFragment.setContest(contest);
		getFragmentManager().beginTransaction().replace(R.id.container, resultsFragment).addToBackStack(resultsFragment.getClass().toString()).commit();
		
	}
	
	@Override
	protected void onDestroy() {
		try {
			Serialization.createDataInputStream(getApplication().getFilesDir().getPath());
			System.out.println("Salvou!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		super.onDestroy();
	}

}
