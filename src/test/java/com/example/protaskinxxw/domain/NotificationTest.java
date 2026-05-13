package com.example.protaskinxxw.domain;

import static com.example.protaskinxxw.domain.AppUserTestSamples.*;
import static com.example.protaskinxxw.domain.NotificationTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.protaskinxxw.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NotificationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Notification.class);
        Notification notification1 = getNotificationSample1();
        Notification notification2 = new Notification();
        assertThat(notification1).isNotEqualTo(notification2);

        notification2.setId(notification1.getId());
        assertThat(notification1).isEqualTo(notification2);

        notification2 = getNotificationSample2();
        assertThat(notification1).isNotEqualTo(notification2);
    }

    @Test
    void userTest() {
        Notification notification = getNotificationRandomSampleGenerator();
        AppUser appUserBack = getAppUserRandomSampleGenerator();

        notification.setUser(appUserBack);
        assertThat(notification.getUser()).isEqualTo(appUserBack);

        notification.user(null);
        assertThat(notification.getUser()).isNull();
    }
}
