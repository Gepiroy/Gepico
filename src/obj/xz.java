package obj;

public class xz {
	
	public int x;
	public int z;
	
	public xz(int x, int z){
		this.x=x;
		this.z=z;
	}
	
	public xz(xz xz){
		this.x=xz.x;
		this.z=xz.z;
	}
	
	public boolean isHere(int x, int z){
		return (this.x==x&&this.z==z);
	}
	
	public boolean isHere(xz xz){
		return (this.x==xz.x&&this.z==xz.z);
	}
	
	public double dist(xz xz){
		return Math.hypot(x-xz.x, z-xz.z);
	}
	
	public xz add(xz xz){
		this.x+=xz.x;
		this.z+=xz.z;
		return this;
	}
	
	public xz add(int x, int z){
		this.x+=x;
		this.z+=z;
		return this;
	}
	
	public xz rot(){
		xz old = new xz(this);
		x=old.z;
		z=-old.x;
		return this;
	}
	
	public void subtract(xz xz){
		this.x-=xz.x;
		this.z-=xz.z;
	}
	
	public void multiply(float m){
		this.x*=m;
		this.z*=m;
	}
	
	public void normalize(){
		double m=Math.sqrt(x*x+z*z);
		x/=m;
		z/=m;
	}
	
	public xz(Conf conf, String where){
		x=conf.getInt(where+".x");
		z=conf.getInt(where+".z");
	}
	
	public void save(Conf conf, String where){
		conf.set(where+".x", x);
		conf.set(where+".z", z);
	}
}
