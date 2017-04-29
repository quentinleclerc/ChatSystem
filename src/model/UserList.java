package model;


import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UserList {


    private List<User> users = new ArrayList<>();

    private static UserList INSTANCE = null;

    private ObservableList<User> obsUsersList;

    private UserList() {
        obsUsersList = FXCollections.observableArrayList(users);
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
            obsUsersList.add(index, user);
        }
    }

    public void add(User user) {
        synchronized (this) {
            users.add(user);
            obsUsersList.add(user);
        }
    }

    public synchronized void remove(int index){
        users.remove(index);
        obsUsersList.remove(index);
    }
    
    public synchronized void remove(User user){
        users.remove(user);
        obsUsersList.remove(user);
    }
    
    public synchronized void clearAll(){
    	users.clear();
        obsUsersList.clear();
    }

    public int indexOf(User user) {
        return users.indexOf(user);
    }

    public boolean contains(User user) {
        return users.contains(user);
    }

    public ObservableList<User> getObsUsersList() {
        return obsUsersList;
    }


}


