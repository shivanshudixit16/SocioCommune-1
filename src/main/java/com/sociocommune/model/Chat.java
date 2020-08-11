package com.sociocommune.model;

import org.springframework.stereotype.Component;

import org.springframework.data.annotation.Id;
@Component
public class Chat {
	@Id
	public String id;
	public String sender;
	public String receiver;
    public String msg ;
    public String time ;
	
	
	public Chat(String sender, String receiver,String msg) {
		super();
		this.sender = sender;
		this.receiver = receiver;
        this.msg= msg;
        this.time=""+(System.currentTimeMillis() / 1000L);
	}
	
	

	public Chat() {}
	
	@Override
	public String toString() {
		return String.format(
	        "Sender=%s, Receiver='%s', Msg='%s'",
	        sender, receiver, msg);
	  }


}
