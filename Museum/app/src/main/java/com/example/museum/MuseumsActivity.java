package com.example.museum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.museum.API.API;
import com.example.museum.Adapter.MuseumsAdapter;
import com.example.museum.Datas.Museum;
import com.example.museum.SQLite.RecordsDao;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.jingbin.library.ByRecyclerView;

/*
* 博物馆搜索页页面
* */
public class MuseumsActivity extends AppCompatActivity {


    private RecordsDao mRecordsDao;
    //默然展示词条个数
    private final int DEFAULT_RECORD_NUMBER = 8;
    private List<String> recordList = new ArrayList<>();
    private TagAdapter mRecordsAdapter;
    private LinearLayout mHistoryContent;
    private ByRecyclerView recyclerView;
    private List<Museum> museumList;
    private  MuseumsAdapter adapter=new MuseumsAdapter(museumList);
    private Integer page=0;
    //防止在子线程中更新UI时程序会崩溃，添加了Handler
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                ProgressBar progressBar=findViewById(R.id.museums_process);
                progressBar.setVisibility(View.GONE);
                if(page==1)
                    recyclerView.setRefreshing(false);
                else
                    recyclerView.loadMoreComplete();       // 加载更多完成
                adapter.setNewData(museumList);            // 设置及刷新数据
            }
            else if(msg.what==0)
            {
                ProgressBar progressBar=findViewById(R.id.museums_process);
                progressBar.setVisibility(View.INVISIBLE);
                TextView error=findViewById(R.id.museums_error);
                error.setVisibility(View.VISIBLE);
            }
            else
            {
                recyclerView.loadMoreEnd();            // 没有更多内容了
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_museums);
        //以下部分是对搜索框属性的一些设置
        //默认账号
        String username = "007";
        //初始化数据库
        mRecordsDao = new RecordsDao(this, username);
        final EditText editText = findViewById(R.id.edit_query);
        final TagFlowLayout tagFlowLayout = findViewById(R.id.fl_search_records);
        final ImageView clearAllRecords = findViewById(R.id.clear_all_records);
        final ImageView moreArrow = findViewById(R.id.iv_arrow);
        ImageView toTop = findViewById(R.id.iv_arrow_toTop);
        final ImageView backToHome = findViewById(R.id.iv_back);
        TextView search = findViewById(R.id.iv_search);
        ImageView clearSearch = findViewById(R.id.iv_clear_search);
        mHistoryContent = findViewById(R.id.ll_history_content);
        initData();
        museumList = new ArrayList<>();
        backToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {//EditorInfo.IME_ACTION_SEARCH、EditorInfo.IME_ACTION_SEND等分别对应EditText的imeOptions属性
                    //TODO回车键按下时要执行的操作
                    String record = editText.getText().toString();
                    if (!TextUtils.isEmpty(record)) {
                        page=0;
                        museumList.clear();
                        recyclerView.setLoadMoreEnabled(false);
                        getMuseums(API.getPartMuseumByName+record+"?size=50");
                        //添加数据
                        mRecordsDao.addRecords(record);
                    }
                    else
                    {
                        page=0;
                        museumList.clear();
                        getMuseums(API.showAllMuseums+"?page="+page.toString());
                    }
                }
                return false;
            }
        });

