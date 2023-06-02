package com.example.trainbrain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class RegistrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void correctRegistration() throws Exception {
        this.mockMvc.perform(post("/registration")
                        .param("username", "test_new")
                        .param("password", "t")
                        .param("password2", "t")
                        .param("lastName", "last")
                        .param("firstName", "first")
                        .param("patronymic","patron")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    public void failedRegistration() throws Exception {
        this.mockMvc.perform(post("/registration")
                        .param("username", "")
                        .param("password", "")
                        .param("password2", "")
                        .param("lastName", "")
                        .param("firstName", "")
                        .param("patronymic","")
                        .with(csrf()))
                .andDo(print())
                .andExpect(xpath("/html/body/div/main/div/section/form/div/table/tr[1]/td[3]")
                        .exists())
                .andExpect(xpath("/html/body/div/main/div/section/form/div/table/tr[2]/td[3]")
                        .exists())
                .andExpect(xpath("/html/body/div/main/div/section/form/div/table/tr[3]/td[3]")
                        .exists())
                .andExpect(xpath("/html/body/div/main/div/section/form/div/table/tr[4]/td[3]")
                        .exists())
                .andExpect(xpath("/html/body/div/main/div/section/form/div/table/tr[5]/td[3]")
                        .exists())
                .andExpect(xpath("/html/body/div/main/div/section/form/div/table/tr[6]/td[3]")
                        .exists());
    }
}
