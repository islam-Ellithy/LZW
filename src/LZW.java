import java.io.File;
import java.io.FileNotFoundException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;
import java.util.Vector;

public class LZW {

	Map<String, Integer> dict;
	Vector<Integer> ascii = new Vector<Integer>();
	int cnt = 128;

	public LZW() {
		dict = new HashMap<String, Integer>();
		initializeMap();
	}

	public void initializeMap() {
		for (int i = 0; i <= 127; i++) {
			String asciiChar = (char) i + "";
			dict.put(asciiChar, i);
		}
	}

	public void displayAscii() {
		System.out.println("Ascii");

		for (int i = 0; i < ascii.size(); i++) {
			System.out.println(ascii.get(i));
		}
	}

	public void displayMap() {

		for (String element : dict.keySet()) {
			String key = element.toString();
			Integer value = dict.get(key);
			if (value > 127)
				System.out.println(key + "\t" + value);
		}
	}

	public String getKeyByValue(int value) {
		for (Entry<String, Integer> entry : dict.entrySet()) {
			if (entry.getValue() == value) {
				return entry.getKey();
			}
		}
		return null;
	}

	
	public Vector<Integer> ReadVectorFromFile(String path)
	{
		Vector<Integer> ascii = new Vector<Integer>();
		try
		{
			Scanner file = new Scanner(new File(path));
			
			while (file.hasNext()) {
				String element = file.next();
				ascii.add(Integer.parseInt(element));
			}
			
			
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return ascii ;
	}
	
	
	public String ReadData(String path)
	{
		try
		{
			Scanner file = new Scanner(new File(path));
			
			StringBuffer data = new StringBuffer();
			while(file.hasNext())
			{
				data.append(file.next());
			}
			return data.toString();
		}
		catch(Exception e)
		{
			return e.getMessage();
		}
	}
	
	
	public void WriteDataToFile(String path,String fileName,String data)
	{
		try
		{
			Formatter file = new Formatter(path+"Decompress"+fileName);
			file.format("%s", data);
			file.close();			

		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	
	public void WriteVectorToFile(String path ,String fileName, Vector<Integer> ascii)
	{
		try
		{
			Formatter file = new Formatter(path+"Compress"+fileName);

			for(int i = 0 ;i<ascii.size();i++)
			{
				file.format("%s\n",ascii.get(i));
			}
			file.close();			
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	
	
	public Vector<Integer> Compress(String data) {

		try {

			dict = new HashMap<String, Integer>();
			initializeMap();

			String current = "";

			int asciiCode = -1 , lengthOfSubStringInDict = 0 , j;

			for (int i = 0; i < data.length(); i++) {

				current = data.charAt(i) + "";

				j = i;

				while (dict.containsKey(current) && ++j < data.length()) {

					asciiCode = (dict.get(current) == null) ? -1 : dict.get(current);

					lengthOfSubStringInDict = current.length();

					current += data.charAt(j) + "";
				}

				// System.out.println(current+"\t"+cnt+"\t"+"\t"+asciiCode+"\t"+i+"\t"+j);

				
				if (!dict.containsKey(current)) {
					
					dict.put(current, cnt++);
					
					i += lengthOfSubStringInDict - 1;
					
				} else {

					if (j == data.length()) {
					
						asciiCode = dict.get(current);
						
						ascii.addElement(asciiCode);
						
						break;
					}

				}
				ascii.addElement(asciiCode);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		// displayMap();
		// displayAscii();
		return ascii;
	}

	
	public String Decompress(Vector<Integer> asciiCodes) {

		String data = "", previous = "", current = "";

		try {

			dict.clear();
			
			initializeMap();

			for (int i = 0; i < asciiCodes.size(); i++) {

				Integer asciiElement = asciiCodes.get(i);

				
				if (asciiElement < dict.size()) {
					
					
					String key = getKeyByValue(asciiElement);

					
					data += key ;

					if(i>=1)
					{
					
						current = getKeyByValue(asciiCodes.get(i - 1)) + key.charAt(0) ;
						dict.put(current, dict.size());						
					}
					
				} 
				else
				{
					
					current = getKeyByValue(asciiCodes.get(i - 1));					

					
					current += current.charAt(0);

					data += current ;
										
					dict.put(current, dict.size());
					
				}
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return data;
	}

	/*
	 * 
	 * ABAABABBAABAABAAAABABBBBBBBB
	 * 
	 * 
	 * 128 AB 129 BA 130 AA 131 ABA 132 ABB 133 BAA 134 ABAA 135 ABAAA 136 AAB
	 * 137 BAB 138 BB 139 BBB 140 BBBB
	 * 
	 * 128 AB 129 BA 130 AA 131 ABA 132 BAB 133 ABB 134 BB 135 BAA 136 AAB 137
	 * ABAA 138 BAAB 139 AABA 140 ABAAA
	 * 
	 */

	public static void maina(String[] args) 
	{
		
		String data = "ABAABABBAABAABAAAABABBBBBBBB";

		LZW algo = new LZW();

		Vector<Integer> ascii = algo.Compress(data);

		System.out.println("Size = " + ascii.size());

		for(int i = 0 ;i<ascii.size();i++)
		{
			System.out.println(ascii.get(i));
		}
		
		System.out.println("Size = " + ascii.size());

		String dataReturn = algo.Decompress(ascii);

		System.out.println(dataReturn + "\t" + dataReturn.equals(data));

	}
}
