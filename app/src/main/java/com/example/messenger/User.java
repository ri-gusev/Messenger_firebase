package com.example.messenger;

public class User {

    private String name;
    private String lastName;
    private String age;
    private boolean online;
    private String id;

    public User(String name, String lastName, String age, boolean isOnline, String id){
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.online = isOnline;
        this.id = id;
    }

    public User(){}

    public String getAge() {
        return age;
    }

    public String getId() {
        return id;
    }

    public boolean isOnline() {
        return online;
    }

    public String getLastName() {
        return lastName;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "User{" +
                "age='" + age + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", isOnline=" + online +
                ", id='" + id + '\'' +
                '}';
    }
}
