package com.mhp.coding.challenges.dependency.notifications;

import com.mhp.coding.challenges.dependency.inquiry.Inquiry;
import com.mhp.coding.challenges.dependency.inquiry.Inquirable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PushNotificationHandler implements Inquirable{

    private static final Logger LOG = LoggerFactory.getLogger(PushNotificationHandler.class);

    public void sendNotification(final Inquiry inquiry) {
        LOG.info("Sending push notification for: {}", inquiry);
    }

	@Override
	public void sendInquiry(final Inquiry inquiry) {
		sendNotification(inquiry);
	}
}
