package model;


import view.ConversationView;
import communication.*;

import java.util.Hashtable;

public class UserDiscussionLink {
	
	private Hashtable<User, ConversationView> userMessages;
	
	private User localUser;

	public UserDiscussionLink(User localUser){
		this.localUser = localUser;
		userMessages = new Hashtable<User, ConversationView>();
	}
	
	public void addDiscussion(User usr){
		if(!userMessages.containsKey(usr)){
			userMessages.put(usr, new ConversationView());
		}
	}
	
	public boolean removeCouple(User usr, Discussion mq){
		return userMessages.remove(usr, mq);
	}
	
	public void removeCouple(User usr){
		userMessages.remove(usr);
	}
	
	public synchronized ConversationView getUserMessageQueue(User usr){
		//return null if the user is not in the HashMap
		return userMessages.get(usr);
	}
	
	public boolean containsUser(User user) {
		return userMessages.containsKey(user);
	}
	
	public boolean containsMQ(Discussion mq) {
		return userMessages.contains(mq);
	}
	
	public void eraseAllDiscussion() {
		userMessages.clear();
	}

}
