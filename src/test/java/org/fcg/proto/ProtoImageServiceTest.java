package org.fcg.proto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.core.io.Resource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ProtoImageServiceTest {
    @Mock
    private ProtoImageGenerator mockImageGenerator;

    @Mock
    private ProtoImageReader mockImageReader;

    @Mock
    private Resource mockResource;

    @InjectMocks
    private ProtoImageService imageService;

    @BeforeEach
    void setUp() throws IOException {
        when(mockResource.contentLength()).thenReturn(100L);
        when(mockImageReader.read(anyString())).thenReturn(null);
    }

    @Test
    void imageServiceChecksRequestObject() {
        assertThrows(NullPointerException.class, () -> imageService.generate(null));
    }

    @Test
    void imageServiceChecksGeneratesImageForVisible() throws IOException {
        //given
        ProtoRequest req = new ProtoRequest("33", "U", "UP", "2018-08-04T10:00:31", "visible");
        when(mockImageGenerator.generate(any(), any(), any())).thenReturn(mockResource);

        //when
        Resource result = imageService.generate(req);

        //then
        assertNotNull(result.contentLength());
        verify(mockImageReader, times(1)).read(contains("B02"));
        verify(mockImageReader, times(1)).read(contains("B03"));
        verify(mockImageReader, times(1)).read(contains("B04"));
    }

    @Test
    void imageServiceChecksGeneratesImageForVegetation() throws IOException {
        //given
        ProtoRequest req = new ProtoRequest("33", "U", "UP", "2018-08-04T10:00:31", "vegetation");
        when(mockImageGenerator.generate(any(), any(), any())).thenReturn(mockResource);

        //when
        Resource result = imageService.generate(req);

        //then
        assertNotNull(result.contentLength());
        verify(mockImageReader, times(1)).read(contains("B05"));
        verify(mockImageReader, times(1)).read(contains("B06"));
        verify(mockImageReader, times(1)).read(contains("B07"));
    }

    @Test
    void imageServiceChecksGeneratesImageForWaterVapor() throws IOException {
        //given
        ProtoRequest req = new ProtoRequest("33", "U", "UP", "2018-08-04T10:00:31", "waterVapor");
        when(mockImageGenerator.generate(any())).thenReturn(mockResource);

        //when
        Resource result = imageService.generate(req);

        //then
        assertNotNull(result.contentLength());
        verify(mockImageReader, only()).read(contains("B09"));
    }

    @Test
    void imageServiceThrowsExceptionOnInvalidChannelMap() throws IOException {
        //given
        ProtoRequest req = new ProtoRequest("33", "U", "UP", "2018-08-04T10:00:31", "");
        when(mockImageGenerator.generate(any(), any(), any())).thenReturn(mockResource);

        //when & then
        assertThrows(IllegalArgumentException.class, () -> imageService.generate(req));
    }
}