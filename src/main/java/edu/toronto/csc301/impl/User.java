package edu.toronto.csc301.impl;

import java.awt.image.RenderedImage;
import java.time.LocalDateTime;
import java.util.Iterator;

import edu.toronto.csc301.IPost;
import edu.toronto.csc301.IUser;

public class User implements IUser {

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPassword(String password) {
		// TODO Auto-generated method stub

	}

	@Override
	public LocalDateTime getRegistrationTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRegistrationTime(LocalDateTime registrationTime) {
		// TODO Auto-generated method stub

	}

	@Override
	public IPost newPost(RenderedImage image, String caption) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<IPost> getPosts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void like(IPost post) {
		// TODO Auto-generated method stub

	}

	@Override
	public void unlike(IPost post) {
		// TODO Auto-generated method stub

	}

	@Override
	public Iterator<IPost> getLikes() {
		// TODO Auto-generated method stub
		return null;
	}

}
