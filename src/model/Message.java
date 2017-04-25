package model;

import java.io.Serializable;


public class Message implements Serializable {
	
	private String data;

	public Message(String msg) {
		this.data = msg;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
