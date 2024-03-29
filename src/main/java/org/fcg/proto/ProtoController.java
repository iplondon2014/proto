package org.fcg.proto;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Objects;

@RestController
public class ProtoController {
    private ProtoImageService imageService;
    private HttpHeaders headers;

    @Autowired
    public ProtoController(ProtoImageService imageService) {
        this.imageService = imageService;
        this.headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
    }

    @RequestMapping(value = "/ping", produces = "text/plain")
    public String ping() {
        return "ok";
    }

    @RequestMapping(value = "/generate-image", consumes = "application/json", method = RequestMethod.POST)
    public ResponseEntity<Resource> generateImage(@RequestBody ProtoRequest req) throws IOException {
        validate(req);
        Resource resource = imageService.generate(req);
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    private void validate(ProtoRequest req) {
        if (Objects.isNull(req)
                || req.getUtmZone() <= 1
                || !Character.isLetter(req.getLatitudeBand())
                || Objects.isNull(req.getGridSquare()) || req.getGridSquare().isBlank()
                || Objects.isNull(req.getDate())
                || Objects.isNull(req.getChannelMap())) {
            throw new IllegalArgumentException();
        }
    }
}
