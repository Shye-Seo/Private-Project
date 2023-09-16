package com.voucher.movie.admin;

import lombok.Data;

@Data
public class QuestionVO {

	private int id;
	private String question_writer;
	private String question_title;
	private String question_phone;
	private String question_content;
	private String question_pw;
	private int question_status;
	private String create_date;
	private String update_date;
}
