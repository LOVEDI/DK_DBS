package com.zd.dk_dbs.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ksyun.media.player.IMediaPlayer;
import com.ksyun.media.player.KSYMediaPlayer;
import com.ksyun.media.player.KSYTextureView;
import com.zd.dk_dbs.R;
import com.zd.dk_dbs.util.NetStateUtil;
import com.zd.dk_dbs.util.QosThread;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/10 0010.
 */

public class LinkIngActivity extends FragmentActivity{
    @BindView(R.id.activity_link_fragment)
    FrameLayout fragment;
    @BindView(R.id.root)
    RelativeLayout fragment1;
    @BindView(R.id.texture_view)
    KSYTextureView mVideoView;


    FragmentManager fm;
    //画面
    public static final int UPDATE_SEEKBAR = 0;
    public static final int HIDDEN_SEEKBAR = 1;
    public static final int UPDATE_QOSMESS = 2;
    public static final int UPADTE_QOSVIEW = 3;
    private Context mContext;
    private QosThread mQosThread;
    private int mVideoWidth = 0;
    private int mVideoHeight = 0;
    private int mVideoScaleIndex = 0;

    private long mStartTime = 0;
    private long mPauseStartTime = 0;
    private long mPausedTime = 0;
    private SharedPreferences settings;
    private String chooseDecode;
    private String chooseDebug;
    private String bufferTime;
    private String bufferSize;
    private boolean mPlayerPanelShow = false;
    private boolean mPause = false;
    boolean useHwCodec = false;
    private boolean showAudioBar = false;
    private IMediaPlayer.OnPreparedListener mOnPreparedListener = new IMediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(IMediaPlayer mp) {
            Log.d("VideoPlayer", "OnPrepared");
            mVideoWidth = mVideoView.getVideoWidth();
            mVideoHeight = mVideoView.getVideoHeight();

            // Set Video Scaling Mode
            mVideoView.setVideoScalingMode(KSYMediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);

            //start player
            mVideoView.start();

            //set progress
            setVideoProgress(0);

            if (mQosThread != null && !mQosThread.isAlive())
                mQosThread.start();
        }
    };
    private String mDataSource;
    private IMediaPlayer.OnBufferingUpdateListener mOnBufferingUpdateListener = new IMediaPlayer.OnBufferingUpdateListener() {
        @Override
        public void onBufferingUpdate(IMediaPlayer mp, int percent) {
            long duration = mVideoView.getDuration();
            long progress = duration * percent / 100;
            //   mPlayerSeekbar.setSecondaryProgress((int) progress);
        }
    };

    private IMediaPlayer.OnVideoSizeChangedListener mOnVideoSizeChangeListener = new IMediaPlayer.OnVideoSizeChangedListener() {
        @Override
        public void onVideoSizeChanged(IMediaPlayer mp, int width, int height, int sarNum, int sarDen) {
            if (mVideoWidth > 0 && mVideoHeight > 0) {
                if (width != mVideoWidth || height != mVideoHeight) {
                    mVideoWidth = mp.getVideoWidth();
                    mVideoHeight = mp.getVideoHeight();

                    if (mVideoView != null)
                        mVideoView.setVideoScalingMode(KSYMediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
                }
            }
        }
    };

    private IMediaPlayer.OnSeekCompleteListener mOnSeekCompletedListener = new IMediaPlayer.OnSeekCompleteListener() {
        @Override
        public void onSeekComplete(IMediaPlayer mp) {
        }
    };

    private IMediaPlayer.OnCompletionListener mOnCompletionListener = new IMediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(IMediaPlayer mp) {
            Toast.makeText(mContext, "OnCompletionListener, play complete.", Toast.LENGTH_LONG).show();
            videoPlayEnd();
        }
    };

    private IMediaPlayer.OnErrorListener mOnErrorListener = new IMediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(IMediaPlayer mp, int what, int extra) {
            switch (what) {
                //case KSYVideoView.MEDIA_ERROR_UNKNOWN:
                // Log.e(TAG, "OnErrorListener, Error Unknown:" + what + ",extra:" + extra);
                //  break;
                default:
            }

            videoPlayEnd();

            return false;
        }
    };

    public IMediaPlayer.OnInfoListener mOnInfoListener = new IMediaPlayer.OnInfoListener() {
        @Override
        public boolean onInfo(IMediaPlayer iMediaPlayer, int i, int i1) {
            switch (i) {
                case KSYMediaPlayer.MEDIA_INFO_BUFFERING_START:
                    break;
                case KSYMediaPlayer.MEDIA_INFO_BUFFERING_END:
                    break;
                case KSYMediaPlayer.MEDIA_INFO_AUDIO_RENDERING_START:
//                    Toast.makeText(get, "Audio Rendering Start", Toast.LENGTH_SHORT).show();
                    break;
                case KSYMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
//                    Toast.makeText(mContext, "Video Rendering Start", Toast.LENGTH_SHORT).show();
                    break;
                case KSYMediaPlayer.MEDIA_INFO_RELOADED:
//                    Toast.makeText(mContext, "Succeed to reload video.", Toast.LENGTH_SHORT).show();
                    return false;
            }
            return false;
        }
    };

    private IMediaPlayer.OnMessageListener mOnMessageListener = new IMediaPlayer.OnMessageListener() {
        @Override
        public void onMessage(IMediaPlayer iMediaPlayer, String name, String info, double number) {
        }
    };

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linking);
        ButterKnife.bind(this);
        initView();
        //初始化直播画面
        initLink();

    }

    private void initLink() {
        mVideoView = (KSYTextureView) findViewById(R.id.texture_view);
        mVideoView.setKeepScreenOn(true);
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case UPDATE_SEEKBAR:
                        setVideoProgress(0);
                        break;
                    case HIDDEN_SEEKBAR:
                        //topPanel.setVisibility(View.GONE);
                        break;
                    case UPDATE_QOSMESS:
                        break;
                    case UPADTE_QOSVIEW:
                        break;
                }
            }
        };
        mDataSource ="rtmp://live.hkstv.hk.lxdns.com/live/hks";
        mVideoView.setOnBufferingUpdateListener(mOnBufferingUpdateListener);
        mVideoView.setOnCompletionListener(mOnCompletionListener);
        mVideoView.setOnPreparedListener(mOnPreparedListener);
        mVideoView.setOnInfoListener(mOnInfoListener);
        mVideoView.setOnVideoSizeChangedListener(mOnVideoSizeChangeListener);
        mVideoView.setOnErrorListener(mOnErrorListener);
        mVideoView.setOnSeekCompleteListener(mOnSeekCompletedListener);
        mVideoView.setOnMessageListener(mOnMessageListener);
        mVideoView.setScreenOnWhilePlaying(true);
        mVideoView.setTimeout(5, 30);
        if (!TextUtils.isEmpty(bufferTime)) {
            mVideoView.setBufferTimeMax(Integer.parseInt(bufferTime));
        }

        if (!TextUtils.isEmpty(bufferSize)) {
            mVideoView.setBufferSize(Integer.parseInt(bufferSize));
        }

       /* if (chooseDecode.equals(Settings.USEHARD)) {
            useHwCodec = true;
        } else {
            useHwCodec = false;
        }*/

        if (useHwCodec) {
            //硬解264&265
            mVideoView.setDecodeMode(KSYMediaPlayer.KSYDecodeMode.KSY_DECODE_MODE_AUTO);
        }

        try {
            mVideoView.setDataSource(mDataSource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mVideoView.prepareAsync();
    }

    private void initView() {
        fm = getSupportFragmentManager();
        changFragment( new LinkPActivity(getSupportFragmentManager()),false);
        //设置获得焦点
        fragment1.setEnabled(true);
        fragment1.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                Log.e("TAG","点击了主播1");
                return false;
            }

        });
    }

    public  void  changFragment(Fragment fragment, boolean isInit){

        //开启事物
        FragmentTransaction ft=fm.beginTransaction();
        //替换Fragemnt
        ft.replace(R.id.activity_link_fragment,fragment);

        //防止出现多个碎片重叠效果
//        if(!isInit) {
//            ft.addToBackStack(null);
//        }
        //提交事务
        ft.commit();
    }

    @OnClick({R.id.texture_view})
    public void linkIngClick(View view){
        switch (view.getId()) {
            case R.id.texture_view :
        Log.e("TAG","点击了主播");
                break;
            case R.id.activity_link_fragment :
                Log.e("TAG","点击了主播");
                break;
        }
    }
    /**
     * 直播画面
     * @param currentProgress
     * @return
     */
    public int setVideoProgress(int currentProgress) {

        if (mVideoView == null)
            return -1;

        long time = currentProgress > 0 ? currentProgress : mVideoView.getCurrentPosition();
        long length = mVideoView.getDuration();

        // Update all view elements
      /*  mPlayerSeekbar.setMax((int) length);
        mPlayerSeekbar.setProgress((int) time);*/


        Message msg = new Message();
        msg.what = UPDATE_SEEKBAR;

        if (handler != null)
            handler.sendMessageDelayed(msg, 1000);
        return (int) time;
    }
    private void videoPlayEnd() {
        if (mVideoView != null) {
            mVideoView.release();
            mVideoView = null;
        }

        if (mQosThread != null) {
            mQosThread.stopThread();
            mQosThread = null;
        }

        handler = null;

        finish();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
//        timer.cancel();
        mVideoView = null;
        NetStateUtil.unregisterNetState(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mVideoView != null) {
            mVideoView.runInBackground(true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mVideoView != null) {
            mVideoView.runInForeground();
        }
      //  NetStateUtil.registerNetState(this, netChangeListener);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            videoPlayEnd();
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public int getChangingConfigurations() {
        return super.getChangingConfigurations();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    // Maybe we could support gesture detect


}
