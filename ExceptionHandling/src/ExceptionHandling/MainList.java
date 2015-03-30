package ExceptionHandling;

public class MainList{
	public static void main(String args[]){
		try{
			ListException <String> friends = new ListException<String>(5);
			System.out.println(friends.toString());
		}catch(EmptyListException e){
			e.printStackTrace();
		}
	}
}