import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;



public class downloader {
  public ArrayList<String> imgurls;
  private final String boardurl = "http://boards.4chan.org/";
  private final String imgurl = "http://images.4chan.org/";
  public downloader(String board, int threadnumber){
	  imgurls=new ArrayList<String>();
	  scaper(board,threadnumber);
	  gui.progressbar.setMaximum(imgurls.size());
	  for(int i=0; i<imgurls.size();i++){
		download(threadnumber, imgurls.get(i));
		gui.progressbar.setValue(i+1);
	  }
  }
  public downloader(String board, int threadnumber, boolean watch){
	  imgurls=new ArrayList<String>();
	  gui.progressbar.setIndeterminate(true);
	  if(watch==true){
		  watch(board, threadnumber);
	  }
	  gui.progressbar.setIndeterminate(false);
  } 
public void scaper(String board, int threadnumber){
	URL url = null; 
	URLConnection urlConn = null;
	BufferedReader in=null;
	try {
		url = new URL(boardurl+board+"/res/"+threadnumber);
		urlConn = url.openConnection();
		in = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
		String line;
		while((line = in.readLine()) != null){
			if(line.contains(imgurl)){
				int begining=line.indexOf(imgurl);
				int ending=line.indexOf("\"", begining);
				String dlurl=line.substring(begining,ending);
				imgurls.add(dlurl);
			}
		}
	} catch (MalformedURLException e) {
	} catch (IOException e) {
	}
}
public ArrayList<String> listscaper(String board, int threadnumber){
	URL url = null; 
	URLConnection urlConn = null;
	BufferedReader in=null;
	ArrayList<String> comparelist=new ArrayList<String>();
	try {
		url = new URL(boardurl+board+"/res/"+threadnumber);
		urlConn = url.openConnection();
		in = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
		String line;
		while((line = in.readLine()) != null){
			if(line.contains(imgurl)){
				int begining=line.indexOf(imgurl);
				int ending=line.indexOf("\"", begining);
				String dlurl=line.substring(begining,ending);
				comparelist.add(dlurl);
			}
		}
	} catch (MalformedURLException e) {
	} catch (IOException e) {
	}
	return comparelist;
}
public void download(int threadnumber, String imageurl){
	try {
		org.apache.commons.io.FileUtils.copyURLToFile(new URL(imageurl), new File(gui.PathToSavePics+threadnumber+"/"+imageurl.substring(imageurl.lastIndexOf('/')+1)));
	} catch (MalformedURLException e) {
	} catch (IOException e) {
	}
} 
public void watch(String board, int threadnumber){
	scaper(board,threadnumber);
	for(int i=0; i<imgurls.size();i++){
		download(threadnumber,imgurls.get(i));
	}
	ArrayList<String> temp=imgurls;
	while(temp.size()>0){	
	temp=listscaper(board, threadnumber);
	if(!temp.isEmpty() && !temp.equals(imgurls)){
	for(int i=imgurls.size()-1;i<temp.size(); i++){
		download(threadnumber, temp.get(i));
	}
		imgurls=temp;
	}
	  try {
			Thread.currentThread();
			Thread.sleep(10000);
		} catch (InterruptedException e) {
		}
	}
}
}