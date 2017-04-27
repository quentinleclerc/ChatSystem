package model;

import java.io.Serializable;


public class Message implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String data;
	private User emetteur;

	public Message(String msg, User usr) {
		this.data = msg;
		emetteur = usr;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	public User getEmetteur(){
		return this.emetteur;
	}
}
