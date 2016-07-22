package com.pascalwelsch.compositeandroid.activity;

import com.pascalwelsch.compositeandroid.core.AbstractDelegate;
import com.pascalwelsch.compositeandroid.core.CallFun0;
import com.pascalwelsch.compositeandroid.core.CallFun1;
import com.pascalwelsch.compositeandroid.core.CallFun2;
import com.pascalwelsch.compositeandroid.core.CallFun3;
import com.pascalwelsch.compositeandroid.core.CallFun4;
import com.pascalwelsch.compositeandroid.core.CallFun6;
import com.pascalwelsch.compositeandroid.core.CallVoid0;
import com.pascalwelsch.compositeandroid.core.CallVoid1;
import com.pascalwelsch.compositeandroid.core.CallVoid2;
import com.pascalwelsch.compositeandroid.core.CallVoid3;
import com.pascalwelsch.compositeandroid.core.CallVoid4;
import com.pascalwelsch.compositeandroid.core.CallVoid5;
import com.pascalwelsch.compositeandroid.core.CallVoid6;
import com.pascalwelsch.compositeandroid.core.CallVoid7;
import com.pascalwelsch.compositeandroid.core.CallVoid8;
import com.pascalwelsch.compositeandroid.core.Removable;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.app.VoiceInteractor;
import android.app.assist.AssistContent;
import android.content.BroadcastReceiver;
import android.content.ComponentCallbacks;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.PersistableBundle;
import android.os.UserHandle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.SharedElementCallback;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.view.ActionMode;
import android.transition.Scene;
import android.transition.TransitionManager;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SearchEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Toolbar;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

public class ActivityDelegate extends AbstractDelegate<ICompositeActivity, ActivityPlugin> {


    @VisibleForTesting
    static final int CALL_COUNT_OPTIMIZATION_THRESHOLD = 100;

    private int mGetResourcesCallCount = 0;

    private transient boolean mIsGetResourcesOverridden = true;

    private final HashMap<String, List<ActivityPlugin>> mMethodImplementingPlugins
            = new HashMap<>();

    public ActivityDelegate(final ICompositeActivity icompositeactivity) {
        super(icompositeactivity);

    }

