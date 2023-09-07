package com.voucher.movie.board;

import lombok.Data;

@Data
public class NewsVO {
	
	private int id;
	private String news_title;
	private String news_date;
	private String news_place;
	private String news_method;
	private String news_contact;
	private String news_content;
	private int news_screeningCheck;
	private String news_poster;
	private String news_link1;
	private String news_link2;
	private String create_date;
	private String update_date;
}
