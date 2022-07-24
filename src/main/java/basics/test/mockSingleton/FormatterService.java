package basics.test.mockSingleton;

public class FormatterService {

    private static FormatterService INSTANCE;

    private FormatterService() {}

    public static FormatterService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FormatterService();
        }
        return INSTANCE;
    }

    public String formatTachoIcon() {
        return "URL";
    }

}