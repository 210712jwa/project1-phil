package com.revature.controller;

import com.revature.service.ReimbursementService;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.revature.dto.EditReimbursementDTO;
import com.revature.model.Reimbursement;
import com.revature.model.User;
import com.revature.service.AuthorizationService;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class ManagerActionsController implements Controller {
	private AuthorizationService authService;
	private ReimbursementService ReimbursementService;
	
	public ManagerActionsController() {
		this.authService = new AuthorizationService();
		this.ReimbursementService = new ReimbursementService();
	}
	
	private Handler filterRequestByStatus = (ctx) -> {
		HttpSession session = ctx.req.getSession();
		authService.guard(ctx);
		
		User currentUser = (User) session.getAttribute("currentUser");
	
		String status = ctx.pathParam("status");
		
		List<Reimbursement> reimbursements = ReimbursementService.getAllReimbursementsFromStatus(status);
		ctx.json(reimbursements);
	};
	
	private Handler processRequest = (ctx) -> {
		HttpSession session = ctx.req.getSession();
		authService.guard(ctx);
		
		
		String reimbId = ctx.pathParam("reimbid");
		
		EditReimbursementDTO reimbursementToEdit = ctx.bodyAsClass(EditReimbursementDTO.class);
		Reimbursement editedReimbursement = ReimbursementService.editReimbursement(reimbId, reimbursementToEdit);
		ctx.json(editedReimbursement);
	};
	private Handler getAllRequests = (ctx) -> {
		HttpSession session = ctx.req.getSession();
		authService.guard(ctx);
		
	
		
		List<Reimbursement> reimbursements = ReimbursementService.getAllReimbursements();
		
		ctx.json(reimbursements);
		
	};

	@Override
	public void mapEndpoints(Javalin app) {
		
		app.get("/getallrequests", getAllRequests );
		app.get("/filterrequestbystatus/:status", filterRequestByStatus);
		app.put("/processrequest/:reimbid", processRequest);
	
		
	}

}
