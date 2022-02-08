package com.spider.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * 保存抓取日志地址弹窗
 * @author 千羽
 *
 */
public class SaveLogAddrDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	
	private JTextField saveLogAddrTextField;

	/**
	 * Create the dialog.
	 */
	public SaveLogAddrDialog(JTextField saveLogAddrTextField) {
		setBounds(100, 100, 392, 146);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		setResizable(false);
		setModal(true);//指定为模式窗口
		
		setLocationRelativeTo(null);
		{
			JLabel label = new JLabel("输入地址");
			label.setBounds(10, 10, 54, 15);
			contentPanel.add(label);
		}
		{
			textField = new JTextField();
			textField.setBounds(66, 7, 303, 21);
			contentPanel.add(textField);
			textField.setColumns(10);//设置其宽度
		}
		{
			JButton btnNewButton_1 = new JButton("确认日志文件保存地址");
			btnNewButton_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// 确认路径是否存在
					File file = new File(textField.getText().trim());
					
					if(!file.exists()) {
						JOptionPane.showMessageDialog(null, "地址不存在");
						return;
					} else {
						// 保存路径
						saveLogAddrTextField.setText(textField.getText());
						saveLogAddrTextField.repaint();
						JOptionPane.showMessageDialog(null, "设置成功");
					}
				}
			});
			btnNewButton_1.setBounds(10, 54, 359, 23);
			contentPanel.add(btnNewButton_1);
		}
	}

}
