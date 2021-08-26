package com.revature.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.revature.service.ReimbursementService;
import com.revature.dto.AddReimbursementDTO;
import com.revature.dto.MessageDTO;
import com.revature.model.Reimbursement;
import com.revature.model.User;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class ReimbursementController implements Controller {

	private ReimbursementService reimbursementService;
	
	public ReimbursementController() {
		this.reimbursementService = new ReimbursementService();
	}
	
	private Handler getAllReimbBelongingToSpecificUser = (ctx) ->{
			HttpSession session = ctx.req.getSession();
			
			if(session.getAttribute("currentUser") == null) {
				ctx.json(new MessageDTO("You need to be logged in to perform this action"));
			}else {
				User currentUser = (User) session.getAttribute("currentUser");
				
				String userId = ctx.pathParam("userid");
				
				if(currentUser.getId() == Integer.parseInt(userId)) {
					List<Reimbursement> reimbursements = reimbursementService.getAllReimbsFromUserId(userId);
					
					ctx.json(reimbursements);
					ctx.status(200);
				}else {
					ctx.json(new MessageDTO("You are not the user that you want to retrieve all reimbursements from"));
					ctx.status(401);
				}
			}
			
				
		
	};
	private Handler addReimbursement = (ctx) ->{
			
			
			
		HttpSession session = ctx.req.getSession();
		
		if(session.getAttribute("currentUser") == null) {
			ctx.json(new MessageDTO("You need to be logged in to perform this action"));
		}else {
			User currentUser = (User) session.getAttribute("currentUser");
			AddReimbursementDTO reimbursementToAdd = ctx.bodyAsClass(AddReimbursementDTO.class);
			String userId = ctx.pathParam("userid");
			if(currentUser.getId() == Integer.parseInt(userId)) {
				Reimbursement reimbursement = reimbursementService.addReimbursement(userId, reimbursementToAdd);
				ctx.json(reimbursement);
				ctx.status(200);
			
			} else {
				ctx.json(new MessageDTO("You are not the user that you want to add a reimbursement to"));
				ctx.status(401);
				
			}
			
		}
			
			
		
		
	};

	


	@Override
	public void mapEndpoints(Javalin app) {
		app.get("/user/:userid/reimbursement", getAllReimbBelongingToSpecificUser );
		app.post("/user/:userid/reimbursement", addReimbursement);
	
	}

}
