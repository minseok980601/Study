package kr.co.ezenac.membership;

import java.sql.Date;
import java.sql.SQLException;

import javax.management.Query;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import kr.co.ezenac.common.JDBConnection;

public class MemberDAO extends JDBConnection{
	
	public MemberDAO() {
		Context ctx;
		try {
			ctx = new InitialContext();
			Context envContext = (Context)ctx.lookup("java:comp/env");
			dataFactory = (DataSource)envContext.lookup("jdbc/oracle");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public MemberDAO(String drv, String url, String id, String pw) {
		super(drv, url, id, pw);
	}
	
	public int joinMember(MemberDTO dto) {

		String query = "INSERT INTO LOGIN_STUDY VALUES(?, ?, ?)";
		
		try {
			
			psmt = con.prepareStatement(query);
			
			psmt.setString(1, dto.getId());
			psmt.setString(2, dto.getPass());
			psmt.setString(3, dto.getPass());
			
			psmt.executeQuery();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	
	public MemberDTO getMember(String uid, String upass) {
		MemberDTO dto = new MemberDTO();
		String query = "SELECT * FROM LOGIN_STUDY WHERE ID = ? AND PASS = ?";
		
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, uid);		// 쿼리문의 첫번째 파라미터 값 설정
			psmt.setString(2, upass);	// 쿼리문의 두번째 파라미터 값 설정
			rs = psmt.executeQuery();	// 쿼리문 실행
			
			if (rs.next()) {
				// 쿼리 결과로 얻은 회원정보를 dto 객체에 저장
				dto.setId(rs.getString("id"));
				dto.setPass(rs.getString("pass"));
				dto.setName(rs.getString(3));

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dto;
	}
}
