package com.revature.service;

import java.util.List;

import com.revature.dao.ReimbursementDAO;
import com.revature.dto.AddReimbursementDTO;
import com.revature.dto.EditReimbursementDTO;
import com.revature.model.Reimbursement;

public class ReimbursementService {
	
	private ReimbursementDAO reimbursementDao;
	
	public ReimbursementService() {
		this.reimbursementDao = new ReimbursementDAO();
	}
	
	
	public List<Reimbursement> getAllReimbsFromUserId(String userId){
		int id = Integer.parseInt(userId);
		
		List<Reimbursement> reimbursements = reimbursementDao.getAllReimbsFromUserId(id);
		
		return reimbursements;
	}


	public Reimbursement addReimbursement(String userId, AddReimbursementDTO reimbursement) {
		int id = Integer.parseInt(userId);
		
		Reimbursement addedReimbursement = reimbursementDao.addReimbursement(id, reimbursement);
		
		return addedReimbursement;
	}


	public List<Reimbursement> getAllReimbursementsFromStatus(String status) {
		int s = Integer.parseInt(status);
		
		List<Reimbursement> reimbursements = reimbursementDao.getAllReimbursementsFromStatus(s);
		
		return reimbursements;
	}


	public Reimbursement editReimbursement(String userIdString, String reimbIdString, EditReimbursementDTO reimbursementToEdit) {
		int userId = Integer.parseInt(userIdString);
		int reimbId = Integer.parseInt(reimbIdString);
		
		Reimbursement editedReimbursement = reimbursementDao.editReimbursement(userId, reimbId, reimbursementToEdit);
		
		return editedReimbursement;
	}


	public List<Reimbursement> getAllReimbursements() {
		
		List<Reimbursement> reimbursements = reimbursementDao.getAllReimbursements();
		
		
		 return reimbursements;
	}


	
	

}
