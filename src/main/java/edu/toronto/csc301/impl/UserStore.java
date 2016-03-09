package edu.toronto.csc301.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import edu.toronto.csc301.IUser;
import edu.toronto.csc301.IUserStore;

public class UserStore implements IUserStore {
	
	List<IUser> allUsers;
	Map<String, IUser> userMap;

	@Override
	public IUser createUser(String username, String password) throws Exception {
		if (username == null || password == null) throw new NullPointerException();
		if (username.trim().length() == 0) throw new IllegalArgumentException();
		if (userMap.containsKey(username)) throw new IllegalArgumentException();
		return null;
	}

	@Override
	public IUser getUser(String username) {
		if (username == null) throw new NullPointerException();
		return null;
	}

	@Override
	public Iterator<IUser> getAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

}
