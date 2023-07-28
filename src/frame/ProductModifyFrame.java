package frame;

import java.awt.EventQueue;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import entity.Product;
import entity.ProductCategory;
import entity.ProductColor;
import service.ProductCategoryService;
import service.ProductColorService;
import service.ProductService;
import utils.CustomSwingComboBoxUtil;
import utils.CustomSwingTextUtil;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;

public class ProductModifyFrame extends JFrame {

	private JPanel contentPane;
	private JTextField productNameTextField;
	private JTextField productPriceTextField;
	private JTextField productIdTextField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProductModifyFrame frame = new ProductModifyFrame(1);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ProductModifyFrame(int productId) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 343);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel titleLabel = new JLabel("상품 수정");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBounds(12, 10, 410, 39);
		contentPane.add(titleLabel);
		
		JLabel productIdLabel = new JLabel("상품번호");
		productIdLabel.setHorizontalAlignment(SwingConstants.CENTER);
		productIdLabel.setBounds(12, 64, 63, 28);
		contentPane.add(productIdLabel);
		
		productIdTextField = new JTextField();		
		productIdTextField.setColumns(10);
		productIdTextField.setBounds(87, 64, 335, 28);
		productIdTextField.setEnabled(false);
		
		JLabel productNameLabel = new JLabel("상품명");
		productNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		productNameLabel.setBounds(12, 102, 63, 28);
		contentPane.add(productNameLabel);
		
		productNameTextField = new JTextField();
		productNameTextField.setBounds(87, 102, 335, 28);
		contentPane.add(productNameTextField);
		productNameTextField.setColumns(10);
		
		JLabel productPriceLabel = new JLabel("가격");
		productPriceLabel.setHorizontalAlignment(SwingConstants.CENTER);
		productPriceLabel.setBounds(12, 140, 63, 28);
		contentPane.add(productPriceLabel);
		
		productPriceTextField = new JTextField();
		productPriceTextField.setColumns(10);
		productPriceTextField.setBounds(87, 140, 335, 28);
		contentPane.add(productPriceTextField);
		
		JLabel productColorLabel = new JLabel("색상");
		productColorLabel.setHorizontalAlignment(SwingConstants.CENTER);
		productColorLabel.setBounds(12, 178, 63, 28);
		contentPane.add(productColorLabel);
		
		JComboBox productColorComboBox = new JComboBox();
		CustomSwingComboBoxUtil.setComboBoxModel(productColorComboBox, ProductColorService.getInstance().getProductColorNameList());
		productColorComboBox.setBounds(87, 178, 335, 28);
		contentPane.add(productColorComboBox);
		
		JLabel productCategoryLabel = new JLabel("카테고리");		
		productCategoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
		productCategoryLabel.setBounds(12, 216, 63, 28);
		contentPane.add(productCategoryLabel);
		
		JComboBox productCategoryComboBox = new JComboBox();
		CustomSwingComboBoxUtil.setComboBoxModel(productCategoryComboBox, ProductCategoryService.getInstance().getProductCategoryNameList());
		productCategoryComboBox.setBounds(87, 216, 335, 28);
		contentPane.add(productCategoryComboBox);
					
		JButton modifySubmitButton = new JButton("수정하기");
		modifySubmitButton.setBounds(12, 254, 410, 40);
		modifySubmitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String productName = productNameTextField.getText();
				if(CustomSwingTextUtil.checkTextEmpty(contentPane, productName)) {return;}			
				String productPrice = productPriceTextField.getText();
				if(CustomSwingTextUtil.checkTextEmpty(contentPane, productPrice)) {return;}
				String productColorName = (String) productColorComboBox.getSelectedItem();
				if(CustomSwingTextUtil.checkTextEmpty(contentPane, productColorName)) {return;}
				String productCategoryName = (String) productCategoryComboBox.getSelectedItem();
				if(CustomSwingTextUtil.checkTextEmpty(contentPane, productCategoryName)) {return;}
				 
				Product product = Product.builder()
					.product_id(productId)
					.productName(productName)
					.productPrice(Integer.parseInt(productPrice))
					.productColor(ProductColor.builder().productColorName(productColorName).build())
					.productCategory(ProductCategory.builder().productCategoryName(productCategoryName).build())
					.build();
						

				if(!ProductService.getInstance().modifyProduct(product)) {
					JOptionPane.showMessageDialog(contentPane, "상품 수정 중 오류 발생","수정 오류", JOptionPane.ERROR_MESSAGE);
					return;
				}

				JOptionPane.showMessageDialog(contentPane, "상품 수정 완료", "수정 완료", JOptionPane.PLAIN_MESSAGE);
				ProductSearchFrame.getInstance().setSearchProductTableModel();
				dispose();
				

			}
		});		
		contentPane.add(modifySubmitButton);		
		contentPane.add(productIdTextField);
		
		Product product = ProductService.getInstance().getProductByProductId(productId);
		
		if(product != null) {
			productIdTextField.setText(Integer.toString(product.getProduct_id()));
			productNameTextField.setText(product.getProductName());
			productPriceTextField.setText(Integer.toString(product.getProductPrice()));
			productColorComboBox.setSelectedItem(product.getProductColor().getProductColorName());
			productCategoryComboBox.setSelectedItem(product.getProductCategory().getProductCategoryName());
		}
	}
}
