package br.com.tick.smashrotation.domain;

public class Player {

	public Player() {
		// Construct something inside.
	}

	private String name;
	private int wins;
	private int losses;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getWins() {
		return wins;
	}

	public void setWins(int wins) {
		this.wins = wins;
	}

	public int getLosses() {
		return losses;
	}

	public void setLosses(int losses) {
		this.losses = losses;
	}

}
