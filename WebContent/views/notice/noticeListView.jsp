<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList, com.kh.notice.model.vo.Notice" %>
<%
	// 조회된 전체 공지사항 리스트 뽑기
	ArrayList<Notice> list = (ArrayList<Notice>)request.getAttribute("list");
	// request.getAttribute 는 오브젝트 머시기라 낮춰서 맞춰야함

%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
    .outer {
        background-color: black;
        color:white;
        width: 1000px;
        height: 500px;
        margin: auto;
        margin-top: 50px;
    }

    .list-area {
        border: 1px solid white;
        text-align: center;
    }

    .list-area>tbody>tr:hover {
        background-color: dimgrey;
        cursor: pointer;
    }


</style>
</head>
<body>

    <%@ include file="../common/menubar.jsp" %>

    <div class="outer">

        <br>
        <h2 align="center">공지사항</h2>
        <br>
        <!-- 글작성 버튼은 관리자만 보이게 처리 -->
        <% if(loginUser != null && loginUser.getUserId().equals("admin")){ %>

        <!--글 작성 버튼-->

        <div align="right" style="width : 850px;">
            <!--글 작성 버튼을 오른쪽에 붙어 보이게 하고 싶었는데 div가 너무 길어서 끝에 붙어버림
            이럴때 가로 길이를 줄인다!-->
           <!-- <button type="button" onclick="location.href=''">글작성</button>-->
            <a href="" class="btn btn-secondary btn-sm">글작성</a>
            <!--a 태그는 gref 속성이 있지만 버튼은 없으므로
                이 경우 다른 페이지로 이동하려면 클릭이벤트 발생 시 
                onclick 이벤트 속성 -location.href - url 지정 하거나 , 
                a태그를 버튼 형식으로 만들어 준다.-->
            <br><br>
        </div>
        <% } %>

        <table class="list-area" align="center">
            <thead>
                <tr>
                    <th>글번호</th>
                    <th width="400">글제목</th>
                    <th width="100">작성자</th>
                    <th>조회수</th>
                    <th width="100">작성일</th>
                </tr>
            </thead>
            <tbody>
                <!-- 모든 작성일 기준 최신글이 가장 위에 오게끔한다.(내림차순)-->
                <% if(list.isEmpty()) { %>
                <!-- 리스트가 비어 있을 경우  -->
                <tr>
                	<td colspan="5">공지사항이 존재하지 않습니다.</td>
                </tr>	
                <% } else { %>
                <!-- 리스트가 비어 있지 않을 경우  -->
                <% for(Notice n : list) {  %>
                <tr> 
                	<td><%= n.getNoticeNo() %></td>
                	<td><%= n.getNoticeTitle() %></td>
                	<td><%= n.getNoticeWriter() %></td>
                	<td><%= n.getCount() %></td>
                	<td><%= n.getCreateDate() %></td>
                </tr>
                
                <% } %>
                <% } %>
                
                <!--
                <tr>
                    <td>3</td>
                    <td>뉴올의 오픈 이벤트!</td>
                    <td>admin</td>
                    <td>120</td>
                    <td>2021-03-12</td>
                </tr>
                <tr>
                    <td>2</td>
                    <td>게시판 서비스를 시작합니다.</td>
                    <td>admin</td>
                    <td>20</td>
                    <td>2021-02-23</td>
                </tr>
                <tr>
                    <td>1</td>
                    <td>뉴올에 오신 것을 환영합니다 ^0^! </td>
                    <td>admin</td>
                    <td>237</td>
                    <td>2021-01-03</td>
                </tr> 
                -->
            </tbody>
        

        </table>


    </div>

</body>
</html>