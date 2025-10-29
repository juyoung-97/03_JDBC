package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class JDBCExample7 {

	public static void main(String[] args) {
		// EMPLOYEE	테이블에서
		// 사번, 이름, 성별, 급여, 직급명, 부서명을 조회
		// 단, 입력 받은 조건에 맞는 결과만 조회하고 정렬할 것
						
		// - 조건 1 : 성별 (M, F)
		// - 조건 2 : 급여 범위
		// - 조건 3 : 급여 오름차순/내림차순
						
		// [실행화면]
		// 조회할 성별(M/F) : F
		// 급여 범위(최소, 최대 순서로 작성) :
		// 3000000
		// 4000000
		// 급여 정렬(1.ASC, 2.DESC) : 2
						
		// 사번 | 이름   | 성별 | 급여    | 직급명 | 부서명
		//--------------------------------------------------------
		// 217  | 전지연 | F    | 3660000 | 대리   | 인사관리부
		// -------------------------------------------------------
				
		// 사번 | 이름   | 성별 | 급여    | 직급명 | 부서명
		//--------------------------------------------------------
		// 218  | 이오리 | F    | 3890000 | 사원   | 없음
		// 203  | 송은희 | F    | 3800000 | 차장   | 해외영업2부
		// 212  | 장쯔위 | F    | 3550000 | 대리   | 기술지원부
		// 222  | 이태림 | F    | 3436240 | 대리   | 기술지원부
		// 207  | 하이유 | F    | 3200000 | 과장   | 해외영업1부
		// 210  | 윤은해 | F    | 3000000 | 사원   | 해외영업1부

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Scanner sc = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String url = "jdbc:oracle:thin:@localhost:1521:XE";
			String userName = "kh_jjy";
			String password = "kh1234";

			conn = DriverManager.getConnection(url, userName, password);
			
			sc = new Scanner(System.in);
			
			System.out.print("조회할 성별 입력(M/F) : ");
			String gender = sc.next().toUpperCase();
			System.out.println("급여 범위(최소, 최대 순서로 작성) : ");
			int min = sc.nextInt();
			int max = sc.nextInt();
			System.out.print("급여 정렬(1.ASC, 2.DESC) : ");
			int sort = sc.nextInt();
			

			
			String sql = """
						SELECT EMP_ID, EMP_NAME, DECODE(SUBSTR(EMP_NO, 8, 1), '1', 'M', '2', 'F') GENDER, SALARY, JOB_NAME, NVL(DEPT_TITLE, '없음') DEPT_TITLE
						FROM EMPLOYEE
						JOIN JOB USING (JOB_CODE)
						LEFT JOIN DEPARTMENT ON (DEPT_CODE = DEPT_ID)
						WHERE DECODE(SUBSTR(EMP_NO, 8, 1), '1', 'M', '2', 'F') = ?
						AND SALARY BETWEEN ? AND ?
						ORDER BY SALARY 
					""" ;
			// ORDER BY 절에 ? (위치홀더) 사용 시 오류 : SQL 명령어가 올바르게 종료되지 않았습니다.
			// 문자열로 출력되기때문에 'ASC' 'DESC' 라고 출력됨
			// PreparedStatement 의 위치홀더(?)는 데이터 값(리터럴) 을 대체하는 용도로만 사용가능
			// SQL 에서 ORDER BY 절의 정렬 기준 (ASC/DESC) 과 같은
			// -> SQL 구분(문법)의 일부는 PreParedStatement 의 위치 홀더(?)로 대체될 수 없음.
			if(sort == 1) sql += "ASC";
			else		  sql += "DESC";
						
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, gender);
			pstmt.setInt(2, min);
			pstmt.setInt(3, max);
			

			rs = pstmt.executeQuery();
			
			System.out.println("사번 | 이름   | 성별 | 급여    | 직급명 | 부서명");
			System.out.println("--------------------------------------------------------");

			while(rs.next()) {
				
				String empId = rs.getString("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				String gen = rs.getString("GENDER");
				int salary = rs.getInt("SALARY");
				String jobName = rs.getString("JOB_NAME");
				String deptTitle = rs.getString("DEPT_TITLE");
				
				
	
	System.out.printf("%-4s | %3s | %-4s | %7d | %-3s  | %s \n",
							empId, empName, gen, salary, jobName, deptTitle);



			}
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
				
				if(sc != null) sc.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		
		
	}

}
