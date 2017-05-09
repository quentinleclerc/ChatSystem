package model;

import org.mindrot.jbcrypt.BCrypt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class HashedUserCredentialsRetriever implements UserCredentialsRetriever {

    @Override
    public String getHashedPassword(String username) {
        String result = "";
        ArrayList<String[]> users = readUsersPassword();

        for (String[] user : users) {
            if (user[0].equals(username)) {
                result = user[1];
            }
        }
        return result;
    }

    @Override
    public boolean checkPasswordCorrect(String hashed, String password) {
        boolean passwordOK = false;
        try {
            passwordOK = BCrypt.checkpw(password, hashed);
        } catch (IndexOutOfBoundsException ide) {
            System.out.println("User not found in database");
        }
        return passwordOK;
    }

    @Override
    public boolean checkUserRegistered(String username) {
        boolean result = false;
        ArrayList<String[]> users = readUsersPassword();

        for (String[] user : users) {
            if (user[0].equals(username)) {
                result = true;
            }
        }
        return result;
    }

    private ArrayList<String[]> readUsersPassword() {
        URL resource = getClass().getResource("/users/users.txt");
        String fileName = resource.getFile();
        File file = new File(fileName);

        BufferedReader br;
        String line;
        String splitBy = "#";
        ArrayList<String[]> users = new ArrayList<>();

        try {
            br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {
                users.add(line.split(splitBy));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

}
