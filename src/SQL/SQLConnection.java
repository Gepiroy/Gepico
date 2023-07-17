package SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import Gepico.main;
import obj.Conf;

public class SQLConnection {
	private Connection c;
	String host,port,login,pass,database,table;
	Conf conf;
	int version=1;//Поменять эту версию, если состав строк был изменён.
	
	public List<SQLine> lines;//Строки нашей таблицы в оч удобном формате
	
	public SQLConnection(){
		conf=new Conf(main.instance.getDataFolder()+"/mysql.yml");
		lines.add(new SQLine("Player", "TEXT(20)", "error"));
		lines.add(new SQLine("Lvl", "TINYINT NOT NULL", "1"));
		lines.add(new SQLine("Money", "DOUBLE NOT NULL", "0.0"));
		if(!conf.contains("Host")){
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"No MySQL cfg. "+ChatColor.WHITE+"Refilling...");
			conf.set("Host", "localhost");
			conf.set("User", "mysql");
			conf.set("Pass", "mysql");
			conf.set("BaseName", "gepcraft");
			conf.set("Table", "table");
			conf.save();
		}
		host = conf.getString("Host");port = "3306";login = conf.getString("User");pass = conf.getString("Pass");database = conf.getString("BaseName");
		table = conf.getString("Table");
	}
	
	void reVersion(){//Если версия изменилась, т. е. строки другие
		//Пока что мне лень писать переход :3
	}
	
	public void connect(){
		if(c != null){//Если мы уже подключены
		    close();//То закрываем его нафиг, чтобы по-новой подключиться.
		}
		try{
		    c = DriverManager.getConnection("jdbc:mysql://" + host + ":3306/" + database + "?useUnicode=true&characterEncoding=utf8&autoReconnect=true" , login, pass);
		    TryToCreateTable();
		}catch(SQLException ex){
		    ex.printStackTrace();
		}
	}
	public void close(){
        try{
            c.close();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
	}
	public void TryToCreateTable(){
		Statement state;
		try{
			state = c.createStatement();
			String update="CREATE TABLE IF NOT EXISTS " + database + "." + table +
					" (id INT NOT NULL AUTO_INCREMENT,";
			for(SQLine s:lines){
				update+=s.key+" "+s.type+",";
			}
			update+="PRIMARY KEY(id));";
			state.executeUpdate(update);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	public void NewStats(String where, String value){
		Statement state;
		ResultSet res;
		try{
			state = c.createStatement();
			res = state.executeQuery("SELECT * FROM " + database + "." + table + " WHERE "+where+" = '" + value + "';");
			
			if(!res.next()){
				String update="INSERT INTO " + database + "." + table + "(";
				int i=0;
				for(SQLine s:lines){
					if(i!=0)update+=",";
					update+=s.key;
					i++;
				}
				update+=") VALUES(";
				i=0;
				for(SQLine s:lines){
					if(i!=0)update+=",";
					if(s.key==where)update+="'"+value+"'";
					else update+="'"+s.defaultSet+"'";
					i++;
				}
				update+=");";
				state.executeUpdate(update);
			}
			else{
				String update="UPDATE " + database + "." + table + " SET ";
				int i=0;
				for(SQLine s:lines){
					if(s.key==where)continue;
					if(i!=0)update+=",";
					update+=s.key+"='"+s.defaultSet+"'";
					i++;
				}
				update+="WHERE "+where+"='"+value+"';";
				state.executeUpdate(update);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	public void SetText(String where, String value, String col, String set){
		Statement state;
		ResultSet res;
		try{
			state = c.createStatement();
			res = state.executeQuery("SELECT * FROM " + database + "." + table + " WHERE "+where+" = '" + value + "';");
			
			if(!res.next())state.executeUpdate("INSERT INTO " + database + "." + table + " ("+where+","+col+") VALUES('" + value + "','" + set + "');");
			else state.executeUpdate("UPDATE " + database + "." + table + " SET "+col+" = '" + set + "' WHERE "+where+" = '" + value + "';");
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
}