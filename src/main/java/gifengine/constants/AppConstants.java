package gifengine.constants;

public class AppConstants {

    private AppConstants() {
        //this constructor should never be called. Throw an exception if it's being called.
        throw new IllegalStateException("Utility class");
    }

    public static final int FETCH_SIZE = 5;
}
