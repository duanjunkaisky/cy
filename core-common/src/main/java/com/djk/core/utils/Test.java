package com.djk.core.utils;

import java.awt.Desktop;
import java.net.URI;

public class Test {
    public static void main(String[] args) {
        // 检查Desktop类是否支持打开Web浏览器
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                // 使用默认浏览器打开谷歌主页
                Desktop.getDesktop().browse(new URI("https://synconhub.coscoshipping.com"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Desktop or BROWSE action not supported.");
        }
    }
}
