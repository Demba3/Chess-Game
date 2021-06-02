package util;

public class Player {
	private String name;
	private int eloRating;
	public Player(String name, int eloRating) {
		this.name = name;
		this.eloRating = eloRating;
	}
	public String toString() {
		return name + ", elo: "+ eloRating;
	}
}
