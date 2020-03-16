package com.mhp.coding.challenges.dependency;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mhp.coding.challenges.dependency.inquiry.InquiryService;
import com.mhp.coding.challenges.dependency.inquiry.NotifyingInquiryService;

@Configuration
public class InquiryServiceConfiguration {

	@Bean
	public InquiryService inquiryService() {
		return new NotifyingInquiryService();
	}
}
