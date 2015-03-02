package br.com.tick.smashrotation.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import br.com.tick.smashrotation.R;
import br.com.tick.smashrotation.domain.Player;
import br.com.tick.smashrotation.listener.ISmashRotation;

public class ActionsDialogFragment extends Fragment implements OnClickListener {

	private transient ISmashRotation listener;
	private transient Player player;
	private transient Button actionWinner;
	private transient Button actionLoser;
	private transient Button actionPassed;

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
			listener.receiveAction(0, player);
			listener.popBackStack();
			break;
		case R.id.action_loser:
			listener.receiveAction(1, player);
			listener.popBackStack();
			break;
		case R.id.action_pass:
			listener.receiveAction(2, player);
			listener.popBackStack();
			break;
		default:
			break;
		}

	}

	public void setData(Player player) {
		this.player = player;
		Log.e("Player in evidence -->", player.getName());
	}

}