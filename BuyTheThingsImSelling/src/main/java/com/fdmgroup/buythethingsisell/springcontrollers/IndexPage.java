package com.fdmgroup.buythethingsisell.springcontrollers;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.fdmgroup.buythethingsisell.entities.BasketEntity;
import com.fdmgroup.buythethingsisell.entities.EntityFactory;
import com.fdmgroup.buythethingsisell.entities.ItemEntity;

@Controller
@RequestMapping(value = "/")
@SessionAttributes({"cart"})
public class IndexPage {
	
	@Resource
	private EntityFactory entityFactory;
	
	@RequestMapping(method = RequestMethod.GET)
	  public String getIndex(Model model) {
	    if(!model.containsAttribute("cart")) {
	      model.addAttribute("cart", entityFactory.getBasketEntity());
	    }
	    return "index";
	  }

	  @RequestMapping(value = "addProduct", method = RequestMethod.GET)
	  public String addProduct(@ModelAttribute ItemEntity itemEntity, @ModelAttribute("cart") BasketEntity basket) {
		  basket.put(itemEntity.getId(), basket.new PriceStockPair(itemEntity.getPrice()));
		  return "redirect:/";
	  }
	  
	  @RequestMapping(value="removeProduct", method = RequestMethod.GET)
	  public String removeProduct(@ModelAttribute ItemEntity itemEntity, @ModelAttribute("cart") BasketEntity basket){
		  basket.remove(itemEntity.getId());
		  return "redirect:/";
	  }

}
