package com.cloud.marom.safetytours.objects;

public class UserLogin {
    private String Name;
    private String Duty;
    private int Rool;
    private boolean IsLogIn;

    public UserLogin(String name, String duty, int rool, boolean isLogIn) {
        Name = name;
        Duty = duty;
        Rool = rool;
        IsLogIn = isLogIn;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDuty() {
        return Duty;
    }

    public void setDuty(String duty) {
        Duty = duty;
    }

    public int getRool() {
        return Rool;
    }

    public void setRool(int rool) {
        Rool = rool;
    }

    public boolean isLogIn() {
        return IsLogIn;
    }

    public void setLogIn(boolean LogIn) {
        IsLogIn = LogIn;
    }
}
