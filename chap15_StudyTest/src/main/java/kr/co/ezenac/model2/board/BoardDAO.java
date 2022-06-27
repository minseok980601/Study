package kr.co.ezenac.model2.board;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.co.ezenac.model2.common.JDBConnection;

public class BoardDAO extends JDBConnection{
	
	public BoardDAO() {			
		super();									
	}
	
	// (검색 조건에 맞게) board 테이블에 저장된 게시물의 개수 반환
	// 목록에서 번호를 출력
	public int selectCount(Map<String, Object> map) {
		int totalCount = 0;			// 결과(게시물 수)를 담는 변수
		
		// 게시물 수를 얻어오는 쿼리문
		// Map 컬렉션에 저장된 값(검색어)이 있는 경우, Map 컬렉션에 키로 저장된 값이 있을때만 where절을 추가함
		String query = "SELECT COUNT(*) FROM mvcboard";
		if (map.get("searchWord") != null) {
			query += " WHERE " + map.get("searchField") + " "
					+ " LIKE '%" + map.get("searchWord") + "%'";
		}
		
		try {
			stmt = con.createStatement();			// 쿼리문을 실행하기 위해 Statement 객체 생성
			rs = stmt.executeQuery(query);			// SELECT 쿼리문 실행. 실행 결과는 ResultSet 객체로 반환
			rs.next();								// 커서를 이동시켜 결과값이 있는지 확인-첫번째 행으로 이동
			totalCount = rs.getInt(1);				// ResultSet 객체의 1번(첫번째) 인덱스 결과를 정수로 추출
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return totalCount;
	}
	
	// board 테이블의 레코드를 가져와서 반환함.
	// 이 메서드가 반환한 resultSet 객체로부터 게시물 목록을 반복하여 출력 (paging 기능)
	public List<BoardDTO> selectListPage(Map<String, Object> map) {
		List<BoardDTO> board = new ArrayList<>();			// 게시물 목록(결과)을 담을 변수
		
		String query = "SELECT * FROM ("
					 + "	SELECT tb.*, rownum rNum FROM ( "
					 + "		SELECT * FROM MVCBOARD ";	
				
		if (map.get("searchWord") != null) {		// Map컬렉션에 값이 있을때만 검색을 위한 where절 추가
			query += " WHERE " + map.get("searchField") + " "
					+ " LIKE '%" + map.get("searchWord") + "%'";
		}
		
		query += " 			ORDER BY idx DESC "				// 항상 최근게시물이 상단에 출력 (num 내림차순)
			  +	 "		) tb"
			  +	 " )"
			  +	 " WHERE rNum BETWEEN ? AND ?";
		
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, map.get("start").toString());
			psmt.setString(2, map.get("end").toString());
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				BoardDTO dto = new BoardDTO();
				
				dto.setIdx(rs.getString(1));
				dto.setName(rs.getString(2));
				dto.setTitle(rs.getString(3));
				dto.setContent(rs.getString(4));
				dto.setPostdate(rs.getDate(5));
				dto.setOfile(rs.getString(6));
				dto.setSfile(rs.getString(7));
				dto.setDownload(rs.getInt(8));
				dto.setPass(rs.getString(9));
				dto.setVisitcount(rs.getInt(10));
				
				board.add(dto);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("게시물 죄회 중 예외 발생");
			e.printStackTrace();
		}
		
		return board;
	}
	
	// 게시글 데이터를 받아 DB에 추가함(파일 업로드 지원)
	public int insertWrite(BoardDTO dto) {
		int result = 0;
		
		String query = "INSERT INTO MVCBOARD "
					 + " (IDX, NAME, TITLE, CONTENT, OFILE, SFILE, PASS) "
					 + " VALUES (seq_board_num.nextval, ?, ?, ?, ?, ?, ? )";
		
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, dto.getName());
			psmt.setString(2, dto.getTitle());
			psmt.setString(3, dto.getContent());
			psmt.setString(4, dto.getOfile());
			psmt.setString(5, dto.getSfile());
			psmt.setString(6, dto.getPass());
			result = psmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("게시물 입력 중 예외 발생");
			e.printStackTrace();
		}
		
		return result;
	}
	
	// 주어진 일변번호에 해당하는 게시물을 DTO에 담아 반환
	public BoardDTO selectView(String idx) {
		BoardDTO dto = new BoardDTO();
		
		String query = "SELECT * FROM MVCBOARD WHERE IDX = ?";
		
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, idx);
			rs = psmt.executeQuery();
			
			if (rs.next()) {
				dto.setIdx(rs.getString(1));
				dto.setName(rs.getString(2));
				dto.setTitle(rs.getString(3));
				dto.setContent(rs.getString(4));
				dto.setPostdate(rs.getDate(5));
				dto.setOfile(rs.getString(6));
				dto.setSfile(rs.getString(7));
				dto.setDownload(rs.getInt(8));
				dto.setPass(rs.getString(9));
				dto.setVisitcount(rs.getInt(10));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			e.printStackTrace();
		}
		
		return dto;
	}
	
	// 주어진 일련번호에 해당하는 게시물의 조회수를 1 증가시킴
	public void updateVisitCount(String idx) {
		String query = "UPDATE MVCBOARD SET VISITCOUNT = VISITCOUNT + 1 "
					 + " WHERE idx = ?";
		
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, idx);
			psmt.executeQuery();
		} catch (SQLException e) {
			System.out.println("게시물 조회수 증가 중 예외 발생");
			e.printStackTrace();
		}
	}
	
	// 다운로드 횟수 1 증가
	public void downCountPlus(String idx) {
		String query = "UPDATE MVCBOARD SET DOWNLOAD = DOWNLOAD + 1 "
					 + " WHERE idx = ?";
		
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, idx);
			psmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// 입력한 비밀번호가 지정한 일련번호의 게시물의 비밀번호와 일치하는지 확인
	public boolean confirmPassword(String pass, String idx) {
		boolean isCorrect = true;
		
		// 비밀번호와 일련번호가 일치하는 개수를 새어 => 비밀번호와 일치하는지 확인
		String sql = "SELECT count(*) FROM MVCBOARD "
				   + " WHERE pass = ? AND idx = ?";
		
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, pass);
			psmt.setString(2, idx);
			rs = psmt.executeQuery();
			
			rs.next();
			// 일치하는 게시물이 없다면 false 반환
			if (rs.getInt(1) == 0) {
				isCorrect = false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return isCorrect;
	}
}
