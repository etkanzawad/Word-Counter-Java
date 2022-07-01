package app;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Word counter app
 * word count first then word
 * console lowercase
 */
public class App {

	private static final String[] STOP_WORDS = "i himself whom having of from where only me she this do at up why own my her that does by down how same myself hers these did for in all so we herself those doing with out any than our it am a about on both too ours its is an against off each very ourselves itself are the between over few can you they was and into under more will your them were but through again most just yours their be if during further other don yourself theirs been or before then some should yourselves themselves being because after once such now he what have as above here no him which has until below there nor his who had while to when not"
			.split(" ");
	private static final String PUNCTUATIONS = "!\"#%&\\\\'()*+,./:;<>=?@[]^_'{}|~-";

	private JFrame frame;
	private JTextArea textArea;
	private JButton countBtn, openFileBtn;

	private File file;
	private String text;

	/**
	 * Constructor
	 */
	public App() {
		textArea = new JTextArea();
		JScrollPane textPane = new JScrollPane(textArea);
		textPane.setPreferredSize(new Dimension(300, 300));
		textPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		// COUNT BUTTON
		countBtn = new JButton("Start Counting");
		countBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (file == null)
					return;

				String allWords[] = text.split(" ");
				HashMap<String, Integer> wordMap = new HashMap<>();

				// count words frequencies and store in hashmap
				for (String word : allWords) {
					String w = word.trim();
					if(w.isEmpty())
						continue;
					
					Integer count = wordMap.get(w);
					if (count == null)
						wordMap.put(w, 1);
					else
						wordMap.put(w, count + 1);
				}

				// display frequencies in text area
				// word map displays count????
				textArea.setText("");
				for (String word : wordMap.keySet())
					textArea.append("(" + wordMap.get(word) + ", " + word + ")\n");
			}

		});

		// OPEN FILE BUTTON
		openFileBtn = new JButton("Select Text File");
		openFileBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// choose a txt file
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Select game text file");
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

				// if user chooses a file
				if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
					file = fileChooser.getSelectedFile();

					// load file as string(lower-case)
					text = loadTextFileAsString(file).toLowerCase();
					System.out.println(text);

					// remove punctuation
					for (char c : PUNCTUATIONS.toCharArray())
						text = text.replace(Character.toString(c), "");

					// remove stop words
					for (String word : STOP_WORDS)
						text = text.replace(" " + word + " ", " ");

				}
			}

			private String loadTextFileAsString(File f) {

				try (Scanner scan = new Scanner(f)) {
					String file = "";
					while (scan.hasNextLine()) {
						file += scan.nextLine() + " \n";
					}
					return file;
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(0);
				}
				return null;
			}

		});

		// frame
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(textPane);
		panel.add(openFileBtn);
		panel.add(countBtn);

		frame.add(panel);
		frame.pack();
		frame.setVisible(true);

	}

	/**
	 * Main method
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		new App();
	}
}
