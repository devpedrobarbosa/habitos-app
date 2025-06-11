package br.uva.habitos.util;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class TimeUtil {

    public static String writeInterval(long interval) {
        interval = Math.round((float) interval / 1000) * 1000L;
        long hours = TimeUnit.MILLISECONDS.toHours(interval),
                minutes = TimeUnit.MILLISECONDS.toMinutes(interval -= TimeUnit.HOURS.toMillis(hours)),
                seconds = TimeUnit.MILLISECONDS.toSeconds(interval - TimeUnit.MINUTES.toMillis(minutes));
        return (interval < 0 ? "-" : "") + String.format(Locale.getDefault(), "%02d:%02d:%02d", Math.abs(hours), Math.abs(minutes), Math.abs(seconds));
    }
}
