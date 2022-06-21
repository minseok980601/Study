<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=devide-width, initial-scale=1.0">
  <title>파일 첨부형 게시판</title>
</head>
<body>
	<h2>파일 첨부형 게시판 - 목록 보기(List)</h2>
	
	<form action="#" method="get" accept-charset="UTF-8" >
		<!-- 검색 -->
		<table border="1" width="90%">
			<tr>
				<td align="center">
					<select name="searchField">
						<option value="title">제목</option>
						<option value="content">내용</option>
					</select>
					<input type="text" name="searchWord" />
					<input type="submit" value="검색하기" />
				</td>
			</tr>
		</table>
		
		<!-- 게시물 목록 테이블 -->
		<table border="1" width="90%">
			<!-- 컬럼이름 -->
			<tr>
				<th width="10%">번호</th>
				<th width="*">제목</th>
				<th width="15%">작성자</th>
				<th width="10%">조회수</th>
				<th width="15%">작성일</th>
				<th width="8%">첨부</th>
			</tr>
			<c:choose>
				<c:when test="${empty boardLists }">	<!-- 게시물이 없을 때 -->
					<tr>
						<td colspan="6" align="center">
							등록된 게시물이 없습니다!
						</td>
					</tr>
				</c:when>
				<c:otherwise>	<!-- 게시물이 있을 때 -->
					<c:forEach items="${boardLists }" var="row" varStatus="loop">
						<tr align="center">
							<td>	<!-- 가상번호 -->
								<%-- 전체 게시물 수 - (((현재페이지 번호-1) * 페이지 사이즈) + varStatus의 index값)
									 17 - ((1-1) * 10) + 0) = 17
									 17 - ((2-1) * 10) + 0) = 7 
								--%>
								${map.totalCount - (((map.pageNum - 1) * map.pageSize) + loop.index ) }
							</td>
							<td align="left">			<!-- 제목 (링크) -->
								<a href="../board/view.do?idx=${row.idx }">${row.title }</a>
							</td>
							<td>${row.name }</td>			<!-- 작성자 -->
							<td>${row.visitcount }</td>		<!-- 조회수 -->
							<td>${row.postdate }</td>		<!-- 작성일 -->
							<td>
								<c:if test="${not empty row.ofile }">
									<a href="../board/download.do?ofile=${row.ofile }&sfile=${row.sfile}&idx=${row.idx}">[Down]</a>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</table>
		
		<!-- 하단 메뉴(페이징, 글쓰기) -->
		<table border="1" width="90%">
			<tr align="center">
				<td>${map.pagingImag }</td>
				<td width="100">
					<button type="button" onclick="location.href='../board/write.do ';">글쓰기</button>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>