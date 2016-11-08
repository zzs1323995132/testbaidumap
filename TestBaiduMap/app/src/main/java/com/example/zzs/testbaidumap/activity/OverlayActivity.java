package com.example.zzs.testbaidumap.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.LogoPosition;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.zzs.testbaidumap.R;

/**
 * 添加覆盖物
 * 步骤:
 * 1,获取Baidumap对象
 * 2,给Baidumap添加覆盖物  addOverlay
 * 3,给覆盖物添加点击监听
 */
public class OverlayActivity extends AppCompatActivity {
    private MapView mvOverlayActivity;
    private BaiduMap baiduMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overlay);
        //初始化视图
        initView();
        //设置地图
        setMap();
    }

    //设置地图
    private void setMap() {
        //获取百度地图对象
        baiduMap = mvOverlayActivity.getMap();
        //设置百度地图图标位置
        mvOverlayActivity.setLogoPosition(LogoPosition.logoPostionRightBottom);
        /**
         * 地图类型
         * MAP_TYPE_NORMAL 普通的
         * MAP_TYPE_SATELLITE 卫星
         * MAP_TYPE_NONE 空白的
         */
        //baiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
        //开启交通图
        //baiduMap.setTrafficEnabled(true);
        //开启热力地图
        //baiduMap.setBaiduHeatMapEnabled(true);
        //获取图标
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory
                .fromResource(R.mipmap.icon_gcoding);
        //获取当前坐标
        final LatLng latLng = new LatLng(40.089597, 116.419385);
        //获取OverlayOptions 对象   设置图标
        OverlayOptions options = new MarkerOptions().icon(bitmapDescriptor)
                .position(latLng).title("天通苑");
        //添加覆盖物
        baiduMap.addOverlay(options);
        //覆盖物的点击监听
        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                Button btn = new Button(OverlayActivity.this);
                btn.setBackgroundColor(Color.WHITE);
                btn.setText(marker.getTitle());
                BitmapDescriptor descriptor = BitmapDescriptorFactory.fromView(btn);
                /**
                 * 参数1:图片
                 * 参数2:坐标点
                 * 参数3:y轴的偏移量
                 * 参数4:提示的监听
                 */
                InfoWindow infoWindow = new InfoWindow(descriptor, latLng, -100, new InfoWindow.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick() {
                        //隐藏
                        baiduMap.hideInfoWindow();
                    }
                });
                //设置提示
                baiduMap.showInfoWindow(infoWindow);
                return false;
            }
        });
    }

    //初始化视图
    private void initView() {
        mvOverlayActivity = (MapView) findViewById(R.id.mv_activity_overlay);
    }
}
