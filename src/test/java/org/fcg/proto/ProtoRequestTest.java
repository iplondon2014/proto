package org.fcg.proto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.fcg.proto.ProtoRequest.ChannelMap.visible;
import static org.junit.jupiter.api.Assertions.*;

class ProtoRequestTest {

    @Test
    public void formatsImageName() {
        //given
        ProtoRequest req = new ProtoRequest(
                33,
                'U',
                "UP",
                LocalDateTime.parse("2018-08-04T10:00:31"),
                visible);

        //when
        String imageName = req.getImageName("B02");

        //then
        assertEquals("T33UUP_20180804T100031_B02.tif", imageName);
    }

}