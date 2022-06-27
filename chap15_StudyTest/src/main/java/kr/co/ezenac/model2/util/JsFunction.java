package kr.co.ezenac.model2.util;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;

public class JsFunction {
	// 메시지 알림창을 띄우고 명시된 URL로 이동함(ex. 로그인에 성공했습니다.)
	public static void alertLocation(String msg, String url, JspWriter out) {
		try {
			String script = ""
						  + "<script>"
						  + "		alert( '" + msg + "' );"
						  + "		location.href = '" + url + "'; "
						  + "</script>";
			out.print(script);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public static void alertLocation(HttpServletResponse response, String msg, String url) {
		try {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			String script = ""
						  + "<script>"
						  + "		alert( '" + msg + "' );"
						  + "		location.href = '" + url + "'; "
						  + "</script>";
			out.print(script);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
