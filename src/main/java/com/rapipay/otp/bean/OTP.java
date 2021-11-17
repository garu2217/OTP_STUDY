package com.rapipay.otp.bean;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Random;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.lang.Nullable;

@Entity
@Table(name = "user_details")
public class OTP {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer orderId;
	private String email;
	private Integer otp;
	private boolean verified;
	private long generated_at;
	private String channelName;

	public OTP() {
	}

	public OTP(String email, Integer otp, long generated_at, boolean verified) {

		this.email = email;
		this.otp = otp;
		this.verified = verified;
		this.generated_at = generated_at;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getOtp() {
		return otp;
	}

	public void setOtp(Integer otp) {
		this.otp = otp;
	}

	public long getGenerated_at() {
		return generated_at;
	}

	public void setGenerated_at(long generated_at) {

		this.generated_at = generated_at;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	@Override
	public String toString() {
		return "OTP [orderId=" + orderId + ", channelName=" + channelName + ", email=" + email + ", otp=" + otp + ", verified=" + verified + ", generated_at=" + generated_at + "]";
	}

}
