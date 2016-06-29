package pibr.bookcorner.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.app.Activity;
import android.view.ViewGroup;
import android.view.WindowManager;

import pibr.bookcorner.R;
import cn.easyar.engine.EasyAR;
import pibr.bookcorner.arutil.GLView;
import pibr.bookcorner.arutil.Renderer;

public class ARActivity extends Activity {

    private String key = "8c7ddda366895286f41460528006bbb3BzpUcACn6PByQxnEW7NVm40qU8IppdSKsL8oxdtNcHkp1xZfGpyWfRjjlln8kqHj9MWbq7xFC9SvmgWqDHHiaJN1u1eBfT0cVG5ISZrd2mpjC64I8Nuhtr1yNxS4hEdsowXKIxjotVneYwqQ76pKyua9ezuKchUk6iNYQ8M5";

    static {
        System.loadLibrary("EasyAR");
        System.loadLibrary("bookCorner");
    }

    public static native void nativeInitGL();
    public static native void nativeResizeGL(int w, int h);
    public static native void nativeRender();
    private native boolean nativeInit();
    private native void nativeDestory();
    private native void nativeRotationChange(boolean portrait);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EasyAR.initialize(this, key);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        EasyAR.initialize(this, key);
        nativeInit();

        GLView glView = new GLView(this);
        glView.setRenderer(new Renderer());
        glView.setZOrderMediaOverlay(true);

        ((ViewGroup) findViewById(R.id.preview)).addView(glView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        nativeRotationChange(getWindowManager().getDefaultDisplay().getRotation() == android.view.Surface.ROTATION_0);
    }
    @Override
    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);
        nativeRotationChange(getWindowManager().getDefaultDisplay().getRotation() == android.view.Surface.ROTATION_0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        nativeDestory();
    }
    @Override
    protected void onResume() {
        super.onResume();
        EasyAR.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        EasyAR.onPause();
    }

}
