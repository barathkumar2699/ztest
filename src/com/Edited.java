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

/**
 * Servlet implementation class Edited
 */
@WebServlet("/edited")
public class Edited extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Edited() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String pathInfo = request.getPathInfo();
		String username=(String) request.getParameter("username");
		String password=(String)request.getParameter("password");
		String email=(String)request.getParameter("email");
		String phone=(String)request.getParameter("phone");
		String address=(String)request.getParameter("address");
		String id=(String)request.getParameter("id");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?characterEncoding=utf8","root","root");
			
			PreparedStatement st = connection.prepareStatement("update users set username=?,password=?,email=?,phone=?,address=? where id=?");
			st.setString(1, username);
			st.setString(2, password); 
			st.setString(3, email);
			st.setString(4, phone); 
			st.setString(5, address);
			
			st.setString(6, id); 
			int rs = st.executeUpdate();
			 if(rs==1) {
				 request.getSession().setAttribute("userDeleted", "User Updated Successful");
				 response.sendRedirect(request.getContextPath() + "/Login");
			 }
			 else {
				 System.err.println("ome issue");
			 }
			}catch (Exception e) {
				// TODO: handle exception
				System.err.println(e);
			}
	}

}
