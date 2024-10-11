package com.reactit.credit.score.domain;

import static com.reactit.credit.score.domain.MemberUserTestSamples.*;
import static com.reactit.credit.score.domain.NotificationTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.reactit.credit.score.web.rest.TestUtil;
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
    void memberUserTest() throws Exception {
        Notification notification = getNotificationRandomSampleGenerator();
        MemberUser memberUserBack = getMemberUserRandomSampleGenerator();

        notification.setMemberUser(memberUserBack);
        assertThat(notification.getMemberUser()).isEqualTo(memberUserBack);

        notification.memberUser(null);
        assertThat(notification.getMemberUser()).isNull();
    }
}
