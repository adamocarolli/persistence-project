package edu.toronto.csc301.impl;

import java.awt.image.RenderedImage;
import java.time.LocalDateTime;
import java.util.Iterator;

import edu.toronto.csc301.IPost;
import edu.toronto.csc301.IUser;

public class Post implements IPost {

	@Override
	public RenderedImage getImage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setImage(RenderedImage profilePic) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getCaption() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCaption(String caption) {
		// TODO Auto-generated method stub

	}

	@Override
	public IUser getPostedBy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPostedBy(IUser user) {
		// TODO Auto-generated method stub

	}

	@Override
	public LocalDateTime getPostedAt() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPostedAt(LocalDateTime time) {
		// TODO Auto-generated method stub

	}

	@Override
	public Iterator<IUser> getLikes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addLike(IUser user) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeLike(IUser user) {
		// TODO Auto-generated method stub

	}

}
