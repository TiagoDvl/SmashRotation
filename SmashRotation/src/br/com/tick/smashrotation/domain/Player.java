package br.com.tick.smashrotation.domain;

import java.io.Serializable;

public class Player implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3308386274455299289L;

	public Player() {
		// Construct something inside.
	}

	private Integer id = 0;
	private String name;
	private Integer wins = 0;
	private Integer losses = 0;
	private boolean selected = false;
	private boolean mvp = false;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getWins() {
		return wins;
	}

	public void setWins(Integer wins) {
		this.wins = wins;
	}

	public int getLosses() {
		return losses;
	}

	public void setLosses(Integer losses) {
		this.losses = losses;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}

	public boolean isMvp() {
		return mvp;
	}

	public void setMvp(boolean mvp) {
		this.mvp = mvp;
	}
	
}
