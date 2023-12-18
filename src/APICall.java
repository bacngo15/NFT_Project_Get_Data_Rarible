import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.FileWriter;
import java.io.IOException;

public class APICall {
    public static void main(String[] args) {
        try {
            // Tạo một URL object với URL của API
            URL url = new URL("https://rarible.com/marketplace/api/v4/collections/search\n");

            // Mở kết nối đến URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Đặt phương thức yêu cầu thành POST
            connection.setRequestMethod("POST");

            // Bật cho phép việc gửi dữ liệu trong yêu cầu
            connection.setDoOutput(true);

            // Đặt Header cho việc gửi dữ liệu dưới dạng JSON
            connection.setRequestProperty("Content-Type", "application/json");

            // Dữ liệu JSON bạn muốn gửi
            String jsonData = "{\n" +
                    "    \"size\": 20,\n" +
                    "    \"filter\": {\n" +
                    "        \"verifiedOnly\": false,\n" +
                    "        \"sort\": \"VOLUME_DESC\",\n" +
                    "        \"blockchains\": [\n" +
                    "            \"ETHEREUM\"\n" +
                    "        ],\n" +
                    "        \"showInRanking\": false,\n" +
                    "        \"period\": \"DAY\",\n" +
                    "        \"hasCommunityMarketplace\": false,\n" +
                    "        \"currency\": \"NATIVE\"\n" +
                    "    }\n" +
                    "}";

            // Ghi dữ liệu JSON vào luồng ghi của yêu cầu
            DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
            dataOutputStream.writeBytes(jsonData);

            // Đóng luồng ghi
            dataOutputStream.close();

            // Nhận mã phản hồi
//            int responseCode = connection.getResponseCode();
            // Đọc phản hồi từ API
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = reader.readLine()) != null) {
                response.append(inputLine);
            }
            reader.close();
            try (FileWriter fileWriter = new FileWriter("rarible.json")) {
                fileWriter.write(response.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Đóng kết nối
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
