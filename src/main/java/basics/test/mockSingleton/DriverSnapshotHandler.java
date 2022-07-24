package basics.test.mockSingleton;

public class DriverSnapshotHandler {

    public String getImageURL() {
        return FormatterService.getInstance().formatTachoIcon();
    }

}