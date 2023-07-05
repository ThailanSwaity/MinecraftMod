package net.fabricmc.example;

import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.dimension.DimensionTypes;

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

    public Waypoint(Vec3d position, String name, int dimension, Colour colour) {
        this(position, name, fromDimensionInt(dimension), colour);
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
        return name + ":" + position.toString() + ":" + colour.toString() + ":" + getDimensionInt();
    }
    public Waypoint(String data) {
        String[] split = data.split(":");
        String wName = split[0].trim();
        String wCoords = split[1].replace("(", "").replace(")", "").replace(" ", "");
        String wColour = split[2].replace(" ", "");
        String wDimension = split[3].trim();

        try {
            String[] temp = wCoords.split(",");
            double x = Double.parseDouble(temp[0]);
            double y = Double.parseDouble(temp[1]);
            double z = Double.parseDouble(temp[2]);

            Vec3d pos = new Vec3d(x, y, z);

            temp = wColour.split(",");
            float r = Float.parseFloat(temp[0]);
            float g = Float.parseFloat(temp[1]);
            float b = Float.parseFloat(temp[2]);
            Colour colour = new Colour(r, g, b);

            int d = Integer.parseInt(wDimension);

            this.position = pos;
            this.name = wName;
            this.dimension = fromDimensionInt(d);
            this.colour = colour;

        } catch (NumberFormatException e) {
            System.out.println("An error occurred while parsing the waypoint");
            e.printStackTrace();

            this.position = null;
        }
    }

    public int getDimensionInt() {
        if (dimension == DimensionTypes.OVERWORLD) return 0;
        else if (dimension == DimensionTypes.THE_END) return 1;
        else if (dimension == DimensionTypes.THE_NETHER) return -1;
        return -2;
    }

    private static RegistryKey<DimensionType> fromDimensionInt(int dimensionInt) {
        if (dimensionInt == 0) return DimensionTypes.OVERWORLD;
        else if (dimensionInt == 1) return DimensionTypes.THE_END;
        else if (dimensionInt == -1) return DimensionTypes.THE_NETHER;
        else return null;
    }

}
