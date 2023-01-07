package study.db;

import java.sql.*;

public class MemberService {

    public void dbSelect(){
        String url = "jdbc:mariadb://172.30.1.93:3306/testdb3";
        String dbUserId = "root";
        String dbPassword = "zerobase";


        /*
            1.  드라이버 로드
            2.  커넥션 객체 생성
            3.  스테이트먼트 객체 생성
            4.  쿼리 실행
            5.  결과 수행
            6.  객체 연결 해제(close)
         */

        try {
            //드라이버 로드하는 부분
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        /*
            sql 로 불러올 데이터 member_type은 언제든 바뀔수 있다면 이렇게 처리하는게 편하긴하다
            하지만 해커들은        String memberTypeValue = "email' or 1 = 1";
            이런식으로 쿼리 파라미터를 조작하여 sql injection 공격을 하기 때문에
            파라미터를 단순히 더하는 방식으로 날코딩하면 위험하다
        */
        String memberTypeValue = "email";


        try {
            // 커넥션 객체생성하는 부분
            connection = DriverManager.getConnection(url, dbUserId, dbPassword);
        /*
             3. 스테이트 먼트객체는 아래와 같이 3가지가 존재한다

            Statement statement = null; ( 파라미터 처리가 부족하다 )
            PreparedStatement preparedStatement = null; ( DB쿼리를 실행하기위해서 파라미터 이슈가 필요하다  )
            CallableStatement callableStatement = null; ( 스토어드프로시저(Stored Procedure)를 실행시키기 위해 사용되는 인터페이스)
        */


            String sql = "select member_type, user_id, password, name" +
                    " from member " +
                    " where member_type = ? ";
            // 쿼리설정을 prepareStatement에서 해줬으므로 아래의 executeQuery는 비워준다
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,memberTypeValue);


            rs = preparedStatement.executeQuery();

            while(rs.next()) {

                String memberType = rs.getString("member_type");
                String userId = rs.getString("user_id");
                String password = rs.getString("password");
                String name = rs.getString("name");

                System.out.println(memberType + ", " + userId + ", " + password + ", " + name);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            /*
            반드시 실행하는 문장! 위에서 if문을돌리면 특정조건에 의해 if문이 안돌아오는 경우도 생기기때문에 여기에서
            if문을 사용하여 rs / statement / connection 을 close() 해주어야한다

            */


            try {
                if(rs != null && !rs.isClosed()){
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if(preparedStatement != null && !preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }



    }


    /**
     * 회원 가입 함수
     * @param member 회원정보
     */

    public void register(Member member){
        String url = "jdbc:mariadb://172.30.1.93:3306/testdb3";
        String dbUserId = "root";
        String dbPassword = "zerobase";

        try {
            //드라이버 로드하는 부분
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            // 커넥션 객체생성하는 부분
            connection = DriverManager.getConnection(url, dbUserId, dbPassword);

            String sql = " insert into member (member_type, user_id, password, name) " +
                    " values (?, ?, ?, ?); ";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, member.getMemberType());
            preparedStatement.setString(2, member.getUserId());
            preparedStatement.setString(3, member.getPassword());
            preparedStatement.setString(4, member.getName());

            int affected  = preparedStatement.executeUpdate();

            if(affected > 0) {
                System.out.println(" 저장 성공 ");
                }else {
                    System.out.println(" 저장 실패 ");
                }


        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            /*
            반드시 실행하는 문장! 위에서 if문을돌리면 특정조건에 의해 if문이 안돌아오는 경우도 생기기때문에 여기에서
            if문을 사용하여 rs / statement / connection 을 close() 해주어야한다

            */


            try {
                if(rs != null && !rs.isClosed()){
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if(preparedStatement != null && !preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }



    }

    public void dbUpdate(){
        String url = "jdbc:mariadb://172.30.1.93:3306/testdb3";
        String dbUserId = "root";
        String dbPassword = "zerobase";

        try {
            //드라이버 로드하는 부분
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        String memberTypeValue = "email";
        String userIdValue = "testInsert2@naver.com";
        String passwordValue = "9999";



        try {
            // 커넥션 객체생성하는 부분
            connection = DriverManager.getConnection(url, dbUserId, dbPassword);

            String sql = " update member set " +
                    " password = ? " +
                    " where member_type = ? and user_id = ? ";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,passwordValue);
            preparedStatement.setString(2,memberTypeValue);
            preparedStatement.setString(3,userIdValue);

            int affected  = preparedStatement.executeUpdate();

            if(affected > 0) {
                System.out.println(" 저장 성공 ");
            }else {
                System.out.println(" 저장 실패 ");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            /*
            반드시 실행하는 문장! 위에서 if문을돌리면 특정조건에 의해 if문이 안돌아오는 경우도 생기기때문에 여기에서
            if문을 사용하여 rs / statement / connection 을 close() 해주어야한다

            */


            try {
                if(rs != null && !rs.isClosed()){
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if(preparedStatement != null && !preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

    }

    /**
     * 회원 탈퇴 함수
     */
    public void withdraw(Member member){
        String url = "jdbc:mariadb://172.30.1.93:3306/testdb3";
        String dbUserId = "root";
        String dbPassword = "zerobase";

        try {
            //드라이버 로드하는 부분
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;


        try {
            // 커넥션 객체생성하는 부분
            connection = DriverManager.getConnection(url, dbUserId, dbPassword);

            String sql = " delete from member " +
                    "where member_type = ? and user_id = ? ";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, member.getMemberType());
            preparedStatement.setString(2, member.getUserId());

            int affected  = preparedStatement.executeUpdate();

            if(affected > 0) {
                System.out.println(" 회원 탈퇴 성공 ");
            }else {
                System.out.println(" 회원 탈퇴 실패 ");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            /*
            반드시 실행하는 문장! 위에서 if문을돌리면 특정조건에 의해 if문이 안돌아오는 경우도 생기기때문에 여기에서
            if문을 사용하여 rs / statement / connection 을 close() 해주어야한다

            */


            try {
                if(rs != null && !rs.isClosed()){
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if(preparedStatement != null && !preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

    }

}
