package personio.example.demo.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Utils {

    private static final Logger LOGGER = LoggerFactory.getLogger(Utils.class);


    public static void printStackTrace(Exception e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        String exceptionAsString = sw.toString();
        LOGGER.error("error : {}",exceptionAsString);
    }
}
