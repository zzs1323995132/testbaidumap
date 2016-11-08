package com.example.zzs.testbaidumap.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.search.busline.BusLineResult;
import com.baidu.mapapi.search.busline.BusLineSearch;
import com.baidu.mapapi.search.busline.BusLineSearchOption;
import com.baidu.mapapi.search.busline.OnGetBusLineSearchResultListener;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.example.zzs.testbaidumap.R;
import com.example.zzs.testbaidumap.overlayutils.BusLineOverlay;

public class BuselineActivity extends AppCompatActivity {
    private MapView mvBuselineActivity;
    private EditText etBuselineActivity;
    private BaiduMap baiduMap;
    private PoiSearch poiSearch;
    private BusLineSearch busLineSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buseline);
        //.初始化视图
        initView();
    }

    //初始化视图
    private void initView() {
        mvBuselineActivity = (MapView) findViewById(R.id.mv_activity_busline);
        etBuselineActivity = (EditText) findViewById(R.id.et_activity_busline);
        baiduMap = mvBuselineActivity.getMap();
        busLineSearch = BusLineSearch.newInstance();
        busLineSearch.setOnGetBusLineSearchResultListener(new OnGetBusLineSearchResultListener() {
            @Override
            public void onGetBusLineResult(BusLineResult busLineResult) {
                if (busLineResult != null && busLineResult.error == SearchResult.ERRORNO.NO_ERROR) {

                    baiduMap.clear();
                    BusLineOverlay overlay = new BusLineOverlay(baiduMap);
                    overlay.setData(busLineResult);
                    overlay.addToMap();

                }
            }
        });

        //poi
        poiSearch = PoiSearch.newInstance();
        poiSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {
                if (poiResult != null && poiResult.error == SearchResult.ERRORNO.NO_ERROR) {
                    String uid = poiResult.getAllPoi().get(0).uid;
                    busLineSearch.searchBusLine(new BusLineSearchOption().city("北京").uid(uid));
                }

            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

            }

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

            }
        });
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_activity_busline:
                //开启搜索   城市   关键字  公交号 476
                poiSearch.searchInCity(new PoiCitySearchOption().city("北京").keyword(etBuselineActivity.getText().toString().trim()));
                break;
        }
    }
}
