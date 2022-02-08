package com.spider.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;

import com.spider.pojo.MyFile;
import com.spider.util.FileUtil;

import javax.swing.tree.DefaultMutableTreeNode;

public class ShowFileDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.启动应用程序
	 */
	public static void main(String[] args) {
		try {
			ShowFileDialog dialog = new ShowFileDialog("D:\\SpiderSaveFile\\Save");
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);//二级窗口的关闭
			dialog.setVisible(true);//窗口可见度
		} catch (Exception e) {
			e.printStackTrace();
		}//try-cater语句块，避免读写异常
	}

	/**
	 * Create the dialog.创建对话框
	 */
	public ShowFileDialog(String basePath) {
		setBounds(100, 100, 1037, 645);
		setModal(true);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		contentPanel.setLayout(null);
		JPanel treePanel = new JPanel();
		treePanel.setBackground(Color.WHITE);
		treePanel.setBounds(10, 10, 221, 586);
		contentPanel.add(treePanel);
		treePanel.setLayout(null);//配置目录面板
		
		JPanel showPanel = new JPanel();
		showPanel.setBackground(Color.WHITE);
		showPanel.setBounds(241, 10, 770, 586);
		contentPanel.add(showPanel);
		showPanel.setLayout(null);
		
		// 文本展示框
		JTextArea textArea = new JTextArea();
		textArea.setBounds(10, 10, 750, 566);
		JScrollPane jTextAreaScrollPane = new JScrollPane(textArea);
		jTextAreaScrollPane.setBounds(10, 10, 750, 566);
		showPanel.add(jTextAreaScrollPane);//文本显示面板
		
		JTree fileTree = new JTree();//返回带有示例模型的 JTree。
		// 文件目录树
		fileTree.setModel(FileUtil.createTree(basePath));
		fileTree.setBounds(10, 10, 201, 566);
		JScrollPane jFileTreeScrollPane = new JScrollPane(fileTree);//加了文件目录树的面板
		jFileTreeScrollPane.setBounds(10, 10, 201, 566);
		treePanel.add(jFileTreeScrollPane);//文件树相关配置
		
		// 监听文件目录树点击状态
		fileTree.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent arg0) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) fileTree.getLastSelectedPathComponent(); //返回当前选择的第一个节点中的最后一个路径组件作为根节点
				MyFile myFile = (MyFile) node.getUserObject();//返回与此节点关联的 Object 值。
				if(myFile.getFile().isFile()) {
					// 读取文件
					try(BufferedReader br = new BufferedReader(new FileReader(myFile.getFile()))) {
						StringBuffer sb = new StringBuffer();
						String str = "";
						while ((str = br.readLine()) != null) {
							sb.append(str + "\r\n");
						}
						textArea.setText(sb.toString());//输出文件内容
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
			}
		});
	}
}

