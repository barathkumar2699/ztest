package com;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model.Users;

/**
 * Servlet implementation class Edit
 */
@WebServlet("/edit")
public class Edit extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Edit() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		 
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String admin = (String) request.getSession().getAttribute("name");
		if(admin==null||!admin.equals("admin"))
		{
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("home.jsp");
			requestDispatcher.forward(request, response);
		}
		try {
			Integer id=Integer.parseInt(request.getParameter("id"));
			System.err.println(id);
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?characterEncoding=utf8","root","root");
			
			PreparedStatement st = connection.prepareStatement("select * from users where id=?");
			st.setInt(1, id);
			Users user=new Users();
			user.setId(id);
			 ResultSet rs = st.executeQuery();
			 while(rs.next()) {
				 String name = rs.getString("username");
				 String password = rs.getString("password");
				 String email = rs.getString("email");
				 String phone = rs.getString("phone");
				 String address = rs.getString("address");
				 user.setUsername(name);
				 user.setPassword(password);
				 user.setPhone(phone);
				 user.setAddress(address);
				 user.setEmail(email);
			 }
			 request.getSession().setAttribute("user", user);
			 System.err.println(user);
			 
			}catch (Exception e) {
				// TODO: handle exception
				System.err.println(e);
			}
		return;
	}

}
