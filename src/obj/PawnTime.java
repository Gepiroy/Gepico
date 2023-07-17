package obj;

public class PawnTime {
	public double time=0;
	Pawn p;
	
	public PawnTime(Pawn p){
		this.p=p;
	}
	
	public void day(double work, double workpath){
		time=16;//8h to sleep
		time-=work;
		//TODO контент для павнов во время дороги, например менять политические взгляды или генерировать "идеи",
		//которые позже ко всяким терактам приводить будут.
		time-=workpath;
	}
	
	public boolean usePartOfTime(){
		
		return false;
	}
}
