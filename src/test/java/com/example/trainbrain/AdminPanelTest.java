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
@Sql(value = {"/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@WithUserDetails("test_admin")
public class AdminPanelTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void usersPageTest() throws Exception {
        this.mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("/html/body/div/header/div/nav/div[5]/a/span")
                        .string("Профіль"));
    }

    @Test
    public void editPageTest() throws Exception {
        this.mockMvc.perform(get("/users/edit/2"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("/html/body/div/header/div/nav/div[5]/a/span")
                        .string("Профіль"));
    }

    @Test
    public void editUsernameTest() throws Exception {
        this.mockMvc.perform(post("/users/editUsername")
                        .param("user_id", "2")
                        .param("username", "new_name")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/edit/2"));
    }

    @Test
    public void editRolesTest() throws Exception {
        this.mockMvc.perform(post("/users/editRoles")
                        .param("user_id", "2")
                        .param("role", "ADMIN")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/edit/2"));
    }

    @Test
    public void removeUserTest() throws Exception {
        this.mockMvc.perform(post("/users/remove/2")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users"));
    }
}
