package org.fcg.proto;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface ProtoImageReader {
    BufferedImage read(String imageName) throws IOException;
}
