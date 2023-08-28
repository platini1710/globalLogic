package com.global.tareas.helper;

import java.util.regex.Pattern;
public class PasswordValidator {



    private static final String PASSWORD_PATTERN = "^(?=\\w*\\d{2})(?=\\w*[A-Z])(?=\\w*[a-z])\\S{8,12}$";

    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    public static boolean isValid( String password) {
    	
    	int cantMay=validMay(password);
    	int cantDigts=validDigts(password);

      if (cantMay!=1 || cantDigts!=2 ||password.length()<8  ||password.length()>12 ) {
    	  return false;
      }
    	  else
    	return true;
    }
    public static int validMay( String password) 
    {

		int contMay=0;
		  char[] chars = password.toCharArray();
		  for (char ch: chars) {
	            if ( ((char)ch)>64 && ((char)ch)<91) {
	            	contMay++;
	            }
	            	
	        }
		  return contMay;
    }
    public static int validDigts( String password) 
    {

		int contNumber=0;
		  char[] chars = password.toCharArray();
		  for (char ch: chars) {
	            if ( ((char)ch)>47 && ((char)ch)<58) {
	            	contNumber++;
	            }
	            	
	        }
		  return contNumber;
    }  
    
}