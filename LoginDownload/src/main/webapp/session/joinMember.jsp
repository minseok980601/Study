<%@page import="kr.co.ezenac.membership.MemberDTO"%>
<%@page import="kr.co.ezenac.membership.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String joinId = request.getParameter("join_id");
	String joinPw = request.getParameter("join_pw");
	String joinNm = request.getParameter("join_nm");
	
	// web.xml에서 가져온 DB 연결 정보
	String oracleDriver = application.getInitParameter("OracleDriver");
	String oracleURL = application.getInitParameter("OracleURL");
	String oracleId = application.getInitParameter("OracleId");
	String oraclePwd = application.getInitParameter("OraclePwd");

	MemberDAO dao = new MemberDAO(oracleDriver, oracleURL, oracleId, oraclePwd);
 	MemberDTO dto = new MemberDTO();
	dto.setId(joinId);
	dto.setPass(joinPw);
	dto.setName(joinNm); 
	
	dao.joinMember(dto);
	dao.close();
	
	// 회원가입 성공 여부 처리
	if (dto.getId() != null || dto.getPass() != null || dto.getName() != null) {
		session.setAttribute("join_id", dto.getId());
		session.setAttribute("UserPw", dto.getPass());
		session.setAttribute("UserNm", dto.getName());
		response.sendRedirect("loginForm.jsp");
	} 
	
%>
