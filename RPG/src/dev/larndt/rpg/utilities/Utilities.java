package dev.larndt.rpg.utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Utilities {

	public static String loadFileAsString(String path){
		StringBuilder builder = new StringBuilder();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			String line;
				while((line = br.readLine()) != null) {
					builder.append(line + ",");
				}
				br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return builder.toString();
	}
	
	public static int parseInt(String number) {
		try{
			return Integer.parseInt(number);
		}catch(NumberFormatException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public static boolean contains(int[] array, int a) {
		
		for(int i = 0; i < array.length; i++) {
			if(array[i] == a) {
				return true;
			}
		}
		return false;
	}
}
