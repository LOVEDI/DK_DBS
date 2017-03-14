package com.zd.dk_dbs.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zd.dk_dbs.R;
import com.zd.dk_dbs.adapter.GiftMsgAdapter;
import com.zd.dk_dbs.adapter.RecycleViewAdapater;
import com.zd.dk_dbs.adapter.RvTitleIconAdapater;
import com.zd.dk_dbs.fragment.base.BaseFragmet;
import com.zd.dk_dbs.hurttveiw.HeartLayout;

import org.dync.giftlibrary.util.GiftPanelControl;
import org.dync.giftlibrary.widget.GiftControl;
import org.dync.giftlibrary.widget.GiftFrameLayout;
import org.dync.giftlibrary.widget.GiftModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/10 0010.
 */

public class FragmentLink extends BaseFragmet{
    @BindView(R.id.heart_layout)
    HeartLayout heartLayout;
    @BindView(R.id.dbsing_rv)
    RecyclerView rv_title_icon;
    @BindView(R.id.dbsing_talking_rv)
    RecyclerView rv_talking;
    @BindView(R.id.MessageText)
    EditText input_msg;
    @BindView(R.id.button_talk)
    Button but_talk;
    @BindView(R.id.button_gifts)
    Button but_gifts;
    @BindView(R.id.link_love)
    Button but_love;
    @BindView(R.id.MessageButton)
    Button send_msg;
    @BindView(R.id.bottom_content)
    TableLayout tableLayout;
    @BindView(R.id.bottom)
    RelativeLayout bottom;


    ViewPager mViewpager;
    RecyclerView mRecyclerView;
    RelativeLayout mDotsLayout;
    private String giftstr = "";
    private GiftControl giftControl;
    private ImageView btnGift;
    private GiftFrameLayout giftFrameLayout1;
    private GiftFrameLayout giftFrameLayout2;
 //   private GiftFrameLayout giftFrameLayout3;
    private TextView tvGiftNum;
    private GiftMsgAdapter adapter;
    private LinearLayout giftLayout;



    private FragmentLink() {}
    private static FragmentLink single=null;
    //静态工厂方法
    public static FragmentLink getInstance() {
        if (single == null) {
            single = new FragmentLink();
        }
        return single;
    }

