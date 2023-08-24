package com.voucher.movie.reservation;

import lombok.Data;

@Data
public class GroupVO {
	private int id;
	private String res_name;
	private String res_phone;
	private String res_visitDate;
	private int res_visitNum;
	private String res_visitTime;
	private int res_theaterCheck;
	private String res_groupName;
	private int res_studentNum;
	private int res_adultNum;
	private int res_leaderNum;
	private int res_vehicleCheck;
	private int res_requestCheck;
	private String res_message;
	private int res_totalNum;
	private int res_status;
	private String update_date;
}
