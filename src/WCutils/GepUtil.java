package WCutils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;

public class GepUtil {
	//Раньше это была утилита, в которой лежало ВСЁ, ЧТО Я КОГДА-ЛИБО СОЗДАВАЛ...
	//Сейчас тут ток возня с листами.
	public static boolean HashMapReplacer(HashMap<String,Integer> hm, String key, int val, boolean zero, boolean set){
		if(hm.containsKey(key)){
			if(set)hm.replace(key, val);
			else hm.replace(key, hm.get(key)+val);
		}
		else{
			hm.put(key, val);
		}
		if(zero){
			if(hm.get(key)<=0){
				hm.remove(key);
				return true;
			}
		}
		return false;
	}
	public static boolean HashMapReplacer(HashMap<String,Double> hm, String key, double val, boolean zero, boolean set){
		if(hm.containsKey(key)){
			if(set)hm.replace(key, val);
			else hm.replace(key, hm.get(key)+val);
		}
		else{
			hm.put(key, val);
		}
		if(zero){
			if(hm.get(key)<=0){
				hm.remove(key);
				return true;
			}
		}
		return false;
	}
	public static boolean HashMapReplacer(HashMap<Location,Integer> hm, Location key, int val, boolean zero, boolean set){
		if(hm.containsKey(key)){
			if(set)hm.replace(key, val);
			else hm.replace(key, hm.get(key)+val);
		}
		else{
			hm.put(key, val);
		}
		if(zero){
			if(hm.get(key)<=0){
				hm.remove(key);
				return true;
			}
		}
		return false;
	}
	public static String chancesByCoef(String[] sts, int[] coefs){
		int coef=0;
		for(int d:coefs){
			coef+=d;
		}
		int r = new Random().nextInt(coef);
		int ch = 0;
		for(int i=0;i<sts.length;i++){
			if(r>=ch&&r<=ch+coefs[i]){
				return sts[i];
			}
			ch+=coefs[i];
		}
		return ""+r;
	}
	public static String chancesByCoef(HashMap<String, Integer> sts){
		int coef=0;
		for(int d:sts.values()){
			coef+=d;
		}
		int r = new Random().nextInt(coef);
		int ch = 0;
		for(String st:sts.keySet()){
			if(r>=ch&&r<ch+sts.get(st)){
				return st;
			}
			ch+=sts.get(st);
		}
		return ""+r;
	}
	public static boolean chance(int ch){
		return new Random().nextInt(100)+1<=ch;
	}
	public static boolean chance(double ch){
		return new Random().nextDouble()<=ch;
	}
	public static String chances(String[] sts, double[] chs){
		double r = new Random().nextInt(100)+new Random().nextDouble();
		double ch = 0.000;
		for(int i=0;i<sts.length;i++){
			if(r>ch&&r<=ch+chs[i]){
				return sts[i];
			}
			ch+=chs[i];
		}
		return "";
	}
	public static ArrayList<String> stringToArrayList(String st){
		ArrayList<String> ret = new ArrayList<>();
		String toadd = "";
		for(int i=0;i<st.length();i++){
			String c = st.charAt(i)+"";
			if(!c.equals(";")){
				toadd=toadd+c;
			}
			else{
				ret.add(toadd);
				toadd="";
			}
		}
		return ret;
	}
	public static String ArrayListToString(ArrayList<String> ara){
		String ret = "";
		for(String st:ara){
			ret = ret+st+";";
		}
		return ret;
	}
	public static String maxFromHM(HashMap<String,Integer> hm){
		String ret="";
		int max=Integer.MIN_VALUE;
		for(String st:hm.keySet()){
			if(hm.get(st)>max){
				max=hm.get(st);
				ret=st;
			}
		}
		return ret;
	}
	//List.contains() works like "==", but i need to use "equals".
	public static boolean contains(List<?> list, Object o){
		for(Object ob:list){
			if(o.equals(ob))return true;
		}
		return false;
	}
}