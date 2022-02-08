package com.spider.view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Executors;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.jsoup.nodes.Document;

import com.spider.util.FileUtil;
import com.spider.util.SpiderUtil;
import com.spider.util.StringUtil;
import com.spider.util.ThreadUtil;

public class HomePage extends JFrame {

	private JPanel contentPane;
	private JTextField saveFileAddrTextField;
	private JTextField saveLogAddrTextField;
	private JButton startBtn;
	private JButton stopBtn;
	private JTextArea logTextArea;
	private JTextField threadSumTextField;
	private JTextField filterWordTextField;
	private static boolean IS_START = false; // 启动状态

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HomePage frame = new HomePage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 初始化
	 * 创建初始目录
	 */
	public void init() {
		// 创建初始路径 D:\\SpiderSaveFile\\Save
		File sfile = new File("D:\\SpiderSaveFile\\Save");
		File lfile = new File("D:\\SpiderSaveFile\\Log");
		if (!sfile.exists()) {  
			sfile.mkdirs();  
        }  
		if (!lfile.exists()) {  
			lfile.mkdirs();  
        }  
	}
	
	/**
	 * Create the frame.
	 */
	public HomePage() {
		init();
	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 555, 542);
		setResizable(false);
		setLocationRelativeTo(null);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu menu = new JMenu("设置");
		menuBar.add(menu);
		
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel msgPanel = new JPanel();
		msgPanel.setBackground(Color.WHITE);
		msgPanel.setBounds(10, 10, 529, 137);
		contentPane.add(msgPanel);
		msgPanel.setLayout(null);
		
		JLabel inputTextFieldLabel = new JLabel("抓取网址");
		inputTextFieldLabel.setBounds(10, 28, 54, 15);
		msgPanel.add(inputTextFieldLabel);
		
		JTextArea inputTextField = new JTextArea();
		JScrollPane JInputTextFieldScrollPane = new JScrollPane(inputTextField);
		inputTextField.setBounds(65, 10, 454, 56);
		JInputTextFieldScrollPane.setBounds(65, 10, 454, 56);
		msgPanel.add(JInputTextFieldScrollPane);
		
		startBtn = new JButton("开始");
		
		// 点击开始
		startBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logTextArea.setText("");
				stopBtn.setEnabled(true);
				startBtn.setEnabled(false);
				threadSumTextField.setEditable(false);
				filterWordTextField.setEditable(false);
				HomePage.IS_START = !HomePage.IS_START;
				
				// 输入校验
				
				// 网址输入校验
				String urlInput = inputTextField.getText();
				if(urlInput == null || "".equals(urlInput.trim())) {
					JOptionPane.showMessageDialog(null, "输入为空，请重新输入！");
					stopBtn.setEnabled(false);
					startBtn.setEnabled(true);
					threadSumTextField.setEditable(true);
					filterWordTextField.setEditable(true);
					return;
				}
				// 线程数输入校验
				String threadSum = threadSumTextField.getText();
				if(StringUtil.isNum(threadSum)) {
					int ts = Integer.parseInt(threadSum);
					// 线程数判断
					if(ts <= 0 || ts > 30) {
						JOptionPane.showMessageDialog(null, "线程数目输入错误，需要在 1-30 之间");
						stopBtn.setEnabled(false);
						startBtn.setEnabled(true);
						threadSumTextField.setEditable(true);
						filterWordTextField.setEditable(true);
						return;
					}
					ThreadUtil.setThreadPool(Executors.newFixedThreadPool(ts));
				} else {
					JOptionPane.showMessageDialog(null, "线程数输入错误");
					stopBtn.setEnabled(false);
					startBtn.setEnabled(true);
					threadSumTextField.setEditable(true);
					filterWordTextField.setEditable(true);
					return;
				}

