package com.voucher.movie.reservation;

import lombok.Data;

@Data
public class TemporaryVO {
	
	private int id;
	private int random_idx;
	private int res_theaterCheck;
	private String res_visitDate;
	private int res_visitNum;
	private String res_visitTime;

}
