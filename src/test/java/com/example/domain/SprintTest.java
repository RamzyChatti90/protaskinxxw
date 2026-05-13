package com.example.domain;

import static com.example.domain.AppUserTestSamples.*;
import static com.example.domain.SprintTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SprintTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sprint.class);
        Sprint sprint1 = getSprintSample1();
        Sprint sprint2 = new Sprint();
        assertThat(sprint1).isNotEqualTo(sprint2);

        sprint2.setId(sprint1.getId());
        assertThat(sprint1).isEqualTo(sprint2);

        sprint2 = getSprintSample2();
        assertThat(sprint1).isNotEqualTo(sprint2);
    }

    @Test
    void ownerTest() {
        Sprint sprint = getSprintRandomSampleGenerator();
        AppUser appUserBack = getAppUserRandomSampleGenerator();

        sprint.setOwner(appUserBack);
        assertThat(sprint.getOwner()).isEqualTo(appUserBack);

        sprint.owner(null);
        assertThat(sprint.getOwner()).isNull();
    }
}
