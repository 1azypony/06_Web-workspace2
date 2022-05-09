package com.kh.member.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.member.model.service.MemberService;
import com.kh.member.model.vo.Member;

/**
 * Servlet implementation class MemberDeleteController
 */
@WebServlet("/delete.me")
public class MemberDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberDeleteController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		// 1) post 방식일 경우 인코딩 세팅 
		request.setCharacterEncoding("UTF-8");
		
		// 2) 요청 시 전달값을 뽑아 변수에 기록하고 vo 객체로 가공
		// 탈퇴하고자 하는 회원의 아이디와 비밀번호
		// > UPDATE MEMBER SET STATUS = 'N' WHERE USER_ID = ? AND 
		
		/*
		 * 로그인한 회원의 정보를 얻어내는 방법
		 *  방법 1. input type="hidden"으로 요청 시 숨겨서 전달
		 *  방법 2. session 영역에 담긴 회원 객체로부터 뽑기
		 */
		
		HttpSession session = request.getSession(); // session 객체 만들기
		String userId = ((Member)session.getAttribute("loginUser")).getUserId();
		// loginUser 는 Object기 때문에 Member로 다운캐스팅 후 다시 .getUserId 불러오기
		
		String userPwd = request.getParameter("userPwd");
		
		// 3) MemberService 클래스의 어떤 메소드를 호출해서 요청 후 결과 받기
		// new MemberService().deleteMember(userId, userPwd);
		// 결과값으로 처리된 행의 갯수가 반환 될거니까 반환 값을 담아줄 int result 객체 만들어주기
		int result = new MemberService().deleteMember(userId, userPwd);
		
		// 4) 성공 실패 여부에 따른 응답화면
		if(result > 0) { // 성공시 메인페이지로 이동(세션 끊기-> 로그아웃)
			
			session.setAttribute("alertMsg", "성공적으로 회원 탈퇴 되었습니다. 다음에 또 만나요!");
			// 1회성 알람 띄워주고 
			
			// 로그아웃 시켜주기(세션 끊기 메소드 invalidate 활용은 적합하지 않음) 
			// -> 세션이 만료 되어 alert메세지까지 전부 다 만료되기 때문에!
			// removeAttribute("키값")을 이용하여
			// 현재 로그인한 사용자의 정보만 지워주는 방식으로 로그아웃을 시켜줌
			session.removeAttribute("loginUser");
			
			response.sendRedirect(request.getContextPath());
			
		}
		else { // 실패시 에러페이지 포워딩
			
			request.setAttribute("errorMsg", "회원탈퇴에 실패했습니다.");
			
			request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
