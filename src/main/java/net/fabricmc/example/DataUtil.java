package net.fabricmc.example;

import net.fabricmc.example.additions.FriendList;
import net.fabricmc.example.additions.Waypoints;
import net.minecraft.util.math.Vec3d;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class DataUtil {

    public static boolean saveFriendsList(FriendList friendList) {
        String data = "";
        for (String friendName : friendList.getFriends()) data += friendName + "\n";
        return saveData("friendslist.txt", "thaifoodclient", data);
    }

    public static ArrayList<String> loadFriendsList() {
        ArrayList<String> friendList = new ArrayList<>();

        String data = loadData("thaifoodclient/friendslist.txt");
        if (data != null) {
            for (String name : data.split("\n")) {
                friendList.add(name);
            }
        }
        return friendList;
    }

    public static boolean saveServerWaypoints(WaypointList waypointList, String serverAddress) {
        String data = "";
        for (WaypointList.Waypoint waypoint : waypointList.getWaypoints()) {
            data += waypoint.toString() + "\n";
        }
        ExampleMod.LOGGER.info(data);
        return saveData(serverAddress.replace(".", "_").replace(":", "_") + ".txt", "thaifoodclient/waypoints", data);
    }

    public static void loadServerWaypoints(WaypointList waypointList, String serverAddress) {
        String filename = serverAddress.replace(".", "_").replace(":", "_");
        String data = loadData("thaifoodclient/waypoints/" + filename + ".txt");

        if (data == null) return;
        String[] lines = data.split("\n");

        String wName;
        String wCoords;
        String wColour;

        Vec3d pos;
        Colour colour;

        for (String line : lines) {
            String[] split = line.split(":");
            wName = split[0].trim();
            wCoords = split[1].replace("(", "").replace(")", "").replace(" ", "");
            wColour = split[2].replace(" ", "");

            try {
                String[] temp = wCoords.split(",");
                double x = Double.parseDouble(temp[0]);
                double y = Double.parseDouble(temp[1]);
                double z = Double.parseDouble(temp[2]);

                pos = new Vec3d(x, y, z);

                temp = wColour.split(",");
                float r = Float.parseFloat(temp[0]);
                float g = Float.parseFloat(temp[1]);
                float b = Float.parseFloat(temp[2]);
                colour = new Colour(r, g, b);

                waypointList.addWaypoint(pos, wName, null, colour);
            } catch (NumberFormatException e) {
                System.out.println("An error occurred while parsing the waypoint");
                e.printStackTrace();
            }
        }
    }

    private static boolean saveData(String filename, String directory, String data) {
        String filepath = directory + "/" + filename;
        File file = new File(filepath);
        File dir = new File(directory);

        if (!dir.exists()) dir.mkdirs();

        try {
            if (!file.exists()) file.createNewFile();
        } catch (IOException e) {
            System.out.println("Error creating the file at " + filepath);
            e.printStackTrace();
        }

        try {
            FileWriter writer = new FileWriter(filepath);
            writer.write(data);
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred while writing to file at " + filepath);
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static String loadData(String filepath) {
        String data = "";
        try {
            File file = new File(filepath);
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                data += reader.nextLine() + "\n";
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Could not find file at " + filepath);
            e.printStackTrace();
            return null;
        }
        return data;
    }



}
