package com.kh.member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.member.model.service.MemberService;
import com.kh.member.model.vo.Member;

/**
 * Servlet implementation class LoginController
 */
@WebServlet("/login.me")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// System.out.println("잘 실행되나..?");
		
		/*
		 * <HttpServletRequest 객체와 HttpServletResponse 객체>
		 * - request : 사용자가 서버로 요청할때의 전달값들이 담겨있음
		 * - response : 요청에 대한 응답할 때 필요한 객체
		 * 
		 * <Get 방식과 Post 방식>
		 * - Get 방식 : 사용자가 입력한 값들이 url 에 노출 / 데이터 길이제한 있음 / 대신 즐겨찾기 기능 사용할때 편리
		 * - Post 방식 : 사용자가 입력한 값들이 url 에 노출 X / 데이터 길이제한 없음 / 대신 즐겨찾기 기능이 불편함 / Timeout 이 존재
		 */
		
		// 로그인 기능 => Post 방식
		// 1) UTF-8 로 인코딩 처리 (Post 방식일 경우)
		request.setCharacterEncoding("UTF-8");
		
		// 2) 요청 시 전달된 값들을 뽑기 (request의 parameter 영역으로부터)
		// 및 변수에 기록하고, VO 객체로 가공하기
		// request.getParameter("키값") : String (밸류값)
		// request.getParameterValues("키값") : String[] (밸류값들)
		String userId = request.getParameter("userId");
		String userPwd = request.getParameter("userPwd");
		
		// System.out.println(userId);
		// System.out.println(userPwd);
		
		// 3) 해당 요청을 처리하는 서비스 클래스의 어떤 메소드를 호출
		Member loginUser = new MemberService().loginMember(userId, userPwd);
		
		// 4) 처리된 결과를 가지고 응답화면을 지정
		// System.out.println(loginUser);
		
		if(loginUser == null) { // 로그인 실패 => 에러페이지 응답 (포워딩방식)
			
			// 4_1) 응답 페이지에서 필요로 하는 데이터를 담기 (request 의 attribute 영역)
			request.setAttribute("errorMsg", "로그인에 실패했습니다.");
			
			// 4_2) 응답 페이지 위임을 위한 RequestDispatcher 객체 생성 (jsp 경로 넘겨주기)
			RequestDispatcher view = request.getRequestDispatcher("views/common/errorPage.jsp");
			
			// 4_3) request, response 객체를 넘겨주는 포워딩 과정
			view.forward(request, response);
			
			/*
			 * * 응답페이지를 요청하는 방식
			 * 1. 포워딩 방식 : RequestDispatcher 객체를 이용하는 방법
			 * 				url 이 변경되지 않는 방식 (요청했을때의 url 이 여전히 주소창에 남아있음)
			 * 				=> 현재 내가 작성중인 servlet 의 url 맵핑값이 유지됨
			 * 2. url 재요청방식 (sendRedirect 방식)
			 * 			  : url 맵핑값을 제시해서 화면을 띄우는 방식 (일종의 새로고침 개념)
			 * 				response.sendRedirect(url주소);
			 */
		}
		else { // 로그인 성공 => 메인페이지 응답 (index.jsp)
			
			// 4_1) 응답 페이지에서 필요로 하는 데이터를 담기
			
			/*
			 * * 응답 페이지에 전달할 값이 있을 경우 어딘가에 담아야 함
			 * (데이터를 담아줄 수 있는 Servlet scope 내장객체 4종류)
			 * 1. application : application 에 담은 데이터는 웹 애플리케이션 전역에서 다 꺼내 쓸 수 있음
			 * 2. session : session 에 담은 데이터는 모든 jsp 와 servlet 에서 꺼내다 쓸 수 있음
			 * 				단, 한번 담은 데이터는 내가 직접 session 으로부터 지우기 전까지 
			 * 				또는, 서버가 멈추기 전까지
			 * 				또는, 브라우저가 종료되기 전까지 접근해서 꺼내다 쓸 수 있음
			 * 3. request : request 에 담은 데이터는 해당 request 를 포워딩한 응답 jsp 에서만 꺼내다 쓸 수 있음
			 * 4. page : 해당 jsp 페이지에서만 꺼내다 쓸 수 있음
			 * 
			 * => session, request 을 주로 쓴다!
			 * 
			 * * 객체들의 사용법
			 * 값을 담을때 : 객체명.setAttribute("키", 밸류);
			 * 값을 꺼낼때 : 객체명.getAttribute("키"); => Object 형식의 밸류
			 * 값을 지울때 : 객체명.removeAttribute("키"); => 키-밸류 세트로 지워줌
			 */
			
			// 로그인한 회원의 정보를 로그아웃 하기 전까지
			// 계속 가져다 써야하기 때문에 session 에 담기
			
			// 4_1_1) Servlet 에서 JSP 내장객체인 session 을 사용하고자 한다면
			// 		    우선적으로 session 객체를 만들어야 함
			HttpSession session = request.getSession();
			
			// 4_1_2) 만들어진 session 객체에 로그인한 회원 정보를 키-밸류 세트로 담기
			session.setAttribute("loginUser", loginUser);
			
			// 4_1_3) 로그인이 성공했을 경우 보여질 성공메세지를 session 에 담기
			session.setAttribute("alertMsg", "성공적으로 로그인이 되었습니다.");
			
			/*
			// 포워딩 방식으로 응답할 뷰 출력
			// 4_2) RequestDispatche 객체 생성
			RequestDispatcher view = request.getRequestDispatcher("index.jsp");
			// 주소창 예측 : localhost:8888/jsp/
			// 실제 주소창 : localhost:8888/jsp/login.me
			
			// 4_3) 포워딩
			view.forward(request, response);
			*/
			
			// 포워딩 방식으로 응답페이지를 요청했을 경우
			// 해당 선택된 jsp 가 보여질 뿐 url 에는 여전히 현재 이 서블릿 맵핑값이 남아있음
			// => url 재요청 방식으로 응답페이지 요청
			// response.sendRedirect("/jsp");
			response.sendRedirect(request.getContextPath());
			// localhost:8888 를 시작점으로 잡기
			// localhost:8888/jsp 로 메인페이지로 접속이 됨
			
			// request.getContextPath()
			// => 현재 이 프로젝트의 context root (== context path) 를 반환해줌
			// 예시) 1_Servlet 에서 이 메소드를 호출 시 "/1_Servlet" 이라는 context root 가 반환
			
			// 각 서비스 마다 사용되는 방식 다름
			// => 로그인 시에는 2번 방법이 쓰임
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
