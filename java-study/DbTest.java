import java.sql.*;

public class DbTest {

    // db를 접속하기 위해서는 5개가 필요하다
    // 1. ip(domain)
    // 2.   port
    // 3.   계정
    // 4.   패스워드
    // 5.   인스턴스

    public static void main(String[] args) {
        System.out.println("Hello");

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



        try {
            // 커넥션 객체생성하는 부분
            Connection connection = DriverManager.getConnection(url, dbUserId, dbPassword);
        /*
             3. 스테이트 먼트객체는 아래와 같이 3가지가 존재한다

            Statement statement = null; ( 파라미터 처리가 부족하다 )
            PreparedStatement preparedStatement = null; ( DB쿼리를 실행하기위해서 파라미터 이슈가 필요하다  )
            CallableStatement callableStatement = null; ( 스토어드프로시저(Stored Procedure)를 실행시키기 위해 사용되는 인터페이스)
        */

            Statement statement = connection.createStatement();

            String sql = "select member_type, user_id, password, name" +
                    " from member " +
                    " where member_type = 'email' ";
                        // 4. 쿼리실행  excute가 쿼를 실행하는 부분이다
            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()) {

                String memberType = rs.getString("member_type");
                String userId = rs.getString("user_id");
                String password = rs.getString("password");
                String name = rs.getString("name");

                System.out.println(memberType + ", " + userId + ", " + password + ", " + name);

            }

            if(!rs.isClosed()){
                rs.close();
            }

            if(!statement.isClosed()) {
                statement.close();
            }

            if (!connection.isClosed()) {
                connection.close();
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
