package org.fcg.proto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

import static org.fcg.proto.ProtoRequest.ChannelMap.*;

@Component
public class ProtoImageService {
    private ProtoImageGenerator imageGenerator;
    private ProtoImageReader imageReader;

    @Autowired
    public ProtoImageService(ProtoImageGenerator imageGenerator, ProtoImageReader imageReader) {
        this.imageGenerator = imageGenerator;
        this.imageReader = imageReader;
    }

    public Resource generate(final ProtoRequest req) throws IOException {
        BufferedImage image = getImage(req);

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", os);

        return new ByteArrayResource(os.toByteArray());
    }

    private BufferedImage getImage(ProtoRequest req) throws IOException {
        Objects.requireNonNull(req);

        if (req.getChannelMap() == visible) {
            return imageGenerator.generate(
                    imageReader.read(req.getImageName("B04")),
                    imageReader.read(req.getImageName("B03")),
                    imageReader.read(req.getImageName("B02")));
        } else if (req.getChannelMap() == vegetation) {
            return imageGenerator.generate(
                    imageReader.read(req.getImageName("B05")),
                    imageReader.read(req.getImageName("B06")),
                    imageReader.read(req.getImageName("B07")));
        } else if (req.getChannelMap() == waterVapor) {
            return imageGenerator.generate(
                    imageReader.read(req.getImageName("B09")));
        } else {
            throw new IllegalArgumentException();
        }
    }
}
