package dev.luminaeclipse.kiro.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.luminaeclipse.kiro.Kiro;
import net.minecraft.entity.player.PlayerEntity;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PlayerURLData {
    public static final String TEAM_URL = "https://raw.githubusercontent.com/PrangellPlays/PrangellModData/refs/heads/main/LuminaData/team.json";
    public static final String PARTNER_URL = "https://raw.githubusercontent.com/PrangellPlays/PrangellModData/refs/heads/main/LuminaData/partners.json";

    public static boolean isPlayerTeam(PlayerEntity player){
        for (PlayerInfo playerInfo : fetchPlayersTeam()){
            if(player.getUuidAsString().equals(playerInfo.uuid)){
                return true;
            }
        }
        return false;
    }

    public static boolean isPlayerPartner(PlayerEntity player){
        for (PlayerInfo playerInfo : fetchPlayersPartner()){
            if(player.getUuidAsString().equals(playerInfo.uuid)){
                return true;
            }
        }
        return false;
    }

    public static List<PlayerInfo> fetchPlayersTeam() {
        List<PlayerInfo> players = new ArrayList<>();
        try {
            URL url = new URL(TEAM_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            if (connection.getResponseCode() == 200) {
                InputStreamReader reader = new InputStreamReader(connection.getInputStream());
                JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();

                // check if "players" exists and is an array
                if (jsonObject.has("players") && jsonObject.get("players").isJsonArray()) {
                    JsonArray playerArray = jsonObject.getAsJsonArray("players");

                    for (var element : playerArray) {
                        JsonObject playerObj = element.getAsJsonObject();
                        String uuid = playerObj.get("uuid").getAsString();
                        String username = playerObj.get("username").getAsString();
                        players.add(new PlayerInfo(uuid, username));
                    }
                } else {
                    Kiro.LOGGER.error("Error: 'players' field is missing or not an array!");
                }
                reader.close();
            } else {
                Kiro.LOGGER.error("HTTP Error: " + connection.getResponseCode());
            }
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return players;
    }

    public static List<PlayerInfo> fetchPlayersPartner() {
        List<PlayerInfo> players = new ArrayList<>();
        try {
            URL url = new URL(PARTNER_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            if (connection.getResponseCode() == 200) {
                InputStreamReader reader = new InputStreamReader(connection.getInputStream());
                JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();

                // check if "players" exists and is an array
                if (jsonObject.has("players") && jsonObject.get("players").isJsonArray()) {
                    JsonArray playerArray = jsonObject.getAsJsonArray("players");

                    for (var element : playerArray) {
                        JsonObject playerObj = element.getAsJsonObject();
                        String uuid = playerObj.get("uuid").getAsString();
                        String username = playerObj.get("username").getAsString();
                        players.add(new PlayerInfo(uuid, username));
                    }
                } else {
                    Kiro.LOGGER.error("Error: 'players' field is missing or not an array!");
                }
                reader.close();
            } else {
                Kiro.LOGGER.error("HTTP Error: " + connection.getResponseCode());
            }
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return players;
    }

    public static class PlayerInfo {
        public final String uuid;
        public final String username;
        public PlayerInfo(String uuid, String username) {
            this.uuid = uuid;
            this.username = username;
        }
    }

    public static void init() {
    }
}
