package net.fabricmc.example;

import java.util.ArrayList;
import java.util.function.Consumer;

public class CommandList {

    private ArrayList<Command> commandList = new ArrayList<>();

    public void register(String command, Consumer<String[]> consumer) {
        commandList.add(new Command(command, consumer));
        ExampleMod.LOGGER.info("Registered command " + command);
    }

    public boolean process(String text) {
        if (text.charAt(0) != '.') return false;
        String commandName = text.split(" ")[0].replace(".", "");
        String[] args = text.replace("." + commandName + " ", "").split(" ");
        for (Command command : commandList) {
            if (command.match(commandName)) {
                command.trigger(args);
                return true;
            }
        }
        return false;
    }

    private class Command {

        private String command;
        private Consumer<String[]> consumer;

        public Command(String command, Consumer<String[]> consumer) {
            this.command = command;
            this.consumer = consumer;
        }

        public String getName() {
            return command;
        }

        public boolean match(String commandName) {
            return command.equalsIgnoreCase(commandName);
        }

        public void trigger(String[] args) {
            consumer.accept(args);
        }

    }

}
