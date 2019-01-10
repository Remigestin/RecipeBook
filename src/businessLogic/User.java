package businessLogic;

import java.util.ArrayList;

/**
 * @author gesti
 *
 */
public class User {
	
	// Properties
	
	private int id;
    private String username;
    private String password;
    private String lastname;
    private String firstname;
    private boolean isAdmin;
    private static User session;
    private ArrayList<Recipe> createList;
    private ArrayList<Recipe> favoriteList;
   
	//Getters/setters
	
	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	public static User getSession() {
        return session;
    }

    public static void setSession(User user) {
        session = user;
    }

	public ArrayList<Recipe> getCreateList() {
		return createList;
	}

	public void setCreateList(ArrayList<Recipe> createList) {
		this.createList = createList;
	}

	public ArrayList<Recipe> getFavoriteList() {
		return favoriteList;
	}

	public void setFavoriteList(ArrayList<Recipe> favoriteList) {
		this.favoriteList = favoriteList;
	}
	
}
