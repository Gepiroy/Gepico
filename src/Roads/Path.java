package Roads;

import java.util.ArrayList;
import java.util.List;

public class Path {
	public List<OnePath> pathes = new ArrayList<>();
	public int length=0;
	
	public Path(){
		
	}
	
	public Path add(OnePath p){
		pathes.add(p);
		length+=p.length;
		return this;
	}
	
	/**
	 * 
	 * @return true if he reached endpoint of one path.
	 */
	public boolean tick(){
		return false;
	}
}
