package com.fdmgroup.buythethingsisell.springcontrollers;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.fdmgroup.buythethingsisell.entities.BasketEntity;
import com.fdmgroup.buythethingsisell.entities.EntityFactory;
import com.fdmgroup.buythethingsisell.entities.ItemEntity;
import com.fdmgroup.buythethingsisell.entities.ItemsEntityWrapper;
import com.fdmgroup.buythethingsisell.jdbc.itemfunctions.ItemReadJDBC;

@Controller
@SessionAttributes({"cart"})
public class BasketPage {
	
	@Resource
	private ItemReadJDBC itemReadJDBC;
	@Resource
	private EntityFactory entityFactory;
	
	@RequestMapping(value="/basket", method = RequestMethod.GET)
	public String gotoBasketPage(@ModelAttribute("cart") BasketEntity basket, Model model){
		ItemsEntityWrapper iew = entityFactory.getItemEntityWrapper();
		for(int i : basket.keySet()){
			ItemEntity ie = itemReadJDBC.getItemByID(i);
			ie.setUnitsAvailable(basket.get(i).amount);
			iew.add(ie);
		}
		model.addAttribute("basketItems",iew.getItems());
		model.addAttribute("totPrice",basket.getPriceTotalS());
		return "basket";
	}
	
	@RequestMapping(value="/basket/update", method = RequestMethod.GET)
	public String updateBasket(@ModelAttribute("cart") BasketEntity basket, Model model, @RequestParam("id") int id, @RequestParam("price") double price, @RequestParam("quantity") int quantity){
		if(quantity < 1){
			basket.trueRemove(id);
		} else {
			basket.replace(id, basket.new PriceStockPair(price, quantity));
		}
		return "redirect:/basket";
	}
	
}
