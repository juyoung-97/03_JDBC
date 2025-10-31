package edu.kh.jdbc.homework.model;

import static edu.kh.jdbc.common.JDBCTemplate.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
	
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	
	/** 1. 학생 등록 DAO
	 * @param conn
	 * @param std
	 * @return
	 * @throws Exception
	 */
	public int insertStd(Connection conn, Student std) throws Exception{
		
		int result = 0;
		
		try {
			String sql = """
					INSERT INTO KH_STUDENT
					VALUES(SEQ_STD_NO.NEXTVAL, ?, ?, ?, DEFAULT)
					""";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, std.getStdName());
			pstmt.setInt(2, std.getStdAge());
			pstmt.setString(3, std.getMajor());
			
			result = pstmt.executeUpdate();
			
			
		} finally {
			close(pstmt);
		}
		
		
		return result;
	}


	/** 2. 전체 학생 조회 DAO
	 * @param conn
	 * @return
	 */
	public List<Student> selectStd(Connection conn) throws Exception{
		
		List<Student> stdList = new ArrayList<Student>();
		
		try {
			
			String sql = """
					SELECT STD_NO, STD_NAME, STD_AGE, MAJOR, 
					TO_CHAR(ENT_DATE, 'YYYY "년" MM "월" DD "일"') ENT_DATE
					FROM KH_STUDENT
					ORDER BY STD_NO
					""";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
	while(rs.next()) {
				
				int stdNo = rs.getInt("STD_NO");
				String stdName = rs.getString("STD_NAME");
				int stdAge = rs.getInt("STD_AGE");
				String major = rs.getString("MAJOR");
				String entDate = rs.getString("ENT_DATE");

				
				// User 객체 새로 생성하여 DB에서 얻어온 컬럼값 필드로 세팅
				Student std = new Student(stdNo, stdName, stdAge, major, entDate);
				
				stdList.add(std);
			}
			
			
		} finally {
			close(rs);
			close(pstmt);
		}
		
		
		
		
		return stdList;
	}


	/** 3. 학생 정보 수정 DAO (학번 있는지 체크)
	 * @param conn
	 * @param input
	 * @return
	 * @throws Exception
	 */
	public int updateStd(Connection conn, int input) throws Exception{
		
		int result = 0;
		
		try {
			
			String sql = """
					SELECT STD_NO
					FROM KH_STUDENT
					WHERE STD_NO = ?
					""";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, input);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt("STD_NO");
			}
			
		} finally {
			close(rs);
			close(pstmt);
		}
		
		
		
		
		return result;
	}


	/** 3-2. 학생 정보 수정 DAO (학번 체크 후 수정하는 DAO)
	 * @param conn
	 * @param name
	 * @param age
	 * @param major
	 * @return
	 */
	public int updateResult(Connection conn, String name, int age, String major, int input) 
	throws Exception{
		
		int result = 0;
		
		try {
			
			String sql = """
					UPDATE KH_STUDENT
					SET STD_NAME = ?
					, STD_AGE = ?
					, MAJOR = ?
					WHERE STD_NO = ?
					""";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, name);
			pstmt.setInt(2, age);
			pstmt.setString(3, major);
			pstmt.setInt(4, input);
			
			result = pstmt.executeUpdate();
			
			
			
		} finally {
			close(pstmt);
			
		}
		
		
		
		return result;
	}


	/** 4. 삭제 DAO
	 * @param conn
	 * @param input
	 * @return
	 */
	public int deleteStd(Connection conn, int input) throws Exception{
		
		int remove = 0;
		
		try {
			String sql = """
					DELETE FROM KH_STUDENT
					WHERE STD_NO = ?
					""";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, input);
			
			remove = pstmt.executeUpdate();
			
			
		} finally {
			close(pstmt);
		}
		
		
		
		
		return remove;
	}


	/** 5. 전공 SELECT
	 * @param conn
	 * @return
	 */
	public List<Student> selectMajor(Connection conn, String input) throws Exception{

		List<Student> stdList = new ArrayList<Student>();
		
		try {
			
			String sql = """
					SELECT STD_NO, STD_NAME, STD_AGE, MAJOR, 
					TO_CHAR(ENT_DATE, 'YYYY "년" MM "월" DD "일"') ENT_DATE
					FROM KH_STUDENT
					WHERE MAJOR = ?
					ORDER BY STD_NO
					""";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, input);
			
			rs = pstmt.executeQuery();
			
				while(rs.next()) {
				
				int stdNo = rs.getInt("STD_NO");
				String stdName = rs.getString("STD_NAME");
				int stdAge = rs.getInt("STD_AGE");
				String major = rs.getString("MAJOR");
				String entDate = rs.getString("ENT_DATE");

				
				Student std = new Student(stdNo, stdName, stdAge, major, entDate);
				
				stdList.add(std);
			}
			
			
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return stdList;
	}


	/** 6-1 나이 이상 검색 DAO
	 * @param conn
	 * @param age
	 * @return
	 * @throws Exception
	 */
	public List<Student> selectAgeUp(Connection conn, int age) throws Exception{
		
		List<Student> stdList = new ArrayList<Student>();
		
		try {
			
			String sql = """
					SELECT STD_NO, STD_NAME, STD_AGE, MAJOR, 
					TO_CHAR(ENT_DATE, 'YYYY "년" MM "월" DD "일"') ENT_DATE
					FROM KH_STUDENT
					WHERE STD_AGE >= ?
					ORDER BY STD_NO
					""";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, age);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
			int stdNo = rs.getInt("STD_NO");
			String stdName = rs.getString("STD_NAME");
			int stdAge = rs.getInt("STD_AGE");
			String major = rs.getString("MAJOR");
			String entDate = rs.getString("ENT_DATE");

			
			Student std = new Student(stdNo, stdName, stdAge, major, entDate);
			
			stdList.add(std);
		}
			
		} finally {
			close(rs);
			close(pstmt);
		}
		
		
		
		
		return stdList;
	}


	/** 6-2 나이 미만 검색 DAO
	 * @param conn
	 * @param age
	 * @return
	 * @throws Exception
	 */
	public List<Student> selectAgeDown(Connection conn, int age) throws Exception{
		

		List<Student> stdList = new ArrayList<Student>();
		
		try {
			
			String sql = """
					SELECT STD_NO, STD_NAME, STD_AGE, MAJOR, 
					TO_CHAR(ENT_DATE, 'YYYY "년" MM "월" DD "일"') ENT_DATE
					FROM KH_STUDENT
					WHERE STD_AGE < ?
					ORDER BY STD_NO
					""";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, age);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
			int stdNo = rs.getInt("STD_NO");
			String stdName = rs.getString("STD_NAME");
			int stdAge = rs.getInt("STD_AGE");
			String major = rs.getString("MAJOR");
			String entDate = rs.getString("ENT_DATE");

			
			Student std = new Student(stdNo, stdName, stdAge, major, entDate);
			
			stdList.add(std);
		}
			
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return stdList;
	}
	
}
