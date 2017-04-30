package model;

public interface UserCredentialsRetriever {

    String getHashedPassword(String username);

    Boolean checkPasswordCorrect(String hashed, String password);

    boolean checkUserRegistered(String username);
}
