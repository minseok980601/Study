<%@page import="kr.co.ezenac.fileupload.MyFileDAO"%>
<%@page import="kr.co.ezenac.fileupload.MyFileDTO"%>
<%@page import="java.io.File"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.oreilly.servlet.MultipartRequest"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String saveDirectory = application.getRealPath("/Uploads");		// 저장될 디렉토리
int maxPostSize = 1024 * 1000;									// 파일 최대 크기(1MB)
String encoding = "utf-8";										// 인코딩 방식

try {
	// 1. MultipartRrequest 객체생성
	MultipartRequest mr = new MultipartRequest(request, saveDirectory, maxPostSize, encoding);
	
	
	// 2. 새로운 파일명 생성 (업로드일시. 확장자)
	String fileName = mr.getFilesystemName("attachedfile");			// 현재 파일 이름
	String ext = fileName.substring(fileName.lastIndexOf("."));		// 파일 확장자
	// 현재 날짜와 시간을 구함
	String now = new SimpleDateFormat("yyyyMMdd_HmsS").format(new Date());
	String newFileName = now + ext;		// 새로운 파일 이름 ("업로드일시. 확장자")
	
	// 3. 파일명 변경 
	// File.separator : 경로 구부하는 특수 기호를 뜻함. OS에 따라 경로 표현 방법이 다름. 환경에 상관없이 코드 동작하게 함.
	File oldFile = new File(saveDirectory + File.separator + fileName); 
	File newFile = new File(saveDirectory + File.separator + newFileName);
	
	oldFile.renameTo(newFile);		// 파일이름 변경
	
	// 4. 다른 폼값 처리
	String name = mr.getParameter("name");
	String title = mr.getParameter("title");
	
	String[] cateArray = mr.getParameterValues("cate");
	
	StringBuffer cateBuf = new StringBuffer();
	if (cateArray == null) {
		cateBuf.append("선택 없음");
	}
	else {
		for(String s : cateArray) {
			cateBuf.append(s + ", ");
		}
	}
	
	// 5. DTO 생성
	MyFileDTO dto = new MyFileDTO();
	dto.setName(name);
	dto.setTitle(title);
	dto.setCate(cateBuf.toString());
	dto.setOfile(fileName);
	dto.setSfile(newFileName);
	
	// 6.DAP 생성
	MyFileDAO dao = new MyFileDAO();
	dao.insertFile(dto);
	dao.close();
	
	response.sendRedirect("fileList.jsp");
} catch(Exception e) {
	e.printStackTrace();
	request.setAttribute("errorMessage", "파일 업로드 오류");
	request.getRequestDispatcher("fileUploadMain.jsp").forward(request, response);
}
%>