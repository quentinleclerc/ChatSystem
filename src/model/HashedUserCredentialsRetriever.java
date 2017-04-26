package model;

import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;
import org.mindrot.jbcrypt.BCrypt;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class HashedUserCredentialsRetriever implements UserCredentialsRetriever {

    @Override
    public String getHashedPassword(String username) {
        String file = "users.txt";
        //String file = this.getClass().getResource("/users.txt").toExternalForm();
        BufferedReader br = null;
        String line = "";
        String splitBy = "#";
        String users[] = null;
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
    }

    @Override
    public Boolean checkPasswordCorrect(String hashed, String password) {
        return BCrypt.checkpw(password, hashed);
    }
}
