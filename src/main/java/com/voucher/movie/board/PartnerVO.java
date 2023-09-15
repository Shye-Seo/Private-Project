package com.voucher.movie.board;

import lombok.Data;

@Data
public class PartnerVO {

	private int id;
	private String partner_title;
	private String partner_date; //제휴기간
	private String partner_sale; //제휴할인
	private String partner_content;
	private String partner_poster;
	private String update_date;
	
}
