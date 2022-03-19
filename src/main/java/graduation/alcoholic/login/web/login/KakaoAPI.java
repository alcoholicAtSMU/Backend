package graduation.alcoholic.login.web.login;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


import graduation.alcoholic.domain.User;
import graduation.alcoholic.login.domain.auth.enumerate.RoleType;
import graduation.alcoholic.login.domain.member.UserDto;
import graduation.alcoholic.login.domain.member.UserUpdateDto;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.transaction.Transactional;


@RequiredArgsConstructor
@Service
public class KakaoAPI {
    @Value("${kakao.login.client_id}")
    private String client_id;
    //토큰 가져오기
    public String getAccessToken (String authorize_code) {
        System.out.println(authorize_code);
        String access_Token = "";
        String refresh_Token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //	POST 요청을 위해 기본값이 false인 setDoOutput을 true로
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            //	POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id="+client_id);
            sb.append("&redirect_uri=http://localhost:3000/auth/login");
            sb.append("&code=" + authorize_code);
            bw.write(sb.toString());
            bw.flush();

            //	결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("토큰 얻기 responseCode : " + responseCode);

            //	요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

            System.out.println("access_token : " + access_Token);
            System.out.println("refresh_token : " + refresh_Token);
            br.close();
            bw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return access_Token;
    }

    //사용자 정보 가져오기
    public UserDto getUserInfo (String access_Token) {
        UserDto userInfo = null;
        //	요청하는 클라이언트마다 가진 정보가 다를 수 있기에 HashMap타입으로 선언
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

            //	요청에 필요한 Header에 포함될 내용
            conn.setRequestProperty("Authorization", "Bearer " + access_Token);

            int responseCode = conn.getResponseCode();
            System.out.println("정보 얻기 responseCode : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            final String authorizationHeader = conn.getHeaderField("Authorization");

            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
            JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

            String username = properties.getAsJsonObject().get("nickname").getAsString();
            String email = kakao_account.getAsJsonObject().get("email").getAsString();
            String age_range = kakao_account.getAsJsonObject().get("age_range").getAsString();
            String sex = kakao_account.getAsJsonObject().get("gender").getAsString();

            userInfo = UserDto.builder()
                    .name(username)
                    .nickname(username)
                    .email(email)
                    .sex(sex)
                    .age_range(age_range)
                    .roletype(RoleType.ROLE_USER)
                    .build();
//            userInfo.setName(username);
//            userInfo.setEmail(email);
//            userInfo.setSex(sex);
//            userInfo.setAge_range(age_range);
//            userInfo.setRoletype(RoleType.ROLE_USER);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return userInfo;
    }

    public void kakaoLogout(String access_Token) {
        String reqURL = "https://kapi.kakao.com/v1/user/logout";
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + access_Token);

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String result = "";
            String line = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println(result);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public void kakaoDelete(String access_Token) {
        String reqURL = "https://kapi.kakao.com/v1/user/unlink";
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + access_Token);

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String result = "";
            String line = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println(result);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Transactional
    public void delete(User userInfo) {
        String del_cd="D";
        userInfo.setDelete("탈퇴한 회원입니다.",del_cd);

        System.out.println(userInfo.getNickname()+","+userInfo.getDel_cd());
    }

    @Transactional
    public void recover(User userInfo){
        if(userInfo.getDel_cd()!=null) {
            userInfo.setDelete(userInfo.getName(), null);
            System.out.println(userInfo.getNickname() + "," + userInfo.getDel_cd());
        }
    }

    @Transactional
    public void update_Nickname(User userInfo, UserUpdateDto userUpdateDto){
        userInfo.signInUpdate(userUpdateDto.getNickname(),userUpdateDto.getCapacity());
    }
}