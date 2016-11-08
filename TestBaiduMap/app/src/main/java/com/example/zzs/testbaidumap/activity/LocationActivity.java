package com.example.zzs.testbaidumap.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.zzs.testbaidumap.R;

public class LocationActivity extends AppCompatActivity {
    private MapView mvLocationActivity;
    private BaiduMap baiduMap;
    //定位对象
    private LocationClient locationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        //初始化视图
        initView();
    }

    //初始化视图
    private void initView() {
        mvLocationActivity = (MapView) findViewById(R.id.mv_actovity_location);
        baiduMap = mvLocationActivity.getMap();
        locationClient = new LocationClient(LocationActivity.this);
        //对定位的设置
        LocationClientOption option = new LocationClientOption();
        /**
         * LocationClientOption.LocationMode.Battery_Saving:低功耗定位  不用GPS  (wifi  基站)
         *
         * LocationClientOption.LocationMode.Hight_Accuracy:高精度定位  全开GPS  wifi  基站
         *
         * LocationClientOption.LocationMode.Device_Sensors  仅仅使用设备 GPS    不支持室内
         */
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        /**
         * bd09ll:百度经纬度坐标
         * bd09:墨卡托坐标
         * gcjo2:国家测量局坐标
         */
        option.setCoorType("bd09ll");
        //设置定位间隔时间
        option.setScanSpan(1000 * 2);
        //设置定位超出时间
        option.setTimeOut(1000 * 10);
        //是否需要得到地址
        option.setIsNeedAddress(true);
        //是否设置手机 机头方向
        option.setNeedDeviceDirect(true);
        //2给定位对象进行设置
        locationClient.setLocOption(option);
        //3监听
        locationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                //获取坐标
                LatLng latLng = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
                BitmapDescriptor descriptor = BitmapDescriptorFactory.fromResource(R.mipmap.icon_gcoding);
                OverlayOptions options = new MarkerOptions().position(latLng).icon(descriptor).title("当前位置");
                baiduMap.addOverlay(options);
            }
        });

        //4启动
        locationClient.start();//开启
        locationClient.requestLocation();//发起请求
    }
}
