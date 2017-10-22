package com.github.kazuhito_m.googlepageregister.webbrothercontrol.googleaccount;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "google")
public class AccountSelector {
    private List<Account> accounts = new ArrayList<>();
    private int nowIndex;

    public void initialize() {
        this.nowIndex = 0;
    }

    public Account next() {
        return accounts.get((nowIndex++) % accounts.size());
    }

    // for SpringBoot Config
    public List<Account> getAccounts() {
        return accounts;
    }
}
