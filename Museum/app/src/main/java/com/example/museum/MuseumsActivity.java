package com.example.museum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.museum.Adapter.MuseumsAdapter;
import com.example.museum.DB.RecordsDao;
import com.example.museum.Datas.Museum;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
/*
* 博物馆搜索页页面
* */
public class MuseumsActivity extends AppCompatActivity {

    private RecordsDao mRecordsDao;
    //默然展示词条个数
    private final int DEFAULT_RECORD_NUMBER = 10;
    private List<String> recordList = new ArrayList<>();
    private TagAdapter mRecordsAdapter;
    private LinearLayout mHistoryContent;
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
        final ImageView backToHome = findViewById(R.id.iv_back);
        TextView search = findViewById(R.id.iv_search);
        ImageView clearSearch = findViewById(R.id.iv_clear_search);
        mHistoryContent = findViewById(R.id.ll_history_content);
        initData();
        backToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //监听回车键按下事件
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            /**
             *
             * @param v 被监听的对象
             * @param actionId  动作标识符,如果值等于EditorInfo.IME_NULL，则回车键被按下。
             * @param event    如果由输入键触发，这是事件；否则，这是空的(比如非输入键触发是空的)。
             * @return 返回你的动作
             */
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Log.e("多行监听", actionId + "\t" + KeyEvent.KEYCODE_ENTER);
                String record = editText.getText().toString();
                if (!TextUtils.isEmpty(record)) {
                    Intent intent = new Intent(MuseumsActivity.this,MuseumActivity.class);
                    //向下个页面传递博物馆名字的参数,方便下页进行查询
                    intent.putExtra("museum_name",record);
                    startActivity(intent);
                    //添加数据
                    mRecordsDao.addRecords(record);
                }
                return event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER);
            }
        });
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
                    Intent intent = new Intent(MuseumsActivity.this,MuseumActivity.class);
                    intent.putExtra("museum_name",record);
                    startActivity(intent);
                    //添加数据
                    mRecordsDao.addRecords(record);
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
        // 隐藏标题栏
//        ActionBar actionbar = getSupportActionBar();
//        if (actionbar != null) {
//            actionbar.hide();
//        }

        // 搜索框的一些属性设置
//        SearchView searchView = (SearchView)findViewById(R.id.search_museum);
//        searchView.setIconified(false);          //展开搜索的内容提示
//        searchView.setSubmitButtonEnabled(true);//显示提交按钮
//        searchView.onActionViewExpanded();      //当展开无输入内容的时候，没有关闭的图标
//        searchView.setIconifiedByDefault(false);//默认为true在框内，设置false则在框外
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                //提交按钮的点击事件，这里应该是根据query即博物馆名称作为关键字进行查询
//                //此处需要第五小组的api,需要把博物馆的信息存到museum对象中，再作为参数传递给博物馆具体信息页面
//                searchView.clearFocus();  //可以收起键盘
//                return true;
//            }
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
////            @Override
////            public boolean onQueryTextChange(String newText) {
//                //当输入框内容改变的时候回调
////                Log.i(TAG,"内容: " + newText);
////                return true;
////            }
//        });
//        //去掉搜索框的下划线
//        View viewById1 = searchView.findViewById(searchView.getContext().getResources().getIdentifier("android:id/search_plate",null,null));
//        if (viewById1 != null) {
//            viewById1.setBackgroundColor(Color.TRANSPARENT);
//        }
//        //去掉搜索框提交箭头下面的下划线
//        View viewById2 = searchView.findViewById(searchView.getContext().getResources().getIdentifier("android:id/submit_area",null,null));
//        if (viewById2 != null) {
//            viewById2.setBackgroundColor(Color.TRANSPARENT);
//        }

        //卡片式布局显示博物馆列表
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_museumsviews);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);
        MuseumsAdapter adapter= new MuseumsAdapter();
        recyclerView.setAdapter(adapter);


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
