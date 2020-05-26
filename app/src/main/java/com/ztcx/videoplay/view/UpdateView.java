package com.ztcx.videoplay.view;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.ztcx.videoplay.R;


public class UpdateView extends Dialog implements View.OnClickListener {
    private String content;
    private Activity context;
    private String title;
    private ClickListenerInterface clickListenerInterface;
    private boolean cancel;
    private TextView tv_ok;
    private View view;

    public UpdateView(Activity context, String title, String content, boolean cancel) {
        super(context, R.style.MyDialogStyle);
        this.context = context;
        this.title = title;
        this.content = content;
        this.cancel = cancel;
        setCancelable(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    /**
     * 初始化操作
     */
    private void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.dialog_update, null);
        setContentView(view);

        if (cancel){
            view.findViewById(R.id.ll_cancel).setVisibility(View.VISIBLE);
        }else {
            view.findViewById(R.id.ll_cancel).setVisibility(View.GONE);
        }


        TextView tv_pop_title = view.findViewById(R.id.tv_pop_title);
        TextView tv_pop_content = view.findViewById(R.id.tv_pop_content);
        tv_ok = view.findViewById(R.id.tv_ok);
        TextView tv_no = view.findViewById(R.id.tv_no);

        tv_no.setOnClickListener(this);
        tv_ok.setOnClickListener(this);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.8); // 高度设置为屏幕的0.6
        dialogWindow.setAttributes(lp);

        tv_pop_content.setText(content);
        tv_pop_title.setText(title);
    }

    public interface ClickListenerInterface {
        void doConfirm(UpdateView dialogUtils);
        void doCancel(UpdateView dialogUtils);
    }

    public void setClicklistener(UpdateView.ClickListenerInterface clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
    }

    public void setUpdateProgress(String progress){
        view.findViewById(R.id.ll_cancel).setVisibility(View.GONE);
        tv_ok.setText(progress);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_no:
                dismiss();
                if (clickListenerInterface != null)
                    clickListenerInterface.doCancel(UpdateView.this);
                break;
            case R.id.tv_ok:
                if (clickListenerInterface != null)
                    clickListenerInterface.doConfirm(UpdateView.this);
                break;
        }
    }

}
