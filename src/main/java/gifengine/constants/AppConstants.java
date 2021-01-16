package gifengine.constants;

public class AppConstants {

    //Utility classes, which are collections of static members, are not meant to be instantiated. So it should not have implicit public constructor.
    //Instead, create a private constructor to overwrite.

    private AppConstants() {
        //this constructor should never be called. Throw an exception if it's being called.
        throw new IllegalStateException("Utility class");
    }

    public static final int FETCH_SIZE = 5;
}
