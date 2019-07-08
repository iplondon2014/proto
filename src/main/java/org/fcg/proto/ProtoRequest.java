package org.fcg.proto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProtoRequest {
    private String utmZone;
    private String latitudeBand;
    private String gridSquare;
    private String date;
    private String channelMap;

    @JsonCreator
    public ProtoRequest(
            @JsonProperty("utmZone") String utmZone,
            @JsonProperty("latitudeBand") String latitudeBand,
            @JsonProperty("gridSquare") String gridSquare,
            @JsonProperty("date") String date,
            @JsonProperty("channelMap") String channelMap) {
        this.utmZone = utmZone;
        this.latitudeBand = latitudeBand;
        this.gridSquare = gridSquare;
        this.date = date;
        this.channelMap = channelMap;
    }

    public String getUtmZone() {
        return utmZone;
    }

    public String getLatitudeBand() {
        return latitudeBand;
    }

    public String getGridSquare() {
        return gridSquare;
    }

    public String getDate() {
        return date;
    }

    public String getChannelMap() {
        return channelMap;
    }
}
