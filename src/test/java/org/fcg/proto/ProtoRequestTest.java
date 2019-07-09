package org.fcg.proto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProtoRequestTest {

    @Test
    public void formatsImageName() {
        //given
        ProtoRequest protoRequest = new ProtoRequest(
                "33",
                "U",
                "UP",
                "2018-08-04T10:00:31",
                "visible");

        //when
        String imageName = protoRequest.getImageName("B02");

        //then
        assertEquals("T33UUP_20180804T100031_B02.tif", imageName);
    }

}