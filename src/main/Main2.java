package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import config.DBConnectionMgr;

public class Main2 {

	public static void insertProduct(int productCode, String productName) {
		DBConnectionMgr pool = DBConnectionMgr.getInstance();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = pool.getConnection();
			String sql = "insert into product_tb values(?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, productCode);
			pstmt.setString(2, productName);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			pool.freeConnection(con, pstmt);
		}
	}
	
	public static Map<String, Object> selectProduct(int productCode) {
		Map<String, Object> resultMap = new HashMap();
		
		DBConnectionMgr pool = DBConnectionMgr.getInstance();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = pool.getConnection();
			String sql = "select * from product_tb where product_code = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, productCode);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				resultMap.put("product_code", rs.getInt(1));
				resultMap.put("product_name", rs.getString(2));
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			pool.freeConnection(con, pstmt, rs);
		}		
		return resultMap;
		
	}
	
	public static void main(String[] args) {
				
//		insertProduct(20230708, "상품8");
		System.out.println(selectProduct(20230708));
		
		
//		DBConnectionMgr pool = DBConnectionMgr.getInstance();
//		
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		
//		try {
//			con = pool.getConnection();
//			String sql = "insert into product_tb values(?, ?)";
//			pstmt = con.prepareStatement(sql);
//			pstmt.setInt(1, 20230706);
//			pstmt.setString(2, "상품6");
//			int insert = pstmt.executeUpdate();
//			System.out.println(insert);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}finally {
//			pool.freeConnection(con, pstmt);
//
//		}
//		
//		
//		try {
//			con = pool.getConnection();
//			String sql = "select * from product_tb";
//			pstmt = con.prepareStatement(sql);
//			
//			rs = pstmt.executeQuery();
//			while(rs.next()) {
//				System.out.println(rs.getInt(1) + "\t|\t" + rs.getString(2));
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}finally {
//			pool.freeConnection(con, pstmt);
//		}
		
		
	}

}
