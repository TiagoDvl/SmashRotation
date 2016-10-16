package br.com.tick.rotation.domain;

public class Contest {

	private int numberOfGames;
	private Player bestPlayer;
	private Player worstPlayer;
	private String location;
	
	public Contest() {
	
	}

	public int getNumberOfGames() {
		return numberOfGames;
	}

	public void setNumberOfGames(int numberOfGames) {
		this.numberOfGames = numberOfGames;
	}

	public Player getBestPlayer() {
		return bestPlayer;
	}

	public void setBestPlayer(Player bestPlayer) {
		this.bestPlayer = bestPlayer;
	}

	public Player getWorstPlayer() {
		return worstPlayer;
	}

	public void setWorstPlayer(Player worstPlayer) {
		this.worstPlayer = worstPlayer;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
