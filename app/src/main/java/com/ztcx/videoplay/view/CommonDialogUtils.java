package com.ztcx.videoplay.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.ztcx.videoplay.R;


/**
 * 公用的提示弹窗
 */
public class CommonDialogUtils extends Dialog implements View.OnClickListener{
    private String content;
    private Context context;
    private String title;
    private ClickListenerInterface clickListenerInterface;
    public CommonDialogUtils(Context context, String title, String content) {
        super(context, R.style.MyDialogStyle);
        this.context = context;
        this.title = title;
        this.content = content;
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
        LayoutInflater inflater =LayoutInflater.from(context);
        View view =inflater.inflate(R.layout.dialog_common, null);
        setContentView(view);
        TextView tv_pop_title =  (TextView) view.findViewById(R.id.tv_pop_title);
        TextView tv_pop_content =  (TextView) view.findViewById(R.id.tv_pop_content);
        TextView tv_ok=  (TextView) view.findViewById(R.id.tv_ok);
        TextView tv_no=  (TextView) view.findViewById(R.id.tv_no);
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
        public void doConfirm(CommonDialogUtils dialogUtils);
    }
    public void setClicklistener(ClickListenerInterface clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_no:
                dismiss();
                break;
            case R.id.tv_ok:
                clickListenerInterface.doConfirm(this);
                break;
        }

    }

}
