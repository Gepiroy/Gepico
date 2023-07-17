package obj;

import WCutils.TextUtil;

public enum Thing {
	Corn("&6��������", 0.6f),
	Sugar("&f�����", 0.6f),
	Meat("&6����", 0.7f),
	Alco("&d����", 1.7f);
	
	public final String display;
	public final float price;
	
	Thing(String display, float price){
		this.display=TextUtil.str(display);
		this.price=price;
	}
}
