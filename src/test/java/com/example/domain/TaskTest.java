package com.example.domain;

import static com.example.domain.AppUserTestSamples.*;
import static com.example.domain.CategoryTestSamples.*;
import static com.example.domain.TaskTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TaskTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Task.class);
        Task task1 = getTaskSample1();
        Task task2 = new Task();
        assertThat(task1).isNotEqualTo(task2);

        task2.setId(task1.getId());
        assertThat(task1).isEqualTo(task2);

        task2 = getTaskSample2();
        assertThat(task1).isNotEqualTo(task2);
    }

    @Test
    void categoryTest() {
        Task task = getTaskRandomSampleGenerator();
        Category categoryBack = getCategoryRandomSampleGenerator();

        task.setCategory(categoryBack);
        assertThat(task.getCategory()).isEqualTo(categoryBack);

        task.category(null);
        assertThat(task.getCategory()).isNull();
    }

    @Test
    void assignedToTest() {
        Task task = getTaskRandomSampleGenerator();
        AppUser appUserBack = getAppUserRandomSampleGenerator();

        task.setAssignedTo(appUserBack);
        assertThat(task.getAssignedTo()).isEqualTo(appUserBack);

        task.assignedTo(null);
        assertThat(task.getAssignedTo()).isNull();
    }

    @Test
    void createdByTest() {
        Task task = getTaskRandomSampleGenerator();
        AppUser appUserBack = getAppUserRandomSampleGenerator();

        task.setCreatedBy(appUserBack);
        assertThat(task.getCreatedBy()).isEqualTo(appUserBack);

        task.createdBy(null);
        assertThat(task.getCreatedBy()).isNull();
    }
}
