package day7;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import user.dao.IUserDao;
import user.dao.UserDao;
import user.model.UserVo;
import user.service.IUserService;
import user.service.UserServiceImpl;

/**
 * Servlet implementation class Day7LoginProcessServlet
 */
@WebServlet("/Day7LoginProcessServlet")
public class Day7LoginProcessServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Day7LoginProcessServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userId = request.getParameter("userId");
		String pass = request.getParameter("pass");
		
		Map<String, String> userInfo = new HashMap<String, String>();
		
		userInfo.put("userId", userId);
		userInfo.put("pass", pass);
		
		//IUserDao udao = new UserDao();
		IUserService userService = new UserServiceImpl();
		
		//�α��� ������
		if(userService.checkLogin(userInfo)){
			//���̵�� ��й�ȣ�� �������̸�, 
			//rememberMe �Ķ���Ͱ� �����ϴ°��
			//rememberMe cookie�� �����Ͽ� ���信 �����Ѵ�.
			
			if(request.getParameter("rememberMe") != null){
				Cookie cookie = new Cookie("rememberMe", "rememberMe");
				
				//setMaxAge : �ʴ��� 60*60*24 = 24�ð�, GMT����, ���ѹα��� GMT+9
				cookie.setMaxAge(60*60*33);		//24 + 9 		
				cookie.setPath("/");
				response.addCookie(cookie);
				
				Cookie userIdCookie = new Cookie("userId", userId);
				userIdCookie.setMaxAge(60*60*33);		//24 + 9 		
				userIdCookie.setPath("/");
				response.addCookie(userIdCookie);
			}
		
			
			
			try {
				UserVo userVo = userService.getUser(userInfo);
				HttpSession session = request.getSession();
				
				session.setAttribute("userVo", userVo);
				request.setAttribute("loginResult", userVo.getUserNm() + " success");
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			RequestDispatcher dis = request.getRequestDispatcher("/day7/main.jsp");
			dis.forward(request, response);
			
		}
		//�α��� ����
		else{
			RequestDispatcher dis = request.getRequestDispatcher("/login.jsp");
			dis.forward(request, response);
		}
			
	}
}
