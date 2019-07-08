package org.fcg.proto;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProtoApplicationTests {

    @MockBean
    ProtoImageService imageService;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void pingWorks() throws Exception {
        mockMvc.perform(get("/ping"))
                .andExpect(status().isOk())
                .andExpect(mvcResult -> "ok".equals(mvcResult.getResponse().getContentAsString()));
    }

    @Test
    public void generateImageThrowsExceptionOnInvalidPayloadType() throws Exception {
        mockMvc.perform(post("/generate-image").contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isUnsupportedMediaType())
                .andExpect(mvcResult -> "bad request".equals(mvcResult.getResponse().getContentAsString()));
    }

    @Test
    public void generateImageThrowsExceptionIfNoPayload() throws Exception {
        mockMvc.perform(post("/generate-image").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult -> "bad request".equals(mvcResult.getResponse().getContentAsString()));
    }

    @Test
    public void generateImageThrowsExceptionOnEmptyPayload() throws Exception {
        mockMvc.perform(post("/generate-image").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult -> "bad request".equals(mvcResult.getResponse().getContentAsString()));
    }

    @Test
    public void generateImageReturnsAnImageIfFound() throws Exception {
        //given
        Resource resource = resourceLoader.getResource("classpath:sample.jpg");
        when(imageService.generate(any())).thenReturn(resource);

        //when
        MvcResult mvcResult = mockMvc.perform(post("/generate-image")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"utmZone\": \"33\", \"latitudeBand\": \"U\", \"gridSquare\": \"UP\", \"date\": \"2018-08-04T10:00:31\", \"channelMap\": \"visible\" }"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_JPEG))
                .andReturn();

        //then
        assertEquals(resource.contentLength(), mvcResult.getResponse().getContentLength());
    }

    @Test
    public void generateImageThrowsExceptionIfImageIsNotFound() throws Exception {
        //given
        when(imageService.generate(any())).thenThrow(new ProtoImageNotFoundException());

        //when & then
        mockMvc.perform(post("/generate-image")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"utmZone\": \"33\", \"latitudeBand\": \"U\", \"gridSquare\": \"UP\", \"date\": \"2018-08-04T10:00:31\", \"channelMap\": \"visible\" }"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(mvcResult -> "not found".equals(mvcResult.getResponse().getContentAsString()));
    }

}