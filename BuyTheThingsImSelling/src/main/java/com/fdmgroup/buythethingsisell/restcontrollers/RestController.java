package com.fdmgroup.buythethingsisell.restcontrollers;

import java.sql.SQLException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fdmgroup.buythethingsisell.entities.EntityFactory;
import com.fdmgroup.buythethingsisell.entities.ItemsEntityWrapper;
import com.fdmgroup.buythethingsisell.entities.JSONConverter;
import com.fdmgroup.buythethingsisell.jdbc.itemfunctions.CategoryReaderJDBC;
import com.fdmgroup.buythethingsisell.jdbc.itemfunctions.ItemReadJDBC;
import com.fdmgroup.buythethingsisell.jdbc.userfunctions.AdminSQLDirectInterface;

@Component
@Path("/json")
public class RestController {
	
	final static Logger logger = Logger.getLogger(RestController.class);
	
	@Autowired
	private ItemReadJDBC itemReadJDBC;
	@Autowired
	private CategoryReaderJDBC categoryReadJDBC;
	@Autowired
	private AdminSQLDirectInterface adminSQLDI;
	@Autowired
	private JSONConverter jc;
	@Autowired
	private EntityFactory entityFactory;
	
 	@GET
	@Path("/items/{id}")
	public Response getResp(@PathParam("id") int id){
		return Response.status(200).entity(itemReadJDBC.getItemByID(id).toString().replaceAll("\r\n", "<br />")).build();
	}
 	
 	@GET
	@Path("/items")
	public Response getResp(@QueryParam("pageNo") int pageNum, @QueryParam("viewCount") int viewCount) {
 		int low = (pageNum-1)*viewCount;
 		int high = low + (viewCount-1);
		return Response.status(200).entity(itemReadJDBC.getItemsByPage(low,high, pageNum).toString().replaceAll("\r\n", "<br />")).build();
	}
 	
 	@GET
 	@Path("/categoriesList")
 	public Response getCats(){
 		String resp = jc.toJsonField("categories", categoryReadJDBC.getAllCategories());
 		return Response.status(200).entity("{" + resp + "}").build();
 	}
 	
 	//Possible the most evil sadistic piece of logic ever written.
	@GET //IS not working with POST which is vital
 	@Consumes({MediaType.APPLICATION_JSON})
 	@Produces({MediaType.APPLICATION_JSON})
 	@Path("/admin/sqlReturn")
 	public Response sqlDI(@QueryParam("query") String query){
 		String type="query";
		try {
			String resp = "{}";
			if (type.equals("update")){
				resp = "{\"rowsUpdated\":" + adminSQLDI.executeUpdate(query) + "}";
			} else if(type.equals("query")){
				resp = adminSQLDI.executeQuery(query);
			}
			return Response.status(200).entity(resp).build();
		} catch (SQLException e) {
			return Response.status(400).entity("{\"error\":\"" + e.getMessage() + "\"}").build();
		}
 	}
 	
 	@GET
 	@Path("/basket")
 	public Response getBasketItemsByID(@QueryParam(value="ids") String ids){
 		String[] idsArr = ids.split(",");
 		ItemsEntityWrapper iew = entityFactory.getItemEntityWrapper();
 		for(String s : idsArr){
 			try {
 				int i = Integer.parseInt(s);
 				iew.add(itemReadJDBC.getItemByID(i));
 			} catch (NumberFormatException | ArrayIndexOutOfBoundsException e){
 				logger.error("Could not parse: " + ids);
 			}
 		}
 		return Response.status(200).entity(iew.toString().replaceAll("\r\n", "<br />")).build();
 	}
}