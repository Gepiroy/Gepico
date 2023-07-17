package obj;

import WCutils.TextUtil;

public enum Thing {
	Corn("&6Кукуруза", 0.6f),
	Sugar("&fСахар", 0.6f),
	Meat("&6Мясо", 0.7f),
	Alco("&dВино", 1.7f);
	
	public final String display;
	public final float price;
	
	Thing(String display, float price){
		this.display=TextUtil.str(display);
		this.price=price;
	}
}
