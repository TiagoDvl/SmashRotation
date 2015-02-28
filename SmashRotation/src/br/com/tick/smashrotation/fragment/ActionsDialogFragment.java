package br.com.tick.smashrotation.fragment;

import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import br.com.tick.smashrotation.R;
import br.com.tick.smashrotation.bo.SmashRotationBO;
import br.com.tick.smashrotation.domain.Player;
import br.com.tick.smashrotation.listener.ISmashRotation;

public class ActionsDialogFragment extends Fragment implements OnClickListener {

	private transient ISmashRotation listener;
	private transient int playerPosition;
	private transient Button actionWinner;
	private transient Button actionLoser;
	private transient Button actionPassed;
	private transient List<Player> listOfPlayers;

	public ActionsDialogFragment() {
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		this.listener = (ISmashRotation) getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_actions_dialog, container, false);

		listOfPlayers = SmashRotationBO.getInstance(getActivity()).getListOfPlayers();
		
		actionWinner = (Button) rootView.findViewById(R.id.action_winner);
		actionLoser = (Button) rootView.findViewById(R.id.action_loser);
		actionPassed = (Button) rootView.findViewById(R.id.action_pass);

		actionWinner.setOnClickListener(this);
		actionLoser.setOnClickListener(this);
		actionPassed.setOnClickListener(this);

		return rootView;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.action_winner:
			break;                                      
		case R.id.action_loser:
			break;
		case R.id.action_pass:
			break;
		default:
			break;
		}

	}

	public void setData(int position) {
		this.playerPosition = position;
	}

}