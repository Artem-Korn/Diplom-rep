package com.example.trainbrain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-before.sql", "/create-studclasses-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-studclasses-after.sql", "/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@WithUserDetails("test_admin")
public class StudClassesTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void studclassesPageTest() throws Exception {
        this.mockMvc.perform(get("/classes"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("/html/body/div/header/div/nav/div[5]/a/span")
                                .string("Профіль"));
    }

    @Test
    public void addStudclassPageTest() throws Exception {
        this.mockMvc.perform(get("/classes/createClass"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("/html/body/div/header/div/nav/div[5]/a/span")
                        .string("Профіль"));
    }

    @Test
    public void addUserPageTest() throws Exception {
        this.mockMvc.perform(get("/classes/inviteUser/2"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("/html/body/div/header/div/nav/div[5]/a/span")
                        .string("Профіль"));
    }

    @Test
    public void studclassesListTest() throws Exception {
        this.mockMvc.perform(get("/classes"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("/html/body/div/main/div/section[1]/div/table/tr")
                        .nodeCount(4))
                .andExpect(xpath("/html/body/div/main/div/section[2]/div")
                        .nodeCount(3));
    }

    @Test
    public void userListTest() throws Exception {
        this.mockMvc.perform(get("/classes"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("/html/body/div/main/div/section[2]/div[1]/table/tr")
                        .nodeCount(4));
    }

    @Test
    public void addStudclassTest() throws Exception {
        this.mockMvc.perform(post("/classes/createClass")
                        .param("name", "test_class4")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/classes"));
    }

    @Test
    public void deleteStudclassTest() throws Exception {
        this.mockMvc.perform(post("/classes/removeClass/1")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/classes"));
    }

    @Test
    public void addUserTest() throws Exception {
        this.mockMvc.perform(post("/classes/inviteUser/2")
                        .param("username", "test_student")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/classes"));
    }

    @Test
    public void deleteUserTest() throws Exception {
        this.mockMvc.perform(post("/classes/removeUser/1/1")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/classes"));
    }

    @Test
    public void getUserProfileTest() throws Exception {
        this.mockMvc.perform(get("/users/profile/2"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(content().string(containsString("test_student")));
    }
}
