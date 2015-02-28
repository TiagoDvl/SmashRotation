package br.com.tick.smashrotation.fragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import br.com.tick.smashrotation.R;
import br.com.tick.smashrotation.domain.Player;
import br.com.tick.smashrotation.fragment.adapter.RotationAdapter;
import br.com.tick.smashrotation.listener.ISmashRotation;

public class StartRotationFragment extends Fragment implements OnClickListener {

	private transient TextView contestantA;
	private transient TextView contestantB;
	private transient int contestantPositionA;
	private transient int contestantPositionB;
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
		for (int i : indices){
			listOfPlayers.remove(i);
			
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.contestant_a:
			showActionsDialog(contestantPositionA);
			break;

		case R.id.contestant_b:
			showActionsDialog(contestantPositionB);
			break;

		default:
			break;
		}

	}

	private void showActionsDialog(int position) {
		listener.showActionsDialog(position);

	}

	public void setData(List<Player> listOfPlayers) {
		// Set any kind of data that i would like to handle.
		this.listOfPlayers = new ArrayList<Player>();
		this.listOfPlayers.addAll(listOfPlayers);
		long seed = System.nanoTime();
		Collections.shuffle(listOfPlayers, new Random(seed));

	}

}