<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=devide-width, initial-scale=1.0">
  <title>파일 첨부형 게시판</title>
  <script type="text/javascript">
  	function validateForm(form) {
		if (form.name.value == "") {
			alert("작성자를 입력하세요.")
			form.name.focus()
			return false
		}
		
		if (form.title.value == "") {
			alert("제목을 입력하세요.")
			form.title.focus()
			return false
		}
		if (form.content.value == "") {
			alert("내용을 입력하세요.")
			form.content.focus()
			return false
		}
		
		if (form.pass.value == "") {
			alert("비밀번호를 입력하세요.")
			form.pass.focus()
			return false
		}
	}
  </script>
</head>
<body>
	<h2>파일 첨부형 게시판 - 글쓰기</h2>
	<!-- multipart/form-data 첨부파일을 올릴려면 필수로 적용시켜야 한다. -->
	<form action="../board/write.do" name="writeFrm" method="post" enctype="multipart/form-data"
		  onsubmit="return validateForm(this)">
		<table border="1" width="90%">
			<tr>
				<td>작성자</td>
				<td>
					<input type="text" name="name" style="width:150px;" />
				</td>
			</tr>
			<tr>
				<td>제목</td>
				<td>
					<input type="text" name="title" style="width:150px;" />
				</td>
			</tr>
			<tr>
				<td>내용</td>
				<td>
					<textarea name="content" style="width: 90%; height: 100px;"></textarea>
				</td>
			</tr>
			<tr>
				<td>첨부파일</td>
				<td>
					<input type="file" name="ofile" />
				</td>
			</tr>
			<tr>
				<td>비밀번호</td>
				<td>
					<input type="password" name="pass" style="width: 100px;" />
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<button type="submit">작성 완료</button>
					<button type="reset">다시 입력</button>
					<button type="button" onclick="location.href='../board/list.do '">목록 보기</button>
				</td>
			</tr>	
		</table>
	
	</form>
</body>
</html>