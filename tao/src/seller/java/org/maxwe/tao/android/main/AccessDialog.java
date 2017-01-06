package org.maxwe.tao.android.main;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import org.maxwe.tao.android.R;

/**
 * Created by Pengwei Ding on 2017-01-03 20:50.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class AccessDialog extends Dialog implements View.OnClickListener {

    private OnKeyListener onKeyListener = new DialogInterface.OnKeyListener() {
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                return true;
            } else {
                return false;
            }
        }
    };

    private EditText et_dia_access_code;
    private Button bt_dia_access_get_access;
    private ProgressBar pb_dia_access_progress;

    public AccessDialog(Context context) {
        super(context);
        this.init();
    }

    public AccessDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.init();
    }

    protected AccessDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.init();
    }

    private void init() {
        this.setContentView(R.layout.dialog_access);
        this.setOnKeyListener(this.onKeyListener);
//        this.setCanceledOnTouchOutside(false);
        this.et_dia_access_code = (EditText) this.findViewById(R.id.et_dia_access_code);
        this.bt_dia_access_get_access = (Button) this.findViewById(R.id.bt_dia_access_get_access);
        this.pb_dia_access_progress = (ProgressBar) this.findViewById(R.id.pb_dia_access_progress);
        this.bt_dia_access_get_access.setOnClickListener(this);
        this.setInitView();
    }

    private void setInitView() {
        this.setTitle(R.string.string_access_status);
        this.et_dia_access_code.setVisibility(View.GONE);
        this.bt_dia_access_get_access.setVisibility(View.GONE);
        this.pb_dia_access_progress.setVisibility(View.GONE);
    }

    private void setAccessingView() {
//        this.setTitle(R.string.string_accessing);
//        this.et_dia_access_code.setVisibility(View.INVISIBLE);
//        this.bt_dia_access_get_access.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        this.setAccessingView();
//        SharedPreferences sharedPreferences = this.getContext().getSharedPreferences(Constants.KEY_SHARD_NAME, Activity.MODE_PRIVATE);
//        String cellphone = sharedPreferences.getString(Constants.KEY_SHARD_T_ACCOUNT, null);
//        String key = sharedPreferences.getString(Constants.KEY_SHARD_T_CONTENT, null);
//        AgentEntity agentEntity = new AgentEntity(cellphone, null, this.getContext().getResources().getInteger(R.integer.type_id));
//        AgentEntityInter agentEntityInter = new AgentEntityInter(agentEntity);
//        agentEntityInter.setT(key);
//        AgentManager.requestAccessCode(agentEntityInter, new AgentManager.OnRequestCallback() {
//            @Override
//            public void onSuccess(Response response) {
//                Toast.makeText(AccessDialog.this.getContext(),R.string.string_access_success,Toast.LENGTH_SHORT).show();
//                AccessDialog.this.hide();
//            }
//
//            @Override
//            public void onError(Throwable exception, AgentEntity agentEntity) {
//                Toast.makeText(AccessDialog.this.getContext(),R.string.string_access_success,Toast.LENGTH_SHORT).show();
//                AccessDialog.this.hide();
//            }
//        });
    }
}
