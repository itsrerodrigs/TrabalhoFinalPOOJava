package util;

import java.util.Scanner;

public class Sc {
	   static Scanner sc = new Scanner(System.in);
	   public static int lerInt() {
	    	
	    	String s;
	    	int numero = 0;
	    	boolean valido = false;
	    	
	        while (!valido) {
	            s = sc.nextLine();
	            try {
	                numero = Integer.parseInt(s);            
	                valido = true;
	            } 
	            catch (NumberFormatException e) {
	                System.out.print("\nEntrada inválida! \nDigite novamente: ");
	            }
	        }
	        return numero;
	        
	    }
	    public static double lerDouble() {
	    	String s;
	        while (true) {
	            s = sc.nextLine();
	            try {
	                return Double.parseDouble(s);
	            } 
	            catch (NumberFormatException e) {
	            	System.out.print("\nEntrada inválida! \nDigite novamente: ");
	            }
	        }
	    }
}
