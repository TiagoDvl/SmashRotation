package br.com.tick.smashrotation.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import br.com.tick.smashrotation.R;
import br.com.tick.smashrotation.domain.Contest;

public class ResultsFragment extends Fragment implements OnClickListener {

//	private transient ISmashRotation listener;
	private transient Contest contest;

	private transient TextView numberOfGames;
	private transient TextView bestPlayer;
	private transient TextView worstPlayer;
	private transient TextView location;

	public ResultsFragment() {
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

//		this.listener = (ISmashRotation) getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_result, container, false);

		numberOfGames = (TextView) rootView.findViewById(R.id.number_of_games);
		bestPlayer = (TextView) rootView.findViewById(R.id.best_player);
		worstPlayer = (TextView) rootView.findViewById(R.id.worst_player);
		location = (TextView) rootView.findViewById(R.id.location);

		numberOfGames.setText(getActivity().getResources().getString(R.string.result_number_of_games) + contest.getNumberOfGames());
		bestPlayer.setText(getActivity().getResources().getString(R.string.result_best_player) + contest.getBestPlayer().getName());
		worstPlayer.setText(getActivity().getResources().getString(R.string.result_worst_player) + contest.getWorstPlayer().getName());
		location.setText(getActivity().getResources().getString(R.string.result_location) + contest.getLocation());

		return rootView;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		default:
			break;
		}

	}

	public void setContest(Contest contest) {
		this.contest = contest;

	}

}