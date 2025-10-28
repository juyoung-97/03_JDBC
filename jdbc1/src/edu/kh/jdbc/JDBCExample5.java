package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class JDBCExample5 {

	public static void main(String[] args) {
		
		// 아이디, 비밀번호, 이름을 입력받아
		// TB_USER 테이블에 삽입(INSERT)하기
		
		/* java.sql.PreparedStatement
		 * - SQL 중간에 ? (위치홀더, placeholder) 를 작성하여
		 * ? 자리에 java 값을 대입할 준비가 되어있는 Statement 
		 * 
		 * 장점 1 : SQL 작성이 간편해짐
		 * 장점 2 : ? 에 값 대입 시 자료형에 맞는 형태의 리터럴으로 대입됨
		 * 			ex) String 대입 -> '값' (자동으로 '' 추가)
		 * 			ex) int 대입 -> 값
		 * 장점 3 : 성능, 속도에서 우위를 가지고 있음
		 * 
		 * ***PreparedStatement는 Statement 자식이다***
		 * 
		 * */
		
		
		
		// 1. JDBC 객체 참조 변수 선언
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		// SELECT 가 아니기 때문에 ResultSet 필요 없음 !
		
		Scanner sc = null;
		
		try {
			
			// 2.DriverManager 를 이용해 Connection 객체 생성
			Class.forName("oracle.jdbc.driver.OracleDriver");
			

			String url = "jdbc:oracle:thin:@localhost:1521:XE"; 
			
			String userName = "kh_jjy"; 
			String password = "kh1234"; 
			
			conn = DriverManager.getConnection(url, userName, password);
			
			// 3. SQL 작성
			sc = new Scanner(System.in);
			
			System.out.print("아이디 입력 : ");
			String id = sc.next();
			System.out.print("비밀번호 입력 : ");
			String pw = sc.next();
			System.out.print("이름 입력 : ");
			String name = sc.next();
			
			String sql = """
					INSERT INTO TB_USER
					VALUES(SEQ_USER_NO.NEXTVAL, ?, ?, ?, DEFAULT)
					""";
			// 위치홀더(?) 가 있을 때 : 무조건 pstmt 
			//				  없을 때 : stmt, pstmt
			// 4. PreparedStatement 객체 생성
			// -> 객체 생성과 동시에 SQL 담겨짐
			// -> 미리 ? (위치홀더)에 값을 받을 준비를 해야되기 때문에..
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.addBatch()
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}
		
		
		
		
		
		
	}

}
