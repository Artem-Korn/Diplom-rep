package com.example.trainbrain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-before.sql", "/create-tasks-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-tasks-after.sql", "/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@WithUserDetails("test_admin")
public class GamesTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void gamesPageTest() throws Exception {
        this.mockMvc.perform(get("/games"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("/html/body/div/header/div/nav/div[5]/a/span")
                        .string("Профіль"));
    }

    @Test
    public void gameMolePageTest() throws Exception {
        this.mockMvc.perform(get("/games/mole"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("/html/body/div/header/div/nav/div[5]/a/span")
                        .string("Профіль"));
    }

    @Test
    public void gameShultePageTest() throws Exception {
        this.mockMvc.perform(get("/games/shulte"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("/html/body/div/header/div/nav/div[5]/a/span")
                        .string("Профіль"));
    }

    @Test
    public void gameComparePageTest() throws Exception {
        this.mockMvc.perform(get("/games/compare"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("/html/body/div/header/div/nav/div[5]/a/span")
                        .string("Профіль"));
    }

    @Test
    public void taskShultePageTest() throws Exception {
        this.mockMvc.perform(get("/games/play/1"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("/html/body/div/header/div/nav/div[5]/a/span")
                        .string("Профіль"));
    }

    @Test
    public void taskMolePageTest() throws Exception {
        this.mockMvc.perform(get("/games/play/2"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("/html/body/div/header/div/nav/div[5]/a/span")
                        .string("Профіль"));
    }

    @Test
    public void taskComparePageTest() throws Exception {
        this.mockMvc.perform(get("/games/play/3"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("/html/body/div/header/div/nav/div[5]/a/span")
                        .string("Профіль"));
    }

    @Test
    public void editTaskPageTest() throws Exception {
        this.mockMvc.perform(get("/games/edit/1"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("/html/body/div/header/div/nav/div[5]/a/span")
                        .string("Профіль"));
    }

    @Test
    public void listTasksTest() throws Exception {
        this.mockMvc.perform(get("/games"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("/html/body/div/main/div/section[1]/div/a")
                        .nodeCount(3));
    }

    @Test
    public void listMyTasksTest() throws Exception {
        this.mockMvc.perform(get("/games"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("/html/body/div/main/div/section[2]/div/a")
                        .nodeCount(3));
    }

    @Test
    public void listGamesTest() throws Exception {
        this.mockMvc.perform(get("/games"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("/html/body/div/main/div/section[3]/div/a")
                        .nodeCount(3));
    }

    @Test
    public void editTaskTest() throws Exception {
        this.mockMvc.perform(post("/games/sendTask/1")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/games"));
    }

    @Test
    public void removeTaskTest() throws Exception {
        this.mockMvc.perform(post("/games/removeTask/1")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/games"));
    }
}
