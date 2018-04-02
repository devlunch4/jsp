package filter;

 import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

@WebFilter("/*")
public class CharacterSetFilter implements Filter {

    /**
     * Default constructor. 
     */
    public CharacterSetFilter() {
        // TODO Auto-generated constructor stub
    }

	public void destroy() {
		// TODO Auto-generated method stub
	}


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		//��ó��
		//ĳ���� ���ڵ� ����
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		System.out.println("request.getCharacterEncoding() : " + request.getCharacterEncoding());
		System.out.println("response.getCharacterEncoding() : " + response.getCharacterEncoding());
		
		//�������� / ���� ��û
		chain.doFilter(request, response);
		
		System.out.println("request.getCharacterEncoding() : " + request.getCharacterEncoding());
		System.out.println("response.getCharacterEncoding() : " + response.getCharacterEncoding());
		
		//��ó��
	}

	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}
}
