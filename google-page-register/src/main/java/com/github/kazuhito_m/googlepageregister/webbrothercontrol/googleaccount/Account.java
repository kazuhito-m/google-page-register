package com.github.kazuhito_m.googlepageregister.webbrothercontrol.googleaccount;

public class Account {

    private String gmail;
    private String password;

    String gmail() {
        return gmail;
    }

    String password() {
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
