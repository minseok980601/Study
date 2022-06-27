package kr.co.ezenac.model2.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;

public class FileUtil {

	// 파일 업로드(multipart/form-data 요청 처리
	public static MultipartRequest uploadFile(HttpServletRequest request, String saveDirectory,
											  int maxPostSize) {
		try {
			// 파일 업로드
			return new MultipartRequest(request, saveDirectory, maxPostSize, "UTF-8"); 
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// 파일 다운로드
	public static void download(HttpServletRequest request, HttpServletResponse response,
								String directory, String sfileName, String ofileName) {
		// 업로드 폴더의 물리적 경로 얻어옴
		String sDirectory = request.getServletContext().getRealPath(directory);
		
		try {
			// 파일을 찾아 입력스트림 생성
			File file = new File(sDirectory, sfileName);
			InputStream inStream = new FileInputStream(file);
			
			// 한글파일명 깨짐 방지
			String client = request.getHeader("User-Agent");
			// 인터넷 익스플로러가 아닌 경우
			if (client.indexOf("WOW64") == -1) {
				// 원본 파일명을 getBytes("UTF-8")로 바이트 배열로 변환후, iso-8859-1 캐릭터 셋 문자열로 재생성함
				ofileName = new String(ofileName.getBytes("UTF-8"), "iso-8859-1");
			}
			else {
				ofileName = new String(ofileName.getBytes("KSC5601"), "iso-8859-1");
			}
			
			// 파일을 다운로드 하기 위한 응답 헤더 설정
			response.reset(); 					// 응답 헤더 초기화
			// 파일 다운로드 창을 띄우기 위한 컨텐츠 타입 지정(application/octet-stream : 8비트 단위의 바이너리 데이터를 의미함)
			// 파일의 종류에 상관없이 브라우저로 다운로드 창을 띄우게 됨
			response.setContentType("application/octet-stream");
			// 웹 브라우저에서 파일 다운로드 창이 뜰 때 원본 파일명이 기본으로 입력되어 있도록 설정
			response.setHeader("Content-Disposition", "attachment; filename= \"" + ofileName + "\"");
			response.setHeader("content-Length", "" + file.length());
			
			// 출력스트림 초기화
			// out.clear()
			// 페이지에 대한 정보를 저장하는 기능
			// out = pageContext.pushBody();
			// response로부터 새로운 출력 스트림 생성
			OutputStream outStream = response.getOutputStream();
			
			// 출력 스트림에 파일 크기 배열 생성해서 파일 내용 출력
			byte[] b = new byte[(int)file.length()];
			int readyBuffer = 0;
			
			while((readyBuffer = inStream.read(b)) > 0) {
				outStream.write(b, 0, readyBuffer);
			}
			
			inStream.close();
			outStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
