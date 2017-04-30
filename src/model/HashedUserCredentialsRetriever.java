package model;

import org.mindrot.jbcrypt.BCrypt;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class HashedUserCredentialsRetriever implements UserCredentialsRetriever {

    private final static String file = "users.txt";

    @Override
    public String getHashedPassword(String username) {

        /*
        //String file = "users.txt";
        //String file = getClass().getResource("/users/users.txt").toExternalForm();
        BufferedReader br = null;
        String line = "";
        String splitBy = "#";
        String users[];
        String result = "";

        try {

            br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {
                users = line.split(splitBy);

                if (users[0].equals(username)) {
                    result = users[1];
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Result : " + result);
        return result;

        */
        String result = "";
        ArrayList<String[]> users = readUsersPassword();
        System.out.println("users.length :" + users.size());
        System.out.println(users.toString());

        for (String[] user : users) {
            if (user[0].equals(username)) {
                result = user[1];
            }
        }

        return result;
    }

    @Override
    public Boolean checkPasswordCorrect(String hashed, String password) {
        return BCrypt.checkpw(password, hashed);
    }

    @Override
    public boolean checkUserRegistered(String username) {
        Boolean result = false;
        ArrayList<String[]> users = readUsersPassword();

        for (String[] user : users) {
            if (user[0].equals(username)) {
                result = true;
            }
        }

        return result;
     /*
        BufferedReader br = null;
        String line = "";
        String splitBy = "#";
        String users[];
        String result = "";

        try {

            br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {
                users = line.split(splitBy);

                if (users[0].equals(username)) {
                    result = users[1];
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Result : " + result);
        return result;

        */



    }

    private ArrayList<String[]> readUsersPassword() {
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
