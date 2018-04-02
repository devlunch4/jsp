package filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

/**
 * Servlet Filter implementation class UriCountFilter
 */
@WebFilter("/*")
public class UriCountFilter implements Filter {
	
	private Map<String, Integer> uriCountMap;
	
    /**
     * Default constructor. 
     */
    public UriCountFilter() {
    }
    
    public void init(FilterConfig fConfig) throws ServletException {
		System.out.println("UriCountFilter init");
		uriCountMap = new HashMap<String, Integer>();
		
		//servletContext == application
		fConfig.getServletContext().setAttribute("uriCountMap", uriCountMap);
		
		//uriCountMap.put("/boardList", 5);
		//�Ӽ� �����ϴ� 4���� ����
		//pageContext, request, session, application
	}

	
    /*
     * localhost:8090/main.do --> UriCounterFilter(/*) --> doFilter
     * 
     *  ��ó��
     *  chain.doFilter(request, response);
     *  --> ���� ���ͷ� ��û(���̻� ó���� ���Ͱ� ���� ������)
     *     --> ������Ϳ��� ������ �Ϸ� --- > servlet���� rquest, response ����
     *  ��ó�� <---
     * 
     */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		//��ó��
		System.out.println("UriCountFilter ��ó�� ");
		
		//��û uri���� ī����
		String uri = ((HttpServletRequest)request).getRequestURI();
		System.out.println("uri : " + uri);
		
		Integer count = uriCountMap.get(uri);
		/*�ѹ��� ������ ���Ѱ�� : null  -->1 
		 *�ѹ� �̻� ������ �� ��� : ����  --> ����+1
		 *uriCountMap.put(uri, ����);
		 */
		if(uriCountMap.containsKey(uri))
			uriCountMap.put(uri, uriCountMap.get(uri) +1);
		else
			uriCountMap.put(uri, 1);
		/*count = count == null ? 1 : count; 
		uriCountMap.put(uri, count);*/

		
		chain.doFilter(request, response);

		//��ó�� (Ȱ�뵵 ����)
		System.out.println("UriCountFilter ��ó�� ");
		
	}
	
	public void destroy() {
		System.out.println("UriCountFilter destory ");
	}
}



