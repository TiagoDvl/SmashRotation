package br.com.tick.rotation.task;

import java.io.IOException;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import br.com.tick.rotation.domain.Contest;
import br.com.tick.rotation.listener.ISmashRotation;
import br.com.tick.rotation.utils.Utils;

public class LocationTask extends AsyncTask<Void, Void, String> {

	private Location location;
	private ProgressDialog dialog;
	private Context ctx;
	private ISmashRotation listener;
	private Contest contest;

	public LocationTask(Location location, Context context, ISmashRotation listener, Contest contest) {
		this.location = location;
		this.ctx = context;
		this.listener = listener;
		this.contest = contest;
		dialog = new ProgressDialog(context);
	}

	@Override
	protected void onPreExecute() {
		this.dialog.setMessage("Progress start");
		this.dialog.show();
	}

	@Override
	protected String doInBackground(Void... params) {
		Geocoder geocoder = new Geocoder(ctx);
		String address = null;
		if (Utils.isNetworkAvailable(ctx)) {
			try {
				if (geocoder != null) {
					List<Address> user = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
					address = user.get(0).getAddressLine(1);
				}

			} catch (IOException e) {
				// probably service not available.
				return address;
			} catch (NullPointerException e) {
                return null;
            }
		}
		return address;
	}

	@Override
	protected void onPostExecute(String result) {
		if (dialog.isShowing()) {
			dialog.dismiss();
		}

		if (result != null && !result.equals("")) {
			// listener.populateWithAddress(result);
			contest.setLocation(result);
			listener.showResultFragment(contest);
		} else {
			contest.setLocation("Not found");
			listener.showResultFragment(contest);
		}
	}

}
