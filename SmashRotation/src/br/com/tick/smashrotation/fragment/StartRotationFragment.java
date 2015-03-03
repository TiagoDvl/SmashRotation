package br.com.tick.smashrotation.fragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import br.com.tick.smashrotation.R;
import br.com.tick.smashrotation.bo.SmashRotationBO;
import br.com.tick.smashrotation.domain.Contest;
import br.com.tick.smashrotation.domain.Player;
import br.com.tick.smashrotation.fragment.adapter.RotationAdapter;
import br.com.tick.smashrotation.listener.ISmashRotation;

public class StartRotationFragment extends Fragment implements OnClickListener {

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

		contestantA = (TextView) rootView.findViewById(R.id.contestant_a_name);
		contestantB = (TextView) rootView.findViewById(R.id.contestant_b_name);
		contestantARelative = (RelativeLayout) rootView.findViewById(R.id.contestant_a);
		contestantBRelative = (RelativeLayout) rootView.findViewById(R.id.contestant_b);
		rotation = (ListView) rootView.findViewById(R.id.rotation);

		startRotation();

		contestantARelative.setOnClickListener(this);
		contestantBRelative.setOnClickListener(this);

		adapter = new RotationAdapter(getActivity(), listOfPlayers);
		rotation.setAdapter(adapter);

		return rootView;
	}

	private void startRotation() {
		playerA = listOfPlayers.get(FIRST_CONTESTANT);
		contestantA.setText(playerA.getName());
		playerB = listOfPlayers.get(SECOND_CONTESTANT);
		contestantB.setText(playerB.getName());

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
				playerB.setLosses(playerB.getLosses() + 1);
				
				// Contest changes.
				contest.setNumberOfGames(contest.getNumberOfGames() + 1);
				
				/** This is commented because i did not put loads of efforts on it.
				 * It is probably wrong or giving false-positives.
				 * 
				if (contest.getBestPlayer() == null) {
					contest.setBestPlayer(playerA);
				} else {
					for (Player itPlayer : listOfPlayers) {
						if (itPlayer.getWins() > contest.getBestPlayer().getWins()){
							contest.setBestPlayer(itPlayer);
							System.out.println("FOREACH -> "+ itPlayer.getName());
						}
					}
				}
				 **/
				
				// Rotation Logic.
				listOfPlayers.add(playerB);
				playerB = null; // The player B have lost.
				playerB = listOfPlayers.get(0);
				listOfPlayers.remove(0);
				contestantB.setText(playerB.getName());
				adapter.notifyDataSetChanged();
			} else {
				// Contest changes.
				contest.setNumberOfGames(contest.getNumberOfGames() + 1);

				// Score change.
				playerB.setWins(playerA.getWins() + 1);
				playerA.setLosses(playerA.getLosses() + 1);
				
				// Contest changes.
				contest.setNumberOfGames(contest.getNumberOfGames() + 1);

				// Rotation Logic.
				listOfPlayers.add(playerA);
				playerA = null; // The player A have lost.
				playerA = listOfPlayers.get(0);
				listOfPlayers.remove(0);
				contestantA.setText(playerA.getName());
				adapter.notifyDataSetChanged();
			}

			break;
		case 1:
			Toast.makeText(getActivity(), "Jogador " + player.getName() + " Perdeu!", Toast.LENGTH_SHORT).show();
			if (player.equals(playerA)) {
				// Contest changes.
				contest.setNumberOfGames(contest.getNumberOfGames() + 1);

				// Score change.
				playerB.setWins(playerB.getWins() + 1);
				playerA.setLosses(playerA.getLosses() + 1);

				// Rotation Logic.
				listOfPlayers.add(playerA);
				playerA = null; // The player A have lost.
				playerA = listOfPlayers.get(0);
				listOfPlayers.remove(0);
				contestantA.setText(playerA.getName());
				adapter.notifyDataSetChanged();
			} else {
				// Contest changes.
				contest.setNumberOfGames(contest.getNumberOfGames() + 1);

				// Score change.
				playerA.setWins(playerA.getWins() + 1);
				playerB.setLosses(playerB.getLosses() + 1);
				
				// Contest changes.
				contest.setNumberOfGames(contest.getNumberOfGames() + 1);

				if (contest.getBestPlayer() == null) {
					contest.setBestPlayer(playerA);
				} else {
					for (Player itPlayer : listOfPlayers) {
						if (itPlayer.getWins() > contest.getBestPlayer().getWins()){
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
				adapter.notifyDataSetChanged();
			}
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
				adapter.notifyDataSetChanged();
			} else {
				// Rotation Logic.
				listOfPlayers.add(playerB);
				playerB = null; // The player B have passed.
				playerB = listOfPlayers.get(0);
				listOfPlayers.remove(0);
				contestantB.setText(playerB.getName());
				adapter.notifyDataSetChanged();
			}
			break;

		default:
			System.out.println("Action -> " + action + " -> Default");
			break;
		}
		
		System.out.println("Melhor jogador -> "+ contest.getBestPlayer().getName());

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

}