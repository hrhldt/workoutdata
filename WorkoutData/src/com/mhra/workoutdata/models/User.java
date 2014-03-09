package com.mhra.workoutdata.models;

public class User {
	public String name;
	public String lastName;
	
   private static User instance = null;
   protected User() {
      // Exists only to defeat instantiation.
   }
   public static User getInstance() {
      if(instance == null) {
         instance = new User();
      }
      return instance;
   }
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
}
