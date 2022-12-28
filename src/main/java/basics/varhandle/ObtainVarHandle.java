package basics.varhandle;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

public class ObtainVarHandle {

    volatile int x;
    private static final VarHandle X;

    static {
        try {
            X = MethodHandles.lookup().findVarHandle(ObtainVarHandle.class, "x", int.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) {

    }



}
