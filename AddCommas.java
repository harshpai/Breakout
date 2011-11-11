

/**
 * This file is the solution to section 4 problem 1
 * It adds commas to 
 * */

import acm.program.*;

public class AddCommas extends ConsoleProgram{
	
	public void run() {
		while (true) {
		String digits = readLine("Enter a numeric string: ");
		if (digits.length() == 0) break;
		println(addCommasToNumericString(digits));
		}
	}
	
	private String addCommasToNumericString(String digits){
		String result=""; 
		int length = digits.length();
		int noOfCommas = length/3;
		
		for (int i = 0;i < noOfCommas; i++){
			int endIndex = length - i * 3;
			int beginIndex = endIndex - 3;
			result = digits.substring(beginIndex, endIndex)+result;
		}
		
		return result;
		
	}
}
