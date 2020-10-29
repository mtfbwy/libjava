package pd.log;

import static pd.log.LogManager.Util.getHostname;
import static pd.log.LogManager.Util.writeLine;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import pd.time.TimeUtil;

public class ConsoleLogger implements ILogger {

    public static final ConsoleLogger defaultInstance = new ConsoleLogger();

    private static final Writer outWriter = new PrintWriter(System.out, true);
    private static final Writer errWriter = new PrintWriter(System.err, true);

    @Override
    public void flush() {
        try {
            errWriter.flush();
            outWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void log(long timestamp, LogLevel level, String message) {
        Writer w = level.isPriorTo(LogLevel.INFO) ? errWriter : outWriter;
        try {
            writeLine(w, ",", TimeUtil.now(), getHostname(), level, message);
            w.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
