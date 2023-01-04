package org.masil.seoulyeok.events.relay.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.lang.reflect.Type;

public class Serializer {

    private static final String EMPTY = "";
    private static Serializer serializer;
    private Gson gson;

    private Serializer() {
        this.build();
    }

    public static synchronized Serializer getInstance() {
        if (Serializer.serializer == null) {
            Serializer.serializer = new Serializer();
        }
        return Serializer.serializer;
    }

    public String serialize(Object object) {
        if (object == null) {
            return EMPTY;
        }
        if (EMPTY.equals(object)) {
            return EMPTY;
        }

        if (object instanceof String) {
            return (String) object;
        }

        return this.gson().toJson(object);
    }

    public <T> T deserialize(String aSerialization, final Class<T> aType) {
        return this.gson().fromJson(aSerialization, aType);
    }

    public <T> T deserialize(String json, Type typeOfT) {
        return this.gson().fromJson(json, typeOfT);
    }

    protected Gson gson() {
        return this.gson;
    }

    private void build() {
        this.gson = new GsonBuilder()
                .serializeNulls()
                .create();
    }
}
