package squaring.vitrox.paginationlist;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.view.ViewGroup.LayoutParams;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import javax.inject.Inject;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import squaring.vitrox.paginationlist.Adapter.ImagesAdapter;
import squaring.vitrox.paginationlist.Adapter.OnItemClickListener;
import squaring.vitrox.paginationlist.Common.PaginationHelper;
import squaring.vitrox.paginationlist.Common.SearchHelper;
import squaring.vitrox.paginationlist.Common.Utils;
import squaring.vitrox.paginationlist.Model.ApiResult;
import squaring.vitrox.paginationlist.Network.ApiService;

public class MainActivity extends BaseActivity implements OnItemClickListener{

    private Button mSearchButton;
    private RecyclerView mListRecyclerView;
    private ImagesAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private ProgressBar progressBar;
    private EditText mSearchText;
    private Boolean mIsLoading;

    @Inject
    ApiService apiService;
    @Inject
    PaginationHelper paginationHelper;
    @Inject
    SearchHelper searchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getComponent().inject(this);
        progressBar =(ProgressBar)findViewById(R.id.spinner);
        mAdapter= new ImagesAdapter(this, this);
        mLayoutManager = new LinearLayoutManager(this);
        mListRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mListRecyclerView.setLayoutManager(mLayoutManager);
        mListRecyclerView.setAdapter(mAdapter);
        mListRecyclerView.addOnScrollListener(myScrollListener);
        mSearchText=(EditText) findViewById(R.id.search_edittext);
        mSearchButton=(Button)findViewById(R.id.search_button);
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchHelper.isNotSameOrEmpty(mSearchText.getText().toString())) {
                    searchHelper.setCurrentSearch(mSearchText.getText().toString());
                    Utils.hideSoftKeyboard(MainActivity.this);
                    resetAdapter();
                    loadDataList();
                }else
                {
                    SendErrorMessage(getResources().getString(R.string.error_empty_text));
                }
            }
        });
    }

    private void loadDataList()
    {
        loading(true);
        final String page = paginationHelper.getCurrentPage() == 0 ? "1" : String.valueOf(paginationHelper.getNextPage());
        apiService.getImageList(page,searchHelper.getCurrentSearch())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Observer<ApiResult>() {
                    @Override
                    public void onCompleted() {
                        loading(false);
                    }
                    @Override
                    public void onError(Throwable e) {
                        SendErrorMessage(e.getMessage());
                    }

                    @Override
                    public void onNext(ApiResult apiResult) {
                        if (apiResult.getResultCount()>0) {
                            if (page.equals("1")) {
                                paginationHelper = new PaginationHelper(apiResult.getResultCount());
                            }
                            mAdapter.addMoreData(apiResult.getImages());
                        }else{SendErrorMessage(getResources().getString(R.string.no_images_found));}
                    }
                });
    }

    @Override
    public void onItemClick(String caption) {
        LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.custom_popup,null);
        final PopupWindow popupWindow = new PopupWindow(
                customView,
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
        );
        if(Build.VERSION.SDK_INT>=21){
            popupWindow.setElevation(5.0f);
        }
        final TextView text= (TextView) customView.findViewById(R.id.popup_caption);
        RelativeLayout rLayout= (RelativeLayout) customView.findViewById(R.id.rl_custom_layout);
        text.setText(caption);
        ImageButton closeButton = (ImageButton) customView.findViewById(R.id.ib_close);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        popupWindow.showAtLocation(rLayout, Gravity.CENTER, 0, 0);
    }

    private RecyclerView.OnScrollListener myScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = mLayoutManager.getChildCount();
            int totalItemCount = mLayoutManager.getItemCount();
            int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();
            if (!mIsLoading && !paginationHelper.isLastPage()) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= paginationHelper.getPageSize()) {
                    loadDataList();
                }
            }
        }
    };

    private void SendErrorMessage(final String txt) {
        //Show errors always on main thread
        this.runOnUiThread(new Runnable() {
            public void run() {
                Snackbar.make(findViewById(android.R.id.content),txt,Snackbar.LENGTH_LONG).show();
            }
        });
    }
    private void loading(Boolean b) {
        mIsLoading = b;
        if (mIsLoading) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
    private void resetAdapter() {
        paginationHelper.setCurrentPage(0);
        this.runOnUiThread(new Runnable() {
            public void run() {
                mAdapter.resetData();
            }
        });
    }

}
