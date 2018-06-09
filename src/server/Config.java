package server;

import providers.versionProviders.IVersionProvider;

import java.util.HashMap;

public class Config {
    private static volatile Config instance;

    public static Config getInstance() {
        if (instance == null)
            synchronized (Config.class) {
                if (instance == null)
                    instance = new Config();
            }
        return instance;
    }

    public HashMap<Integer, String> repo = new HashMap<>();

    public HashMap<Integer, String> currentRepo = new HashMap<>();

    public HashMap<Integer, IVersionProvider> versionCounter = new HashMap<>();
}