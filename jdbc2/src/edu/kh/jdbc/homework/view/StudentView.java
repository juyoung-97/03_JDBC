package edu.kh.jdbc.homework.view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc.homework.model.Student;
import edu.kh.jdbc.homework.model.StudentService;

public class StudentView {
	
	private StudentService service = new StudentService();
	private Scanner sc = new Scanner(System.in);
	
	
	public void mainMenu() {
		
		
		int input = 0;
		
		do {
			try {
				System.out.println("\n===== User 관리 프로그램 =====\n");
				System.out.println("1. 학생 등록");
				System.out.println("2. 학생 전체 조회");
				System.out.println("3. 학생 정보 수정(이름 나이 전공 변경 가능)");
				System.out.println("4. 학생 삭제 (학번 기준)");
				System.out.println("5. 전공별 학생 조회");
				System.out.println("6. 입력받은 나이 이하/이상 학생 조회");
				System.out.println("0. 프로그램 종료");
				System.out.print("메뉴 선택 : ");
				input = sc.nextInt();
				sc.nextLine(); 
				switch (input) {
				case 1: insertStd(); break;
				case 2: selectStd(); break;
				case 3: updateStd(); break;
				case 4: deleteStd(); break;
				case 5: selectMajor(); break;
				case 6: selectAge(); break;
				case 0: System.out.println("\n[프로그램 종료]\n"); break;
				default: System.out.println("\n[메뉴 번호만 입력하세요]\n");
				}
				System.out.println("\n-------------------------------------\n");

				
				
			} catch (InputMismatchException e) {
				System.out.println("\n***잘못 입력하셨습니다***\n");
				input = -1; 
				sc.nextLine(); 
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		} while(input != 0);
		
		
		
		
	}




	/** 1. 학생 등록
	 * 
	 */
	private void insertStd() throws Exception{
		
		System.out.println("===학생 등록===");
		
		System.out.print("이름 입력 : ");
		String stdName = sc.next();
		
		System.out.print("나이 입력 : ");
		int stdAge = sc.nextInt();
		
		System.out.print("전공 입력 : ");
		String stdMajor = sc.next();
		
		Student std = new Student();
		
		std.setStdName(stdName);
		std.setStdAge(stdAge);
		std.setMajor(stdMajor);
		
		int result = service.insertStd(std);
		
		if(result > 0) { 
			System.out.println("\n" + stdName + " 학생이 등록되었습니다.\n");
		} else { 
			System.out.println("\n***등록 실패***\n");
		}
		
		
	}


	/** 2. 학생 전체 조회
	 * 
	 */
	private void selectStd() throws Exception {
		
		System.out.println("===학생 전체 조회===");
		
		List<Student> stdList = service.selectStd();
		
		if(stdList.isEmpty()){
			System.out.println("\n***조회 결과가 없습니다***\n");
			return;
		}
		
	
		for(Student std : stdList) {
			System.out.println(std);
		};
	}


	/** 3. 학생 정보 수정 ( 이름 , 나이 , 전공 )
	 * UPDATE
	 * 고유번호 입력받은 후에 수정 진행
	 */
	private void updateStd() throws Exception {
		
		System.out.println("===학생 정보 수정===");
		
		System.out.print("수정할 학생의 번호 입력 : ");
		int input = sc.nextInt();
		
		int result = service.updateStd(input);
		
		if(result == 0) {
			System.out.println("존재하지 않는 학생 번호 입니다");
			return;
		}
		
		System.out.print("수정할 이름 입력 : ");
		String name = sc.next();
		
		System.out.print("수정할 나이 입력 : ");
		int age = sc.nextInt();
		
		System.out.print("수정할 전공 입력 : ");
		String major = sc.next();
		
		int update = service.updateResult(name, age, major, input);
		
		if(update > 0) System.out.println(input + "번 학생의 정보를 수정하는데 성공했습니다");
		else			System.out.println("수정이 실패했습니다");
		
	}


	/** 4. 학번 기준 삭제
	 * 삭제하기 전에 다시한번 삭제하겠냐는 문구 추가해보기
	 * 3에서 사용한 번호 확인 서비스 재사용
	 */
	private void deleteStd() throws Exception {
		
		System.out.println("===학생 삭제===");
		
		System.out.print("삭제할 학생의 학생 번호 입력 : ");
		int input = sc.nextInt();
		
		int result = service.updateStd(input);
		
		if(result == 0) {
			System.out.println("존재하지 않는 학생 번호 입니다");
			return;
		}
		
		
		System.out.print("정말 삭제하시겠습니까? 한번 삭제하시면 복구가 불가능합니다 (Y/N) : ");
		String deleteYN = sc.next().toUpperCase();
		if(deleteYN.equals("N")) {
			System.out.println("삭제 취소. 메인메뉴로 돌아갑니다."); return;
		} else if(deleteYN.equals("Y")) {
			System.out.println(input + "번 학생의 정보를 삭제합니다.");
			int remove = service.deleteStd(input);
		} else System.out.println("잘못된 입력입니다");
		
		
	}


	/** 5. 특정 전공 학생만 필터링 조회
	 * 전공 입력 받은 후 출력
	 */
	private void selectMajor() throws Exception {
		
		System.out.println("===전공별 학생 조회===");
		
		System.out.print("전공 입력 : ");
		String input = sc.next();
				
		List<Student> stdList = service.selectMajor(input);
		
		if(stdList.isEmpty()){
			System.out.println("\n***조회 결과가 없습니다***\n");
			return;
		}
		
	
		for(Student std : stdList) {
			System.out.println(std);
		};
		
		
		
		
	}
	
	
	
	/** 6. 입력받은 나이 이상 OR 이하 학생 조회
	 * 첫 질문으로 나이를 물어본뒤 이상 조회 할지 이하 조회할지 다시묻기
	 * 서비스 2개를 이용해 조회해볼 생각
	 */
	private void selectAge() throws Exception{
		System.out.println("===나이 검색===");

		
		System.out.print("입력받을 나이 : ");
		int age = sc.nextInt();
		
		
		while(true) {
			System.out.print("입력한 나이 이상 검색을 원하시면 1번 / 미만 검색을 원하시면 2번을 입력해주세요 : ");
			int input = sc.nextInt();
			sc.nextLine();
			if(input == 1) {
				System.out.println(age + "살 이상 검색 결과");

				List<Student> stdList = service.selectAgeUp(age);
				for(Student std : stdList) {
					System.out.println(std);
				}; break;
			} else if(input == 2) {
				System.out.println(age + "살 미만 검색 결과");

				List<Student> stdList = service.selectAgeDown(age);
				for(Student std : stdList) {
					System.out.println(std);
				}; break;
			} else System.out.println("잘못 입력하셨습니다. 다시 입력해주세요"); 
		}
		
		
		
	


	}

	
	
}
