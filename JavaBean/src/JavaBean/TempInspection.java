package JavaBean;

import java.beans.*;

public class TempInspection {
	public static void main(String args[]){
		try{
			Class cl = Class.forName("Temperature");
			BeanInfo beanInfo = Introspector.getBeanInfo(cl);

			System.out.println("Properties of Temperature class: ");

			PropertyDescriptor propertyDesc[] = beanInfo.getPropertyDescriptors();

			for(int i=0; i<propertyDesc.length; i++){
				System.out.println(propertyDesc[i].getName());
			}

			System.out.println();
			System.out.println("Methods of Temperature class: ");

			MethodDescriptor methodDesc[] = beanInfo.getMethodDescriptors();

			for(int i=0; i<methodDesc.length; i++){
				System.out.println(methodDesc[i].getName());
			}
		}catch(ClassNotFoundException e){
			System.out.println("Temperature class is not found.");
		}catch(IntrospectionException e){
			System.out.println("Introspector threw some killer exception!");
		}

	}
}