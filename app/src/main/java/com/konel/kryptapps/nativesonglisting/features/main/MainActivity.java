package com.konel.kryptapps.nativesonglisting.features.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.konel.kryptapps.nativesonglisting.NSLImageDownloader.ImageLoader;
import com.konel.kryptapps.nativesonglisting.NavigationUtil;
import com.konel.kryptapps.nativesonglisting.R;
import com.konel.kryptapps.nativesonglisting.features.entity.SongItem;

import java.lang.ref.WeakReference;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private ImageView ivEmptyState;
    private RecyclerView rvList;
    private MainContract.Presenter presenter;
    @Nullable
    private ImageLoader imageLoader;
    @Nullable
    private SongAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ivEmptyState = findViewById(R.id.ivEmptyState);
        rvList = findViewById(R.id.rvSongsList);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        presenter = new MainPresenter(
                new WeakReference<MainContract.View>(this),
                new NavigationUtil(this)
        );
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (imageLoader != null) {
            imageLoader.clearCache();
            imageLoader = null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (!TextUtils.isEmpty(s))
                    presenter.onQuery(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.d("SEARCH", s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void showSongs(@NonNull List<SongItem> songItems) {
        ivEmptyState.setVisibility(View.GONE);
        rvList.setVisibility(View.VISIBLE);
        if (imageLoader == null)
            imageLoader = new ImageLoader();
        if (adapter == null) {
            adapter = new SongAdapter(songItems, presenter, imageLoader);
            rvList.setAdapter(adapter);
        } else {
            adapter.updateList(songItems);
        }
    }

    @Override
    public void stopPagination() {
        if (adapter != null)
            adapter.removePagination();
    }

    @Override
    public void showEmptyState() {
        ivEmptyState.setVisibility(View.VISIBLE);
        rvList.setVisibility(View.GONE);
    }

    @Override
    public void clearSongs() {
        adapter = null;
    }

}
