package br.com.tick.smashrotation.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import br.com.tick.smashrotation.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class SmashRotationFragment extends Fragment {

	public SmashRotationFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_smash_rotation, container, false);
		return rootView;
	}
}