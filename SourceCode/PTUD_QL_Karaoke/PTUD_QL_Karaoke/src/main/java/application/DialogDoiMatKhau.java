
package application;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;

import dao.TaiKhoanDAO;
import entity.TaiKhoan;
import helpers.DataValidator;
import helpers.MessageDialogHelpers;
import helpers.ShareData;


public class DialogDoiMatKhau extends JDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JPasswordField txtMatKhauMoi;
	private JPasswordField txtNhapLaiMK;
	private JButton btnXacNhan;
	private JButton btnDong;

	/**
	 * Create the dialog.
	 */
	public DialogDoiMatKhau() {
		
		/**Color & font **/
		Color whiteColor = new Color(255, 255, 255);
		Color mainColor = new Color(88, 177, 159);
		
		Font tahoma16 = new Font("Tahoma", Font.PLAIN, 16);
		Font tahoma20 = new Font("Tahoma", Font.BOLD, 20);
		
		setFont(new Font("Dialog", Font.PLAIN, 16));
		setForeground(new Color(0, 0, 0));
		setTitle("\u0110\u1ED5i m\u1EADt kh\u1EA9u");
		setResizable(false);
		setBounds(100, 100, 493, 236);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(whiteColor);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblNewLabel_MKMoi = new JLabel("M\u1EADt kh\u1EA9u m\u1EDBi:");
		lblNewLabel_MKMoi.setFont(tahoma16);
		lblNewLabel_MKMoi.setBounds(37, 50, 150, 27);
		contentPanel.add(lblNewLabel_MKMoi);

		JLabel lblNewLabel = new JLabel("Nh\u1EADp l\u1EA1i m\u1EADt kh\u1EA9u:");
		lblNewLabel.setFont(tahoma16);
		lblNewLabel.setBounds(37, 102, 150, 27);
		contentPanel.add(lblNewLabel);

		txtMatKhauMoi = new JPasswordField();
		txtMatKhauMoi.setBounds(197, 50, 242, 27);
		contentPanel.add(txtMatKhauMoi);
		txtMatKhauMoi.setColumns(10);

		txtNhapLaiMK = new JPasswordField();
		txtNhapLaiMK.setBounds(197, 105, 242, 27);
		contentPanel.add(txtNhapLaiMK);
		txtNhapLaiMK.setColumns(10);

		JPanel panel = new JPanel();
		panel.setBackground(whiteColor);
		panel.setBorder(new LineBorder(new Color(192, 192, 192), 0));
		panel.setBounds(0, 229, 434, 50);
		contentPanel.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("\u0110\u1ED5i m\u1EADt kh\u1EA9u");
		lblNewLabel_1.setFont(tahoma20);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(10, 0, 467, 39);
		contentPanel.add(lblNewLabel_1);

		btnXacNhan = new JButton("X\u00E1c nh\u1EADn");
		btnXacNhan.setBorder(null);
		btnXacNhan.setBounds(107, 158, 111, 30);
		btnXacNhan.setBackground(mainColor);
		btnXacNhan.setForeground(whiteColor);
		btnXacNhan.setFocusable(false);
		btnXacNhan.setFont(tahoma16);
		contentPanel.add(btnXacNhan);

		btnDong = new JButton("\u0110\u00F3ng");
		btnDong.setBorder(null);
		btnDong.setBounds(266, 158, 109, 30);
		contentPanel.add(btnDong);
		btnDong.setFocusable(false);
		btnDong.setIcon(null);
		btnDong.setFont(tahoma16);
		btnDong.setBackground(mainColor);
		btnDong.setForeground(whiteColor);
		
		btnXacNhan.addActionListener(this);
		btnDong.addActionListener(this);
	}

	private void dataValidate(StringBuilder sb) {
		DataValidator.validateEmpty(txtNhapLaiMK, sb, "Ch??a nh???p l???i m???t kh???u");
		DataValidator.validateEmpty(txtMatKhauMoi, sb, "M???t kh???u kh??ng ???????c ????? tr???ng");
		
		DataValidator.validateMatKhau(txtMatKhauMoi, sb,
				"M???t kh???u ph???i c?? ??t nh???t 1 ch??? th?????ng, 1 ch??? hoa, 1 k?? t??? ?????t bi???t, kh??ng c?? kho???ng tr???ng v?? t???i thi???u 8 k?? t???");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		
		// x??c nh???n
		if (o.equals(btnXacNhan)) {
			StringBuilder sb = new StringBuilder();
			dataValidate(sb);
			if (sb.length() > 0) {
				MessageDialogHelpers.showErrorDialog(contentPanel, "L???i",
						sb.toString());
				return;
			}

			if (MessageDialogHelpers.showConfirmDialog(contentPanel, "L???i",
					"B???n c?? ch???c mu???n ?????i m???t kh???u") == JOptionPane.NO_OPTION) {
				return;
			}

			if (txtMatKhauMoi.getText().equals(txtNhapLaiMK.getText())) {
				try {
					TaiKhoan tk = ShareData.taiKhoanDangNhap;
					tk.setMatKhau(txtMatKhauMoi.getText());
					TaiKhoanDAO tkDAO = new TaiKhoanDAO();
					if (tkDAO.updateMatKhau(tk)) {
						MessageDialogHelpers.showMessageDialog(contentPanel, "Th??ng b??o",
								"M???t kh???u ???? c???p nh???t th??nh c??ng");
						dispose();
					} else {
						MessageDialogHelpers.showErrorDialog(contentPanel, "L???i", "C???p nh???t kh??ng th??nh c??ng");
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			} else {
				MessageDialogHelpers.showErrorDialog(contentPanel, "L???i",
						"M???t kh???u v?? nh???p l???i m???t kh???u ch??a gi???ng nhau");
			}
		}
		
		// ????ng
		if (o.equals(btnDong)) {
			dispose();
		}
		
	}
	
}
