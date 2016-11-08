package com.fdmgroup.buythethingsisell.springcontrollers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/admin")
public class AdminSQLConsole {
	
	@RequestMapping(value = "/sqlConsole")
	public String goToSQLConsole(){
		return "sqlConsole";
	}
	
	
	

}
