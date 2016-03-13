package edu.toronto.csc301.impl;

import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.mrbean.MrBeanModule;

import edu.toronto.csc301.IPost;
import edu.toronto.csc301.ISerializer;
import edu.toronto.csc301.IUser;
import edu.toronto.csc301.Util;

public class Serializer implements ISerializer {
	
	private final ObjectMapper mapper;
	private Map<String, UserRepresentation> UserMap;
	private Map<String, User> MyUserMap;
	private Map<PostKey, Post> MyPostMap;
	private Map<PostKey, PostRepresentation> PostMap;
	private Set<PostRepresentation> GraphPostSet;
	private Queue<IUser> IUserQueue;
	
	// Constructor
	public Serializer() {
		this.mapper = new ObjectMapper();
		this.mapper.findAndRegisterModules();
		this.mapper.registerModule(new MrBeanModule());
		this.UserMap = new HashMap<String, UserRepresentation>();
		this.MyUserMap = new HashMap<String, User>();
		this.MyPostMap = new HashMap<PostKey, Post>();
		this.PostMap = new HashMap<PostKey, PostRepresentation>();
		this.GraphPostSet = new HashSet<PostRepresentation>();
		this.IUserQueue = new LinkedList<IUser>();
	}

	@Override
	public void serialize(IUser user, OutputStream output) throws Exception {
		if (output == null) throw new NullPointerException();
		if (user == null) throw new NullPointerException();
		
		// Represent `user` and all objects it touches
		IUserQueue.add(user);
		while (!IUserQueue.isEmpty()) this.representUser(IUserQueue.poll());
		
		// Represent graph of Post's/User's touched by `user`
		GraphRepresentation graph = new GraphRepresentation();
		graph.setOrigin(user.getUsername());
		graph.setUserMap(this.UserMap);
		graph.setPostMap(this.GraphPostSet);
		
		// Write `graph` to `output`
		this.mapper.writeValue(output, graph);
	}

	@Override
	public IUser deserializeUser(InputStream input) throws Exception {
		if (input == null) throw new NullPointerException();
		
		GraphRepresentation graph = this.mapper.readValue(input, GraphRepresentation.class);
		Iterator<Map.Entry<String, UserRepresentation>> userIt = graph.UserMap.entrySet().iterator();
		Iterator<PostRepresentation> postIt = graph.PostSet.iterator();
		
		
		while (userIt.hasNext()) {
			Map.Entry<String, UserRepresentation> pair = userIt.next();
			String username = pair.getKey();
			UserRepresentation userRepr = pair.getValue();
			this.deRepresentUser(username, userRepr);
		}
		
		while (postIt.hasNext()) {
			PostRepresentation postRepr = postIt.next();
			PostKey postKey = new PostKey();
			postKey.setPostedBy(postRepr.getPostedBy());
			postKey.setPostedAt(postRepr.getPostedAt());
			this.deRepresentPost(postKey, postRepr);
		}
				
		return MyUserMap.get(graph.origin);
	}
	
	private void deRepresentUser(String username, UserRepresentation userRepr) {
		if (MyUserMap.containsKey(username)) return;
		
		User user = new User(username, userRepr.getPassword());
		user.setRegistrationTime(userRepr.getRegistrationTime());
		MyUserMap.put(user.getUsername(), user);
	}
	
	private void deRepresentPost(PostKey postKey, PostRepresentation postRepr) {
		if (!MyPostMap.containsKey(postKey)) {
			User user = MyUserMap.get(postKey.postedBy);
			Post newPost = (Post) user.newPost(Util.byteArrayToImage(postRepr.getProfilePic()), postRepr.getCaption());
			newPost.setPostedAt(postRepr.getPostedAt());
			MyPostMap.put(postKey, newPost);
		}
		
		Post post = MyPostMap.get(postKey);
		Iterator<String> likes = postRepr.getLikes();
		
		while (likes.hasNext()) {
			String uid = likes.next();
			User user = MyUserMap.get(uid);
			post.addLike(user);
		}
		
	}
	
	private void representUser(IUser user) {
		if (UserMap.containsKey(user.getUsername())) return;
		
		// Represent `user`
		UserRepresentation userRepr = new UserRepresentation();
		userRepr.setPassword(user.getPassword());
		userRepr.setRegistrationTime(user.getRegistrationTime());
		
		// Map `user`
		UserMap.put(user.getUsername(), userRepr);
		
		// Represent all posts created by or liked by `user`
		Iterator<IPost> posts = user.getPosts();
		Iterator<IPost> likedPosts = user.getLikes();
		while (posts.hasNext()) this.representPost(posts.next());
		while (likedPosts.hasNext()) this.representPost(likedPosts.next());
	}
	
