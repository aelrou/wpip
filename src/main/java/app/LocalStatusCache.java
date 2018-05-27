package app;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalStatusCache {
    public String IP_CACHE;
    public LocalDateTime LAST_UPDATE;

    public LocalStatusCache (boolean useDefault) {
        if(useDefault) {
            this.IP_CACHE = "0.0.0.0";
            this.LAST_UPDATE = LocalDateTime.parse("2001-01-01T01:01:01", DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        }
    }
}
