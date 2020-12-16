package server;

public class DBAuthService implements AuthService{

        @Override
        public String getNicknameByLoginAndPassword(String login, String password) {
            return getNicknameByLoginAndPassword(login, password);
        }

        @Override
        public boolean registration(String login, String password, String nickname) {
            return registration(login, password, nickname);
        }

        @Override
        public boolean changeNick(String oldNickname, String newNickname) {
            return changeNick(oldNickname, newNickname);
        }
    }

