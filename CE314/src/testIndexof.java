public class testIndexof {
	public static void main(String[] args) {
		String a = "acl";
		String b = "dfg";
		String c = "�x�W�@�����A�x�U�Q�~�\�C";
		if (a.compareTo(b) > a.length()){
			
		}
		System.out.println(a.compareTo(b));
		System.out.println(b.indexOf('a', 10));
		System.out.println(c.indexOf("�x�U"));
	}

}
