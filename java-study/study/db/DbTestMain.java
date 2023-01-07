package study.db;


import java.util.Scanner;

public class DbTestMain {

    public static void main(String[] args) {

        DbTest dbTest = new DbTest();
        //dbTest.dbSelect();
        //dbTest.dbInsert();
//        dbTest.dbUpdate();
//        dbTest.dbDelete();

        Scanner scanner = new Scanner(System.in);

        String memberType = "email";

        System.out.println("아이디입력:>>>");
        String userID = scanner.next();
        System.out.println("비밀번호입력:>>>");
        String password = scanner.next();
        System.out.println("이름입력:>>>");
        String name = scanner.next();

        dbTest.dbInsert(memberType, userID, password, name);



   }

}
