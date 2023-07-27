package entity;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Product {
	private int product_id;
	private String productName;
	private int productPrice;
	private int productColorId; // productColorId, productColorName 
	private int productCategoryId; // productCategoryId, productCategoryName
	
	private ProductColor productColor;
	private ProductCategory productCategory;
}
