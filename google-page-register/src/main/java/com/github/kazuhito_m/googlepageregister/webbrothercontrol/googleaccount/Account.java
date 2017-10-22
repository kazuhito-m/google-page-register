package com.github.kazuhito_m.googlepageregister.webbrothercontrol.googleaccount;

public class Account {

    private String gmail;
    private String password;

    public String gmail() {
        return gmail;
    }

    public String password() {
        return password;
    }

    // for SpringBoot Config.

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
