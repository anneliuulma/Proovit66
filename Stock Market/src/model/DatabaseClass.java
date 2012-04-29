package model;

import java.util.ResourceBundle;

public class DatabaseClass {
	
	String url;
    String usr;
    String pwd;
	
	public DatabaseClass() {
		try {
	    	ResourceBundle bundle = ResourceBundle.getBundle("properties.DBconnection");
	    	this.setUrl(bundle.getString("URL"));
	    	this.setUsr(bundle.getString("usr"));
	    	this.setPwd(bundle.getString("pwd"));
	    	Class.forName(bundle.getString("Driver"));
	    }
		catch(Exception ex) {
			System.out.println("Error: "+ex.getMessage());
		}
	}

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUsr() {
		return usr;
	}
	public void setUsr(String usr) {
		this.usr = usr;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
}