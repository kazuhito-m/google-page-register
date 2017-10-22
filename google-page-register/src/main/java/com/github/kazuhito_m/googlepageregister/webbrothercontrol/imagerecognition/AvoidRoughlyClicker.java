package com.github.kazuhito_m.googlepageregister.webbrothercontrol.imagerecognition;

import org.sikuli.api.*;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.robot.desktop.DesktopMouse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Component
public class AvoidRoughlyClicker {

    private static Logger logger = LoggerFactory.getLogger(AvoidRoughlyClicker.class);

    public void searchAndClick() throws IOException {
        System.setProperty("java.awt.headless", "false");
        DesktopScreenRegion screen = new DesktopScreenRegion(1);

        BufferedImage image = resourceImageBy("nonRobotCheck.png");
        Target target = new ImageTarget(image);

        ScreenRegion region = screen.find(target);

        ScreenLocation location = region.getCenter();
        location.setX(location.getX() + 3600);  // 謎の補正。3600ズレル。

        Mouse mouse = new DesktopMouse();
        mouse.click(location);

        BufferedImage checkedImage = resourceImageBy("nonRobotChecked.png");
        Target checkedTarget = new ImageTarget(checkedImage);

        ScreenRegion checkedRegion = screen.wait(checkedTarget, 5000);

        if (checkedRegion == null || checkedRegion.getScore() < 0.9) throw new IllegalAccessError();
        logger.info("score:" + checkedRegion.getScore());
    }

    private BufferedImage resourceImageBy(String resourcePath) throws IOException {
        return ImageIO.read(this.getClass().getResourceAsStream(resourcePath));
    }
}
