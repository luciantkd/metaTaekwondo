package com.taekwondo.metataekwondowebsite;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class WebLayerTest {

    @Autowired
    private MockMvc mockMvc;

    // Follow the redirection and test the final content
    @Test
    void shouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isFound())  // Expect 302 redirect
                .andExpect(header().string("Location", "/home"))
                .andDo(result -> {
                    this.mockMvc.perform(get(result.getResponse().getHeader("Location")))
                            .andExpect(status().isOk())
                            .andExpect(content().string(containsString("Meta-Taekwondo")));
                });
    }

}
