package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dao.UserLocal;
import entities.Phone;
import entities.User;

@WebServlet("/UserController")
public class UserController extends HttpServlet {
	@EJB
	private UserLocal service;

	public UserController() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getParameter("op") == null) {
			String email = request.getParameter("email");
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String birthdate = request.getParameter("birthdate").replace("-", "/");
			service.create(new User(name, new Date(birthdate), email, password));
		}else {
			if(request.getParameter("op").toString().equals("getphones")) {
				User u = service.findById(Integer.valueOf(request.getParameter("user").toString()));
				List<Phone> phones = u.getPhones();
				List<String> imeis = new ArrayList<String>();
				for(Phone p : phones) {
					imeis.add(p.getImei());
				}
				response.setContentType("application/json");
				Gson json = new Gson();
				response.getWriter().write(json.toJson(imeis));
				return;
			}
		}
		response.setContentType("application/json");
		List<User> users = service.findAll();
		List<User> res = new ArrayList<User>();
		for(User u : users) {
			res.add(new User(u.getId(), u.getName(), u.getBirthDate(), u.getEmail(), u.getPassword()));
		}
		Gson json = new Gson();
		response.getWriter().write(json.toJson(res));

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	
}
