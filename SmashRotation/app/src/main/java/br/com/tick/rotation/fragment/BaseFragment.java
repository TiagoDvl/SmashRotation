package br.com.tick.rotation.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import br.com.tick.rotation.R;
import br.com.tick.rotation.utils.DisplayUtil;

public class BaseFragment extends Fragment implements OnClickListener {

//	private transient ISmashRotation listener;

	public BaseFragment() {
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

//		this.listener = (ISmashRotation) getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_start_rotation, container, false);
		DisplayUtil.setLayoutParams((ViewGroup) rootView);

		return rootView;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		default:
			break;
		}

	}

}