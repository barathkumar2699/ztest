package com;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
 * Servlet implementation class SearchUser
 */
@WebServlet("/search")
public class SearchUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchUser() {
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
		List<Users> users =new ArrayList<>();
		String user = request.getParameter("username");
		System.out.println(user);
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?characterEncoding=utf8","root","root");
			PreparedStatement st = connection.prepareStatement("select * from users where username like '%"+user+"%'");
			
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				Integer id=Integer.parseInt(rs.getString("id"));
				String username=rs.getString("username");
				String password=rs.getString("password");
				String email=rs.getString("email");
				String phone=rs.getString("phone");
				String address=rs.getString("address");
				users.add(new Users(id, username,password,email,phone,address));
			}
			request.getSession().setAttribute("users", users);
			
			
	
		}catch (Exception e) {
			// TODO: handle exception
			System.err.println(e);
		}
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("admin.jsp");
		requestDispatcher.forward(request, response);
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

}
