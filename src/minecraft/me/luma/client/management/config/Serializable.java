package me.luma.client.management.config;

import com.google.gson.JsonObject;

public interface Serializable {
	JsonObject save();

    void load(JsonObject object);

}
