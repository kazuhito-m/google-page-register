package com.github.kazuhito_m.googlepageregister.webbrothercontrol.imagerecognition;

import java.io.IOException;

public class AvoidRoughlyClickerTest {

    //    @Test
    public void ブラウザが表示された状態でSikliによるクリックが出来るか() throws IOException {
        AvoidRoughlyClicker sut = new AvoidRoughlyClicker();
        sut.searchAndClick();
    }
}
