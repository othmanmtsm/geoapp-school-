package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dao.PhoneLocal;
import entities.Phone;
import entities.Position;

@WebServlet("/PhoneController")
public class PhoneController extends HttpServlet {
	
@EJB
private PhoneLocal service;

public PhoneController() {
	super();
}

protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
	if (request.getParameter("op") == null) {
		String imei = request.getParameter("imei");
		int user = Integer.valueOf(request.getParameter("user"));
		service.create(new Phone(imei), user);
	}else {
		if(request.getParameter("op").toString().equals("getpos")) {
			String imei = request.getParameter("imei").toString();
			String date1 = request.getParameter("date1").replace("-", "/");
			String date2 = request.getParameter("date2").replace("-", "/");
			Phone p = service.findByImei(imei);
			List<Position> pos = new ArrayList<Position>();
			for(Position po : p.getPositions()) {
				if( po.getDate().after(new Date(date1)) && po.getDate().before(new Date(date2)) ) {
					pos.add(new Position(po.getLatitude(), po.getLongitude(), po.getDate()));
				}
			}
			response.setContentType("application/json");
			Gson json = new Gson();
			response.getWriter().write(json.toJson(pos));
			return;
		}
		if(request.getParameter("op").toString().equals("getnbpos")) {
			List<Object[]> nbpos = service.nbpositions();
			response.setContentType("application/json");
			Gson json = new Gson();
			response.getWriter().write(json.toJson(nbpos));
			return;
		}
		if(request.getParameter("op").toString().equals("getnbpospermonth")) {
			int m = Integer.valueOf(request.getParameter("month").toString());
			List<Object[]> nbpos = service.nbpositions_per_month(m);
			response.setContentType("application/json");
			Gson json = new Gson();
			response.getWriter().write(json.toJson(nbpos));
			return;
		}
	}
	response.setContentType("application/json");
	List<Phone> phones = service.findAll();
	List<String> imeis = new ArrayList<String>();
	for(Phone p : phones) {
		imeis.add(p.getImei());
	}
	Gson json = new Gson();
	response.getWriter().write(json.toJson(imeis));
}


protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
	// TODO Auto-generated method stub
	doGet(request, response);
}
}
