<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=devide-width, initial-scale=1.0">
  <title>Session</title>
  <script type="text/javascript">
  		function validateForm(form) {
			if (!form.user_id.value) {
				alert("아이디를 입력하세요.")
				return false
			}
			if (!form.user_pw.value) {
				alert("비밀번호를 입력하세요.")
				return false
			}
		}
  		
  		function joinMember() {
			location.href="joinForm.jsp"
		}
  </script>
</head>
<body>
	<h2>로그인 페이지</h2>
	<span style="color: red; font-size: 1.2em;">
	</span>
	
	<%
		// 로그인 상태 확인
		// 사용자 아이디가 저장되어 있는지 확인
		if (session.getAttribute("UserId") == null) {
			// 저장되지 않은 것 => 로그아웃 상태 뜻함
		
	%>
	<form action="loginProcess.jsp" method="post" name="loginFrm"
		  onsubmit="return validateForm(this)">
		아이디 : <input type="text" name="user_id" /><br/>
		비밀번호 : <input type="password" name="user_pw" /><br/>
		<input type="submit" value="로그인하기" />
		<input type="button" value="회원가입하기" onclick="joinMember()">
	</form>
	<%
		}
		else {		// 로그인 한 상태
	%>
		<%=	session.getAttribute("UserName") %> 회원님, 로그인 하셨습니다. <br/>
		<a href="logout.jsp">[로그아웃]</a>
	<%
		}
	%>
</body>
</html>