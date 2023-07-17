package BuildSegments;

import java.util.List;

import BuildSystem.Building;
import obj.Pawn;

public class WorkPlace {
	public int slots;
	public List<Pawn> pawns;
	public int education;
	public int payment;
	
	public Building assign;
	
	public WorkPlace(Building b, int slots, int edu, int payment){
		this.assign=b;
		this.slots=slots;
		this.education=edu;
		this.payment=payment;
	}
}
