package edu.kh.jdbc.model.service;

import java.sql.Connection;
import java.util.List;

import edu.kh.jdbc.common.JDBCTemplate;
import edu.kh.jdbc.model.dao.UserDAO;
import edu.kh.jdbc.model.dto.User;

// (Model 중 하나) Service : 비즈니스 로직을 처리하는 계층,
// 데이터를 가공하고 트랜잭션(commit, rollback) 관리 수행

public class UserService {
	
	// 필드
	private UserDAO dao = new UserDAO();

	/** 1. User 등록 서비스
	 * @param user : 입력받은 id, pw, name 이 세팅된 객체
	 * @return insert 된 결과 행의 갯수
	 */
	public int insertUser(User user) throws Exception{
		
		// 1. 커넥션 생성
		Connection conn = JDBCTemplate.getConnection();
		
		// 2. 데이터 가공(할 거 없음 생략해도됨)
		
		// 3. DAO 메서드 호출 후 결과 반환받기
		int result = dao.insertUser(conn, user);
		
		// 4. DML(INSERT) 수행 결과에 따라 트랜잭션 제어 처리
		if(result > 0) { // INSERT 성공
			JDBCTemplate.commit(conn);
		} else { // INSERT 실패
			JDBCTemplate.rollback(conn);
		}
		
		// 5. Connection 반환하기
		JDBCTemplate.close(conn);	
		
		// 6. 결과 반환
		return result;
	}

	/** 2. User 전체 조회 서비스
	 * @return 조회된 User 들이 담긴 List
	 */
	public List<User> selectAll() throws Exception{
		
		// 1. 커넥션 생성
		Connection conn = JDBCTemplate.getConnection();
		
		// 2. DAO 메서드 호출(SELECT) 후 결과 반환(List<User>)받기
		List<User> userList = dao.selectAll(conn);
		
		// 3. Connection 반환하기
		JDBCTemplate.close(conn);
		
		// 4. 결과 반환
		return userList;
	}

	/** 3. User 중 이름에 검색어가 포함된 회원 조회
	 * @param keyword
	 * @return
	 * @throws Exception
	 */
	public List<User> selectName(String keyword) throws Exception{
		
		Connection conn = JDBCTemplate.getConnection();
		
		List<User> searchList = dao.selectName(conn, keyword);
		
		JDBCTemplate.close(conn);
		
		return searchList;
	}

	/** 4. 
	 * @param number
	 * @return
	 * @throws Exception
	 */
	public List<User> selectUser(int number) throws Exception{
		
		Connection conn = JDBCTemplate.getConnection();
		
		List<User> numberList = dao.selectUser(conn, number);
		
		JDBCTemplate.close(conn);
		
		return numberList;
	}

	/** 5. 
	 * @param deleteNumber
	 * @return
	 * @throws Exception
	 */
	public int deleteUser(int deleteNumber) throws Exception{
		
		Connection conn = JDBCTemplate.getConnection();
		
		int result = dao.deleteUser(conn, deleteNumber);
		
		if(result > 0) { // INSERT 성공
			JDBCTemplate.commit(conn);
		} else { // INSERT 실패
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return result;
	}

	/** 6. 조회 하는 메서드
	 * @param user
	 * @return
	 */
	public int updateName(User user) throws Exception{
		
		Connection conn = JDBCTemplate.getConnection();
		
		int result = dao.updateName(conn, user);
			
		
		
		return 0;
	}


	
	
}
