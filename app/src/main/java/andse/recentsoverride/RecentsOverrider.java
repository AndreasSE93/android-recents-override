package andse.recentsoverride;

import android.app.ActivityManager.RecentTaskInfo;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;
import de.robv.android.xposed.XposedHelpers;

public class RecentsOverrider implements IXposedHookLoadPackage {
    public void handleLoadPackage(final LoadPackageParam lpparam) {
        if (!lpparam.packageName.equals("com.android.systemui")) {
            return;
        }

        XposedBridge.log("Patching SystemUI with Recents auto-removal override");

        XposedHelpers.findAndHookMethod(
                "com.android.systemui.recents.model.RecentsTaskLoadPlan",
                lpparam.classLoader,
                "isHistoricalTask",
                RecentTaskInfo.class, new XC_MethodReplacement() {
            @Override
            protected Object replaceHookedMethod(MethodHookParam param) {
                //String name = ((RecentTaskInfo) param.args[0]).baseIntent.getComponent().flattenToShortString();
                //Log.d("Xposed", "Hooked RecentsTaskLoadPlan.isHistoricalTask(" + name + ")");
                return false;
            }
        });
    }
}
