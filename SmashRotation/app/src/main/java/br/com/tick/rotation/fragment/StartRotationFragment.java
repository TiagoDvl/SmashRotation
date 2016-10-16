package br.com.tick.rotation.fragment;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import br.com.tick.rotation.R;
import br.com.tick.rotation.bo.SmashRotationBO;
import br.com.tick.rotation.domain.Contest;
import br.com.tick.rotation.domain.Player;
import br.com.tick.rotation.fragment.adapter.RotationAdapter;
import br.com.tick.rotation.listener.ISmashRotation;
import br.com.tick.rotation.task.LocationTask;
import br.com.tick.rotation.utils.DisplayUtil;

public class StartRotationFragment extends Fragment implements OnClickListener {

	private transient TextView contestantA;
	private transient TextView contestantB;
	private transient ImageView mvpA;
	private transient ImageView mvpB;

	private transient RelativeLayout contestantARelative;
	private transient RelativeLayout contestantBRelative;

	private transient List<Player> listOfPlayers;
	private transient ListView rotation;
	private transient RotationAdapter adapter;
	private transient ISmashRotation listener;

	private transient static int FIRST_CONTESTANT = 0;
	private transient static int SECOND_CONTESTANT = 1;

	private transient Player playerA = null;
	private transient Player playerB = null;

	private transient Contest contest;
	private transient int wins;

	private transient TextView rotationTopBarText;
	private transient TextView nowPlayingText;
	private transient Button finishMatch;
	private transient TextView nextPlayer;

	private transient TextView winsCounterA;
	private transient TextView winsCounterB;

	private transient TextView lossesCounterA;
	private transient TextView lossesCounterB;

	private transient ImageView insertNewPlayer;
	private LocationManager mLocationManager;
	private Location myLocation;

	public StartRotationFragment() {
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		this.listener = (ISmashRotation) getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_start_rotation, container, false);
		DisplayUtil.setLayoutParams((ViewGroup) rootView);

		Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Oswald-Regular.ttf");
		Typeface typefaceLight = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Oswald-Light.ttf");
		contestantA = (TextView) rootView.findViewById(R.id.contestant_a_name);
		contestantB = (TextView) rootView.findViewById(R.id.contestant_b_name);
		rotationTopBarText = (TextView) rootView.findViewById(R.id.top_bar_app_name);
		nowPlayingText = (TextView) rootView.findViewById(R.id.now_playing_txt);
		finishMatch = (Button) rootView.findViewById(R.id.finish_match_text);
		nextPlayer = (TextView) rootView.findViewById(R.id.next_player_text);

		winsCounterA = (TextView) rootView.findViewById(R.id.wins_counter_a);
		winsCounterB = (TextView) rootView.findViewById(R.id.wins_counter_b);
		lossesCounterA = (TextView) rootView.findViewById(R.id.losses_counter_a);
		lossesCounterB = (TextView) rootView.findViewById(R.id.losses_counter_b);

		contestantARelative = (RelativeLayout) rootView.findViewById(R.id.contestant_a);
		contestantBRelative = (RelativeLayout) rootView.findViewById(R.id.contestant_b);
		rotation = (ListView) rootView.findViewById(R.id.rotation);

		mvpA = (ImageView) rootView.findViewById(R.id.mvp_match);
		mvpB = (ImageView) rootView.findViewById(R.id.mvp_match_b);

		insertNewPlayer = (ImageView) rootView.findViewById(R.id.top_bar_insert_new_player);

		contestantARelative.setOnClickListener(this);
		contestantBRelative.setOnClickListener(this);
		finishMatch.setOnClickListener(this);
		insertNewPlayer.setOnClickListener(this);

		contestantA.setTypeface(typeface);
		contestantB.setTypeface(typeface);
		rotationTopBarText.setTypeface(typeface);
		nowPlayingText.setTypeface(typeface);
		finishMatch.setTypeface(typeface);
		nextPlayer.setTypeface(typeface);

