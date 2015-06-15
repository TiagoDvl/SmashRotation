package br.com.tick.smashrotation.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import br.com.tick.smashrotation.R;
import br.com.tick.smashrotation.listener.ISmashRotation;
import br.com.tick.smashrotation.utils.DisplayUtil;

public class SplashScreenFragment extends Fragment implements OnClickListener {

	private transient ISmashRotation listener;

	public SplashScreenFragment() {
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		this.listener = (ISmashRotation) getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_splash_rotation, container, false);
		DisplayUtil.setLayoutParams((ViewGroup) rootView);

		final Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			public void run() {
				listener.showMainScreen();
			}
		}, 3000);

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