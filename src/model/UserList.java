package model;


import java.net.InetAddress;
import java.util.*;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UserList {


    private List<User> users = new ArrayList<>();

    private ObservableList<User> obsUsersList;

    public UserList() {
        obsUsersList = FXCollections.observableArrayList(users);
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

    public void replace(User oldUsr, User newUser){
    	int i = users.indexOf(oldUsr);
    	users.remove(i);
    	users.add(i, newUser);

        int j = obsUsersList.indexOf(oldUsr);
        Platform.runLater(() -> {
            obsUsersList.remove(j);
            obsUsersList.add(j, newUser);
        });
    }
    
    public int indexOf(User user) {
        return users.indexOf(user);
    }

    public boolean contains(User user) {
        return users.contains(user);
    }
    
    public User getUser(int i){
    	return users.get(i);
    }
    
    public User findUser(String pseudo, InetAddress iP){
    	Iterator<User> userIterator = users.iterator();
    	User userFound = null;
    	
        while (userIterator.hasNext()) {
        	User possibleUser = userIterator.next();
        	if(possibleUser.getPseudo().equals(pseudo) && possibleUser.getIP().equals(iP)){
        		userFound = possibleUser;
        	}
        }
        return userFound;
    }

    public ObservableList<User> getObsUsersList() {
        return obsUsersList;
    }

    public List<User> getUsers() {
        return this.users;
    }

}
