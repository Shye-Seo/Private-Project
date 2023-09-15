package com.voucher.movie.board;

import lombok.Data;

@Data
public class EventVO {

	private int id;
	private String event_title;
	private String event_date;
	private String event_place;
	private String event_method;
	private String event_contact;
	private String event_content;
	private int event_screeningCheck;
	private String event_poster;
	private String event_link1;
	private String event_link2;
	private String create_date;
	private String update_date;
}
