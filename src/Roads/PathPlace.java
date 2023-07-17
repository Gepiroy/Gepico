package Roads;

import obj.Tile;

public class PathPlace {
	public Tile t;
	public PathPlace prevoius;
	public int price;
	
	public PathPlace(Tile t, PathPlace previous, int price){
		this.t=t;
		this.prevoius = previous;
		this.price = price;
	}
}
