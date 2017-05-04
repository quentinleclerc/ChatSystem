package model;

public interface UserCredentialsRetriever {

    String getHashedPassword(String username);

    boolean checkPasswordCorrect(String hashed, String password);

    boolean checkUserRegistered(String username);
}
