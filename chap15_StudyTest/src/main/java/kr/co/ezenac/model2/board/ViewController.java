package kr.co.ezenac.model2.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/board/view.do")
public class ViewController extends HttpServlet{

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		BoardDAO dao = new BoardDAO();
		String idx = request.getParameter("idx");
		
		dao.updateVisitCount(idx); 			// 조회수 1 증가
		BoardDTO dto = dao.selectView(idx);
		dao.close();
		
		// 게시물 내용 줄바꿈 처리
		// HTML 문서는 일반 텍스트 문서의 줄바꿈 문자 (\r\n)를 무시하기 때문에, HTML이 인식하는 줄바꿈 태그(<br/>로 바꿔줌
		dto.setContent(dto.getContent().replaceAll("\r\n", "<br />"));
		// 게시물 저장(바인딩) 후 뷰로 포워딩
		request.setAttribute("dto", dto);
		request.getRequestDispatcher("/board/view.jsp").forward(request, response);
		
	}
}
