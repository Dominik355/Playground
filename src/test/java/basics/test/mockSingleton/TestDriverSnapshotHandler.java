package basics.test.mockSingleton;

import org.junit.After;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestDriverSnapshotHandler {

    private FormatterService formatter;
    private static final String MOCKED_URL = "MOCKED_URL";

    private void setMock(FormatterService mock) {
        try {
            Field instance = FormatterService.class.getDeclaredField("INSTANCE");
            instance.setAccessible(true);
            instance.set(instance, mock);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // set value to null after the test, so it will initialize INSTAnce again
    @After
    public void resetSingleton() throws Exception {
        Field instance = FormatterService.class.getDeclaredField("INSTANCE");
        instance.setAccessible(true);
        instance.set(null, null);
    }

    @Test
    public void A_testFormatterServiceIsCalled() {
        // given
        formatter = mock(FormatterService.class);
        setMock(formatter);
        when(formatter.formatTachoIcon()).thenReturn(MOCKED_URL);

        // when
        DriverSnapshotHandler handler = new DriverSnapshotHandler();
        String url = handler.getImageURL();

        // then
        verify(formatter, atLeastOnce()).formatTachoIcon();
        assertEquals(MOCKED_URL, url);
    }

    @Test
    public void B_test2ToCheckIfInstanceIsAlright() {
        DriverSnapshotHandler handler = new DriverSnapshotHandler();
        String url = handler.getImageURL();

        System.out.println("url: " + url);
    }

}
