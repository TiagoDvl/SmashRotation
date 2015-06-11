package br.com.tick.smashrotation.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import br.com.tick.smashrotation.R;
import br.com.tick.smashrotation.bo.SmashRotationBO;
import br.com.tick.smashrotation.domain.Player;
import br.com.tick.smashrotation.fragment.adapter.PlayersAdapter;
import br.com.tick.smashrotation.listener.IChoseYou;
import br.com.tick.smashrotation.listener.ISmashRotation;
import br.com.tick.smashrotation.utils.DisplayUtil;

public class SmashRotationFragment extends Fragment implements OnClickListener, IChoseYou {

	private transient ListView listOfPlayers;
	private transient EditText insertPlayerName;
	private transient Button insertPlayerNameButton;
	private transient InputMethodManager mgr;
	private transient SmashRotationBO instance;
	private transient PlayersAdapter playersAdapter;
	private transient RelativeLayout startRotation;
	private transient ISmashRotation listener;
	private transient List<Player> chosenPlayers;

	private transient TextView rotationTopBar;
	private transient TextView startRotationText;

	private static String EMPTY_STRING = "";

	public SmashRotationFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_smash_rotation, container, false);
		DisplayUtil.setLayoutParams((ViewGroup) rootView);

		instance = SmashRotationBO.getInstance(getActivity());
		chosenPlayers = new ArrayList<Player>();

		mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		listOfPlayers = (ListView) rootView.findViewById(R.id.list_of_players);
		insertPlayerName = (EditText) rootView.findViewById(R.id.insert_player_name);
		insertPlayerNameButton = (Button) rootView.findViewById(R.id.insert_player_name_button);
		startRotation = (RelativeLayout) rootView.findViewById(R.id.holder_start_rotation);

		rotationTopBar = (TextView) rootView.findViewById(R.id.top_bar_app_name);
		startRotationText = (TextView) rootView.findViewById(R.id.start_rotation_text);

		Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Oswald-Regular.ttf");
		rotationTopBar.setTypeface(typeface);
		startRotationText.setTypeface(typeface);
		insertPlayerName.setTypeface(typeface);

		if (instance.getListOfPlayers() != null && instance.getListOfPlayers().size() > 0) {
			for (Player player : instance.getListOfPlayers()) {
				player.setWins(0);
				player.setLosses(0);
				player.setMvp(false);
			}
		}

		playersAdapter = new PlayersAdapter(getActivity(), instance.getListOfPlayers(), this);
		listOfPlayers.setAdapter(playersAdapter);
		insertPlayerNameButton.setOnClickListener(this);
		startRotation.setOnClickListener(this);

		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		this.listener = (ISmashRotation) getActivity();
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

		case R.id.holder_start_rotation:
			if (chosenPlayers.size() > 2) {
				showStartRotationScreen(chosenPlayers);
			} else {
				Toast.makeText(getActivity(), R.string.not_enough, Toast.LENGTH_SHORT).show();
			}
			break;

		default:
			break;
		}

	}

	private void showStartRotationScreen(List<Player> listOfPlayers) {
		listener.showRotationScreen(listOfPlayers);
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

	public void setData() {
		// Set any kind of data that i would like to handle.

	}

	@Override
	public void updateChosenPlayers(Player player, boolean isChecked) {
		player.setSelected(isChecked);

		if (isChecked) {
			chosenPlayers.add(player);
		} else {
			// Is this going to work?
			chosenPlayers.remove(player);
		}

	}

	@Override
	public void onResume() {
		for (Player player : SmashRotationBO.getInstance(getActivity()).getListOfPlayers()) {
			player.setSelected(false);
		}

		super.onResume();
	}
}