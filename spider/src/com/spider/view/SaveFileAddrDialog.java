package com.spider.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;//awt包布局及响应
import java.io.File;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;//swing窗口函数相关申明

/**
 * 保存抓取文件地址弹窗
 * @author 千羽
 *
 */
public class SaveFileAddrDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	
	private JTextField saveFileAddrTextField;


	/**
	 * Create the dialog.
	 */
	public SaveFileAddrDialog(JTextField saveFileAddrTextField) {
		setBounds(100, 100, 392, 146);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);//窗口可关闭
		getContentPane().setLayout(new BorderLayout());//东南西北中布局
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));//边框设置
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);//布局清空
		setResizable(false);//固定窗体大小
		setLocationRelativeTo(null);//窗口显示在屏幕中间
		setModal(true);//设为模式窗口
		
		{
			JLabel label = new JLabel("输入地址");
			label.setBounds(10, 10, 54, 15);
			contentPanel.add(label);//文本标签
		}
		{
			textField = new JTextField();
			textField.setBounds(66, 7, 303, 21);//设置 位置大小
			contentPanel.add(textField);
			textField.setColumns(10);//设置此文本字段中的列数。列是近似平均字符宽度，它与平台有关。
		}
		{
			JButton btnNewButton_1 = new JButton("确认抓取文件保存地址");
			btnNewButton_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					// 确认路径是否存在
					File file = new File(textField.getText().trim());
					
					if(!file.exists()) {
						JOptionPane.showMessageDialog(null, "地址不存在");//弹出文本信息，使用该函数一般为null
						return;
					} else {
						// 保存路径
						saveFileAddrTextField.setText(textField.getText());//获得textfeil里的文本
						saveFileAddrTextField.repaint();//刷新savefileaddrtextfileld值
						JOptionPane.showMessageDialog(null, "设置成功");
					}
				}
			});
			btnNewButton_1.setBounds(10, 54, 359, 23);
			contentPanel.add(btnNewButton_1);//确定按键
		}
	}

}
