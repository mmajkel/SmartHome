package automation.admin;

import java.util.Random;

public class RandomLinkGenerator {
	
	public RandomLinkGenerator() {

		
	}
	

	public static String generateRandomString(int length) {

		char[] permittedChars = { 'a','b','c','d','e','f','g','h',
							      'i','j','k','l','m','n','o','p',
							      'r','s','t','u','w','x','y','z',
							      'A','B','C','D','E','F','G','H',
							      'I','J','K','L','M','N','O','P',
							      'R','S','T','U','W','X','Y','Z',
							      '1','2','3','4','5','6','7','8','9','0',
							   //   '!','@','#','$','%','^','&','*','(',')',
							   //   '_','+'//,'}','{','[',']'//,';',':','>','<',
							   //   '/','\\','\'','\"','?','.',',','|'
								};
		
		String randomLink = "";
		
		for (int i=1;i<=length;i++) {
			Random rand = new Random();
			int positiveRandom = rand.nextInt(permittedChars.length);
			
			randomLink += permittedChars[positiveRandom];
		}
		
		System.out.println(randomLink);
		
		return randomLink;
	}
	
}
