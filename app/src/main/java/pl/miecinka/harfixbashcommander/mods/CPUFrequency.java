package pl.miecinka.harfixbashcommander.mods;

import java.util.ArrayList;
import java.util.List;

import pl.miecinka.harfixbashcommander.utils.UnitFormatter;

/**
 * Created by emsi on 10.05.2016.
 */
public class CPUFrequency {
    public static final String[] frequencyValues = {"2100000", "2000000", "1904000", "1800000", "1704000", "1600000", "1500000", "1400000", "1300000", "1200000", "1100000", "1000000", "900000", "800000", "700000", "600000", "500000", "400000", "300000", "200000", "100000"};
    public static final String minFrequencyPath = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_min_freq";
    public static final String maxFrequencyPath = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_max_freq";

    private String rawValue;
    private String formattedValue;

    public CPUFrequency(String value)
    {
        rawValue = value;
        formattedValue = UnitFormatter.formatHertz(value);
    }

    public String getRawValue() {
        return rawValue;
    }

    public void setRawValue(String rawValue) {
        this.rawValue = rawValue;
    }

    public String getFormattedValue() {
        return formattedValue;
    }

    public void setFormattedValue(String formattedValue) {
        this.formattedValue = formattedValue;
    }

    public static ArrayList<CPUFrequency> buildFrequenciesList()
    {
        ArrayList<CPUFrequency> frequenciesList = new ArrayList<>();
        for (String value: frequencyValues)
        {
            CPUFrequency frequency = new CPUFrequency(value);
            frequenciesList.add(frequency);
        }
        return frequenciesList;
    }

    @Override
    public String toString() {
        return formattedValue;
    }
}
