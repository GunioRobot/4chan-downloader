import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
//import java.util.prefs.Preferences;

@SuppressWarnings("serial")
public class gui extends JFrame implements ActionListener {
  public static javax.swing.JFrame frame;
  public static javax.swing.JTextField threadnumber;
  public static javax.swing.JTextField board;
  public static javax.swing.JButton wheretosavepics;
  public static javax.swing.JLabel boardlabel;
  public static javax.swing.JLabel threadnumberlabel;
  public static javax.swing.JCheckBox watch;
  public static javax.swing.JLabel watchlabel;
  public static javax.swing.JLabel wheretosavepicslabel;
  public static javax.swing.JButton submit;
  public static String PathToSavePics="";
  public static Properties properties=null;
//  private Preferences prefs;
  public static JProgressBar progressbar;
  private final static String version="1.0.2";
 public gui(){
//	 prefs = Preferences.userNodeForPackage(this.getClass());
//	 PathToSavePics=prefs.get("PicsStore",""); 
	 properties=new Properties();
         try {
			properties.load(new FileInputStream("4chandownloader.properties"));
			PathToSavePics=properties.getProperty("PicsStore");
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
	 setupgui();
 }
public static void main(String[] args) {
	frame= new JFrame("4chan Downloader");
	new gui();
	frame.pack();
	frame.setVisible(true);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
}  
public void actionPerformed(ActionEvent e) {
	if(e.getActionCommand()=="download"){
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
		Date startdate = new Date();
		submit.setEnabled(false);
		watch.setEnabled(false);
	if(watch.isSelected()){
		new downloader(board.getText(),Integer.parseInt(threadnumber.getText()), true);
	}
	else{
		new downloader(board.getText(),Integer.parseInt(threadnumber.getText()));
	}
//	prefs.put("version",version);
//	prefs.put("PicsStore",PathToSavePics); 
		properties.put("version", version);
		properties.put("PicsStore", PathToSavePics);
	try {
		properties.store(new FileOutputStream(new File("4chandownloader.properties")), "");
	} catch (FileNotFoundException e1) {
	} catch (IOException e1) {
	}
		watch.setEnabled(true);
		submit.setEnabled(true);
		JOptionPane.showMessageDialog(null, "Your Image Download is Done \nTime downloader start: "+dateFormat.format(startdate)+"\nTime Finished: "+dateFormat.format(new Date()), "4chan Downloader",JOptionPane.PLAIN_MESSAGE);
	}
	else if(e.getActionCommand()=="pickdictory"){
		JFileChooser chooser = new JFileChooser();
		if(PathToSavePics!=""){
	    chooser.setCurrentDirectory(new File(PathToSavePics));
		}else{
			chooser.setCurrentDirectory(new File("."));	
		}
	    chooser.setDialogTitle("choosertitle");
	    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    chooser.setAcceptAllFileFilterUsed(false);
	    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
	    	PathToSavePics=chooser.getSelectedFile().toString()+"/";
	    	wheretosavepicslabel.setText("Current path to save pics is: "+PathToSavePics);
	      } 
	    	else {
	    		JOptionPane.showMessageDialog(null, "Choose a new directory");
	      }
	   }
	}
public void setupgui(){
	GridLayout layout = new GridLayout(0,2);
	Container contentPane = frame.getContentPane();
	contentPane.setLayout(layout);
	contentPane.add(wheretosavepicslabel=new JLabel("Current path to save pics is: "+PathToSavePics));
	contentPane.add(wheretosavepics=new JButton("Pick directory to save pictures"));
	wheretosavepics.setActionCommand("pickdictory");
	contentPane.add(threadnumberlabel=new JLabel("Enter in the the thread number taken from the URL"));
	contentPane.add(threadnumber=new JTextField());
	contentPane.add(boardlabel=new JLabel("Enter the board it was found on (ie b)"));
	contentPane.add(board=new JTextField());
	contentPane.add(watchlabel=new JLabel("Watch the thread for new pictures being uploaded"));
	contentPane.add(watch=new JCheckBox());
	contentPane.add(submit=new JButton("Start"));
	contentPane.add(progressbar=new JProgressBar());
	submit.setActionCommand("download");
	submit.addActionListener(this);
	wheretosavepics.addActionListener(this);
}
}