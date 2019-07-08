package org.fcg.proto;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProtoApplicationTests {

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
}