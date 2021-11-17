package com.rapipay.otp.services;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.rapipay.otp.bean.OTP;
import com.rapipay.otp.controller.OTPResponse;
import com.rapipay.otp.exception.EmailNotFoundException;
import com.rapipay.otp.exception.InvalidAttemptsException;
import com.rapipay.otp.exception.InvalidEmailException;
import com.rapipay.otp.exception.InvalidOTPException;
import com.rapipay.otp.repository.OTPRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OTPService {

	@Value("${Attempts}")
	private int attempts;
	@Value("${Time}")
	private int time;

	@Autowired
	public OTPRepository otpRepo;

	@Autowired
	public EmailService emailService;

	public Integer generateOTP() {
		return (int) ((Math.random() * 800000) + 100000);
	}

	public boolean checkEmail(String email) {
		String regex = "^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w{2,}([-.]\\w+)*$";
		Pattern pattern = Pattern.compile(regex);

		Matcher matcher = pattern.matcher(email);
		return matcher.matches();

	}

	public String sendOTP(OTP ipotp) {

		List<OTP> check= otpRepo.findByEmailAndVerified(ipotp.getEmail(), false);
		int count= 1;
		for (OTP o : check) {

			if (o.getGenerated_at() > (new Date().getTime() / 1000) - time) {
				count++;
				if (count > attempts) {
					throw new InvalidAttemptsException("Try After sometime!!");
				}

			}
		}
		if (checkEmail(ipotp.getEmail())) {
			OTP otp = new OTP();

			otp.setEmail(ipotp.getEmail());
			otp.setChannelName(ipotp.getChannelName());
			otp.setOtp(generateOTP());
			otp.setVerified(false);
			otp.setGenerated_at(new Date().getTime() / 1000);
			sendOTPMail(otp);
			otpRepo.saveAndFlush(otp);
			return "successfully";
		}
		throw new InvalidEmailException("Entered Email is Wrong!!");
	}

	public String verifyOTP(OTP otp) {
		OTPResponse response = new OTPResponse();
		List<OTP> check = otpRepo.findByEmailAndVerified(otp.getEmail(), false);

		if (check == null) {
			response.setMessage("does not contain anything");
			response.setStatus(false);
			throw new EmailNotFoundException("Email does Not Exists");

		}

		List<OTP> otps;
		otps = (otpRepo.findByEmailAndOtpAndVerified(otp.getEmail(), otp.getOtp(), false));

		if (otps == null) {
			response.setMessage("verification failed");
			response.setStatus(false);
			response.toString();
			throw new InvalidOTPException("Entered OTP is Wrong!");
		}

		for (OTP o : otps) {
			if (o.getGenerated_at() > (new Date().getTime() / 1000) - 300) {
				o.setVerified(true);
				otpRepo.save(o);
				response.setMessage("OTP verified success");
				response.setStatus(true);
				return "OTP verified success";
			}
		}
		response.setMessage(" OTP has expired");
		response.setStatus(false);
		return response.toString();
	}

	public void sendOTPMail(OTP otp) {
		emailService.sendSimpleMessage(otp.getEmail(), "OTP for email verification",
				" otp for verifying email address is: " + otp.getOtp());

	}

}
