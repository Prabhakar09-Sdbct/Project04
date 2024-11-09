import java.util.Scanner;

public class Palindrome {
	
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
        String A=sc.next();
        /* Enter your code here. Print output to STDOUT. */
        
        String R = "";
        for(int i=A.length(); i>0; i--){
        	System.out.print("i"+(i-1));
           R +=  A.charAt(i-1);
       }
        System.out.println("R"+R);
       if(A.equals(R)){
            System.out.print("Yes");
       } else {
           System.out.print("No");
       }
	}

}
