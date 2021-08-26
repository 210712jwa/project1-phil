package com.revature.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.revature.model.Reimbursement;
import com.revature.model.ReimbursementStatus;
import com.revature.model.ReimbursementType;

import com.revature.model.User;
import com.revature.model.UserRole;

public class PopulateDataInDatabase {

	public static void main(String[] args) {
		
//		populateReimbStatus_ReimbType_UserRole();
//		addSampleUsers();
		addReimbursements();

	}



	private static void populateReimbStatus_ReimbType_UserRole() {
		SessionFactory sf = SessionFactorySingleton.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		
		UserRole financeManager = new UserRole("finance_manager");
		UserRole employee = new UserRole("employee");
		session.persist(financeManager);
		session.persist(employee);
		
		ReimbursementStatus pending = new ReimbursementStatus("pending");
		ReimbursementStatus approved = new ReimbursementStatus("approved");
		ReimbursementStatus denied = new ReimbursementStatus("denied");
		session.persist(pending);
		session.persist(approved);
		session.persist(denied);
		
		ReimbursementType lodging = new ReimbursementType("lodging");
		ReimbursementType travel = new ReimbursementType("travel");
		ReimbursementType food = new ReimbursementType("food");
		ReimbursementType other = new ReimbursementType("other");
		session.persist(lodging);
		session.persist(travel);
		session.persist(food);
		session.persist(other);
		
		tx.commit();
		session.close();
		
	}
	
	private static void addSampleUsers() {
		SessionFactory sf = SessionFactorySingleton.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		
		User financeManager1 = new User("Phil", "Belinsky", "phil.belinsky@revature.com","phil12345", "12345");
		UserRole financeManager = (UserRole) session.createQuery("FROM UserRole ur WHERE ur.role = 'finance_manager'").getSingleResult();
		financeManager1.setUserRole(financeManager);
		session.persist(financeManager1);
		
		UserRole employee = (UserRole) session.createQuery("FROM UserRole ur WHERE ur.role = 'employee'").getSingleResult();
		User employee1 = new User("Jane", "Doe", "janedoe@test.com", "jane12345", "12345");
		employee1.setUserRole(employee);
		User employee2 = new User("John", "Doe", "johndoe@gmail.com", "johnny123", "password123");
		employee2.setUserRole(employee);
		
		session.persist(employee1);
		session.persist(employee2);
		
		tx.commit();
		session.close();
		
	}
	
	private static void addReimbursements() {
		SessionFactory sf = SessionFactorySingleton.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		
		User phil12345 = (User) session.createQuery("FROM User u WHERE u.username = 'phil12345'").getSingleResult();
		User jane12345 = (User) session.createQuery("FROM User u WHERE u.username = 'jane12345'").getSingleResult();
		User johnny123 = (User) session.createQuery("FROM User u WHERE u.username = 'johnny123'").getSingleResult();
		ReimbursementStatus pending = (ReimbursementStatus) session.createQuery("FROM ReimbursementStatus r WHERE r.status = 'pending'").getSingleResult();
		
		ReimbursementType lodging = (ReimbursementType) session.createQuery("FROM ReimbursementType r WHERE r.type = 'lodging'").getSingleResult();
		ReimbursementType  travel = (ReimbursementType) session.createQuery("FROM ReimbursementType r WHERE r.type = 'travel'").getSingleResult();
		ReimbursementType food = (ReimbursementType) session.createQuery("FROM ReimbursementType r WHERE r.type = 'food'").getSingleResult();
		ReimbursementType other = (ReimbursementType) session.createQuery("FROM ReimbursementType r WHERE r.type = 'other'").getSingleResult();
		
		
		
		
		Reimbursement reimb1 = new Reimbursement("Dinner with clients", 100);
		reimb1.setAuthor(phil12345);
		reimb1.setStatus(pending);
		reimb1.setType(food);
		
		Reimbursement reimb2 = new Reimbursement("Two Nights at the Holliday Inn", 300);
		reimb2.setAuthor(jane12345);
		reimb2.setStatus(pending);
		reimb2.setType(lodging);
		
		Reimbursement reimb3 = new Reimbursement("Drive to Ohio", 200);
		reimb3.setAuthor(johnny123);
		reimb3.setStatus(pending);
		reimb3.setType(travel);
		
		
		session.persist(reimb1);
		session.persist(reimb2);
		session.persist(reimb3);
		
		
		tx.commit();
		session.close();
		
	}
	
}
