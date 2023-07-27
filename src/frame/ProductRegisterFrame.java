package frame;

import java.awt.EventQueue;
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

public class ProductRegisterFrame extends JFrame {

	private JPanel contentPane;
	private JTextField productNameTextField;
	private JTextField productPriceTextField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProductRegisterFrame frame = new ProductRegisterFrame();
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
	public ProductRegisterFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel titleLabel = new JLabel("상품 등록");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBounds(12, 10, 410, 39);
		contentPane.add(titleLabel);
		
		JLabel productNameLabel = new JLabel("상품명");
		productNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		productNameLabel.setBounds(12, 59, 63, 28);
		contentPane.add(productNameLabel);
		
		productNameTextField = new JTextField();
		productNameTextField.setBounds(87, 59, 335, 28);
		contentPane.add(productNameTextField);
		productNameTextField.setColumns(10);
		
		JLabel productPriceLabel = new JLabel("가격");
		productPriceLabel.setHorizontalAlignment(SwingConstants.CENTER);
		productPriceLabel.setBounds(12, 97, 63, 28);
		contentPane.add(productPriceLabel);
		
		productPriceTextField = new JTextField();
		productPriceTextField.setColumns(10);
		productPriceTextField.setBounds(87, 97, 335, 28);
		contentPane.add(productPriceTextField);
		
		JLabel productColorLabel = new JLabel("색상");
		productColorLabel.setHorizontalAlignment(SwingConstants.CENTER);
		productColorLabel.setBounds(12, 135, 63, 28);
		contentPane.add(productColorLabel);
		
		JComboBox productColorComboBox = new JComboBox();
		CustomSwingComboBoxUtil.setComboBoxModel(productColorComboBox, ProductColorService.getInstance().getProductColorNameList());
		productColorComboBox.setBounds(87, 135, 335, 28);
		contentPane.add(productColorComboBox);
		
		JLabel productCategoryLabel = new JLabel("카테고리");		
		productCategoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
		productCategoryLabel.setBounds(12, 173, 63, 28);
		contentPane.add(productCategoryLabel);
		
		JComboBox productCategoryComboBox = new JComboBox();
		CustomSwingComboBoxUtil.setComboBoxModel(productCategoryComboBox, ProductCategoryService.getInstance().getProductCategoryNameList());
		productCategoryComboBox.setBounds(87, 173, 335, 28);
		contentPane.add(productCategoryComboBox);
			
		
		JButton registerSubmitButton = new JButton("등록하기");
		registerSubmitButton.setBounds(12, 211, 410, 40);
		registerSubmitButton.addMouseListener(new MouseAdapter() {
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
					.productName(productName)
					.productPrice(Integer.parseInt(productPrice))
					.productColor(ProductColor.builder().productColorName(productColorName).build())
					.productCategory(ProductCategory.builder().productCategoryName(productCategoryName).build())
					.build();
					
				if(ProductService.getInstance().isProductDeplicated(productName)) {
					JOptionPane.showMessageDialog(contentPane, "이미 존재하는 이름입니다.", "등록 오류", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(!ProductService.getInstance().registerProduct(product)) {
					JOptionPane.showMessageDialog(contentPane, "상품 등록 중 오류 발생","등록 오류", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				JOptionPane.showMessageDialog(contentPane, "상품 등록 완료", "등록 완료", JOptionPane.PLAIN_MESSAGE);
				CustomSwingTextUtil.clearTextField(productNameTextField);
				CustomSwingTextUtil.clearTextField(productPriceTextField);	
				productColorComboBox.setSelectedIndex(0);
				productCategoryComboBox.setSelectedIndex(0);
			}
		});
		
		contentPane.add(registerSubmitButton);
	}

}
