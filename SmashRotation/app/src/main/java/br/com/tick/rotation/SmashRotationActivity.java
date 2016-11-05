package br.com.tick.rotation;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.ads.MobileAds;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

import br.com.tick.rotation.bo.SmashRotationBO;
import br.com.tick.rotation.domain.Contest;
import br.com.tick.rotation.domain.Player;
import br.com.tick.rotation.fragment.ActionsDialogFragment;
import br.com.tick.rotation.fragment.InsertNewPlayerDialogFragment;
import br.com.tick.rotation.fragment.LineRandomFragment;
import br.com.tick.rotation.fragment.ResultsFragment;
import br.com.tick.rotation.fragment.SmashRotationFragment;
import br.com.tick.rotation.fragment.SplashScreenFragment;
import br.com.tick.rotation.fragment.StartRotationFragment;
import br.com.tick.rotation.listener.ISmashRotation;
import br.com.tick.rotation.persistence.Serialization;
import br.com.tick.rotation.utils.DisplayUtil;

public class SmashRotationActivity extends Activity implements ISmashRotation {
	
	// Put them in order to understand the app flow.
	private SplashScreenFragment splashScreenFragment;
	private SmashRotationFragment smashRotationFragment;
	private LineRandomFragment lineRandomFragment;
	
	private StartRotationFragment startRotationFragment;
	private InsertNewPlayerDialogFragment insertNewPlayerDialogFragment;
	
	private ActionsDialogFragment actionsdialogFragment;
	private ResultsFragment resultsFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_smash_rotation);
		SmashRotationBO.getInstance(getApplicationContext());
		DisplayUtil.init(this);
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-7270107445522798~9017597067");
		
		if (savedInstanceState == null) {
			splashScreenFragment = new SplashScreenFragment();
			getFragmentManager().beginTransaction().replace(R.id.container, splashScreenFragment).commit();
		}
	}

	@Override
	public void showRotationScreen(List<Player> listOfPlayers, int i) {
		// Inflate rotation fragment.
		startRotationFragment = new StartRotationFragment();
		startRotationFragment.setListOfPlayers(listOfPlayers, i);
		
		if (SmashRotationBO.getInstance(this).getContest().getNumberOfGames() > 0){
			SmashRotationBO.getInstance(this).setContest(new Contest());
		}
		
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
	public void populateWithAddress(String location) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showMainScreen() {
		smashRotationFragment = new SmashRotationFragment();
		smashRotationFragment.setData();
		getFragmentManager().beginTransaction().replace(R.id.container, smashRotationFragment).commit();
		
	}
	
	@Override
	public void showDialogLineRandom(List<Player> chosenPlayers) {
		lineRandomFragment = new LineRandomFragment();
		lineRandomFragment.setChosenPlayers(chosenPlayers);
		getFragmentManager().beginTransaction().add(R.id.holder_dialog_line_random, lineRandomFragment).addToBackStack(lineRandomFragment.getClass().toString()).commit();
	}

	@Override
	public void sendNewChallenger(Player player) {
		startRotationFragment.receiveNewChallenger(player);
		
	}
	
	@Override
	public void showDialogInsertNewPlayer() {
		
		insertNewPlayerDialogFragment = new InsertNewPlayerDialogFragment();
		getFragmentManager().beginTransaction().add(R.id.holder_dialog, insertNewPlayerDialogFragment).addToBackStack(insertNewPlayerDialogFragment.getClass().toString()).commit();
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		AppEventsLogger.activateApp(this);
	}
	
	@Override
	protected void onPause() {
		AppEventsLogger.deactivateApp(this);
        try {
            ObjectOutputStream objectOutputStream = Serialization.createDataOutputStream(getApplication().getFilesDir().getPath());
            objectOutputStream.writeObject(SmashRotationBO.getInstance(getApplicationContext()).getListOfPlayers());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		super.onPause();
	}

    @Override
    public void onBackPressed() {
        if (startRotationFragment != null
                && resultsFragment == null
                && insertNewPlayerDialogFragment == null) {
            showDecisionDialog(getResources().getString(R.string.decision_lose_everything), false);
        } else {
            super.onBackPressed();
        }
    }

    public void showDecisionDialog(String message, final boolean isEndingMatch) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setPositiveButton(getResources().getString(R.string.decision_ok),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (isEndingMatch) {
                            if (startRotationFragment != null) {
                                startRotationFragment.finishMatch();
                            }
                        } else {
                            getFragmentManager().popBackStack();
                        }
                    }
                });
        alertDialogBuilder.setNegativeButton(getResources().getString(R.string.decision_no),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // This was only programmed just in case it needs.
                        if (isEndingMatch) {
                            return;
                        } else {
                            return;
                        }
                    }
                });
        alertDialogBuilder.setTitle(getResources().getString(R.string.decision_title));
        alertDialogBuilder.show();
    }
}
