package br.com.tick.smashrotation.task;

import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import br.com.tick.smashrotation.domain.Contest;
import br.com.tick.smashrotation.listener.ISmashRotation;

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
		String adress = null;
		String cep = null;
		try {
			List<Address> user = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
			adress = user.get(0).getAddressLine(0);
			cep = user.get(0).getPostalCode();

		} catch (Exception e) {
			
		}
		return adress+" | "+cep;
	}
	
	@Override
	protected void onPostExecute(String result) {
		if (dialog.isShowing()) {
            dialog.dismiss();
        }
		
		if(!result.equals("")){
//			listener.populateWithAddress(result);
			contest.setLocation(result);
			listener.showResultFragment(contest);
		}
	}

}
