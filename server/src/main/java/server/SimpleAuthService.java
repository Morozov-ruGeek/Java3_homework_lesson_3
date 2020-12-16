package server;

import java.sql.*;

public class SimpleAuthService implements AuthService {

    private static Connection connection;
    private static PreparedStatement prepStatGetNickname;
    private static PreparedStatement prepStatRegistration;
    private static PreparedStatement prepStatChangeNick;

    //подключение к SQlite
    public static boolean connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:main.db");
            prepStatGetNickname = connection.prepareStatement("SELECT nickname FROM chatclients WHERE login = ? AND password = ?;");
            prepStatRegistration = connection.prepareStatement("INSERT INTO chatclients(login, password, nickname) VALUES (? ,? ,? );");
            prepStatChangeNick = connection.prepareStatement("UPDATE chatclients SET nickname = ? WHERE nickname = ?;");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getNicknameByLoginAndPassword(String login, String password) {
        String nick = null;
        try {
            prepStatGetNickname.setString(1, login);
            prepStatGetNickname.setString(2, password);
            ResultSet rs = prepStatGetNickname.executeQuery();
            if (rs.next()) {
                nick = rs.getString(1);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nick;
    }

    public boolean registration(String login, String password, String nickname) {
        try {
            prepStatRegistration.setString(1, login);
            prepStatRegistration.setString(2, password);
            prepStatRegistration.setString(3, nickname);
            prepStatRegistration.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean changeNick(String oldNickname, String newNickname) {
        try {
            prepStatChangeNick.setString(1, newNickname);
            prepStatChangeNick.setString(2, oldNickname);
            prepStatChangeNick.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    //отключение от SQlite
    public static void disconnect(){
        try {
            prepStatGetNickname.close();
            prepStatRegistration.close();
            prepStatChangeNick.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
