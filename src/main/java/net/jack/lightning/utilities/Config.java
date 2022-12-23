package net.jack.lightning.utilities;

import lombok.Getter;
import net.jack.lightning.LightningCore;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;

@Getter
public class Config {

    private final LightningCore core;

    public Config(File file, FileConfiguration configuration, String dir, LightningCore core){
        this.core = core;
        if (!file.exists()) {
            file.getParentFile().mkdir();
            this.core.saveResource(dir, false);
        }

        try {
            configuration.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    public Config(File file, FileConfiguration configuration, LightningCore core) {
        this.core = core;
        try {
            configuration.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            configuration.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

}