//        //监听回车键按下事件
//        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            /**
//             *
//             * @param v 被监听的对象
//             * @param actionId  动作标识符,如果值等于EditorInfo.IME_NULL，则回车键被按下。
//             * @param event    如果由输入键触发，这是事件；否则，这是空的(比如非输入键触发是空的)。
//             * @return 返回你的动作
//             */
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//
//                return event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER);
//            }
//        });
        //创建历史标签适配器
        //为标签设置对应的内容
        mRecordsAdapter = new TagAdapter<String>(recordList) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) LayoutInflater.from(MuseumsActivity.this).inflate(R.layout.museum_search_history,
                        tagFlowLayout, false);
                //为标签设置对应的内容
                tv.setText(s);
                return tv;
            }
        };
        tagFlowLayout.setAdapter(mRecordsAdapter);
        tagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public void onTagClick(View view, int position, FlowLayout parent) {
                //清空editText之前的数据
                editText.setText("");
                //将获取到的字符串传到搜索结果界面,点击后搜索对应条目内容
                editText.setText(recordList.get(position));
                editText.setSelection(editText.length());
            }
        });
        //删除某个条目
        tagFlowLayout.setOnLongClickListener(new TagFlowLayout.OnLongClickListener() {
            @Override
            public void onLongClick(View view, final int position) {
                showDialog("确定要删除该条历史记录？", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //删除某一条记录
                        mRecordsDao.deleteRecord(recordList.get(position));
                    }
                });
            }
        });
        //view加载完成时回调
        tagFlowLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                boolean isOverFlow = tagFlowLayout.isOverFlow();
                boolean isLimit = tagFlowLayout.isLimit();
                if (isLimit && isOverFlow) {
                    moreArrow.setVisibility(View.VISIBLE);
                } else {
                    moreArrow.setVisibility(View.GONE);
                }
            }
        });
        moreArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tagFlowLayout.setLimit(false);
                mRecordsAdapter.notifyDataChanged();
                toTop.setVisibility(View.VISIBLE);

            }
        });
        // 将内容再折叠起来
        toTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tagFlowLayout.setLimit(true);
                mRecordsAdapter.notifyDataChanged();
                toTop.setVisibility(View.GONE);
            }
        });
        //清除所有记录
        clearAllRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog("确定要删除全部历史记录？", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tagFlowLayout.setLimit(true);
                        //清除所有数据
                        mRecordsDao.deleteUsernameAllRecords();
                    }
                });
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String record = editText.getText().toString();
                if (!TextUtils.isEmpty(record)) {
                    page=0;
                    museumList.clear();
                    recyclerView.setLoadMoreEnabled(false);
                    System.out.println(API.getPartMuseumByName+record+"?size=50");
                    getMuseums(API.getPartMuseumByName+record+"?size=50");
                    //添加数据
                    mRecordsDao.addRecords(record);
                }
                else
                {
                    page=0;
                    museumList.clear();
                    getMuseums(API.showAllMuseums+"?page="+page.toString());
                }
            }
        });
        clearSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //清除搜索历史
                editText.setText("");
            }
        });
        mRecordsDao.setNotifyDataChanged(new RecordsDao.NotifyDataChanged() {
            @Override
            public void notifyDataChanged() {
                initData();
            }
        });

        //卡片式布局显示博物馆列表
        recyclerView = (ByRecyclerView) findViewById(R.id.recycler_museumsviews);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);
        getMuseums(API.showAllMuseums+"?page=0");
        recyclerView.setAdapter(adapter);
        recyclerView.setLoadMoreEnabled(true);
        recyclerView.setOnLoadMoreListener(new ByRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                getMuseums(API.showAllMuseums+"?page="+page.toString());
            }
        }, 10);// delayMillis: 延迟多少毫秒调用接口
        //设置列表项的点击事件
        recyclerView.setOnItemClickListener(new ByRecyclerView.OnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                Museum museum = adapter.getItemData(position);     // 通过adapter获取对应position的数据
                Intent intent = new Intent(MuseumsActivity.this, MuseumActivity.class);
                intent.putExtra("mid",museum.getMid());
                intent.putExtra("name",museum.getName());
                intent.putExtra("introduction",museum.getIntroduction());
                intent.putExtra("avgstar",museum.getAvgstar());
                intent.putExtra("opentime",museum.getOpentime());
                intent.putExtra("address",museum.getAddress());
                intent.putExtra("imgurl",museum.getImgurl());
                intent.putExtra("mobile",museum.getMobile());
                startActivity(intent);
            }
        });

    }

    private void getMuseums(String url) {
        new Thread(()->{
            if(page==0)
            museumList.clear();
            Message message = new Message();
            try {
                String jsonData = HttpRequest.Get(url);
//                System.out.println(url);
//                System.out.println(jsonData);
                //考虑网络请求出错的情况
                if(jsonData==null)
                    message.what = 0;
                else {
                    JSONObject jsonObject = new JSONObject(jsonData);
                    int pagenum = jsonObject.getInt("totalPages");
                    JSONArray Jarray = jsonObject.getJSONArray("content");
                    for (int i = 0; i < Jarray.length(); i++) {
                        JSONObject object = Jarray.getJSONObject(i);
                        museumList.add(new Museum(object.getInt("mid"), object.getString("imgurl"), object.getString("name"),
                                object.getDouble("avgenvironmentstar"), object.getDouble("avgexhibitionstar"), object.getDouble("avgservicestar"),
                                object.getString("address"), object.getDouble("avgstar"),
                                object.getString("introduction"), object.getString("opentime"), object.getString("mobile")));
                    }
                    if(page>=pagenum)
                        message.what=-1;
                    else
                    {
                        page++;
                        message.what = 1;
                    }
                }
                handler.sendMessage(message);    // 将Message对象发送出去
            } catch (JSONException e) {
                message.what=-1;
                handler.sendMessage(message);
                e.printStackTrace();
            }

        }).start();
    }

    private void showDialog(String dialogTitle, @NonNull DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MuseumsActivity.this);
        builder.setMessage(dialogTitle);
        builder.setPositiveButton("确定", onClickListener);
        builder.setNegativeButton("取消", null);
        builder.create().show();
    }

    @SuppressLint("CheckResult")
    private void initData() {
        Observable.create(new ObservableOnSubscribe<List<String>>() {
            @Override
            public void subscribe(ObservableEmitter<List<String>> emitter) throws Exception {
                emitter.onNext(mRecordsDao.getRecordsByNumber(DEFAULT_RECORD_NUMBER));
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> s) throws Exception {
                        recordList.clear();
                        recordList = s;
                        if (null == recordList || recordList.size() == 0) {
                            mHistoryContent.setVisibility(View.GONE);
                        } else {
                            mHistoryContent.setVisibility(View.VISIBLE);
                        }
                        if (mRecordsAdapter != null) {
                            mRecordsAdapter.setData(recordList);
                            mRecordsAdapter.notifyDataChanged();
                        }
                    }
                });
    }


    @Override
    protected void onDestroy() {
        mRecordsDao.closeDatabase();
        mRecordsDao.removeNotifyDataChanged();
        super.onDestroy();
    }
}
