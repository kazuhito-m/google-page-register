package com.github.kazuhito_m.googlepageregister.webbrothercontrol.googleaccount;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountSelectorTest {

    @Autowired
    private AccountSelector sut;

    @Before
    public void setUp() {
        sut.initialize();
    }

    @Test
    public void コンフィグファイルがら設定が拾えてるか() {
        assertThat(sut.getAccounts().size(), is(3));
    }

    @Test
    public void googleアカウントが個数分順番に出せるか() {
        assertThat(sut.next().gmail(), is("one@gmail.com"));
        assertThat(sut.next().gmail(), is("two@gmail.com"));
        assertThat(sut.next().gmail(), is("three@gmail.com"));
    }

    @Test
    public void googleアカウントが個数以降回るとループして出てくるか() {
        sut.next();
        sut.next();
        assertThat(sut.next().gmail(), is("three@gmail.com"));
        assertThat(sut.next().gmail(), is("one@gmail.com"));
    }

}
