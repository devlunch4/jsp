package filter;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class DefaultParameterWrapper extends HttpServletRequestWrapper{

	private	Map<String, String[]> customMap;
	
	public DefaultParameterWrapper(HttpServletRequest request) {
		super(request);
		//request.getParameterMap()���� ���ϵǴ� Map<String, String[]> ��ü��
		//�츮�� �ʿ��� ���·� ������
		customMap = new HashMap<String, String[]>(request.getParameterMap());
		customMap.put("unt_cd", new String[]{"defaultUnt_cd"});
	}
	
	public void setParameter(String key, String value){
		customMap.put(key, new String[]{value});
	}
	
	//request��ü�� ����ϴ��� (���� 4���� �޼ҵ尡 parameter map�� ������ �޼ҵ�)
	public String getParameter(String name){
		//request.getParameter �⺻����
		//Map<String, String[]>
		//String[] : 0���̸� : null
		//			 1���̸� : �ش�� ����
		//			 1�� �̻� : ù��° ��ü (String[0])
		String[] values = customMap.get(name);
		
		if(values != null && values.length > 0)
			return values[0];
		else
			return null;
		
		/*if(values==null){
			return null;
		}else if(values.length==1){
			return values[0];
		}else if(values.length>1 && values.length!=0){
			return values[0];
		}else{
			return null;
		}
//		return values==null ? null : values.length==1 ? values[0] : values[0];*/
	}
	
	public String[] getParameterValues(String name){
		return customMap.get(name);
	}
	
	public Map<String, String[]> getParameterMap(){
		return customMap;
	}
	
	//map ��ü�� Ű���� enumration<String>
	public Enumeration<String> getParameterNames(){
		return Collections.enumeration(customMap.keySet());
	}

}





