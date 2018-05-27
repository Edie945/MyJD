package com.example.hp.jd_dome.view.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.hp.jd_dome.R;
import com.example.hp.jd_dome.model.bean.SuDoKu_TypeBean;
import com.example.hp.jd_dome.model.bean.TuiJian_MiaoShaBean;
import com.example.hp.jd_dome.model.http.MyHttpUtils;
import com.example.hp.jd_dome.view.activity.MainActivity;
import com.example.hp.jd_dome.view.activity.MiaoShaActivity;
import com.example.hp.jd_dome.view.activity.TitleActivity;
import com.example.hp.jd_dome.view.activity.ZXingActivity;
import com.example.hp.jd_dome.view.adapter.SuDoKu_TypeAdapter;
import com.example.hp.jd_dome.view.adapter.TuiJianAdapter;
import com.example.hp.jd_dome.view.interfaces.Cid;
import com.google.gson.Gson;
import com.stx.xhb.xbanner.XBanner;
import com.stx.xhb.xbanner.transformers.Transformer;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class Fragment_home extends Fragment implements View.OnClickListener,Cid {
    private View view;
    private LinearLayout sao;
    private LinearLayout sousuo;
    private LinearLayout hongbao;
    private LinearLayout xiaoxi;
    private Toast toast;
    private XBanner banner;
    private String imgurl1 = "https://www.zhaoapi.cn/images/quarter/ad1.png";
    private String imgurl2 = "https://www.zhaoapi.cn/images/quarter/ad2.png";
    private String imgurl3 = "https://www.zhaoapi.cn/images/quarter/ad3.png";
    private String imgurl4 = "https://www.zhaoapi.cn/images/quarter/ad4.png";
    private String imgtext1 = "第十三界瑞丽模特大赛";
    private String imgtext2 = "文化艺术节";
    private String imgtext3 = "直播封面标准";
    private String imgtext4 = "人气谁最高，金主谁最豪气";
    public static final int REQUEST_CODE = 0;
    private String SuDoKu_Type = "https://www.zhaoapi.cn/product/getCatagory";
    private String TuiJian_MiaoSha = "https://www.zhaoapi.cn/ad/getAd";
    private List<SuDoKu_TypeBean.DataBean> suDoKu_typeBeanData;
    private int q = 5;
    private int e;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                SuDoKu_TypeAdapter suDoKu_typeAdapter = new SuDoKu_TypeAdapter(suDoKu_typeBeanData, getActivity(),Fragment_home.this);
                sudoku_recy_view.setLayoutManager(new GridLayoutManager(getActivity(), 2, LinearLayoutManager.HORIZONTAL, false));
                sudoku_recy_view.setAdapter(suDoKu_typeAdapter);
            }
            if (msg.what == 2) {
                if (e > 0) {
                    e--;
                    if (e >= 10) {
                        mHomeContentDownText2.setText("" + e);
                    } else {
                        mHomeContentDownText2.setText("0" + e);
                    }
                    handler.sendEmptyMessageDelayed(2, 1000);
                } else {
                    handler.sendEmptyMessage(3);
                }
            }
            if (msg.what == 3) {
                if (q == 0) {
                    handler.removeCallbacksAndMessages(0);
                    mHomeContentDownText1.setText("00");
                    mHomeContentDownText2.setText("00");
                    return;
                }
                if (q >= 0) {
                    q--;
                    e = 59;
                    mHomeContentDownText1.setText("0" + q);
                    mHomeContentDownText2.setText("" + e);
                    handler.sendEmptyMessageDelayed(2, 1000);
                }
            }
            if (msg.what == 4) {

                tuiJianAdapter = new TuiJianAdapter(tuijian_data, getActivity());
                recy_tuijian_view.setLayoutManager(new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false));
                recy_tuijian_view.setAdapter(tuiJianAdapter);
            }

        }
    };
    private RecyclerView sudoku_recy_view;
    private XBanner mBanner1;
    private RecyclerView mSudokuRecyView;
    private LinearLayout mTvWarningContent1;
    private LinearLayout mTvWarningContent2;
    private LinearLayout mTvWarningContent3;
    private LinearLayout mTvWarningContent4;
    private LinearLayout mTvWarningContent5;
    private ViewFlipper mViewFlipper;
    private RelativeLayout mGengduo;
    /**
     * 00
     */
    private TextView mHomeContentDownText1;
    /**
     * 00
     */
    private TextView mHomeContentDownText2;
    private LinearLayout mHhh;
    private TuiJian_MiaoShaBean tuiJian_miaoShaBean;
    private List<TuiJian_MiaoShaBean.TuijianBean.ListBean> tuijian_data;
    private TuiJianAdapter tuiJianAdapter;
    private RecyclerView recy_tuijian_view;
    private ScrollView scroll;
    private LinearLayout miaoshamk;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_home, null);

        toast = Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT);
        initView(view);//找控件及点击事件
