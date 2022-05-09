package com.kh.member.model.service;

import java.sql.Connection;

import com.kh.common.JDBCTemplate;
import com.kh.member.model.dao.MemberDao;
import com.kh.member.model.vo.Member;

public class MemberService {

	// 로그인 요청 서비스
	public Member loginMember(String userId, String userPwd) {
		
		// 1) Connection 객체 생성
		Connection conn = JDBCTemplate.getConnection();
		
		// 2) 만들어진 Connection 객체와 전달받았던 값들을 DAO 한테 넘기기
		Member m = new MemberDao().loginMember(conn, userId, userPwd);
		
		// 3) INSERT, UPDATE, DELETE 구문의 경우 commit 또는 rollback
		// => SELECT 문일 경우는 생략
		
		// 4) Connection 객체를 반납
		JDBCTemplate.close(conn);
		
		// 5) Controller 한테 결과 반환
		return m;
	}
	
	// 회원 가입용 서비스
	public int insertMember(Member m) {
		
		// 1) Connection 객체 생성
		Connection conn = JDBCTemplate.getConnection();
		
		// 2) MemberDao 의 어떤 메소드를 호출 (Connection 객체와 전달값을 넘기면서)
		int result = new MemberDao().insertMember(conn, m);
		
		// 3) 결과값에 따라 commit 또는 rollback 처리
		if(result > 0) { // 성공
			JDBCTemplate.commit(conn);
		}
		else { // 실패
			JDBCTemplate.rollback(conn);
		}
		
		// 4) Connection 객체 반납
		JDBCTemplate.close(conn);
		
		// 5) 결과값을 Controller 로 반환
		return result;
	}
	
	// 회원 정보 수정용 서비스
	public Member updateMember(Member m) {
		
		// 1) Connection 객체 생성
		Connection conn = JDBCTemplate.getConnection();
		
		// 2) 만들어진 Connection 객체와 전달값을 DAO 로 넘겨서 결과 받기
		int result = new MemberDao().updateMember(conn, m);
		
		// 3) 결과에 따른 commit, rollback 처리
		Member updateMem = null;
		
		if(result > 0) { // 성공
			
			JDBCTemplate.commit(conn);
			
			// 갱신된 회원의 정보를 다시 조회해오기 (리턴용)
			updateMem = new MemberDao().selectMember(conn, m.getUserId());
		}
		else { // 실패
			
			JDBCTemplate.rollback(conn);
		}
		
		// 4) Connection 객체 반납
		JDBCTemplate.close(conn);
		
		// 5) Controller 로 결과 반환
		return updateMem;
	}
	
	// 회원 비밀번호 변경용 서비스
	public Member updatePwdMember(String userId, String userPwd, String updatePwd) {
		
		// 1) Connection 객체 생성
		Connection conn = JDBCTemplate.getConnection();
		// Connection 객체 생성하여 
		
		// 2) 만들어진 Connection 객체와 전달값들을 DAO의 메소드에게 넘기고 결과 받기
		int result = new MemberDao().updatePwdMember(conn, userId, userPwd, updatePwd);
		// 매개변수로 conn과 받아온 세개의 데이터 담기 
		
		// 3) update문이기 때문에  결과값에 따라 commit과 rollbck 처리를 해주어야 함
		Member updateMem = null;
		
		if(result > 0) { // 성공시
			
			JDBCTemplate.commit(conn);
			
			updateMem = new MemberDao().selectMember(conn, userId);
			// Dao단에서 하나의 아이디를 조회하는 것을 했었음 그래서 다시 재사용하기 위해
			// Doa단에서 .selectMember하여 conn과 userId를 가지고 옴
		}
		else { // 실패시
			JDBCTemplate.rollback(conn);
		}
		
		// 4) Connection 객체 반납
		JDBCTemplate.close(conn);
		
		//5) 결과 반환
		return updateMem; // 갱신된 회원 정보를 얻어 옴
	}
	
	//----------------------------------------------------------------------------------------
	// 회원 탈퇴용 서비스
	public int deleteMember(String userId, String userPwd) {
		// 전달받은 값들 매개변수에 같이 담아오기
		
		// 1) Connection 객체 생성
		Connection conn = JDBCTemplate.getConnection();
		
		// 2) 만들어진 Connection 객체와 전달 값을 DAO에게 넘기기
		int result = new MemberDao().deleteMember(conn, userId, userPwd);
		// MemberDao에게 넘겨줄게 . delete(얘네들을~ conn객체와 받아온 userId, userPwd);
		
		
		//3) 성공 실패 여부에 따라 commit, rollback 처리
		if(result > 0) {
			
			JDBCTemplate.commit(conn);
		}
		else {
			JDBCTemplate.rollback(conn);
		}
		
		// 4) Connection 자원 반남
		JDBCTemplate.close(conn);
		
		// 5) 결과 반환
		return result;
	}
}









