import java.time.LocalDate;
import java.util.Scanner;

public class ExchangeRate {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите год: ");
        int inputYear = scanner.nextInt();

        LocalDate currentDate = LocalDate.of(inputYear, 01, 01);
        LocalDate lastDate = LocalDate.of(inputYear, 12, 31);
        PageDownloader downloader = new PageDownloader();

        while (currentDate.isBefore(lastDate)) {
            String day = withZero(currentDate.getDayOfMonth());
            String month = withZero(currentDate.getMonthValue());
            String year = withZero(currentDate.getYear());
            String dateForUrl = day + "/" + month + "/" + year;
            String url = "https://www.cbr.ru/scripts/XML_dynamic.asp?date_req1=" + dateForUrl +
                    "&date_req2=" + dateForUrl + "&VAL_NM_RQ=R01235";
            String page = downloader.downloadWebPage(url);
            int startIndex = page.lastIndexOf("<Value>");
            if (startIndex != -1) {
                int endIndex = page.lastIndexOf("</Value>");
                String courseStr = page.substring(startIndex + 7, endIndex);
                System.out.println(currentDate + ": " + courseStr);
            }
            currentDate = currentDate.plusDays(1);
        }
    }
    static String withZero(int value) {
        if (value<10) {
            return "0" + value;
        } else {
            return String.valueOf(value);
        }
    }
}
