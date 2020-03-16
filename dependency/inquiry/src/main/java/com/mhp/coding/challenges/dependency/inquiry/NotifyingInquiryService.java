package com.mhp.coding.challenges.dependency.inquiry;

import com.mhp.coding.challenges.dependency.inquiry.Inquiry;
import com.mhp.coding.challenges.dependency.inquiry.InquiryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotifyingInquiryService extends InquiryService {

	@Autowired
	private List<Inquirable> medium;

	@Override
	public void create(Inquiry inquiry) {
		super.create(inquiry);
		this.sendInquiry(inquiry);
	}

	private void sendInquiry(Inquiry inquiry) {
		for (Inquirable m : medium) {
			m.sendInquiry(inquiry);
		}
	}
}
