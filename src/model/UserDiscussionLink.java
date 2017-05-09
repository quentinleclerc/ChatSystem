package model;


import view.ConversationView;
import communication.*;

import java.util.Hashtable;

public class UserDiscussionLink {
	
	private Hashtable<User, ConversationView> userMessages;

	public UserDiscussionLink(){
		userMessages = new Hashtable<>();
	}
	
	public void addDiscussion(User user){
		if(!userMessages.containsKey(user)){
			userMessages.put(user, new ConversationView());
		}
	}

	public synchronized ConversationView getUserMessageQueue(User user){
		//return null if the user is not in the HashMap
		return userMessages.get(user);
	}

}
