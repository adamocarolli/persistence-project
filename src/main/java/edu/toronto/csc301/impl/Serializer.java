package edu.toronto.csc301.impl;

import java.io.InputStream;
import java.io.OutputStream;

import edu.toronto.csc301.ISerializer;
import edu.toronto.csc301.IUser;

public class Serializer implements ISerializer {

	@Override
	public void serialize(IUser user, OutputStream output) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public IUser deserializeUser(InputStream input) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}