    List<String> msg_list = new ArrayList();
    Handler handler = new Handler();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_linking, container, false);
        ButterKnife.bind(this,view);
        tableLayout = (TableLayout) view.findViewById(R.id.bottom_content);
        initListener();
        //送礼物
        initRecycler();
        send_gifs(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void sendMsg() {
        RecycleViewAdapater adapater;
        String msg = input_msg.getText().toString();
        if("".equals(msg)) {

        }else{
            msg_list.add("好姑娘:"+msg);
        }
        input_msg.setText("");
        List<String> fall_list = new ArrayList();
        if(msg_list.size()>1) {
            for(int i = msg_list.size()-1; i >0; i--) {
                fall_list.add(msg_list.get(i));
                if(i==1) {
                    fall_list.add(msg_list.get(0));
                }
                Log.e("TAG","是多少?"+msg_list.get(i));
            }
            adapater = new  RecycleViewAdapater(getActivity(),fall_list);
        }else{
          adapater = new  RecycleViewAdapater(getActivity(),msg_list);
        }


        rv_talking.setAdapter(adapater);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        //设置翻转布局
        manager.setReverseLayout(true);
        rv_talking.setLayoutManager(manager);
        rv_talking.setItemAnimator(new DefaultItemAnimator());
    }

    private void send_gifs(View view) {
        giftLayout = (LinearLayout) view.findViewById(R.id.giftLayout);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_gift);
        mViewpager = (ViewPager) view.findViewById(R.id.toolbox_pagers_face);
        mDotsLayout = (RelativeLayout) view.findViewById(R.id.face_dots_container);
        btnGift = (ImageView) view.findViewById(R.id.toolbox_iv_face);
        GiftPanelControl giftPanelControl = new GiftPanelControl(getActivity(), mViewpager, mRecyclerView, mDotsLayout);
        giftPanelControl.setGiftListener(new GiftPanelControl.GiftListener() {
            @Override
            public void getGiftStr(String giftStr) {
                giftstr = giftStr;
            }
        });
        giftControl = new GiftControl(getActivity());
        giftFrameLayout1 = (GiftFrameLayout) view.findViewById(R.id.gift_layout1);
        giftFrameLayout2 = (GiftFrameLayout) view.findViewById(R.id.gift_layout2);
        //giftFrameLayout3 = (GiftFrameLayout) view.findViewById(R.id.gift_layout3);
        giftControl.setGiftLayout(giftFrameLayout1, giftFrameLayout2);
        tvGiftNum = (TextView) view.findViewById(R.id.toolbox_tv_gift_num);
        adapter = new GiftMsgAdapter(getActivity());
        tvGiftNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                showGiftDialog();
            }
        });
        btnGift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(giftstr)) {
                    Toast.makeText(getActivity(), "你还没选择礼物呢", Toast.LENGTH_SHORT).show();
                } else {
                    giftLayout.setVisibility(View.VISIBLE);
                    String numStr = tvGiftNum.getText().toString();
                    if (!TextUtils.isEmpty(numStr)) {
                        int giftnum = Integer.parseInt(numStr);
                        if (giftnum == 0) {
                            return;
                        } else {
                            giftControl.loadGift(new GiftModel(giftstr, "安卓机器人", giftnum, "http://www.baidu.com", "123", "Lee123", "http://www.baidu.com", System.currentTimeMillis()));

                            adapter.add(giftstr);
                        }
                    }
                }
            }
        });
    }

    private void initListener() {
        //给发送按钮设置监听
        input_msg.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                int screenWidth = send_msg.getWidth();
                input_msg.setWidth((3*screenWidth)/4);
                send_msg.setWidth(screenWidth/4);
            }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,int arg3) {

            }
            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });
    }

    private void initRecycler() {
        List<String> list = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            list.add("");
        }

        RvTitleIconAdapater titleAdapter  = new RvTitleIconAdapater(getActivity(),list);
        LinearLayoutManager manger = new LinearLayoutManager(getActivity());
        manger.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_title_icon.setAdapter(titleAdapter);
        rv_title_icon.setLayoutManager(manger);
        rv_title_icon.setItemAnimator(new DefaultItemAnimator());
       /* int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.space);
        rv_title_icon.addItemDecoration(new SpaceItemDecoration(spacingInPixels));*/
    if(msg_list.size()>0) {
        sendMsg();

    }
        //自动点心

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                    heartLayout.addFavor();
                    }
                });
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 500, 1000);


    }


    @OnClick({R.id.link_love,R.id.button_talk,R.id.button_gifts,R.id.MessageButton})
    public void clickView(View view){
        switch (view.getId()){
            case R.id.link_love:
                //点赞
                heartLayout.addFavor();
                break;
            case R.id.button_talk:
                //发消息
                //1.设置EditText显示
                tableLayout.setVisibility(View.VISIBLE);
                //自动弹出软键盘
                input_msg.setFocusableInTouchMode(true);
                input_msg.requestFocus();
                input_msg.setFocusable(true);
                InputMethodManager inputManager =
                        (InputMethodManager)input_msg.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(input_msg, 0);
                break;
            case R.id.button_gifts:
                //送礼物
                bottom.setVisibility(View.VISIBLE);
                if (giftLayout.getVisibility() == View.VISIBLE) {
                    giftLayout.setVisibility(View.GONE);
                } else {
                    giftLayout.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.MessageButton:
                sendMsg();
                break;
        }
    }
   /* public Handler handler = new Handler(){
        public void handleMessage(Message msg){
            if(msg.what==1) {
                try {
                    Log.e("TAG","他不layout是空的额? "+tableLayout);
                    tableLayout.setVisibility(View.GONE);
                }catch (Exception e){
                    Log.e("TAG","出现的异常是："+e.toString());
                }

            }
        }
    };*/
    public void hindeLayout(CallBack callback){
        Log.e("TAG","在这里他他不layout是空的么？"+tableLayout);
        callback.hide(tableLayout,bottom);
    }
    public interface CallBack{
        void hide(TableLayout tableLayout,RelativeLayout bottom);
    }

    private void showGiftDialog() {
        final GiftDialogFrament giftDialogFrament = new GiftDialogFrament();
        giftDialogFrament.show(getActivity().getFragmentManager(), "GiftDialogFrament");
        giftDialogFrament.setGiftListener(new GiftDialogFrament.GiftListener() {
            @Override
            public void giftNum(String giftNum) {
                tvGiftNum.setText(giftNum);
                giftDialogFrament.dismiss();
            }
        });
    }
}
