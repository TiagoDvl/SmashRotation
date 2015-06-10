package br.com.tick.smashrotation.fragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Typeface;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import br.com.tick.smashrotation.R;
import br.com.tick.smashrotation.bo.SmashRotationBO;
import br.com.tick.smashrotation.domain.Contest;
import br.com.tick.smashrotation.domain.Player;
import br.com.tick.smashrotation.fragment.adapter.RotationAdapter;
import br.com.tick.smashrotation.listener.ISmashRotation;
import br.com.tick.smashrotation.task.LocationTask;
import br.com.tick.smashrotation.utils.DisplayUtil;

public class StartRotationFragment extends Fragment implements OnClickListener, OnMenuItemClickListener {

	private transient TextView contestantA;
	private transient TextView contestantB;
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
	private transient ImageView menuPopUp;
	private transient RelativeLayout menuPopUpHolder;
	private transient int wins;

	private transient TextView rotationTopBarText;
	private transient TextView nowPlayingText;
	private transient Button finishMatch;
	private transient TextView nextPlayer;
	
	private transient TextView winsCounterA;
	private transient TextView winsCounterB;
	
	private transient TextView lossesCounterA;
	private transient TextView lossesCounterB;

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
		menuPopUp = (ImageView) rootView.findViewById(R.id.top_bar_menu);
		menuPopUpHolder = (RelativeLayout) rootView.findViewById(R.id.menu_holder);

		if (playerA == null && playerB == null) {
			// This will be a problem.
			startRotation();
		} else {
			contestantA.setText(playerA.getName());
			contestantB.setText(playerB.getName());
		}

		contestantARelative.setOnClickListener(this);
		contestantBRelative.setOnClickListener(this);
		menuPopUp.setOnClickListener(this);
		menuPopUpHolder.setOnClickListener(this);
		finishMatch.setOnClickListener(this);

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

