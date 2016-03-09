package edu.toronto.csc301.impl;

import java.io.InputStream;
import java.io.OutputStream;

import edu.toronto.csc301.ISerializer;
import edu.toronto.csc301.IUser;

public class Serializer implements ISerializer {

	@Override
	public void serialize(IUser user, OutputStream output) throws Exception {
		if (output == null) throw new NullPointerException();
		if (user == null) throw new NullPointerException();

	}

	@Override
	public IUser deserializeUser(InputStream input) throws Exception {
		if (input == null) throw new NullPointerException();
		return null;
	}

}
