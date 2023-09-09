package com.voucher.movie.admin;

import lombok.Data;

@Data
public class PopupVO {
	
	private int no; //메인에 팝업 띄울 넘버 설정(첫번째 팝업, 두번째 팝업)
	private int id;
	private String popup_title;
	private String start_date;
	private String end_date;
	private int status;
	private String file_name;
	private String click_link;
	private String update_date;
}
