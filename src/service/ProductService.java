package service;

import entity.Product;
import repository.ProductRepository;

public class ProductService {
	private static ProductService instance;
	private ProductRepository productRepository;
	
	public static ProductService getInstance() {
		if(instance == null) {
			instance = new ProductService();
		}
		return instance;
	}
	
	private ProductService() {
		productRepository = ProductRepository.getInstance();
	}
	
		
	public boolean isProductDeplicated(String productName) {
		boolean result = false;
		result = productRepository.findProductByProduct(productName) != null;
		return result;
	}
	
	public boolean registerProduct(Product product) {
		boolean result;
		result = productRepository.saveProduct(product) > 0;
		return result;
	}
	
}
