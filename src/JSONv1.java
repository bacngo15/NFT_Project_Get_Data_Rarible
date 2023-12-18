import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.IOException;

public class JSONv1 {
    public static void main(String[] args) {
        try {
            // Đọc file JSON
            JsonElement jsonElement = JsonParser.parseReader(new FileReader("rarible.json"));

            // Kiểm tra xem nó có phải là một mảng không
            if (jsonElement.isJsonArray()) {
                // Chuyển đổi thành JsonArray
                JsonArray jsonArray = jsonElement.getAsJsonArray();

                // Lặp qua từng phần tử trong mảng
                for (JsonElement element : jsonArray) {
                    // Kiểm tra xem phần tử có phải là một đối tượng JSON không
                    if (element.isJsonObject()) {
                        // Chuyển đổi thành JsonObject
                        JsonObject jsonObject = element.getAsJsonObject();

                        // Khai báo thông tin từ đối tượng JSON
                        ThongTin thongTin = khaiBao(jsonObject);

                        // In ra thông tin
                        inThongTin(thongTin);

                        System.out.println("-------------------");
                    }
                }
            } else {
                System.out.println("File không chứa một mảng JSON.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Hàm để khai báo thông tin từ đối tượng JSON
    private static ThongTin khaiBao(JsonObject jsonObject) {
        ThongTin thongTin = new ThongTin();

        thongTin.id = jsonObject.get("id").getAsString();
        thongTin.name = jsonObject.get("name").getAsString();

        JsonObject collectionObject = jsonObject.getAsJsonObject("collection");
        thongTin.blockchain = collectionObject.get("blockchain").getAsString();
        thongTin.shared = collectionObject.get("shared").getAsString();
        thongTin.blacklisted = collectionObject.get("blacklisted").getAsString();
        thongTin.itemsWithRarity = collectionObject.get("itemsWithRarity").getAsString();
        thongTin.size = collectionObject.get("size").getAsString();
        thongTin.onSaleSize = collectionObject.get("onSaleSize").getAsString();
        thongTin.hasTraits = collectionObject.get("hasTraits").getAsString();
        thongTin.hasRewards = collectionObject.get("hasRewards").getAsString();

        JsonObject statisticsObject = collectionObject.getAsJsonObject("statistics");
        thongTin.itemCountTotal = statisticsObject.get("itemCountTotal").getAsString();
        thongTin.ownerCountTotal = statisticsObject.get("ownerCountTotal").getAsString();

        JsonObject totalVolumeObject = statisticsObject.getAsJsonObject("totalVolume");
        thongTin.value = totalVolumeObject.get("value").getAsString();
        thongTin.valueUsd = totalVolumeObject.get("valueUsd").getAsString();

        JsonObject mainstatisticsObject = jsonObject.getAsJsonObject("statistics");
        thongTin.owners = mainstatisticsObject.get("owners").getAsString();
        thongTin.items = mainstatisticsObject.get("items").getAsString();

        JsonObject floorPriceObject = mainstatisticsObject.getAsJsonObject("floorPrice");
        if (floorPriceObject != null) {
            thongTin.floorPricevalue = floorPriceObject.get("value").getAsString();
            thongTin.floorPricecurrency = floorPriceObject.get("currency").getAsString();
            thongTin.floorPricechangePercent = floorPriceObject.get("changePercent").getAsString();
        }
        JsonObject amountObject = mainstatisticsObject.getAsJsonObject("amount");
        thongTin.amountvalue = amountObject.get("value").getAsString();
        thongTin.amountcurrency = amountObject.get("currency").getAsString();

        JsonObject usdAmountObject = mainstatisticsObject.getAsJsonObject("usdAmount");
        thongTin.usdAmountvalue = usdAmountObject.get("value").getAsString();
        thongTin.usdAmountcurrency = usdAmountObject.get("currency").getAsString();

        if (usdAmountObject.has("changePercent")) {
            thongTin.usdAmountchangePercent = usdAmountObject.get("changePercent").getAsString();
        }

        return thongTin;
    }

    // Hàm để in thông tin
    private static void inThongTin(ThongTin thongTin) {
        System.out.println("Id: " + thongTin.id);
        System.out.println("Name: " + thongTin.name);
        System.out.println("Blockchain: " + thongTin.blockchain);
        System.out.println("Shared: " + thongTin.shared);
        System.out.println("Blacklisted: " + thongTin.blacklisted);
        System.out.println("ItemsWihtRarity: " + thongTin.itemsWithRarity);
        System.out.println("Size: " + thongTin.size);
        System.out.println("OnSaleSize: " + thongTin.onSaleSize);
        System.out.println("HasTraits: " + thongTin.hasTraits);
        System.out.println("HasRewards: " + thongTin.hasRewards);
        System.out.println("ItemCountTotal: " + thongTin.itemCountTotal);
        System.out.println("OwnerCountTotal: " + thongTin.ownerCountTotal);
        System.out.println("Value: " + thongTin.value);
        System.out.println("ValueUsd: " + thongTin.valueUsd);
        System.out.println("Owners: " + thongTin.owners);
        System.out.println("items: " + thongTin.items);
        System.out.println("FloorPrice Value: " + thongTin.floorPricevalue);
        System.out.println("FloorPrice Currency: " + thongTin.floorPricecurrency);
        System.out.println("FloorPrice ChangePercent: " + thongTin.floorPricechangePercent);
        System.out.println("Amount Value: " + thongTin.amountvalue);
        System.out.println("Amount Currency: " + thongTin.amountcurrency);
        System.out.println("UsdAmount Value: " + thongTin.usdAmountvalue);
        System.out.println("UsdAmount Currency: " + thongTin.usdAmountcurrency);

        if (thongTin.usdAmountchangePercent != null) {
            System.out.println("UsdAmount ChangePercent: " + thongTin.usdAmountchangePercent);
        } else {
            System.out.println("Khong co usd change percent");
        }
    }

    // Class để lưu trữ thông tin
    private static class ThongTin {
        String id;
        String name;
        String blockchain;
        String shared;
        String blacklisted;
        String itemsWithRarity;
        String size;
        String onSaleSize;
        String hasTraits;
        String hasRewards;
        String itemCountTotal;
        String ownerCountTotal;
        String value;
        String valueUsd;
        String owners;
        String items;
        String floorPricevalue;
        String floorPricecurrency;
        String floorPricechangePercent;
        String amountvalue;
        String amountcurrency;
        String usdAmountvalue;
        String usdAmountcurrency;
        String usdAmountchangePercent;
    }
}
