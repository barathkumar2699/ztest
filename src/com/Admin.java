package com;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Admin
 */
@WebServlet("/create")
public class Admin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Admin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String admin = (String) request.getSession().getAttribute("name");
		
		if(admin==null||!admin.equals("admin"))
		{
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("home.jsp");
			requestDispatcher.forward(request, response);
		}
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("user.jsp");
		requestDispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?characterEncoding=utf8","root","root");
			String username=(String) request.getParameter("username");
			String password=(String)request.getParameter("password");
			String email=(String)request.getParameter("email");
			String phone=(String)request.getParameter("phone");
			String address=(String)request.getParameter("address");
			Integer id=new Random().nextInt();
			PreparedStatement st = connection.prepareStatement("insert into users values(?,?,?,?,?,?)");
			st.setInt(1, id);
			st.setString(2, username);
			st.setString(3, password);
			st.setString(4, email);
			st.setString(5, phone);
			st.setString(6, address);
			 boolean rs = st.execute();
			 System.err.println(rs);
			 if(!rs) {
				 request.getSession().setAttribute("userCreated", "User Created Successfully");
				 response.sendRedirect(request.getContextPath() + "/create");
				 //response.getWriter().append("User created").append(request.getContextPath());
			 }
			 else {
				// response.getWriter().append("Usernot created").append(request.getContextPath());
			 }
			}catch (Exception e) {
				// TODO: handle exception
				System.err.println(e);
				response.getWriter().append("User already existor problem").append(request.getContextPath());
			}
	}

}
