<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=devide-width, initial-scale=1.0">
  <title>회원 가입</title>
</head>
<body>
	<h3>회원가입</h3>
	<form action="joinMember.jsp" method="post" name="joinFrm">
		아이디 : <input type="text" name="join_id" /><br/>
		비밀번호 : <input type="text" name="join_pw" /><br/>
		이름 : <input type="text" name="join_nm" /><br/>
		<input type="submit" value="가입하기" />
	</form>
</body>
</html>