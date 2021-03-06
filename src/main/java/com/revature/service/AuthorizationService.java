package com.revature.service;

import com.revature.exception.NotAuthorizedException;
import com.revature.model.User;

import io.javalin.http.Context;

public class AuthorizationService {
	
	public void guard(Context ctx) throws NotAuthorizedException{
		User user = (User) ctx.sessionAttribute("currentUser");
		
		if(user.getUserRole().getId() != 1) {
			throw new NotAuthorizedException("Permission Denied: User is not Finance Manager");
		}
	}

}
