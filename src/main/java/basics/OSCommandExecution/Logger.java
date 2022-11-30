package basics.OSCommandExecution;

public class Logger {

    public void info(String log, Object... params) {
        System.out.println(String.format(log.replace("{}", "%s"), params));
    }

    public void debug(String log, Object... params) {
        System.out.println(String.format(log.replace("{}", "%s"), params));
    }

    public void error(String log, Object... params) {
        System.out.println(String.format(log.replace("{}", "%s"), params));
    }

}
