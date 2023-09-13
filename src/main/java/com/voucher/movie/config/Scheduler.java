package com.voucher.movie.config;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;

import javax.inject.Inject;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.voucher.movie.admin.AdminService;

@EnableScheduling
public class Scheduler {
	
	@Inject
	AdminService adminService;
	
	@Scheduled(cron = "0 0 0 1 1 ?") //매년 1월 1일 00:00마다 실행
	public void closed_scheduler() {
		
		Calendar today = Calendar.getInstance();
		int currentYear = today.get(Calendar.YEAR);
		
		for(int i=1; i<13; i++) {
			int lastDay=31;
		    if(i==2) {
		    	if(currentYear%4==0 && currentYear%100 !=0 || currentYear%4==0 && currentYear%400==0) { // ||보다 &&가 우선순위가 먼저임
		        	lastDay=29;
		        	for(int j=1; j<=lastDay; j++) {
		        		LocalDate date = LocalDate.of(currentYear, i, j);
		        		DayOfWeek dayOfWeek = date.getDayOfWeek();
		        		int dayOfWeekNumber = dayOfWeek.getValue();
		        		
		        		//i<10 -> 월 앞에 0 붙이기, j<10 -> 일 앞에 0 붙이기
		        		if(dayOfWeekNumber == 1) {
		        			if(i<10 && j<10) {
		        				String date_str = currentYear + ".0" + i + ".0" + j;
		        			}else if(i<10 && j>10) {
		        				String date_str = currentYear + ".0" + i + "." + j;
		        			}else if(i>10 && j<10) {
		        				String date_str = currentYear + "." + i + ".0" + j;
		        			}else {
		        				String date_str = currentYear + "." + i + "." + j;
		        			}
//		        			adminService.closed_scheduler(date_str);
		        		}
		        	}
		        }else {
		        	lastDay=28;
		        }
		    }else if(i==4 || i==6 || i==9 || i==11) {
		      	lastDay = 30;
		    }
			
		}
		
		//휴관일 설정(모든 월요일 insert)
//		adminService.closed_scheduler(date);
	}

}
