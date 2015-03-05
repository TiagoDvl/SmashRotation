package br.com.tick.smashrotation.bo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import br.com.tick.smashrotation.domain.Contest;
import br.com.tick.smashrotation.domain.Player;
import br.com.tick.smashrotation.persistence.Serialization;

public class SmashRotationBO {

	private static SmashRotationBO instance = null;
	private List<Player> listOfPlayers;
	private Player chosenPlayer;
	private Contest contest;

	protected SmashRotationBO(Context context) {
		contest = new Contest();
	}

	public static SmashRotationBO getInstance(Context context) {
		if (instance == null) {
			instance = new SmashRotationBO(context);
			
			if(instance.getListOfPlayers() == null){
				instance.setListOfPlayers(new ArrayList<Player>());
			}
			
			instance.getSerializedObjects(context);
		}
		return instance;
	}

	public List<Player> getListOfPlayers() {
		return listOfPlayers;
	}

	public void setListOfPlayers(List<Player> listOfPlayers) {
		this.listOfPlayers = listOfPlayers;
	}

	public Player getChosenPlayer() {
		return chosenPlayer;
	}

	public void setChosenPlayer(Player chosenPlayer) {
		this.chosenPlayer = chosenPlayer;
	}

	public Contest getContest() {
		return contest;
	}

	public void setContest(Contest contest) {
		this.contest = contest;
	}
	
	@SuppressWarnings("unchecked")
	public void getSerializedObjects(Context ctx){
		
		try {
			ObjectInputStream is = Serialization.createDataInputStream(ctx.getFilesDir().getPath());
			if (is != null){
				instance.setListOfPlayers((List<Player>) is.readObject());
				is.close();
				
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
