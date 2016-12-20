import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.awt.event.ActionEvent;

public class GUI extends JFrame {

	private JPanel contentPane;
	private JTextField path;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
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
	public GUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		path = new JTextField();
		path.setText("C:\\\\Users\\\\islam\\\\workspace\\\\LZW\\\\data.txt");
		path.setBounds(74, 110, 293, 20);
		contentPane.add(path);
		path.setColumns(10);
		
		JLabel msg = new JLabel("Message here");
		msg.setBounds(74, 154, 293, 20);
		contentPane.add(msg);
		
		JLabel lblEnterFilePath = new JLabel("Enter file path here");
		lblEnterFilePath.setBounds(74, 63, 293, 24);
		contentPane.add(lblEnterFilePath);
		
		JButton compress = new JButton("Compress");
		compress.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String Path = path.getText();
				int start = Path.lastIndexOf('\\');

				String path1 = Path.substring(0, start);

				String fileName = Path.substring(start + 1, Path.length());
				
				LZW algo = new LZW();
				
				String data = algo.ReadData(Path);

				Vector<Integer> ascii = algo.Compress(data);
				
				algo.WriteVectorToFile(path1, fileName, ascii);
				
				msg.setText("File has been created!");
			}
		});
		compress.setBounds(40, 214, 124, 23);
		contentPane.add(compress);
		
		JButton decompress = new JButton("Decompress");
		decompress.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String Path = path.getText();
				int start = Path.lastIndexOf('\\');

				String path1 = Path.substring(0, start);

				String fileName = Path.substring(start + 1, Path.length());
				

				LZW algo = new LZW();

				Vector<Integer> ascii = algo.ReadVectorFromFile(Path);
				
				
				String data = algo.Decompress(ascii);
				
				System.out.println(data);
				
				algo.WriteDataToFile(path1, fileName, data);				
				
				msg.setText("Decompress file has been created!");
			}
		});
		decompress.setBounds(271, 214, 124, 23);
		contentPane.add(decompress);
	}
}
