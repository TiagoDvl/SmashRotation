package br.com.tick.smashrotation.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import br.com.tick.smashrotation.R;
import br.com.tick.smashrotation.domain.Player;
import br.com.tick.smashrotation.fragment.adapter.RotationAdapter;

public class StartRotationFragment extends Fragment implements OnClickListener {
	
	private transient TextView contestantA;
	private transient TextView contestantB;
	private transient List<Player> listOfPlayers;
	private transient ListView rotation;
	private transient RotationAdapter adapter;

	public StartRotationFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_start_rotation, container, false);
		
		contestantA = (TextView) rootView.findViewById(R.id.contestant_a_name);
		contestantB = (TextView) rootView.findViewById(R.id.contestant_b_name);
		rotation = (ListView) rootView.findViewById(R.id.rotation);
		
		setContestantsNames(listOfPlayers.get(0).getName(),listOfPlayers.get(1).getName());
		
		List<Player> rotationList = new ArrayList<Player>();
		rotationList = listOfPlayers.subList(2, listOfPlayers.size());
		adapter = new RotationAdapter(getActivity(), rotationList);
		rotation.setAdapter(adapter);
		
		return rootView;
	}

	private void setContestantsNames(String nameA, String nameB) {
		contestantA.setText(nameA);
		contestantB.setText(nameB);
	}

	@Override
	public void onClick(View v) {

	}

	public void setData(List<Player> listOfPlayers) {
		// Set any kind of data that i would like to handle.
		this.listOfPlayers = listOfPlayers;
		
	}

}