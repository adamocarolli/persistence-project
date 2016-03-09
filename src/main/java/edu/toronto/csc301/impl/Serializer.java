package edu.toronto.csc301.impl;

import java.awt.image.RenderedImage;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.Iterator;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.mrbean.MrBeanModule;

import edu.toronto.csc301.ISerializer;
import edu.toronto.csc301.IUser;

public class Serializer implements ISerializer {
	
	private final ObjectMapper mapper;
	private final JsonFactory jsonFactory;
	
	// Constructor
	public Serializer() {
		this.mapper = new ObjectMapper();
		this.mapper.findAndRegisterModules();
		this.mapper.registerModule(new MrBeanModule());
		this.jsonFactory = this.mapper.getFactory();
	}

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
