package fr.i360matt.ssreflection;

import de.leonhard.storage.Yaml;
import fr.i360matt.ssreflection.internal.Reflect;

import java.io.File;

public class YamlReflect extends Reflect {
    public YamlReflect (final File file) {
        super(file);
        this.config = new Yaml(file);
    }

    public Yaml getConfig () {
        return (Yaml) this.config;
    }
}
