package LampadaStuff;

public class LampadaStuff {

    /**
     * This is Type 1 Lampada Expression of 2 Different ones
     * This is located in the Login Screen, in the initialized section
     * <p>
     * This is Lampada Expression with a single Expression
     */
    public interface LampadaExpress {
        String LoginScreenEnter(String s);
    }

    /**
     * This is Type 2 Lampada Expression of 2 Different ones
     * this one is located on Main Screen
     * <p>
     * This is Lampada Expression with 2 Expressions
     */
    public interface LampadaExpressMainS {
        String MainScreenEnter(String x, String y);
    }


    public interface LampadaAdd {
        String AddSucsess(String X);
    }

    public interface DeleteSucses {
        String DeleteSucses(String x);
    }

    /**
     * There is also a lampada expression on LoginScreen1Controller
     * on lines 219-221 and methods for it below
     */
}
