package com.voucher.movie.admin;

import lombok.Data;

@Data
public class PopupVO {
	
	private int id;
	private String popup_title;
	private String start_date;
	private String end_date;
	private int status;
	private String file_name;
	private String click_link;

}
