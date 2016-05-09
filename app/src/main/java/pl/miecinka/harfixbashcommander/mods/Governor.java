package pl.miecinka.harfixbashcommander.mods;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by emsi on 07.05.2016.
 */
public class Governor {

    public static String path = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_governor";
    public static String[] names  = {"darkness", "zzmoove", "intelliactive", "smartassV2", "lulzactiveq", "adaptive", "interactive", "conservative", "ondemand", "userspace", "powersave", "pegasusq", "performance"};
}
