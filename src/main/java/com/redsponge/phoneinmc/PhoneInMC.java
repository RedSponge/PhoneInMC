package com.redsponge.phoneinmc;

import org.bukkit.plugin.java.JavaPlugin;

public final class PhoneInMC extends JavaPlugin {

    public static PhoneInMC INSTANCE;
    private ImageListener listener;
    public ImageBuilder builder;

    @Override
    public void onEnable() {
        INSTANCE = this;
        builder = new ImageBuilder();
        listener = new ImageListener(10000);
    }

    @Override
    public void onDisable() {
        listener.close();
    }
}
