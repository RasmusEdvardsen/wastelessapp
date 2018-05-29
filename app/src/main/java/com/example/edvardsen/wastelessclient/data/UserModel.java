package com.example.edvardsen.wastelessclient.data;

/**
 * Created by Epico-u-01 on 4/8/2018.
 */

public class UserModel {

    private static UserModel instance = null;
    private static String firstName = "";
    private static String lastName = "";
    private static String email = "";
    private static String password = "";
    private static int userID = -1;

    private UserModel (){}

    public static UserModel getInstance(){
        if (instance == null){
            instance = new UserModel();
        }
        return instance;
    }

    public static String getFirstName() {
        return firstName;
    }

    public static String getLastName() {
        return lastName;
    }

    public static String getEmail() {
        return email;
    }

    public static int getUserID(){return userID;}

    public static void setUserID(int userID) {
        UserModel.userID = userID;
    }

    public static String getPassword() {
        return password;
    }

    public static void setFirstName(String firstName) {
        UserModel.firstName = firstName;
    }

    public static void setLastName(String lastName) {
        UserModel.lastName = lastName;
    }

    public static void setEmail(String email) {
        UserModel.email = email;
    }

    public static void setPassword(String password) {
        UserModel.password = password;
    }
}