	private void representPost(IPost post) {
		PostKey postKey = new PostKey();
		postKey.setPostedBy(post.getPostedBy().getUsername());
		postKey.setPostedAt(post.getPostedAt());
		if (PostMap.containsKey(postKey)) return;
		
		// Represent `post`
		PostRepresentation postRepr = new PostRepresentation();
		postRepr.setProfilePic(Util.imageToByteArray(post.getImage()));
		postRepr.setCaption(post.getCaption());
		postRepr.setPostedAt(post.getPostedAt());
		postRepr.setPostedBy(post.getPostedBy().getUsername());
		
		// Add user `post` was posted by
		IUserQueue.add(post.getPostedBy());
		
		// Add all users `post` has been liked by to queue
		Iterator<IUser> users = post.getLikes();
		while (users.hasNext()) {
			IUser u = users.next();
			IUserQueue.add(u);
			postRepr.addLike(u.getUsername());
		}
		
		// Map `post`
		GraphPostSet.add(postRepr);
		PostMap.put(postKey, postRepr);
	}
	
	static class PostKey {
		private String postedBy;
		private LocalDateTime postedAt;
		
		public String getPostedBy() {
			return this.postedBy;
		}
		
		public void setPostedBy(String postedBy) {
			this.postedBy = postedBy;
		}
		
		public LocalDateTime getPostedAt() {
			return this.postedAt;
		}
		
		public void setPostedAt(LocalDateTime postedAt) {
			this.postedAt = postedAt;
		}
		
	    @Override
	    public int hashCode() {
	        final int prime = 31;
	        int result = 1;
	        result = prime * result + postedBy.hashCode()*prime + postedAt.hashCode();  
	        return result;
	    }
		
		@Override
	    public boolean equals(Object obj) {
	        if (this == obj)
	            return true;
	        if (obj == null)
	            return false;
	        if (this.getClass() != obj.getClass())
	            return false;
	        PostKey other = (PostKey) obj;
	        if (this.postedBy != other.postedBy)
	            return false;
	        if (this.postedAt != other.postedAt)
	            return false;
	        return true;
	    }
	}
	
	static class UserRepresentation {
		private String password;
		private LocalDateTime registrationTime;
		
		public String getPassword() {
			return this.password;
		}
		
		public void setPassword(String password) {
			this.password = password;
		}
		
		public LocalDateTime getRegistrationTime() {
			return this.registrationTime;
		}
		
		public void setRegistrationTime(LocalDateTime registrationTime) {
			this.registrationTime = registrationTime;
		}
	}
	
	static class GraphRepresentation {
		private String origin;
		private Map<String, UserRepresentation> UserMap;
		private Set<PostRepresentation> PostSet;
		
		public GraphRepresentation() {
			this.UserMap = new HashMap<String, UserRepresentation>();
			this.PostSet = new HashSet<PostRepresentation>();
		}
		
		public String getOrigin() {
			return this.origin;
		}
		
		public void setOrigin(String origin) {
			this.origin = origin;
		}
		
		public Map<String, UserRepresentation> getUserMap() {
			return this.UserMap;
		}
		
		public void setUserMap(Map<String, UserRepresentation> UserMap) {
			this.UserMap = UserMap;
		}
		
		public Set<PostRepresentation> getPostSet() {
			return this.PostSet;
		}
		
		public void setPostMap(Set<PostRepresentation> PostSet) {
			this.PostSet = PostSet;
		}
	}
	
	
	static class PostRepresentation {
		private byte[] profilePic;
		private String caption;
		private LocalDateTime postedAt;
		private HashSet<String> likes;
		private String postedBy;
		
		public PostRepresentation() {
			this.likes = new HashSet<String>();
		}
		
		public byte[] getProfilePic() {
			return this.profilePic;
		}
		
		public void setProfilePic(byte[] profilePic) {
			this.profilePic = profilePic;
		}
		
		public String getCaption() {
			return this.caption;
		}
		
		public void setCaption(String caption) {
			this.caption = caption;
		}
		
		public LocalDateTime getPostedAt() {
			return this.postedAt;
		}
		
		public void setPostedAt(LocalDateTime postedAt) {
			this.postedAt = postedAt;
		}
		
		public Iterator<String> getLikes() {
			return likes.iterator();
		}

		public void addLike(String uid) {
			if (this.likes.contains(uid)) return;
			likes.add(uid);
		}

		public void removeLike(String uid) {
			if (!this.likes.contains(uid)) return;
			likes.remove(uid);
		}
		
		public String getPostedBy() {
			return this.postedBy;
		}
		
		public void setPostedBy(String postedBy) {
			this.postedBy = postedBy;
		}
		
	}

}


