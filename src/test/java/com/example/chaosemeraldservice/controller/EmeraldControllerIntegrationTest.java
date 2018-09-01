package com.example.chaosemeraldservice.controller;

import com.example.chaosemeraldservice.ChaosEmeraldServiceApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ChaosEmeraldServiceApplication.class)
@AutoConfigureMockMvc
public class EmeraldControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void order1_shouldGetEmeraldSuccessfully() throws Exception {
        this.mockMvc
                .perform(get("/emeralds/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.colour", is("YELLOW")))
                .andExpect(jsonPath("$.powerLevel", is(100)));
    }

    @Test
    public void order2_shouldFailToGetEmeraldWithErrorReturnedWhenDoesNotExist() throws Exception {
        this.mockMvc
                .perform(get("/emeralds/-1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Requested Emerald does not exist"));
    }

    @Test
    public void order3_shouldGetEmeraldsSuccessfully() throws Exception {
        this.mockMvc
                .perform(get("/emeralds"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(6)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].colour", is("YELLOW")))
                .andExpect(jsonPath("$[0].powerLevel", is(100)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].colour", is("PINK")))
                .andExpect(jsonPath("$[1].powerLevel", is(200)))
                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[2].colour", is("BLUE")))
                .andExpect(jsonPath("$[2].powerLevel", is(300)))
                .andExpect(jsonPath("$[3].id", is(4)))
                .andExpect(jsonPath("$[3].colour", is("GREEN")))
                .andExpect(jsonPath("$[3].powerLevel", is(400)))
                .andExpect(jsonPath("$[4].id", is(5)))
                .andExpect(jsonPath("$[4].colour", is("RED")))
                .andExpect(jsonPath("$[4].powerLevel", is(500)))
                .andExpect(jsonPath("$[5].id", is(6)))
                .andExpect(jsonPath("$[5].colour", is("WHITE")))
                .andExpect(jsonPath("$[5].powerLevel", is(600)));
    }

    @Test
    public void order4_shouldCreateEmeraldSuccessfully() throws Exception {
        String emeraldJson = "{\"powerLevel\":\"700\",\"colour\":\"YELLOW\"}";
        this.mockMvc
                .perform(post("/emeralds")
                        .content(emeraldJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(7)))
                .andExpect(jsonPath("$.powerLevel", is(700)))
                .andExpect(jsonPath("$.colour", is("YELLOW")));

    }

    @Test
    public void order5_shouldUpdateEmeraldSuccessfully() throws Exception {
        String emeraldJson = "{\"powerLevel\":\"700\",\"colour\":\"YELLOW\"}";
        this.mockMvc
                .perform(put("/emeralds/2")
                        .content(emeraldJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.powerLevel", is(700)))
                .andExpect(jsonPath("$.colour", is("YELLOW")));
    }

    @Test
    public void order6_shouldNotUpdateEmeraldWhenSameValues() throws Exception {
        String emeraldJson = "{\"powerLevel\":\"300\",\"colour\":\"BLUE\"}";
        this.mockMvc
                .perform(put("/emeralds/3")
                        .content(emeraldJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Requested Emerald was not updated"));
    }

    @Test
    public void order7_shouldDeleteEmerald() throws Exception {
        this.mockMvc
                .perform(delete("/emeralds/4"))
                .andExpect(status().isNoContent());
        this.mockMvc
                .perform(get("/emeralds/4"))
                .andExpect(status().isBadRequest());
    }
}