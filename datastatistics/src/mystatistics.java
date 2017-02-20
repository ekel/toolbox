package datastatistics;

import java.io.BufferedReader;
import java.io.File;
//import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
//import java.util.ArrayList;
import java.util.Date;
//import java.util.HashSet;
import java.util.Iterator;
//import java.util.List;
import java.util.Set;
import java.util.TreeSet;

// 统计某列字段和
// 统计某列字段去重值
public class mystatistics {

	public static void main(String[] args) {
		readFile("config.ini");
	}

	public static void readFile(String fielName) {
		String file = null;
		String separtor = null;
		int num = 0;
		String type = null;
		int method = 0;
		Set<String> mySet = new TreeSet<String>();
		try {
			
			BufferedReader br = new BufferedReader(new FileReader(fielName));
			String strLine = br.readLine();
			
			// parser data
			while (strLine != null) {
				if (!strLine.startsWith("//")) {
					//System.out.println(strLine);
					String[] strArr = strLine.split("=", 2);
					switch (strArr[0].toLowerCase()) {
					case "file":
						file = strArr[1];
						break;
					case "separator":
						separtor = strArr[1];
						break;
					case "num":
						num = Integer.parseInt(strArr[1]);
						break;
					case "type":
						type = strArr[1];
						break;
					case "method":
						method = Integer.parseInt(strArr[1]);
						break;
					default :
						break;
					}
				}
				strLine = br.readLine();
			}
			br.close();
		} catch(IOException e) {
			System.out.println(e.toString());
		}
		
		if (file != null && separtor != null) {
			//System.out.println(file);
			//System.out.println(separtor);
			
			try {
				BufferedReader br = new BufferedReader(new FileReader(file));
				String strLine = null;//br.readLine();
				long i = 0;
				double f = 0;
				
				while ((strLine = br.readLine()) != null) {
					if (strLine.trim().isEmpty()) {
						continue;
					}
					
					String[] strArr = strLine.split(separtor);
					
					if (num > strArr.length) {
						continue;
					}
					
					if (method == 1) {
						mySet.add(strArr[num-1]);
					} else {
						switch (type.toLowerCase()) {
						case "d":
							i += Integer.parseInt(strArr[num-1]);
							break;
						case "f":
							f += Double.parseDouble(strArr[num-1]);
							break;
						}
					}
					//System.out.println(strLine);
					//strLine = br.readLine();
				}
				br.close();
				if (mySet.size() > 0) {
					FileWriter fw = createWriteFile(getFileName());
					Iterator<String> iter = mySet.iterator();
					while (iter.hasNext()) {
						writeContent(fw, iter.next());
					}
					closeFile(fw);
				} else {
					if (type.toLowerCase().matches("d")) {
						System.out.println(i);
					} else {
						System.out.printf("%.4f", f);
					}
				}
			} catch(IOException e) {
				System.out.println(e.toString());
			}
		}
	}
	/*
	 * 设置文件名
	 */
	public static String getFileName() {
		//Date df = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		String date = df.format(new Date());
		return date +".txt";
	}
	
	public static FileWriter createWriteFile(String name) {
		File f = new File(name);
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		FileWriter fw = null;
		try
		{
			fw = new FileWriter(f, true);
			SimpleDateFormat nowTime = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
			String str = nowTime.format(new Date());
			str += "---------------------------------------------------------\r\n";
			fw.write(str);
			fw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fw;
	}
	
	public static void writeContent(FileWriter f, String str) {
		str +="\r\n";
		try {
			f.write(str);
			f.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void closeFile(FileWriter f) {
		try {
			f.flush();
			f.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
