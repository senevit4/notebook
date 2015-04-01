package com.epam.wt.view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

import com.epam.wt.command.CommandName;
import com.epam.wt.controller.Controller;
import com.epam.wt.controller.Request;
import com.epam.wt.controller.RequestParam;
import com.epam.wt.controller.Response;
import com.epam.wt.controller.ResponseParam;

public class View {
	private static Scanner sc1 = new Scanner(System.in);
	private static Scanner sc2 = new Scanner(System.in);
	private Controller con = new Controller();
	private static Locale loc;
	private static ResourceBundle bundle;

	private static int showMenu() {
		bundle=ResourceBundle.getBundle("properties.menu", loc);
		System.out.println(bundle.getString("an"));
		System.out.println(bundle.getString("atn"));
		System.out.println(bundle.getString("fbi"));
		System.out.println(bundle.getString("dbi"));
		System.out.println(bundle.getString("da"));
		System.out.println(bundle.getString("san"));
		System.out.println(bundle.getString("sn"));
		System.out.println(bundle.getString("e"));
		int result = sc1.nextInt();
		return result;
	}

	public void getLocale() {
		System.out.println("Enter 0 for EN, 1 for RU:");
		int result = sc1.nextInt();
		switch (result) {
		case 0:
			loc = new Locale("en");
			break;
		case 1:
			loc = new Locale("ru");
			break;
		default:
			System.out.println("Incorrect input, try again.");
			getLocale();
			break;
		}
	}

	public void run() {
		int _do = -1;
		while (_do != 0) {
			_do = showMenu();
			Request req;
			Response resp;
			switch (_do) {
			case 1:
				req = prepareParamsForAddNote();
				resp = this.con.doRequest(CommandName.ADD, req);
				printResultAddNote(resp);
				break;
			case 2:
				req = prepareParamsForAddTopicNote();
				resp = this.con.doRequest(CommandName.ADD, req);
				printResultAddNote(resp);
				break;
			case 3:
				req = prepareParamsForFindById();
				resp = this.con.doRequest(CommandName.FIND, req);
				printResultFind(resp);
				break;
			case 4:
				req = prepareParamsForDeleteById();
				resp = this.con.doRequest(CommandName.DETETE, req);
				printResultDeleteById(resp);
				break;
			case 5:
				resp = this.con.doRequest(CommandName.DELETE_ALL, null);
				printResultDeleteAll(resp);
				break;
			case 6:
				resp = this.con.doRequest(CommandName.SHOW, null);
				printResultShow(resp);
				break;
			case 7:
				resp = this.con.doRequest(CommandName.SORT, null);
				printResultSort(resp);
				break;
			case 0:
				_do = 0;
				break;

			}
		}

	}

	private void printResultSort(Response response) {
		System.out.println(response.getParam(ResponseParam.LIST_OF_NOTES
				.toString()));
	}

	private void printResultShow(Response response) {
		System.out.println(response.getParam(ResponseParam.LIST_OF_NOTES
				.toString()));
	}

	private void printResultDeleteAll(Response response) {
		System.out.println(bundle.getString("aond")
				+ response.getParam(ResponseParam.NUMBER.toString()));
	}

	private Request prepareParamsForDeleteById() {
		String id = sc2.nextLine();
		Request request = new Request();
		request.setParam(RequestParam.ID.toString(), id);
		return request;
	}

	private void printResultDeleteById(Response response) {
		System.out.println(response.getParam(ResponseParam.LIST_OF_NOTES
				.toString()));
	}

	private Request prepareParamsForFindById() {
		String id = sc2.nextLine();
		Request request = new Request();
		request.setParam(RequestParam.ID.toString(), id);
		return request;
	}

	private void printResultFind(Response response) {
		System.out.println(response.getParam(ResponseParam.NOTE.toString()));
	}

	private Request prepareParamsForAddNote() {
		String note = sc2.nextLine();
		String date = sc2.nextLine();
		Request request = new Request();
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		request.setParam(RequestParam.RECORD.toString(), note);
		try {
			request.setParam(RequestParam.DATE.toString(), sdf.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return request;
	}

	private Request prepareParamsForAddTopicNote() {
		String note = sc2.nextLine();
		String topic = sc2.nextLine();
		String date = sc2.nextLine();
		Request request = new Request();
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		request.setParam(RequestParam.RECORD.toString(), note);
		request.setParam(RequestParam.TOPIC.toString(), topic);
		try {
			request.setParam(RequestParam.DATE.toString(), sdf.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return request;
	}

	private void printResultAddNote(Response response) {
		System.out.println(response.getParam(ResponseParam.LIST_OF_NOTES
				.toString()));
	}

	public static void main(String[] args) {
		try{
		View v = new View();
		v.getLocale();
		v.run();}
		catch(Exception e){
			System.out.println("CRITICAL ERROR!!!");
		}

	}

}
