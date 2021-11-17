package com.rapipay.otp.controller;

import com.rapipay.otp.bean.OTP;
import com.rapipay.otp.exception.EmailNotFoundException;
import com.rapipay.otp.exception.InvalidEmailException;
import com.rapipay.otp.services.OTPService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class OTPController {

	@Autowired
	OTPService otpService;
	private static final Logger LOGGER = LoggerFactory.getLogger(OTPController.class);

	@RequestMapping(method = RequestMethod.POST, value = "/generateOTP")
	public String sendOTP(@RequestBody OTP otp) {
		System.out.println("Inside Email");
		LOGGER.info("Entered generate otp section");
		try {
			if(otp.getChannelName().equals("email")) {
				otpService.sendOTP(otp);
				
			}else {
				
			}
				
			
		}catch(Exception e){
			LOGGER.error("Error level log message:"+e.toString());
			return e.toString();
		}
		
		return "OTP sent successfully";
	}
	

	@RequestMapping(method = RequestMethod.POST, value = "/verifyOTP")
	public String verifyOTP(@RequestBody OTP otp){
		LOGGER.info("Entered verify otp section");
		try {
			return otpService.verifyOTP(otp);
		}catch(Exception e) {
			LOGGER.error("Error level log message:"+e.toString());
			return e.toString();
		}
		
	}


}
