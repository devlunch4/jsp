package kr.or.ddit.user.web;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.or.ddit.common.model.Page;
import kr.or.ddit.user.model.User;
import kr.or.ddit.user.service.IUserService;
import kr.or.ddit.user.service.UserService;

@WebServlet("/userPagingList")
public class UserPagingListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LoggerFactory.getLogger(UserPagingListController.class);
	
	private IUserService userService;
	
	@Override
	public void init() throws ServletException {
		userService = new UserService();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.debug("userPagingList doGet");
		
		// page, pagesize 파라미터 받기
		String pageStr = request.getParameter("page");
		String pagesizeStr = request.getParameter("pagesize");
		
		Page page = new Page(Integer.parseInt(pageStr), Integer.parseInt(pagesizeStr));
		
		//userService 객체를 이용하여 getUserPagingList를 호출
		//반환된 사용자 리스트를 request객체에 속성으로 저장
		Map<String, Object> resultMap = userService.getUserPagingList(page); 
		List<User> userList = (List<User>)resultMap.get("userList");
		int paginationSize = (Integer)resultMap.get("paginationSize");
		request.setAttribute("userList", userList);
		request.setAttribute("paginationSize", paginationSize);
		
		//조회된 사용자 리스트 정보를 html로 만들어줄 jsp로 요청 위임
		// webapp/user/userPagingList.jsp (userList.jsp 복사)
		request.getRequestDispatcher("/user/userPagingList.jsp").forward(request, response);
	}

}




