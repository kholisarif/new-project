package test;

import java.util.Timer;
import java.util.TimerTask;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Date;

public final class TImer extends TimerTask {

  /** Construct and use a TimerTask and Timer. */
  public static void main (String... arguments ) {
    TimerTask fetchMail = new TImer();
    //perform the task once a day at 4 a.m., starting tomorrow morning
    //(other styles are possible as well)
    Timer timer = new Timer();
    //timer.scheduleAtFixedRate(fetchMail, getTomorrowMorning4am(), fONCE_PER_DAY);
    timer.scheduleAtFixedRate(fetchMail, getNextFourMinute(), fONCE_PER_MINUTE);
  }

  /**
  * Implements TimerTask's abstract run method.
  */
  @Override public void run(){
	  Date now = new Date();
    //toy implementation
    System.out.println("Fetching mail...");
    System.out.println(now);
    String sourceFile = "C:\\Users\\kholis\\Downloads\\posbasic";
    FileOutputStream fos;
	try {
		fos = new FileOutputStream("C:\\Users\\kholis\\Downloads\\posbasic.zip");
		ZipOutputStream zipOut = new ZipOutputStream(fos);
	    File fileToZip = new File(sourceFile);

	    zipFile(fileToZip, fileToZip.getName(), zipOut);
	    zipOut.close();
	    fos.close();
	    System.out.println("Selesai bikin zip...");
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
  }

  // PRIVATE

  //expressed in milliseconds
  private final static long fONCE_PER_DAY = 1000*60*60*24;

  private final static int fONE_DAY = 1;
  private final static int fFOUR_AM = 4;
  private final static int fZERO_MINUTES = 0;
  
  private final static long fONCE_PER_MINUTE = 1000*60*5;

  private static Date getTomorrowMorning4am(){
    Calendar tomorrow = new GregorianCalendar();
    tomorrow.add(Calendar.DATE, fONE_DAY);
    Calendar result = new GregorianCalendar(
      tomorrow.get(Calendar.YEAR),
      tomorrow.get(Calendar.MONTH),
      tomorrow.get(Calendar.DATE),
      fFOUR_AM,
      fZERO_MINUTES
    );
    return result.getTime();
  }
  
  private static Date getNextFourMinute(){
	    Calendar cal = Calendar.getInstance();
	    cal.set(Calendar.HOUR_OF_DAY, 13);
	    cal.set(Calendar.MINUTE, 52);
	    cal.set(Calendar.SECOND, 0);
	    return cal.getTime();
  }
  
  private static void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
      if (fileToZip.isHidden()) {
          return;
      }
      if (fileToZip.isDirectory()) {
          File[] children = fileToZip.listFiles();
          for (File childFile : children) {
              zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
          }
          return;
      }
      FileInputStream fis = new FileInputStream(fileToZip);
      ZipEntry zipEntry = new ZipEntry(fileName);
      zipOut.putNextEntry(zipEntry);
      byte[] bytes = new byte[1024];
      int length;
      while ((length = fis.read(bytes)) >= 0) {
          zipOut.write(bytes, 0, length);
      }
      fis.close();
  }
}
