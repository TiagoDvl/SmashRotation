package br.com.tick.smashrotation.fragment.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import br.com.tick.smashrotation.R;
import br.com.tick.smashrotation.domain.Player;
import br.com.tick.smashrotation.fragment.SmashRotationFragment;
import br.com.tick.smashrotation.listener.IChoseYou;

public class PlayersAdapter extends BaseAdapter {

	private transient Context activity;
	private transient List<Player> listOfPlayers;
	private transient LayoutInflater inflater;
	private transient IChoseYou listener;

	public PlayersAdapter(Context context, List<Player> listOfPlayers, IChoseYou listener) {

		this.activity = context;
		this.listOfPlayers = listOfPlayers;
		this.listener = listener;
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
			holder.checkBoxChoosePlayer = (CheckBox) convertView.findViewById(R.id.chosen_player);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		
		holder.playerName.setText(player.getName());
		holder.checkBoxChoosePlayer.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				listener.updateChosenPlayers(player, isChecked);
				
			}
		});
		return convertView;
	}

	private static class ViewHolder {
		TextView playerName;
		CheckBox checkBoxChoosePlayer;
	}

}
