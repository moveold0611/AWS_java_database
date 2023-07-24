package main;

public class ArrayMain {

	public static void main(String[] args) {
		String[][] strArray = new String[2][3];
		strArray[0][0] = "대";
		strArray[0][1] = "머";
		strArray[0][2] = "리";
		strArray[1][0] = "빡";
		strArray[1][1] = "빡";
		strArray[1][2] = "이";
		
		for(int i = 0; i < strArray.length; i++) {
			for(int j = 0; j < strArray[i].length; j++) {
				System.out.print(strArray[i][j] + "\t");				
			}
			System.out.println();
		}
		
	}
}
