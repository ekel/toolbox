package datastatistics;

import java.io.BufferedReader;
//import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class mystatistics {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//System.out.println("hello world!");
		readFile("config.ini");
	}

	public static void readFile(String fielName) {
		String file = null;
		String separtor = null;
		int num = 0;
		String type = null;
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
					
					switch (type.toLowerCase()) {
					case "d":
						i += Integer.parseInt(strArr[num-1]);
						break;
					case "f":
						f += Double.parseDouble(strArr[num-1]);
						break;
					}
					
					//System.out.println(strLine);
					//strLine = br.readLine();
				}
				br.close();
				
				if (type.toLowerCase().matches("d")) {
					System.out.println(i);
				} else {
					System.out.printf("%.4f", f);
				}
			} catch(IOException e) {
				System.out.println(e.toString());
			}
		}
	}
}
