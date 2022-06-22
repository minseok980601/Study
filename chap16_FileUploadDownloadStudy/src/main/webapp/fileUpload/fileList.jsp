<%@page import="java.net.URLEncoder"%>
<%@page import="kr.co.ezenac.fileupload.MyFileDTO"%>
<%@page import="java.util.List"%>
<%@page import="kr.co.ezenac.fileupload.MyFileDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=devide-width, initial-scale=1.0">
  <title>파일 업로드</title>
</head>
<body>
	<h2>DB에 등록된 파일 목록 보기</h2>
	<a href="fileUploadMain.jsp">파일 등록하기</a>
	<%
		MyFileDAO dao = new MyFileDAO();
		List<MyFileDTO> fileLists = dao.myFiileList();
		dao.close();
	%>
		<table border="1">
		<tr>
			<th>No</th>
			<th>작성자</th>
			<th>제목</th>
			<th>카테고리</th>
			<th>원본 파일명</th>
			<th>저장된 파일명</th>
			<th>작성일</th>
			<th></th>
		</tr>
	<%
		for(MyFileDTO f : fileLists) {
	%>
		<tr>
			<td><%= f.getIdx() %></td>
			<td><%= f.getName() %></td>
			<td><%= f.getTitle() %></td>
			<td><%= f.getCate() %></td>
			<td><%= f.getOfile() %></td>
			<td><%= f.getSfile() %></td>
			<td><%= f.getPostdate() %></td>
			<td><a href="download.jsp?oName=<%=URLEncoder.encode(f.getOfile(), "UTF-8") %>&sName=<%=URLEncoder.encode(f.getSfile(), "UTF-8") %>">[다운로드]</a></td>
		</tr>
	<%
		}
	%>
	</table>
</body>
</html>