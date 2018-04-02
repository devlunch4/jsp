package listener;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import user.model.UserVo;

/**
 * Application Lifecycle Listener implementation class HttpSessionAttrListener
 *
 */
@WebListener
public class HttpSessionAttrListener implements HttpSessionAttributeListener {
	private Map<String, UserVo> sessionMap;
	
    /**
     * Default constructor. 
     */
    public HttpSessionAttrListener() {
        sessionMap = new HashMap<String, UserVo>(); 
    }

	/**
     * @see HttpSessionAttributeListener#attributeAdded(HttpSessionBindingEvent)
     */
    public void attributeAdded(HttpSessionBindingEvent se)  {
    	//day7LoginProcessServlet : session.setAttribute("userVo", userVo);
    	//--->
    	//HttpSessionAttrListener.attributeAdded ȣ��
    	//session�� �Էµ� �Ӽ� Ű��(�̸�) userVo �̸� == �α��� ������ ���ǿ� userVo��ü�� setAttribute �����Ѱ�.
    	
    	//�츮�� �����ִ� userVo�� session�� ���ε� �Ǿ���, map ��ü�� ����� ������ �����Ѵ�.
    	if("userVo".equals(se.getName())){
    		ServletContext application = se.getSession().getServletContext();
    		UserVo userVo = (UserVo)se.getValue();
    		
    		//����� ���̵� : key, ����� ��ü : value
    		sessionMap.put(userVo.getUserId(), userVo);
    		application.setAttribute("sessionMap", sessionMap);	//application jsp���� ������ ����
        	
    	}
    	System.out.println("se.getName() : " + se.getName());
    	
    	
    	//servletContext ��ü�� sessionMap �Ӽ��� �ִ��� �˻�, ���� ��� sessionMap�� �Ӽ����� �����Ѵ�.
    	/*ServletContext sc = se.getSession().getServletContext();
    	if(sc.getAttribute("sessionMap") == null)
    		sc.setAttribute("sessionMap", sessionMap);
    	
    	//�α��� ó���� userVo(�����)��ü�� ���ǿ� ���εɰ�� sessionMap ����� ���̵� Ű, ����� ��ü�� ������
    	//sessionMap �־��ش�.
        if("userVo".equals(se.getName())){
        	UserVo userVo = (UserVo)se.getValue();
        	sessionMap.put(userVo.getUserId(), userVo);
     	}*/
         
         System.out.println("HttpSessionAttrListener attributeAdded ");
    }

	/**
     * @see HttpSessionAttributeListener#attributeRemoved(HttpSessionBindingEvent)
     */
    public void attributeRemoved(HttpSessionBindingEvent se)  {
    	
    	if("userVo".equals(se.getName())){
    		//����� ���� ��Ȳ map ��ü�� remove
    		ServletContext application = se.getSession().getServletContext();
    		UserVo userVo = (UserVo)se.getValue();
    		sessionMap.remove(userVo.getUserId());
    	}
    	
    	 
    	System.out.println("HttpSessionAttrListener attributeRemoved ");
    }

	/**
     * @see HttpSessionAttributeListener#attributeReplaced(HttpSessionBindingEvent)
     */
    public void attributeReplaced(HttpSessionBindingEvent se)  { 
    	System.out.println("HttpSessionAttrListener attributeReplaced ");
    }
	
}



