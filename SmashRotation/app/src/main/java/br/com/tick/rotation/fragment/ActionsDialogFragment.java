package br.com.tick.rotation.fragment;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import br.com.tick.rotation.R;
import br.com.tick.rotation.domain.Player;
import br.com.tick.rotation.listener.ISmashRotation;
import br.com.tick.rotation.utils.DisplayUtil;

public class ActionsDialogFragment extends Fragment implements OnClickListener {

	private transient ISmashRotation listener;
	private transient Player player;
	
	private transient Button actionWinner;
	private transient Button actionLoser;
	private transient Button actionPassed;
	private transient Button playerInFocus;

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
		DisplayUtil.setLayoutParams((ViewGroup) rootView);
		
		Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Oswald-Regular.ttf");
		Typeface typefaceLight = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Oswald-Light.ttf");
		
		playerInFocus = (Button) rootView.findViewById(R.id.holder_player_name);
		playerInFocus.setText(player.getName());
		playerInFocus.setClickable(false);

		actionWinner = (Button) rootView.findViewById(R.id.action_winner);
		actionLoser = (Button) rootView.findViewById(R.id.action_loser);
		actionPassed = (Button) rootView.findViewById(R.id.action_pass);

		actionWinner.setOnClickListener(this);
		actionLoser.setOnClickListener(this);
		actionPassed.setOnClickListener(this);
		
		playerInFocus.setTypeface(typeface);
		actionWinner.setTypeface(typefaceLight);
		actionLoser.setTypeface(typefaceLight);
		actionPassed.setTypeface(typefaceLight);
		

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