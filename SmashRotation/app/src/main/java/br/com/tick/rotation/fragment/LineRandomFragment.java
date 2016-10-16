package br.com.tick.rotation.fragment;

import java.util.List;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import br.com.tick.rotation.R;
import br.com.tick.rotation.domain.Player;
import br.com.tick.rotation.listener.ISmashRotation;
import br.com.tick.rotation.utils.DisplayUtil;

public class LineRandomFragment extends Fragment implements OnClickListener {

	private transient ISmashRotation listener;
	private transient Button line;
	private transient Button random;
	private List<Player> chosenPlayers;

	public LineRandomFragment() {
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		this.listener = (ISmashRotation) getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_line_random_dialog, container, false);
		DisplayUtil.setLayoutParams((ViewGroup) rootView);

		Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Oswald-Regular.ttf");

		line = (Button) rootView.findViewById(R.id.line);
		random = (Button) rootView.findViewById(R.id.random);

		line.setTypeface(typeface);
		random.setTypeface(typeface);

		line.setOnClickListener(this);
		random.setOnClickListener(this);

		return rootView;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.line:
			listener.showRotationScreen(chosenPlayers, 0);
			break;
		case R.id.random:
			listener.showRotationScreen(chosenPlayers, 1);
			break;

		default:
			break;
		}

	}

	public void setChosenPlayers(List<Player> chosenPlayers) {
		this.chosenPlayers = chosenPlayers;
	}

}