    public void addContentView(final View view, final ViewGroup.LayoutParams params) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_addContentView(view, params);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid2<View, ViewGroup.LayoutParams> superCall
                = new CallVoid2<View, ViewGroup.LayoutParams>(
                "addContentView(View, ViewGroup.LayoutParams)") {

            @Override
            public void call(final View view, final ViewGroup.LayoutParams params) {
                if (iterator.hasPrevious()) {
                    iterator.previous().addContentView(this, view, params);
                } else {
                    getOriginal().super_addContentView(view, params);
                }
            }
        };
        superCall.call(view, params);
    }

    @Override
    public Removable addPlugin(final ActivityPlugin plugin) {
        synchronized (mPlugins) {
            mIsGetResourcesOverridden = true;
            mMethodImplementingPlugins.clear();
        }
        return super.addPlugin(plugin);
    }

    public void applyOverrideConfiguration(final Configuration overrideConfiguration) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_applyOverrideConfiguration(overrideConfiguration);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid1<Configuration> superCall = new CallVoid1<Configuration>(
                "applyOverrideConfiguration(Configuration)") {

            @Override
            public void call(final Configuration overrideConfiguration) {
                if (iterator.hasPrevious()) {
                    iterator.previous().applyOverrideConfiguration(this, overrideConfiguration);
                } else {
                    getOriginal().super_applyOverrideConfiguration(overrideConfiguration);
                }
            }
        };
        superCall.call(overrideConfiguration);
    }

    public void attachBaseContext(final Context newBase) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_attachBaseContext(newBase);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid1<Context> superCall = new CallVoid1<Context>("attachBaseContext(Context)") {

            @Override
            public void call(final Context newBase) {
                if (iterator.hasPrevious()) {
                    iterator.previous().attachBaseContext(this, newBase);
                } else {
                    getOriginal().super_attachBaseContext(newBase);
                }
            }
        };
        superCall.call(newBase);
    }

    public boolean bindService(final Intent service, final ServiceConnection conn,
            final int flags) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_bindService(service, conn, flags);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun3<Boolean, Intent, ServiceConnection, Integer> superCall
                = new CallFun3<Boolean, Intent, ServiceConnection, Integer>(
                "bindService(Intent, ServiceConnection, Integer)") {

            @Override
            public Boolean call(final Intent service, final ServiceConnection conn,
                    final Integer flags) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().bindService(this, service, conn, flags);
                } else {
                    return getOriginal().super_bindService(service, conn, flags);
                }
            }
        };
        return superCall.call(service, conn, flags);
    }

    public int checkCallingOrSelfPermission(final String permission) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_checkCallingOrSelfPermission(permission);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun1<Integer, String> superCall = new CallFun1<Integer, String>(
                "checkCallingOrSelfPermission(String)") {

            @Override
            public Integer call(final String permission) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().checkCallingOrSelfPermission(this, permission);
                } else {
                    return getOriginal().super_checkCallingOrSelfPermission(permission);
                }
            }
        };
        return superCall.call(permission);
    }

    public int checkCallingOrSelfUriPermission(final Uri uri, final int modeFlags) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_checkCallingOrSelfUriPermission(uri, modeFlags);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun2<Integer, Uri, Integer> superCall = new CallFun2<Integer, Uri, Integer>(
                "checkCallingOrSelfUriPermission(Uri, Integer)") {

            @Override
            public Integer call(final Uri uri, final Integer modeFlags) {
                if (iterator.hasPrevious()) {
                    return iterator.previous()
                            .checkCallingOrSelfUriPermission(this, uri, modeFlags);
                } else {
                    return getOriginal().super_checkCallingOrSelfUriPermission(uri, modeFlags);
                }
            }
        };
        return superCall.call(uri, modeFlags);
    }

    public int checkCallingPermission(final String permission) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_checkCallingPermission(permission);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun1<Integer, String> superCall = new CallFun1<Integer, String>(
                "checkCallingPermission(String)") {

            @Override
            public Integer call(final String permission) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().checkCallingPermission(this, permission);
                } else {
                    return getOriginal().super_checkCallingPermission(permission);
                }
            }
        };
        return superCall.call(permission);
    }

    public int checkCallingUriPermission(final Uri uri, final int modeFlags) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_checkCallingUriPermission(uri, modeFlags);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun2<Integer, Uri, Integer> superCall = new CallFun2<Integer, Uri, Integer>(
                "checkCallingUriPermission(Uri, Integer)") {

            @Override
            public Integer call(final Uri uri, final Integer modeFlags) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().checkCallingUriPermission(this, uri, modeFlags);
                } else {
                    return getOriginal().super_checkCallingUriPermission(uri, modeFlags);
                }
            }
        };
        return superCall.call(uri, modeFlags);
    }

    public int checkPermission(final String permission, final int pid, final int uid) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_checkPermission(permission, pid, uid);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun3<Integer, String, Integer, Integer> superCall
                = new CallFun3<Integer, String, Integer, Integer>(
                "checkPermission(String, Integer, Integer)") {

            @Override
            public Integer call(final String permission, final Integer pid, final Integer uid) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().checkPermission(this, permission, pid, uid);
                } else {
                    return getOriginal().super_checkPermission(permission, pid, uid);
                }
            }
        };
        return superCall.call(permission, pid, uid);
    }

    public int checkSelfPermission(final String permission) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_checkSelfPermission(permission);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun1<Integer, String> superCall = new CallFun1<Integer, String>(
                "checkSelfPermission(String)") {

            @Override
            public Integer call(final String permission) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().checkSelfPermission(this, permission);
                } else {
                    return getOriginal().super_checkSelfPermission(permission);
                }
            }
        };
        return superCall.call(permission);
    }

    public int checkUriPermission(final Uri uri, final int pid, final int uid,
            final int modeFlags) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_checkUriPermission(uri, pid, uid, modeFlags);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun4<Integer, Uri, Integer, Integer, Integer> superCall
                = new CallFun4<Integer, Uri, Integer, Integer, Integer>(
                "checkUriPermission(Uri, Integer, Integer, Integer)") {

            @Override
            public Integer call(final Uri uri, final Integer pid, final Integer uid,
                    final Integer modeFlags) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().checkUriPermission(this, uri, pid, uid, modeFlags);
                } else {
                    return getOriginal().super_checkUriPermission(uri, pid, uid, modeFlags);
                }
            }
        };
        return superCall.call(uri, pid, uid, modeFlags);
    }

    public int checkUriPermission(final Uri uri, final String readPermission,
            final String writePermission, final int pid, final int uid, final int modeFlags) {
        if (mPlugins.isEmpty()) {
            return getOriginal()
                    .super_checkUriPermission(uri, readPermission, writePermission, pid, uid,
                            modeFlags);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun6<Integer, Uri, String, String, Integer, Integer, Integer> superCall
                = new CallFun6<Integer, Uri, String, String, Integer, Integer, Integer>(
                "checkUriPermission(Uri, String, String, Integer, Integer, Integer)") {

            @Override
            public Integer call(final Uri uri, final String readPermission,
                    final String writePermission, final Integer pid, final Integer uid,
                    final Integer modeFlags) {
                if (iterator.hasPrevious()) {
                    return iterator.previous()
                            .checkUriPermission(this, uri, readPermission, writePermission, pid,
                                    uid, modeFlags);
                } else {
                    return getOriginal()
                            .super_checkUriPermission(uri, readPermission, writePermission, pid,
                                    uid, modeFlags);
                }
            }
        };
        return superCall.call(uri, readPermission, writePermission, pid, uid, modeFlags);
    }

    public void clearWallpaper() throws IOException {
        if (mPlugins.isEmpty()) {
            try {
                getOriginal().super_clearWallpaper();
            } catch (IOException e) {
                throw new SuppressedException(e);
            }
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid0 superCall = new CallVoid0("clearWallpaper()") {

            @Override
            public void call() {
                if (iterator.hasPrevious()) {
                    try {
                        iterator.previous().clearWallpaper(this);
                    } catch (IOException e) {
                        throw new SuppressedException(e);
                    }
                } else {
                    try {
                        getOriginal().super_clearWallpaper();
                    } catch (IOException e) {
                        throw new SuppressedException(e);
                    }
                }
            }
        };
        superCall.call();
    }

    public void closeContextMenu() {
        if (mPlugins.isEmpty()) {
            getOriginal().super_closeContextMenu();
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid0 superCall = new CallVoid0("closeContextMenu()") {

            @Override
            public void call() {
                if (iterator.hasPrevious()) {
                    iterator.previous().closeContextMenu(this);
                } else {
                    getOriginal().super_closeContextMenu();
                }
            }
        };
        superCall.call();
    }

    public void closeOptionsMenu() {
        if (mPlugins.isEmpty()) {
            getOriginal().super_closeOptionsMenu();
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid0 superCall = new CallVoid0("closeOptionsMenu()") {

            @Override
            public void call() {
                if (iterator.hasPrevious()) {
                    iterator.previous().closeOptionsMenu(this);
                } else {
                    getOriginal().super_closeOptionsMenu();
                }
            }
        };
        superCall.call();
    }

    public Context createConfigurationContext(final Configuration overrideConfiguration) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_createConfigurationContext(overrideConfiguration);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun1<Context, Configuration> superCall = new CallFun1<Context, Configuration>(
                "createConfigurationContext(Configuration)") {

            @Override
            public Context call(final Configuration overrideConfiguration) {
                if (iterator.hasPrevious()) {
                    return iterator.previous()
                            .createConfigurationContext(this, overrideConfiguration);
                } else {
                    return getOriginal().super_createConfigurationContext(overrideConfiguration);
                }
            }
        };
        return superCall.call(overrideConfiguration);
    }

    public Context createDisplayContext(final Display display) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_createDisplayContext(display);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun1<Context, Display> superCall = new CallFun1<Context, Display>(
                "createDisplayContext(Display)") {

            @Override
            public Context call(final Display display) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().createDisplayContext(this, display);
                } else {
                    return getOriginal().super_createDisplayContext(display);
                }
            }
        };
        return superCall.call(display);
    }

    public Context createPackageContext(final String packageName, final int flags)
            throws PackageManager.NameNotFoundException {
        if (mPlugins.isEmpty()) {
            try {
                return getOriginal().super_createPackageContext(packageName, flags);
            } catch (PackageManager.NameNotFoundException e) {
                throw new SuppressedException(e);
            }
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun2<Context, String, Integer> superCall = new CallFun2<Context, String, Integer>(
                "createPackageContext(String, Integer)") {

            @Override
            public Context call(final String packageName, final Integer flags) {
                if (iterator.hasPrevious()) {
                    try {
                        return iterator.previous().createPackageContext(this, packageName, flags);
                    } catch (PackageManager.NameNotFoundException e) {
                        throw new SuppressedException(e);
                    }
                } else {
                    try {
                        return getOriginal().super_createPackageContext(packageName, flags);
                    } catch (PackageManager.NameNotFoundException e) {
                        throw new SuppressedException(e);
                    }
                }
            }
        };
        return superCall.call(packageName, flags);
    }

    public PendingIntent createPendingResult(final int requestCode, final Intent data,
            final int flags) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_createPendingResult(requestCode, data, flags);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun3<PendingIntent, Integer, Intent, Integer> superCall
                = new CallFun3<PendingIntent, Integer, Intent, Integer>(
                "createPendingResult(Integer, Intent, Integer)") {

            @Override
            public PendingIntent call(final Integer requestCode, final Intent data,
                    final Integer flags) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().createPendingResult(this, requestCode, data, flags);
                } else {
                    return getOriginal().super_createPendingResult(requestCode, data, flags);
                }
            }
        };
        return superCall.call(requestCode, data, flags);
    }

    public String[] databaseList() {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_databaseList();
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun0<String[]> superCall = new CallFun0<String[]>("databaseList()") {

            @Override
            public String[] call() {
                if (iterator.hasPrevious()) {
                    return iterator.previous().databaseList(this);
                } else {
                    return getOriginal().super_databaseList();
                }
            }
        };
        return superCall.call();
    }

    public boolean deleteDatabase(final String name) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_deleteDatabase(name);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun1<Boolean, String> superCall = new CallFun1<Boolean, String>(
                "deleteDatabase(String)") {

            @Override
            public Boolean call(final String name) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().deleteDatabase(this, name);
                } else {
                    return getOriginal().super_deleteDatabase(name);
                }
            }
        };
        return superCall.call(name);
    }

    public boolean deleteFile(final String name) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_deleteFile(name);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun1<Boolean, String> superCall = new CallFun1<Boolean, String>(
                "deleteFile(String)") {

            @Override
            public Boolean call(final String name) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().deleteFile(this, name);
                } else {
                    return getOriginal().super_deleteFile(name);
                }
            }
        };
        return superCall.call(name);
    }

    public boolean dispatchGenericMotionEvent(final MotionEvent ev) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_dispatchGenericMotionEvent(ev);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun1<Boolean, MotionEvent> superCall = new CallFun1<Boolean, MotionEvent>(
                "dispatchGenericMotionEvent(MotionEvent)") {

            @Override
            public Boolean call(final MotionEvent ev) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().dispatchGenericMotionEvent(this, ev);
                } else {
                    return getOriginal().super_dispatchGenericMotionEvent(ev);
                }
            }
        };
        return superCall.call(ev);
    }

    public boolean dispatchKeyEvent(final KeyEvent event) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_dispatchKeyEvent(event);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun1<Boolean, KeyEvent> superCall = new CallFun1<Boolean, KeyEvent>(
                "dispatchKeyEvent(KeyEvent)") {

            @Override
            public Boolean call(final KeyEvent event) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().dispatchKeyEvent(this, event);
                } else {
                    return getOriginal().super_dispatchKeyEvent(event);
                }
            }
        };
        return superCall.call(event);
    }

    public boolean dispatchKeyShortcutEvent(final KeyEvent event) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_dispatchKeyShortcutEvent(event);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun1<Boolean, KeyEvent> superCall = new CallFun1<Boolean, KeyEvent>(
                "dispatchKeyShortcutEvent(KeyEvent)") {

            @Override
            public Boolean call(final KeyEvent event) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().dispatchKeyShortcutEvent(this, event);
                } else {
                    return getOriginal().super_dispatchKeyShortcutEvent(event);
                }
            }
        };
        return superCall.call(event);
    }

    public boolean dispatchPopulateAccessibilityEvent(final AccessibilityEvent event) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_dispatchPopulateAccessibilityEvent(event);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun1<Boolean, AccessibilityEvent> superCall
                = new CallFun1<Boolean, AccessibilityEvent>(
                "dispatchPopulateAccessibilityEvent(AccessibilityEvent)") {

            @Override
            public Boolean call(final AccessibilityEvent event) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().dispatchPopulateAccessibilityEvent(this, event);
                } else {
                    return getOriginal().super_dispatchPopulateAccessibilityEvent(event);
                }
            }
        };
        return superCall.call(event);
    }

    public boolean dispatchTouchEvent(final MotionEvent ev) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_dispatchTouchEvent(ev);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun1<Boolean, MotionEvent> superCall = new CallFun1<Boolean, MotionEvent>(
                "dispatchTouchEvent(MotionEvent)") {

            @Override
            public Boolean call(final MotionEvent ev) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().dispatchTouchEvent(this, ev);
                } else {
                    return getOriginal().super_dispatchTouchEvent(ev);
                }
            }
        };
        return superCall.call(ev);
    }

    public boolean dispatchTrackballEvent(final MotionEvent ev) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_dispatchTrackballEvent(ev);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun1<Boolean, MotionEvent> superCall = new CallFun1<Boolean, MotionEvent>(
                "dispatchTrackballEvent(MotionEvent)") {

            @Override
            public Boolean call(final MotionEvent ev) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().dispatchTrackballEvent(this, ev);
                } else {
                    return getOriginal().super_dispatchTrackballEvent(ev);
                }
            }
        };
        return superCall.call(ev);
    }

    public void dump(final String prefix, final FileDescriptor fd, final PrintWriter writer,
            final String[] args) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_dump(prefix, fd, writer, args);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid4<String, FileDescriptor, PrintWriter, String[]> superCall
                = new CallVoid4<String, FileDescriptor, PrintWriter, String[]>(
                "dump(String, FileDescriptor, PrintWriter, String[])") {

            @Override
            public void call(final String prefix, final FileDescriptor fd, final PrintWriter writer,
                    final String[] args) {
                if (iterator.hasPrevious()) {
                    iterator.previous().dump(this, prefix, fd, writer, args);
                } else {
                    getOriginal().super_dump(prefix, fd, writer, args);
                }
            }
        };
        superCall.call(prefix, fd, writer, args);
    }

    public void enforceCallingOrSelfPermission(final String permission, final String message) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_enforceCallingOrSelfPermission(permission, message);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid2<String, String> superCall = new CallVoid2<String, String>(
                "enforceCallingOrSelfPermission(String, String)") {

            @Override
            public void call(final String permission, final String message) {
                if (iterator.hasPrevious()) {
                    iterator.previous().enforceCallingOrSelfPermission(this, permission, message);
                } else {
                    getOriginal().super_enforceCallingOrSelfPermission(permission, message);
                }
            }
        };
        superCall.call(permission, message);
    }

    public void enforceCallingOrSelfUriPermission(final Uri uri, final int modeFlags,
            final String message) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_enforceCallingOrSelfUriPermission(uri, modeFlags, message);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid3<Uri, Integer, String> superCall = new CallVoid3<Uri, Integer, String>(
                "enforceCallingOrSelfUriPermission(Uri, Integer, String)") {

            @Override
            public void call(final Uri uri, final Integer modeFlags, final String message) {
                if (iterator.hasPrevious()) {
                    iterator.previous()
                            .enforceCallingOrSelfUriPermission(this, uri, modeFlags, message);
                } else {
                    getOriginal().super_enforceCallingOrSelfUriPermission(uri, modeFlags, message);
                }
            }
        };
        superCall.call(uri, modeFlags, message);
    }

    public void enforceCallingPermission(final String permission, final String message) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_enforceCallingPermission(permission, message);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid2<String, String> superCall = new CallVoid2<String, String>(
                "enforceCallingPermission(String, String)") {

            @Override
            public void call(final String permission, final String message) {
                if (iterator.hasPrevious()) {
                    iterator.previous().enforceCallingPermission(this, permission, message);
                } else {
                    getOriginal().super_enforceCallingPermission(permission, message);
                }
            }
        };
        superCall.call(permission, message);
    }

    public void enforceCallingUriPermission(final Uri uri, final int modeFlags,
            final String message) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_enforceCallingUriPermission(uri, modeFlags, message);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid3<Uri, Integer, String> superCall = new CallVoid3<Uri, Integer, String>(
                "enforceCallingUriPermission(Uri, Integer, String)") {

            @Override
            public void call(final Uri uri, final Integer modeFlags, final String message) {
                if (iterator.hasPrevious()) {
                    iterator.previous().enforceCallingUriPermission(this, uri, modeFlags, message);
                } else {
                    getOriginal().super_enforceCallingUriPermission(uri, modeFlags, message);
                }
            }
        };
        superCall.call(uri, modeFlags, message);
    }

    public void enforcePermission(final String permission, final int pid, final int uid,
            final String message) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_enforcePermission(permission, pid, uid, message);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid4<String, Integer, Integer, String> superCall
                = new CallVoid4<String, Integer, Integer, String>(
                "enforcePermission(String, Integer, Integer, String)") {

            @Override
            public void call(final String permission, final Integer pid, final Integer uid,
                    final String message) {
                if (iterator.hasPrevious()) {
                    iterator.previous().enforcePermission(this, permission, pid, uid, message);
                } else {
                    getOriginal().super_enforcePermission(permission, pid, uid, message);
                }
            }
        };
        superCall.call(permission, pid, uid, message);
    }

    public void enforceUriPermission(final Uri uri, final int pid, final int uid,
            final int modeFlags, final String message) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_enforceUriPermission(uri, pid, uid, modeFlags, message);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid5<Uri, Integer, Integer, Integer, String> superCall
                = new CallVoid5<Uri, Integer, Integer, Integer, String>(
                "enforceUriPermission(Uri, Integer, Integer, Integer, String)") {

            @Override
            public void call(final Uri uri, final Integer pid, final Integer uid,
                    final Integer modeFlags, final String message) {
                if (iterator.hasPrevious()) {
                    iterator.previous()
                            .enforceUriPermission(this, uri, pid, uid, modeFlags, message);
                } else {
                    getOriginal().super_enforceUriPermission(uri, pid, uid, modeFlags, message);
                }
            }
        };
        superCall.call(uri, pid, uid, modeFlags, message);
    }

    public void enforceUriPermission(final Uri uri, final String readPermission,
            final String writePermission, final int pid, final int uid, final int modeFlags,
            final String message) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_enforceUriPermission(uri, readPermission, writePermission, pid, uid,
                    modeFlags, message);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid7<Uri, String, String, Integer, Integer, Integer, String> superCall
                = new CallVoid7<Uri, String, String, Integer, Integer, Integer, String>(
                "enforceUriPermission(Uri, String, String, Integer, Integer, Integer, String)") {

            @Override
            public void call(final Uri uri, final String readPermission,
                    final String writePermission, final Integer pid, final Integer uid,
                    final Integer modeFlags, final String message) {
                if (iterator.hasPrevious()) {
                    iterator.previous()
                            .enforceUriPermission(this, uri, readPermission, writePermission, pid,
                                    uid, modeFlags, message);
                } else {
                    getOriginal()
                            .super_enforceUriPermission(uri, readPermission, writePermission, pid,
                                    uid, modeFlags, message);
                }
            }
        };
        superCall.call(uri, readPermission, writePermission, pid, uid, modeFlags, message);
    }

    public String[] fileList() {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_fileList();
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun0<String[]> superCall = new CallFun0<String[]>("fileList()") {

            @Override
            public String[] call() {
                if (iterator.hasPrevious()) {
                    return iterator.previous().fileList(this);
                } else {
                    return getOriginal().super_fileList();
                }
            }
        };
        return superCall.call();
    }

    public View findViewById(@IdRes final int id) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_findViewById(id);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun1<View, Integer> superCall = new CallFun1<View, Integer>(
                "findViewById(Integer)") {

            @Override
            public View call(final Integer id) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().findViewById(this, id);
                } else {
                    return getOriginal().super_findViewById(id);
                }
            }
        };
        return superCall.call(id);
    }

    public void finish() {
        if (mPlugins.isEmpty()) {
            getOriginal().super_finish();
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid0 superCall = new CallVoid0("finish()") {

            @Override
            public void call() {
                if (iterator.hasPrevious()) {
                    iterator.previous().finish(this);
                } else {
                    getOriginal().super_finish();
                }
            }
        };
        superCall.call();
    }

    public void finishActivity(final int requestCode) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_finishActivity(requestCode);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid1<Integer> superCall = new CallVoid1<Integer>("finishActivity(Integer)") {

            @Override
            public void call(final Integer requestCode) {
                if (iterator.hasPrevious()) {
                    iterator.previous().finishActivity(this, requestCode);
                } else {
                    getOriginal().super_finishActivity(requestCode);
                }
            }
        };
        superCall.call(requestCode);
    }

    public void finishActivityFromChild(final Activity child, final int requestCode) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_finishActivityFromChild(child, requestCode);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid2<Activity, Integer> superCall = new CallVoid2<Activity, Integer>(
                "finishActivityFromChild(Activity, Integer)") {

            @Override
            public void call(final Activity child, final Integer requestCode) {
                if (iterator.hasPrevious()) {
                    iterator.previous().finishActivityFromChild(this, child, requestCode);
                } else {
                    getOriginal().super_finishActivityFromChild(child, requestCode);
                }
            }
        };
        superCall.call(child, requestCode);
    }

    public void finishAffinity() {
        if (mPlugins.isEmpty()) {
            getOriginal().super_finishAffinity();
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid0 superCall = new CallVoid0("finishAffinity()") {

            @Override
            public void call() {
                if (iterator.hasPrevious()) {
                    iterator.previous().finishAffinity(this);
                } else {
                    getOriginal().super_finishAffinity();
                }
            }
        };
        superCall.call();
    }

    public void finishAfterTransition() {
        if (mPlugins.isEmpty()) {
            getOriginal().super_finishAfterTransition();
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid0 superCall = new CallVoid0("finishAfterTransition()") {

            @Override
            public void call() {
                if (iterator.hasPrevious()) {
                    iterator.previous().finishAfterTransition(this);
                } else {
                    getOriginal().super_finishAfterTransition();
                }
            }
        };
        superCall.call();
    }

    public void finishAndRemoveTask() {
        if (mPlugins.isEmpty()) {
            getOriginal().super_finishAndRemoveTask();
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid0 superCall = new CallVoid0("finishAndRemoveTask()") {

            @Override
            public void call() {
                if (iterator.hasPrevious()) {
                    iterator.previous().finishAndRemoveTask(this);
                } else {
                    getOriginal().super_finishAndRemoveTask();
                }
            }
        };
        superCall.call();
    }

    public void finishFromChild(final Activity child) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_finishFromChild(child);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid1<Activity> superCall = new CallVoid1<Activity>("finishFromChild(Activity)") {

            @Override
            public void call(final Activity child) {
                if (iterator.hasPrevious()) {
                    iterator.previous().finishFromChild(this, child);
                } else {
                    getOriginal().super_finishFromChild(child);
                }
            }
        };
        superCall.call(child);
    }

    public android.app.ActionBar getActionBar() {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_getActionBar();
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun0<android.app.ActionBar> superCall = new CallFun0<android.app.ActionBar>(
                "getActionBar()") {

            @Override
            public android.app.ActionBar call() {
                if (iterator.hasPrevious()) {
                    return iterator.previous().getActionBar(this);
                } else {
                    return getOriginal().super_getActionBar();
                }
            }
        };
        return superCall.call();
    }

    public Context getApplicationContext() {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_getApplicationContext();
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun0<Context> superCall = new CallFun0<Context>("getApplicationContext()") {

            @Override
            public Context call() {
                if (iterator.hasPrevious()) {
                    return iterator.previous().getApplicationContext(this);
                } else {
                    return getOriginal().super_getApplicationContext();
                }
            }
        };
        return superCall.call();
    }

    public ApplicationInfo getApplicationInfo() {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_getApplicationInfo();
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun0<ApplicationInfo> superCall = new CallFun0<ApplicationInfo>(
                "getApplicationInfo()") {

            @Override
            public ApplicationInfo call() {
                if (iterator.hasPrevious()) {
                    return iterator.previous().getApplicationInfo(this);
                } else {
                    return getOriginal().super_getApplicationInfo();
                }
            }
        };
        return superCall.call();
    }

    public AssetManager getAssets() {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_getAssets();
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun0<AssetManager> superCall = new CallFun0<AssetManager>("getAssets()") {

            @Override
            public AssetManager call() {
                if (iterator.hasPrevious()) {
                    return iterator.previous().getAssets(this);
                } else {
                    return getOriginal().super_getAssets();
                }
            }
        };
        return superCall.call();
    }

    public Context getBaseContext() {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_getBaseContext();
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun0<Context> superCall = new CallFun0<Context>("getBaseContext()") {

            @Override
            public Context call() {
                if (iterator.hasPrevious()) {
                    return iterator.previous().getBaseContext(this);
                } else {
                    return getOriginal().super_getBaseContext();
                }
            }
        };
        return superCall.call();
    }

    public File getCacheDir() {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_getCacheDir();
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun0<File> superCall = new CallFun0<File>("getCacheDir()") {

            @Override
            public File call() {
                if (iterator.hasPrevious()) {
                    return iterator.previous().getCacheDir(this);
                } else {
                    return getOriginal().super_getCacheDir();
                }
            }
        };
        return superCall.call();
    }

    public ComponentName getCallingActivity() {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_getCallingActivity();
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun0<ComponentName> superCall = new CallFun0<ComponentName>(
                "getCallingActivity()") {

            @Override
            public ComponentName call() {
                if (iterator.hasPrevious()) {
                    return iterator.previous().getCallingActivity(this);
                } else {
                    return getOriginal().super_getCallingActivity();
                }
            }
        };
        return superCall.call();
    }

    public String getCallingPackage() {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_getCallingPackage();
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun0<String> superCall = new CallFun0<String>("getCallingPackage()") {

            @Override
            public String call() {
                if (iterator.hasPrevious()) {
                    return iterator.previous().getCallingPackage(this);
                } else {
                    return getOriginal().super_getCallingPackage();
                }
            }
        };
        return superCall.call();
    }

    public int getChangingConfigurations() {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_getChangingConfigurations();
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun0<Integer> superCall = new CallFun0<Integer>("getChangingConfigurations()") {

            @Override
            public Integer call() {
                if (iterator.hasPrevious()) {
                    return iterator.previous().getChangingConfigurations(this);
                } else {
                    return getOriginal().super_getChangingConfigurations();
                }
            }
        };
        return superCall.call();
    }

    public ClassLoader getClassLoader() {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_getClassLoader();
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun0<ClassLoader> superCall = new CallFun0<ClassLoader>("getClassLoader()") {

            @Override
            public ClassLoader call() {
                if (iterator.hasPrevious()) {
                    return iterator.previous().getClassLoader(this);
                } else {
                    return getOriginal().super_getClassLoader();
                }
            }
        };
        return superCall.call();
    }

    public File getCodeCacheDir() {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_getCodeCacheDir();
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun0<File> superCall = new CallFun0<File>("getCodeCacheDir()") {

            @Override
            public File call() {
                if (iterator.hasPrevious()) {
                    return iterator.previous().getCodeCacheDir(this);
                } else {
                    return getOriginal().super_getCodeCacheDir();
                }
            }
        };
        return superCall.call();
    }

    public ComponentName getComponentName() {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_getComponentName();
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun0<ComponentName> superCall = new CallFun0<ComponentName>(
                "getComponentName()") {

            @Override
            public ComponentName call() {
                if (iterator.hasPrevious()) {
                    return iterator.previous().getComponentName(this);
                } else {
                    return getOriginal().super_getComponentName();
                }
            }
        };
        return superCall.call();
    }

    public ContentResolver getContentResolver() {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_getContentResolver();
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun0<ContentResolver> superCall = new CallFun0<ContentResolver>(
                "getContentResolver()") {

            @Override
            public ContentResolver call() {
                if (iterator.hasPrevious()) {
                    return iterator.previous().getContentResolver(this);
                } else {
                    return getOriginal().super_getContentResolver();
                }
            }
        };
        return superCall.call();
    }

    public Scene getContentScene() {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_getContentScene();
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun0<Scene> superCall = new CallFun0<Scene>("getContentScene()") {

            @Override
            public Scene call() {
                if (iterator.hasPrevious()) {
                    return iterator.previous().getContentScene(this);
                } else {
                    return getOriginal().super_getContentScene();
                }
            }
        };
        return superCall.call();
    }

    public TransitionManager getContentTransitionManager() {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_getContentTransitionManager();
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun0<TransitionManager> superCall = new CallFun0<TransitionManager>(
                "getContentTransitionManager()") {

            @Override
            public TransitionManager call() {
                if (iterator.hasPrevious()) {
                    return iterator.previous().getContentTransitionManager(this);
                } else {
                    return getOriginal().super_getContentTransitionManager();
                }
            }
        };
        return superCall.call();
    }

    public View getCurrentFocus() {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_getCurrentFocus();
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun0<View> superCall = new CallFun0<View>("getCurrentFocus()") {

            @Override
            public View call() {
                if (iterator.hasPrevious()) {
                    return iterator.previous().getCurrentFocus(this);
                } else {
                    return getOriginal().super_getCurrentFocus();
                }
            }
        };
        return superCall.call();
    }

    public File getDatabasePath(final String name) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_getDatabasePath(name);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun1<File, String> superCall = new CallFun1<File, String>(
                "getDatabasePath(String)") {

            @Override
            public File call(final String name) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().getDatabasePath(this, name);
                } else {
                    return getOriginal().super_getDatabasePath(name);
                }
            }
        };
        return superCall.call(name);
    }

    public AppCompatDelegate getDelegate() {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_getDelegate();
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun0<AppCompatDelegate> superCall = new CallFun0<AppCompatDelegate>(
                "getDelegate()") {

            @Override
            public AppCompatDelegate call() {
                if (iterator.hasPrevious()) {
                    return iterator.previous().getDelegate(this);
                } else {
                    return getOriginal().super_getDelegate();
                }
            }
        };
        return superCall.call();
    }

    public File getDir(final String name, final int mode) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_getDir(name, mode);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun2<File, String, Integer> superCall = new CallFun2<File, String, Integer>(
                "getDir(String, Integer)") {

            @Override
            public File call(final String name, final Integer mode) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().getDir(this, name, mode);
                } else {
                    return getOriginal().super_getDir(name, mode);
                }
            }
        };
        return superCall.call(name, mode);
    }

    public ActionBarDrawerToggle.Delegate getDrawerToggleDelegate() {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_getDrawerToggleDelegate();
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun0<ActionBarDrawerToggle.Delegate> superCall
                = new CallFun0<ActionBarDrawerToggle.Delegate>("getDrawerToggleDelegate()") {

            @Override
            public ActionBarDrawerToggle.Delegate call() {
                if (iterator.hasPrevious()) {
                    return iterator.previous().getDrawerToggleDelegate(this);
                } else {
                    return getOriginal().super_getDrawerToggleDelegate();
                }
            }
        };
        return superCall.call();
    }

    public File getExternalCacheDir() {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_getExternalCacheDir();
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun0<File> superCall = new CallFun0<File>("getExternalCacheDir()") {

            @Override
            public File call() {
                if (iterator.hasPrevious()) {
                    return iterator.previous().getExternalCacheDir(this);
                } else {
                    return getOriginal().super_getExternalCacheDir();
                }
            }
        };
        return superCall.call();
    }

    public File[] getExternalCacheDirs() {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_getExternalCacheDirs();
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun0<File[]> superCall = new CallFun0<File[]>("getExternalCacheDirs()") {

            @Override
            public File[] call() {
                if (iterator.hasPrevious()) {
                    return iterator.previous().getExternalCacheDirs(this);
                } else {
                    return getOriginal().super_getExternalCacheDirs();
                }
            }
        };
        return superCall.call();
    }

    public File getExternalFilesDir(final String type) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_getExternalFilesDir(type);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun1<File, String> superCall = new CallFun1<File, String>(
                "getExternalFilesDir(String)") {

            @Override
            public File call(final String type) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().getExternalFilesDir(this, type);
                } else {
                    return getOriginal().super_getExternalFilesDir(type);
                }
            }
        };
        return superCall.call(type);
    }

    public File[] getExternalFilesDirs(final String type) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_getExternalFilesDirs(type);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun1<File[], String> superCall = new CallFun1<File[], String>(
                "getExternalFilesDirs(String)") {

            @Override
            public File[] call(final String type) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().getExternalFilesDirs(this, type);
                } else {
                    return getOriginal().super_getExternalFilesDirs(type);
                }
            }
        };
        return superCall.call(type);
    }

    public File[] getExternalMediaDirs() {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_getExternalMediaDirs();
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun0<File[]> superCall = new CallFun0<File[]>("getExternalMediaDirs()") {

            @Override
            public File[] call() {
                if (iterator.hasPrevious()) {
                    return iterator.previous().getExternalMediaDirs(this);
                } else {
                    return getOriginal().super_getExternalMediaDirs();
                }
            }
        };
        return superCall.call();
    }

    public File getFileStreamPath(final String name) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_getFileStreamPath(name);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun1<File, String> superCall = new CallFun1<File, String>(
                "getFileStreamPath(String)") {

            @Override
            public File call(final String name) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().getFileStreamPath(this, name);
                } else {
                    return getOriginal().super_getFileStreamPath(name);
                }
            }
        };
        return superCall.call(name);
    }

    public File getFilesDir() {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_getFilesDir();
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun0<File> superCall = new CallFun0<File>("getFilesDir()") {

            @Override
            public File call() {
                if (iterator.hasPrevious()) {
                    return iterator.previous().getFilesDir(this);
                } else {
                    return getOriginal().super_getFilesDir();
                }
            }
        };
        return superCall.call();
    }

    public android.app.FragmentManager getFragmentManager() {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_getFragmentManager();
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun0<android.app.FragmentManager> superCall
                = new CallFun0<android.app.FragmentManager>("getFragmentManager()") {

            @Override
            public android.app.FragmentManager call() {
                if (iterator.hasPrevious()) {
                    return iterator.previous().getFragmentManager(this);
                } else {
                    return getOriginal().super_getFragmentManager();
                }
            }
        };
        return superCall.call();
    }

    public Intent getIntent() {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_getIntent();
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun0<Intent> superCall = new CallFun0<Intent>("getIntent()") {

            @Override
            public Intent call() {
                if (iterator.hasPrevious()) {
                    return iterator.previous().getIntent(this);
                } else {
                    return getOriginal().super_getIntent();
                }
            }
        };
        return superCall.call();
    }

    public Object getLastCompositeCustomNonConfigurationInstance() {
        final Object nci = getOriginal().getLastCustomNonConfigurationInstance();
        if (nci instanceof NonConfigurationInstanceWrapper) {
            final NonConfigurationInstanceWrapper all = (NonConfigurationInstanceWrapper) nci;
            return all.getSuperNonConfigurationInstance();
        }
        return null;
    }

    public Object getLastNonConfigurationInstance(final String key) {
        final Object nci = getOriginal().getLastCustomNonConfigurationInstance();
        if (nci instanceof NonConfigurationInstanceWrapper) {
            final NonConfigurationInstanceWrapper all = (NonConfigurationInstanceWrapper) nci;
            return all.getPluginNonConfigurationInstance(key);
        }
        return null;
    }

    public LayoutInflater getLayoutInflater() {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_getLayoutInflater();
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun0<LayoutInflater> superCall = new CallFun0<LayoutInflater>(
                "getLayoutInflater()") {

            @Override
            public LayoutInflater call() {
                if (iterator.hasPrevious()) {
                    return iterator.previous().getLayoutInflater(this);
                } else {
                    return getOriginal().super_getLayoutInflater();
                }
            }
        };
        return superCall.call();
    }

    public android.app.LoaderManager getLoaderManager() {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_getLoaderManager();
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun0<android.app.LoaderManager> superCall
                = new CallFun0<android.app.LoaderManager>("getLoaderManager()") {

            @Override
            public android.app.LoaderManager call() {
                if (iterator.hasPrevious()) {
                    return iterator.previous().getLoaderManager(this);
                } else {
                    return getOriginal().super_getLoaderManager();
                }
            }
        };
        return superCall.call();
    }

    public String getLocalClassName() {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_getLocalClassName();
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun0<String> superCall = new CallFun0<String>("getLocalClassName()") {

            @Override
            public String call() {
                if (iterator.hasPrevious()) {
                    return iterator.previous().getLocalClassName(this);
                } else {
                    return getOriginal().super_getLocalClassName();
                }
            }
        };
        return superCall.call();
    }

    public Looper getMainLooper() {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_getMainLooper();
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun0<Looper> superCall = new CallFun0<Looper>("getMainLooper()") {

            @Override
            public Looper call() {
                if (iterator.hasPrevious()) {
                    return iterator.previous().getMainLooper(this);
                } else {
                    return getOriginal().super_getMainLooper();
                }
            }
        };
        return superCall.call();
    }

    public MenuInflater getMenuInflater() {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_getMenuInflater();
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun0<MenuInflater> superCall = new CallFun0<MenuInflater>("getMenuInflater()") {

            @Override
            public MenuInflater call() {
                if (iterator.hasPrevious()) {
                    return iterator.previous().getMenuInflater(this);
                } else {
                    return getOriginal().super_getMenuInflater();
                }
            }
        };
        return superCall.call();
    }

    public File getNoBackupFilesDir() {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_getNoBackupFilesDir();
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun0<File> superCall = new CallFun0<File>("getNoBackupFilesDir()") {

            @Override
            public File call() {
                if (iterator.hasPrevious()) {
                    return iterator.previous().getNoBackupFilesDir(this);
                } else {
                    return getOriginal().super_getNoBackupFilesDir();
                }
            }
        };
        return superCall.call();
    }

    public File getObbDir() {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_getObbDir();
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun0<File> superCall = new CallFun0<File>("getObbDir()") {

            @Override
            public File call() {
                if (iterator.hasPrevious()) {
                    return iterator.previous().getObbDir(this);
                } else {
                    return getOriginal().super_getObbDir();
                }
            }
        };
        return superCall.call();
    }

    public File[] getObbDirs() {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_getObbDirs();
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun0<File[]> superCall = new CallFun0<File[]>("getObbDirs()") {

            @Override
            public File[] call() {
                if (iterator.hasPrevious()) {
                    return iterator.previous().getObbDirs(this);
                } else {
                    return getOriginal().super_getObbDirs();
                }
            }
        };
        return superCall.call();
    }

    public String getPackageCodePath() {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_getPackageCodePath();
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun0<String> superCall = new CallFun0<String>("getPackageCodePath()") {

            @Override
            public String call() {
                if (iterator.hasPrevious()) {
                    return iterator.previous().getPackageCodePath(this);
                } else {
                    return getOriginal().super_getPackageCodePath();
                }
            }
        };
        return superCall.call();
    }

    public PackageManager getPackageManager() {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_getPackageManager();
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun0<PackageManager> superCall = new CallFun0<PackageManager>(
                "getPackageManager()") {

            @Override
            public PackageManager call() {
                if (iterator.hasPrevious()) {
                    return iterator.previous().getPackageManager(this);
                } else {
                    return getOriginal().super_getPackageManager();
                }
            }
        };
        return superCall.call();
    }

    public String getPackageName() {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_getPackageName();
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun0<String> superCall = new CallFun0<String>("getPackageName()") {

            @Override
            public String call() {
                if (iterator.hasPrevious()) {
                    return iterator.previous().getPackageName(this);
                } else {
                    return getOriginal().super_getPackageName();
                }
            }
        };
        return superCall.call();
    }

    public String getPackageResourcePath() {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_getPackageResourcePath();
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun0<String> superCall = new CallFun0<String>("getPackageResourcePath()") {

            @Override
            public String call() {
                if (iterator.hasPrevious()) {
                    return iterator.previous().getPackageResourcePath(this);
                } else {
                    return getOriginal().super_getPackageResourcePath();
                }
            }
        };
        return superCall.call();
    }

    public Intent getParentActivityIntent() {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_getParentActivityIntent();
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun0<Intent> superCall = new CallFun0<Intent>("getParentActivityIntent()") {

            @Override
            public Intent call() {
                if (iterator.hasPrevious()) {
                    return iterator.previous().getParentActivityIntent(this);
                } else {
                    return getOriginal().super_getParentActivityIntent();
                }
            }
        };
        return superCall.call();
    }

    public SharedPreferences getPreferences(final int mode) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_getPreferences(mode);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun1<SharedPreferences, Integer> superCall
                = new CallFun1<SharedPreferences, Integer>("getPreferences(Integer)") {

            @Override
            public SharedPreferences call(final Integer mode) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().getPreferences(this, mode);
                } else {
                    return getOriginal().super_getPreferences(mode);
                }
            }
        };
        return superCall.call(mode);
    }

    public Uri getReferrer() {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_getReferrer();
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun0<Uri> superCall = new CallFun0<Uri>("getReferrer()") {

            @Override
            public Uri call() {
                if (iterator.hasPrevious()) {
                    return iterator.previous().getReferrer(this);
                } else {
                    return getOriginal().super_getReferrer();
                }
            }
        };
        return superCall.call();
    }

    public int getRequestedOrientation() {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_getRequestedOrientation();
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun0<Integer> superCall = new CallFun0<Integer>("getRequestedOrientation()") {

            @Override
            public Integer call() {
                if (iterator.hasPrevious()) {
                    return iterator.previous().getRequestedOrientation(this);
                } else {
                    return getOriginal().super_getRequestedOrientation();
                }
            }
        };
        return superCall.call();
    }

    public Resources getResources() {
        if (!mIsGetResourcesOverridden) {
            return getOriginal().super_getResources();
        }

        final ListIterator<ActivityPlugin> iterator;
        if (mGetResourcesCallCount < CALL_COUNT_OPTIMIZATION_THRESHOLD) {
            mGetResourcesCallCount++;
            iterator = mPlugins.listIterator(mPlugins.size());
        } else {
            List<ActivityPlugin> implementingPlugins = mMethodImplementingPlugins
                    .get("getResources()");
            if (implementingPlugins == null) {
                implementingPlugins = getImplementingPlugins("getResources");
                mMethodImplementingPlugins.put("getResources()", implementingPlugins);
                mIsGetResourcesOverridden = implementingPlugins.size() > 0;
            }

            iterator = implementingPlugins.listIterator(implementingPlugins.size());
        }

        final CallFun0<Resources> superCall = new CallFun0<Resources>("getResources()") {

            @Override
            public Resources call() {
                if (iterator.hasPrevious()) {
                    return iterator.previous().getResources(this);
                } else {
                    return getOriginal().super_getResources();
                }
            }
        };
        return superCall.call();
    }

    public SharedPreferences getSharedPreferences(final String name, final int mode) {

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun2<SharedPreferences, String, Integer> superCall
                = new CallFun2<SharedPreferences, String, Integer>(
                "getSharedPreferences(String, Integer)") {

            @Override
            public SharedPreferences call(final String name, final Integer mode) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().getSharedPreferences(this, name, mode);
                } else {
                    return getOriginal().super_getSharedPreferences(name, mode);
                }
            }
        };
        return superCall.call(name, mode);
    }

    public ActionBar getSupportActionBar() {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_getSupportActionBar();
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun0<ActionBar> superCall = new CallFun0<ActionBar>("getSupportActionBar()") {

            @Override
            public ActionBar call() {
                if (iterator.hasPrevious()) {
                    return iterator.previous().getSupportActionBar(this);
                } else {
                    return getOriginal().super_getSupportActionBar();
                }
            }
        };
        return superCall.call();
    }

    public FragmentManager getSupportFragmentManager() {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_getSupportFragmentManager();
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun0<FragmentManager> superCall = new CallFun0<FragmentManager>(
                "getSupportFragmentManager()") {

            @Override
            public FragmentManager call() {
                if (iterator.hasPrevious()) {
                    return iterator.previous().getSupportFragmentManager(this);
                } else {
                    return getOriginal().super_getSupportFragmentManager();
                }
            }
        };
        return superCall.call();
    }

    public LoaderManager getSupportLoaderManager() {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_getSupportLoaderManager();
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun0<LoaderManager> superCall = new CallFun0<LoaderManager>(
                "getSupportLoaderManager()") {

            @Override
            public LoaderManager call() {
                if (iterator.hasPrevious()) {
                    return iterator.previous().getSupportLoaderManager(this);
                } else {
                    return getOriginal().super_getSupportLoaderManager();
                }
            }
        };
        return superCall.call();
    }

    public Intent getSupportParentActivityIntent() {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_getSupportParentActivityIntent();
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun0<Intent> superCall = new CallFun0<Intent>(
                "getSupportParentActivityIntent()") {

            @Override
            public Intent call() {
                if (iterator.hasPrevious()) {
                    return iterator.previous().getSupportParentActivityIntent(this);
                } else {
                    return getOriginal().super_getSupportParentActivityIntent();
                }
            }
        };
        return superCall.call();
    }

    public Object getSystemService(final String name) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_getSystemService(name);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun1<Object, String> superCall = new CallFun1<Object, String>(
                "getSystemService(String)") {

            @Override
            public Object call(final String name) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().getSystemService(this, name);
                } else {
                    return getOriginal().super_getSystemService(name);
                }
            }
        };
        return superCall.call(name);
    }

    public String getSystemServiceName(final Class<?> serviceClass) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_getSystemServiceName(serviceClass);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun1<String, Class<?>> superCall = new CallFun1<String, Class<?>>(
                "getSystemServiceName(Class<?>)") {

            @Override
            public String call(final Class<?> serviceClass) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().getSystemServiceName(this, serviceClass);
                } else {
                    return getOriginal().super_getSystemServiceName(serviceClass);
                }
            }
        };
        return superCall.call(serviceClass);
    }

    public int getTaskId() {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_getTaskId();
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun0<Integer> superCall = new CallFun0<Integer>("getTaskId()") {

            @Override
            public Integer call() {
                if (iterator.hasPrevious()) {
                    return iterator.previous().getTaskId(this);
                } else {
                    return getOriginal().super_getTaskId();
                }
            }
        };
        return superCall.call();
    }

    public Resources.Theme getTheme() {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_getTheme();
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun0<Resources.Theme> superCall = new CallFun0<Resources.Theme>("getTheme()") {

            @Override
            public Resources.Theme call() {
                if (iterator.hasPrevious()) {
                    return iterator.previous().getTheme(this);
                } else {
                    return getOriginal().super_getTheme();
                }
            }
        };
        return superCall.call();
    }

    public VoiceInteractor getVoiceInteractor() {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_getVoiceInteractor();
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun0<VoiceInteractor> superCall = new CallFun0<VoiceInteractor>(
                "getVoiceInteractor()") {

            @Override
            public VoiceInteractor call() {
                if (iterator.hasPrevious()) {
                    return iterator.previous().getVoiceInteractor(this);
                } else {
                    return getOriginal().super_getVoiceInteractor();
                }
            }
        };
        return superCall.call();
    }

    public Drawable getWallpaper() {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_getWallpaper();
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun0<Drawable> superCall = new CallFun0<Drawable>("getWallpaper()") {

            @Override
            public Drawable call() {
                if (iterator.hasPrevious()) {
                    return iterator.previous().getWallpaper(this);
                } else {
                    return getOriginal().super_getWallpaper();
                }
            }
        };
        return superCall.call();
    }

    public int getWallpaperDesiredMinimumHeight() {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_getWallpaperDesiredMinimumHeight();
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun0<Integer> superCall = new CallFun0<Integer>(
                "getWallpaperDesiredMinimumHeight()") {

            @Override
            public Integer call() {
                if (iterator.hasPrevious()) {
                    return iterator.previous().getWallpaperDesiredMinimumHeight(this);
                } else {
                    return getOriginal().super_getWallpaperDesiredMinimumHeight();
                }
            }
        };
        return superCall.call();
    }

    public int getWallpaperDesiredMinimumWidth() {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_getWallpaperDesiredMinimumWidth();
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun0<Integer> superCall = new CallFun0<Integer>(
                "getWallpaperDesiredMinimumWidth()") {

            @Override
            public Integer call() {
                if (iterator.hasPrevious()) {
                    return iterator.previous().getWallpaperDesiredMinimumWidth(this);
                } else {
                    return getOriginal().super_getWallpaperDesiredMinimumWidth();
                }
            }
        };
        return superCall.call();
    }

    public Window getWindow() {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_getWindow();
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun0<Window> superCall = new CallFun0<Window>("getWindow()") {

            @Override
            public Window call() {
                if (iterator.hasPrevious()) {
                    return iterator.previous().getWindow(this);
                } else {
                    return getOriginal().super_getWindow();
                }
            }
        };
        return superCall.call();
    }

    public WindowManager getWindowManager() {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_getWindowManager();
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun0<WindowManager> superCall = new CallFun0<WindowManager>(
                "getWindowManager()") {

            @Override
            public WindowManager call() {
                if (iterator.hasPrevious()) {
                    return iterator.previous().getWindowManager(this);
                } else {
                    return getOriginal().super_getWindowManager();
                }
            }
        };
        return superCall.call();
    }

    public void grantUriPermission(final String toPackage, final Uri uri, final int modeFlags) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_grantUriPermission(toPackage, uri, modeFlags);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid3<String, Uri, Integer> superCall = new CallVoid3<String, Uri, Integer>(
                "grantUriPermission(String, Uri, Integer)") {

            @Override
            public void call(final String toPackage, final Uri uri, final Integer modeFlags) {
                if (iterator.hasPrevious()) {
                    iterator.previous().grantUriPermission(this, toPackage, uri, modeFlags);
                } else {
                    getOriginal().super_grantUriPermission(toPackage, uri, modeFlags);
                }
            }
        };
        superCall.call(toPackage, uri, modeFlags);
    }

    public boolean hasWindowFocus() {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_hasWindowFocus();
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun0<Boolean> superCall = new CallFun0<Boolean>("hasWindowFocus()") {

            @Override
            public Boolean call() {
                if (iterator.hasPrevious()) {
                    return iterator.previous().hasWindowFocus(this);
                } else {
                    return getOriginal().super_hasWindowFocus();
                }
            }
        };
        return superCall.call();
    }

    public void invalidateOptionsMenu() {
        if (mPlugins.isEmpty()) {
            getOriginal().super_invalidateOptionsMenu();
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid0 superCall = new CallVoid0("invalidateOptionsMenu()") {

            @Override
            public void call() {
                if (iterator.hasPrevious()) {
                    iterator.previous().invalidateOptionsMenu(this);
                } else {
                    getOriginal().super_invalidateOptionsMenu();
                }
            }
        };
        superCall.call();
    }

    public boolean isChangingConfigurations() {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_isChangingConfigurations();
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun0<Boolean> superCall = new CallFun0<Boolean>("isChangingConfigurations()") {

            @Override
            public Boolean call() {
                if (iterator.hasPrevious()) {
                    return iterator.previous().isChangingConfigurations(this);
                } else {
                    return getOriginal().super_isChangingConfigurations();
                }
            }
        };
        return superCall.call();
    }

    public boolean isDestroyed() {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_isDestroyed();
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun0<Boolean> superCall = new CallFun0<Boolean>("isDestroyed()") {

            @Override
            public Boolean call() {
                if (iterator.hasPrevious()) {
                    return iterator.previous().isDestroyed(this);
                } else {
                    return getOriginal().super_isDestroyed();
                }
            }
        };
        return superCall.call();
    }

    public boolean isFinishing() {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_isFinishing();
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun0<Boolean> superCall = new CallFun0<Boolean>("isFinishing()") {

            @Override
            public Boolean call() {
                if (iterator.hasPrevious()) {
                    return iterator.previous().isFinishing(this);
                } else {
                    return getOriginal().super_isFinishing();
                }
            }
        };
        return superCall.call();
    }

    public boolean isImmersive() {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_isImmersive();
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun0<Boolean> superCall = new CallFun0<Boolean>("isImmersive()") {

            @Override
            public Boolean call() {
                if (iterator.hasPrevious()) {
                    return iterator.previous().isImmersive(this);
                } else {
                    return getOriginal().super_isImmersive();
                }
            }
        };
        return superCall.call();
    }

    public boolean isRestricted() {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_isRestricted();
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun0<Boolean> superCall = new CallFun0<Boolean>("isRestricted()") {

            @Override
            public Boolean call() {
                if (iterator.hasPrevious()) {
                    return iterator.previous().isRestricted(this);
                } else {
                    return getOriginal().super_isRestricted();
                }
            }
        };
        return superCall.call();
    }

    public boolean isTaskRoot() {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_isTaskRoot();
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun0<Boolean> superCall = new CallFun0<Boolean>("isTaskRoot()") {

            @Override
            public Boolean call() {
                if (iterator.hasPrevious()) {
                    return iterator.previous().isTaskRoot(this);
                } else {
                    return getOriginal().super_isTaskRoot();
                }
            }
        };
        return superCall.call();
    }

    public boolean isVoiceInteraction() {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_isVoiceInteraction();
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun0<Boolean> superCall = new CallFun0<Boolean>("isVoiceInteraction()") {

            @Override
            public Boolean call() {
                if (iterator.hasPrevious()) {
                    return iterator.previous().isVoiceInteraction(this);
                } else {
                    return getOriginal().super_isVoiceInteraction();
                }
            }
        };
        return superCall.call();
    }

    public boolean isVoiceInteractionRoot() {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_isVoiceInteractionRoot();
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun0<Boolean> superCall = new CallFun0<Boolean>("isVoiceInteractionRoot()") {

            @Override
            public Boolean call() {
                if (iterator.hasPrevious()) {
                    return iterator.previous().isVoiceInteractionRoot(this);
                } else {
                    return getOriginal().super_isVoiceInteractionRoot();
                }
            }
        };
        return superCall.call();
    }

    public boolean moveTaskToBack(final boolean nonRoot) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_moveTaskToBack(nonRoot);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun1<Boolean, Boolean> superCall = new CallFun1<Boolean, Boolean>(
                "moveTaskToBack(Boolean)") {

            @Override
            public Boolean call(final Boolean nonRoot) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().moveTaskToBack(this, nonRoot);
                } else {
                    return getOriginal().super_moveTaskToBack(nonRoot);
                }
            }
        };
        return superCall.call(nonRoot);
    }

    public boolean navigateUpTo(final Intent upIntent) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_navigateUpTo(upIntent);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun1<Boolean, Intent> superCall = new CallFun1<Boolean, Intent>(
                "navigateUpTo(Intent)") {

            @Override
            public Boolean call(final Intent upIntent) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().navigateUpTo(this, upIntent);
                } else {
                    return getOriginal().super_navigateUpTo(upIntent);
                }
            }
        };
        return superCall.call(upIntent);
    }

    public boolean navigateUpToFromChild(final Activity child, final Intent upIntent) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_navigateUpToFromChild(child, upIntent);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun2<Boolean, Activity, Intent> superCall
                = new CallFun2<Boolean, Activity, Intent>(
                "navigateUpToFromChild(Activity, Intent)") {

            @Override
            public Boolean call(final Activity child, final Intent upIntent) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().navigateUpToFromChild(this, child, upIntent);
                } else {
                    return getOriginal().super_navigateUpToFromChild(child, upIntent);
                }
            }
        };
        return superCall.call(child, upIntent);
    }

    public void onActionModeFinished(final android.view.ActionMode mode) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_onActionModeFinished(mode);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid1<android.view.ActionMode> superCall = new CallVoid1<android.view.ActionMode>(
                "onActionModeFinished(android.view.ActionMode)") {

            @Override
            public void call(final android.view.ActionMode mode) {
                if (iterator.hasPrevious()) {
                    iterator.previous().onActionModeFinished(this, mode);
                } else {
                    getOriginal().super_onActionModeFinished(mode);
                }
            }
        };
        superCall.call(mode);
    }

    public void onActionModeStarted(final android.view.ActionMode mode) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_onActionModeStarted(mode);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid1<android.view.ActionMode> superCall = new CallVoid1<android.view.ActionMode>(
                "onActionModeStarted(android.view.ActionMode)") {

            @Override
            public void call(final android.view.ActionMode mode) {
                if (iterator.hasPrevious()) {
                    iterator.previous().onActionModeStarted(this, mode);
                } else {
                    getOriginal().super_onActionModeStarted(mode);
                }
            }
        };
        superCall.call(mode);
    }

    public void onActivityReenter(final int resultCode, final Intent data) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_onActivityReenter(resultCode, data);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid2<Integer, Intent> superCall = new CallVoid2<Integer, Intent>(
                "onActivityReenter(Integer, Intent)") {

            @Override
            public void call(final Integer resultCode, final Intent data) {
                if (iterator.hasPrevious()) {
                    iterator.previous().onActivityReenter(this, resultCode, data);
                } else {
                    getOriginal().super_onActivityReenter(resultCode, data);
                }
            }
        };
        superCall.call(resultCode, data);
    }

    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_onActivityResult(requestCode, resultCode, data);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid3<Integer, Integer, Intent> superCall
                = new CallVoid3<Integer, Integer, Intent>(
                "onActivityResult(Integer, Integer, Intent)") {

            @Override
            public void call(final Integer requestCode, final Integer resultCode,
                    final Intent data) {
                if (iterator.hasPrevious()) {
                    iterator.previous().onActivityResult(this, requestCode, resultCode, data);
                } else {
                    getOriginal().super_onActivityResult(requestCode, resultCode, data);
                }
            }
        };
        superCall.call(requestCode, resultCode, data);
    }

    public void onApplyThemeResource(final Resources.Theme theme, final int resid,
            final boolean first) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_onApplyThemeResource(theme, resid, first);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid3<Resources.Theme, Integer, Boolean> superCall
                = new CallVoid3<Resources.Theme, Integer, Boolean>(
                "onApplyThemeResource(Resources.Theme, Integer, Boolean)") {

            @Override
            public void call(final Resources.Theme theme, final Integer resid,
                    final Boolean first) {
                if (iterator.hasPrevious()) {
                    iterator.previous().onApplyThemeResource(this, theme, resid, first);
                } else {
                    getOriginal().super_onApplyThemeResource(theme, resid, first);
                }
            }
        };
        superCall.call(theme, resid, first);
    }

    public void onAttachFragment(final Fragment fragment) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_onAttachFragment(fragment);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid1<Fragment> superCall = new CallVoid1<Fragment>(
                "onAttachFragment(Fragment)") {

            @Override
            public void call(final Fragment fragment) {
                if (iterator.hasPrevious()) {
                    iterator.previous().onAttachFragment(this, fragment);
                } else {
                    getOriginal().super_onAttachFragment(fragment);
                }
            }
        };
        superCall.call(fragment);
    }

    public void onAttachFragment(final android.app.Fragment fragment) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_onAttachFragment(fragment);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid1<android.app.Fragment> superCall = new CallVoid1<android.app.Fragment>(
                "onAttachFragment(android.app.Fragment)") {

            @Override
            public void call(final android.app.Fragment fragment) {
                if (iterator.hasPrevious()) {
                    iterator.previous().onAttachFragment(this, fragment);
                } else {
                    getOriginal().super_onAttachFragment(fragment);
                }
            }
        };
        superCall.call(fragment);
    }

    public void onAttachedToWindow() {
        if (mPlugins.isEmpty()) {
            getOriginal().super_onAttachedToWindow();
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid0 superCall = new CallVoid0("onAttachedToWindow()") {

            @Override
            public void call() {
                if (iterator.hasPrevious()) {
                    iterator.previous().onAttachedToWindow(this);
                } else {
                    getOriginal().super_onAttachedToWindow();
                }
            }
        };
        superCall.call();
    }

    public void onBackPressed() {
        if (mPlugins.isEmpty()) {
            getOriginal().super_onBackPressed();
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid0 superCall = new CallVoid0("onBackPressed()") {

            @Override
            public void call() {
                if (iterator.hasPrevious()) {
                    iterator.previous().onBackPressed(this);
                } else {
                    getOriginal().super_onBackPressed();
                }
            }
        };
        superCall.call();
    }

    public void onChildTitleChanged(final Activity childActivity, final CharSequence title) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_onChildTitleChanged(childActivity, title);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid2<Activity, CharSequence> superCall = new CallVoid2<Activity, CharSequence>(
                "onChildTitleChanged(Activity, CharSequence)") {

            @Override
            public void call(final Activity childActivity, final CharSequence title) {
                if (iterator.hasPrevious()) {
                    iterator.previous().onChildTitleChanged(this, childActivity, title);
                } else {
                    getOriginal().super_onChildTitleChanged(childActivity, title);
                }
            }
        };
        superCall.call(childActivity, title);
    }

    public void onConfigurationChanged(final Configuration newConfig) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_onConfigurationChanged(newConfig);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid1<Configuration> superCall = new CallVoid1<Configuration>(
                "onConfigurationChanged(Configuration)") {

            @Override
            public void call(final Configuration newConfig) {
                if (iterator.hasPrevious()) {
                    iterator.previous().onConfigurationChanged(this, newConfig);
                } else {
                    getOriginal().super_onConfigurationChanged(newConfig);
                }
            }
        };
        superCall.call(newConfig);
    }

    public void onContentChanged() {
        if (mPlugins.isEmpty()) {
            getOriginal().super_onContentChanged();
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid0 superCall = new CallVoid0("onContentChanged()") {

            @Override
            public void call() {
                if (iterator.hasPrevious()) {
                    iterator.previous().onContentChanged(this);
                } else {
                    getOriginal().super_onContentChanged();
                }
            }
        };
        superCall.call();
    }

    public boolean onContextItemSelected(final MenuItem item) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_onContextItemSelected(item);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun1<Boolean, MenuItem> superCall = new CallFun1<Boolean, MenuItem>(
                "onContextItemSelected(MenuItem)") {

            @Override
            public Boolean call(final MenuItem item) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().onContextItemSelected(this, item);
                } else {
                    return getOriginal().super_onContextItemSelected(item);
                }
            }
        };
        return superCall.call(item);
    }

    public void onContextMenuClosed(final Menu menu) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_onContextMenuClosed(menu);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid1<Menu> superCall = new CallVoid1<Menu>("onContextMenuClosed(Menu)") {

            @Override
            public void call(final Menu menu) {
                if (iterator.hasPrevious()) {
                    iterator.previous().onContextMenuClosed(this, menu);
                } else {
                    getOriginal().super_onContextMenuClosed(menu);
                }
            }
        };
        superCall.call(menu);
    }

    public void onCreate(final Bundle savedInstanceState, final PersistableBundle persistentState) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_onCreate(savedInstanceState, persistentState);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid2<Bundle, PersistableBundle> superCall
                = new CallVoid2<Bundle, PersistableBundle>("onCreate(Bundle, PersistableBundle)") {

            @Override
            public void call(final Bundle savedInstanceState,
                    final PersistableBundle persistentState) {
                if (iterator.hasPrevious()) {
                    iterator.previous().onCreate(this, savedInstanceState, persistentState);
                } else {
                    getOriginal().super_onCreate(savedInstanceState, persistentState);
                }
            }
        };
        superCall.call(savedInstanceState, persistentState);
    }

    public void onCreate(@Nullable final Bundle savedInstanceState) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_onCreate(savedInstanceState);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid1<Bundle> superCall = new CallVoid1<Bundle>("onCreate(Bundle)") {

            @Override
            public void call(final Bundle savedInstanceState) {
                if (iterator.hasPrevious()) {
                    iterator.previous().onCreate(this, savedInstanceState);
                } else {
                    getOriginal().super_onCreate(savedInstanceState);
                }
            }
        };
        superCall.call(savedInstanceState);
    }

    public void onCreateContextMenu(final ContextMenu menu, final View v,
            final ContextMenu.ContextMenuInfo menuInfo) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_onCreateContextMenu(menu, v, menuInfo);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid3<ContextMenu, View, ContextMenu.ContextMenuInfo> superCall
                = new CallVoid3<ContextMenu, View, ContextMenu.ContextMenuInfo>(
                "onCreateContextMenu(ContextMenu, View, ContextMenu.ContextMenuInfo)") {

            @Override
            public void call(final ContextMenu menu, final View v,
                    final ContextMenu.ContextMenuInfo menuInfo) {
                if (iterator.hasPrevious()) {
                    iterator.previous().onCreateContextMenu(this, menu, v, menuInfo);
                } else {
                    getOriginal().super_onCreateContextMenu(menu, v, menuInfo);
                }
            }
        };
        superCall.call(menu, v, menuInfo);
    }

    public CharSequence onCreateDescription() {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_onCreateDescription();
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun0<CharSequence> superCall = new CallFun0<CharSequence>(
                "onCreateDescription()") {

            @Override
            public CharSequence call() {
                if (iterator.hasPrevious()) {
                    return iterator.previous().onCreateDescription(this);
                } else {
                    return getOriginal().super_onCreateDescription();
                }
            }
        };
        return superCall.call();
    }

    public Dialog onCreateDialog(final int id) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_onCreateDialog(id);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun1<Dialog, Integer> superCall = new CallFun1<Dialog, Integer>(
                "onCreateDialog(Integer)") {

            @Override
            public Dialog call(final Integer id) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().onCreateDialog(this, id);
                } else {
                    return getOriginal().super_onCreateDialog(id);
                }
            }
        };
        return superCall.call(id);
    }

    public Dialog onCreateDialog(final int id, final Bundle args) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_onCreateDialog(id, args);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun2<Dialog, Integer, Bundle> superCall = new CallFun2<Dialog, Integer, Bundle>(
                "onCreateDialog(Integer, Bundle)") {

            @Override
            public Dialog call(final Integer id, final Bundle args) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().onCreateDialog(this, id, args);
                } else {
                    return getOriginal().super_onCreateDialog(id, args);
                }
            }
        };
        return superCall.call(id, args);
    }

    public void onCreateNavigateUpTaskStack(final TaskStackBuilder builder) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_onCreateNavigateUpTaskStack(builder);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid1<TaskStackBuilder> superCall = new CallVoid1<TaskStackBuilder>(
                "onCreateNavigateUpTaskStack(TaskStackBuilder)") {

            @Override
            public void call(final TaskStackBuilder builder) {
                if (iterator.hasPrevious()) {
                    iterator.previous().onCreateNavigateUpTaskStack(this, builder);
                } else {
                    getOriginal().super_onCreateNavigateUpTaskStack(builder);
                }
            }
        };
        superCall.call(builder);
    }

    public boolean onCreateOptionsMenu(final Menu menu) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_onCreateOptionsMenu(menu);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun1<Boolean, Menu> superCall = new CallFun1<Boolean, Menu>(
                "onCreateOptionsMenu(Menu)") {

            @Override
            public Boolean call(final Menu menu) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().onCreateOptionsMenu(this, menu);
                } else {
                    return getOriginal().super_onCreateOptionsMenu(menu);
                }
            }
        };
        return superCall.call(menu);
    }

    public boolean onCreatePanelMenu(final int featureId, final Menu menu) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_onCreatePanelMenu(featureId, menu);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun2<Boolean, Integer, Menu> superCall = new CallFun2<Boolean, Integer, Menu>(
                "onCreatePanelMenu(Integer, Menu)") {

            @Override
            public Boolean call(final Integer featureId, final Menu menu) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().onCreatePanelMenu(this, featureId, menu);
                } else {
                    return getOriginal().super_onCreatePanelMenu(featureId, menu);
                }
            }
        };
        return superCall.call(featureId, menu);
    }

    public View onCreatePanelView(final int featureId) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_onCreatePanelView(featureId);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun1<View, Integer> superCall = new CallFun1<View, Integer>(
                "onCreatePanelView(Integer)") {

            @Override
            public View call(final Integer featureId) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().onCreatePanelView(this, featureId);
                } else {
                    return getOriginal().super_onCreatePanelView(featureId);
                }
            }
        };
        return superCall.call(featureId);
    }

    public void onCreateSupportNavigateUpTaskStack(
            @NonNull final android.support.v4.app.TaskStackBuilder builder) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_onCreateSupportNavigateUpTaskStack(builder);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid1<android.support.v4.app.TaskStackBuilder> superCall
                = new CallVoid1<android.support.v4.app.TaskStackBuilder>(
                "onCreateSupportNavigateUpTaskStack(android.support.v4.app.TaskStackBuilder)") {

            @Override
            public void call(final android.support.v4.app.TaskStackBuilder builder) {
                if (iterator.hasPrevious()) {
                    iterator.previous().onCreateSupportNavigateUpTaskStack(this, builder);
                } else {
                    getOriginal().super_onCreateSupportNavigateUpTaskStack(builder);
                }
            }
        };
        superCall.call(builder);
    }

    public boolean onCreateThumbnail(final Bitmap outBitmap, final Canvas canvas) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_onCreateThumbnail(outBitmap, canvas);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun2<Boolean, Bitmap, Canvas> superCall = new CallFun2<Boolean, Bitmap, Canvas>(
                "onCreateThumbnail(Bitmap, Canvas)") {

            @Override
            public Boolean call(final Bitmap outBitmap, final Canvas canvas) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().onCreateThumbnail(this, outBitmap, canvas);
                } else {
                    return getOriginal().super_onCreateThumbnail(outBitmap, canvas);
                }
            }
        };
        return superCall.call(outBitmap, canvas);
    }

    public View onCreateView(final View parent, final String name, final Context context,
            final AttributeSet attrs) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_onCreateView(parent, name, context, attrs);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun4<View, View, String, Context, AttributeSet> superCall
                = new CallFun4<View, View, String, Context, AttributeSet>(
                "onCreateView(View, String, Context, AttributeSet)") {

            @Override
            public View call(final View parent, final String name, final Context context,
                    final AttributeSet attrs) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().onCreateView(this, parent, name, context, attrs);
                } else {
                    return getOriginal().super_onCreateView(parent, name, context, attrs);
                }
            }
        };
        return superCall.call(parent, name, context, attrs);
    }

    public View onCreateView(final String name, final Context context, final AttributeSet attrs) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_onCreateView(name, context, attrs);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun3<View, String, Context, AttributeSet> superCall
                = new CallFun3<View, String, Context, AttributeSet>(
                "onCreateView(String, Context, AttributeSet)") {

            @Override
            public View call(final String name, final Context context, final AttributeSet attrs) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().onCreateView(this, name, context, attrs);
                } else {
                    return getOriginal().super_onCreateView(name, context, attrs);
                }
            }
        };
        return superCall.call(name, context, attrs);
    }

    public void onDestroy() {
        if (mPlugins.isEmpty()) {
            getOriginal().super_onDestroy();
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid0 superCall = new CallVoid0("onDestroy()") {

            @Override
            public void call() {
                if (iterator.hasPrevious()) {
                    iterator.previous().onDestroy(this);
                } else {
                    getOriginal().super_onDestroy();
                }
            }
        };
        superCall.call();
    }

    public void onDetachedFromWindow() {
        if (mPlugins.isEmpty()) {
            getOriginal().super_onDetachedFromWindow();
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid0 superCall = new CallVoid0("onDetachedFromWindow()") {

            @Override
            public void call() {
                if (iterator.hasPrevious()) {
                    iterator.previous().onDetachedFromWindow(this);
                } else {
                    getOriginal().super_onDetachedFromWindow();
                }
            }
        };
        superCall.call();
    }

    public void onEnterAnimationComplete() {
        if (mPlugins.isEmpty()) {
            getOriginal().super_onEnterAnimationComplete();
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid0 superCall = new CallVoid0("onEnterAnimationComplete()") {

            @Override
            public void call() {
                if (iterator.hasPrevious()) {
                    iterator.previous().onEnterAnimationComplete(this);
                } else {
                    getOriginal().super_onEnterAnimationComplete();
                }
            }
        };
        superCall.call();
    }

    public boolean onGenericMotionEvent(final MotionEvent event) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_onGenericMotionEvent(event);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun1<Boolean, MotionEvent> superCall = new CallFun1<Boolean, MotionEvent>(
                "onGenericMotionEvent(MotionEvent)") {

            @Override
            public Boolean call(final MotionEvent event) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().onGenericMotionEvent(this, event);
                } else {
                    return getOriginal().super_onGenericMotionEvent(event);
                }
            }
        };
        return superCall.call(event);
    }

    public boolean onKeyDown(final int keyCode, final KeyEvent event) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_onKeyDown(keyCode, event);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun2<Boolean, Integer, KeyEvent> superCall
                = new CallFun2<Boolean, Integer, KeyEvent>("onKeyDown(Integer, KeyEvent)") {

            @Override
            public Boolean call(final Integer keyCode, final KeyEvent event) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().onKeyDown(this, keyCode, event);
                } else {
                    return getOriginal().super_onKeyDown(keyCode, event);
                }
            }
        };
        return superCall.call(keyCode, event);
    }

    public boolean onKeyLongPress(final int keyCode, final KeyEvent event) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_onKeyLongPress(keyCode, event);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun2<Boolean, Integer, KeyEvent> superCall
                = new CallFun2<Boolean, Integer, KeyEvent>("onKeyLongPress(Integer, KeyEvent)") {

            @Override
            public Boolean call(final Integer keyCode, final KeyEvent event) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().onKeyLongPress(this, keyCode, event);
                } else {
                    return getOriginal().super_onKeyLongPress(keyCode, event);
                }
            }
        };
        return superCall.call(keyCode, event);
    }

    public boolean onKeyMultiple(final int keyCode, final int repeatCount, final KeyEvent event) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_onKeyMultiple(keyCode, repeatCount, event);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun3<Boolean, Integer, Integer, KeyEvent> superCall
                = new CallFun3<Boolean, Integer, Integer, KeyEvent>(
                "onKeyMultiple(Integer, Integer, KeyEvent)") {

            @Override
            public Boolean call(final Integer keyCode, final Integer repeatCount,
                    final KeyEvent event) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().onKeyMultiple(this, keyCode, repeatCount, event);
                } else {
                    return getOriginal().super_onKeyMultiple(keyCode, repeatCount, event);
                }
            }
        };
        return superCall.call(keyCode, repeatCount, event);
    }

    public boolean onKeyShortcut(final int keyCode, final KeyEvent event) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_onKeyShortcut(keyCode, event);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun2<Boolean, Integer, KeyEvent> superCall
                = new CallFun2<Boolean, Integer, KeyEvent>("onKeyShortcut(Integer, KeyEvent)") {

            @Override
            public Boolean call(final Integer keyCode, final KeyEvent event) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().onKeyShortcut(this, keyCode, event);
                } else {
                    return getOriginal().super_onKeyShortcut(keyCode, event);
                }
            }
        };
        return superCall.call(keyCode, event);
    }

    public boolean onKeyUp(final int keyCode, final KeyEvent event) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_onKeyUp(keyCode, event);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun2<Boolean, Integer, KeyEvent> superCall
                = new CallFun2<Boolean, Integer, KeyEvent>("onKeyUp(Integer, KeyEvent)") {

            @Override
            public Boolean call(final Integer keyCode, final KeyEvent event) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().onKeyUp(this, keyCode, event);
                } else {
                    return getOriginal().super_onKeyUp(keyCode, event);
                }
            }
        };
        return superCall.call(keyCode, event);
    }

    public void onLowMemory() {
        if (mPlugins.isEmpty()) {
            getOriginal().super_onLowMemory();
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid0 superCall = new CallVoid0("onLowMemory()") {

            @Override
            public void call() {
                if (iterator.hasPrevious()) {
                    iterator.previous().onLowMemory(this);
                } else {
                    getOriginal().super_onLowMemory();
                }
            }
        };
        superCall.call();
    }

    public boolean onMenuOpened(final int featureId, final Menu menu) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_onMenuOpened(featureId, menu);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun2<Boolean, Integer, Menu> superCall = new CallFun2<Boolean, Integer, Menu>(
                "onMenuOpened(Integer, Menu)") {

            @Override
            public Boolean call(final Integer featureId, final Menu menu) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().onMenuOpened(this, featureId, menu);
                } else {
                    return getOriginal().super_onMenuOpened(featureId, menu);
                }
            }
        };
        return superCall.call(featureId, menu);
    }

    public boolean onNavigateUp() {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_onNavigateUp();
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun0<Boolean> superCall = new CallFun0<Boolean>("onNavigateUp()") {

            @Override
            public Boolean call() {
                if (iterator.hasPrevious()) {
                    return iterator.previous().onNavigateUp(this);
                } else {
                    return getOriginal().super_onNavigateUp();
                }
            }
        };
        return superCall.call();
    }

    public boolean onNavigateUpFromChild(final Activity child) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_onNavigateUpFromChild(child);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun1<Boolean, Activity> superCall = new CallFun1<Boolean, Activity>(
                "onNavigateUpFromChild(Activity)") {

            @Override
            public Boolean call(final Activity child) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().onNavigateUpFromChild(this, child);
                } else {
                    return getOriginal().super_onNavigateUpFromChild(child);
                }
            }
        };
        return superCall.call(child);
    }

    public void onNewIntent(final Intent intent) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_onNewIntent(intent);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid1<Intent> superCall = new CallVoid1<Intent>("onNewIntent(Intent)") {

            @Override
            public void call(final Intent intent) {
                if (iterator.hasPrevious()) {
                    iterator.previous().onNewIntent(this, intent);
                } else {
                    getOriginal().super_onNewIntent(intent);
                }
            }
        };
        superCall.call(intent);
    }

    public boolean onOptionsItemSelected(final MenuItem item) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_onOptionsItemSelected(item);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun1<Boolean, MenuItem> superCall = new CallFun1<Boolean, MenuItem>(
                "onOptionsItemSelected(MenuItem)") {

            @Override
            public Boolean call(final MenuItem item) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().onOptionsItemSelected(this, item);
                } else {
                    return getOriginal().super_onOptionsItemSelected(item);
                }
            }
        };
        return superCall.call(item);
    }

    public void onOptionsMenuClosed(final Menu menu) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_onOptionsMenuClosed(menu);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid1<Menu> superCall = new CallVoid1<Menu>("onOptionsMenuClosed(Menu)") {

            @Override
            public void call(final Menu menu) {
                if (iterator.hasPrevious()) {
                    iterator.previous().onOptionsMenuClosed(this, menu);
                } else {
                    getOriginal().super_onOptionsMenuClosed(menu);
                }
            }
        };
        superCall.call(menu);
    }

    public void onPanelClosed(final int featureId, final Menu menu) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_onPanelClosed(featureId, menu);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid2<Integer, Menu> superCall = new CallVoid2<Integer, Menu>(
                "onPanelClosed(Integer, Menu)") {

            @Override
            public void call(final Integer featureId, final Menu menu) {
                if (iterator.hasPrevious()) {
                    iterator.previous().onPanelClosed(this, featureId, menu);
                } else {
                    getOriginal().super_onPanelClosed(featureId, menu);
                }
            }
        };
        superCall.call(featureId, menu);
    }

    public void onPause() {
        if (mPlugins.isEmpty()) {
            getOriginal().super_onPause();
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid0 superCall = new CallVoid0("onPause()") {

            @Override
            public void call() {
                if (iterator.hasPrevious()) {
                    iterator.previous().onPause(this);
                } else {
                    getOriginal().super_onPause();
                }
            }
        };
        superCall.call();
    }

    public void onPostCreate(final Bundle savedInstanceState,
            final PersistableBundle persistentState) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_onPostCreate(savedInstanceState, persistentState);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid2<Bundle, PersistableBundle> superCall
                = new CallVoid2<Bundle, PersistableBundle>(
                "onPostCreate(Bundle, PersistableBundle)") {

            @Override
            public void call(final Bundle savedInstanceState,
                    final PersistableBundle persistentState) {
                if (iterator.hasPrevious()) {
                    iterator.previous().onPostCreate(this, savedInstanceState, persistentState);
                } else {
                    getOriginal().super_onPostCreate(savedInstanceState, persistentState);
                }
            }
        };
        superCall.call(savedInstanceState, persistentState);
    }

    public void onPostCreate(@Nullable final Bundle savedInstanceState) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_onPostCreate(savedInstanceState);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid1<Bundle> superCall = new CallVoid1<Bundle>("onPostCreate(Bundle)") {

            @Override
            public void call(final Bundle savedInstanceState) {
                if (iterator.hasPrevious()) {
                    iterator.previous().onPostCreate(this, savedInstanceState);
                } else {
                    getOriginal().super_onPostCreate(savedInstanceState);
                }
            }
        };
        superCall.call(savedInstanceState);
    }

    public void onPostResume() {
        if (mPlugins.isEmpty()) {
            getOriginal().super_onPostResume();
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid0 superCall = new CallVoid0("onPostResume()") {

            @Override
            public void call() {
                if (iterator.hasPrevious()) {
                    iterator.previous().onPostResume(this);
                } else {
                    getOriginal().super_onPostResume();
                }
            }
        };
        superCall.call();
    }

    public void onPrepareDialog(final int id, final Dialog dialog) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_onPrepareDialog(id, dialog);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid2<Integer, Dialog> superCall = new CallVoid2<Integer, Dialog>(
                "onPrepareDialog(Integer, Dialog)") {

            @Override
            public void call(final Integer id, final Dialog dialog) {
                if (iterator.hasPrevious()) {
                    iterator.previous().onPrepareDialog(this, id, dialog);
                } else {
                    getOriginal().super_onPrepareDialog(id, dialog);
                }
            }
        };
        superCall.call(id, dialog);
    }

    public void onPrepareDialog(final int id, final Dialog dialog, final Bundle args) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_onPrepareDialog(id, dialog, args);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid3<Integer, Dialog, Bundle> superCall = new CallVoid3<Integer, Dialog, Bundle>(
                "onPrepareDialog(Integer, Dialog, Bundle)") {

            @Override
            public void call(final Integer id, final Dialog dialog, final Bundle args) {
                if (iterator.hasPrevious()) {
                    iterator.previous().onPrepareDialog(this, id, dialog, args);
                } else {
                    getOriginal().super_onPrepareDialog(id, dialog, args);
                }
            }
        };
        superCall.call(id, dialog, args);
    }

    public void onPrepareNavigateUpTaskStack(final TaskStackBuilder builder) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_onPrepareNavigateUpTaskStack(builder);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid1<TaskStackBuilder> superCall = new CallVoid1<TaskStackBuilder>(
                "onPrepareNavigateUpTaskStack(TaskStackBuilder)") {

            @Override
            public void call(final TaskStackBuilder builder) {
                if (iterator.hasPrevious()) {
                    iterator.previous().onPrepareNavigateUpTaskStack(this, builder);
                } else {
                    getOriginal().super_onPrepareNavigateUpTaskStack(builder);
                }
            }
        };
        superCall.call(builder);
    }

    public boolean onPrepareOptionsMenu(final Menu menu) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_onPrepareOptionsMenu(menu);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun1<Boolean, Menu> superCall = new CallFun1<Boolean, Menu>(
                "onPrepareOptionsMenu(Menu)") {

            @Override
            public Boolean call(final Menu menu) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().onPrepareOptionsMenu(this, menu);
                } else {
                    return getOriginal().super_onPrepareOptionsMenu(menu);
                }
            }
        };
        return superCall.call(menu);
    }

    public boolean onPrepareOptionsPanel(final View view, final Menu menu) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_onPrepareOptionsPanel(view, menu);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun2<Boolean, View, Menu> superCall = new CallFun2<Boolean, View, Menu>(
                "onPrepareOptionsPanel(View, Menu)") {

            @Override
            public Boolean call(final View view, final Menu menu) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().onPrepareOptionsPanel(this, view, menu);
                } else {
                    return getOriginal().super_onPrepareOptionsPanel(view, menu);
                }
            }
        };
        return superCall.call(view, menu);
    }

    public boolean onPreparePanel(final int featureId, final View view, final Menu menu) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_onPreparePanel(featureId, view, menu);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun3<Boolean, Integer, View, Menu> superCall
                = new CallFun3<Boolean, Integer, View, Menu>(
                "onPreparePanel(Integer, View, Menu)") {

            @Override
            public Boolean call(final Integer featureId, final View view, final Menu menu) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().onPreparePanel(this, featureId, view, menu);
                } else {
                    return getOriginal().super_onPreparePanel(featureId, view, menu);
                }
            }
        };
        return superCall.call(featureId, view, menu);
    }

    public void onPrepareSupportNavigateUpTaskStack(
            @NonNull final android.support.v4.app.TaskStackBuilder builder) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_onPrepareSupportNavigateUpTaskStack(builder);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid1<android.support.v4.app.TaskStackBuilder> superCall
                = new CallVoid1<android.support.v4.app.TaskStackBuilder>(
                "onPrepareSupportNavigateUpTaskStack(android.support.v4.app.TaskStackBuilder)") {

            @Override
            public void call(final android.support.v4.app.TaskStackBuilder builder) {
                if (iterator.hasPrevious()) {
                    iterator.previous().onPrepareSupportNavigateUpTaskStack(this, builder);
                } else {
                    getOriginal().super_onPrepareSupportNavigateUpTaskStack(builder);
                }
            }
        };
        superCall.call(builder);
    }

    public void onProvideAssistContent(final AssistContent outContent) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_onProvideAssistContent(outContent);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid1<AssistContent> superCall = new CallVoid1<AssistContent>(
                "onProvideAssistContent(AssistContent)") {

            @Override
            public void call(final AssistContent outContent) {
                if (iterator.hasPrevious()) {
                    iterator.previous().onProvideAssistContent(this, outContent);
                } else {
                    getOriginal().super_onProvideAssistContent(outContent);
                }
            }
        };
        superCall.call(outContent);
    }

    public void onProvideAssistData(final Bundle data) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_onProvideAssistData(data);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid1<Bundle> superCall = new CallVoid1<Bundle>("onProvideAssistData(Bundle)") {

            @Override
            public void call(final Bundle data) {
                if (iterator.hasPrevious()) {
                    iterator.previous().onProvideAssistData(this, data);
                } else {
                    getOriginal().super_onProvideAssistData(data);
                }
            }
        };
        superCall.call(data);
    }

    public Uri onProvideReferrer() {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_onProvideReferrer();
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun0<Uri> superCall = new CallFun0<Uri>("onProvideReferrer()") {

            @Override
            public Uri call() {
                if (iterator.hasPrevious()) {
                    return iterator.previous().onProvideReferrer(this);
                } else {
                    return getOriginal().super_onProvideReferrer();
                }
            }
        };
        return superCall.call();
    }

    public void onRequestPermissionsResult(final int requestCode,
            @NonNull final String[] permissions, @NonNull final int[] grantResults) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid3<Integer, String[], int[]> superCall
                = new CallVoid3<Integer, String[], int[]>(
                "onRequestPermissionsResult(Integer, String[], int[])") {

            @Override
            public void call(final Integer requestCode, final String[] permissions,
                    final int[] grantResults) {
                if (iterator.hasPrevious()) {
                    iterator.previous().onRequestPermissionsResult(this, requestCode, permissions,
                            grantResults);
                } else {
                    getOriginal().super_onRequestPermissionsResult(requestCode, permissions,
                            grantResults);
                }
            }
        };
        superCall.call(requestCode, permissions, grantResults);
    }

    public void onRestart() {
        if (mPlugins.isEmpty()) {
            getOriginal().super_onRestart();
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid0 superCall = new CallVoid0("onRestart()") {

            @Override
            public void call() {
                if (iterator.hasPrevious()) {
                    iterator.previous().onRestart(this);
                } else {
                    getOriginal().super_onRestart();
                }
            }
        };
        superCall.call();
    }

    public void onRestoreInstanceState(final Bundle savedInstanceState,
            final PersistableBundle persistentState) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_onRestoreInstanceState(savedInstanceState, persistentState);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid2<Bundle, PersistableBundle> superCall
                = new CallVoid2<Bundle, PersistableBundle>(
                "onRestoreInstanceState(Bundle, PersistableBundle)") {

            @Override
            public void call(final Bundle savedInstanceState,
                    final PersistableBundle persistentState) {
                if (iterator.hasPrevious()) {
                    iterator.previous()
                            .onRestoreInstanceState(this, savedInstanceState, persistentState);
                } else {
                    getOriginal().super_onRestoreInstanceState(savedInstanceState, persistentState);
                }
            }
        };
        superCall.call(savedInstanceState, persistentState);
    }

    public void onRestoreInstanceState(final Bundle savedInstanceState) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_onRestoreInstanceState(savedInstanceState);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid1<Bundle> superCall = new CallVoid1<Bundle>(
                "onRestoreInstanceState(Bundle)") {

            @Override
            public void call(final Bundle savedInstanceState) {
                if (iterator.hasPrevious()) {
                    iterator.previous().onRestoreInstanceState(this, savedInstanceState);
                } else {
                    getOriginal().super_onRestoreInstanceState(savedInstanceState);
                }
            }
        };
        superCall.call(savedInstanceState);
    }

    public void onResume() {
        if (mPlugins.isEmpty()) {
            getOriginal().super_onResume();
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid0 superCall = new CallVoid0("onResume()") {

            @Override
            public void call() {
                if (iterator.hasPrevious()) {
                    iterator.previous().onResume(this);
                } else {
                    getOriginal().super_onResume();
                }
            }
        };
        superCall.call();
    }

    public void onResumeFragments() {
        if (mPlugins.isEmpty()) {
            getOriginal().super_onResumeFragments();
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid0 superCall = new CallVoid0("onResumeFragments()") {

            @Override
            public void call() {
                if (iterator.hasPrevious()) {
                    iterator.previous().onResumeFragments(this);
                } else {
                    getOriginal().super_onResumeFragments();
                }
            }
        };
        superCall.call();
    }

    public Object onRetainNonConfigurationInstance() {
        final NonConfigurationInstanceWrapper all = new NonConfigurationInstanceWrapper(
                getOriginal().onRetainCompositeCustomNonConfigurationInstance());
        for (final ActivityPlugin plugin : mPlugins) {
            final CompositeNonConfigurationInstance pluginNci = plugin
                    .onRetainNonConfigurationInstance();
            if (pluginNci != null) {
                all.putPluginNonConfigurationInstance(pluginNci);
            }
        }
        return all;
    }

    public void onSaveInstanceState(final Bundle outState,
            final PersistableBundle outPersistentState) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_onSaveInstanceState(outState, outPersistentState);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid2<Bundle, PersistableBundle> superCall
                = new CallVoid2<Bundle, PersistableBundle>(
                "onSaveInstanceState(Bundle, PersistableBundle)") {

            @Override
            public void call(final Bundle outState, final PersistableBundle outPersistentState) {
                if (iterator.hasPrevious()) {
                    iterator.previous().onSaveInstanceState(this, outState, outPersistentState);
                } else {
                    getOriginal().super_onSaveInstanceState(outState, outPersistentState);
                }
            }
        };
        superCall.call(outState, outPersistentState);
    }

    public void onSaveInstanceState(final Bundle outState) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_onSaveInstanceState(outState);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid1<Bundle> superCall = new CallVoid1<Bundle>("onSaveInstanceState(Bundle)") {

            @Override
            public void call(final Bundle outState) {
                if (iterator.hasPrevious()) {
                    iterator.previous().onSaveInstanceState(this, outState);
                } else {
                    getOriginal().super_onSaveInstanceState(outState);
                }
            }
        };
        superCall.call(outState);
    }

    public boolean onSearchRequested(final SearchEvent searchEvent) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_onSearchRequested(searchEvent);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun1<Boolean, SearchEvent> superCall = new CallFun1<Boolean, SearchEvent>(
                "onSearchRequested(SearchEvent)") {

            @Override
            public Boolean call(final SearchEvent searchEvent) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().onSearchRequested(this, searchEvent);
                } else {
                    return getOriginal().super_onSearchRequested(searchEvent);
                }
            }
        };
        return superCall.call(searchEvent);
    }

    public boolean onSearchRequested() {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_onSearchRequested();
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun0<Boolean> superCall = new CallFun0<Boolean>("onSearchRequested()") {

            @Override
            public Boolean call() {
                if (iterator.hasPrevious()) {
                    return iterator.previous().onSearchRequested(this);
                } else {
                    return getOriginal().super_onSearchRequested();
                }
            }
        };
        return superCall.call();
    }

    public void onStart() {
        if (mPlugins.isEmpty()) {
            getOriginal().super_onStart();
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid0 superCall = new CallVoid0("onStart()") {

            @Override
            public void call() {
                if (iterator.hasPrevious()) {
                    iterator.previous().onStart(this);
                } else {
                    getOriginal().super_onStart();
                }
            }
        };
        superCall.call();
    }

    public void onStateNotSaved() {
        if (mPlugins.isEmpty()) {
            getOriginal().super_onStateNotSaved();
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid0 superCall = new CallVoid0("onStateNotSaved()") {

            @Override
            public void call() {
                if (iterator.hasPrevious()) {
                    iterator.previous().onStateNotSaved(this);
                } else {
                    getOriginal().super_onStateNotSaved();
                }
            }
        };
        superCall.call();
    }

    public void onStop() {
        if (mPlugins.isEmpty()) {
            getOriginal().super_onStop();
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid0 superCall = new CallVoid0("onStop()") {

            @Override
            public void call() {
                if (iterator.hasPrevious()) {
                    iterator.previous().onStop(this);
                } else {
                    getOriginal().super_onStop();
                }
            }
        };
        superCall.call();
    }

    public void onSupportActionModeFinished(@NonNull final ActionMode mode) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_onSupportActionModeFinished(mode);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid1<ActionMode> superCall = new CallVoid1<ActionMode>(
                "onSupportActionModeFinished(ActionMode)") {

            @Override
            public void call(final ActionMode mode) {
                if (iterator.hasPrevious()) {
                    iterator.previous().onSupportActionModeFinished(this, mode);
                } else {
                    getOriginal().super_onSupportActionModeFinished(mode);
                }
            }
        };
        superCall.call(mode);
    }

    public void onSupportActionModeStarted(@NonNull final ActionMode mode) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_onSupportActionModeStarted(mode);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid1<ActionMode> superCall = new CallVoid1<ActionMode>(
                "onSupportActionModeStarted(ActionMode)") {

            @Override
            public void call(final ActionMode mode) {
                if (iterator.hasPrevious()) {
                    iterator.previous().onSupportActionModeStarted(this, mode);
                } else {
                    getOriginal().super_onSupportActionModeStarted(mode);
                }
            }
        };
        superCall.call(mode);
    }

    public void onSupportContentChanged() {
        if (mPlugins.isEmpty()) {
            getOriginal().super_onSupportContentChanged();
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid0 superCall = new CallVoid0("onSupportContentChanged()") {

            @Override
            public void call() {
                if (iterator.hasPrevious()) {
                    iterator.previous().onSupportContentChanged(this);
                } else {
                    getOriginal().super_onSupportContentChanged();
                }
            }
        };
        superCall.call();
    }

    public boolean onSupportNavigateUp() {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_onSupportNavigateUp();
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun0<Boolean> superCall = new CallFun0<Boolean>("onSupportNavigateUp()") {

            @Override
            public Boolean call() {
                if (iterator.hasPrevious()) {
                    return iterator.previous().onSupportNavigateUp(this);
                } else {
                    return getOriginal().super_onSupportNavigateUp();
                }
            }
        };
        return superCall.call();
    }

    public void onTitleChanged(final CharSequence title, final int color) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_onTitleChanged(title, color);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid2<CharSequence, Integer> superCall = new CallVoid2<CharSequence, Integer>(
                "onTitleChanged(CharSequence, Integer)") {

            @Override
            public void call(final CharSequence title, final Integer color) {
                if (iterator.hasPrevious()) {
                    iterator.previous().onTitleChanged(this, title, color);
                } else {
                    getOriginal().super_onTitleChanged(title, color);
                }
            }
        };
        superCall.call(title, color);
    }

    public boolean onTouchEvent(final MotionEvent event) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_onTouchEvent(event);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun1<Boolean, MotionEvent> superCall = new CallFun1<Boolean, MotionEvent>(
                "onTouchEvent(MotionEvent)") {

            @Override
            public Boolean call(final MotionEvent event) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().onTouchEvent(this, event);
                } else {
                    return getOriginal().super_onTouchEvent(event);
                }
            }
        };
        return superCall.call(event);
    }

    public boolean onTrackballEvent(final MotionEvent event) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_onTrackballEvent(event);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun1<Boolean, MotionEvent> superCall = new CallFun1<Boolean, MotionEvent>(
                "onTrackballEvent(MotionEvent)") {

            @Override
            public Boolean call(final MotionEvent event) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().onTrackballEvent(this, event);
                } else {
                    return getOriginal().super_onTrackballEvent(event);
                }
            }
        };
        return superCall.call(event);
    }

    public void onTrimMemory(final int level) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_onTrimMemory(level);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid1<Integer> superCall = new CallVoid1<Integer>("onTrimMemory(Integer)") {

            @Override
            public void call(final Integer level) {
                if (iterator.hasPrevious()) {
                    iterator.previous().onTrimMemory(this, level);
                } else {
                    getOriginal().super_onTrimMemory(level);
                }
            }
        };
        superCall.call(level);
    }

    public void onUserInteraction() {
        if (mPlugins.isEmpty()) {
            getOriginal().super_onUserInteraction();
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid0 superCall = new CallVoid0("onUserInteraction()") {

            @Override
            public void call() {
                if (iterator.hasPrevious()) {
                    iterator.previous().onUserInteraction(this);
                } else {
                    getOriginal().super_onUserInteraction();
                }
            }
        };
        superCall.call();
    }

    public void onUserLeaveHint() {
        if (mPlugins.isEmpty()) {
            getOriginal().super_onUserLeaveHint();
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid0 superCall = new CallVoid0("onUserLeaveHint()") {

            @Override
            public void call() {
                if (iterator.hasPrevious()) {
                    iterator.previous().onUserLeaveHint(this);
                } else {
                    getOriginal().super_onUserLeaveHint();
                }
            }
        };
        superCall.call();
    }

    public void onVisibleBehindCanceled() {
        if (mPlugins.isEmpty()) {
            getOriginal().super_onVisibleBehindCanceled();
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid0 superCall = new CallVoid0("onVisibleBehindCanceled()") {

            @Override
            public void call() {
                if (iterator.hasPrevious()) {
                    iterator.previous().onVisibleBehindCanceled(this);
                } else {
                    getOriginal().super_onVisibleBehindCanceled();
                }
            }
        };
        superCall.call();
    }

    public void onWindowAttributesChanged(final WindowManager.LayoutParams params) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_onWindowAttributesChanged(params);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid1<WindowManager.LayoutParams> superCall
                = new CallVoid1<WindowManager.LayoutParams>(
                "onWindowAttributesChanged(WindowManager.LayoutParams)") {

            @Override
            public void call(final WindowManager.LayoutParams params) {
                if (iterator.hasPrevious()) {
                    iterator.previous().onWindowAttributesChanged(this, params);
                } else {
                    getOriginal().super_onWindowAttributesChanged(params);
                }
            }
        };
        superCall.call(params);
    }

    public void onWindowFocusChanged(final boolean hasFocus) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_onWindowFocusChanged(hasFocus);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid1<Boolean> superCall = new CallVoid1<Boolean>(
                "onWindowFocusChanged(Boolean)") {

            @Override
            public void call(final Boolean hasFocus) {
                if (iterator.hasPrevious()) {
                    iterator.previous().onWindowFocusChanged(this, hasFocus);
                } else {
                    getOriginal().super_onWindowFocusChanged(hasFocus);
                }
            }
        };
        superCall.call(hasFocus);
    }

    public android.view.ActionMode onWindowStartingActionMode(
            final android.view.ActionMode.Callback callback) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_onWindowStartingActionMode(callback);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun1<android.view.ActionMode, android.view.ActionMode.Callback> superCall
                = new CallFun1<android.view.ActionMode, android.view.ActionMode.Callback>(
                "onWindowStartingActionMode(android.view.ActionMode.Callback)") {

            @Override
            public android.view.ActionMode call(final android.view.ActionMode.Callback callback) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().onWindowStartingActionMode(this, callback);
                } else {
                    return getOriginal().super_onWindowStartingActionMode(callback);
                }
            }
        };
        return superCall.call(callback);
    }

    public android.view.ActionMode onWindowStartingActionMode(
            final android.view.ActionMode.Callback callback, final int type) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_onWindowStartingActionMode(callback, type);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun2<android.view.ActionMode, android.view.ActionMode.Callback, Integer> superCall
                = new CallFun2<android.view.ActionMode, android.view.ActionMode.Callback, Integer>(
                "onWindowStartingActionMode(android.view.ActionMode.Callback, Integer)") {

            @Override
            public android.view.ActionMode call(final android.view.ActionMode.Callback callback,
                    final Integer type) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().onWindowStartingActionMode(this, callback, type);
                } else {
                    return getOriginal().super_onWindowStartingActionMode(callback, type);
                }
            }
        };
        return superCall.call(callback, type);
    }

    public ActionMode onWindowStartingSupportActionMode(
            @NonNull final ActionMode.Callback callback) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_onWindowStartingSupportActionMode(callback);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun1<ActionMode, ActionMode.Callback> superCall
                = new CallFun1<ActionMode, ActionMode.Callback>(
                "onWindowStartingSupportActionMode(ActionMode.Callback)") {

            @Override
            public ActionMode call(final ActionMode.Callback callback) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().onWindowStartingSupportActionMode(this, callback);
                } else {
                    return getOriginal().super_onWindowStartingSupportActionMode(callback);
                }
            }
        };
        return superCall.call(callback);
    }

    public void openContextMenu(final View view) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_openContextMenu(view);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid1<View> superCall = new CallVoid1<View>("openContextMenu(View)") {

            @Override
            public void call(final View view) {
                if (iterator.hasPrevious()) {
                    iterator.previous().openContextMenu(this, view);
                } else {
                    getOriginal().super_openContextMenu(view);
                }
            }
        };
        superCall.call(view);
    }

    public FileInputStream openFileInput(final String name) throws FileNotFoundException {
        if (mPlugins.isEmpty()) {
            try {
                return getOriginal().super_openFileInput(name);
            } catch (FileNotFoundException e) {
                throw new SuppressedException(e);
            }
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun1<FileInputStream, String> superCall = new CallFun1<FileInputStream, String>(
                "openFileInput(String)") {

            @Override
            public FileInputStream call(final String name) {
                if (iterator.hasPrevious()) {
                    try {
                        return iterator.previous().openFileInput(this, name);
                    } catch (FileNotFoundException e) {
                        throw new SuppressedException(e);
                    }
                } else {
                    try {
                        return getOriginal().super_openFileInput(name);
                    } catch (FileNotFoundException e) {
                        throw new SuppressedException(e);
                    }
                }
            }
        };
        return superCall.call(name);
    }

    public FileOutputStream openFileOutput(final String name, final int mode)
            throws FileNotFoundException {
        if (mPlugins.isEmpty()) {
            try {
                return getOriginal().super_openFileOutput(name, mode);
            } catch (FileNotFoundException e) {
                throw new SuppressedException(e);
            }
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun2<FileOutputStream, String, Integer> superCall
                = new CallFun2<FileOutputStream, String, Integer>(
                "openFileOutput(String, Integer)") {

            @Override
            public FileOutputStream call(final String name, final Integer mode) {
                if (iterator.hasPrevious()) {
                    try {
                        return iterator.previous().openFileOutput(this, name, mode);
                    } catch (FileNotFoundException e) {
                        throw new SuppressedException(e);
                    }
                } else {
                    try {
                        return getOriginal().super_openFileOutput(name, mode);
                    } catch (FileNotFoundException e) {
                        throw new SuppressedException(e);
                    }
                }
            }
        };
        return superCall.call(name, mode);
    }

    public void openOptionsMenu() {
        if (mPlugins.isEmpty()) {
            getOriginal().super_openOptionsMenu();
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid0 superCall = new CallVoid0("openOptionsMenu()") {

            @Override
            public void call() {
                if (iterator.hasPrevious()) {
                    iterator.previous().openOptionsMenu(this);
                } else {
                    getOriginal().super_openOptionsMenu();
                }
            }
        };
        superCall.call();
    }

    public SQLiteDatabase openOrCreateDatabase(final String name, final int mode,
            final SQLiteDatabase.CursorFactory factory) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_openOrCreateDatabase(name, mode, factory);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun3<SQLiteDatabase, String, Integer, SQLiteDatabase.CursorFactory> superCall
                = new CallFun3<SQLiteDatabase, String, Integer, SQLiteDatabase.CursorFactory>(
                "openOrCreateDatabase(String, Integer, SQLiteDatabase.CursorFactory)") {

            @Override
            public SQLiteDatabase call(final String name, final Integer mode,
                    final SQLiteDatabase.CursorFactory factory) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().openOrCreateDatabase(this, name, mode, factory);
                } else {
                    return getOriginal().super_openOrCreateDatabase(name, mode, factory);
                }
            }
        };
        return superCall.call(name, mode, factory);
    }

    public SQLiteDatabase openOrCreateDatabase(final String name, final int mode,
            final SQLiteDatabase.CursorFactory factory, final DatabaseErrorHandler errorHandler) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_openOrCreateDatabase(name, mode, factory, errorHandler);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun4<SQLiteDatabase, String, Integer, SQLiteDatabase.CursorFactory, DatabaseErrorHandler>
                superCall
                = new CallFun4<SQLiteDatabase, String, Integer, SQLiteDatabase.CursorFactory, DatabaseErrorHandler>(
                "openOrCreateDatabase(String, Integer, SQLiteDatabase.CursorFactory, DatabaseErrorHandler)") {

            @Override
            public SQLiteDatabase call(final String name, final Integer mode,
                    final SQLiteDatabase.CursorFactory factory,
                    final DatabaseErrorHandler errorHandler) {
                if (iterator.hasPrevious()) {
                    return iterator.previous()
                            .openOrCreateDatabase(this, name, mode, factory, errorHandler);
                } else {
                    return getOriginal()
                            .super_openOrCreateDatabase(name, mode, factory, errorHandler);
                }
            }
        };
        return superCall.call(name, mode, factory, errorHandler);
    }

    public void overridePendingTransition(final int enterAnim, final int exitAnim) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_overridePendingTransition(enterAnim, exitAnim);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid2<Integer, Integer> superCall = new CallVoid2<Integer, Integer>(
                "overridePendingTransition(Integer, Integer)") {

            @Override
            public void call(final Integer enterAnim, final Integer exitAnim) {
                if (iterator.hasPrevious()) {
                    iterator.previous().overridePendingTransition(this, enterAnim, exitAnim);
                } else {
                    getOriginal().super_overridePendingTransition(enterAnim, exitAnim);
                }
            }
        };
        superCall.call(enterAnim, exitAnim);
    }

    public Drawable peekWallpaper() {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_peekWallpaper();
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun0<Drawable> superCall = new CallFun0<Drawable>("peekWallpaper()") {

            @Override
            public Drawable call() {
                if (iterator.hasPrevious()) {
                    return iterator.previous().peekWallpaper(this);
                } else {
                    return getOriginal().super_peekWallpaper();
                }
            }
        };
        return superCall.call();
    }

    public void postponeEnterTransition() {
        if (mPlugins.isEmpty()) {
            getOriginal().super_postponeEnterTransition();
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid0 superCall = new CallVoid0("postponeEnterTransition()") {

            @Override
            public void call() {
                if (iterator.hasPrevious()) {
                    iterator.previous().postponeEnterTransition(this);
                } else {
                    getOriginal().super_postponeEnterTransition();
                }
            }
        };
        superCall.call();
    }

    public void recreate() {
        if (mPlugins.isEmpty()) {
            getOriginal().super_recreate();
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid0 superCall = new CallVoid0("recreate()") {

            @Override
            public void call() {
                if (iterator.hasPrevious()) {
                    iterator.previous().recreate(this);
                } else {
                    getOriginal().super_recreate();
                }
            }
        };
        superCall.call();
    }

    public void registerComponentCallbacks(final ComponentCallbacks callback) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_registerComponentCallbacks(callback);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid1<ComponentCallbacks> superCall = new CallVoid1<ComponentCallbacks>(
                "registerComponentCallbacks(ComponentCallbacks)") {

            @Override
            public void call(final ComponentCallbacks callback) {
                if (iterator.hasPrevious()) {
                    iterator.previous().registerComponentCallbacks(this, callback);
                } else {
                    getOriginal().super_registerComponentCallbacks(callback);
                }
            }
        };
        superCall.call(callback);
    }

    public void registerForContextMenu(final View view) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_registerForContextMenu(view);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid1<View> superCall = new CallVoid1<View>("registerForContextMenu(View)") {

            @Override
            public void call(final View view) {
                if (iterator.hasPrevious()) {
                    iterator.previous().registerForContextMenu(this, view);
                } else {
                    getOriginal().super_registerForContextMenu(view);
                }
            }
        };
        superCall.call(view);
    }

    public Intent registerReceiver(final BroadcastReceiver receiver, final IntentFilter filter) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_registerReceiver(receiver, filter);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun2<Intent, BroadcastReceiver, IntentFilter> superCall
                = new CallFun2<Intent, BroadcastReceiver, IntentFilter>(
                "registerReceiver(BroadcastReceiver, IntentFilter)") {

            @Override
            public Intent call(final BroadcastReceiver receiver, final IntentFilter filter) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().registerReceiver(this, receiver, filter);
                } else {
                    return getOriginal().super_registerReceiver(receiver, filter);
                }
            }
        };
        return superCall.call(receiver, filter);
    }

    public Intent registerReceiver(final BroadcastReceiver receiver, final IntentFilter filter,
            final String broadcastPermission, final Handler scheduler) {
        if (mPlugins.isEmpty()) {
            return getOriginal()
                    .super_registerReceiver(receiver, filter, broadcastPermission, scheduler);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun4<Intent, BroadcastReceiver, IntentFilter, String, Handler> superCall
                = new CallFun4<Intent, BroadcastReceiver, IntentFilter, String, Handler>(
                "registerReceiver(BroadcastReceiver, IntentFilter, String, Handler)") {

            @Override
            public Intent call(final BroadcastReceiver receiver, final IntentFilter filter,
                    final String broadcastPermission, final Handler scheduler) {
                if (iterator.hasPrevious()) {
                    return iterator.previous()
                            .registerReceiver(this, receiver, filter, broadcastPermission,
                                    scheduler);
                } else {
                    return getOriginal()
                            .super_registerReceiver(receiver, filter, broadcastPermission,
                                    scheduler);
                }
            }
        };
        return superCall.call(receiver, filter, broadcastPermission, scheduler);
    }

    public boolean releaseInstance() {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_releaseInstance();
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun0<Boolean> superCall = new CallFun0<Boolean>("releaseInstance()") {

            @Override
            public Boolean call() {
                if (iterator.hasPrevious()) {
                    return iterator.previous().releaseInstance(this);
                } else {
                    return getOriginal().super_releaseInstance();
                }
            }
        };
        return superCall.call();
    }

    public void removeStickyBroadcast(final Intent intent) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_removeStickyBroadcast(intent);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid1<Intent> superCall = new CallVoid1<Intent>("removeStickyBroadcast(Intent)") {

            @Override
            public void call(final Intent intent) {
                if (iterator.hasPrevious()) {
                    iterator.previous().removeStickyBroadcast(this, intent);
                } else {
                    getOriginal().super_removeStickyBroadcast(intent);
                }
            }
        };
        superCall.call(intent);
    }

    public void removeStickyBroadcastAsUser(final Intent intent, final UserHandle user) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_removeStickyBroadcastAsUser(intent, user);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid2<Intent, UserHandle> superCall = new CallVoid2<Intent, UserHandle>(
                "removeStickyBroadcastAsUser(Intent, UserHandle)") {

            @Override
            public void call(final Intent intent, final UserHandle user) {
                if (iterator.hasPrevious()) {
                    iterator.previous().removeStickyBroadcastAsUser(this, intent, user);
                } else {
                    getOriginal().super_removeStickyBroadcastAsUser(intent, user);
                }
            }
        };
        superCall.call(intent, user);
    }

    public void reportFullyDrawn() {
        if (mPlugins.isEmpty()) {
            getOriginal().super_reportFullyDrawn();
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid0 superCall = new CallVoid0("reportFullyDrawn()") {

            @Override
            public void call() {
                if (iterator.hasPrevious()) {
                    iterator.previous().reportFullyDrawn(this);
                } else {
                    getOriginal().super_reportFullyDrawn();
                }
            }
        };
        superCall.call();
    }

    public boolean requestVisibleBehind(final boolean visible) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_requestVisibleBehind(visible);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun1<Boolean, Boolean> superCall = new CallFun1<Boolean, Boolean>(
                "requestVisibleBehind(Boolean)") {

            @Override
            public Boolean call(final Boolean visible) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().requestVisibleBehind(this, visible);
                } else {
                    return getOriginal().super_requestVisibleBehind(visible);
                }
            }
        };
        return superCall.call(visible);
    }

    public void revokeUriPermission(final Uri uri, final int modeFlags) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_revokeUriPermission(uri, modeFlags);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid2<Uri, Integer> superCall = new CallVoid2<Uri, Integer>(
                "revokeUriPermission(Uri, Integer)") {

            @Override
            public void call(final Uri uri, final Integer modeFlags) {
                if (iterator.hasPrevious()) {
                    iterator.previous().revokeUriPermission(this, uri, modeFlags);
                } else {
                    getOriginal().super_revokeUriPermission(uri, modeFlags);
                }
            }
        };
        superCall.call(uri, modeFlags);
    }

    public void sendBroadcast(final Intent intent) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_sendBroadcast(intent);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid1<Intent> superCall = new CallVoid1<Intent>("sendBroadcast(Intent)") {

            @Override
            public void call(final Intent intent) {
                if (iterator.hasPrevious()) {
                    iterator.previous().sendBroadcast(this, intent);
                } else {
                    getOriginal().super_sendBroadcast(intent);
                }
            }
        };
        superCall.call(intent);
    }

    public void sendBroadcast(final Intent intent, final String receiverPermission) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_sendBroadcast(intent, receiverPermission);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid2<Intent, String> superCall = new CallVoid2<Intent, String>(
                "sendBroadcast(Intent, String)") {

            @Override
            public void call(final Intent intent, final String receiverPermission) {
                if (iterator.hasPrevious()) {
                    iterator.previous().sendBroadcast(this, intent, receiverPermission);
                } else {
                    getOriginal().super_sendBroadcast(intent, receiverPermission);
                }
            }
        };
        superCall.call(intent, receiverPermission);
    }

    public void sendBroadcastAsUser(final Intent intent, final UserHandle user) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_sendBroadcastAsUser(intent, user);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid2<Intent, UserHandle> superCall = new CallVoid2<Intent, UserHandle>(
                "sendBroadcastAsUser(Intent, UserHandle)") {

            @Override
            public void call(final Intent intent, final UserHandle user) {
                if (iterator.hasPrevious()) {
                    iterator.previous().sendBroadcastAsUser(this, intent, user);
                } else {
                    getOriginal().super_sendBroadcastAsUser(intent, user);
                }
            }
        };
        superCall.call(intent, user);
    }

    public void sendBroadcastAsUser(final Intent intent, final UserHandle user,
            final String receiverPermission) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_sendBroadcastAsUser(intent, user, receiverPermission);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid3<Intent, UserHandle, String> superCall
                = new CallVoid3<Intent, UserHandle, String>(
                "sendBroadcastAsUser(Intent, UserHandle, String)") {

            @Override
            public void call(final Intent intent, final UserHandle user,
                    final String receiverPermission) {
                if (iterator.hasPrevious()) {
                    iterator.previous().sendBroadcastAsUser(this, intent, user, receiverPermission);
                } else {
                    getOriginal().super_sendBroadcastAsUser(intent, user, receiverPermission);
                }
            }
        };
        superCall.call(intent, user, receiverPermission);
    }

    public void sendOrderedBroadcast(final Intent intent, final String receiverPermission) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_sendOrderedBroadcast(intent, receiverPermission);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid2<Intent, String> superCall = new CallVoid2<Intent, String>(
                "sendOrderedBroadcast(Intent, String)") {

            @Override
            public void call(final Intent intent, final String receiverPermission) {
                if (iterator.hasPrevious()) {
                    iterator.previous().sendOrderedBroadcast(this, intent, receiverPermission);
                } else {
                    getOriginal().super_sendOrderedBroadcast(intent, receiverPermission);
                }
            }
        };
        superCall.call(intent, receiverPermission);
    }

    public void sendOrderedBroadcast(final Intent intent, final String receiverPermission,
            final BroadcastReceiver resultReceiver, final Handler scheduler, final int initialCode,
            final String initialData, final Bundle initialExtras) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_sendOrderedBroadcast(intent, receiverPermission, resultReceiver,
                    scheduler, initialCode, initialData, initialExtras);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid7<Intent, String, BroadcastReceiver, Handler, Integer, String, Bundle>
                superCall
                = new CallVoid7<Intent, String, BroadcastReceiver, Handler, Integer, String, Bundle>(
                "sendOrderedBroadcast(Intent, String, BroadcastReceiver, Handler, Integer, String, Bundle)") {

            @Override
            public void call(final Intent intent, final String receiverPermission,
                    final BroadcastReceiver resultReceiver, final Handler scheduler,
                    final Integer initialCode, final String initialData,
                    final Bundle initialExtras) {
                if (iterator.hasPrevious()) {
                    iterator.previous()
                            .sendOrderedBroadcast(this, intent, receiverPermission, resultReceiver,
                                    scheduler, initialCode, initialData, initialExtras);
                } else {
                    getOriginal()
                            .super_sendOrderedBroadcast(intent, receiverPermission, resultReceiver,
                                    scheduler, initialCode, initialData, initialExtras);
                }
            }
        };
        superCall.call(intent, receiverPermission, resultReceiver, scheduler, initialCode,
                initialData, initialExtras);
    }

    public void sendOrderedBroadcastAsUser(final Intent intent, final UserHandle user,
            final String receiverPermission, final BroadcastReceiver resultReceiver,
            final Handler scheduler, final int initialCode, final String initialData,
            final Bundle initialExtras) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_sendOrderedBroadcastAsUser(intent, user, receiverPermission,
                    resultReceiver, scheduler, initialCode, initialData, initialExtras);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid8<Intent, UserHandle, String, BroadcastReceiver, Handler, Integer, String, Bundle>
                superCall
                = new CallVoid8<Intent, UserHandle, String, BroadcastReceiver, Handler, Integer, String, Bundle>(
                "sendOrderedBroadcastAsUser(Intent, UserHandle, String, BroadcastReceiver, Handler, Integer, String, Bundle)") {

            @Override
            public void call(final Intent intent, final UserHandle user,
                    final String receiverPermission, final BroadcastReceiver resultReceiver,
                    final Handler scheduler, final Integer initialCode, final String initialData,
                    final Bundle initialExtras) {
                if (iterator.hasPrevious()) {
                    iterator.previous()
                            .sendOrderedBroadcastAsUser(this, intent, user, receiverPermission,
                                    resultReceiver, scheduler, initialCode, initialData,
                                    initialExtras);
                } else {
                    getOriginal().super_sendOrderedBroadcastAsUser(intent, user, receiverPermission,
                            resultReceiver, scheduler, initialCode, initialData, initialExtras);
                }
            }
        };
        superCall.call(intent, user, receiverPermission, resultReceiver, scheduler, initialCode,
                initialData, initialExtras);
    }

    public void sendStickyBroadcast(final Intent intent) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_sendStickyBroadcast(intent);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid1<Intent> superCall = new CallVoid1<Intent>("sendStickyBroadcast(Intent)") {

            @Override
            public void call(final Intent intent) {
                if (iterator.hasPrevious()) {
                    iterator.previous().sendStickyBroadcast(this, intent);
                } else {
                    getOriginal().super_sendStickyBroadcast(intent);
                }
            }
        };
        superCall.call(intent);
    }

    public void sendStickyBroadcastAsUser(final Intent intent, final UserHandle user) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_sendStickyBroadcastAsUser(intent, user);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid2<Intent, UserHandle> superCall = new CallVoid2<Intent, UserHandle>(
                "sendStickyBroadcastAsUser(Intent, UserHandle)") {

            @Override
            public void call(final Intent intent, final UserHandle user) {
                if (iterator.hasPrevious()) {
                    iterator.previous().sendStickyBroadcastAsUser(this, intent, user);
                } else {
                    getOriginal().super_sendStickyBroadcastAsUser(intent, user);
                }
            }
        };
        superCall.call(intent, user);
    }

    public void sendStickyOrderedBroadcast(final Intent intent,
            final BroadcastReceiver resultReceiver, final Handler scheduler, final int initialCode,
            final String initialData, final Bundle initialExtras) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_sendStickyOrderedBroadcast(intent, resultReceiver, scheduler,
                    initialCode, initialData, initialExtras);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid6<Intent, BroadcastReceiver, Handler, Integer, String, Bundle> superCall
                = new CallVoid6<Intent, BroadcastReceiver, Handler, Integer, String, Bundle>(
                "sendStickyOrderedBroadcast(Intent, BroadcastReceiver, Handler, Integer, String, Bundle)") {

            @Override
            public void call(final Intent intent, final BroadcastReceiver resultReceiver,
                    final Handler scheduler, final Integer initialCode, final String initialData,
                    final Bundle initialExtras) {
                if (iterator.hasPrevious()) {
                    iterator.previous()
                            .sendStickyOrderedBroadcast(this, intent, resultReceiver, scheduler,
                                    initialCode, initialData, initialExtras);
                } else {
                    getOriginal()
                            .super_sendStickyOrderedBroadcast(intent, resultReceiver, scheduler,
                                    initialCode, initialData, initialExtras);
                }
            }
        };
        superCall.call(intent, resultReceiver, scheduler, initialCode, initialData, initialExtras);
    }

    public void sendStickyOrderedBroadcastAsUser(final Intent intent, final UserHandle user,
            final BroadcastReceiver resultReceiver, final Handler scheduler, final int initialCode,
            final String initialData, final Bundle initialExtras) {
        if (mPlugins.isEmpty()) {
            getOriginal()
                    .super_sendStickyOrderedBroadcastAsUser(intent, user, resultReceiver, scheduler,
                            initialCode, initialData, initialExtras);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid7<Intent, UserHandle, BroadcastReceiver, Handler, Integer, String, Bundle>
                superCall
                = new CallVoid7<Intent, UserHandle, BroadcastReceiver, Handler, Integer, String, Bundle>(
                "sendStickyOrderedBroadcastAsUser(Intent, UserHandle, BroadcastReceiver, Handler, Integer, String, Bundle)") {

            @Override
            public void call(final Intent intent, final UserHandle user,
                    final BroadcastReceiver resultReceiver, final Handler scheduler,
                    final Integer initialCode, final String initialData,
                    final Bundle initialExtras) {
                if (iterator.hasPrevious()) {
                    iterator.previous()
                            .sendStickyOrderedBroadcastAsUser(this, intent, user, resultReceiver,
                                    scheduler, initialCode, initialData, initialExtras);
                } else {
                    getOriginal()
                            .super_sendStickyOrderedBroadcastAsUser(intent, user, resultReceiver,
                                    scheduler, initialCode, initialData, initialExtras);
                }
            }
        };
        superCall.call(intent, user, resultReceiver, scheduler, initialCode, initialData,
                initialExtras);
    }

    public void setActionBar(final Toolbar toolbar) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_setActionBar(toolbar);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid1<Toolbar> superCall = new CallVoid1<Toolbar>("setActionBar(Toolbar)") {

            @Override
            public void call(final Toolbar toolbar) {
                if (iterator.hasPrevious()) {
                    iterator.previous().setActionBar(this, toolbar);
                } else {
                    getOriginal().super_setActionBar(toolbar);
                }
            }
        };
        superCall.call(toolbar);
    }

    public void setContentTransitionManager(final TransitionManager tm) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_setContentTransitionManager(tm);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid1<TransitionManager> superCall = new CallVoid1<TransitionManager>(
                "setContentTransitionManager(TransitionManager)") {

            @Override
            public void call(final TransitionManager tm) {
                if (iterator.hasPrevious()) {
                    iterator.previous().setContentTransitionManager(this, tm);
                } else {
                    getOriginal().super_setContentTransitionManager(tm);
                }
            }
        };
        superCall.call(tm);
    }

    public void setContentView(@LayoutRes final int layoutResID) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_setContentView(layoutResID);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid1<Integer> superCall = new CallVoid1<Integer>("setContentView(Integer)") {

            @Override
            public void call(final Integer layoutResID) {
                if (iterator.hasPrevious()) {
                    iterator.previous().setContentView(this, layoutResID);
                } else {
                    getOriginal().super_setContentView(layoutResID);
                }
            }
        };
        superCall.call(layoutResID);
    }

    public void setContentView(final View view) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_setContentView(view);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid1<View> superCall = new CallVoid1<View>("setContentView(View)") {

            @Override
            public void call(final View view) {
                if (iterator.hasPrevious()) {
                    iterator.previous().setContentView(this, view);
                } else {
                    getOriginal().super_setContentView(view);
                }
            }
        };
        superCall.call(view);
    }

    public void setContentView(final View view, final ViewGroup.LayoutParams params) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_setContentView(view, params);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid2<View, ViewGroup.LayoutParams> superCall
                = new CallVoid2<View, ViewGroup.LayoutParams>(
                "setContentView(View, ViewGroup.LayoutParams)") {

            @Override
            public void call(final View view, final ViewGroup.LayoutParams params) {
                if (iterator.hasPrevious()) {
                    iterator.previous().setContentView(this, view, params);
                } else {
                    getOriginal().super_setContentView(view, params);
                }
            }
        };
        superCall.call(view, params);
    }

    public void setEnterSharedElementCallback(final SharedElementCallback callback) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_setEnterSharedElementCallback(callback);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid1<SharedElementCallback> superCall = new CallVoid1<SharedElementCallback>(
                "setEnterSharedElementCallback(SharedElementCallback)") {

            @Override
            public void call(final SharedElementCallback callback) {
                if (iterator.hasPrevious()) {
                    iterator.previous().setEnterSharedElementCallback(this, callback);
                } else {
                    getOriginal().super_setEnterSharedElementCallback(callback);
                }
            }
        };
        superCall.call(callback);
    }

    public void setEnterSharedElementCallback(final android.app.SharedElementCallback callback) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_setEnterSharedElementCallback(callback);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid1<android.app.SharedElementCallback> superCall
                = new CallVoid1<android.app.SharedElementCallback>(
                "setEnterSharedElementCallback(android.app.SharedElementCallback)") {

            @Override
            public void call(final android.app.SharedElementCallback callback) {
                if (iterator.hasPrevious()) {
                    iterator.previous().setEnterSharedElementCallback(this, callback);
                } else {
                    getOriginal().super_setEnterSharedElementCallback(callback);
                }
            }
        };
        superCall.call(callback);
    }

    public void setExitSharedElementCallback(final SharedElementCallback listener) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_setExitSharedElementCallback(listener);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid1<SharedElementCallback> superCall = new CallVoid1<SharedElementCallback>(
                "setExitSharedElementCallback(SharedElementCallback)") {

            @Override
            public void call(final SharedElementCallback listener) {
                if (iterator.hasPrevious()) {
                    iterator.previous().setExitSharedElementCallback(this, listener);
                } else {
                    getOriginal().super_setExitSharedElementCallback(listener);
                }
            }
        };
        superCall.call(listener);
    }

    public void setExitSharedElementCallback(final android.app.SharedElementCallback callback) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_setExitSharedElementCallback(callback);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid1<android.app.SharedElementCallback> superCall
                = new CallVoid1<android.app.SharedElementCallback>(
                "setExitSharedElementCallback(android.app.SharedElementCallback)") {

            @Override
            public void call(final android.app.SharedElementCallback callback) {
                if (iterator.hasPrevious()) {
                    iterator.previous().setExitSharedElementCallback(this, callback);
                } else {
                    getOriginal().super_setExitSharedElementCallback(callback);
                }
            }
        };
        superCall.call(callback);
    }

    public void setFinishOnTouchOutside(final boolean finish) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_setFinishOnTouchOutside(finish);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid1<Boolean> superCall = new CallVoid1<Boolean>(
                "setFinishOnTouchOutside(Boolean)") {

            @Override
            public void call(final Boolean finish) {
                if (iterator.hasPrevious()) {
                    iterator.previous().setFinishOnTouchOutside(this, finish);
                } else {
                    getOriginal().super_setFinishOnTouchOutside(finish);
                }
            }
        };
        superCall.call(finish);
    }

    public void setImmersive(final boolean i) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_setImmersive(i);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid1<Boolean> superCall = new CallVoid1<Boolean>("setImmersive(Boolean)") {

            @Override
            public void call(final Boolean i) {
                if (iterator.hasPrevious()) {
                    iterator.previous().setImmersive(this, i);
                } else {
                    getOriginal().super_setImmersive(i);
                }
            }
        };
        superCall.call(i);
    }

    public void setIntent(final Intent newIntent) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_setIntent(newIntent);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid1<Intent> superCall = new CallVoid1<Intent>("setIntent(Intent)") {

            @Override
            public void call(final Intent newIntent) {
                if (iterator.hasPrevious()) {
                    iterator.previous().setIntent(this, newIntent);
                } else {
                    getOriginal().super_setIntent(newIntent);
                }
            }
        };
        superCall.call(newIntent);
    }

    public void setRequestedOrientation(final int requestedOrientation) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_setRequestedOrientation(requestedOrientation);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid1<Integer> superCall = new CallVoid1<Integer>(
                "setRequestedOrientation(Integer)") {

            @Override
            public void call(final Integer requestedOrientation) {
                if (iterator.hasPrevious()) {
                    iterator.previous().setRequestedOrientation(this, requestedOrientation);
                } else {
                    getOriginal().super_setRequestedOrientation(requestedOrientation);
                }
            }
        };
        superCall.call(requestedOrientation);
    }

    public void setSupportActionBar(@Nullable final android.support.v7.widget.Toolbar toolbar) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_setSupportActionBar(toolbar);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid1<android.support.v7.widget.Toolbar> superCall
                = new CallVoid1<android.support.v7.widget.Toolbar>(
                "setSupportActionBar(android.support.v7.widget.Toolbar)") {

            @Override
            public void call(final android.support.v7.widget.Toolbar toolbar) {
                if (iterator.hasPrevious()) {
                    iterator.previous().setSupportActionBar(this, toolbar);
                } else {
                    getOriginal().super_setSupportActionBar(toolbar);
                }
            }
        };
        superCall.call(toolbar);
    }

    public void setSupportProgress(final int progress) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_setSupportProgress(progress);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid1<Integer> superCall = new CallVoid1<Integer>("setSupportProgress(Integer)") {

            @Override
            public void call(final Integer progress) {
                if (iterator.hasPrevious()) {
                    iterator.previous().setSupportProgress(this, progress);
                } else {
                    getOriginal().super_setSupportProgress(progress);
                }
            }
        };
        superCall.call(progress);
    }

    public void setSupportProgressBarIndeterminate(final boolean indeterminate) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_setSupportProgressBarIndeterminate(indeterminate);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid1<Boolean> superCall = new CallVoid1<Boolean>(
                "setSupportProgressBarIndeterminate(Boolean)") {

            @Override
            public void call(final Boolean indeterminate) {
                if (iterator.hasPrevious()) {
                    iterator.previous().setSupportProgressBarIndeterminate(this, indeterminate);
                } else {
                    getOriginal().super_setSupportProgressBarIndeterminate(indeterminate);
                }
            }
        };
        superCall.call(indeterminate);
    }

    public void setSupportProgressBarIndeterminateVisibility(final boolean visible) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_setSupportProgressBarIndeterminateVisibility(visible);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid1<Boolean> superCall = new CallVoid1<Boolean>(
                "setSupportProgressBarIndeterminateVisibility(Boolean)") {

            @Override
            public void call(final Boolean visible) {
                if (iterator.hasPrevious()) {
                    iterator.previous().setSupportProgressBarIndeterminateVisibility(this, visible);
                } else {
                    getOriginal().super_setSupportProgressBarIndeterminateVisibility(visible);
                }
            }
        };
        superCall.call(visible);
    }

    public void setSupportProgressBarVisibility(final boolean visible) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_setSupportProgressBarVisibility(visible);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid1<Boolean> superCall = new CallVoid1<Boolean>(
                "setSupportProgressBarVisibility(Boolean)") {

            @Override
            public void call(final Boolean visible) {
                if (iterator.hasPrevious()) {
                    iterator.previous().setSupportProgressBarVisibility(this, visible);
                } else {
                    getOriginal().super_setSupportProgressBarVisibility(visible);
                }
            }
        };
        superCall.call(visible);
    }

    public void setTaskDescription(final ActivityManager.TaskDescription taskDescription) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_setTaskDescription(taskDescription);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid1<ActivityManager.TaskDescription> superCall
                = new CallVoid1<ActivityManager.TaskDescription>(
                "setTaskDescription(ActivityManager.TaskDescription)") {

            @Override
            public void call(final ActivityManager.TaskDescription taskDescription) {
                if (iterator.hasPrevious()) {
                    iterator.previous().setTaskDescription(this, taskDescription);
                } else {
                    getOriginal().super_setTaskDescription(taskDescription);
                }
            }
        };
        superCall.call(taskDescription);
    }

    public void setTheme(@StyleRes final int resid) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_setTheme(resid);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid1<Integer> superCall = new CallVoid1<Integer>("setTheme(Integer)") {

            @Override
            public void call(final Integer resid) {
                if (iterator.hasPrevious()) {
                    iterator.previous().setTheme(this, resid);
                } else {
                    getOriginal().super_setTheme(resid);
                }
            }
        };
        superCall.call(resid);
    }

    public void setTitle(final CharSequence title) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_setTitle(title);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid1<CharSequence> superCall = new CallVoid1<CharSequence>(
                "setTitle(CharSequence)") {

            @Override
            public void call(final CharSequence title) {
                if (iterator.hasPrevious()) {
                    iterator.previous().setTitle(this, title);
                } else {
                    getOriginal().super_setTitle(title);
                }
            }
        };
        superCall.call(title);
    }

    public void setTitle(final int titleId) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_setTitle(titleId);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid1<Integer> superCall = new CallVoid1<Integer>("setTitle(Integer)") {

            @Override
            public void call(final Integer titleId) {
                if (iterator.hasPrevious()) {
                    iterator.previous().setTitle(this, titleId);
                } else {
                    getOriginal().super_setTitle(titleId);
                }
            }
        };
        superCall.call(titleId);
    }

    public void setTitleColor(final int textColor) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_setTitleColor(textColor);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid1<Integer> superCall = new CallVoid1<Integer>("setTitleColor(Integer)") {

            @Override
            public void call(final Integer textColor) {
                if (iterator.hasPrevious()) {
                    iterator.previous().setTitleColor(this, textColor);
                } else {
                    getOriginal().super_setTitleColor(textColor);
                }
            }
        };
        superCall.call(textColor);
    }

    public void setVisible(final boolean visible) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_setVisible(visible);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid1<Boolean> superCall = new CallVoid1<Boolean>("setVisible(Boolean)") {

            @Override
            public void call(final Boolean visible) {
                if (iterator.hasPrevious()) {
                    iterator.previous().setVisible(this, visible);
                } else {
                    getOriginal().super_setVisible(visible);
                }
            }
        };
        superCall.call(visible);
    }

    public void setWallpaper(final Bitmap bitmap) throws IOException {
        if (mPlugins.isEmpty()) {
            try {
                getOriginal().super_setWallpaper(bitmap);
            } catch (IOException e) {
                throw new SuppressedException(e);
            }
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid1<Bitmap> superCall = new CallVoid1<Bitmap>("setWallpaper(Bitmap)") {

            @Override
            public void call(final Bitmap bitmap) {
                if (iterator.hasPrevious()) {
                    try {
                        iterator.previous().setWallpaper(this, bitmap);
                    } catch (IOException e) {
                        throw new SuppressedException(e);
                    }
                } else {
                    try {
                        getOriginal().super_setWallpaper(bitmap);
                    } catch (IOException e) {
                        throw new SuppressedException(e);
                    }
                }
            }
        };
        superCall.call(bitmap);
    }

    public void setWallpaper(final InputStream data) throws IOException {
        if (mPlugins.isEmpty()) {
            try {
                getOriginal().super_setWallpaper(data);
            } catch (IOException e) {
                throw new SuppressedException(e);
            }
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid1<InputStream> superCall = new CallVoid1<InputStream>(
                "setWallpaper(InputStream)") {

            @Override
            public void call(final InputStream data) {
                if (iterator.hasPrevious()) {
                    try {
                        iterator.previous().setWallpaper(this, data);
                    } catch (IOException e) {
                        throw new SuppressedException(e);
                    }
                } else {
                    try {
                        getOriginal().super_setWallpaper(data);
                    } catch (IOException e) {
                        throw new SuppressedException(e);
                    }
                }
            }
        };
        superCall.call(data);
    }

    public boolean shouldShowRequestPermissionRationale(final String permission) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_shouldShowRequestPermissionRationale(permission);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun1<Boolean, String> superCall = new CallFun1<Boolean, String>(
                "shouldShowRequestPermissionRationale(String)") {

            @Override
            public Boolean call(final String permission) {
                if (iterator.hasPrevious()) {
                    return iterator.previous()
                            .shouldShowRequestPermissionRationale(this, permission);
                } else {
                    return getOriginal().super_shouldShowRequestPermissionRationale(permission);
                }
            }
        };
        return superCall.call(permission);
    }

    public boolean shouldUpRecreateTask(final Intent targetIntent) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_shouldUpRecreateTask(targetIntent);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun1<Boolean, Intent> superCall = new CallFun1<Boolean, Intent>(
                "shouldUpRecreateTask(Intent)") {

            @Override
            public Boolean call(final Intent targetIntent) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().shouldUpRecreateTask(this, targetIntent);
                } else {
                    return getOriginal().super_shouldUpRecreateTask(targetIntent);
                }
            }
        };
        return superCall.call(targetIntent);
    }

    public boolean showAssist(final Bundle args) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_showAssist(args);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun1<Boolean, Bundle> superCall = new CallFun1<Boolean, Bundle>(
                "showAssist(Bundle)") {

            @Override
            public Boolean call(final Bundle args) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().showAssist(this, args);
                } else {
                    return getOriginal().super_showAssist(args);
                }
            }
        };
        return superCall.call(args);
    }

    public void showLockTaskEscapeMessage() {
        if (mPlugins.isEmpty()) {
            getOriginal().super_showLockTaskEscapeMessage();
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid0 superCall = new CallVoid0("showLockTaskEscapeMessage()") {

            @Override
            public void call() {
                if (iterator.hasPrevious()) {
                    iterator.previous().showLockTaskEscapeMessage(this);
                } else {
                    getOriginal().super_showLockTaskEscapeMessage();
                }
            }
        };
        superCall.call();
    }

    public android.view.ActionMode startActionMode(
            final android.view.ActionMode.Callback callback) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_startActionMode(callback);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun1<android.view.ActionMode, android.view.ActionMode.Callback> superCall
                = new CallFun1<android.view.ActionMode, android.view.ActionMode.Callback>(
                "startActionMode(android.view.ActionMode.Callback)") {

            @Override
            public android.view.ActionMode call(final android.view.ActionMode.Callback callback) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().startActionMode(this, callback);
                } else {
                    return getOriginal().super_startActionMode(callback);
                }
            }
        };
        return superCall.call(callback);
    }

    public android.view.ActionMode startActionMode(final android.view.ActionMode.Callback callback,
            final int type) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_startActionMode(callback, type);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun2<android.view.ActionMode, android.view.ActionMode.Callback, Integer> superCall
                = new CallFun2<android.view.ActionMode, android.view.ActionMode.Callback, Integer>(
                "startActionMode(android.view.ActionMode.Callback, Integer)") {

            @Override
            public android.view.ActionMode call(final android.view.ActionMode.Callback callback,
                    final Integer type) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().startActionMode(this, callback, type);
                } else {
                    return getOriginal().super_startActionMode(callback, type);
                }
            }
        };
        return superCall.call(callback, type);
    }

    public void startActivities(final Intent[] intents) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_startActivities(intents);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid1<Intent[]> superCall = new CallVoid1<Intent[]>("startActivities(Intent[])") {

            @Override
            public void call(final Intent[] intents) {
                if (iterator.hasPrevious()) {
                    iterator.previous().startActivities(this, intents);
                } else {
                    getOriginal().super_startActivities(intents);
                }
            }
        };
        superCall.call(intents);
    }

    public void startActivities(final Intent[] intents, final Bundle options) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_startActivities(intents, options);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid2<Intent[], Bundle> superCall = new CallVoid2<Intent[], Bundle>(
                "startActivities(Intent[], Bundle)") {

            @Override
            public void call(final Intent[] intents, final Bundle options) {
                if (iterator.hasPrevious()) {
                    iterator.previous().startActivities(this, intents, options);
                } else {
                    getOriginal().super_startActivities(intents, options);
                }
            }
        };
        superCall.call(intents, options);
    }

    public void startActivity(final Intent intent) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_startActivity(intent);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid1<Intent> superCall = new CallVoid1<Intent>("startActivity(Intent)") {

            @Override
            public void call(final Intent intent) {
                if (iterator.hasPrevious()) {
                    iterator.previous().startActivity(this, intent);
                } else {
                    getOriginal().super_startActivity(intent);
                }
            }
        };
        superCall.call(intent);
    }

    public void startActivity(final Intent intent, final Bundle options) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_startActivity(intent, options);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid2<Intent, Bundle> superCall = new CallVoid2<Intent, Bundle>(
                "startActivity(Intent, Bundle)") {

            @Override
            public void call(final Intent intent, final Bundle options) {
                if (iterator.hasPrevious()) {
                    iterator.previous().startActivity(this, intent, options);
                } else {
                    getOriginal().super_startActivity(intent, options);
                }
            }
        };
        superCall.call(intent, options);
    }

    public void startActivityForResult(final Intent intent, final int requestCode) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_startActivityForResult(intent, requestCode);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid2<Intent, Integer> superCall = new CallVoid2<Intent, Integer>(
                "startActivityForResult(Intent, Integer)") {

            @Override
            public void call(final Intent intent, final Integer requestCode) {
                if (iterator.hasPrevious()) {
                    iterator.previous().startActivityForResult(this, intent, requestCode);
                } else {
                    getOriginal().super_startActivityForResult(intent, requestCode);
                }
            }
        };
        superCall.call(intent, requestCode);
    }

    public void startActivityForResult(final Intent intent, final int requestCode,
            final Bundle options) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_startActivityForResult(intent, requestCode, options);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid3<Intent, Integer, Bundle> superCall = new CallVoid3<Intent, Integer, Bundle>(
                "startActivityForResult(Intent, Integer, Bundle)") {

            @Override
            public void call(final Intent intent, final Integer requestCode, final Bundle options) {
                if (iterator.hasPrevious()) {
                    iterator.previous().startActivityForResult(this, intent, requestCode, options);
                } else {
                    getOriginal().super_startActivityForResult(intent, requestCode, options);
                }
            }
        };
        superCall.call(intent, requestCode, options);
    }

    public void startActivityFromChild(final Activity child, final Intent intent,
            final int requestCode) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_startActivityFromChild(child, intent, requestCode);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid3<Activity, Intent, Integer> superCall
                = new CallVoid3<Activity, Intent, Integer>(
                "startActivityFromChild(Activity, Intent, Integer)") {

            @Override
            public void call(final Activity child, final Intent intent, final Integer requestCode) {
                if (iterator.hasPrevious()) {
                    iterator.previous().startActivityFromChild(this, child, intent, requestCode);
                } else {
                    getOriginal().super_startActivityFromChild(child, intent, requestCode);
                }
            }
        };
        superCall.call(child, intent, requestCode);
    }

    public void startActivityFromChild(final Activity child, final Intent intent,
            final int requestCode, final Bundle options) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_startActivityFromChild(child, intent, requestCode, options);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid4<Activity, Intent, Integer, Bundle> superCall
                = new CallVoid4<Activity, Intent, Integer, Bundle>(
                "startActivityFromChild(Activity, Intent, Integer, Bundle)") {

            @Override
            public void call(final Activity child, final Intent intent, final Integer requestCode,
                    final Bundle options) {
                if (iterator.hasPrevious()) {
                    iterator.previous()
                            .startActivityFromChild(this, child, intent, requestCode, options);
                } else {
                    getOriginal().super_startActivityFromChild(child, intent, requestCode, options);
                }
            }
        };
        superCall.call(child, intent, requestCode, options);
    }

    public void startActivityFromFragment(final Fragment fragment, final Intent intent,
            final int requestCode) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_startActivityFromFragment(fragment, intent, requestCode);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid3<Fragment, Intent, Integer> superCall
                = new CallVoid3<Fragment, Intent, Integer>(
                "startActivityFromFragment(Fragment, Intent, Integer)") {

            @Override
            public void call(final Fragment fragment, final Intent intent,
                    final Integer requestCode) {
                if (iterator.hasPrevious()) {
                    iterator.previous()
                            .startActivityFromFragment(this, fragment, intent, requestCode);
                } else {
                    getOriginal().super_startActivityFromFragment(fragment, intent, requestCode);
                }
            }
        };
        superCall.call(fragment, intent, requestCode);
    }

    public void startActivityFromFragment(final Fragment fragment, final Intent intent,
            final int requestCode, @Nullable final Bundle options) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_startActivityFromFragment(fragment, intent, requestCode, options);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid4<Fragment, Intent, Integer, Bundle> superCall
                = new CallVoid4<Fragment, Intent, Integer, Bundle>(
                "startActivityFromFragment(Fragment, Intent, Integer, Bundle)") {

            @Override
            public void call(final Fragment fragment, final Intent intent,
                    final Integer requestCode, final Bundle options) {
                if (iterator.hasPrevious()) {
                    iterator.previous()
                            .startActivityFromFragment(this, fragment, intent, requestCode,
                                    options);
                } else {
                    getOriginal().super_startActivityFromFragment(fragment, intent, requestCode,
                            options);
                }
            }
        };
        superCall.call(fragment, intent, requestCode, options);
    }

    public void startActivityFromFragment(final android.app.Fragment fragment, final Intent intent,
            final int requestCode) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_startActivityFromFragment(fragment, intent, requestCode);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid3<android.app.Fragment, Intent, Integer> superCall
                = new CallVoid3<android.app.Fragment, Intent, Integer>(
                "startActivityFromFragment(android.app.Fragment, Intent, Integer)") {

            @Override
            public void call(final android.app.Fragment fragment, final Intent intent,
                    final Integer requestCode) {
                if (iterator.hasPrevious()) {
                    iterator.previous()
                            .startActivityFromFragment(this, fragment, intent, requestCode);
                } else {
                    getOriginal().super_startActivityFromFragment(fragment, intent, requestCode);
                }
            }
        };
        superCall.call(fragment, intent, requestCode);
    }

    public void startActivityFromFragment(final android.app.Fragment fragment, final Intent intent,
            final int requestCode, final Bundle options) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_startActivityFromFragment(fragment, intent, requestCode, options);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid4<android.app.Fragment, Intent, Integer, Bundle> superCall
                = new CallVoid4<android.app.Fragment, Intent, Integer, Bundle>(
                "startActivityFromFragment(android.app.Fragment, Intent, Integer, Bundle)") {

            @Override
            public void call(final android.app.Fragment fragment, final Intent intent,
                    final Integer requestCode, final Bundle options) {
                if (iterator.hasPrevious()) {
                    iterator.previous()
                            .startActivityFromFragment(this, fragment, intent, requestCode,
                                    options);
                } else {
                    getOriginal().super_startActivityFromFragment(fragment, intent, requestCode,
                            options);
                }
            }
        };
        superCall.call(fragment, intent, requestCode, options);
    }

    public boolean startActivityIfNeeded(final Intent intent, final int requestCode) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_startActivityIfNeeded(intent, requestCode);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun2<Boolean, Intent, Integer> superCall = new CallFun2<Boolean, Intent, Integer>(
                "startActivityIfNeeded(Intent, Integer)") {

            @Override
            public Boolean call(final Intent intent, final Integer requestCode) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().startActivityIfNeeded(this, intent, requestCode);
                } else {
                    return getOriginal().super_startActivityIfNeeded(intent, requestCode);
                }
            }
        };
        return superCall.call(intent, requestCode);
    }

    public boolean startActivityIfNeeded(final Intent intent, final int requestCode,
            final Bundle options) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_startActivityIfNeeded(intent, requestCode, options);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun3<Boolean, Intent, Integer, Bundle> superCall
                = new CallFun3<Boolean, Intent, Integer, Bundle>(
                "startActivityIfNeeded(Intent, Integer, Bundle)") {

            @Override
            public Boolean call(final Intent intent, final Integer requestCode,
                    final Bundle options) {
                if (iterator.hasPrevious()) {
                    return iterator.previous()
                            .startActivityIfNeeded(this, intent, requestCode, options);
                } else {
                    return getOriginal().super_startActivityIfNeeded(intent, requestCode, options);
                }
            }
        };
        return superCall.call(intent, requestCode, options);
    }

    public boolean startInstrumentation(final ComponentName className, final String profileFile,
            final Bundle arguments) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_startInstrumentation(className, profileFile, arguments);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun3<Boolean, ComponentName, String, Bundle> superCall
                = new CallFun3<Boolean, ComponentName, String, Bundle>(
                "startInstrumentation(ComponentName, String, Bundle)") {

            @Override
            public Boolean call(final ComponentName className, final String profileFile,
                    final Bundle arguments) {
                if (iterator.hasPrevious()) {
                    return iterator.previous()
                            .startInstrumentation(this, className, profileFile, arguments);
                } else {
                    return getOriginal()
                            .super_startInstrumentation(className, profileFile, arguments);
                }
            }
        };
        return superCall.call(className, profileFile, arguments);
    }

    public void startIntentSender(final IntentSender intent, final Intent fillInIntent,
            final int flagsMask, final int flagsValues, final int extraFlags)
            throws IntentSender.SendIntentException {
        if (mPlugins.isEmpty()) {
            try {
                getOriginal().super_startIntentSender(intent, fillInIntent, flagsMask, flagsValues,
                        extraFlags);
            } catch (IntentSender.SendIntentException e) {
                throw new SuppressedException(e);
            }
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid5<IntentSender, Intent, Integer, Integer, Integer> superCall
                = new CallVoid5<IntentSender, Intent, Integer, Integer, Integer>(
                "startIntentSender(IntentSender, Intent, Integer, Integer, Integer)") {

            @Override
            public void call(final IntentSender intent, final Intent fillInIntent,
                    final Integer flagsMask, final Integer flagsValues, final Integer extraFlags) {
                if (iterator.hasPrevious()) {
                    try {
                        iterator.previous().startIntentSender(this, intent, fillInIntent, flagsMask,
                                flagsValues, extraFlags);
                    } catch (IntentSender.SendIntentException e) {
                        throw new SuppressedException(e);
                    }
                } else {
                    try {
                        getOriginal().super_startIntentSender(intent, fillInIntent, flagsMask,
                                flagsValues, extraFlags);
                    } catch (IntentSender.SendIntentException e) {
                        throw new SuppressedException(e);
                    }
                }
            }
        };
        superCall.call(intent, fillInIntent, flagsMask, flagsValues, extraFlags);
    }

    public void startIntentSender(final IntentSender intent, final Intent fillInIntent,
            final int flagsMask, final int flagsValues, final int extraFlags, final Bundle options)
            throws IntentSender.SendIntentException {
        if (mPlugins.isEmpty()) {
            try {
                getOriginal().super_startIntentSender(intent, fillInIntent, flagsMask, flagsValues,
                        extraFlags, options);
            } catch (IntentSender.SendIntentException e) {
                throw new SuppressedException(e);
            }
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid6<IntentSender, Intent, Integer, Integer, Integer, Bundle> superCall
                = new CallVoid6<IntentSender, Intent, Integer, Integer, Integer, Bundle>(
                "startIntentSender(IntentSender, Intent, Integer, Integer, Integer, Bundle)") {

            @Override
            public void call(final IntentSender intent, final Intent fillInIntent,
                    final Integer flagsMask, final Integer flagsValues, final Integer extraFlags,
                    final Bundle options) {
                if (iterator.hasPrevious()) {
                    try {
                        iterator.previous().startIntentSender(this, intent, fillInIntent, flagsMask,
                                flagsValues, extraFlags, options);
                    } catch (IntentSender.SendIntentException e) {
                        throw new SuppressedException(e);
                    }
                } else {
                    try {
                        getOriginal().super_startIntentSender(intent, fillInIntent, flagsMask,
                                flagsValues, extraFlags, options);
                    } catch (IntentSender.SendIntentException e) {
                        throw new SuppressedException(e);
                    }
                }
            }
        };
        superCall.call(intent, fillInIntent, flagsMask, flagsValues, extraFlags, options);
    }

    public void startIntentSenderForResult(final IntentSender intent, final int requestCode,
            final Intent fillInIntent, final int flagsMask, final int flagsValues,
            final int extraFlags) throws IntentSender.SendIntentException {
        if (mPlugins.isEmpty()) {
            try {
                getOriginal().super_startIntentSenderForResult(intent, requestCode, fillInIntent,
                        flagsMask, flagsValues, extraFlags);
            } catch (IntentSender.SendIntentException e) {
                throw new SuppressedException(e);
            }
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid6<IntentSender, Integer, Intent, Integer, Integer, Integer> superCall
                = new CallVoid6<IntentSender, Integer, Intent, Integer, Integer, Integer>(
                "startIntentSenderForResult(IntentSender, Integer, Intent, Integer, Integer, Integer)") {

            @Override
            public void call(final IntentSender intent, final Integer requestCode,
                    final Intent fillInIntent, final Integer flagsMask, final Integer flagsValues,
                    final Integer extraFlags) {
                if (iterator.hasPrevious()) {
                    try {
                        iterator.previous()
                                .startIntentSenderForResult(this, intent, requestCode, fillInIntent,
                                        flagsMask, flagsValues, extraFlags);
                    } catch (IntentSender.SendIntentException e) {
                        throw new SuppressedException(e);
                    }
                } else {
                    try {
                        getOriginal()
                                .super_startIntentSenderForResult(intent, requestCode, fillInIntent,
                                        flagsMask, flagsValues, extraFlags);
                    } catch (IntentSender.SendIntentException e) {
                        throw new SuppressedException(e);
                    }
                }
            }
        };
        superCall.call(intent, requestCode, fillInIntent, flagsMask, flagsValues, extraFlags);
    }

    public void startIntentSenderForResult(final IntentSender intent, final int requestCode,
            final Intent fillInIntent, final int flagsMask, final int flagsValues,
            final int extraFlags, final Bundle options) throws IntentSender.SendIntentException {
        if (mPlugins.isEmpty()) {
            try {
                getOriginal().super_startIntentSenderForResult(intent, requestCode, fillInIntent,
                        flagsMask, flagsValues, extraFlags, options);
            } catch (IntentSender.SendIntentException e) {
                throw new SuppressedException(e);
            }
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid7<IntentSender, Integer, Intent, Integer, Integer, Integer, Bundle> superCall
                = new CallVoid7<IntentSender, Integer, Intent, Integer, Integer, Integer, Bundle>(
                "startIntentSenderForResult(IntentSender, Integer, Intent, Integer, Integer, Integer, Bundle)") {

            @Override
            public void call(final IntentSender intent, final Integer requestCode,
                    final Intent fillInIntent, final Integer flagsMask, final Integer flagsValues,
                    final Integer extraFlags, final Bundle options) {
                if (iterator.hasPrevious()) {
                    try {
                        iterator.previous()
                                .startIntentSenderForResult(this, intent, requestCode, fillInIntent,
                                        flagsMask, flagsValues, extraFlags, options);
                    } catch (IntentSender.SendIntentException e) {
                        throw new SuppressedException(e);
                    }
                } else {
                    try {
                        getOriginal()
                                .super_startIntentSenderForResult(intent, requestCode, fillInIntent,
                                        flagsMask, flagsValues, extraFlags, options);
                    } catch (IntentSender.SendIntentException e) {
                        throw new SuppressedException(e);
                    }
                }
            }
        };
        superCall.call(intent, requestCode, fillInIntent, flagsMask, flagsValues, extraFlags,
                options);
    }

    public void startIntentSenderFromChild(final Activity child, final IntentSender intent,
            final int requestCode, final Intent fillInIntent, final int flagsMask,
            final int flagsValues, final int extraFlags) throws IntentSender.SendIntentException {
        if (mPlugins.isEmpty()) {
            try {
                getOriginal()
                        .super_startIntentSenderFromChild(child, intent, requestCode, fillInIntent,
                                flagsMask, flagsValues, extraFlags);
            } catch (IntentSender.SendIntentException e) {
                throw new SuppressedException(e);
            }
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid7<Activity, IntentSender, Integer, Intent, Integer, Integer, Integer>
                superCall
                = new CallVoid7<Activity, IntentSender, Integer, Intent, Integer, Integer, Integer>(
                "startIntentSenderFromChild(Activity, IntentSender, Integer, Intent, Integer, Integer, Integer)") {

            @Override
            public void call(final Activity child, final IntentSender intent,
                    final Integer requestCode, final Intent fillInIntent, final Integer flagsMask,
                    final Integer flagsValues, final Integer extraFlags) {
                if (iterator.hasPrevious()) {
                    try {
                        iterator.previous()
                                .startIntentSenderFromChild(this, child, intent, requestCode,
                                        fillInIntent, flagsMask, flagsValues, extraFlags);
                    } catch (IntentSender.SendIntentException e) {
                        throw new SuppressedException(e);
                    }
                } else {
                    try {
                        getOriginal().super_startIntentSenderFromChild(child, intent, requestCode,
                                fillInIntent, flagsMask, flagsValues, extraFlags);
                    } catch (IntentSender.SendIntentException e) {
                        throw new SuppressedException(e);
                    }
                }
            }
        };
        superCall
                .call(child, intent, requestCode, fillInIntent, flagsMask, flagsValues, extraFlags);
    }

    public void startIntentSenderFromChild(final Activity child, final IntentSender intent,
            final int requestCode, final Intent fillInIntent, final int flagsMask,
            final int flagsValues, final int extraFlags, final Bundle options)
            throws IntentSender.SendIntentException {
        if (mPlugins.isEmpty()) {
            try {
                getOriginal()
                        .super_startIntentSenderFromChild(child, intent, requestCode, fillInIntent,
                                flagsMask, flagsValues, extraFlags, options);
            } catch (IntentSender.SendIntentException e) {
                throw new SuppressedException(e);
            }
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid8<Activity, IntentSender, Integer, Intent, Integer, Integer, Integer, Bundle>
                superCall
                = new CallVoid8<Activity, IntentSender, Integer, Intent, Integer, Integer, Integer, Bundle>(
                "startIntentSenderFromChild(Activity, IntentSender, Integer, Intent, Integer, Integer, Integer, Bundle)") {

            @Override
            public void call(final Activity child, final IntentSender intent,
                    final Integer requestCode, final Intent fillInIntent, final Integer flagsMask,
                    final Integer flagsValues, final Integer extraFlags, final Bundle options) {
                if (iterator.hasPrevious()) {
                    try {
                        iterator.previous()
                                .startIntentSenderFromChild(this, child, intent, requestCode,
                                        fillInIntent, flagsMask, flagsValues, extraFlags, options);
                    } catch (IntentSender.SendIntentException e) {
                        throw new SuppressedException(e);
                    }
                } else {
                    try {
                        getOriginal().super_startIntentSenderFromChild(child, intent, requestCode,
                                fillInIntent, flagsMask, flagsValues, extraFlags, options);
                    } catch (IntentSender.SendIntentException e) {
                        throw new SuppressedException(e);
                    }
                }
            }
        };
        superCall.call(child, intent, requestCode, fillInIntent, flagsMask, flagsValues, extraFlags,
                options);
    }

    public void startLockTask() {
        if (mPlugins.isEmpty()) {
            getOriginal().super_startLockTask();
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid0 superCall = new CallVoid0("startLockTask()") {

            @Override
            public void call() {
                if (iterator.hasPrevious()) {
                    iterator.previous().startLockTask(this);
                } else {
                    getOriginal().super_startLockTask();
                }
            }
        };
        superCall.call();
    }

    public void startManagingCursor(final Cursor c) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_startManagingCursor(c);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid1<Cursor> superCall = new CallVoid1<Cursor>("startManagingCursor(Cursor)") {

            @Override
            public void call(final Cursor c) {
                if (iterator.hasPrevious()) {
                    iterator.previous().startManagingCursor(this, c);
                } else {
                    getOriginal().super_startManagingCursor(c);
                }
            }
        };
        superCall.call(c);
    }

    public boolean startNextMatchingActivity(final Intent intent) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_startNextMatchingActivity(intent);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun1<Boolean, Intent> superCall = new CallFun1<Boolean, Intent>(
                "startNextMatchingActivity(Intent)") {

            @Override
            public Boolean call(final Intent intent) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().startNextMatchingActivity(this, intent);
                } else {
                    return getOriginal().super_startNextMatchingActivity(intent);
                }
            }
        };
        return superCall.call(intent);
    }

    public boolean startNextMatchingActivity(final Intent intent, final Bundle options) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_startNextMatchingActivity(intent, options);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun2<Boolean, Intent, Bundle> superCall = new CallFun2<Boolean, Intent, Bundle>(
                "startNextMatchingActivity(Intent, Bundle)") {

            @Override
            public Boolean call(final Intent intent, final Bundle options) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().startNextMatchingActivity(this, intent, options);
                } else {
                    return getOriginal().super_startNextMatchingActivity(intent, options);
                }
            }
        };
        return superCall.call(intent, options);
    }

    public void startPostponedEnterTransition() {
        if (mPlugins.isEmpty()) {
            getOriginal().super_startPostponedEnterTransition();
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid0 superCall = new CallVoid0("startPostponedEnterTransition()") {

            @Override
            public void call() {
                if (iterator.hasPrevious()) {
                    iterator.previous().startPostponedEnterTransition(this);
                } else {
                    getOriginal().super_startPostponedEnterTransition();
                }
            }
        };
        superCall.call();
    }

    public void startSearch(final String initialQuery, final boolean selectInitialQuery,
            final Bundle appSearchData, final boolean globalSearch) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_startSearch(initialQuery, selectInitialQuery, appSearchData,
                    globalSearch);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid4<String, Boolean, Bundle, Boolean> superCall
                = new CallVoid4<String, Boolean, Bundle, Boolean>(
                "startSearch(String, Boolean, Bundle, Boolean)") {

            @Override
            public void call(final String initialQuery, final Boolean selectInitialQuery,
                    final Bundle appSearchData, final Boolean globalSearch) {
                if (iterator.hasPrevious()) {
                    iterator.previous()
                            .startSearch(this, initialQuery, selectInitialQuery, appSearchData,
                                    globalSearch);
                } else {
                    getOriginal().super_startSearch(initialQuery, selectInitialQuery, appSearchData,
                            globalSearch);
                }
            }
        };
        superCall.call(initialQuery, selectInitialQuery, appSearchData, globalSearch);
    }

    public ComponentName startService(final Intent service) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_startService(service);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun1<ComponentName, Intent> superCall = new CallFun1<ComponentName, Intent>(
                "startService(Intent)") {

            @Override
            public ComponentName call(final Intent service) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().startService(this, service);
                } else {
                    return getOriginal().super_startService(service);
                }
            }
        };
        return superCall.call(service);
    }

    public ActionMode startSupportActionMode(@NonNull final ActionMode.Callback callback) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_startSupportActionMode(callback);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun1<ActionMode, ActionMode.Callback> superCall
                = new CallFun1<ActionMode, ActionMode.Callback>(
                "startSupportActionMode(ActionMode.Callback)") {

            @Override
            public ActionMode call(final ActionMode.Callback callback) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().startSupportActionMode(this, callback);
                } else {
                    return getOriginal().super_startSupportActionMode(callback);
                }
            }
        };
        return superCall.call(callback);
    }

    public void stopLockTask() {
        if (mPlugins.isEmpty()) {
            getOriginal().super_stopLockTask();
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid0 superCall = new CallVoid0("stopLockTask()") {

            @Override
            public void call() {
                if (iterator.hasPrevious()) {
                    iterator.previous().stopLockTask(this);
                } else {
                    getOriginal().super_stopLockTask();
                }
            }
        };
        superCall.call();
    }

    public void stopManagingCursor(final Cursor c) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_stopManagingCursor(c);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid1<Cursor> superCall = new CallVoid1<Cursor>("stopManagingCursor(Cursor)") {

            @Override
            public void call(final Cursor c) {
                if (iterator.hasPrevious()) {
                    iterator.previous().stopManagingCursor(this, c);
                } else {
                    getOriginal().super_stopManagingCursor(c);
                }
            }
        };
        superCall.call(c);
    }

    public boolean stopService(final Intent name) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_stopService(name);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun1<Boolean, Intent> superCall = new CallFun1<Boolean, Intent>(
                "stopService(Intent)") {

            @Override
            public Boolean call(final Intent name) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().stopService(this, name);
                } else {
                    return getOriginal().super_stopService(name);
                }
            }
        };
        return superCall.call(name);
    }

    public void supportFinishAfterTransition() {
        if (mPlugins.isEmpty()) {
            getOriginal().super_supportFinishAfterTransition();
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid0 superCall = new CallVoid0("supportFinishAfterTransition()") {

            @Override
            public void call() {
                if (iterator.hasPrevious()) {
                    iterator.previous().supportFinishAfterTransition(this);
                } else {
                    getOriginal().super_supportFinishAfterTransition();
                }
            }
        };
        superCall.call();
    }

    public void supportInvalidateOptionsMenu() {
        if (mPlugins.isEmpty()) {
            getOriginal().super_supportInvalidateOptionsMenu();
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid0 superCall = new CallVoid0("supportInvalidateOptionsMenu()") {

            @Override
            public void call() {
                if (iterator.hasPrevious()) {
                    iterator.previous().supportInvalidateOptionsMenu(this);
                } else {
                    getOriginal().super_supportInvalidateOptionsMenu();
                }
            }
        };
        superCall.call();
    }

    public void supportNavigateUpTo(@NonNull final Intent upIntent) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_supportNavigateUpTo(upIntent);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid1<Intent> superCall = new CallVoid1<Intent>("supportNavigateUpTo(Intent)") {

            @Override
            public void call(final Intent upIntent) {
                if (iterator.hasPrevious()) {
                    iterator.previous().supportNavigateUpTo(this, upIntent);
                } else {
                    getOriginal().super_supportNavigateUpTo(upIntent);
                }
            }
        };
        superCall.call(upIntent);
    }

    public void supportPostponeEnterTransition() {
        if (mPlugins.isEmpty()) {
            getOriginal().super_supportPostponeEnterTransition();
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid0 superCall = new CallVoid0("supportPostponeEnterTransition()") {

            @Override
            public void call() {
                if (iterator.hasPrevious()) {
                    iterator.previous().supportPostponeEnterTransition(this);
                } else {
                    getOriginal().super_supportPostponeEnterTransition();
                }
            }
        };
        superCall.call();
    }

    public boolean supportRequestWindowFeature(final int featureId) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_supportRequestWindowFeature(featureId);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun1<Boolean, Integer> superCall = new CallFun1<Boolean, Integer>(
                "supportRequestWindowFeature(Integer)") {

            @Override
            public Boolean call(final Integer featureId) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().supportRequestWindowFeature(this, featureId);
                } else {
                    return getOriginal().super_supportRequestWindowFeature(featureId);
                }
            }
        };
        return superCall.call(featureId);
    }

    public boolean supportShouldUpRecreateTask(@NonNull final Intent targetIntent) {
        if (mPlugins.isEmpty()) {
            return getOriginal().super_supportShouldUpRecreateTask(targetIntent);
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallFun1<Boolean, Intent> superCall = new CallFun1<Boolean, Intent>(
                "supportShouldUpRecreateTask(Intent)") {

            @Override
            public Boolean call(final Intent targetIntent) {
                if (iterator.hasPrevious()) {
                    return iterator.previous().supportShouldUpRecreateTask(this, targetIntent);
                } else {
                    return getOriginal().super_supportShouldUpRecreateTask(targetIntent);
                }
            }
        };
        return superCall.call(targetIntent);
    }

    public void supportStartPostponedEnterTransition() {
        if (mPlugins.isEmpty()) {
            getOriginal().super_supportStartPostponedEnterTransition();
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid0 superCall = new CallVoid0("supportStartPostponedEnterTransition()") {

            @Override
            public void call() {
                if (iterator.hasPrevious()) {
                    iterator.previous().supportStartPostponedEnterTransition(this);
                } else {
                    getOriginal().super_supportStartPostponedEnterTransition();
                }
            }
        };
        superCall.call();
    }

    public void takeKeyEvents(final boolean get) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_takeKeyEvents(get);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid1<Boolean> superCall = new CallVoid1<Boolean>("takeKeyEvents(Boolean)") {

            @Override
            public void call(final Boolean get) {
                if (iterator.hasPrevious()) {
                    iterator.previous().takeKeyEvents(this, get);
                } else {
                    getOriginal().super_takeKeyEvents(get);
                }
            }
        };
        superCall.call(get);
    }

    public void triggerSearch(final String query, final Bundle appSearchData) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_triggerSearch(query, appSearchData);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid2<String, Bundle> superCall = new CallVoid2<String, Bundle>(
                "triggerSearch(String, Bundle)") {

            @Override
            public void call(final String query, final Bundle appSearchData) {
                if (iterator.hasPrevious()) {
                    iterator.previous().triggerSearch(this, query, appSearchData);
                } else {
                    getOriginal().super_triggerSearch(query, appSearchData);
                }
            }
        };
        superCall.call(query, appSearchData);
    }

    public void unbindService(final ServiceConnection conn) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_unbindService(conn);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid1<ServiceConnection> superCall = new CallVoid1<ServiceConnection>(
                "unbindService(ServiceConnection)") {

            @Override
            public void call(final ServiceConnection conn) {
                if (iterator.hasPrevious()) {
                    iterator.previous().unbindService(this, conn);
                } else {
                    getOriginal().super_unbindService(conn);
                }
            }
        };
        superCall.call(conn);
    }

    public void unregisterComponentCallbacks(final ComponentCallbacks callback) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_unregisterComponentCallbacks(callback);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid1<ComponentCallbacks> superCall = new CallVoid1<ComponentCallbacks>(
                "unregisterComponentCallbacks(ComponentCallbacks)") {

            @Override
            public void call(final ComponentCallbacks callback) {
                if (iterator.hasPrevious()) {
                    iterator.previous().unregisterComponentCallbacks(this, callback);
                } else {
                    getOriginal().super_unregisterComponentCallbacks(callback);
                }
            }
        };
        superCall.call(callback);
    }

    public void unregisterForContextMenu(final View view) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_unregisterForContextMenu(view);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid1<View> superCall = new CallVoid1<View>("unregisterForContextMenu(View)") {

            @Override
            public void call(final View view) {
                if (iterator.hasPrevious()) {
                    iterator.previous().unregisterForContextMenu(this, view);
                } else {
                    getOriginal().super_unregisterForContextMenu(view);
                }
            }
        };
        superCall.call(view);
    }

    public void unregisterReceiver(final BroadcastReceiver receiver) {
        if (mPlugins.isEmpty()) {
            getOriginal().super_unregisterReceiver(receiver);
            return;
        }

        final ListIterator<ActivityPlugin> iterator = mPlugins.listIterator(mPlugins.size());

        final CallVoid1<BroadcastReceiver> superCall = new CallVoid1<BroadcastReceiver>(
                "unregisterReceiver(BroadcastReceiver)") {

            @Override
            public void call(final BroadcastReceiver receiver) {
                if (iterator.hasPrevious()) {
                    iterator.previous().unregisterReceiver(this, receiver);
                } else {
                    getOriginal().super_unregisterReceiver(receiver);
                }
            }
        };
        superCall.call(receiver);
    }

    private List<ActivityPlugin> getImplementingPlugins(final String methodName,
            final Class<?>... parameterTypes) {
        synchronized (mPlugins) {
            final ArrayList<ActivityPlugin> implementingPlugins = new ArrayList<>();
            for (int i = 0; i < mPlugins.size(); i++) {
                final ActivityPlugin plugin = mPlugins.get(i);
                if (plugin.isMethodOverridden(methodName, parameterTypes)) {
                    implementingPlugins.add(plugin);
                }
            }
            return implementingPlugins;
        }
    }

}