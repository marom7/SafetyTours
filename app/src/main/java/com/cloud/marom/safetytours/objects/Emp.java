package com.cloud.marom.safetytours.objects;

public class Emp {
    String ImeiIsOk;
    String Enable;
    String Emp_Num;
    String First_Name;
    String Last_Name;

    public String getImeiIsOk() {
        return ImeiIsOk;
    }

    public void setImeiIsOk(String imeiIsOk) {
        ImeiIsOk = imeiIsOk;
    }

    public String getEnable() {
        return Enable;
    }

    public void setEnable(String enable) {
        Enable = enable;
    }

    public String getEmp_Num() {
        return Emp_Num;
    }

    public void setEmp_Num(String emp_Num) {
        Emp_Num = emp_Num;
    }

    public String getFirst_Name() {
        return First_Name;
    }

    public void setFirst_Name(String first_Name) {
        First_Name = first_Name;
    }

    public String getLast_Name() {
        return Last_Name;
    }

    public void setLast_Name(String last_Name) {
        Last_Name = last_Name;
    }

    public String getPhone_Number() {
        return Phone_Number;
    }

    public void setPhone_Number(String phone_Number) {
        Phone_Number = phone_Number;
    }

    public String getDuty() {
        return Duty;
    }

    public void setDuty(String duty) {
        Duty = duty;
    }

    public String getROOL() {
        return ROOL;
    }

    public void setROOL(String ROOL) {
        this.ROOL = ROOL;
    }

    String Phone_Number;
    String Duty;
    String ROOL;
    @Override
    public String toString() {
        return getFirst_Name()+" "+getLast_Name();
    }
}
