package Gepico;

public class TimeManager {
	
	
	
	int t=0;
	public void tick(){
		t++;
		if(t%20==0)sec();
		if(t%400==0)month();
		
		
	}
	
	public void sec(){
		
	}
	
	public void month(){
		//for(Pawn p:PawnManager.pawns){
			//if(p.workplace!=null)p.money+=p.workplace.payment;
		//}
	}
}
