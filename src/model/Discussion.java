package model;

import java.util.ArrayList;
import java.util.Iterator;
import communication.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Discussion {

    private final User localUser;

    private ArrayList<Message> discussion;

    public Discussion(User localUser) {
        this.localUser = localUser;
        this.discussion = new ArrayList<Message>();
    }

    public ArrayList getDiscussion(){
        return discussion;
    }

    public Boolean addMessage(Message msg) {
        return discussion.add(msg);
    }
    
    public boolean addMessage(String msg, User emetteur){
    	Message message = new Message(msg, emetteur);
    	return discussion.add(message);
    }
  
    public String toString(){
    	String str = "";
	    Iterator<Message> listIterator = discussion.iterator();

        while (listIterator.hasNext()) {
			Message message = listIterator.next();
			if(message.getSender().equals(localUser))
				str += "[Moi] " + message.getData() + "\n";
			else
				str += "["+ message.getSender().getPseudo() +"] " + message.getData() + "\n";

		}
		return str;
    }

}

