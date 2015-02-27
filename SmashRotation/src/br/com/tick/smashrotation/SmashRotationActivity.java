package br.com.tick.smashrotation;

import android.app.Activity;
import android.os.Bundle;
import br.com.tick.smashrotation.bo.SmashRotationBO;
import br.com.tick.smashrotation.domain.Player;
import br.com.tick.smashrotation.fragment.SmashRotationFragment;

public class SmashRotationActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_smash_rotation);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction().add(R.id.container, new SmashRotationFragment()).commit();
		}
		
		SmashRotationBO.getInstance(getApplicationContext());
		// Proving that the singleton design patters works and its gonna be used through the application.
		for (Player player: SmashRotationBO.getInstance(getApplicationContext()).generateStaticPlayers()){
			System.out.println("Nome -> "+ player.getName());
			System.out.println("Wins -> "+ player.getWins() + "\n");
		}
	}

}
