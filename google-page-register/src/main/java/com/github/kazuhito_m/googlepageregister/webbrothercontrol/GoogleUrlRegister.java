package com.github.kazuhito_m.googlepageregister.webbrothercontrol;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;

@Component
public class GoogleUrlRegister {

    private static Logger logger = LoggerFactory.getLogger(GoogleUrlRegister.class);

    public void register(List<URL> links) {
        logger.info("Googleページ登録も通っているよ");
        // TODO 以下、仮実装を失くす。
        links.stream().forEach(System.out::println);
    }
}
