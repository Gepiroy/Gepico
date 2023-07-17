package BuildSystem;

import obj.Thing;

public class Production {
	public final Thing t;
	public final int perMin;
	
	float tmp=0;
	
	public Production(Thing t, int perMin){
		this.t=t;
		this.perMin=perMin;
	}
	
	public int prod(float coef){//every sec, 60 = min
		tmp+=perMin*coef/60.0f;
		int ret=(int) (tmp/1);
		tmp-=(int)tmp/1;
		return ret;
	}
}
