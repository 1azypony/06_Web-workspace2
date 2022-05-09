package com.kh.notice.model.dao;

import static com.kh.common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import com.kh.notice.model.vo.Notice;

public class NoticeDao {

	// 전역변수로 Properties 타입의 객체 하나 만들어두기
	private Properties prop = new Properties();
	
	// 공통적인 코드는 기본생성자에 빼기
	
	public NoticeDao() {
		
		// 나중에 배포될 classes 폴더 기준으로 xml 파일 경로 잡기 
		String fileName = NoticeDao.class.getResource("/sql/notice/notice-mapper.xml").getPath();
		
		try {
			prop.loadFromXML(new FileInputStream(fileName)); // 예외처리도 해주기(try.catch)
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	// 공지사항 전체 조회용 쿼리실행할  DAO 작성
	
	public ArrayList<Notice> selectNoticeList(Connection conn) {
		
		// SELECT문은 -> resultSet 객체(여러행 조회) -> ArrayList
		
		// 1) 필요한 변수들 셋팅
		
		ArrayList<Notice> list = new ArrayList<>();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null; // 결과를 받을 ResultSet 만들기
		
		String sql = prop.getProperty("selectNoticeList");
		// XML 파일에 쿼리문 작성해서 돌아오기
		
		
		try {
		// 돌아와서  2) PreparedStatement 객체 생성
		pstmt = conn.prepareStatement(sql); // try/catch문으로 오류 잡아주기
		
		// 3) 미완성된 SQL문 완성 시키기
		// 그런데 전달값 없고 뚫려있는 위치홀더도 없기 때문에 단계 건너뛰기
		
		// 4,5) 쿼리문 실행 후 결과 받기
		rset = pstmt.executeQuery();
		
		// 6) VO객체 또는 ArrayList로 가공하기
		while(rset.next()) {
			
			list.add(new Notice(rset.getInt("NOTICE_NO")
							  , rset.getString("NOTICE_TITLE")
							  , rset.getString("USER_ID")
							  , rset.getInt("COUNT")
							  , rset.getDate("CREATE_DATE"))); //  xml파일 내 해당 컬럼 순차적으로 가져오기
			// 매개변수가 5개짜리 생성자가 아직 없기 떄문에 빨간 밑줄 생김,, notice.java 파일 가서 
			// 조회용  생성자를 따로 만들어주어야 함니다!!! 명심
		}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			// 7) 자원반납 (생성된 순서의 역순)
			close(rset);
			close(pstmt);
			
		}
		
		// 8) 결과 반환
		return list;
		
		
	}
}
