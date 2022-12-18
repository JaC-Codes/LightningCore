package net.jack.spartan.utilities;

import lombok.Getter;
import net.jack.spartan.SpartanCore;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;

@Getter
public class Config {

    private final SpartanCore core;

    public Config(File file, FileConfiguration configuration, String dir, SpartanCore core){
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

    public Config(File file, FileConfiguration configuration, SpartanCore core) {
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
