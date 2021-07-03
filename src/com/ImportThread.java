package com;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.model.Users;

public class ImportThread extends Thread {

	Users user;
	Connection connection;
	public ImportThread(Users user,Connection connection) {
		this.user=user;
		this.connection=connection;
	}
	
	

	public void run() {
		try {
		PreparedStatement st = connection.prepareStatement("insert into users values(?,?,?,?,?,?)");
		st.setInt(1, user.getId());
		st.setString(2, user.getUsername());
		st.setString(3, user.getPassword());
		st.setString(4, user.getEmail());
		st.setString(5, user.getPhone());
		st.setString(6, user.getAddress());
		boolean rs = st.execute();
		}
		catch (Exception e) {
			// TODO: handle exception
			System.err.println(e);
		}
	}
	
	
}
