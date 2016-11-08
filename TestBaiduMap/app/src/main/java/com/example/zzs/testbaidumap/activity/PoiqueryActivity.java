package com.example.zzs.testbaidumap.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.example.zzs.testbaidumap.R;
import com.example.zzs.testbaidumap.overlayutils.PoiOverlay;

import java.util.List;

/**
 * 1,获取对象
 * 2,设置监听
 * 3,开启搜索
 */
public class PoiqueryActivity extends AppCompatActivity {
    private MapView mvPoiqueryActivity;
    private EditText etPoiqueryActivity;
    private BaiduMap baiduMap;
    private PoiSearch mPoiSearch;//Poi检索的对象
    private PoiInfo poiInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poiquery);
        //初始化视图
        initView();
    }

    //点击按钮开始搜索
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_activity_poiquery:
                String keyStr = etPoiqueryActivity.getText().toString().trim();
                if (TextUtils.isEmpty(keyStr)) {
                    return;
                }
                //实例化检索对象
                mPoiSearch = PoiSearch.newInstance();

                //Poi搜索的监听
                mPoiSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
                    @Override
                    public void onGetPoiResult(PoiResult poiResult) {
                        //Poi搜索的结果
                        //清除之前的数据
                        baiduMap.clear();
                        MyPoiOverlay myPoiOverlay = new MyPoiOverlay(baiduMap);
                        myPoiOverlay.setData(poiResult);
                        baiduMap.setOnMarkerClickListener(myPoiOverlay);
                        myPoiOverlay.addToMap();

                    }

                    @Override
                    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
                        //得到结果的详情
                        Intent intent = new Intent(PoiqueryActivity.this, PoiqueryDetailActivity.class);
                        intent.putExtra("url", poiDetailResult.getDetailUrl());
                        startActivity(intent);

                    }

                    @Override
                    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {
                        //获取室内的结果

                    }
                });
                //开启搜索    搜索所在的城市    关键字
                mPoiSearch.searchInCity(new PoiCitySearchOption().city("北京").keyword(keyStr));


//        LatLng llC = new LatLng(39.639723, 118.425541);
//        LatLng llD = new LatLng(39.906965, 118.401394);
                //坐标点的附近
//        mPoiSearch.searchNearby(new PoiNearbySearchOption().location(llC).keyword())
                //区域  设置俩个坐标点  左上 右下
//        LatLngBounds latLngBounds = new LatLngBounds.Builder().include(llC).include(llD).build();
//        mPoiSearch.searchInBound(new PoiBoundSearchOption().bound(latLngBounds))

                break;
        }
    }

    //初始化视图
    private void initView() {
        mvPoiqueryActivity = (MapView) findViewById(R.id.mv_activity_poiquery);
        etPoiqueryActivity = (EditText) findViewById(R.id.et_activity_poiquery);
        baiduMap = mvPoiqueryActivity.getMap();
    }

    //Poi搜索的覆盖物
    class MyPoiOverlay extends PoiOverlay {

        /**
         * 构造函数
         *
         * @param baiduMap 该 PoiOverlay 引用的 BaiduMap 对象
         */
        public MyPoiOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        //点击覆盖物的监听
        @Override
        public boolean onPoiClick(int i) {
            //获取Poi所有节点
            List<PoiInfo> list = getPoiResult().getAllPoi();
            //获取点击的节点
            poiInfo = list.get(i);
            Button button = new Button(getApplicationContext());
            button.setBackgroundColor(Color.GREEN);
            button.setText(poiInfo.name);
            BitmapDescriptor descriptor = BitmapDescriptorFactory.fromView(button);
            InfoWindow infoWindow = new InfoWindow(descriptor, poiInfo.location, -100, new InfoWindow.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick() {
                    //根据Uid搜索详情
                    mPoiSearch.searchPoiDetail(new PoiDetailSearchOption().poiUid(poiInfo.uid));
                    baiduMap.hideInfoWindow();
                }
            });
            baiduMap.showInfoWindow(infoWindow);
            return super.onPoiClick(i);
        }
    }
}
