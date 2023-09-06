package com.voucher.movie.board;

import lombok.Data;

@Data
public class NewsVO {
	
	private int id;
	private String news_title;
	private String news_content;
	private String news_link;
	private String create_date;
	private String update_date;
}
