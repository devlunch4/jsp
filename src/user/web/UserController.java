package user.web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.codehaus.jackson.map.ObjectMapper;

import user.dao.IUserDao;
import user.dao.UserDaoMybatis;
import user.model.UserVo;
import user.service.IUserService;
import user.service.UserServiceImpl;
import util.DefaultConst;
import util.NaviUtil;
import util.PartsUtil;

/**
 * Servlet implementation class UserController
 */
/*
 * ����� ����Ʈ : /user.do          ==> doGet (��ȸ��)
 * ����� ������ ��ȸ : /userView.do  ==> doGet (��ȸ��)
 * ����� ���� : /userDelete.do  ==> doPost (Ʈ����� �߻�)
 * ����� ���� ajax /userDeleteAjax.do
 * ����� ����/�ű��Է� view : /userFormView.do ==> doGet (��ȸ��)
 * ����� ����/�ű��Է� ��� ȣ�� : /userForm.do ==> doPost(Ʈ�����)
 * ����� ����Ʈ ajax view : /userAjax.do
 * ����� ����Ʈ ajax      : /userAjaxPage.do
 */
@WebServlet(urlPatterns={"/user.do", "/userView.do", "/userDelete.do",
						"/userFormView.do", "/userForm.do",
						"/checkDupId.do",
						"/userAjax.do", "/userAjaxPage.do"})
