package com.voucher.movie.board;

import lombok.Data;

@Data
public class EduVO {

	private int id;
	private String edu_title; //제목
	private String edu_date; //교육일시
	private String edu_place; //교육장소
	private String edu_target; //교육대상(인원)
	private String edu_explain; //교육내용 
	private String edu_price; //수강비용
	private String edu_deadline; //접수기간
	private String edu_method; //접수방법
	private String edu_content; //상세내용
	private String edu_poster;
	private String edu_link1;
	private String edu_link2;
	private String create_date;
	private String update_date;
	
}
