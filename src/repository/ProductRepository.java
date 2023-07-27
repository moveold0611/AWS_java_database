package repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import config.DBConnectionMgr;
import entity.Product;
import entity.ProductColor;
import service.ProductService;

public class ProductRepository {
	private DBConnectionMgr pool;
	private static ProductRepository instance;
	
	public static ProductRepository getInstance() {
		if(instance == null) {
			instance = new ProductRepository();
		}
		return instance;
	}
	
	private ProductRepository() {
		pool = DBConnectionMgr.getInstance();
	}
	
	
	public int saveProduct(Product product) {
		Connection con = null;
		CallableStatement cstmt = null;
		
		int successCount = 0;
		
		try {
			con = pool.getConnection();
			String sql = "{ call p_insert_product(?, ?, ?, ?) }";			
			cstmt = con.prepareCall(sql);			
			cstmt.setString(1, product.getProductName());
			cstmt.setInt(2, product.getProductPrice());
			cstmt.setString(3, product.getProductColor().getProductColorName());
			cstmt.setString(4, product.getProductCategory().getProductCategoryName());
			successCount = cstmt.executeUpdate();	

		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(cstmt != null) {
				try {
					cstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			pool.freeConnection(con);
		}
		return successCount;
	}
	
	public Product findProductByProduct(String productName) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Product product = null;
		try {
			con = pool.getConnection();
			String sql = 
					"select * "
					+ "from "
					+ "product_tb "
					+ "where "
					+ "product_name = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, productName);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				product = product.builder()
						.product_id(rs.getInt(1))
						.productName(rs.getString(2))
						.productPrice(rs.getInt(3))
						.productCategoryId(rs.getInt(4))
						.productColorId(rs.getInt(5))
						.build();
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return product;
	}
	
	
	
}
