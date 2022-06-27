package kr.co.ezenac.model2.board;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;

import kr.co.ezenac.model2.util.FileUtil;
import kr.co.ezenac.model2.util.JsFunction;



public class WriteController extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 글쓰기 폼으로 진입
		request.getRequestDispatcher("/board/write.jsp").forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. 파일 업로드 처리
		// 업로드 시 디렉토리 물리적 경로
		String saveDirectory = request.getServletContext().getRealPath("/Uploads");
		
		// (초기화 매개변수)첨부 파일 최대 용량 확인
		ServletContext application = getServletContext();
		// web.xml에 컨텍스트 초기화 매개변수로 설정해둔 업로드 제한 용량 얻어옴
		int maxPostSize = Integer.parseInt(application.getInitParameter("maxPostSize"));
		
		// 파일 업로드
		MultipartRequest mr = FileUtil.uploadFile(request, saveDirectory, maxPostSize);
		// 파일 업로드 실패시 경고창 띄어주고 작성 페이지 이동
		if (mr == null) {
			// 파일 업로드 실패시 경고창 띄어주고 작성 페이지 이동
			JsFunction.alertLocation(response, "첨부 파일이 제한 용량을 초과합니다", "../board/write.do");
			return;
		}
		
		// 2. 파일 업로드 외 처리
		// form값을 dto에 저장
		BoardDTO dto = new BoardDTO();
		dto.setName(mr.getParameter("name"));
		dto.setTitle(mr.getParameter("title"));
		dto.setContent(mr.getParameter("content"));
		dto.setPass(mr.getParameter("pass"));
		
		// 원본 파일명
		String fileName = mr.getFilesystemName("ofile");
		if (fileName != null) {		// 첨부파일이 있을 때 파일명 변경
			// 새로운 파일명 생성
			// yyyy - 년도, MM - 월, dd - 일, H - 시간, m - 분, s - 초, S - 밀리초
			String now = new SimpleDateFormat("yyyyMMdd_HmsS").format(new Date());
			String ext = fileName.substring(fileName.lastIndexOf(".")); 	// 파일 확장자
			String newFileName = now + ext; 		// 새로운 파일 이름 ("업로드 일시. 확장자")
			
			// 파일명 변경
			// File.separator : 경로 구분하는 특수 기호를 뜻함. OS에 따라 경로 표현 방법이 다름. 환경에 상관없이 코드 동작하게 함.
			File oldFile = new File(saveDirectory + File.separator + fileName);
			File newFile = new File(saveDirectory + File.separator + newFileName);
			
			oldFile.renameTo(newFile);		// 파일이름 변경
			
			dto.setOfile(fileName);			// 원래 파일 이름
			dto.setSfile(newFileName);		// 서버에 저장된 파일 이름
		}
		
		BoardDAO dao = new BoardDAO();
		int result = dao.insertWrite(dto);
		
		dao.close();
		
		// 모든 작업이 오류없이 완료되었다면 목록으로 이동, 실패했다면 글쓰기 페이지로 다시 들어감
		if (result == 1) {	// 글쓰기 성공
			response.sendRedirect("../board/list.do");
		} else {
			response.sendRedirect("../board/write.do");
		}
	}
}