		winsCounterA.setTypeface(typefaceLight);
		winsCounterB.setTypeface(typefaceLight);
		lossesCounterA.setTypeface(typefaceLight);
		lossesCounterB.setTypeface(typefaceLight);

		contestantA.setSelected(true);
		contestantB.setSelected(true);

		adapter = new RotationAdapter(getActivity(), listOfPlayers);
		rotation.setAdapter(adapter);

		if (playerA == null && playerB == null) {
			// This will be a problem.
			wins = 0;
			startRotation();
		} else {

			if (playerA != null) {
				contestantA.setText(playerA.getName());
				winsCounterA.setText(getResources().getString(R.string.wins) + playerA.getWins());
				lossesCounterA.setText(getResources().getString(R.string.losses) + playerA.getLosses());
			}

			if (playerB != null) {
				contestantB.setText(playerB.getName());
				winsCounterB.setText(getResources().getString(R.string.wins) + playerB.getWins());
				lossesCounterB.setText(getResources().getString(R.string.losses) + playerB.getLosses());
			}
		}

		return rootView;
	}

	private void startRotation() {
		playerA = listOfPlayers.get(FIRST_CONTESTANT);
		contestantA.setText(playerA.getName());
		winsCounterA.setText(getResources().getString(R.string.wins) + playerA.getWins());
		lossesCounterA.setText(getResources().getString(R.string.losses) + playerA.getLosses());

		playerB = listOfPlayers.get(SECOND_CONTESTANT);
		contestantB.setText(playerB.getName());
		winsCounterB.setText(getResources().getString(R.string.wins) + playerB.getWins());
		lossesCounterB.setText(getResources().getString(R.string.losses) + playerB.getLosses());

		// Proper way to remove more than one element of lists.
		Integer[] intArray = new Integer[] { FIRST_CONTESTANT, SECOND_CONTESTANT };
		List<Integer> indices = new ArrayList<Integer>(Arrays.asList(intArray));
		Collections.sort(indices, Collections.reverseOrder());
		for (int i : indices) {
			listOfPlayers.remove(i);
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.contestant_a:
			showActionsDialog(1);
			break;

		case R.id.contestant_b:
			showActionsDialog(2);
			break;
		case R.id.finish_match_text:
			int contWins = 0;
			if (playerA.getWins() > 0) {
				contWins = playerA.getWins();
				contest.setBestPlayer(playerA);
			}

			if (playerB.getWins() > contWins) {
				contWins = playerB.getWins();
				contest.setBestPlayer(playerB);
			}

			for (Player player : listOfPlayers) {
				if (player.getWins() > contWins) {
					contWins = player.getWins();
					contest.setBestPlayer(player);
				}
			}

			contWins = 0;

			for (Player player : listOfPlayers) {
				if (player.getLosses() > contWins) {
					contWins = player.getLosses();
					contest.setWorstPlayer(player);
				}
			}

			Location location = getLastKnownLocation(getActivity());
			LocationTask task = new LocationTask(location, getActivity(), listener, contest);

			if (contest.getNumberOfGames() > 0) {
				task.execute();
			} else {
				Toast.makeText(getActivity(),
                        getResources().getString(R.string.insufficient_matches), Toast.LENGTH_SHORT).show();
			}
			break;

		case R.id.top_bar_insert_new_player:
			listener.showDialogInsertNewPlayer();
			break;
		default:
			break;
		}
	}

	private Location getLastKnownLocation(Context context) {
		mLocationManager = (LocationManager) context.getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
		List<String> providers = mLocationManager.getProviders(true);
		Location bestLocation = null;
		for (String provider : providers) {
			Location l = mLocationManager.getLastKnownLocation(provider);
			if (l == null) {
				continue;
			}
			if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
				// Found best last known location: %s", l);
				bestLocation = l;
			}
		}
		return bestLocation;
	}

	private void showActionsDialog(int player) {

		if (player == 1) {
			SmashRotationBO.getInstance(getActivity()).setChosenPlayer(playerA);
		} else if (player == 2) {
			SmashRotationBO.getInstance(getActivity()).setChosenPlayer(playerB);
		} else {
			Log.e("Erro na escolha", "Jogador escolhido inválido");
		}

		listener.showActionsDialog();

	}

	public void receiveAction(int action, Player player) {
		switch (action) {
		case 0:
			Toast.makeText(getActivity(), getResources().getString(R.string.player) +
                    player.getName() + getResources().getString(R.string.won), Toast.LENGTH_SHORT).show();
			if (player.equals(playerA)) {
				// Score change.
				playerA.setWins(playerA.getWins() + 1);
				winsCounterA.setText(getResources().getString(R.string.wins) + playerA.getWins());

				playerB.setLosses(playerB.getLosses() + 1);

				// Contest changes.
				contest.setNumberOfGames(contest.getNumberOfGames() + 1);
				giveThisManACrown();

				// Rotation Logic.
				listOfPlayers.add(playerB);
				playerB = null; // The player B have lost.
				playerB = listOfPlayers.get(0);
				if (playerB.isMvp()) {
					mvpB.setVisibility(View.VISIBLE);
					mvpA.setVisibility(View.INVISIBLE);
				}

				if (playerA.isMvp() == false && playerB.isMvp() == false) {
					mvpB.setVisibility(View.INVISIBLE);
					mvpA.setVisibility(View.INVISIBLE);
				}

				listOfPlayers.remove(0);
				contestantB.setText(playerB.getName());
				winsCounterB.setText(getResources().getString(R.string.wins) + playerB.getWins());
				lossesCounterB.setText(getResources().getString(R.string.losses) + playerB.getLosses());
			} else {
				// Contest changes.
				contest.setNumberOfGames(contest.getNumberOfGames() + 1);

				// Score change.
				playerB.setWins(playerB.getWins() + 1);
				winsCounterB.setText(getResources().getString(R.string.wins) + playerB.getWins());

				playerA.setLosses(playerA.getLosses() + 1);
				giveThisManACrown();

				// Rotation Logic.
				listOfPlayers.add(playerA);
				playerA = null; // The player A have lost.
				playerA = listOfPlayers.get(0);
				if (playerA.isMvp()) {
					mvpA.setVisibility(View.VISIBLE);
					mvpB.setVisibility(View.INVISIBLE);
				}

				if (playerA.isMvp() == false && playerB.isMvp() == false) {
					mvpB.setVisibility(View.INVISIBLE);
					mvpA.setVisibility(View.INVISIBLE);
				}

				listOfPlayers.remove(0);
				contestantA.setText(playerA.getName());
				winsCounterA.setText(getResources().getString(R.string.wins) + playerA.getWins());
				lossesCounterA.setText(getResources().getString(R.string.losses) + playerA.getLosses());
			}

			adapter.notifyDataSetChanged();

			break;
		case 1:
			Toast.makeText(getActivity(), getResources().getString(R.string.player) +
                    player.getName() + getResources().getString(R.string.lost), Toast.LENGTH_SHORT).show();
			if (player.equals(playerA)) {
				// Contest changes.
				contest.setNumberOfGames(contest.getNumberOfGames() + 1);

				// Score change.
				playerB.setWins(playerB.getWins() + 1);
				winsCounterB.setText(getResources().getString(R.string.wins) + playerB.getWins());

				playerA.setLosses(playerA.getLosses() + 1);
				giveThisManACrown();

				// Rotation Logic.
				listOfPlayers.add(playerA);
				playerA = null; // The player A have lost.
				playerA = listOfPlayers.get(0);
				if (playerA.isMvp()) {
					mvpA.setVisibility(View.VISIBLE);
					mvpB.setVisibility(View.INVISIBLE);
				}

				if (playerA.isMvp() == false && playerB.isMvp() == false) {
					mvpB.setVisibility(View.INVISIBLE);
					mvpA.setVisibility(View.INVISIBLE);
				}

				listOfPlayers.remove(0);
				contestantA.setText(playerA.getName());
				winsCounterA.setText(getResources().getString(R.string.wins) + playerA.getWins());
				lossesCounterA.setText(getResources().getString(R.string.losses) + playerA.getLosses());
			} else {
				// Contest changes.
				contest.setNumberOfGames(contest.getNumberOfGames() + 1);

				// Score change.
				playerA.setWins(playerA.getWins() + 1);
				winsCounterA.setText(getResources().getString(R.string.wins) + playerA.getWins());

				playerB.setLosses(playerB.getLosses() + 1);
				giveThisManACrown();

				// Rotation Logic.
				listOfPlayers.add(playerB);
				playerB = null; // The player B have lost.
				playerB = listOfPlayers.get(0);
				if (playerB.isMvp()) {
					mvpB.setVisibility(View.VISIBLE);
					mvpA.setVisibility(View.INVISIBLE);
				}

				if (playerA.isMvp() == false && playerB.isMvp() == false) {
					mvpB.setVisibility(View.INVISIBLE);
					mvpA.setVisibility(View.INVISIBLE);
				}

				listOfPlayers.remove(0);
				contestantB.setText(playerB.getName());
			}

			adapter.notifyDataSetChanged();

			break;
		case 2:
			Toast.makeText(getActivity(), getResources().getString(R.string.player) +
                    player.getName() + getResources().getString(R.string.passed), Toast.LENGTH_SHORT).show();
			if (player.equals(playerA)) {
				// Rotation Logic.
				listOfPlayers.add(playerA);
				playerA = null; // The player A have passed.
				playerA = listOfPlayers.get(0);
				listOfPlayers.remove(0);
				contestantA.setText(playerA.getName());
				winsCounterA.setText(getResources().getString(R.string.wins) + playerA.getWins());
				lossesCounterA.setText(getResources().getString(R.string.losses) + playerA.getLosses());
			} else {
				// Rotation Logic.
				listOfPlayers.add(playerB);
				playerB = null; // The player B have passed.
				playerB = listOfPlayers.get(0);
				listOfPlayers.remove(0);
				contestantB.setText(playerB.getName());
				winsCounterB.setText(getResources().getString(R.string.wins) + playerB.getWins());
				lossesCounterB.setText(getResources().getString(R.string.losses) + playerB.getLosses());
			}
			adapter.notifyDataSetChanged();
			break;

		default:
			break;
		}

	}

	private void giveThisManACrown() {

		if (playerA.getWins() > wins) {
			wins = playerA.getWins();
			fullResetOfMvp();
			playerA.setMvp(true);
			mvpA.setVisibility(View.VISIBLE);
			mvpB.setVisibility(View.INVISIBLE);
		} else if (playerB.getWins() > wins) {
			wins = playerB.getWins();
			fullResetOfMvp();
			playerB.setMvp(true);
			mvpB.setVisibility(View.VISIBLE);
			mvpA.setVisibility(View.INVISIBLE);
		} else {
			for (Player player : listOfPlayers) {
				if (player.getWins() > wins) {
					fullResetOfMvp();
					player.setMvp(true);
					break;
				}
			}
		}

	}

	private void fullResetOfMvp() {

		playerA.setMvp(false);
		playerB.setMvp(false);

		for (Player player : listOfPlayers) {
			player.setMvp(false);
		}

	}

	public void setNewContest(Contest contest) {
		this.contest = contest;

	}

	public void setListOfPlayers(List<Player> listOfPlayers, int i) {
		// Set any kind of data that i would like to handle.
		this.listOfPlayers = new ArrayList<Player>();
		this.listOfPlayers.addAll(listOfPlayers);

		if (i == 1) {
			long seed = System.nanoTime();
			Collections.shuffle(this.listOfPlayers, new Random(seed));
			Collections.shuffle(this.listOfPlayers, new Random(seed));
		}

	}

	public void receiveNewChallenger(Player newPlayer) {
		listOfPlayers.add(newPlayer);
		SmashRotationBO.getInstance(getActivity()).getListOfPlayers().add(newPlayer);
		adapter.notifyDataSetChanged();
	}
}