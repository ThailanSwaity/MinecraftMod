package net.thailan.client.utils;

import com.google.gson.*;
import net.thailan.client.ThaiFoodClient;
import net.thailan.client.Waypoint;
import net.thailan.client.WaypointList;
import net.thailan.client.additions.FriendList;
import net.thailan.client.additions.Hack;

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
        for (Waypoint waypoint : waypointList.getWaypoints()) {
            data += waypoint.toString() + "\n";
        }
        ThaiFoodClient.LOGGER.info(data);
        return saveData(serverAddress.replace(".", "_").replace(":", "_") + ".txt", "thaifoodclient/waypoints", data);
    }

    public static void loadServerWaypoints(WaypointList waypointList, String serverAddress) {
        String filename = serverAddress.replace(".", "_").replace(":", "_");
        String data = loadData("thaifoodclient/waypoints/" + filename + ".txt");

        if (data == null) return;
        String[] lines = data.split("\n");

        for (String line : lines) {
            Waypoint waypoint = new Waypoint(line);
            if (waypoint.getPosition() != null) waypointList.addWaypoint(waypoint);
        }
    }

    public static void saveHackSettings(ArrayList<Hack> hacks) {
        JsonArray jsonArray = new JsonArray();

        for (int i = 0; i < hacks.size(); i++) {
            jsonArray.add(hacks.get(i).toJSON());
        }

        saveData("hackSettings.txt", "thaifoodclient", jsonArray.toString());
    }

    public static void loadHackSettings(ArrayList<Hack> hacks) {
        String data = loadData("thaifoodclient/hackSettings.txt");
        if (data == null) {
            throw new NullPointerException("Data could not be read.");
        }
        JsonElement json = JsonParser.parseString(data);

        ThaiFoodClient.LOGGER.info(json.toString());

        if (json.isJsonArray()) {
            JsonArray jsonHackArray = json.getAsJsonArray();

            for (int i = 0; i < hacks.size(); i++) {
                loadHack(hacks.get(i), jsonHackArray);
            }

        }

    }

    private static void loadHack(Hack hack, JsonArray jsonHackArray) {
        for (int i = 0; i < jsonHackArray.size(); i++) {
            JsonObject obj = jsonHackArray.get(i).getAsJsonObject();
            String hackName = obj.get("hack").getAsJsonObject().get("name").getAsString();
            if (hackName.equalsIgnoreCase(hack.getName())) {
                hack.fromJSON(obj);
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
