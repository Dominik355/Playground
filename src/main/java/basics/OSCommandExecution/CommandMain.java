package basics.OSCommandExecution;

import basics.OSCommandExecution.implementation.Command;

import java.util.concurrent.TimeUnit;

public class CommandMain {


    public static void main(String[] args) throws InterruptedException {
        Command command = new Command.CommandBuilder()
                .timeout(30)
                .timeUnit(TimeUnit.SECONDS)
                .cmdarray(new String[]{"/home/bilik/Downloads/vhuit64", "-t", "LIST"})
                .build();
        System.out.println("Command has been create,now executing");
        command.execute();
        System.out.println("RESULT:\n" + command);
        Thread.sleep(5000);
        System.out.println("after 5 seconds: ");
    }

}
