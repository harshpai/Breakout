

/**
 * This file is the solution to section 4 problem 1
 * It adds commas to numeric strings
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
	
	private String addCommasToNumericString(String digits) {
		String result = "";
		int len = digits.length();
		int nDigits = 0;
		for (int i = len - 1; i >= 0; i--) {
		result = digits.charAt(i) + result;
		nDigits++;
		if (((nDigits % 3) == 0) && (i > 0)) {
		result = "," + result;
		}
		}
		return result;
		}
	/*private String addCommasToNumericString(String digits){
		String result="";
		int endIndex =digits.length()-1; 
		
		for (int i =endIndex ;i>=0;i--){
			if(i!=endIndex  && (endIndex -i)%3 == 0){
				result = ','+result;
			}
			result = digits.charAt(i)+result;
		}
		
		return result;
	}*/
}
