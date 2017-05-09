package model;

import java.util.ArrayList;
import java.util.Iterator;
import communication.*;


/*
    This class is not used anymore for the project. However some methods have been tested in the test package.
 */

public class Discussion {

    private final User localUser;

    private ArrayList<Message> discussion;

    public Discussion(User localUser) {
        this.localUser = localUser;
        this.discussion = new ArrayList<>();
    }

    public Boolean addMessage(Message msg) {
        return discussion.add(msg);
    }

    @Override
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

