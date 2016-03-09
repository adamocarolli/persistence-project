package edu.toronto.csc301.impl;

import java.awt.image.RenderedImage;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Iterator;

import edu.toronto.csc301.IPost;
import edu.toronto.csc301.IUser;;

public class User implements IUser {
	
	private String username;
	private String password;
	private LocalDateTime registrationTime;
	private HashSet<IPost> posts;
	private HashSet<IPost> likes;

	public User(String username, String password) {
		this.username = username;
		this.password = password;
		this.registrationTime = LocalDateTime.now();
		this.posts = new HashSet<IPost>();
		this.likes = new HashSet<IPost>();
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public LocalDateTime getRegistrationTime() {
		return this.registrationTime;
	}

	@Override
	public void setRegistrationTime(LocalDateTime registrationTime) {
		this.registrationTime = registrationTime;
	}

	@Override
	public IPost newPost(RenderedImage image, String caption) {
		if (image == null && caption == null) throw new IllegalArgumentException();
		IPost post = new Post(image, caption);
		post.setPostedBy(this);
		posts.add(post);
		return post;
	}

	@Override
	public Iterator<IPost> getPosts() {
		return this.posts.iterator();
	}

	@Override
	public void like(IPost post) {
		if (this.likes.contains(post)) return;
		this.likes.add(post);
		post.addLike(this);
	}

	@Override
	public void unlike(IPost post) {
		if (!this.likes.contains(post)) return;
		this.likes.remove(post);
		post.removeLike(this);
	}

	@Override
	public Iterator<IPost> getLikes() {
		return this.likes.iterator();
	}

}
