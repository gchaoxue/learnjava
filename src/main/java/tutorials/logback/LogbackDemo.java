package tutorials.logback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogbackDemo {
    public static void main(String[] args) {
        Logger LOG = LoggerFactory.getLogger(LogbackDemo.class);
        LOG.debug("debug");
        LOG.info("info");
        LOG.warn("warn");
        LOG.error("error");
    }
}
