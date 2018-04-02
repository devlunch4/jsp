package day19;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import util.PartsUtil;

/**
 * Servlet implementation class FileUploadServlet
 */
@WebServlet("/fileUploadServlet")
@MultipartConfig(maxFileSize=1024*1024*2, maxRequestSize=1024*1024*5)
//���� : byte, 1024btye = 1k, 1k * 1024 = 1M

/*
@MultipartConfig ������
testParam : null 
testFileParam : null

@MultipartConfig ������
testParam : testParamValue 
testFileParam : null

@MulitipartConfig�� ������, form enctype="multipart/form=data"�� ������ �ϸ�
    �Ϲ����� �ؽ�Ʈ �Ķ���ʹ� requeset.getParamert�� �Ķ���� ������ ����
    
   file�� request.getParts ������ ���� ������ ����
*/ 

public class FileUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public FileUploadServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		
		//testParam, testFileParam �Ķ���͸� request��ü���� �޾� out ��ü�� ���
		String testParam		=	request.getParameter("testParam");
		String testFileParam	=	request.getParameter("testFileParam");
		
		/* ���Ͽ� ���ؼ��� getParameter�� �������� ����(multipart/form-data)
			Map<String, String> fileMap = request.getParameter("testFileParam");
			
			fileMap.get("content-type") // "image/png"
			fileMap.get("filename") 	// "moon.png"
			
			//option(�Ҽ��ִ� �е鸸)
			fileMap.get("fileSize")		//���� ���� ������(byte)
		*/
		
		
		PrintWriter pw = response.getWriter();
		pw.println("testParam : " + testParam +" <br/>");
		pw.println("testFileParam : " + testFileParam +" <br/>");
		
		System.out.println("testParam : " + testParam +" <br/>");
		System.out.println("testFileParam : " + testFileParam +" <br/>");
		
		pw.println("<br/><br/><br/>");
		
		printPartsHeaders(request, pw);
		
		//part �̸� : testFileParam
		//file �̸� : part.getHeader("content-disposition")
		
		
		
		
		//inputStream���� ��û ������ �б�
		/*InputStream is = request.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		char[] buff = new char[512];
		int len = -1;
		
		while((len = br.read(buff)) != -1){
			pw.println(buff);
		}*/
		
	}

	private void printPartsHeaders(HttpServletRequest request, PrintWriter pw)
			throws IOException, ServletException {
		//parts �������
		Collection<Part> partCollection = request.getParts();
		for(Part part : partCollection){
			Collection<String> headerCollection = part.getHeaderNames();
			for(String header : headerCollection){
				pw.println(part.getName() + "- header : " + header + " ::"
						 + part.getHeader(header) + ":: </br>");
			}
		}
	}
}






