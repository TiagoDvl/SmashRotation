package br.com.tick.smashrotation.fragment.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.com.tick.smashrotation.R;
import br.com.tick.smashrotation.domain.Player;

public class PlayersAdapter extends BaseAdapter{
	
	private transient Context activity;
	private transient List<Player> listOfPlayers;
	private transient LayoutInflater inflater;
	
	public PlayersAdapter(Context context, List<Player> listOfPlayers) {
		
		this.activity = context;
		this.listOfPlayers = listOfPlayers;
		this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
	}

	@Override
	public int getCount() {
		return listOfPlayers.size();
	}

	@Override
	public Player getItem(int position) {
		return listOfPlayers.get(position);
	}

	@Override
	public long getItemId(int position) {
		return listOfPlayers.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		final Player player = getItem(position);
		
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.adapter_players, ((ViewGroup) null));
			
			
			holder.playerName = (TextView) convertView.findViewById(R.id.list_of_players_names);
			holder.playerName.setText(player.getName());
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		return convertView;
	}
	
	private static class ViewHolder {
		TextView playerName;
	}

}
