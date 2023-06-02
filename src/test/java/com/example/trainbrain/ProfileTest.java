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
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-before.sql", "/create-marks-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-marks-after.sql", "/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@WithUserDetails("test_admin")
public class ProfileTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void profilePageTest() throws Exception {
        this.mockMvc.perform(get("/users/profile/1"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("/html/body/div/header/div/nav/div[5]/a/span")
                        .string("Профіль"));
    }

    @Test
    public void listHistoryTest() throws Exception {
        this.mockMvc.perform(get("/users/profile/1"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("/html/body/div/main/div/div/section[3]/div[1]/table/tr")
                        .nodeCount(4));
    }

    @Test
    public void changePasswordTest() throws Exception {
        this.mockMvc.perform(post("/users/profile/editPassword")
                        .param("password_new", "new")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/profile"));
    }

    @Test
    public void logoutTest() throws Exception {
        this.mockMvc.perform(post("/logout")
                        .with(csrf()))
                .andDo(print())
                .andExpect(unauthenticated())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?logout"));
    }
}
