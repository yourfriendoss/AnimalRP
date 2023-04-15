package lv.pi.animalrp.util;

import com.google.gson.JsonObject;

public class Cooldown {
    public long timeCreated;
    public Integer length;
    public String type;

    public long getTime() {
        return this.timeCreated - (System.currentTimeMillis() - this.length);
    }
    
    public boolean isExpired() {
        return this.getTime() <= 0;
    }

    public JsonObject toJson() {
        JsonObject obj = new JsonObject();
        obj.addProperty("timeCreated", this.timeCreated);
        obj.addProperty("length", this.length);
        obj.addProperty("type", this.type);

        return obj;
    }

    public static Cooldown fromJson(JsonObject obj) {
        Cooldown cldn = new Cooldown();
        cldn.timeCreated = obj.get("timeCreated").getAsLong();
        cldn.length = obj.get("length").getAsInt();
        cldn.type = obj.get("type").getAsString();

        return cldn;
    }
}
