package com.voucher.movie.reservation;

import lombok.Data;

@Data
public class FacilitiesVO {
	
	private int id;
	private int res_placeCheck;
	private String res_visitDate;
	private int res_visitNum;
	private String res_name;
	private String res_phone;
	private String res_email;
	private String res_rentalCheck;
	private String res_file;
	private int res_status;
	private String update_date;

}
