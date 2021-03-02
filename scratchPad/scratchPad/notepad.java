package scratchPad;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.awt.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
//import javax.speech.*;
//import com.sun.speech.freetts.Voice;
//import com.sun.speech.freetts.VoiceManager;

public class notepad extends JFrame implements ActionListener{
	
	static JFrame frame;
	static JMenuBar jmbar;
    static JMenu file; 
	static JMenu edit; static JMenu help;static JMenu info;
	static JTextArea area;
	static JScrollPane spane;
	JMenu listen; JMenuItem readAloud;
	JMenuItem copy; JMenuItem paste; JMenuItem font_styles;
	JMenuItem new_note; JMenuItem save; JMenuItem open; JMenuItem exit;
	String textToBeCopied; 
	notepad(){
		//constructor of the class 
		createFrame();
		createMenu();
		createShortcuts();
		createTextArea();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		
		new notepad();
		
	}
	void buttonPerform() {
		new_note.addActionListener(this);
		save.addActionListener(this);
		open.addActionListener(this);
		exit.addActionListener(this);
		copy.addActionListener(this);
		paste.addActionListener(this);
		font_styles.addActionListener(this);
		readAloud.addActionListener(this);
	}
	public static void createFrame() {
		frame = new JFrame("Your Notepad");
		frame.setTitle("All your notes. At one place.");
		frame.setBounds(0, 0, 600, 800);
		
		
	}
	public static void createTextArea() {
		area = new JTextArea();
		spane = new JScrollPane(area);
		area.setFont(new Font("Verdana", Font.BOLD, 18));
		area.setLineWrap(true);
		frame.add(spane, BorderLayout.CENTER);
	}
	public static void createShortcuts() {
		//create all shortcuts
		
	}
	public void createMenu() {
		jmbar = new JMenuBar();
		file = new JMenu("File");
		edit = new JMenu("Edit");
		listen = new JMenu("Listen");
		help = new JMenu("Help");
		info = new JMenu("About");
		
		jmbar.add(file); jmbar.add(edit); 
		jmbar.add(listen); jmbar.add(help); jmbar.add(info);
		frame.setJMenuBar(jmbar);
		
		 new_note = new JMenuItem("New");
		 save = new JMenuItem("Save");
		 open = new JMenuItem("Open");
		 exit = new JMenuItem("Exit");
		 file.add(new_note); file.add(save); file.add(open); file.add(exit);
	    
		 copy = new JMenuItem("Copy");
		 paste = new JMenuItem("Paste");
		 font_styles = new JMenuItem("Font Styles");
		edit.add(copy); edit.add(paste); edit.add(font_styles);
		
		readAloud = new JMenuItem("Read Aloud...");
		listen.add(readAloud);
		buttonPerform();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	
		if(e.getActionCommand().equals("Exit")) {
			exitSystemOrSave();
			return;
		}
		
		if(e.getActionCommand().equals("New")) {
			newNotepadCreated();
			return;
		}
		if(e.getActionCommand().equals("Open")) {
			openFileToDisplay();
			return;
		}
		if(e.getActionCommand().equals("Save")) {
			saveFileToDesktop();
			return;
		}
		if(e.getActionCommand().equals("Print")) {
			printContentsOfNotePad();
			return;
		}
		
		if(e.getActionCommand().equals("Copy")) {
			textToBeCopied = area.getSelectedText(); return;
		}
		if(e.getActionCommand().equals("Paste")) {
			area.insert(textToBeCopied, area.getCaretPosition()); return;
		}
		if(e.getActionCommand().equals("Read Aloud...")) {
			readTheNotes(); return;
		}
		
	}
	void readTheNotes() {
		
//		Text2Speech obj=new Text2Speech(); obj.dospeak(area.getText(),"kevin16"); 
//		Voice voice;//Creating object of Voice class
//        voice = VoiceManager.getInstance().getVoice("kevin");//Getting voice
//        if (voice != null) {
//            voice.allocate();//Allocating Voice
//        }
//        try {
//            voice.setRate(190);//Setting the rate of the voice
//            voice.setPitch(150);//Setting the Pitch of the voice
//            voice.setVolume(3);//Setting the volume of the voice
//            voice.speak("Hello this is Tutorials Field");//Calling speak() method
//  
//        }
//        catch(Exception e)
//        {
//       e.printStackTrace();
//        }
//		return;
	
	}
	void newNotepadCreated() {
		Object[] options = {"Save my file",
        "Close and exit"};
		int n = JOptionPane.showOptionDialog(frame,
			"Opening new notepad will lose all unsaved data. Please choose appropriately.",
				"WARNING",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,     //do not use a custom Icon
				options,  //the titles of buttons
				options[0]); //default button title
		if(n==0) {
			saveFileToDesktop(); return;
		} else {
			area.setText("");
		}
	}
	void exitSystemOrSave() {
		Object[] options = {"Save my file",
        "Close and exit"};
		int n = JOptionPane.showOptionDialog(frame,
			"Exiting from the application will lose all unsaved data. Please choose appropriately.",
				"WARNING",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,     //do not use a custom Icon
				options,  //the titles of buttons
				options[0]); //default button title
		if(n==0) {
			saveFileToDesktop(); return;
		} else {
			System.exit(0); return;
		}
		//System.out.println(n);
	}
	void openFileToDisplay() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Choose the file to open");
		//display only the text files
		fileChooser.setAcceptAllFileFilterUsed(false);
	//	FileNameExtensionFilter doNotShow = new FileNameExtensionFilter("txt", "Only .txt files");
	//	fileChooser.addChoosableFileFilter(doNotShow);
		int action = fileChooser.showOpenDialog(frame);
		if(action!=fileChooser.APPROVE_OPTION) return;
		File file = fileChooser.getSelectedFile();
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			try {
				area.read(br, null);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
	}
	void printContentsOfNotePad() {
		try {
			area.print();
		} catch (PrinterException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	void saveFileToDesktop() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Specify the folder where you'd like to save");
		fileChooser.setApproveButtonText("Save");
		int action = fileChooser.showSaveDialog(frame);
		if(action!=fileChooser.APPROVE_OPTION) return;
		System.out.println(fileChooser.getSelectedFile());
		//writing out to the java NOTEPAD created 
		File file = new File(fileChooser.getSelectedFile()+".txt");
		BufferedWriter bw;
		try {
			FileWriter filewriter = new FileWriter(file);
			bw = new BufferedWriter(filewriter);
			area.write(bw);
		} catch (IOException e1) {
			
			e1.printStackTrace();
		}
	}

}
