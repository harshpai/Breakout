/**
 * This is problem is solution to Section 4 problem 2
 * It removes a specified character from a string
 * */

import acm.program.*;

public class removeStringOccurrences extends ConsoleProgram{

	public void run(){
		
		String str  = readLine("Enter a string:");
		String strRemove =  readLine("Enter the character to remove from the string");
		char ch = strRemove.charAt(0);
		
		println((removeAllOccurrences(str,ch));
		
		
	}
	
	
	private String removeAllOccurrences(String str,char ch){
		String result="";
		
		for (int i = 0; i <str.length();i++){
			
			if(str.charAt(i)!=ch){
				result+=str.charAt(i);
			}
		}
		
			
		return result;
	}
	
}
