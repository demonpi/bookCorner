package pibr.bookcorner.activity;

import android.os.Bundle;
import android.app.Activity;

import pibr.bookcorner.R;
import cn.easyar.engine.EasyAR;

public class ARActivity extends Activity {

    private String key = "8c7ddda366895286f41460528006bbb3BzpUcACn6PByQxnEW7NVm40qU8IppdSKsL8oxdtNcHkp1xZfGpyWfRjjlln8kqHj9MWbq7xFC9SvmgWqDHHiaJN1u1eBfT0cVG5ISZrd2mpjC64I8Nuhtr1yNxS4hEdsowXKIxjotVneYwqQ76pKyua9ezuKchUk6iNYQ8M5";

//    static {
//        System.loadLibrary("EasyAR");
//        System.loadLibrary("HelloARVideoNative");
//    }

//    public static native void nativeInitGL();
//    public static native void nativeResizeGL(int w, int h);
//    public static native void nativeRender();
//    private native boolean nativeInit();
//    private native void nativeDestory();
//    private native void nativeRotationChange(boolean portrait);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EasyAR.initialize(this, key);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar);
    }

}
