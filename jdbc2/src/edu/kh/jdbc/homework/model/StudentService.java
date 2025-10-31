package edu.kh.jdbc.homework.model;

import java.sql.Connection;
import java.util.List;

import edu.kh.jdbc.common.JDBCTemplate;

public class StudentService {
	
	private StudentDAO dao = new StudentDAO();

	/** 1. 학생 등록 서비스
	 * @param std
	 * @return
	 */
	public int insertStd(Student std) throws Exception{
		
		Connection conn = JDBCTemplate.getConnection();
		
		int result = dao.insertStd(conn, std);
		
		if(result > 0) { 
			JDBCTemplate.commit(conn);
		} else { 
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);	
		
		return result;
	}

	/** 2. 학생 전체 조회 서비스
	 * @return
	 * @throws Exception
	 */
	public List<Student> selectStd() throws Exception{
		
		Connection conn = JDBCTemplate.getConnection();
		
		List<Student> stdList = dao.selectStd(conn);
		
		JDBCTemplate.close(conn);
		
		return stdList;
	}

	/** 3. 학생 정보 수정 서비스
	 * @param input
	 * @return
	 */
	public int updateStd(int input) throws Exception{
		
		Connection conn = JDBCTemplate.getConnection();
		
		int result = dao.updateStd(conn, input);
		
		JDBCTemplate.close(conn);	
		
		return result;
	}

	/** 3-2.
	 * @param name
	 * @param age
	 * @param major
	 * @return
	 * @throws Exception
	 */
	public int updateResult(String name, int age, String major, int input) 
	throws Exception{
		
		Connection conn = JDBCTemplate.getConnection();
		
		int result = dao.updateResult(conn, name, age, major, input);
		
		if(result > 0)  JDBCTemplate.commit(conn);
		else			JDBCTemplate.rollback(conn);
		
		JDBCTemplate.close(conn);
		
		
		
		
		return result;
	}

	/** 4. 삭제
	 * @param input
	 * @return
	 */
	public int deleteStd(int input) throws Exception{
		
		Connection conn = JDBCTemplate.getConnection();
		
		int remove = dao.deleteStd(conn, input);
		
		JDBCTemplate.close(conn);	
		
		return remove;
	}

	/** 5. 전공 SELECT
	 * @return
	 */
	public List<Student> selectMajor(String input) throws Exception{
		
		Connection conn = JDBCTemplate.getConnection();
		
		List<Student> stdList = dao.selectMajor(conn, input);
		
		JDBCTemplate.close(conn);
		
		return stdList;
	}

	/** 6-1 ) 입력 나이 이상 검색 서비스
	 * @param age
	 * @return
	 */
	public List<Student> selectAgeUp(int age) throws Exception{
		
		Connection conn = JDBCTemplate.getConnection();
		
		List<Student> stdList = dao.selectAgeUp(conn, age);
		
		JDBCTemplate.close(conn);
		
		return stdList;
	}

	/** 6-2 ) 입력 나이 미만 검색 서비스
	 * @param age
	 * @return
	 */
	public List<Student> selectAgeDown(int age) throws Exception{
		
		Connection conn = JDBCTemplate.getConnection();
		
		List<Student> stdList = dao.selectAgeDown(conn, age);
		
		JDBCTemplate.close(conn);
		
		return stdList;
	}



}
