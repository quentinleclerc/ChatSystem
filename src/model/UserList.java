package model;


import java.util.*;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UserList {

    private ArrayList<String> usernames = new ArrayList<>();

    private ArrayList<MessageUser> users = new ArrayList<>();

    private final ObservableList<String> obsUsersList;
    
    private final ObjectProperty<String> currentUser = new SimpleObjectProperty<>(null);
    
    private static UserList INSTANCE = null;
    
    private UserList() {
        obsUsersList = FXCollections.observableArrayList(usernames);
    }
    
    public synchronized static UserList getInstance(){
    	if(INSTANCE == null){
    		INSTANCE = new UserList();
    	}
    		return INSTANCE;
    }

    public void add(int index, MessageUser user){
        synchronized (this) {
            users.add(index, user);
            usernames.add(index, user.getPseudo());
            obsUsersList.add(index, user.getPseudo());
        }
    }

    public void add(MessageUser user) {
        synchronized (this) {
            users.add(user);
            usernames.add(user.getPseudo());
            obsUsersList.add(user.getPseudo());
        }
    }

    public synchronized void remove(int index){
        users.remove(index);
        usernames.remove(index);
        obsUsersList.remove(index);
    }
    
    public synchronized void remove(MessageUser user){
        users.remove(user);
        usernames.remove(user.getPseudo());
        obsUsersList.remove(user.getPseudo());
    }
    
    public synchronized void clearAll(){
    	users.clear();
    	usernames.clear();
    	obsUsersList.clear();
    }

    public int indexOf(MessageUser user) {
        return usernames.indexOf(user.getPseudo());
    }

    public boolean contains(MessageUser user) {
        return usernames.contains(user.getPseudo());
    }

    public ObjectProperty<String> currentUserProperty() {
        return currentUser ;
    }

    public final String getCurrentUser() {
        return currentUserProperty().get();
    }

    public final void setCurrentUser(String user) {
        currentUserProperty().set(user);
    }

    public ObservableList<String> getObsUsersList() {
        return obsUsersList;
    }


}


