
public class student extends person{

	public student(int age) {
		super(age);
		// TODO Auto-generated constructor stub
	}
	public String job(){
		return "student";
	}
	public void readOne() {
		System.out.println("Student: One");
		
	}
	public void readTwo() {
		System.out.println("Student: Two");		
	}
	@Override
	public void readThree() {
		System.out.println("Student: Three");
		
	}

}
