package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import config.DBConnectionMgr;

public class Main {
	public static void main(String[] args) {
		DBConnectionMgr pool = DBConnectionMgr.getInstance();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = pool.getConnection(); // db와 연결 상태를 변수에 저장
			String sql = "select * from user_tb"; // 쿼리문
			pstmt = con.prepareStatement(sql); // 연결된 db에 쿼리문 보내기
			
			rs = pstmt.executeQuery(); // ResultSet은 리스트 형태의 자료형 (쿼리의 결과를 출력하기 위해 리스트로 저장)
			while(rs.next()) { // select결과 를 열 단위로 이동하며 true,false값 리턴
				System.out.println("번호\t|\t아이디\t|\t비밀번호");
				System.out.println(rs.getInt(1) + "\t|\t" + rs.getString(2) +"\t|\t" + rs.getString(3)); // 2번 컬럼 호출
			}
			} catch (Exception e) {
			e.printStackTrace();
		}finally {
			pool.freeConnection(con, pstmt, rs);// 연결 종료 및 객체 소멸
		}
		
		
		
		
		try {
			con = pool.getConnection(); // 데이터 베이스 연결 및 변수 대입
			String sql = "insert into user_tb values(0, ?, ?)"; // 쿼리문
			pstmt = con.prepareStatement(sql); // 쿼리문 가공 준비
			pstmt.setString(1, "ggg"); // 쿼리문 가공 및 ?에 대입
			pstmt.setString(2, "1234");
			int successCount = pstmt.executeUpdate(); // 쿼리문 실행
			System.out.println("insert 성공 횟수: " + successCount); // 그냥 확인문
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			pool.freeConnection(con, pstmt);// 연결 종료 및 객체 소멸
		}
			
		
		
		
		
		try {
			con = pool.getConnection(); // db와 연결 상태를 변수에 저장
			String sql = "select * from user_tb"; // 쿼리문
			pstmt = con.prepareStatement(sql); // 연결된 db에 쿼리문 보내기
			
			rs = pstmt.executeQuery(); // ResultSet은 리스트 형태의 자료형 (쿼리의 결과를 리스트로 저장)
			while(rs.next()) { // db를 열 단위로 이동하며 true,false값 리턴
				System.out.println("번호\t|\t아이디\t|\t비밀번호");
				System.out.println(rs.getInt(1) + "\t|\t" + rs.getString(2) +"\t|\t" + rs.getString(3)); // 2번 컬럼 호출
			}
			} catch (Exception e) {
			e.printStackTrace();
		}finally {
			pool.freeConnection(con, pstmt, rs);// 연결 종료 및 객체 소멸
		}
		
		
	}
}
