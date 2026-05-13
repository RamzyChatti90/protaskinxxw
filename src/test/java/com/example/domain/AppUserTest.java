package com.example.domain;

import static com.example.domain.AppUserTestSamples.*;
import static com.example.domain.TaskTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class AppUserTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppUser.class);
        AppUser appUser1 = getAppUserSample1();
        AppUser appUser2 = new AppUser();
        assertThat(appUser1).isNotEqualTo(appUser2);

        appUser2.setId(appUser1.getId());
        assertThat(appUser1).isEqualTo(appUser2);

        appUser2 = getAppUserSample2();
        assertThat(appUser1).isNotEqualTo(appUser2);
    }

    @Test
    void tasksTest() {
        AppUser appUser = getAppUserRandomSampleGenerator();
        Task taskBack = getTaskRandomSampleGenerator();

        appUser.addTasks(taskBack);
        assertThat(appUser.getTasks()).containsOnly(taskBack);
        assertThat(taskBack.getAppUser()).isEqualTo(appUser);

        appUser.removeTasks(taskBack);
        assertThat(appUser.getTasks()).doesNotContain(taskBack);
        assertThat(taskBack.getAppUser()).isNull();

        appUser.tasks(new HashSet<>(Set.of(taskBack)));
        assertThat(appUser.getTasks()).containsOnly(taskBack);
        assertThat(taskBack.getAppUser()).isEqualTo(appUser);

        appUser.setTasks(new HashSet<>());
        assertThat(appUser.getTasks()).doesNotContain(taskBack);
        assertThat(taskBack.getAppUser()).isNull();
    }
}
