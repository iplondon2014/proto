package org.fcg.proto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Component
public class FileSystemBasedProtoImageReader implements ProtoImageReader {
    private String basePath;

    public FileSystemBasedProtoImageReader(@Value("${image.source.base.path}") String basePath) {
        this.basePath = basePath;
    }

    @Override
    public BufferedImage read(String imageName) throws IOException {
        return ImageIO.read(new File(basePath+"/"+imageName));
    }
}
