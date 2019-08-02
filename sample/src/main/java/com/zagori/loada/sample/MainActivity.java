package com.zagori.loada.sample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zagori.loada.Loada;
import com.zagori.loada.interfaces.JsonArrayListener;
import com.zagori.loada.models.ResponseError;
import com.zagori.loada.sample.adapters.PinAdapter;
import com.zagori.loada.sample.models.Pin;
import com.zagori.loada.sample.utils.Constants;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private PublishProcessor<Integer> paginator = PublishProcessor.create();

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private StaggeredGridLayoutManager layoutManager;

    private boolean loading = false;

    private List<Pin> pinList;
    private PinAdapter adapter;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gson = new Gson();
        pinList = new ArrayList<>();

        recyclerView = findViewById(R.id.recycler_view);
        progressBar = findViewById(R.id.progress_bar);

        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setOrientation(RecyclerView.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);
        adapter = new PinAdapter(this, pinList);
        recyclerView.setAdapter(adapter);

        setUpLoadMoreListener();
        subscribeForData();
        loadJsonFile();
    }

    public void scrollToTop(View view){
        recyclerView.smoothScrollToPosition(0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    private void setUpLoadMoreListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                // check is recycler still can scroll up, and show/hide FAB based on that
                if (recyclerView.canScrollVertically(-1)) {
                    findViewById(R.id.fab).setVisibility(View.VISIBLE);
                } else findViewById(R.id.fab).setVisibility(View.INVISIBLE);

                if (layoutManager.getItemCount() == pinList.size() && !recyclerView.canScrollVertically(1)){
                    Toast.makeText(MainActivity.this, "No more pins", Toast.LENGTH_SHORT).show();
                    return;
                }

                // layoutManager.getItemCount returns the 1st index of the next load list
                if (!loading && !recyclerView.canScrollVertically(1)){
                    paginator.onNext(layoutManager.getItemCount());
                }
            }
        });
    }

    private void subscribeForData() {
        Disposable disposable = paginator
                .onBackpressureDrop()
                .doOnNext(page -> {
                    loading = true;
                    progressBar.setVisibility(View.VISIBLE);
                })
                .concatMapSingle(firstIndex ->
                        loadPinsFromSource(firstIndex)
                                .subscribeOn(Schedulers.io())
                                .doOnError(throwable -> {
                                    // handle error
                                    Log.e(TAG, throwable.toString());
                                })
                )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(pins -> {
                    adapter.addPins(pins);
                    loading = false;
                    progressBar.setVisibility(View.INVISIBLE);
                } );

        compositeDisposable.add(disposable);
    }

    /*
     * This method will get the full-raw load from the pinboard api
     * and parse it as List<Pin>
     * */
    public void loadJsonFile() {
        Loada.get(this).loadJsonArray(Constants.BASE_API_PIN_BOARD, new JsonArrayListener() {
            @Override
            public void onSuccess(JSONArray result) {

                // parse result to the pinList
                pinList = gson.fromJson(result.toString(), new TypeToken<List<Pin>>() {}.getType());

                // let us duplicate the list
                /*List<Pin> newList = pinList;
                for (int i = 0; i < 3; i++){
                    pinList.addAll(newList);
                }*/

                // load first index is 0
                paginator.onNext(0);
            }

            @Override
            public void onError(ResponseError error) {
                Log.e(TAG, "Error code: " + error.getCode());
                Log.e(TAG, "Error data: " + error.getData());
                Toast.makeText(MainActivity.this, "Something went wrong while loading pinboard", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*
     * This method will help keep loading pins from the pins full load
     *
     * @param firstIndex is the start point of the load to be returned
     * */
    private Single<List<Pin>> loadPinsFromSource(int firstIndex) {
        return Single.just(true)
                // 1 sec delay just to show the loading.
                // There will be no need to set delay in case the call is made to an API that
                // allows pagination, as the network request itself causes a delay.
                .delay(1, TimeUnit.SECONDS)
                .map(value -> getSubList(pinList, firstIndex, Constants.API_PAGE_LOAD_SIZE))
                .doOnError(throwable -> {
                    Log.e(TAG, "Something went Wrong: " + throwable);
                });
    }

    /*
     * This method is a helper of the API, as it does not support pagination,
     * It breaks down the result from the api and return a specific portion of the result.
     *
     * @param originList is the complete result list of the api
     * @param startIndex is start point of the returned sublist. It is inclusive.
     * @param pageList is the sublist
     * */
    private static List<Pin> getSubList(List<Pin> originList, int startIndex, int pageSize){

        // if the original list
        if (startIndex >= originList.size()){
            originList.clear();
            return originList; // return an empty list
        }
        // find the end point of the sublist.
        // Add +1 as subList excludes endIndex from the returned list
        int endIndex = startIndex + pageSize;

        // returned what is left of the origin list
        if (endIndex >= originList.size())
            endIndex = originList.size();

        return originList.subList(startIndex, endIndex);
    }
}
