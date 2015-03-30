package Collection;

//import java.util.TreeSet;

public class wordList{
	public static void main(String args[]){
		String paragraph;

		paragraph = "My name is Richard Anabeto Opoku. I am a computer engineering student. I love computers like crazy.";
		paragraph+="I currently need about GHC5000 to buy and build a custom PC.";

		String []sentence = paragraph.split(". ");
		/*for(String word: sentence){
			System.out.print(word+" ");
		}*/

		System.out.println("Total number of words is "+sentence.length);
	}
}