//        scroll.setScrollbarFadingEnabled(false);
//        recy_tuijian_view.setScrollbarFadingEnabled(false);
        initXBanner();//XBanner轮播图
        initSuDoKu();//九宫格
        handler.sendEmptyMessage(3);//执行秒杀倒计时
        inittuijian(TuiJian_MiaoSha);//即时推荐

        return view;
    }

    private void inittuijian(String tuiJian_miaoSha) {
        MyHttpUtils.doGet(TuiJian_MiaoSha, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                Gson gson = new Gson();
                tuiJian_miaoShaBean = gson.fromJson(s, TuiJian_MiaoShaBean.class);
                tuijian_data = tuiJian_miaoShaBean.getTuijian().getList();
                handler.sendEmptyMessage(4);
            }
        });
    }

    private void initSuDoKu() {
        MyHttpUtils.doGet(SuDoKu_Type, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                Gson gson = new Gson();
                SuDoKu_TypeBean suDoKu_typeBean = gson.fromJson(s, SuDoKu_TypeBean.class);
                suDoKu_typeBeanData = suDoKu_typeBean.getData();
                handler.sendEmptyMessage(0);
            }
        });
    }

    private void initXBanner() {
        final List<String> imgesUrl = new ArrayList<>();
        final List<String> textUrl = new ArrayList<>();
        imgesUrl.add(imgurl1);
        imgesUrl.add(imgurl2);
        imgesUrl.add(imgurl3);
        imgesUrl.add(imgurl4);
        textUrl.add(imgtext1);
        textUrl.add(imgtext2);
        textUrl.add(imgtext3);
        textUrl.add(imgtext4);

        // XBanner绑定数据
        banner.setData(imgesUrl, textUrl);
        // XBanner适配数据
        banner.setmAdapter(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                Glide.with(getActivity()).load(imgesUrl.get(position)).into((ImageView) view);
            }
        });

        banner.setPageTransformer(Transformer.Depth);//本页左移，下页从后面出来
        banner.setPageChangeDuration(1000);
        banner.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, int position) {
                Toast.makeText(getActivity(), "点击了第" + position + "图片", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView(View view) {
        sudoku_recy_view = (RecyclerView) view.findViewById(R.id.sudoku_recy_view);
        banner = (XBanner) view.findViewById(R.id.banner_1);
        sao = (LinearLayout) view.findViewById(R.id.sao);
        sao.setOnClickListener(this);
        sousuo = (LinearLayout) view.findViewById(R.id.sousuo);
        sousuo.setOnClickListener(this);
        hongbao = (LinearLayout) view.findViewById(R.id.hongbao);
        hongbao.setOnClickListener(this);
        xiaoxi = (LinearLayout) view.findViewById(R.id.xiaoxi);
        xiaoxi.setOnClickListener(this);


        recy_tuijian_view = (RecyclerView) view.findViewById(R.id.recy_tuijian_view);
        mBanner1 = (XBanner) view.findViewById(R.id.banner_1);
        mSudokuRecyView = (RecyclerView) view.findViewById(R.id.sudoku_recy_view);
        mTvWarningContent1 = (LinearLayout) view.findViewById(R.id.tv_warning_content1);
        mTvWarningContent2 = (LinearLayout) view.findViewById(R.id.tv_warning_content2);
        mTvWarningContent3 = (LinearLayout) view.findViewById(R.id.tv_warning_content3);
        mTvWarningContent4 = (LinearLayout) view.findViewById(R.id.tv_warning_content4);
        mTvWarningContent5 = (LinearLayout) view.findViewById(R.id.tv_warning_content5);
        mViewFlipper = (ViewFlipper) view.findViewById(R.id.view_flipper);
        mGengduo = (RelativeLayout) view.findViewById(R.id.gengduo);
        mHomeContentDownText1 = (TextView) view.findViewById(R.id.home_content_down_text_1);
        mHomeContentDownText2 = (TextView) view.findViewById(R.id.home_content_down_text_2);
        miaoshamk = (LinearLayout) view.findViewById(R.id.miaoshamk);
        miaoshamk.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.sao:
                toast.setText("扫二维码了");
                toast.show();
                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.sousuo:
                toast.setText("搜索开始");
                toast.show();
                startActivity(new Intent(getActivity(), TitleActivity.class));
                break;
            case R.id.hongbao:
                toast.setText("领红包");
                toast.show();
                break;
            case R.id.xiaoxi:
                toast.setText("打开消息");
                toast.show();
                break;
            case R.id.miaoshamk:
                toast.setText("商品秒殺");
                toast.show();
                startActivity(new Intent(getActivity(), MiaoShaActivity.class));
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {

            Bundle bundle = data.getExtras();
            if (bundle == null) {
                return;
            }
            if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                //这是拿到解析扫描到的信息，并转成字符串
                String result = bundle.getString(CodeUtils.RESULT_STRING);

                Toast.makeText(getContext(), "解析结果:" + result, Toast.LENGTH_LONG).show();
                //解析扫到的二维码后就跳转页面
                Intent intent = new Intent(getActivity(), ZXingActivity.class);
                //把扫到并解析到的信息(既:字符串)带到详情页面
                intent.putExtra("path", result);
                startActivity(intent);
            } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                //否则土司解析二维码失败
                Toast.makeText(getContext(), "解析二维码失败:", Toast.LENGTH_LONG).show();

            }
        }
    }

    @Override
    public void getCid(int cids) {
        Log.e("TAG", "getCid: "+cids );
        Toast.makeText(getContext(),""+cids,Toast.LENGTH_SHORT).show();
    }
}
