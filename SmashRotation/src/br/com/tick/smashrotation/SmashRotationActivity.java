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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_smash_rotation);

		if (savedInstanceState == null) {
			SmashRotationFragment fragment = new SmashRotationFragment();
			fragment.setData();
			getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
		}

		SmashRotationBO.getInstance(getApplicationContext());
		// Proving that the singleton design patters works and its gonna be used
		// through the application.
		for (Player player : SmashRotationBO.getInstance(getApplicationContext()).generateStaticPlayers()) {
			System.out.println("Nome -> " + player.getName());
			System.out.println("Wins -> " + player.getWins() + "\n");
		}
	}

	@Override
	public void showRotationScreen(List<Player> listOfPlayers) {
		// Inflate rotation fragment.
		StartRotationFragment fragment = new StartRotationFragment();
		fragment.setData(listOfPlayers);
		getFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack(fragment.getClass().toString()).commit();
	}

	@Override
	public void showActionsDialog() {
		ActionsDialogFragment fragment = new ActionsDialogFragment();
		fragment.setData(SmashRotationBO.getInstance(getApplicationContext()).getChosenPlayer());
		getFragmentManager().beginTransaction().add(R.id.holder_dialog, fragment).addToBackStack(fragment.getClass().toString()).commit();

	}

}
