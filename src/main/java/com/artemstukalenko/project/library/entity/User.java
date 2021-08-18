package com.artemstukalenko.project.library.entity;

import java.util.List;
import java.util.Objects;

public class User {

//    private UserDetails userDetails;
//
//
//    private List<Subscription> subscriptionList;

    private String username;

    private String password;

    private int enabled;



    public User() {
        this.enabled = 1;

    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.enabled = 1;
    }

    public User(String username, String password, int enabled) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public String getEnabled() {
//        return enabled == 0 ? FirstView.userBlocked : FirstView.userNotBlocked;
//    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

//    public UserDetails getUserDetails() {
//        return userDetails;
//    }

//    public void setUserDetails(UserDetails userDetails) {
//        this.userDetails = userDetails;
//    }

//    public List<Subscription> getSubscriptionList() {
//        return subscriptionList;
//    }

//    public void addSubscription(Subscription subscriptionToAdd) {
//        this.subscriptionList.add(subscriptionToAdd);
//    }

//    public String getAuthorityString() {
//        return this.userDetails.getAuthorityString();
//    }

//    public void setAuthorityString(String authorityString) {
//        this.getUserDetails().setAuthorityString(authorityString);
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return enabled == user.enabled && username.equals(user.username) && password.equals(user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, enabled);
    }
}
