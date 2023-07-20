package net.thailan.client;

import net.thailan.client.utils.render.Colour;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.dimension.DimensionType;

import java.util.ArrayList;

public class WaypointList {

    private ArrayList<Waypoint> waypoints = new ArrayList<>();

    public WaypointList() {

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



}
