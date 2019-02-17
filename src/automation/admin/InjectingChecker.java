package automation.admin;

import java.nio.charset.StandardCharsets;

public class InjectingChecker {
	private byte[] forbiddenChars = {'=','/','\\','\'','\"',};

	public InjectingChecker() {
		
	}
	
	public boolean isSqlInjection(String string) {
		byte[] b = string.getBytes(StandardCharsets.UTF_8);
		
		for (int i=0;i<b.length;i++) {
			for(int j=0;j<forbiddenChars.length;j++) {
				if (b[i]==forbiddenChars[j]) {
					System.out.println("numer znaku="+(i+1)+" wartość znaku= \'"+(char)b[i]+"\'");
					return true;
				}
			}
		}
		return false;
	}	
}
