package me.padej.entitynbtviewer.config;

import com.google.gson.*;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ConfigManager {

    private static final File configFile = new File("config/nbt_view_options.json");

    // --- NBTView --- //
    public static boolean motion = true;
    public static boolean movementDirection = false;
    public static boolean hitboxHeight = false;
    public static boolean hitboxWidth = false;
    public static boolean eyeHeight = false;
    public static boolean eyeY = false;
    public static boolean yaw = true;
    public static boolean pitch = true;
    public static boolean headYaw = false;
    public static boolean bodyYaw = false;
    public static boolean posX = true;
    public static boolean posY = true;
    public static boolean posZ = true;
    public static boolean dimensions = false;
    public static boolean armorItems = false;
    public static boolean handItems = false;
    public static boolean itemEquipped = false;
    public static boolean entityName = false;
    public static boolean name = true;
    public static boolean fireTicks = true;
    public static boolean frozenTicks = true;
    public static boolean air = true;
    public static boolean maxAir = true;
    public static boolean health = true;
    public static boolean maxHealth = true;
    public static boolean entityId = true;
    public static boolean teamInfo = true;
    public static boolean leashPos = true;
    public static boolean uuid = true;
    public static boolean itemEntityInfo = false;
    public static boolean projectileEntityInfo = false;
    public static boolean helpInfo = true;
    public static boolean luck = false;
    public static boolean inventory = false;
    public static boolean hurtTimeInfo = true;
    public static boolean headRotation = true;
    public static boolean bodyRotation = true;
    public static boolean leftArmRotation = true;
    public static boolean rightArmRotation = true;
    public static boolean leftLegRotation = true;
    public static boolean rightLegRotation = true;
    public static boolean NoGravity = true;
    public static boolean Invulnerable = true;
    public static boolean Collidable = false;
//    public static boolean Crawling = true;
    public static boolean Glowing = false;
    public static boolean ImmuneToExplosion = false;
    public static boolean CustomNameVisible = true;
    public static boolean FireImmune = false;
    public static boolean InLava = false;
    public static boolean InsideWall = false;
//    public static boolean InSneakingPose = true;
//    public static boolean InSwimmingPose = true;
    public static boolean Invisible = true;
    public static boolean OnFire = false;
    public static boolean OnGround = false;
    public static boolean OnRail = false;
    public static boolean Pushable = false;
    public static boolean PushedByFluids = false;
    public static boolean Silent = true;
    public static boolean Spectator = false;
//    public static boolean Sprinting = true;
    public static boolean TouchingWater = false;
    public static boolean TouchingWaterOrRain = false;

    private static final Map<String, Field> configFields = new HashMap<>();

    static {
        for (Field field : ConfigManager.class.getFields()) {
            if (field.getType().equals(boolean.class)) {
                configFields.put(field.getName(), field);
            }
        }
    }

    public static void loadConfig() {
        if (!configFile.exists()) {
            createConfigFile();
            return;
        }

        try {
            String json = new String(Files.readAllBytes(Paths.get(configFile.toURI())));
            if (json.isEmpty()) {
                createConfigFile();
                return;
            }

            JsonObject configObject = JsonParser.parseString(json).getAsJsonObject();
            JsonObject options = configObject.getAsJsonObject("NBTView");

            if (options != null) {
                for (Map.Entry<String, JsonElement> entry : options.entrySet()) {
                    String key = entry.getKey();
                    JsonElement value = entry.getValue();

                    Field field = configFields.get(key);
                    if (field != null && value.isJsonPrimitive() && value.getAsJsonPrimitive().isBoolean()) {
                        field.setBoolean(null, value.getAsBoolean());
                    }
                }
            }
        } catch (IOException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void createConfigFile() {
        if (!configFile.exists()) {
            updateConfig();
        }
    }

    public static void updateConfig() {
        JsonObject options = new JsonObject();
        for (Map.Entry<String, Field> entry : configFields.entrySet()) {
            try {
                options.addProperty(entry.getKey(), entry.getValue().getBoolean(null));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        JsonObject configObject = new JsonObject();
        configObject.add("NBTView", options);

        // Создаем Gson объект с настройками форматирования
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonString = gson.toJson(configObject);

        try (FileWriter writer = new FileWriter(configFile)) {
            writer.write(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
