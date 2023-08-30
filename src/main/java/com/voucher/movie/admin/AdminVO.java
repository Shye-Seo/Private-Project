package com.voucher.movie.admin;

import lombok.Data;

@Data
public class AdminVO {

	private int id;
	private int authority;
	private String login_id;
	private String login_pw;
	
}
