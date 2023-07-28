package frame;

import java.awt.EventQueue;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

import entity.Product;
import service.ProductService;
import utils.CustomSwingTableUtil;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class ProductSearchFrame extends JFrame {
	private JComboBox searchTypeComboBox;
	private DefaultTableModel searchProductTableModel; 
	private JPanel contentPane;
	private JTextField searchTextField;
	private JTable productTable;
	private JButton productRemoveButton;
	private JButton productModifyButton;

	private static ProductSearchFrame instance;
	
	public static ProductSearchFrame getInstance() {
		if(instance == null) {
			instance = new ProductSearchFrame();
		}
		return instance;
	}
	
	
	
	
	
	public void setSearchProductTableModel() {
		String searchOption = (String) searchTypeComboBox .getSelectedItem();
		String searchValue = searchTextField.getText();
							
		List<Product> searchProductList = ProductService.getInstance().searchProduct(searchOption, searchValue);
		String[][] searchProductModelArray = CustomSwingTableUtil.searchProductListToArray(searchProductList);
		
		searchProductTableModel = new DefaultTableModel(
				searchProductModelArray,
				new String[] {
					"product_id", 
					"product_price", 
					"product_color_id", 
					"product_color_name", 
					"product_category_id",
					"product_category_name"
				}
			);		
		productTable.setModel(searchProductTableModel);
		productRemoveButton.setEnabled(false);
		productModifyButton.setEnabled(false);
	}
	

	
	
	
	

	


	
	/**
	 * Create the frame.
	 */
	private ProductSearchFrame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1000, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel titleLabel = new JLabel("상품 조회");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBounds(12, 10, 68, 22);
		contentPane.add(titleLabel);
		
		
		
		productModifyButton = new JButton("수정");		
		productModifyButton.setBounds(766, 10, 97, 23);
		productModifyButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Product product = null;
				if(!productModifyButton.isEnabled()) {return;}		
				
				ProductModifyFrame productModifyFrame =
						new ProductModifyFrame(Integer.parseInt((String) searchProductTableModel
								.getValueAt(productTable.getSelectedRow(), 0)));
				productModifyFrame.setVisible(true);
				
//				if(!ProductService.getInstance().updateProduct(product)) {
//					JOptionPane.showMessageDialog(contentPane, "상품 정보 수정 실패","수정 오류", JOptionPane.ERROR_MESSAGE);
//					return;
//				}
//				
//				JOptionPane.showMessageDialog(contentPane, "선택한 상품 정보를 수정하였습니다.","상품 정보 수정", JOptionPane.PLAIN_MESSAGE);
				setSearchProductTableModel();
			}
		});
		contentPane.add(productModifyButton);
		
		productRemoveButton = new JButton("삭제");
		productRemoveButton.setBounds(875, 10, 97, 23);
		productRemoveButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!productRemoveButton.isEnabled()) {return;}
				
				int productId = Integer.parseInt((String) searchProductTableModel
						.getValueAt(productTable.getSelectedRow(), 0));
				if(!ProductService.getInstance().removeProduct(productId)) {
					JOptionPane.showMessageDialog(contentPane, "상품 삭제 중 오류 발생", "삭제 오류", JOptionPane.ERROR_MESSAGE);
					return;
				}
				JOptionPane.showMessageDialog(contentPane, "선택한 상품을 삭제하였습니다.","상품 삭제",JOptionPane.PLAIN_MESSAGE);
				setSearchProductTableModel();
				
			}
		});
		contentPane.add(productRemoveButton);
		
		
		JLabel searchLabel = new JLabel("검색");
		searchLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		searchLabel.setBounds(527, 41, 60, 22);
		contentPane.add(searchLabel);
			
		searchTypeComboBox = new JComboBox();
		searchTypeComboBox.setModel(new DefaultComboBoxModel(new String[] {"전체", "상품명", "색상", "카테고리"}));
		searchTypeComboBox.setBounds(595, 41, 131, 22);
		
		searchTextField = new JTextField();
		searchTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {				
					setSearchProductTableModel();
				}
			}
		});
		searchTextField.setBounds(737, 42, 235, 21);
		contentPane.add(searchTextField);
		searchTextField.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 74, 950, 377);
		contentPane.add(scrollPane);
		
		productTable = new JTable();
		productTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				productRemoveButton.setEnabled(true);
				productModifyButton.setEnabled(true);
			}
		});
		
		scrollPane.setViewportView(productTable);
		
		contentPane.add(searchTypeComboBox);
	}
}
