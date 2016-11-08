package com.example.zzs.testbaidumap.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.example.zzs.testbaidumap.R;
import com.example.zzs.testbaidumap.overlayutils.DrivingRouteOverlay;

import java.util.List;

/**
 * 路线规划步骤:
 * 1,获取路线规划对象RoutePlanSearch
 * 2,对路线规划对象进行监听
 * 3,开启路线查询(有四种)
 * 4,在监听中 进行处理  获取路线覆盖物 将其添加到地图  可以进行点击监听节点
 */
public class RouteActivity extends AppCompatActivity {
    private MapView mvRouteActivity;
    private BaiduMap baiduMap;
    //路线规划的对象
    private RoutePlanSearch routePlanSearch;
    private DrivingRouteLine drivingRouteLine;//这个路线展示到Map

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        //初始化视图
        initView();
    }

    //初始化视图
    private void initView() {
        mvRouteActivity = (MapView) findViewById(R.id.mv_activity_route);
        //获取百度地图对象
        baiduMap = mvRouteActivity.getMap();
        //获取路线规划对象
        routePlanSearch = RoutePlanSearch.newInstance();
        //对路线规划进行监听
        routePlanSearch.setOnGetRoutePlanResultListener(new OnGetRoutePlanResultListener() {
            @Override
            public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {
                //走路的规划
            }

            @Override
            public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {
                //公交的规划

            }

            @Override
            public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {
                //自驾的规划
                //获取所有的路线
                List<DrivingRouteLine> lines = drivingRouteResult.getRouteLines();
                if (lines != null && lines.size() > 0) {//说明有路线
                    Log.e("AAA", "===" + lines.size());
                    drivingRouteLine = lines.get(0);
                } else {
                    Log.e("AAA", "==没有路==");
                }

                //将路线展示到地图  以覆盖物
                MyOverlay myOverlay = new MyOverlay(baiduMap);
                //将开车的路线  给覆盖物
                myOverlay.setData(drivingRouteLine);
                //对路线覆盖物监听
                baiduMap.setOnMarkerClickListener(myOverlay);
                //将覆盖物添加到地图
                myOverlay.addToMap();

            }

            @Override
            public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {
                //骑行的规划
            }
        });
        //开启路线查询
        routePlanSearch.drivingSearch(new DrivingRoutePlanOption().
                from(PlanNode.withCityNameAndPlaceName("北京", "北京科技职业学院")).
                to(PlanNode.withCityNameAndPlaceName("北京", "北京西站")));
    }

    //开车的覆盖物
    public class MyOverlay extends DrivingRouteOverlay {

        /**
         * 构造函数
         *
         * @param baiduMap 该DrivingRouteOvelray引用的 BaiduMap
         */
        public MyOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        //路线节点的 点击监听
        @Override
        public boolean onRouteNodeClick(int i) {
            List<DrivingRouteLine.DrivingStep> lists = drivingRouteLine.getAllStep();
            DrivingRouteLine.DrivingStep step = lists.get(i);
            Log.e("AAAA", "==>" + step.getInstructions());
            return super.onRouteNodeClick(i);
        }
    }
}
