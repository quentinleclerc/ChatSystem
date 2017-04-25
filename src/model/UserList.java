package model;


import java.util.*;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UserList {

    private List<User> users = new ArrayList<>();

    private List<String> usernames = new ArrayList<>();

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

    public void add(int index, User user){
        synchronized (this) {
            users.add(index, user);
            usernames.add(index, user.getPseudo());
            obsUsersList.add(index, user.getPseudo());
        }
    }

    public void add(User user) {
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
    
    public synchronized void remove(User user){
        users.remove(user);
        usernames.remove(user.getPseudo());
        obsUsersList.remove(user.getPseudo());
    }
    
    public synchronized void clearAll(){
    	users.clear();
    	usernames.clear();
    	obsUsersList.clear();
    }

    public User getUserByUsername(String username) {
        return users.get(usernames.indexOf(username));
    }

    public int indexOf(User user) {
        return usernames.indexOf(user.getPseudo());
    }

    public boolean contains(User user) {
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


