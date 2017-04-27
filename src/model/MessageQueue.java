package model;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MessageQueue {

    private ConcurrentLinkedQueue<Message> discussion;

    public MessageQueue() {
        this.discussion = new ConcurrentLinkedQueue<>();
    }

    public ConcurrentLinkedQueue<Message> getDiscussion(){
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
			if(message.getEmetteur() == UserList.getInstance().getLocalUser())
				str += "[Moi]" + message.getData() + "\n";
			else
				str += "["+ message.getEmetteur().getPseudo() +"]" + message.getData() + "\n";
		}
		return str;
    }

}

