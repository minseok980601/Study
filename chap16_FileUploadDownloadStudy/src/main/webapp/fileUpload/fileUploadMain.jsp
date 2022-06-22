<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=devide-width, initial-scale=1.0">
  <title>fileUpload</title>
  <script type="text/javascript">
  	function validateFrom(this) {
		if (form.name.value == "") {
			alert("작성자를 입력하세요.")
			form.name.focus();
			return false;
		}
		if (form.title.value == "") {
			alert("제목 입력하세요.")
			form.title.focus();
			return false;
		}
		if (form.attachedfile.value == "") {
			alert("첨부파일 선택은 필수입니다.")
			form.attachedfile.focus();
			return false;
		}
	}
  </script>
</head>
<body>
	<h3>파일 업로드</h3>
	<span style="color: red;">${errorMessage }</span>
	<form action="UploadProcess.jsp" name="fileForm" method="post" enctype="multipart/form-data"
		  onsubmit="return validateForm(this)">
		작성자 : <input type="text" name="name" value="이젠IT" /><br/>
		제목 : <input type="text" name="title"> <br/>
		카테고리(선택사항) :
			<input type="checkbox" name="cate" value="사진" checked>사진
			<input type="checkbox" name="cate" value="과제" >과제
			<input type="checkbox" name="cate" value="워드" >워드
			<input type="checkbox" name="cate" value="음원" >워드<br/>
		첨부파일 : <input type="file" name="attachedfile" /><br/>
		<input type="submit" value="전송하기" />			  
	</form>
</body>
</html>