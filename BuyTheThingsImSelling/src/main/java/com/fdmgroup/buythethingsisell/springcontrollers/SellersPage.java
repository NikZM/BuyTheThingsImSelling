package com.fdmgroup.buythethingsisell.springcontrollers;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fdmgroup.buythethingsisell.entities.ItemEntity;
import com.fdmgroup.buythethingsisell.entities.ItemsEntityWrapper;
import com.fdmgroup.buythethingsisell.jdbc.itemfunctions.DeleteItemJDBC;
import com.fdmgroup.buythethingsisell.jdbc.itemfunctions.ItemBySellerNameJDBC;
import com.fdmgroup.buythethingsisell.jdbc.itemfunctions.ItemReadJDBC;
import com.fdmgroup.buythethingsisell.jdbc.itemfunctions.ItemWriteJDBC;
import com.fdmgroup.buythethingsisell.jdbc.itemfunctions.UpdateItemJDBC;

@Controller
public class SellersPage {
	
	@Resource
	private SecurityContextUserName securityContextUserName;
	@Resource
	private ItemReadJDBC itemReadJDBC;
	@Resource
	private ItemWriteJDBC itemWriteJDBC;
	@Resource
	private UpdateItemJDBC updateItemJDBC;
	@Resource
	private DeleteItemJDBC deleteItemJDBC;
	@Resource
	private ItemBySellerNameJDBC itemBySellerNameJDBC;
		
		@RequestMapping(value = "/sell", method = RequestMethod.GET)
		public String getSellPage(ModelMap model){
			String email = null;
			if ((email = securityContextUserName.getUserName()) != null) {
				ItemsEntityWrapper iew = itemBySellerNameJDBC.getItemsBySeller(email);
				model.addAttribute("prevItems", iew.getItems());
			}
			return "sell";
		}
		
		@RequestMapping(value = "/sell/newItem", method = RequestMethod.GET)
		public ModelAndView getNewItem(ModelAndView modelAndView, @RequestParam(value="id", required=false) Integer id){
			if (id == null){
				modelAndView.addObject("itemEntity", new ItemEntity());
			} else {
				modelAndView.addObject("itemEntity", itemReadJDBC.getItemByID(id));
			}
			modelAndView.setViewName("newItem");
			return modelAndView;
		}
		
		@RequestMapping(value = "/sell/postItem", method = RequestMethod.POST)
		public String submitNewItem(ModelMap model, @ModelAttribute ItemEntity itemEntity){
			boolean submitted = false;
			if (itemEntity.getId() != 0){
				submitted = updateItemJDBC.updateItem(itemEntity);
			} else {
				String email;
				if ((email = securityContextUserName.getUserName()) != null) {
					submitted = itemWriteJDBC.postItem(itemEntity, email);
				}
			}
			model.addAttribute("submitted", submitted); 
			return "redirect:/sell";
		}
		
		@RequestMapping(value = "/sell/deleteItem", method = RequestMethod.GET)
		public String deleteItem(ModelMap model, @ModelAttribute ItemEntity itemEntity){
			boolean submitted = false;
			int itemId = itemEntity.getId();
			submitted = deleteItemJDBC.deleteItem(itemId);
			model.addAttribute("submitted", submitted); 
			return "redirect:/sell";
		}

}
