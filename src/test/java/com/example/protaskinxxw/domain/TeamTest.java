package com.example.protaskinxxw.domain;

import static com.example.protaskinxxw.domain.AppUserTestSamples.*;
import static com.example.protaskinxxw.domain.TeamTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.protaskinxxw.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TeamTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Team.class);
        Team team1 = getTeamSample1();
        Team team2 = new Team();
        assertThat(team1).isNotEqualTo(team2);

        team2.setId(team1.getId());
        assertThat(team1).isEqualTo(team2);

        team2 = getTeamSample2();
        assertThat(team1).isNotEqualTo(team2);
    }

    @Test
    void ownerTest() {
        Team team = getTeamRandomSampleGenerator();
        AppUser appUserBack = getAppUserRandomSampleGenerator();

        team.setOwner(appUserBack);
        assertThat(team.getOwner()).isEqualTo(appUserBack);

        team.owner(null);
        assertThat(team.getOwner()).isNull();
    }
}