		return rootView;
	}

	private void startRotation() {
		playerA = listOfPlayers.get(FIRST_CONTESTANT);
		contestantA.setText(playerA.getName());
		winsCounterA.setText("Wins: "+playerA.getWins());
		lossesCounterA.setText("Losses: "+ playerA.getLosses());
		
		playerB = listOfPlayers.get(SECOND_CONTESTANT);
		contestantB.setText(playerB.getName());
		winsCounterB.setText("Wins: "+playerB.getWins());
		lossesCounterB.setText("Losses: "+ playerB.getLosses());

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
		case R.id.menu_holder:
		case R.id.top_bar_menu:
			showMenuPopup(v);
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

			LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
			Criteria criteria = new Criteria();
			String bestProvider = lm.getBestProvider(criteria, false);
			Location location = lm.getLastKnownLocation(bestProvider);
			LocationTask task = new LocationTask(location, getActivity(), listener, contest);

			task.execute();
			break;
		default:
			break;
		}

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
			Toast.makeText(getActivity(), "Jogador " + player.getName() + " Ganhou!", Toast.LENGTH_SHORT).show();
			if (player.equals(playerA)) {
				// Score change.
				playerA.setWins(playerA.getWins() + 1);
				winsCounterA.setText("Wins: "+ playerA.getWins());
				
				playerB.setLosses(playerB.getLosses() + 1);

				// Contest changes.
				contest.setNumberOfGames(contest.getNumberOfGames() + 1);

				/**
				 * This is commented because i did not put loads of efforts on it. It is probably wrong or giving false-positives.
				 * 
				 * if (contest.getBestPlayer() == null) { contest.setBestPlayer(playerA); } else { for (Player itPlayer : listOfPlayers) { if (itPlayer.getWins() > contest.getBestPlayer().getWins()){
				 * contest.setBestPlayer(itPlayer); System.out.println("FOREACH -> "+ itPlayer.getName()); } } }
				 **/

				// Rotation Logic.
				listOfPlayers.add(playerB);
				playerB = null; // The player B have lost.
				playerB = listOfPlayers.get(0);
				listOfPlayers.remove(0);
				contestantB.setText(playerB.getName());
				winsCounterB.setText("Wins: "+playerB.getWins());
				lossesCounterB.setText("Losses: "+ playerB.getLosses());
			} else {
				// Contest changes.
				contest.setNumberOfGames(contest.getNumberOfGames() + 1);

				// Score change.
				playerB.setWins(playerA.getWins() + 1);
				winsCounterB.setText("Wins: "+ playerB.getWins());
				
				playerA.setLosses(playerA.getLosses() + 1);

				// Contest changes.
				contest.setNumberOfGames(contest.getNumberOfGames() + 1);

				// Rotation Logic.
				listOfPlayers.add(playerA);
				playerA = null; // The player A have lost.
				playerA = listOfPlayers.get(0);
				listOfPlayers.remove(0);
				contestantA.setText(playerA.getName());
				winsCounterA.setText("Wins: "+playerA.getWins());
				lossesCounterA.setText("Losses: "+ playerA.getLosses());
			}

			giveThisManACrown();
			adapter.notifyDataSetChanged();

			break;
		case 1:
			Toast.makeText(getActivity(), "Jogador " + player.getName() + " Perdeu!", Toast.LENGTH_SHORT).show();
			if (player.equals(playerA)) {
				// Contest changes.
				contest.setNumberOfGames(contest.getNumberOfGames() + 1);

				// Score change.
				playerB.setWins(playerB.getWins() + 1);
				winsCounterB.setText("Wins: "+ playerB.getWins());
				
				playerA.setLosses(playerA.getLosses() + 1);

				// Rotation Logic.
				listOfPlayers.add(playerA);
				playerA = null; // The player A have lost.
				playerA = listOfPlayers.get(0);
				listOfPlayers.remove(0);
				contestantA.setText(playerA.getName());
				winsCounterA.setText("Wins: "+playerA.getWins());
				lossesCounterA.setText("Losses: "+ playerA.getLosses());
			} else {
				// Contest changes.
				contest.setNumberOfGames(contest.getNumberOfGames() + 1);

				// Score change.
				playerA.setWins(playerA.getWins() + 1);
				winsCounterA.setText("Wins: "+ playerA.getWins());
				
				playerB.setLosses(playerB.getLosses() + 1);

				// Contest changes.
				contest.setNumberOfGames(contest.getNumberOfGames() + 1);

				if (contest.getBestPlayer() == null) {
					contest.setBestPlayer(playerA);
				} else {
					for (Player itPlayer : listOfPlayers) {
						if (itPlayer.getWins() > contest.getBestPlayer().getWins()) {
							contest.setBestPlayer(itPlayer);
						}
					}
				}

				// Rotation Logic.
				listOfPlayers.add(playerB);
				playerB = null; // The player B have lost.
				playerB = listOfPlayers.get(0);
				listOfPlayers.remove(0);
				contestantB.setText(playerB.getName());
			}

			giveThisManACrown();
			adapter.notifyDataSetChanged();

			break;
		case 2:
			Toast.makeText(getActivity(), "Jogador " + player.getName() + " Passou!", Toast.LENGTH_SHORT).show();
			if (player.equals(playerA)) {
				// Rotation Logic.
				listOfPlayers.add(playerA);
				playerA = null; // The player A have passed.
				playerA = listOfPlayers.get(0);
				listOfPlayers.remove(0);
				contestantA.setText(playerA.getName());
				winsCounterA.setText("Wins: "+playerA.getWins());
				lossesCounterA.setText("Losses: "+ playerA.getLosses());
			} else {
				// Rotation Logic.
				listOfPlayers.add(playerB);
				playerB = null; // The player B have passed.
				playerB = listOfPlayers.get(0);
				listOfPlayers.remove(0);
				contestantB.setText(playerB.getName());
				winsCounterB.setText("Wins: "+playerB.getWins());
				lossesCounterB.setText("Losses: "+ playerB.getLosses());
			}
			adapter.notifyDataSetChanged();
			break;

		default:
			break;
		}

	}

	private void giveThisManACrown() {

		wins = 0;

		if (playerA.getWins() > wins) {
			wins = playerA.getWins();
			fullResetOfMvp();
			playerA.setMvp(true);
		} else if (playerB.getWins() > wins) {
			wins = playerB.getWins();
			fullResetOfMvp();
			playerB.setMvp(true);
		} else {
			for (Player player : listOfPlayers) {
				if (player.getWins() > wins) {
					fullResetOfMvp();
					player.setMvp(true);
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

	public void setListOfPlayers(List<Player> listOfPlayers) {
		// Set any kind of data that i would like to handle.
		this.listOfPlayers = new ArrayList<Player>();
		this.listOfPlayers.addAll(listOfPlayers);
		long seed = System.nanoTime();
		Collections.shuffle(listOfPlayers, new Random(seed));
		Collections.shuffle(listOfPlayers, new Random(seed));

	}

	private void showMenuPopup(final View view) {
		PopupMenu menu = new PopupMenu(getActivity(), view);
		menu.setOnMenuItemClickListener(this);
		MenuInflater inflater = menu.getMenuInflater();
		inflater.inflate(R.menu.menu_popup, menu.getMenu());

		menu.show();
	}

	@Override
	public boolean onMenuItemClick(MenuItem item) {
		switch (item.getItemId()) {

		case R.id.finish_contest:

			break;

		}
		return false;
	}
}