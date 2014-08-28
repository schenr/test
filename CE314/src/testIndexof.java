public class testIndexof {
	public static void main(String[] args) {
		String a = "acl";
		String b = "dfg";
		String c = "台上一分鐘，台下十年功。";
		if (a.compareTo(b) > a.length()){
			
		}
		System.out.println(a.compareTo(b));
		System.out.println(b.indexOf('a', 10));
		System.out.println(c.indexOf("台下"));
	}

}
