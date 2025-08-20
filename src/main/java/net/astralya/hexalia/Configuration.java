package net.astralya.hexalia;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Configuration {

    public int brewEffectDuration = 4800;
    public int brewAmplifierBonus = 0;

    private static final Path CONFIG_PATH = FabricLoader.getInstance()
            .getConfigDir().resolve("hexalia-common.json");

    private static Configuration INSTANCE;

    public static Configuration get() {
        if (INSTANCE == null) load();
        return INSTANCE;
    }

    public static void load() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            if (Files.exists(CONFIG_PATH)) {
                INSTANCE = gson.fromJson(Files.readString(CONFIG_PATH), Configuration.class);
            } else {
                INSTANCE = new Configuration();
                save();
            }
        } catch (IOException e) {
            e.printStackTrace();
            INSTANCE = new Configuration();
        }
    }

    public static void save() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            Files.writeString(CONFIG_PATH, gson.toJson(INSTANCE));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

