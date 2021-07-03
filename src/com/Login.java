package com;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model.Users;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

    public List<Users> fetchUsers(Connection connection){
    	try {
    	PreparedStatement st2 = connection.prepareStatement("select * from users");
		 ResultSet rs = st2.executeQuery();
		 List<Users> users =new ArrayList<>();
		 while(rs.next()) {
			 String username = rs.getString("username");
			 String password = rs.getString("password");
			 String email = rs.getString("email");
			 String phone = rs.getString("phone");
			 String address = rs.getString("address");
			 Integer id=Integer.parseInt(rs.getString("id"));
			 users.add(new Users(id, username,password,email,phone,address));
		 }
		 return users;
    	}
    	catch (Exception e) {
			// TODO: handle exception
		}
		return null;
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		String admin = (String) request.getSession().getAttribute("name");
		if(admin==null||!admin.equals("admin"))
		{
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("home.jsp");
			requestDispatcher.forward(request, response);
		}
		
		
		
		doPost(request,response);
			}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String username=(String) request.getParameter("username");
		String password=(String)request.getParameter("password");
		String name = (String)request.getSession().getAttribute("name");
		if(username==null&&name.equals("admin")) {
			username="admin";
			password="admin";
		}
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?characterEncoding=utf8","root","root");
			PreparedStatement st = connection.prepareStatement("select * from users where username=? and password=?");
			st.setString(1, username);
			st.setString(2, password);
			 ResultSet rs = st.executeQuery();
			 while(rs.next()) {
				 String dbusername = rs.getString("username");
				 String dbpasword = rs.getString("password");
				 request.getSession().setAttribute("name", dbusername);
				 if(dbusername.equals("admin")&&password.equals("admin"))
				 {
					 //admin logic
					 List<Users> users = fetchUsers(connection);
					 request.getSession().setAttribute("users", users);
					 RequestDispatcher requestDispatcher = request.getRequestDispatcher("admin.jsp");
						requestDispatcher.forward(request, response);
					
				 }
				 else {
					 RequestDispatcher requestDispatcher = request.getRequestDispatcher("welcome.jsp");
						requestDispatcher.forward(request, response);
				 }
				 
			 }
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.err.println(username+":"+password);
		request.setAttribute("error", "Invalid credentials");
		return ;
		//response.getWriter().append("Logged in").append(request.getContextPath());

	}

}
