package model;


import java.util.*;

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
    
    public static UserList getInstance(){
    	if(INSTANCE == null){
    		INSTANCE = new UserList();
    	}
    		return INSTANCE;
    }

    public void add(int index, MessageUser user){
        users.add(index, user);
        usernames.add(index, user.getPseudo());
        obsUsersList.add(index, user.getPseudo());
    }

    public void add(MessageUser user) {
        users.add(user);
        usernames.add(user.getPseudo());
        obsUsersList.add(user.getPseudo());
    }

    public void remove(int index){
        users.remove(index);
        usernames.remove(index);
        obsUsersList.remove(index);
    }
    
    public void remove(MessageUser user){
        users.remove(user);
        usernames.remove(user.getPseudo());
        obsUsersList.remove(user.getPseudo());
    }
    
    public void clearAll(){
    	users.clear();
    	usernames.clear();
    	obsUsersList.clear();
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


