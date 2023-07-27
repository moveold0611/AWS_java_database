package frame;

import java.awt.EventQueue;
import java.awt.TextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import entity.ProductCategory;
import service.ProductCategoryService;
import utils.CustomSwingTextUtil;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;

public class ProductCategoryRegisterFrame extends JFrame {

	private JPanel contentPane;
	private JTextField productCategoryTextField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProductCategoryRegisterFrame frame = new ProductCategoryRegisterFrame();
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
	public ProductCategoryRegisterFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel titleLabel = new JLabel("상품 카테고리 등록");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBounds(12, 10, 410, 62);
		contentPane.add(titleLabel);
		
		JLabel productCategoryNameLabel = new JLabel("카테고리명");
		productCategoryNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		productCategoryNameLabel.setBounds(12, 82, 77, 35);
		contentPane.add(productCategoryNameLabel);
		
		productCategoryTextField = new JTextField();
		productCategoryTextField.setBounds(101, 82, 321, 35);
		contentPane.add(productCategoryTextField);
		productCategoryTextField.setColumns(10);
		
		JButton registerSubmitButton = new JButton("등록");
		registerSubmitButton.setBounds(12, 127, 410, 124);
		registerSubmitButton.addMouseListener(new MouseAdapter() {		
			@Override
			public void mouseClicked(MouseEvent e) {
				String productCategoryName = productCategoryTextField.getText();
				if(CustomSwingTextUtil.checkTextEmpty(contentPane, productCategoryName)) {
					return;
				}
				
				if(ProductCategoryService.getInstance().isProductCategoryNameCategoryDeplicated(productCategoryName)) {
					JOptionPane.showMessageDialog(contentPane, "이미 존재하는 카테고리입니다.", "등록오류", JOptionPane.ERROR_MESSAGE);
					return;
				}
				ProductCategory productCategory = ProductCategory.builder()
						.productCategoryName(productCategoryName)
						.build();
				if(!ProductCategoryService.getInstance().resisterProductCategory(productCategory)) {
					JOptionPane.showMessageDialog(contentPane, "카테고리 등록 중 오류 발생", "등록오류", JOptionPane.ERROR_MESSAGE);
					return;
				}
				JOptionPane.showMessageDialog(contentPane, "새로운 카테고리 등록 완료", "등록 완료", JOptionPane.PLAIN_MESSAGE);		
				CustomSwingTextUtil.clearTextField(productCategoryTextField);
			}			
		});
		
		contentPane.add(registerSubmitButton);
	}
	

}
