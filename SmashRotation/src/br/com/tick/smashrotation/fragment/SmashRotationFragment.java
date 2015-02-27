package br.com.tick.smashrotation.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import br.com.tick.smashrotation.R;
import br.com.tick.smashrotation.bo.SmashRotationBO;
import br.com.tick.smashrotation.domain.Player;
import br.com.tick.smashrotation.fragment.adapter.PlayersAdapter;

public class SmashRotationFragment extends Fragment implements OnClickListener {

	private transient ListView listOfPlayers;
	private transient EditText insertPlayerName;
	private transient Button insertPlayerNameButton;
	private transient InputMethodManager mgr;
	private transient SmashRotationBO instance;
	private transient PlayersAdapter playersAdapter;
	
	private static String EMPTY_STRING = "";

	public SmashRotationFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_smash_rotation, container, false);
		instance = SmashRotationBO.getInstance(getActivity());

		mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		listOfPlayers = (ListView) rootView.findViewById(R.id.list_of_players);
		insertPlayerName = (EditText) rootView.findViewById(R.id.insert_player_name);
		insertPlayerNameButton = (Button) rootView.findViewById(R.id.insert_player_name_button);

		playersAdapter = new PlayersAdapter(getActivity(), instance.getListOfPlayers());
		listOfPlayers.setAdapter(playersAdapter);
		insertPlayerNameButton.setOnClickListener(this);

		return rootView;
	}

	private void disapearKeyBoard() {
		mgr.hideSoftInputFromWindow(insertPlayerName.getWindowToken(), 0);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.insert_player_name_button:
			persistPlayer(insertPlayerName.getText().toString());
			break;

		default:
			break;
		}

	}

	private void persistPlayer(String string) {
		insertPlayerName.setText(EMPTY_STRING);
		if (!"".equals(string.trim())) {
			Player player = new Player();
			player.setName(string);
			instance.getListOfPlayers().add(player);
			playersAdapter.notifyDataSetChanged();
			Toast.makeText(getActivity(), R.string.success_insert_player_name, Toast.LENGTH_LONG).show();
			disapearKeyBoard();

		} else {
			Toast.makeText(getActivity(), R.string.error_insert_player_name, Toast.LENGTH_LONG).show();
		}

	}
}