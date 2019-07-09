package org.fcg.proto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ProtoRequest {
    private int utmZone;
    private char latitudeBand;
    private String gridSquare;
    private LocalDateTime date;
    private ChannelMap channelMap;

    @JsonCreator
    public ProtoRequest(
            @JsonProperty("utmZone") int utmZone,
            @JsonProperty("latitudeBand") char latitudeBand,
            @JsonProperty("gridSquare") String gridSquare,
            @JsonProperty("date") LocalDateTime date,
            @JsonProperty("channelMap") ChannelMap channelMap) {
        this.utmZone = utmZone;
        this.latitudeBand = latitudeBand;
        this.gridSquare = gridSquare;
        this.date = date;
        this.channelMap = channelMap;
    }

    public int getUtmZone() {
        return utmZone;
    }

    public char getLatitudeBand() {
        return latitudeBand;
    }

    public String getGridSquare() {
        return gridSquare;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public ChannelMap getChannelMap() {
        return channelMap;
    }

    public String getImageName(String sensorBand){
        return String.format("T%s%s%s_%s_%s.tif",
                utmZone,
                latitudeBand,
                gridSquare,
                date.format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss")),
                sensorBand);
    }

    public enum ChannelMap {
        visible,
        vegetation,
        waterVapor;
    }
}
