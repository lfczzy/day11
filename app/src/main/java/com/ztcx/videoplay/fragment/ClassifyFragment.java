package com.ztcx.videoplay.fragment;

import android.annotation.SuppressLint;
import android.os.Handler;

import com.ztcx.videoplay.R;
import com.ztcx.videoplay.adapter.ClassifyAdapter;
import com.ztcx.videoplay.base.NativeBaseListFragment;
import com.ztcx.videoplay.been.Classify;
import com.ztcx.videoplay.constant.AppConstant;

import java.util.ArrayList;
import java.util.List;

public class ClassifyFragment extends NativeBaseListFragment<Classify, ClassifyAdapter> {

    Handler handler;

    public ClassifyFragment() {
    }

    @SuppressLint("ValidFragment")
    public ClassifyFragment(Handler handler) {
        this.handler = handler;
    }

    @Override
    protected ClassifyAdapter setAdapter() {
        return new ClassifyAdapter(R.layout.item_classify,data,handler);
    }

    @Override
    protected void setUpView() {
        super.setUpView();
        setVisibleTitleBar(AppConstant.MAIN_COLOR,"分类");
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setEnableLoadMore(false);
    }

    @Override
    protected void setUpData() {
        data = new ArrayList<>();
        Classify classify1 = new Classify();
        classify1.setTitle("电影");
        List<Classify.Reclassify> reclassifyList = new ArrayList<>();
        reclassifyList.add(new Classify.Reclassify("喜剧",R.mipmap.ic_aqy,"103"));
        reclassifyList.add(new Classify.Reclassify("爱情",R.mipmap.ic_aqy,"100"));
        reclassifyList.add(new Classify.Reclassify("动作",R.mipmap.ic_aqy,"106"));
        reclassifyList.add(new Classify.Reclassify("恐怖",R.mipmap.ic_aqy,"102"));
        reclassifyList.add(new Classify.Reclassify("科幻",R.mipmap.ic_aqy,"104"));
        reclassifyList.add(new Classify.Reclassify("剧情",R.mipmap.ic_aqy,"112"));
        reclassifyList.add(new Classify.Reclassify("犯罪",R.mipmap.ic_aqy,"105"));
        reclassifyList.add(new Classify.Reclassify("奇幻",R.mipmap.ic_aqy,"113"));
        reclassifyList.add(new Classify.Reclassify("战争",R.mipmap.ic_aqy,"108"));
        reclassifyList.add(new Classify.Reclassify("悬疑",R.mipmap.ic_aqy,"115"));
        reclassifyList.add(new Classify.Reclassify("动画",R.mipmap.ic_aqy,"107"));
        reclassifyList.add(new Classify.Reclassify("文艺",R.mipmap.ic_aqy,"117"));
        reclassifyList.add(new Classify.Reclassify("伦理",R.mipmap.ic_aqy,"101"));
        reclassifyList.add(new Classify.Reclassify("纪录",R.mipmap.ic_aqy,"118"));
        reclassifyList.add(new Classify.Reclassify("传记",R.mipmap.ic_aqy,"119"));
        reclassifyList.add(new Classify.Reclassify("歌舞",R.mipmap.ic_aqy,"120"));
        reclassifyList.add(new Classify.Reclassify("古装",R.mipmap.ic_aqy,"121"));
        reclassifyList.add(new Classify.Reclassify("历史",R.mipmap.ic_aqy,"122"));
        reclassifyList.add(new Classify.Reclassify("惊悚",R.mipmap.ic_aqy,"123"));
        reclassifyList.add(new Classify.Reclassify("其他",R.mipmap.ic_aqy,"other"));
        classify1.setReclassify(reclassifyList);
        data.add(classify1);

        Classify classify2 = new Classify();
        classify2.setTitle("电视");
        List<Classify.Reclassify> reclassifyList2 = new ArrayList<>();
        reclassifyList2.add(new Classify.Reclassify("言情",R.mipmap.ic_aqy,"101"));
        reclassifyList2.add(new Classify.Reclassify("伦理",R.mipmap.ic_aqy,"105"));
        reclassifyList2.add(new Classify.Reclassify("喜剧",R.mipmap.ic_aqy,"109"));
        reclassifyList2.add(new Classify.Reclassify("悬疑",R.mipmap.ic_aqy,"108"));
        reclassifyList2.add(new Classify.Reclassify("都市",R.mipmap.ic_aqy,"111"));
        reclassifyList2.add(new Classify.Reclassify("偶像",R.mipmap.ic_aqy,"100"));
        reclassifyList2.add(new Classify.Reclassify("古装",R.mipmap.ic_aqy,"104"));
        reclassifyList2.add(new Classify.Reclassify("军事",R.mipmap.ic_aqy,"107"));
        reclassifyList2.add(new Classify.Reclassify("警匪",R.mipmap.ic_aqy,"103"));
        reclassifyList2.add(new Classify.Reclassify("历史",R.mipmap.ic_aqy,"112"));
        reclassifyList2.add(new Classify.Reclassify("励志",R.mipmap.ic_aqy,"116"));
        reclassifyList2.add(new Classify.Reclassify("神话",R.mipmap.ic_aqy,"117"));
        reclassifyList2.add(new Classify.Reclassify("谍战",R.mipmap.ic_aqy,"118"));
        reclassifyList2.add(new Classify.Reclassify("青春",R.mipmap.ic_aqy,"119"));
        reclassifyList2.add(new Classify.Reclassify("家庭",R.mipmap.ic_aqy,"120"));
        reclassifyList2.add(new Classify.Reclassify("动作",R.mipmap.ic_aqy,"115"));
        reclassifyList2.add(new Classify.Reclassify("情景",R.mipmap.ic_aqy,"114"));
        reclassifyList2.add(new Classify.Reclassify("武侠",R.mipmap.ic_aqy,"106"));
        reclassifyList2.add(new Classify.Reclassify("科幻",R.mipmap.ic_aqy,"113"));
        reclassifyList2.add(new Classify.Reclassify("其他",R.mipmap.ic_aqy,"other"));
        classify2.setReclassify(reclassifyList2);
        data.add(classify2);

        Classify classify3 = new Classify();
        classify3.setTitle("综艺");
        List<Classify.Reclassify> reclassifyList3 = new ArrayList<>();
        reclassifyList3.add(new Classify.Reclassify("脱口秀",R.mipmap.ic_aqy,"121"));
        reclassifyList3.add(new Classify.Reclassify("真人秀",R.mipmap.ic_aqy,"120"));
        reclassifyList3.add(new Classify.Reclassify("选秀",R.mipmap.ic_aqy,"101"));
        reclassifyList3.add(new Classify.Reclassify("八卦",R.mipmap.ic_aqy,"102"));
        reclassifyList3.add(new Classify.Reclassify("访谈",R.mipmap.ic_aqy,"103"));
        reclassifyList3.add(new Classify.Reclassify("情感",R.mipmap.ic_aqy,"104"));
        reclassifyList3.add(new Classify.Reclassify("生活",R.mipmap.ic_aqy,"105"));
        reclassifyList3.add(new Classify.Reclassify("晚会",R.mipmap.ic_aqy,"106"));
        reclassifyList3.add(new Classify.Reclassify("搞笑",R.mipmap.ic_aqy,"107"));
        reclassifyList3.add(new Classify.Reclassify("音乐",R.mipmap.ic_aqy,"108"));
        reclassifyList3.add(new Classify.Reclassify("时尚",R.mipmap.ic_aqy,"109"));
        reclassifyList3.add(new Classify.Reclassify("游戏",R.mipmap.ic_aqy,"110"));
        reclassifyList3.add(new Classify.Reclassify("少儿",R.mipmap.ic_aqy,"111"));
        reclassifyList3.add(new Classify.Reclassify("体育",R.mipmap.ic_aqy,"112"));
        reclassifyList3.add(new Classify.Reclassify("纪实",R.mipmap.ic_aqy,"113"));
        reclassifyList3.add(new Classify.Reclassify("科教",R.mipmap.ic_aqy,"114"));
        reclassifyList3.add(new Classify.Reclassify("曲艺",R.mipmap.ic_aqy,"115"));
        reclassifyList3.add(new Classify.Reclassify("歌舞",R.mipmap.ic_aqy,"116"));
        reclassifyList3.add(new Classify.Reclassify("财经",R.mipmap.ic_aqy,"117"));
        reclassifyList3.add(new Classify.Reclassify("汽车",R.mipmap.ic_aqy,"118"));
        reclassifyList3.add(new Classify.Reclassify("播报",R.mipmap.ic_aqy,"119"));
        reclassifyList3.add(new Classify.Reclassify("其他",R.mipmap.ic_aqy,"other"));
        classify3.setReclassify(reclassifyList3);
        data.add(classify3);

        Classify classify4 = new Classify();
        classify4.setTitle("动漫");
        List<Classify.Reclassify> reclassifyList4 = new ArrayList<>();
        reclassifyList4.add(new Classify.Reclassify("热血",R.mipmap.ic_aqy,"100"));
        reclassifyList4.add(new Classify.Reclassify("科幻",R.mipmap.ic_aqy,"134"));
        reclassifyList4.add(new Classify.Reclassify("美少女",R.mipmap.ic_aqy,"102"));
        reclassifyList4.add(new Classify.Reclassify("魔幻",R.mipmap.ic_aqy,"109"));
        reclassifyList4.add(new Classify.Reclassify("经典",R.mipmap.ic_aqy,"135"));
        reclassifyList4.add(new Classify.Reclassify("励志",R.mipmap.ic_aqy,"136"));
        reclassifyList4.add(new Classify.Reclassify("少儿",R.mipmap.ic_aqy,"111"));
        reclassifyList4.add(new Classify.Reclassify("冒险",R.mipmap.ic_aqy,"107"));
        reclassifyList4.add(new Classify.Reclassify("搞笑",R.mipmap.ic_aqy,"105"));
        reclassifyList4.add(new Classify.Reclassify("推理",R.mipmap.ic_aqy,"137"));
        reclassifyList4.add(new Classify.Reclassify("恋爱",R.mipmap.ic_aqy,"101"));
        reclassifyList4.add(new Classify.Reclassify("治愈",R.mipmap.ic_aqy,"138"));
        reclassifyList4.add(new Classify.Reclassify("幻想",R.mipmap.ic_aqy,"106"));
        reclassifyList4.add(new Classify.Reclassify("校园",R.mipmap.ic_aqy,"104"));
        reclassifyList4.add(new Classify.Reclassify("动物",R.mipmap.ic_aqy,"110"));
        reclassifyList4.add(new Classify.Reclassify("机战",R.mipmap.ic_aqy,"112"));
        reclassifyList4.add(new Classify.Reclassify("亲子",R.mipmap.ic_aqy,"131"));
        reclassifyList4.add(new Classify.Reclassify("运动",R.mipmap.ic_aqy,"103"));
        reclassifyList4.add(new Classify.Reclassify("悬疑",R.mipmap.ic_aqy,"108"));
        reclassifyList4.add(new Classify.Reclassify("怪物",R.mipmap.ic_aqy,"113"));
        reclassifyList4.add(new Classify.Reclassify("战争",R.mipmap.ic_aqy,"115"));
        reclassifyList4.add(new Classify.Reclassify("益智",R.mipmap.ic_aqy,"114"));
        reclassifyList4.add(new Classify.Reclassify("青春",R.mipmap.ic_aqy,"123"));
        reclassifyList4.add(new Classify.Reclassify("童话",R.mipmap.ic_aqy,"121"));
        reclassifyList4.add(new Classify.Reclassify("竞技",R.mipmap.ic_aqy,"119"));
        reclassifyList4.add(new Classify.Reclassify("动作",R.mipmap.ic_aqy,"126"));
        reclassifyList4.add(new Classify.Reclassify("社会",R.mipmap.ic_aqy,"116"));
        reclassifyList4.add(new Classify.Reclassify("友情",R.mipmap.ic_aqy,"117"));
        reclassifyList4.add(new Classify.Reclassify("真人版",R.mipmap.ic_aqy,"127"));
        reclassifyList4.add(new Classify.Reclassify("电影版",R.mipmap.ic_aqy,"130"));
        reclassifyList4.add(new Classify.Reclassify("ova版",R.mipmap.ic_aqy,"128"));
        reclassifyList4.add(new Classify.Reclassify("tv版",R.mipmap.ic_aqy,"129"));
        reclassifyList4.add(new Classify.Reclassify("新番动画",R.mipmap.ic_aqy,"132"));
        reclassifyList4.add(new Classify.Reclassify("完结动画",R.mipmap.ic_aqy,"133"));
        classify4.setReclassify(reclassifyList4);
        data.add(classify4);

        adapter.setNewData(data);
        adapter.notifyDataSetChanged();

    }
}
