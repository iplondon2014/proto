package org.fcg.proto;

import java.awt.image.BufferedImage;

public interface ProtoImageGenerator {
    BufferedImage generate(BufferedImage redImage, BufferedImage greenImage, BufferedImage blueImage);

    BufferedImage generate(BufferedImage blueImage);
}
