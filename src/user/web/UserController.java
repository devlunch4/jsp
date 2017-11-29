package user.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import user.dao.UserDao;
import user.dao.UserDaoMyBatisImpl;
import util.DefaultConst;
import util.NaviUtil;

/**
 * Servlet implementation class UserController
 */
@WebServlet("/user.do")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	//localhost:8090/user.do�� ���� ����
		//mybatis ����� �̿��Ͽ� userList ��ȸ
		//��ȸ�� userList�� /user/user.jsp�� ����
		// /user/user.jsp������ �ش� ����� ȭ�鿡 ���
    	
    	UserDao userDao = new UserDaoMyBatisImpl();
    	try {
			//request.setAttribute("userList", userDao.getUserList());
    		
    		//���� ����Ʈ ����¡ ó��
    		Map<String, Integer> paramMap = new HashMap<String, Integer>();
    		
    		// ����ڰ� /user.do ������
    		// 1. page, pageSize�� �Ķ���ͷ� �������
    		///user.do?page=1&pageSize=10
    		
    		// 2.  page, pageSize�� �Ķ���ͷ� �Ⱥ������
    		//		--> controller���� default�� page = 1, pageSize = 10
    		
    		//page, pageSize
    		//��ȸ �ϰ��� �ϴ� ������, ������ ������ : �Ķ���ͷ� �Ѿ���� ������� default 1, 10
    		String	pageParam		=	request.getParameter("page");
    		String	pageSizeParam	=	request.getParameter("pageSize");
    		
    		int	page		=	pageParam == null ? 1 : Integer.valueOf(pageParam);
    		int	pageSize	=	pageSizeParam == null ? DefaultConst.PAGESIZE : Integer.valueOf(pageSizeParam); 
    		
    		
	    	paramMap.put("page", page);
	    	paramMap.put("pageSize", pageSize);
	    	
    		request.setAttribute("userList", userDao.getUserListPaging(paramMap));
    		
    		String html = NaviUtil.makePageNav(request.getContextPath() + "user.do", 
    											userDao.getUserTotalCnt(),
    											Integer.valueOf(page), DefaultConst.PAGESIZE);
			request.setAttribute("pageNav", html);
			
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	RequestDispatcher rd = request.getRequestDispatcher("/user/user.jsp");
    	rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
