package model;

import java.util.Hashtable;

public class UserDiscussionLink {
	
	private Hashtable<User, Discussion> userMessages;
	
	private User localUser;

	public UserDiscussionLink(User localUser){
		this.localUser = localUser;
		userMessages = new Hashtable<User, Discussion>();
	}
	
	public void addDiscussion(User usr){
		if(!userMessages.containsKey(usr)){
			userMessages.put(usr, new Discussion(localUser));
		}
	}
	
	public boolean removeCouple(User usr, Discussion mq){
		return userMessages.remove(usr, mq);
	}
	
	public void removeCouple(User usr){
		userMessages.remove(usr);
	}
	
	public synchronized Discussion getUserMessageQueue(User usr){
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
