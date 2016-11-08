package com.fdmgroup.buythethingsisell.springcontrollers;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fdmgroup.buythethingsisell.entities.ItemsEntityWrapper;
import com.fdmgroup.buythethingsisell.jdbc.itemfunctions.ItemByCategoryJDBC;

@Controller
@RequestMapping(value = "/category")
public class CategoriesListPage {
	
	@Resource
	private ItemByCategoryJDBC itemByCategoryJDBC;
	
	@RequestMapping(value = "/{catName}")
	public String gotoFilteredCategoryPage(Model model, @PathVariable("catName") String category){
		category = category.replaceAll("%20", " ").replaceAll("&", "&amp;");
		if (category.equals("all")){
			category = "%";
		}
		ItemsEntityWrapper itemsEntityWrapper = itemByCategoryJDBC.getItemsByCategory(category);
		model.addAttribute("catPageName", category);
		model.addAttribute("itemListObj", itemsEntityWrapper.getItems());
		return "itemList";
	}
}
