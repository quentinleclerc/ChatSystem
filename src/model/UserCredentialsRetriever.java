package model;

public interface UserCredentialsRetriever {

    String getHashedPassword(String username);

}
