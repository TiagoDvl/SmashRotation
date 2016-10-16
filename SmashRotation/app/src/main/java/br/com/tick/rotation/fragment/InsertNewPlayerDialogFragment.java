package br.com.tick.rotation.fragment;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import br.com.tick.rotation.R;
import br.com.tick.rotation.domain.Player;
import br.com.tick.rotation.listener.ISmashRotation;
import br.com.tick.rotation.utils.DisplayUtil;

public class InsertNewPlayerDialogFragment extends Fragment implements OnClickListener {

	private transient ISmashRotation listener;

	private transient TextView newChallenger;
	private transient EditText etNewChallenger;
	private transient Button btnNewChallenger;
	private transient InputMethodManager mgr;

	public InsertNewPlayerDialogFragment() {
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		this.listener = (ISmashRotation) getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.insert_new_player_dialog, container, false);
		DisplayUtil.setLayoutParams((ViewGroup) rootView);
		Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Oswald-Regular.ttf");

		mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		newChallenger = (TextView) rootView.findViewById(R.id.insert_new_player);
		etNewChallenger = (EditText) rootView.findViewById(R.id.et_new_player);
		btnNewChallenger = (Button) rootView.findViewById(R.id.insert_new_player_btn);

		newChallenger.setTypeface(typeface);
		etNewChallenger.setTypeface(typeface);
		btnNewChallenger.setTypeface(typeface);

		btnNewChallenger.setOnClickListener(this);

		return rootView;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.insert_new_player_btn:
			if (!etNewChallenger.getText().toString().trim().equals("")) {
				Player player = new Player();
				player.setLosses(0);
				player.setMvp(false);
				player.setName(etNewChallenger.getText().toString().trim());
				player.setWins(0);
				listener.sendNewChallenger(player);
				listener.popBackStack();
				disapearKeyBoard();
			} else {
				Toast.makeText(getActivity(), "Name cannot be empty", Toast.LENGTH_SHORT).show();
			}
			break;
		default:
			break;
		}

	}

	private void disapearKeyBoard() {
		mgr.hideSoftInputFromWindow(etNewChallenger.getWindowToken(), 0);

	}

}