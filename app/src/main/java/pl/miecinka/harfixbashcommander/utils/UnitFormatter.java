package pl.miecinka.harfixbashcommander.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by emsi on 10.05.2016.
 */
public class UnitFormatter {

    public static double roundValue(double rawValue, int places)
    {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(rawValue);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static String formatHertz(String rawValue)
    {
        String formattedValue;
        double intValue = Integer.parseInt(rawValue);
        if(intValue/1000000 > 1)
        {
            double dividedValue = roundValue(intValue / 1000000, 3);
            formattedValue = Double.toString(dividedValue) + "GHz";
        }
        else
        {
            double dividedValue = intValue/1000;
            formattedValue = Double.toString(dividedValue) + "MHz";
        }
        return formattedValue;
    }
}
