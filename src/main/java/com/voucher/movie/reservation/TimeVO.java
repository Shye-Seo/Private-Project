package com.voucher.movie.reservation;

import lombok.Data;

@Data
public class TimeVO {

		private int id;
		private String res_date;
		private int res_num;
		private String res_time;
		private int res_limited;
		private int time_status;
}
