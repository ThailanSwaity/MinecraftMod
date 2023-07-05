package net.fabricmc.example;

import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.dimension.DimensionType;

import java.util.ArrayList;

public class WaypointList {

    private ArrayList<Waypoint> waypoints = new ArrayList<>();

    public WaypointList() {

    }

    public void loadServerWaypoints(String serverAddress) {

    }

    public void saveServerWaypoints(String serverAddress) {

    }

    public ArrayList<Waypoint> getWaypoints() {
        return waypoints;
    }

    public Waypoint addWaypoint(Vec3d position, RegistryKey<DimensionType> dimension, Colour colour) {
        Waypoint waypoint = new Waypoint(position, dimension, colour);
        waypoints.add(waypoint);
        return waypoint;
    }

    public int size() {
        return waypoints.size();
    }

    public Waypoint addWaypoint(Vec3d position, String name, RegistryKey<DimensionType> dimension, Colour colour) {
        Waypoint waypoint = new Waypoint(position, name, dimension, colour);
        waypoints.add(waypoint);
        return waypoint;
    }

    public void remove(int n) {
        waypoints.remove(n);
    }

    public void addWaypoint(Waypoint waypoint) {
        waypoints.add(waypoint);
    }

    public void deleteWaypoint(Vec3d position) {
        for (int i = 0; i < waypoints.size(); i++) {
            if (waypoints.get(i).getPosition().equals(position)) waypoints.remove(i);
        }
    }

    public void clear() {
        waypoints = new ArrayList<>();
    }

    public class Waypoint {

        private Vec3d position;
        private String name;
        private Colour colour;
        private RegistryKey<DimensionType> dimension;

        public Waypoint(Vec3d position, RegistryKey<DimensionType> dimension, Colour colour) {
            this.position = position;
            this.dimension = dimension;
            this.colour = colour;
        }

        public Waypoint(Vec3d position, String name, RegistryKey<DimensionType> dimension, Colour colour) {
            this(position, dimension, colour);
            this.name = name;
        }

        public Vec3d getPosition() {
            return position;
        }

        public Colour getColour() {
            return colour;
        }

        public RegistryKey<DimensionType> getDimension() {
            return dimension;
        }

        @Override
        public String toString() {
            return name + ":" + position.toString() + ":" + colour.toString();
        }

    }

}
