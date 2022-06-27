<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=devide-width, initial-scale=1.0">
  <title>Insert title here</title>
</head>
<body>
	<h2>파일 첨부형 게시판 - 상세 보기(View)</h2>
	<table border="1" width="90%">
		<colgroup>
			<col width="15%" /><col width="35%" /><col width="15%" /><col width="*" />
		</colgroup>
		
		<!-- 게시글 내용 -->
		<tr>
			<td>번호</td> <td>${dto.idx }</td>
			<td>작성자</td> <td>${dto.name }</td>
		</tr>
		<tr>
			<td>작성일</td> <td>${dto.postdate }</td>
			<td>조회수</td> <td>${dto.visitcount }</td>
		</tr>
		<tr>
			<td>내용</td>
			<td colspan="3" height="100">${dto.content }</td>
		</tr>
		
		<!-- 첨부파일 -->
		<tr>
			<td>첨부파일</td>
			<td>
				<c:if test="${not empty dto.ofile }">
					${dto.ofile }
					<a href="../board/download.do?ofile=${dto.ofile }&sfile=${dto.sfile}&idx=${dto.idx}">[다운로드]<</a>
				</c:if>
			</td>
			<td>다운로드</td>
			<td>${dto.download }</td>
		</tr>
		
		<tr>
			<td colspan="4" align="center">
				<button type="button" onclick="location.href='../board/pass.do?mode=edit&idx=${param.idx}'">
					수정
				</button>
				<button type="button" onclick="location.href='../board/pass.do?mode=delete&idx=${param.idx}'">
					삭제
				</button>
				<button type="button" onclick="location.href='../board/list.do'">
					목록보기
				</button>
			</td>
		</tr>
	</table>
</body>
</html>