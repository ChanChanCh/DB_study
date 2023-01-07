package study.db;


import java.util.Scanner;

public class DbTestMain {

    public static void main(String[] args) {

        MemberService memberService = new MemberService();
        //dbTest.dbSelect();
        //dbTest.dbInsert();
//        dbTest.dbUpdate();
//        dbTest.dbDelete();

        Scanner scanner = new Scanner(System.in);

        String memberType = "email";

        System.out.println("탈퇴할 회원 아이디를 입력해 주세요:>>>");
        System.out.println("아이디입력:>>>");
        String userID = scanner.next();
        /*
        System.out.println("비밀번호입력:>>>");
        String password = scanner.next();
        System.out.println("이름입력:>>>");
        String name = scanner.next();
         */

        Member member = new Member();
        member.setMemberType(memberType);
        member.setUserId(userID);
//        member.setPassword(password);
//        member.setName(name);


        memberService.withdraw(member);



   }

}
