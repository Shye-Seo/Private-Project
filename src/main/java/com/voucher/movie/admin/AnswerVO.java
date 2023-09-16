package com.voucher.movie.admin;

import lombok.Data;

@Data
public class AnswerVO {

	private int id;
	private int question_id;
	private String answer_title;
	private String answer_content;
	private String update_date;
}
