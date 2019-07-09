package org.fcg.proto;

import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;
import static org.junit.jupiter.api.Assertions.*;

class SimpleProtoImageGeneratorTest {

    private ProtoImageGenerator imageGenerator = new SimpleProtoImageGenerator();

    @Test
    void imageGeneratorGeneratesRGBImage() {
        //given
        BufferedImage image = new BufferedImage(5, 5, TYPE_INT_RGB);

        //when
        BufferedImage generate = imageGenerator.generate(image, image, image);

        //then
        assertNotNull(generate);
    }

    @Test
    void imageGeneratorThrowsExceptionIfAnyImageIsNull() {
        //given
        BufferedImage image = new BufferedImage(5, 5, TYPE_INT_RGB);

        //when
        assertThrows(ProtoImageNotFoundException.class, () ->
                imageGenerator.generate(image, null, image));
    }

    @Test
    void imageGeneratorGeneratesBlueImage() {
        //given
        BufferedImage image = new BufferedImage(5, 5, TYPE_INT_RGB);

        //when
        BufferedImage generate = imageGenerator.generate(image);

        //then
        assertNotNull(generate);
    }

}