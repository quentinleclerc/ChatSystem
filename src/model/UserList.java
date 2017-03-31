package model;


import java.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UserList extends Observable {

    private ArrayList<String> usernames = new ArrayList<>();

    private ArrayList<MessageUser> users = new ArrayList<>();

    private ObservableList<String> obsUsersList;

    private static UserList INSTANCE = new UserList();

    private UserList() {
        obsUsersList = FXCollections.observableList(usernames);
    }

    public static synchronized UserList getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserList();
        }

        return INSTANCE;
    }

    public void add(int index, MessageUser user){
        users.add(index, user);
        usernames.add(index, user.getPseudo());
        setChanged();
        notifyObservers();
    }

    public void add(MessageUser user) {
        users.add(user);
        usernames.add(user.getPseudo());
        setChanged();
        notifyObservers();
    }



    public void remove(int index, MessageUser user){
        users.remove(index);
        usernames.remove(index);
        setChanged();
        notifyObservers();
    }

    public ObservableList<String> getObsUsersList() {
        return obsUsersList;
    }


}


