package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

import application.Main;

public class Superuser implements Serializable {
	
	/**
	 * Class that manages the photo album/
	 * Also takes care of user persistence
	 */
	private static final long serialVersionUID = 1L;
	public static final String storeDir = "dat";
	public static final String storeFile = "users.dat";
	public ArrayList<User> users;
	public User current;
	public boolean loggedIn;

	public Superuser() {
		users = new ArrayList<User>();
		users.add(new User("admin"));
		this.current = null;
		this.loggedIn = false;
	}
	
	
	public void addUser(String username) {
		users.add(new User(username));
	}
	
	public void deleteUser(int index) {
		users.remove(index);
		System.out.println(users);
	}
	
	public void deleteUser(String username) {
		User temp = new User(username);
		users.remove(temp);
	}
	
	public boolean exists(String username) {
		for(User user : users) {
			if(user.getUsername().equals(username)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean checkUser(String user) {
		int index = 0;
		for(int i = 0; i < users.size(); i++) {
			if(users.get(i).getUsername().equals(user)) {
				index = i;
			}
		}
		
		if(index == -1) {
			return false;
		}
		this.setCurrent(users.get(index));
		this.loggedIn = true;
		return true;
		
	}
	
	public int getUserIndex() {
		int index = 0;
		for(User user : users) {
			if(user.getUsername().equals(Main.driver.getCurrent().getUsername())) {
				return index;
			}
			index++;
		}
		return -1;
	}
	
	public ArrayList<User> getUsers(){
		return users;
	}
	
	public User getUser(String username) {
		for(User user : users) {
			if(user.getUsername().equals(username)) {
				return user;
			}
		}
		
		return null;
	}
	
	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}
	
	public User getCurrent() {
		return current;
	}

	public void setCurrent(User current) {
		this.current = current;
	}
	
	public static void save(Superuser pdApp) throws IOException {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storeDir + File.separator + storeFile));
			oos.writeObject(pdApp);
			oos.close();
	}
	
	public static Superuser load() throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storeDir + File.separator + storeFile));
		Superuser userList = (Superuser) ois.readObject();
		ois.close();
		return userList;
		
	}

}
