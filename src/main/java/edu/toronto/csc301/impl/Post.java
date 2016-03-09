package edu.toronto.csc301.impl;

import java.awt.image.RenderedImage;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Iterator;

import edu.toronto.csc301.IPost;
import edu.toronto.csc301.IUser;

public class Post implements IPost {
	
	private IUser postedBy;
	private RenderedImage profilePic;
	private String caption;
	private LocalDateTime postedAt;
	private HashSet<IUser> likes;

	public Post(RenderedImage image, String caption) {
		this.profilePic = image;
		this.caption = caption;
		this.postedAt = LocalDateTime.now();
		this.likes = new HashSet<IUser>();
	}

	@Override
	public RenderedImage getImage() {
		return this.profilePic;
	}

	@Override
	public void setImage(RenderedImage profilePic) {
		this.profilePic = profilePic;
	}

	@Override
	public String getCaption() {
		return this.caption;
	}

	@Override
	public void setCaption(String caption) {
		this.caption = caption;
	}

	@Override
	public IUser getPostedBy() {
		return this.postedBy;
	}

	@Override
	public void setPostedBy(IUser user) {
		this.postedBy = user;
	}

	@Override
	public LocalDateTime getPostedAt() {
		return this.postedAt;
	}

	@Override
	public void setPostedAt(LocalDateTime time) {
		this.postedAt = time;
	}

	@Override
	public Iterator<IUser> getLikes() {
		return likes.iterator();
	}

	@Override
	public void addLike(IUser user) {
		if (this.likes.contains(user)) return;
		likes.add(user);
		user.like(this);
	}

	@Override
	public void removeLike(IUser user) {
		if (!this.likes.contains(user)) return;
		likes.remove(user);
		user.unlike(this);
	}

}
