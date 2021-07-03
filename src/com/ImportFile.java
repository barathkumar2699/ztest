package com;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.json.simple.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.model.Users;




/**
 * Servlet implementation class ImportFile
 */
@WebServlet("/import")
@MultipartConfig
public class ImportFile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImportFile() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    private void extcall(Users user) {
		// TODO Auto-generated method stub
    	try {
    		String url="https://dev115002.service-now.com/api/now/v2/table/sys_user?sysparm_input_display_value=true";
    		RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			String auth = "admin" + ":" + "kFSlYhjw8BO7";
	         byte[] encodedAuth = Base64.getEncoder().encode( 
	            auth.getBytes(Charset.forName("US-ASCII")) );
	         String authHeader = "Basic " + new String( encodedAuth );
	         headers.add("Accept", "application/json");
	         headers.add("Content-Type", "application/json");
		    headers.add("Authorization", authHeader);    
		    JSONObject request= new JSONObject();
		    request.put("user_password", user.getPassword());
		    request.put("phone", user.getPhone());
		    request.put("user_name", user.getUsername());
		    request.put("email", user.getEmail());
		    request.put("state", user.getAddress());
		    HttpEntity<String> r = new HttpEntity<String>(request.toString(), headers);
		    System.out.println(r.toString());
		    restTemplate.postForObject(url, r, String.class);
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e);
			//ResponseEntity<String> result = restTemplate.getForEntity("", String.class);
		}
    	
		
	}
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
    	    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    	public static boolean validateEmail(String emailStr) {
    	        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
    	        return matcher.find();
    	}
    	public static boolean validatePhone(String phone) {
    		Pattern pattern = Pattern.compile("^\\d{10}$");
    	    Matcher matcher = pattern.matcher(phone);
    	    
    	    return matcher.matches();
    	}
    
    private List<Users> getTextFromPart(Part part) throws IOException {
    	List<Users> list=new ArrayList<>();
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(part.getInputStream(), "UTF-8"));
        StringBuilder value = new StringBuilder();
        char[] buffer = new char[2048];
        String thisLine = null;
        while ((thisLine = reader.readLine()) != null) {
        	System.err.println(thisLine);
            String[] split = thisLine.split(",");
            boolean contains = split[0].contains("username");
            Integer id=new Random().nextInt();
          String username=split[0],password=split[1],email=split[2],phone=split[3],address=split[4];
          Users user=new Users(id, username, password, email, phone, address);  
          System.err.println(user+":"+contains);
          if(!contains) {        	  
        	  list.add(user);
          }
         }
        return list;
     
//        for (int length = 0; (length = reader.read(buffer)) > 0; ) {
//            value.append(buffer, 0, length);
//            String[] split = value.toString().split(",");
//            boolean contains = split[0].contains("username");
//            System.out.print(contains);
//			Integer id=new Random().nextInt();
//            String username=split[0],password=split[1],email=split[2],phone=split[3],address=split[4];
//            Users user=new Users(id, username, password, email, phone, address);      	
//            System.err.println(user);
//            
//            
//        }
        
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
		//doGet(request, response);
		try {
		System.out.println("Parts: " + request.getParts().toString());
		System.out.println("Part 1: " + request.getPart("file"));
		Part part = request.getPart("file");
		List<Users> list = getTextFromPart(part);
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?characterEncoding=utf8","root","root");
//		for(Users user:list) {
//			System.out.println(user);
//			PreparedStatement st = connection.prepareStatement("insert into users values(?,?,?,?,?,?)");
//			st.setInt(1, user.getId());
//			st.setString(2, user.getUsername());
//			st.setString(3, user.getPassword());
//			st.setString(4, user.getEmail());
//			st.setString(5, user.getPhone());
//			st.setString(6, user.getAddress());
//			boolean rs = st.execute();
//			 System.err.println(rs);
//			 
//		}
		/*int n=list.size(),i;
		for( i=0;i<list.size();i++) {
			Thread thread1 = null,thread2 = null,thread3 = null;
			
			 thread1 = new ImportThread(list.get(i), connection);
			 thread1.start();
			i++;
			if(i<n) {
				 thread2 = new ImportThread(list.get(i), connection);
				thread2.start();
			}
			
			 i++;
			 if(i<n) {
				 thread3 = new ImportThread(list.get(i), connection);
				 thread3.start();
			 }
		}
			 */
		
//		ExecutorService pool = Executors.newFixedThreadPool(5);
//		CompletableFuture<List<Users>> completableFuture
//		  = CompletableFuture.supplyAsync(() -> {
//			  List<Users> fail=new ArrayList<>();
//			  
//				  for(Users user:list) {
//					 
//					  try {
//					PreparedStatement st = connection.prepareStatement("insert into users values(?,?,?,?,?,?)");
//					st.setInt(1, user.getId());
//					st.setString(2, user.getUsername());
//					st.setString(3, user.getPassword());
//					st.setString(4, user.getEmail());
//					st.setString(5, user.getPhone());
//					st.setString(6, user.getAddress());
//					boolean rs = st.execute();
//					}
//					catch (Exception e) {
//						// TODO: handle exception
//						System.err.println(e);
//						fail.add(user);
//					}
//				  }
//				  return fail;
//		  },pool);
		
		Callable<JSONObject> task = () -> {
			List<Users> fail=new ArrayList<>();
			JSONObject obj = new JSONObject();
				  for(Users user:list) {
					 
					  try {
					PreparedStatement st = connection.prepareStatement("insert into users values(?,?,?,?,?,?)");
					st.setInt(1, user.getId());
					st.setString(2, user.getUsername());
					st.setString(3, user.getPassword());
					st.setString(4, user.getEmail());
					st.setString(5, user.getPhone());
					st.setString(6, user.getAddress());
					if(!validateEmail(user.getEmail()))
						throw new Exception("Invalid email format '"+user.getEmail()+"'");
					if(!validatePhone(user.getPhone()))
						throw new Exception("Invalid phone format '"+user.getPhone()+"'");
					
					extcall(user);
					boolean rs = st.execute();
					}
					catch (Exception e) {
						// TODO: handle exception
						
						System.err.println(e);
						obj.put(user, e.getMessage());
						//fail.add(user);
					}
				  }
				  if(obj.size()==0)
				  obj.put(1, "No Logs");
				  return obj;
		};
		ExecutorService executorService= Executors.newScheduledThreadPool(5);
		   Future future = executorService.submit(task);
		System.out.println("value - "+future.get()); //returns task
		
		request.getSession().setAttribute("failed", future.get());
			 
		
			
		
		 request.getSession().setAttribute("userCreated", "User Imported Successfully");
		 response.sendRedirect(request.getContextPath() + "/create");
		}
		catch (Exception e) {
			// TODO: handle exception
			System.err.println(e);
			response.getWriter().append("User already existor problem").append(request.getContextPath());
		}
		return ;
		
	}
	

}
