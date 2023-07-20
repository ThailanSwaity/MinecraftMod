package net.thailan.client.utils.command;

import net.thailan.client.ThaiFoodClient;

import java.util.ArrayList;
import java.util.function.Consumer;

public class CommandList {

    private ArrayList<Command> commandList = new ArrayList<>();

    public void register(String command, Consumer<String[]> consumer) {
        commandList.add(new Command(command, consumer));
        ThaiFoodClient.LOGGER.info("Registered command " + command);
    }

    public void register(String command, String description, Consumer<String[]> consumer) {
        commandList.add(new Command(command, description, consumer));
        ThaiFoodClient.LOGGER.info("Registered command " + command);
    }

    public boolean process(String text) {
        if (text.charAt(0) != '.') return false;
        ThaiFoodClient.getInstance().client.inGameHud.getChatHud().addToMessageHistory(text);
        String commandName = text.split(" ")[0].replace(".", "");
        String[] args = text.replace("." + commandName + " ", "").replace("." + commandName, "").split(" ");
        for (Command command : commandList) {
            if (command.match(commandName)) {
                command.trigger(args);
                return true;
            }
        }
        return false;
    }

    public ArrayList<String> getCommands() {
        ArrayList<String> list = new ArrayList<>();
        for (Command command : commandList) {
            list.add(command.toString());
        }
        return list;
    }

    private class Command {

        private String command;
        private String description;
        private Consumer<String[]> consumer;

        public Command(String command, Consumer<String[]> consumer) {
            this.command = command;
            this.consumer = consumer;
        }

        public Command(String command, String description, Consumer<String[]> consumer) {
            this.command = command;
            this.description = description;
            this.consumer = consumer;
        }

        public String getName() {
            return command;
        }
        public String getDescription() {
            return description;
        }

        public boolean match(String commandName) {
            return command.equalsIgnoreCase(commandName);
        }

        public void trigger(String[] args) {
            consumer.accept(args);
        }

        @Override
        public String toString() {
            return command + " | " + description;
        }

    }

}
