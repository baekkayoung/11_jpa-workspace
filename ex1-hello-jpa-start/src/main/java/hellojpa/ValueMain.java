package hellojpa;

public class ValueMain {

    //psvm 단축키
    public static void main(String[] args) {

        int a = 10;
        int b = 10;

//        System.out.println("a==b :" + (a==b)); // true

        Address address1 =new Address("서울", "테헤란로", "10000");
        Address address2 =new Address("서울", "테헤란로", "10000");
//        System.out.println("a1 == a2 : " + (address1 == address2)); // false

        // 1. equals 호출 결과 true
        System.out.println(address1.equals(address2));
    }
}