@MultipartConfig(maxFileSize=1024*1024*2, maxRequestSize=1024*1024*5)
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserController() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	System.out.println("doGet : " + request.getRequestURI());
    	
    	String uri = request.getRequestURI();
    	uri = uri.replace(request.getContextPath(), "");
    	
    	if(uri.equals("/user.do"))
    		user(request, response);
    	if(uri.equals("/userAjax.do"))
    		userAjax(request, response);
    	if(uri.equals("/userAjaxPage.do"))
    		userAjaxPage(request,response);
    	else if(uri.equals("/userView.do"))
    		userView(request, response);
    	else if(uri.equals("/userFormView.do"))
    		userFormView(request, response);
    	else if(uri.equals("/checkDupId.do"))
    		checkDupId(request, response);
	}
    
    /**
     * ����� ����¡ ��ȸ
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void userAjaxPage(HttpServletRequest request,
			HttpServletResponse response)  throws ServletException, IOException{
    	response.setContentType("text/json");
    	response.setCharacterEncoding("utf-8");
    	
    	//����� ����Ʈ ����¡ ��ȸ���� �߰�
    	

    	//IUserDao userDao = new UserDaoMybatis();
    	IUserService userService = new UserServiceImpl();
    	try {
			//���� ����Ʈ ����¡ ó��
    		Map<String, String> paramMap = new HashMap<String, String>();
    		
    		String	page		=	request.getParameter("page");
    		String	pageSize	=	request.getParameter("pageSize");
    		
    		page		=	page == null ? "1" : page;
    		pageSize	=	pageSize == null ? String.valueOf(DefaultConst.PAGESIZE) : pageSize;
    		
	    	paramMap.put("page", page);
	    	paramMap.put("pageSize", pageSize);
	    	
    		List<UserVo> userList = userService.getUserListPaging(paramMap);
    		
    		//page������̼� �� ���� json ���ڿ� ���� ���� �߰� �ʿ�
    		String pageNav = NaviUtil.makePageNavForFunc(userService.getUserTotalCnt(), Integer.parseInt(page), Integer.parseInt(pageSize));
    	
    		//userList -> json �������� ����
    		//���� : userList ��ü�� json���� ��ȯ�Ͽ� ����
    		//���� : map��ü�� List<UserVo> userList, ->String pageNav�� ��� json���� ����
    		// 1.Map ��ü ���� (Map<String, Object> resultMap = new HashMap<String, Object>(); 
    		// 2.Map ��ü�� �ΰ��� �����͸� ��´�. 
    		//map.put("userList", userList);
    		//map.put("pageNav", pageNav);		//��ü�̸��� ������ ������ ��ü������ 
    		//ajax callback ������ ������ ���� ������ ����
    		//  data.userList / data.pageNav
    		Map<String, Object> resultMap = new HashMap<String, Object>();
    		resultMap.put("userList", userList);
    		resultMap.put("pageNav", pageNav);
    		
	    	ObjectMapper om = new ObjectMapper();
	    	om.writeValue(response.getWriter(), resultMap);
	    	
    	}catch(IOException e){
    		e.printStackTrace();
    	} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
     * ����� ajax view
     * @param request
     * @param response
     */
    private void userAjax(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		
    	RequestDispatcher rd = request.getRequestDispatcher("/user/userAjax.jsp");
    	rd.forward(request, response);
	}

	/**
     * ����� ���̵� �ߺ� üũ
     * @param request
     * @param response
     */
    private void checkDupId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
    	ObjectMapper om = new ObjectMapper();
    	
    	//result 0 : �ߺ��Ǽ� 0 
    	//result 1 : �ߺ��Ǽ� 1
    	
    	//�ߺ� üũ ���� ����
    	//ajax�� ���� �ѱ� userId�� reuqest��ü���� �޾ƿ´�.
    	//userId�� ������ ���̵� t_user�� �����ϴ��� üũ �ϴ� sql / dao ����
    	//�ߺ� �Ǽ��� map��ü�� ������ �ѱ��. map.put("result", �ߺ��Ǽ�);
    	
    	String userId = request.getParameter("userId");
    	
    	//IUserDao userDao = new UserDaoMybatis();
    	IUserService userService = new UserServiceImpl();
    	Map<String, Integer> map = new HashMap<String, Integer>();
    	
    	try {
			map.put("result", userService.checkDupId(userId));
			om.writeValue(response.getWriter(), map);
    		   		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
    
    
    //response.setContentType("text/json");    	
	//PrintWriter pw = response.getWriter(); 
	//pw.println("{'result' : '1' }");
		/*userDao.checkDupId(userId);
	pw.println("{\"result\" : \"1\" }");*/

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        	
		System.out.println("doPost : " + request.getRequestURI());
		
		String uri = request.getRequestURI();
    	uri = uri.replace(request.getContextPath(), "");
    	
    	if(uri.equals("/userDelete.do"))
    		userDelete(request, response);
    	else if(uri.equals("/userForm.do"))
    		userForm(request, response);
	}
    
	/**
	 * ����� form view 
	 * @param request
	 * @param response
	 */
  	//�ű��Է� / ���� ������ ���� �Ķ���� (method : insert -> �ű� / update -> ����)
	private void userFormView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		method = method == null ? "insert" : method;
		request.setAttribute("method", method);
		
		//�ű� �Է��� ���
		if(method.equals("insert")){
			//  /user/userForm.jsp���� �̵��Ѵ�
		}
		//������Ʈ�� ���
		else if(method.equals("update")){
			
			try {
				//������Ʈ ��� ����� ���� ��ȸ
				//����� ������ request ��ü�� �����Ѵ�.
				//IUserDao userDao = new UserDaoMybatis();
				IUserService userService = new UserServiceImpl();
				Map<String, String> pMap = new HashMap<String, String>();
				pMap.put("userId", request.getParameter("userId"));
				
				UserVo userVo = userService.getUser(pMap);
				request.setAttribute("userVo", userVo);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		//  /user/userForm.jsp���� �̵��Ѵ�
		
		RequestDispatcher rd = request.getRequestDispatcher("/user/userForm.jsp");
		rd.forward(request, response);
	}

	/**
	 * ����� ���� ���� / �ű� �Է�
	 * @param request
	 * @param response
	 */
	private void userForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//�Ķ���� ����
		String	userId		=	request.getParameter("userId");
		String	userNm		=	request.getParameter("userNm");
		String	userAlias	=	request.getParameter("userAlias");
		String	pass		=	request.getParameter("pass");
		
		 	
		
		UserVo userVo = new UserVo(userId, userNm, userAlias, pass, "system");
		//IUserDao userDao = new UserDaoMybatis();
		IUserService userService = new UserServiceImpl();
		
		//���� part�� ���Ѵ�.
		Part filePart = request.getPart("profilePicture");
		String filePath = "";
		//�ش� ��Ʈ�� ���� ����� Ȯ��, 0���� Ŭ��츸 ���ϻ���ó��
		if(filePart.getSize() > 0 ){
			String path = getServletContext().getRealPath("/uploadPicture");
			PartsUtil.uploadFile(filePart, path);
			filePath = "uploadPicture" + File.separator +  PartsUtil.getFileName(filePart.getHeader("content-disposition"));	
		}
		
		int	modifyCnt	=	0;
		
		//����� ���� ���� / �ű��Է� ����
		String method = request.getParameter("method");
		
		try {
			if(method == null){
				
			}
			else if(method.equals("insert")){
				userVo.setPicturePath(filePath);
				modifyCnt = userService.insertUser(userVo);
				//����� ���� ���� ���� ���� ���� ������� ����� ���� ��ȸ �������� �̵�
				userView(request, response);
			}
			else if(method.equals("update")){
				Map<String,Object> map = new HashMap<String,Object>();
				
				//����ڰ� ������ ���ε� �Ѱ��
				if(!filePath.equals("")){
					userVo.setPicturePath(filePath);
				}
				//����ڰ� ������ ���ε� ���� �������
				else{
					//����ڰ� ����� �̸��� ����, ������ �״�� ����
					Map<String, String> userinfo = new HashMap<String, String>();
					userinfo.put("userId", userId);
					UserVo orgUserVo = userService.getUser(userinfo);
					userVo.setPicturePath(orgUserVo.getPicturePath());
				}
				
				modifyCnt = userService.updateUser(userVo);
				//����� ���� ���� ���� ���� ���� ������� ����� ���� ��ȸ �������� �̵�
				userView(request, response);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * ����� ����
	 * @param request
	 * @param response
	 */
	private void userDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//userId�� �Ķ���ͷ� �޴´�.
		String userId = request.getParameter("userId");
		Map<String, String> userinfo = new HashMap<String, String>();
		userinfo.put("userId", userId);
		
		//dao
		//IUserDao userDao = new UserDaoMybatis();
		IUserService userService = new UserServiceImpl();
		int deleteCnt = 0;
		try {
			deleteCnt = userService.deleteUser(userinfo);
			request.setAttribute("deleteCnt", deleteCnt);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//��������Ȱ��  --> user.do(����� ����Ʈ)
		if(deleteCnt == 1)
			user(request, response);
		//���� �����Ѱ�� --> userView.do(����� ��ȭ��)
		else
			userView(request, response);
		
	}

	/**
	 * ����� �� ��ȸ ȭ��
	 * @param request
	 * @param response
	 */
	private void userView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//userId�� �Ķ���ͷ� �޴´�.
		String userId = request.getParameter("userId");
		Map<String, String> userinfo = new HashMap<String, String>();
		userinfo.put("userId", userId);
		
		//dao
		//IUserDao userDao = new UserDaoMybatis();
		IUserService userService = new UserServiceImpl();
		
		try {
			UserVo userVo = userService.getUser(userinfo);
			request.setAttribute("userVo", userVo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/user/userView.jsp");
		rd.forward(request, response);
	}

	/**
	 * ����� ����Ʈ ��ȸ
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void user(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//localhost:8090/user.do�� ���� ����
		//mybatis ����� �̿��Ͽ� userList ��ȸ
		//��ȸ�� userList�� /user/user.jsp�� ����
		// /user/user.jsp������ �ش� ����� ȭ�鿡 ���
    	
    	//IUserDao userDao = new UserDaoMybatis();
		IUserService userService = new UserServiceImpl();
    	try {
			//request.setAttribute("userList", userDao.getUserList());
    		
    		//���� ����Ʈ ����¡ ó��
    		Map<String, String> paramMap = new HashMap<String, String>();
    		
    		// ����ڰ� /user.do ������
    		// 1. page, pageSize�� �Ķ���ͷ� �������
    		///user.do?page=1&pageSize=10
    		
    		// 2.  page, pageSize�� �Ķ���ͷ� �Ⱥ������
    		//		--> controller���� default�� page = 1, pageSize = 10
    		
    		//page, pageSize
    		//��ȸ �ϰ��� �ϴ� ������, ������ ������ : �Ķ���ͷ� �Ѿ���� ������� default 1, 10
    		String	page		=	request.getParameter("page");
    		String	pageSize	=	request.getParameter("pageSize");
    		
    		page		=	page == null ? "1" : page;
    		pageSize	=	pageSize == null ? String.valueOf(DefaultConst.PAGESIZE) : pageSize;
    		
	    	paramMap.put("page", page);
	    	paramMap.put("pageSize", pageSize);
	    	
    		request.setAttribute("userList", userService.getUserListPaging(paramMap));
    		
    		String html = NaviUtil.makePageNav(request.getContextPath() + "user.do", 
    											userService.getUserTotalCnt(),
    											Integer.valueOf(page), DefaultConst.PAGESIZE);
			request.setAttribute("pageNav", html);
			
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	RequestDispatcher rd = request.getRequestDispatcher("/user/user.jsp");
    	rd.forward(request, response);
	}
}
