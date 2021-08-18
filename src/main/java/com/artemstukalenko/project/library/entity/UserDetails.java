package com.artemstukalenko.project.library.entity;

public class UserDetails {

    private String username;
    private String userFirstName;
    private String userLastName;
    private String userEmail;
    private String userPhoneNumber;
    private String userAddress;
    private int userPenalty;
    private String authorityString;
    private transient boolean hasPenalty;

    public UserDetails() {}

    public UserDetails(User user) {
        this.username = user.getUsername();
        this.userPenalty = 0;
    }

    public UserDetails(String userFirstName, String userLastName, String userEmail, String userPhoneNumber, String userAddress) {
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userEmail = userEmail;
        this.userPhoneNumber = userPhoneNumber;
        this.userAddress = userAddress;
        this.userPenalty = 0;
    }

    public UserDetails(String username, String userFirstName, String userLastName,
                       String userEmail, String userPhoneNumber, String userAddress,
                       int userPenalty, String authorityString) {
        this.username = username;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userEmail = userEmail;
        this.userPhoneNumber = userPhoneNumber;
        this.userAddress = userAddress;
        this.userPenalty = userPenalty;
        this.authorityString = authorityString;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public int getUserPenalty() {
        return userPenalty;
    }

    public void setPenalty(int penalty) {
        this.userPenalty += penalty;
    }

    public void setAuthorityString(String authorityStringFromDB) {
        switch (authorityStringFromDB) {
            case "ROLE_ADMIN":
                this.authorityString = "ADMIN";
                break;
            case "ROLE_LIBRARIAN":
                this.authorityString = "LIBRARIAN";
                break;
            default:
                this.authorityString = "";
                break;
        }
    }

    public String getAuthorityString() {
        switch (authorityString) {
            case "ROLE_ADMIN":
                return  "ADMIN";
            case "ROLE_LIBRARIAN":
                return  "LIBRARIAN";
            default:
                return  "";
        }
    }

    public boolean isHasPenalty() {
        return this.userPenalty > 0;
    }

    @Override
    public String toString() {
        return "UserDetails{" +
                "username='" + username + '\'' +
                ", userFirstName='" + userFirstName + '\'' +
                ", userLastName='" + userLastName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userPhoneNumber='" + userPhoneNumber + '\'' +
                ", userAddress='" + userAddress + '\'' +
                ", userPenalty=" + userPenalty +
                '}';
    }
}
