package com.voucher.movie.board;

import lombok.Data;

@Data
public class NoticeVO {
	private int id;
	private String notice_title;
	private String notice_date;
	private String notice_content;
	private String update_date;
}
