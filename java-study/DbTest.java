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

        Connection connection = null;
        Statement statement = null;
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

            statement = connection.createStatement();

            String sql = "select member_type, user_id, password, name" +
                    " from member " +
                    " where member_type = '" + memberTypeValue + "' ";
                        // 4. 쿼리실행  excute가 쿼를 실행하는 부분이다
            rs = statement.executeQuery(sql);

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
                if(statement != null && !statement.isClosed()) {
                    statement.close();
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
