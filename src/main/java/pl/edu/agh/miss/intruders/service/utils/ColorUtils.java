package pl.edu.agh.miss.intruders.service.utils;

import java.awt.*;

public class ColorUtils {

    public static Color probabilityToColor(double value) {
        value = value < 1 ? value : value - 0.1;
        value *= 100;

        double r, g, b = 0;
        if (value <= 50) {    // green to yellow
            r = Math.floor((255 * (value / 50)));
            g = 255;
        } else {            // yellow to red
            r = 255;
            g = Math.floor((255 * ((50 - value % 50) / 50)));
        }

        return new Color((int) r, (int) g, (int) b);
    }

    public static String getRGBString(Color color) {
        StringBuilder sb = new StringBuilder();
        sb.append("rgb(")
                .append(color.getRed()).append(",")
                .append(color.getGreen()).append(",")
                .append(color.getBlue()).append(")");
        return sb.toString();
    }
}
