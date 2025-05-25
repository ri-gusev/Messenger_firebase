package com.example.messenger;

public class User {

    private String name;
    private String lastName;
    private String age;
    private boolean isOnline;
    private String id;

    public User(String name, String lastName, String age, boolean isOnline, String id){
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.isOnline = isOnline;
        this.id = id;
    }

    public String getAge() {
        return age;
    }

    public String getId() {
        return id;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public String getLastName() {
        return lastName;
    }

    public String getName() {
        return name;
    }
}
