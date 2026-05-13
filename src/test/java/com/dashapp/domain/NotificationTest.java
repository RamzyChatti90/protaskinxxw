package com.dashapp.domain;

import static com.dashapp.domain.AppUserTestSamples.*;
import static com.dashapp.domain.NotificationTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dashapp.web.rest.TestUtil;
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
