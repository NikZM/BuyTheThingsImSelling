package com.fdmgroup.buythethingsisell.springcontrollers;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fdmgroup.buythethingsisell.entities.ItemEntity;
import com.fdmgroup.buythethingsisell.jdbc.itemfunctions.ItemReadJDBC;

@Controller
public class ProductPage {

	@Resource
	private ItemReadJDBC itemReadJDBC;
	
	@RequestMapping(value="/productPage", method = RequestMethod.GET)
	public String gotoProductPage(Model model, @RequestParam(value="id") int id){
		ItemEntity ie = itemReadJDBC.getItemByID(id);
		model.addAttribute("item", ie);
		return "productPage";
	}
	
}
