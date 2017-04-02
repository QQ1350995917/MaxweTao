package org.maxwe.tao.android.main;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.maxwe.tao.android.Constants;
import org.maxwe.tao.android.R;
import org.maxwe.tao.android.SellerApplication;
import org.maxwe.tao.android.account.user.UserEntity;
import org.maxwe.tao.android.activity.BaseFragmentActivity;
import org.maxwe.tao.android.activity.ExistDialog;
import org.maxwe.tao.android.activity.LoginActivity;
import org.maxwe.tao.android.common.AuthorActivity;
import org.maxwe.tao.android.common.BrandActivity;
import org.maxwe.tao.android.index.IndexFragment;
import org.maxwe.tao.android.mine.MineFragment;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseFragmentActivity implements View.OnClickListener {

    public static final int REQUEST_CODE_PROXY = 0;
    public static final int REQUEST_CODE_CONVERT_LINK = 1;
    public static final int REQUEST_CODE_MODIFY_PASSWORD = 3;
    public static final int REQUEST_CODE_ACCESS_CHECK = 4;
    public static final int REQUEST_CODE_LOGIN_TIME_OUT = 5;
    private static final int CODE_REQUEST_AUTHOR = 6;
    private static final int CODE_REQUEST_BRAND = 7;

    private Fragment indexFragment;
    private Fragment linkFragment;
    private Fragment mineFragment;

    @ViewInject(R.id.rg_act_navigate)
    private RadioGroup rg_act_navigate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for (int index = 0; index < this.rg_act_navigate.getChildCount(); index++) {
            this.rg_act_navigate.getChildAt(index).setOnClickListener(this);
        }
        this.setCurrentFragment(R.id.rb_act_main_index);

        if (SellerApplication.currentUserEntity == null || SellerApplication.currentUserEntity.getActCode() == null) {
            Intent intent = new Intent(this, AccessActivity.class);
            this.startActivityForResult(intent, REQUEST_CODE_ACCESS_CHECK);
        } else {
            AuthorActivity.requestTaoLoginStatus(this, new AuthorActivity.TaoLoginStatusCallback() {
                @Override
                public void onNeedLoginCallback() {
                    Intent intent = new Intent(MainActivity.this, AuthorActivity.class);
                    intent.putExtra(AuthorActivity.KEY_INTENT_OF_STATE_CODE, 1234);
                    MainActivity.this.startActivityForResult(intent, MainActivity.this.CODE_REQUEST_AUTHOR);
                }

                @Override
                public void onNeedBrandCallback() {

                }

                @Override
                public void onNeedOkCallback() {

                }

                @Override
                public void onNeedErrorCallback() {

                }
            });
        }

        this.onCheckNewVersion();

//        getRunningActivityName();

//        Intent intent = new Intent();
//        intent.setAction("android.intent.action.VIEW");
//        String url = "taobao://s.click.taobao.com/LsreO4x";
//        Uri uri = Uri.parse(url);
//        intent.setData(uri);
//        startActivity(intent);

//        Intent intent = new Intent(Intent.ACTION_MAIN);
//        intent.addCategory(Intent.CATEGORY_LAUNCHER);
//        Uri uri = Uri.parse("taobao://s.click.taobao.com/LsreO4x"); // 商品地址
//        intent.setData(uri);
//        ComponentName cn = new ComponentName("com.taobao.taobao", "com.taobao.tao.welcome.Welcome");
//        intent.setComponent(cn);
//        startActivity(intent);

//        getRunningActivityName();

//        doStartApplicationWithPackageName("com.taobao.taobao");


//        Intent intent = new Intent();
//        intent.setAction("Android.intent.action.VIEW");
//        Uri uri = Uri.parse("taobao://s.click.taobao.com/LsreO4x"); // 商品地址
//        intent.setData(uri);
//        intent.setClassName("com.taobao.taobao", "com.taobao.tao.detail.activity.DetailActivity");
//        startActivity(intent);


    }

    @Override
    public void onClick(View v) {
        if (v instanceof RadioButton) {
            this.setCurrentFragment(v.getId());
        }
    }

    private void setCurrentFragment(int index) {
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        this.hideAllFragments(fragmentTransaction);
        switch (index) {
            case R.id.rb_act_main_index:
                if (this.indexFragment == null) {
                    this.indexFragment = new IndexFragment();
                    fragmentTransaction.add(R.id.fl_act_content, this.indexFragment);
                } else {
                    fragmentTransaction.show(this.indexFragment);
                }
                break;
            case R.id.rb_act_main_convert_link:
                if (this.linkFragment == null) {
                    this.linkFragment = new LinkFragment();
                    fragmentTransaction.add(R.id.fl_act_content, this.linkFragment);
                } else {
                    fragmentTransaction.show(this.linkFragment);
                }
                break;
            case R.id.rb_act_main_mine:
                if (this.mineFragment == null) {
                    this.mineFragment = new MineFragment();
                    fragmentTransaction.add(R.id.fl_act_content, this.mineFragment);
                } else {
                    fragmentTransaction.show(this.mineFragment);
                }
                break;
            default:
                break;
        }
        fragmentTransaction.commit();
    }

    private void hideAllFragments(FragmentTransaction fragmentTransaction) {
        if (this.indexFragment != null) {
            fragmentTransaction.hide(this.indexFragment);
        }
        if (this.linkFragment != null) {
            fragmentTransaction.hide(this.linkFragment);
        }
        if (this.mineFragment != null) {
            fragmentTransaction.hide(this.mineFragment);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_PROXY:
                if (resultCode == LoginActivity.RESPONSE_CODE_SUCCESS) {
                }
                break;
            case REQUEST_CODE_CONVERT_LINK:
                if (resultCode == LoginActivity.RESPONSE_CODE_SUCCESS) {
                    onConvertLinkSuccessCallback();
                }
                break;

            case REQUEST_CODE_ACCESS_CHECK:
                if (resultCode == LoginActivity.RESPONSE_CODE_SUCCESS) {
                    SellerApplication.currentUserEntity = (UserEntity) data.getSerializableExtra(Constants.KEY_INTENT_SESSION);
                } else {
                    this.finish();
                }
                break;

            case REQUEST_CODE_LOGIN_TIME_OUT:
                Intent intent = new Intent(this, LoginActivity.class);
                this.startActivity(intent);
                this.finish();
                break;

            case CODE_REQUEST_AUTHOR:
                if (resultCode == AuthorActivity.CODE_RESULT_OF_AUTHOR_SUCCESS) {

                } else if (resultCode == AuthorActivity.CODE_RESULT_OF_AUTHOR_FAIL) {
                    Toast.makeText(this, "登录失败", Toast.LENGTH_SHORT).show();
                }
                break;

            case CODE_REQUEST_BRAND:
                if (resultCode == BrandActivity.CODE_RESULT_SUCCESS) {

                } else if (resultCode == BrandActivity.CODE_RESULT_FAIL) {
                    Toast.makeText(this, "您尚未请选择推广位,请您选择推广位", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    private void onConvertLinkSuccessCallback() {

    }


    private void onRequestMyInfoCallback(UserEntity userEntity) {

    }


    private void getRunningActivityName() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean flag = true;
                while (flag) {

                    ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
                    ActivityManager.RunningTaskInfo info = manager.getRunningTasks(1).get(0);
                    String shortClassName = info.topActivity.getShortClassName();    //类名
                    String className = info.topActivity.getClassName();              //完整类名
                    String packageName = info.topActivity.getPackageName();          //包名

                    System.out.println("栈顶信息" + className);

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }


    private void doStartApplicationWithPackageName(String packagename) {
        // 通过包名获取此APP详细信息，包括Activities、services、versioncode、name等等
        PackageInfo packageinfo = null;
        try {
            packageinfo = getPackageManager().getPackageInfo(packagename, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageinfo == null) {
            return;
        }

        // 创建一个类别为CATEGORY_LAUNCHER的该包名的Intent
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(packageinfo.packageName);

        // 通过getPackageManager()的queryIntentActivities方法遍历
        List<ResolveInfo> resolveinfoList = getPackageManager()
                .queryIntentActivities(resolveIntent, 0);

        ResolveInfo resolveinfo = resolveinfoList.iterator().next();
        if (resolveinfo != null) {
            // packagename = 参数packname
            String packageName = resolveinfo.activityInfo.packageName;
            // 这个就是我们要找的该APP的LAUNCHER的Activity[组织形式：packagename.mainActivityname]
            String className = resolveinfo.activityInfo.name;
            // LAUNCHER Intent
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);

            // 设置ComponentName参数1:packagename参数2:MainActivity路径
            ComponentName cn = new ComponentName(packageName, className);

            intent.setComponent(cn);
            startActivity(intent);
        }
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ExistDialog.Builder builder = new ExistDialog.Builder(this);
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    MainActivity.this.finish();
                }
            });

            builder.setNegativeButton("取消",
                    new android.content.DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            builder.create().show();
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_HOME) {
            this.moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
