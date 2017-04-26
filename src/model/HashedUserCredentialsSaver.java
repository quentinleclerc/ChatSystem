package model;

import org.mindrot.jbcrypt.BCrypt;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class HashedUserCredentialsSaver implements UserCredentialsSaver {
    @Override
    public void saveUserCredentials(String username, String password) {
        String file = "users.txt";
        // String file = this.getClass().getResource("/users.txt").toExternalForm();
        BufferedWriter bw = null;
        String splitBy = "#";
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt());

        try {
            bw = new BufferedWriter(new FileWriter(file, true));
            bw.write(username + splitBy + hashed+"\n");
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
