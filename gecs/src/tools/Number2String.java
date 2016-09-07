package tools;
/*traduce un numero in stringa*/
public class Number2String {
	
	public String number2String(String s){
		String result ="";
		int cont = 0;
		for(int i=0; i<s.length();i++){
			switch (s.charAt(i)){
			case '1':
				if(cont==0)
					result=s.substring(0,i)+"one";
				else
					result=result.substring(0,i)+"one";

				cont++;
				break;
			case '2':
				if(cont==0)
					result=s.substring(0,i)+"two";
				else
					result=result.substring(0,i)+"two";

				cont++;
				break;
			case '3':
				if(cont==0)
					result=s.substring(0,i)+"three";
				else
					result=result.substring(0,i)+"three";

				cont++;
				break;
			case '4':
				if(cont==0)
					result=s.substring(0,i)+"four";
				else
					result=result+"four";

				cont++;
				break;
			case '5':
				if(cont==0)
					result=s.substring(0,i)+"five";
				else
					result=result.substring(0,i)+"five";

				cont++;
				break;
			case '6':
				if(cont==0)
					result=s.substring(0,i)+"six";
				else
					result=result.substring(0,i)+"six";

				cont++;
				break;
			case '7':
				if(cont==0)
					result=s.substring(0,i)+"seven";
				else
					result=result.substring(0,i)+"seven";

				cont++;
				break;
			case '8':
				if(cont==0)
					result=s.substring(0,i)+"eight";
				else
					result=result+"eight";

				cont++;
				break;
			case '9':
				if(cont==0)
					result=s.substring(0,i)+"nine";
				else
					result=result.substring(0,i)+"nine";

				cont++;
				break;
			case '0':
				if(cont==0)
					result=s.substring(0,i)+"zero";
				else
					result=result.substring(0,i)+"zero";
				cont++;
				break;
			default:
				result+=s.charAt(i);

			
				
				
			}	
		}
		if(result.equals(""))
			result = s;
		return result;
	}
}
