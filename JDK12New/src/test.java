public class test {

	public static void main(String[] args) {
		
		String s1 = "Four"+4;
		
		String s2 = new StringBuffer().append("Four").append(4).toString();
		
		String s3 = Integer.toString(4);
		
		System.out.println(s1);
		
		System.out.println(s2);
		
		System.out.println(s3);	
		
	}

}
