package com.rapipay.otp.app.Services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.rapipay.otp.bean.OTP;
import com.rapipay.otp.repository.OTPRepository;
import com.rapipay.otp.services.OTPService;

@SpringBootTest
class OTPServiceTest {

	@Autowired
	private OTPService otpservice;

	@MockBean
	private OTPRepository dao;

	@Test
	@DisplayName("Validate Email 1")
	void testCheckEmail1() {
		boolean expected = true;
		boolean actual = otpservice.checkEmail("abc@gmail.com");
		assertEquals(expected, actual);
	}

	@Test
	@DisplayName("Validate Email 2")
	void testCheckEmail2() {
		boolean expected = false;
		boolean actual = otpservice.checkEmail("abc@gmail.");
		assertEquals(expected, actual);
	}
	
	@Test
	@DisplayName("Validate Email 3")
	void testCheckEmail3() {
		boolean expected = false;
		boolean actual = otpservice.checkEmail("@gmail.");
		assertEquals(expected, actual);
	}
	@Test
	@DisplayName("Validate Email 4")
	void testCheckEmail4() {
		boolean expected = false;
		boolean actual = otpservice.checkEmail("gmail.");
		assertEquals(expected, actual);
	}

	@Test
	void testSendOTP() {
		assertThrows(Exception.class, () -> {
			otpservice.sendOTP(new OTP("bhumikasharma2k17@gmail.com", 12345, 0, true));
		});
	}

	@Test
	void testVerifyOTP() {
		assertThrows(Exception.class, () -> {
			otpservice.verifyOTP(new OTP("tripti@gmail.com", 12378, 0, false));
		});

	}

	@Test
	void testVerifyOTP2() {
		assertThrows(Exception.class, () -> {
			otpservice.verifyOTP(new OTP("garima@gmail.com", 91234, 0, true));
		});

	}

}
