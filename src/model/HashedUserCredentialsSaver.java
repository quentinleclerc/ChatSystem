package model;

import org.mindrot.jbcrypt.BCrypt;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;


public class HashedUserCredentialsSaver implements UserCredentialsSaver {

    @Override
    public void saveUserCredentials(String username, String password) {

        URL resource = getClass().getResource("/users/users.txt");
        String fileName = resource.getFile();
        File file = new File(fileName);

        BufferedWriter bw = null;
        String splitBy = "#";
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt());

        try {
            bw = new BufferedWriter(new FileWriter(file, true));
            bw.write(username + splitBy + hashed +"\n");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