				String[] urls = urlInput.split("\n");
				for (String url : urls) {
					ThreadUtil.getThreadPool().execute(() -> {
						logTextArea.setText("开始解析界面...\r\n首页地址:" + url + "\r\n====================================================\r\n\r\n");
						try {
							Document document = SpiderUtil.get(url);
							spider(url, saveFileAddrTextField.getText().trim(), saveLogAddrTextField.getText().trim() +  "\\" + (document == null ? new Random().nextLong() + "" : StringUtil.conviterTitle(SpiderUtil.get(url).title())));
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}); 
				}
			}
		});
		
		startBtn.setBounds(10, 76, 509, 23);
		msgPanel.add(startBtn);
		
		// 停止抓取
		stopBtn = new JButton("停止抓取");
		stopBtn.setEnabled(false);
		stopBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// 设置基础
					stopBtn.setEnabled(false);
					startBtn.setEnabled(true);
					threadSumTextField.setEditable(true);
					filterWordTextField.setEditable(true);
					HomePage.IS_START = !HomePage.IS_START;
					// 清除保存的地址
					set.clear();
					// 关闭所有线程
					ThreadUtil.stop();
					// 打印结束信息
					logTextArea.setText("\r\n抓取结束...\r\n");
				} catch (Exception e1) {
					// TODO: handle exception
				}
			}
		});
		stopBtn.setBounds(10, 104, 509, 23);
		msgPanel.add(stopBtn);
		
		JPanel logPanel = new JPanel();
		logPanel.setBackground(Color.WHITE);
		logPanel.setBounds(10, 293, 529, 189);
		contentPane.add(logPanel);
		logPanel.setLayout(null);
		
		logTextArea = new JTextArea();
		logTextArea.setBounds(10, 10, 509, 264);
		JScrollPane jsp = new JScrollPane(logTextArea);
		jsp.setBounds(10, 10, 509, 169);
		jsp.setVisible(true);
		logPanel.add(jsp);
		
		JPanel addrPanel = new JPanel();
		addrPanel.setBackground(Color.WHITE);
		addrPanel.setBounds(10, 157, 529, 126);
		contentPane.add(addrPanel);
		addrPanel.setLayout(null);
		
		JLabel saveFileAddrLabel = new JLabel("抓取文件存放地址");
		saveFileAddrLabel.setBounds(10, 10, 122, 15);
		addrPanel.add(saveFileAddrLabel);
		
		saveFileAddrTextField = new JTextField();
		saveFileAddrTextField.setEditable(false);
		saveFileAddrTextField.setText("D:\\SpiderSaveFile\\Save");
		saveFileAddrTextField.setBounds(118, 7, 401, 21);
		addrPanel.add(saveFileAddrTextField);
		saveFileAddrTextField.setColumns(10);
		
		JLabel saveLogAddrLabel = new JLabel("抓取日志存放地址");
		saveLogAddrLabel.setBounds(10, 37, 122, 15);
		addrPanel.add(saveLogAddrLabel);
		
		saveLogAddrTextField = new JTextField();
		saveLogAddrTextField.setEditable(false);
		saveLogAddrTextField.setText("D:\\SpiderSaveFile\\Log");
		saveLogAddrTextField.setColumns(10);
		saveLogAddrTextField.setBounds(118, 34, 401, 21);
		addrPanel.add(saveLogAddrTextField);
		
		JLabel threadSumLabel = new JLabel("开启线程数");
		threadSumLabel.setBounds(10, 65, 91, 15);
		addrPanel.add(threadSumLabel);
		
		threadSumTextField = new JTextField();
		threadSumTextField.setText("3");
		threadSumTextField.setColumns(10);
		threadSumTextField.setBounds(118, 62, 401, 21);
		addrPanel.add(threadSumTextField);
		
		JLabel filterWordLabel = new JLabel("过滤词设置");
		filterWordLabel.setBounds(10, 93, 91, 15);
		addrPanel.add(filterWordLabel);
		
		filterWordTextField = new JTextField();
		filterWordTextField.setColumns(10);
		filterWordTextField.setBounds(118, 90, 401, 21);
		addrPanel.add(filterWordTextField);
		
		JMenuItem saveFileMenuItem = new JMenuItem("设置文件存放路径");
		menu.add(saveFileMenuItem);
		saveFileMenuItem.addActionListener((e) -> {
			if(HomePage.IS_START) {
				JOptionPane.showMessageDialog(null, "先停止抓取再设置");
				return;
			}
			new SaveFileAddrDialog(saveFileAddrTextField).setVisible(true);
		});
		
		JMenuItem saveLogMenuItem = new JMenuItem("设置日志存放路径");
		menu.add(saveLogMenuItem);
		saveLogMenuItem.addActionListener((e) -> {
			if(HomePage.IS_START) {
				JOptionPane.showMessageDialog(null, "先停止抓取再设置");
				return;
			}
			new SaveLogAddrDialog(saveLogAddrTextField).setVisible(true);
		});
		
		JMenuItem lookMenuItem = new JMenuItem("查看抓取网页文件");
		menu.add(lookMenuItem);
		lookMenuItem.addActionListener((e) -> {
			if(HomePage.IS_START) {
				JOptionPane.showMessageDialog(null, "先停止抓取再查看");
				return;
			}
			new ShowFileDialog(saveFileAddrTextField.getText()).setVisible(true);
		});
		
		JMenuItem lookLogMenuItem = new JMenuItem("查看抓取日志信息");
		menu.add(lookLogMenuItem);
		lookLogMenuItem.addActionListener((e) -> {
			if(HomePage.IS_START) {
				JOptionPane.showMessageDialog(null, "先停止抓取再查看");
				return;
			}
			new ShowFileDialog(saveLogAddrTextField.getText()).setVisible(true);
		});
	}
	
	/**
	 * 递归抓取所有界面，并去重
	 */
	public static Set<String> set = new HashSet();//问题代码
	public void spider(String url, String baseDir, String logDir){
		Document document = null;
		try {
			document = SpiderUtil.get(url);
		} catch (Exception e1) {
			// ...
		}
		// 抓取为空结束
		if(document == null) return;
		// 过滤词过滤，不包含过滤词这结束
		if(!StringUtil.contain(document.title(), filterWordTextField.getText().trim())) return;
		// 抓取页面，并保存到相应分类目录
		final String baseDirect = baseDir + "\\" + StringUtil.conviterTitle(document.title().trim());
		File file = new File(baseDirect);
		if(!file.exists()) file.mkdirs();
		// 创建日志目录
		File logFileDir = new File(logDir);
		if(!logFileDir.exists()) logFileDir.mkdirs();//去重
		try {
			parse(url, baseDirect, logDir);
			List<String> urls = SpiderUtil.getAllIncludeUrlsByPage(url);//调用实现所有url抓取。
			for (String u : urls) {//依次赋值
				if(set.contains(u)) continue;
				set.add(u);
				ThreadUtil.getThreadPool().execute(() -> { 
					spider(u, baseDirect, logDir); 
				}); 
				// 暂停 50 ms
				Thread.sleep(50);
			}
		} catch (Exception e) {
			// ...
		}
	}
	
	/**
	 * 抓取页面
	 * @param url 抓取的URL地址
	 * @param dir 页面内容保存地址
	 */
	public void parse(String url, String dir, String logDir) {
		if(logTextArea.getText().length() > 5000) logTextArea.setText("");
		try {
			// 抓取页面
			Document document = SpiderUtil.get(url);
			// 保存抓取页面
			FileUtil.save(new File(dir + "\\" + StringUtil.conviterTitle(document.title().trim()) + ".html"), document.toString(), false);
			// 打印框显示
			logTextArea.setText(logTextArea.getText()  + "\r\n" + Thread.currentThread().getName() + "\r\n网页标题: " + document.title() + "\r\n网页地址: " + url + "\r\n爬取状态: 抓取成功\r\n");
			// 保存到日志文件
			FileUtil.save(new File(logDir + "\\log.txt"), "\r\n" + Thread.currentThread().getName() + "\r\n网页标题: " + document.title() + "\r\n网页地址: " + url + "\r\n爬取状态: 抓取成功\r\n", true);
		} catch (Exception e2) {
			logTextArea.setText(logTextArea.getText()  + "\r\n" + Thread.currentThread().getName() + "\r\n网页地址: " + url + "\r\n爬取状态: 抓取失败\r\n");
			FileUtil.save(new File(logDir + "\\log.txt"), "\r\n" + Thread.currentThread().getName() + "\r\n网页地址: " + url + "\r\n爬取状态: 抓取失败\r\n", true);
		}
	}
}
