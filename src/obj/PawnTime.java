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
		//TODO ������� ��� ������ �� ����� ������, �������� ������ ������������ ������� ��� ������������ "����",
		//������� ����� �� ������ �������� ��������� �����.
		time-=workpath;
	}
	
	public boolean usePartOfTime(){
		
		return false;
	}
}
