package model;

import java.util.Hashtable;

public class UserDiscussionLink {
	
	private Hashtable<User, MessageQueue> userMessages;
	
	private static  UserDiscussionLink INSTANCE = null;
	
	private UserDiscussionLink(){
		userMessages = new Hashtable<User, MessageQueue>();
	}
	
    public synchronized static UserDiscussionLink getInstance(){
    	if(INSTANCE == null){
    		INSTANCE = new UserDiscussionLink();
    	}
    		return INSTANCE;
    }
	
	public void addUserDiscussion(User usr){
		if(!userMessages.containsKey(usr)){
			userMessages.put(usr, new MessageQueue());
		}
	}
	
	public boolean removeCouple(User usr, MessageQueue mq){
		return userMessages.remove(usr, mq);
	}
	
	public void removeCouple(User usr){
		userMessages.remove(usr);
	}
	
	public synchronized MessageQueue getUserMessageQueue(User usr){
		//return null if the user is not in the HashMap
		return userMessages.get(usr);
	}
	
	public boolean containsUser(User user) {
		return userMessages.containsKey(user);
	}
	
	public boolean containsMQ(MessageQueue mq) {
		return userMessages.contains(mq);
	}
	
	public void eraseAllDiscussion(){
		userMessages.clear();
	}

}
