package com.example.zzs.testbaidumap.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.example.zzs.testbaidumap.R;
import com.example.zzs.testbaidumap.overlayutils.BikingRouteOverlay;
import com.example.zzs.testbaidumap.overlayutils.DrivingRouteOverlay;
import com.example.zzs.testbaidumap.overlayutils.TransitRouteOverlay;
import com.example.zzs.testbaidumap.overlayutils.WalkingRouteOverlay;

public class RoutemapActivity extends AppCompatActivity {
    private EditText etRoutemapActivityStart, etRoutemapActivityEnd;

    private MapView mvRoutemapActivity;

    //线路规划引擎
    private RoutePlanSearch routePlanSearch;

    private BaiduMap baiduMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routemap);
        //初始化视图
        initView();
    }

    //初始化视图
    private void initView() {
        etRoutemapActivityEnd = (EditText) findViewById(R.id.et_activity_routemap_end);
        etRoutemapActivityStart = (EditText) findViewById(R.id.et_activity_routemap_start);
        mvRoutemapActivity = (MapView) findViewById(R.id.mv_activity_routemap);
        baiduMap = mvRoutemapActivity.getMap();
        //获取实例
        routePlanSearch = RoutePlanSearch.newInstance();

        //设置路线检索的监听者,拿到结果
        routePlanSearch.setOnGetRoutePlanResultListener(new OnGetRoutePlanResultListener() {

            @Override
            public void onGetWalkingRouteResult(WalkingRouteResult result) {
                // 步行
                if (result != null && result.error == SearchResult.ERRORNO.NO_ERROR) {

                    baiduMap.clear();

                    WalkingRouteOverlay overlay = new WalkingRouteOverlay(baiduMap);

                    //百度地图计算合适的线路
                    overlay.setData(result.getRouteLines().get(0));
                    overlay.addToMap();
                    overlay.zoomToSpan();//缩放到合适的比例


                } else {
                    Toast.makeText(getApplicationContext(), "数据异常, 获取数据失败, 查询结果不存在", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onGetTransitRouteResult(TransitRouteResult result) {
                //  公交

                if (result != null && result.error == SearchResult.ERRORNO.NO_ERROR) {
                    baiduMap.clear();
                    TransitRouteOverlay overlay = new TransitRouteOverlay(baiduMap);
                    overlay.setData(result.getRouteLines().get(0));
                    overlay.addToMap();
                    overlay.zoomToSpan();

                } else {
                    Toast.makeText(getApplicationContext(), "数据异常, 获取数据失败, 查询结果不存在", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onGetDrivingRouteResult(DrivingRouteResult result) {
                //  自驾


                if (result != null && result.error == SearchResult.ERRORNO.NO_ERROR) {
                    baiduMap.clear();
                    DrivingRouteOverlay overlay = new DrivingRouteOverlay(baiduMap);
                    overlay.setData(result.getRouteLines().get(0));
                    overlay.addToMap();
                    overlay.zoomToSpan();

                } else {
                    Toast.makeText(getApplicationContext(), "数据异常, 获取数据失败, " +
                            "查询结果不存在", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onGetBikingRouteResult(BikingRouteResult result) {
                // 骑行
                if (result != null && result.error == SearchResult.ERRORNO.NO_ERROR) {
                    baiduMap.clear();
                    BikingRouteOverlay overlay = new BikingRouteOverlay(baiduMap);
                    overlay.setData(result.getRouteLines().get(0));
                    overlay.addToMap();
                    overlay.zoomToSpan();

                } else {
                    Toast.makeText(getApplicationContext(), "数据异常, 获取数据失败, " +
                            "查询结果不存在", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void onClickButton(View view) {

        PlanNode startPlanNode = PlanNode.withCityNameAndPlaceName("北京",
                etRoutemapActivityStart.getText().toString().trim());
        PlanNode endPlanNode = PlanNode.withCityNameAndPlaceName("北京",
                etRoutemapActivityEnd.getText().toString().trim());


        switch (view.getId()) {
            case R.id.btn_activity_routemap_driving:

                routePlanSearch.drivingSearch(new DrivingRoutePlanOption()
                        .from(startPlanNode).to(endPlanNode));

                break;

            case R.id.btn_activity_routemap_walking:

                routePlanSearch.walkingSearch(new WalkingRoutePlanOption()
                        .from(startPlanNode).to(endPlanNode));

                break;

            case R.id.btn_activity_routemap_bus:

                routePlanSearch.transitSearch(new TransitRoutePlanOption()
                        .city("北京").from(startPlanNode).to(endPlanNode));

                break;
        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        mvRoutemapActivity.onDestroy();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        mvRoutemapActivity.onResume();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        mvRoutemapActivity.onPause();
    }

}

