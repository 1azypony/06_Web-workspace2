package com.kh.notice.model.service;

// JDBCTemplate 클래스의 모든 메소드를 가져다 쓰겠다.
import static com.kh.common.JDBCTemplate.close;
import static com.kh.common.JDBCTemplate.commit;
import static com.kh.common.JDBCTemplate.getConnection;
import static com.kh.common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.ArrayList;

import com.kh.notice.model.dao.NoticeDao;
import com.kh.notice.model.vo.Notice;

public class NoticeService {

	
	// 공지사항 전체 조회 서비스
	public ArrayList<Notice> selectNoticeList() {
		
		// 1) Connection 객체 만들기
		 Connection conn = getConnection();
		 
		// 2) Connection 객체와 전달값을 받아서 DAO에게 보내고 결과 받을것
		 ArrayList<Notice> list = new NoticeDao().selectNoticeList(conn); 
		 // 전달값이 없기 때문에 conn만 넘기기
		
		// 3) commit, rollback 처리 (select문의 경우 생략)
		 
		// 4) Connection  객체 반납
		 close(conn);
		 
		// 5) 결과 반환
		 return list;
	}
	// ----------------------------------------------------------------
		// 공지사항 작성 서비스
		// controller단에서 보내준 Notice n을 매개변수로 데려옴
		// 그런데 결과가 처리된 행의 갯수로 담겨야하니까 int! 
		public int insertNotice(Notice n) {
			
			// 1) Connection 객체 생성
			Connection conn = getConnection();
			
			// 2)DAO로 conn과 전달값 넘기기
			int result = new NoticeDao().insertNotice(conn, n);
			// dao로 보낸 결과는 처리된 행의 갯수로 돌아오기 때문에 int result로 받기!!
			
			// 3) 결과에 따라 commit, rollback처리
			if(result > 0) {
				commit(conn);
			}
			else {
				rollback(conn);
			}
			
			// 4) Connection 객체 반납
			close(conn);
			
			// 5) 결과 반환
			return result;	
		}
		
	// -------------------------------------------------------------
	// 공지사항 조회수 증가용 서비스
	public int increaseCount(int noticeNo) {
			
		// 1) Connection 객체 생성
		Connection conn = getConnection();
			
		// 2) Dao로 보내 결과값 받기
		int result = new NoticeDao().increaseCount(conn, noticeNo);
			
		// commit, rollback
			if(result > 0) {
			 commit(conn);
		 }
			else {
			rollback(conn);
			}
			 
			// Connection 객체 반납
			close(conn);
			 
			// 결과 리턴 
			return result;
		}
	// ----------------------------------------------------------------
	// 공지사항 상세보기용 서비스
	public Notice selectNotice(int NoticeNo) {
			
		Connection conn = getConnection();
			
		Notice n = new NoticeDao().selectNotice(conn, NoticeNo);
			
		close(conn);
			
		return n;
	}
	// ----------------------------------------------------------------
	// 공지사항 수정하기용 서비스
	public int updateNotice(Notice n) {
		
		Connection conn = getConnection();
		
		int result = new NoticeDao().updateNotice(conn, n);
		
		if(result > 0) {
			commit(conn);
		}
		else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
	}
	// ---------------------------------------------------------------
	// 공지사항 삭제용 서비스
	public int deleteNotice(int noticeNo) {
		
		Connection conn = getConnection();
		
		int result = new NoticeDao().deleteNotice(conn, noticeNo);
		
		if(result > 0) {
			commit(conn);
		}
		else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
	}
}

