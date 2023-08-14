package net.thailan.client.utils.command;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec3d;
import net.thailan.client.AdditionManager;
import net.thailan.client.ThaiFoodClient;
import net.thailan.client.Waypoint;
import net.thailan.client.WaypointList;
import net.thailan.client.additions.ChatWatermark;
import net.thailan.client.additions.FriendList;
import net.thailan.client.additions.Surround;
import net.thailan.client.utils.DataUtil;
import net.thailan.client.utils.render.Colour;
import org.slf4j.Logger;

import java.util.ArrayList;

public class CommandHelper {

    public static final CommandList commandList = new CommandList();
    private static final MinecraftClient client = MinecraftClient.getInstance();
    private static final FriendList friendList = ThaiFoodClient.friendList;
    private static final ChatWatermark chatWatermark = ThaiFoodClient.chatWatermark;
    private static final Surround surround = ThaiFoodClient.surround;
    private static final Logger LOGGER = ThaiFoodClient.LOGGER;
    private static final WaypointList waypointList = ThaiFoodClient.waypointList;
    private static final AdditionManager additionManager = ThaiFoodClient.getInstance().additionManager;

    public static void registerCommands() {
        commandList.register("obama", (args) -> {
            client.player.sendMessage(Text.literal("Oh no no no"));
        });

        commandList.register("add", "add friends to your friends list.", (args) -> {
            for (String name : args) {
                boolean success = friendList.addFriend(name);
                client.player.sendMessage(Text.literal(success ? name + " added as a friend." : name + " is already a friend."));
            }
            DataUtil.saveFriendsList(friendList);
        });

        commandList.register("remove", "remove friends from your friends list.", (args) -> {
            for (String name : args) {
                boolean success = friendList.removeFriend(name);
                client.player.sendMessage(Text.literal(success ? "Removed " + name + " from friends list." : name + " is not in your friends list."));
            }
            DataUtil.saveFriendsList(friendList);
        });

        commandList.register("friends", "show your friends list", (args) -> {
            ArrayList<String> friendNames = friendList.getFriends();
            if (friendList.isEmpty()) {
                client.player.sendMessage(Text.literal("Friend list is empty."));
            } else {
                client.player.sendMessage(Text.literal("Friend list:"));
                for (String name : friendNames) {
                    PlayerListEntry playerListEntry = client.player.networkHandler.getPlayerListEntry(name);
                    if (playerListEntry != null) {
                        client.player.sendMessage(Text.literal("\t" + name).formatted(Formatting.GREEN));
                    } else client.player.sendMessage(Text.literal("\t" + name));
                }
            }
        });

        commandList.register("surround", "change the tickDelay of surround", (args) -> {
            if (args[0] != null) {
                try {
                    int tickDelay = Integer.parseInt(args[0]);
                    surround.setTickDelay(tickDelay);
                    client.player.sendMessage(Text.literal("Surround tickDelay set to " + tickDelay));
                } catch (NumberFormatException e) {
                    client.player.sendMessage(Text.literal("First argument must be an integer!").formatted(Formatting.RED));
                }
            }
        });

        commandList.register("watermark", "edit the chat watermark", (args) -> {
            String watermark = "";
            for (int i = 0; i < args.length; i++) {
                watermark += args[i];
                if (i != args.length) watermark += " ";
            }
            chatWatermark.set(watermark);
        });

        commandList.register("help", "display all commands", (args) -> {
            ArrayList<String> list = commandList.getCommands();
            for (String command : list) client.player.sendMessage(Text.literal(command));
        });

        commandList.register("waypoint", "add a waypoint", (args) -> {
            //client.player.sendMessage(Text.literal(args.length + "").formatted(Formatting.GREEN));
            for (int i = 0; i < args.length; i++) LOGGER.info("args " + i + ": " + args[i]);
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("add")) {
                    Waypoint waypoint = waypointList.addWaypoint(client.player.getPos(), client.world.getDimensionKey(), Colour.CORAL);
                    client.player.sendMessage(Text.literal(waypoint.toString()).formatted(Formatting.GREEN));
                }
            } else if (args.length > 1) {

                // .waypoint add(String name)
                if (args[0].equalsIgnoreCase("add")) {
                    LOGGER.info(args[1]);
                    LOGGER.info("Is it a number?" + CommandUtil.isDouble(args[1]));
                    if (args.length > 2 && !CommandUtil.isDouble(args[1])) {
                        String name = "";
                        for (int i = 1; i < args.length; i++) {
                            name += args[i];
                            if (i != args.length) name += " ";
                        }
                        Waypoint waypoint = waypointList.addWaypoint(new Vec3d(client.player.getX(), client.player.getY(), client.player.getZ()), name, client.world.getDimensionKey(), Colour.CORAL);
                        client.player.sendMessage(Text.literal(waypoint.toString()).formatted(Formatting.GREEN));
                        return;
                    }

                    // .waypoint add(double x, double y, double z, String name)
                    try {
                        double x = Double.parseDouble(args[1]);
                        double y = Double.parseDouble(args[2]);
                        double z = Double.parseDouble(args[3]);
                        if (args.length >= 4) {
                            String name = "";
                            for (int i = 4; i < args.length; i++) {
                                name += args[i];
                                if (i != args.length) name += " ";
                            }
                            Waypoint waypoint = waypointList.addWaypoint(new Vec3d(x, y, z), name, client.world.getDimensionKey(), Colour.CORAL);
                            client.player.sendMessage(Text.literal(waypoint.toString()).formatted(Formatting.GREEN));
                        } else {
                            Waypoint waypoint = waypointList.addWaypoint(new Vec3d(x, y, z), client.world.getDimensionKey(), Colour.CORAL);
                            client.player.sendMessage(Text.literal(waypoint.toString()).formatted(Formatting.GREEN));
                        }
                    } catch (NumberFormatException e) {
                        client.player.sendMessage(Text.literal("Incorrect syntax").formatted(Formatting.RED));
                    }
                } else if (args[0].equalsIgnoreCase("remove")) {

                    // .waypoint remove all
                    if (args[1].equalsIgnoreCase("all")) {
                        waypointList.clear();
                        client.player.sendMessage(Text.literal("Removed all waypoints."));
                    } else {
                        // .waypoint remove(int n)
                        try {
                            int n = Integer.parseInt(args[1]);
                            waypointList.remove(n);
                            client.player.sendMessage(Text.literal("Removed waypoint " + n));
                        } catch (NumberFormatException e) {
                            client.player.sendMessage(Text.literal("Syntax error. Second argument must be an integer.").formatted(Formatting.RED));
                        }
                    }
                }
            }
        });

        commandList.register("waypoints", "lists waypoints", (args) -> {
            if (waypointList.size() == 0) {
                client.player.sendMessage(Text.literal("There are currently no waypoints."));
                return;
            }
            for (int i = 0; i < waypointList.size(); i++) {
                client.player.sendMessage(Text.literal(i + ": " + waypointList.getWaypoints().get(i)));
            }
        });

        commandList.register("test", (args) -> {
            if (client.getNetworkHandler().getServerInfo() != null) {
                ServerInfo serverInfo = client.getNetworkHandler().getServerInfo();
                client.player.sendMessage(Text.literal(serverInfo.name).formatted(Formatting.BOLD));
                client.player.sendMessage(Text.literal(serverInfo.address));
            }
        });

        commandList.register("save", (args) -> {
            if (client.getNetworkHandler().getServerInfo() != null) {
                DataUtil.saveServerWaypoints(waypointList, client.getNetworkHandler().getServerInfo().address);
                client.player.sendMessage(Text.literal("Saved waypoints for " + client.getNetworkHandler().getServerInfo().address));
            }
        });

        commandList.register("load", (args) -> {
            if (client.getNetworkHandler().getServerInfo() != null) {
                DataUtil.loadServerWaypoints(waypointList, client.getNetworkHandler().getServerInfo().address);
                client.player.sendMessage(Text.literal("Loaded waypoints for " + client.getNetworkHandler().getServerInfo().address));
            }
        });

        commandList.register("ego", (args) -> {
            String player = "";
            for (int i = 0; i < args.length; i++) {
                player += args[i];
                if (i != args.length) player += " ";
            }
            client.getNetworkHandler().sendChatMessage("u have a huge ego " + player);
        });

        commandList.register("test2", (args) -> {
            client.inGameHud.setOverlayMessage(Text.literal("Test"), false);
        });

        commandList.register("saveHackSettings", (args) -> {
            DataUtil.saveHackSettings(additionManager.getAdditions());
            client.inGameHud.setOverlayMessage(Text.literal("Hacks saved."), false);
        });

        commandList.register("loadHackSettings", (args) -> {
            DataUtil.loadHackSettings(additionManager.getAdditions());
            client.inGameHud.setOverlayMessage(Text.literal("Hacks loaded."), false);
        });

        commandList.register("ping", (args) -> {
            if (client.getNetworkHandler().getServerInfo() != null) {
                client.player.sendMessage(Text.literal("Ping: " + client.getNetworkHandler().getServerInfo().ping).formatted(Formatting.GOLD));
            }
        });

        commandList.register("keyboard", (args) -> {
            client.player.sendMessage(Text.literal("This keyboard is awesome. I love the Varmilo Bluebell, it's so nice and silent, but also feels really good! I could type on this forever... I don't want to stop tbh"));
        });
    }

    public static boolean process(String text) {
        return commandList.process(text);
    }

}
