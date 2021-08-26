package com.revature.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.revature.dto.AddReimbursementDTO;
import com.revature.dto.EditReimbursementDTO;
import com.revature.model.Reimbursement;
import com.revature.model.ReimbursementStatus;
import com.revature.model.ReimbursementType;
import com.revature.model.User;
import com.revature.util.SessionFactorySingleton;

public class ReimbursementDAO {
	
	public List<Reimbursement> getAllReimbsFromUserId(int id){
		SessionFactory sf = SessionFactorySingleton.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		
		List<Reimbursement> reimbursements = session.createQuery("SELECT r FROM Reimbursement r JOIN r.author u WHERE u.id = :userid").setParameter("userid", id).getResultList();
		
		return reimbursements;
		
		
		
	}

	public Reimbursement addReimbursement(int id, AddReimbursementDTO reimbursement) {
		
		SessionFactory sf = SessionFactorySingleton.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		
		Reimbursement addedReimbursement = new Reimbursement();
		addedReimbursement.setAuthor(session.get(User.class, id));
		addedReimbursement.setType(session.get(ReimbursementType.class, reimbursement.getType()));
		addedReimbursement.setDescription(reimbursement.getDescription());
		addedReimbursement.setAmount(reimbursement.getAmount());
		addedReimbursement.setStatus(session.get(ReimbursementStatus.class, 1));
		addedReimbursement.setSubmitted(null);
		addedReimbursement.setResolved(null);
		addedReimbursement.setResolver(null);
		
				
		session.persist(addedReimbursement);
		
		tx.commit();
		
		session.close();
		
		return addedReimbursement;
	}

	public List<Reimbursement> getAllReimbursementsFromStatus(int s) {
		SessionFactory sf = SessionFactorySingleton.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		
		List<Reimbursement> reimbursements = session.createQuery("SELECT r FROM Reimbursement r JOIN r.status u WHERE u.id = :statusid").setParameter("statusid", s).getResultList();
		tx.commit();
		
		session.close();
		
		return reimbursements;
	}

	public Reimbursement editReimbursement(int userId, int reimbId, EditReimbursementDTO reimbursement) {
		Date date = new Date();
		Timestamp resolved = new Timestamp(date.getTime());
		
		SessionFactory sf = SessionFactorySingleton.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		
		Reimbursement reimbursementToEdit = session.get(Reimbursement.class, reimbId);
		reimbursementToEdit.setResolver(session.get(User.class, userId));
		reimbursementToEdit.setStatus(session.get(ReimbursementStatus.class, reimbursement.getStatus()));
		reimbursementToEdit.setResolved(resolved);
		session.saveOrUpdate(reimbursementToEdit);
		
		tx.commit();
		session.close();
		
		return reimbursementToEdit;
	}

	public List<Reimbursement> getAllReimbursements() {
		SessionFactory sf = SessionFactorySingleton.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		
		List<Reimbursement> reimbursements = session.createQuery("SELECT r FROM Reimbursement r").getResultList();
		
		tx.commit();
		session.close();
		
		
		return reimbursements;
	}




}
