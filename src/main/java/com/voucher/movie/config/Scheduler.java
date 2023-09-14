package com.voucher.movie.config;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;

import javax.inject.Inject;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.voucher.movie.admin.AdminService;

@Component
public class Scheduler {
	
	@Inject
	AdminService adminService;
	
//	@Scheduled(cron = "0 54 8 * * *") //매일 오전 8:30
	@Scheduled(cron = "0 0 0 1 1 ?") //매년 1월 1일 00:00마다 실행
	public void closed_scheduler() {
		
		Calendar today = Calendar.getInstance();
		int currentYear = today.get(Calendar.YEAR);
		System.out.println(currentYear);
		
		for(int i=1; i<13; i++) {
			int lastDay=31;
		    if(i==2) {
		    	if(currentYear%4==0 && currentYear%100 !=0 || currentYear%4==0 && currentYear%400==0) { // ||보다 &&가 우선순위가 먼저임
		        	lastDay=29;
		        	for(int j=1; j<=lastDay; j++) {
		        		LocalDate date = LocalDate.of(currentYear, i, j);
		        		DayOfWeek dayOfWeek = date.getDayOfWeek();
		        		int dayOfWeekNumber = dayOfWeek.getValue();
		        		String date_str = "";
		        		
		        		//i<10 -> 월 앞에 0 붙이기, j<10 -> 일 앞에 0 붙이기
		        		if(dayOfWeekNumber == 1) {
		        			if(i<10 && j<10) {
		        				date_str = currentYear + ".0" + i + ".0" + j;
		        			}else if(i<10 && j>=10) {
		        				date_str = currentYear + ".0" + i + "." + j;
		        			}else if(i>=10 && j<10) {
		        				date_str = currentYear + "." + i + ".0" + j;
		        			}else if(i>=10 && j>=10){
		        				date_str = currentYear + "." + i + "." + j;
		        			}
		        			adminService.closed_scheduler(date_str);
		        		}
		        	}
		        }else {
		        	lastDay=28;
		        	for(int j=1; j<=lastDay; j++) {
		        		LocalDate date = LocalDate.of(currentYear, i, j);
		        		DayOfWeek dayOfWeek = date.getDayOfWeek();
		        		int dayOfWeekNumber = dayOfWeek.getValue();
		        		String date_str = "";
		        		
		        		//i<10 -> 월 앞에 0 붙이기, j<10 -> 일 앞에 0 붙이기
		        		if(dayOfWeekNumber == 1) {
		        			if(i<10 && j<10) {
		        				date_str = currentYear + ".0" + i + ".0" + j;
		        			}else if(i<10 && j>=10) {
		        				date_str = currentYear + ".0" + i + "." + j;
		        			}else if(i>=10 && j<10) {
		        				date_str = currentYear + "." + i + ".0" + j;
		        			}else if(i>=10 && j>=10){
		        				date_str = currentYear + "." + i + "." + j;
		        			}
		        			adminService.closed_scheduler(date_str);
		        		}
		        	}
		        }
		    }else if(i==4 || i==6 || i==9 || i==11) {
		      	lastDay = 30;
		      	for(int j=1; j<=lastDay; j++) {
	        		LocalDate date = LocalDate.of(currentYear, i, j);
	        		DayOfWeek dayOfWeek = date.getDayOfWeek();
	        		int dayOfWeekNumber = dayOfWeek.getValue();
	        		String date_str = "";
	        		
	        		//i<10 -> 월 앞에 0 붙이기, j<10 -> 일 앞에 0 붙이기
	        		if(dayOfWeekNumber == 1) {
	        			if(i<10 && j<10) {
	        				date_str = currentYear + ".0" + i + ".0" + j;
	        			}else if(i<10 && j>=10) {
	        				date_str = currentYear + ".0" + i + "." + j;
	        			}else if(i>=10 && j<10) {
	        				date_str = currentYear + "." + i + ".0" + j;
	        			}else if(i>=10 && j>=10){
	        				date_str = currentYear + "." + i + "." + j;
	        			}
	        			adminService.closed_scheduler(date_str);
	        		}
	        	}
		    }else{
		    	for(int j=1; j<=lastDay; j++) {
	        		LocalDate date = LocalDate.of(currentYear, i, j);
	        		DayOfWeek dayOfWeek = date.getDayOfWeek();
	        		int dayOfWeekNumber = dayOfWeek.getValue();
	        		String date_str = "";
	        		
	        		//i<10 -> 월 앞에 0 붙이기, j<10 -> 일 앞에 0 붙이기
	        		if(dayOfWeekNumber == 1) {
	        			if(i<10 && j<10) {
	        				date_str = currentYear + ".0" + i + ".0" + j;
	        			}else if(i<10 && j>=10) {
	        				date_str = currentYear + ".0" + i + "." + j;
	        			}else if(i>=10 && j<10) {
	        				date_str = currentYear + "." + i + ".0" + j;
	        			}else if(i>=10 && j>=10){
	        				date_str = currentYear + "." + i + "." + j;
	        			}
	        			adminService.closed_scheduler(date_str);
	        		}
	        	}
		    }
			
		}
//		return "admin_reservationSetting";
	}

}
