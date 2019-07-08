package org.fcg.proto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProtoImageServiceTest {
    private ProtoImageService imageService = new ProtoImageService();

    @Test
    void imageServiceChecksRequestObject() {
        assertThrows(NullPointerException.class, () -> imageService.generate(null));
    }

    //imageServiceChecksGeneratesImageForVisible
    //imageServiceChecksGeneratesImageForVegetation
    //imageServiceChecksGeneratesImageForWaterVapor
}