package br.com.tick.rotation.component;

import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import br.com.tick.rotation.domain.Player;
import br.com.tick.rotation.listener.IChoseYou;

public class ConstructedOnCheckedChangeListener implements
		OnCheckedChangeListener {

	private Player player;
	private IChoseYou listener;

	public ConstructedOnCheckedChangeListener(Player player, IChoseYou listener) {
		this.player = player;
		this.listener = listener;
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		listener.updateChosenPlayers(player, isChecked);

	}

}
