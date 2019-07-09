package org.fcg.proto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;

import java.util.Objects;

public class ProtoImageService {
    private ProtoImageGenerator imageGenerator;
    private ProtoImageReader imageReader;

    @Autowired
    public ProtoImageService(ProtoImageGenerator imageGenerator, ProtoImageReader imageReader) {
        this.imageGenerator = imageGenerator;
        this.imageReader = imageReader;
    }

    public Resource generate(final ProtoRequest req) {
        Objects.requireNonNull(req);

        if (req.getChannelMap().equals("visible")) {
            return imageGenerator.generate(
                    imageReader.read(getImageName("B04")),
                    imageReader.read(getImageName("B03")),
                    imageReader.read(getImageName("B02")));
        } else if (req.getChannelMap().equals("vegetation")) {
            return imageGenerator.generate(
                    imageReader.read(getImageName("B05")),
                    imageReader.read(getImageName("B06")),
                    imageReader.read(getImageName("B07")));
        } else if (req.getChannelMap() == "waterVapor") {
            return imageGenerator.generate(
                    imageReader.read(getImageName("B09")));
        } else {
            throw new IllegalArgumentException();
        }
    }

    private String getImageName(String sensorBand) {
        return sensorBand;
    }
}
