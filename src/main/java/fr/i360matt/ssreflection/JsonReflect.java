package fr.i360matt.ssreflection;

import de.leonhard.storage.Json;
import fr.i360matt.ssreflection.internal.Reflect;

import java.io.File;

public class JsonReflect extends Reflect {
    public JsonReflect (final File file) {
        super(file);
        this.config = new Json(file);
    }

    public Json getConfig () {
        return (Json) this.config;
    }
}
