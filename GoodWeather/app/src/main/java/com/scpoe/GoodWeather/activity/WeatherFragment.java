package com.scpoe.GoodWeather.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.scpoe.GoodWeather.R;
import com.scpoe.GoodWeather.model.ForecastDetail;
import com.scpoe.GoodWeather.model.HourDetail;
import com.scpoe.GoodWeather.model.PMDetail;
import com.scpoe.GoodWeather.model.WeatherDetail;
import com.scpoe.GoodWeather.other.MyApplication;
import com.thinkland.sdk.android.DataCallBack;
import com.thinkland.sdk.android.JuheData;
import com.thinkland.sdk.android.Parameters;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    SwipeRefreshLayout swipeRefreshLayout;
    String cityName;
    View view;

    WeatherDetail weatherDetail;
    List<HourDetail> hourDetails;
    PMDetail pmDetail;


    private WeatherDetail weatherDetailOri;
    private List<HourDetail> hourDetailsOri;
    private PMDetail pmDetailOri;

    //head
    private TextView headCityName;
    private TextView headPublishTime;
    //summary
    private TextView summaryWeatherText;
    private ImageView summaryWeatherImage;
    private TextView summaryTemperatureTop;
    private TextView summaryTemperatureBottom;
    private TextView summaryTemperature;
    //forecast
    private TextView forecastDayOneWeek;
    private ImageView forecastDayOneWeatherImage;
    private TextView forecastDayOneTemperatureTop;
    private TextView forecastDayOneTemperatureBottom;
    private TextView forecastDayTwoWeek;
    private ImageView forecastDayTwoWeatherImage;
    private TextView forecastDayTwoTemperatureTop;
    private TextView forecastDayTwoTemperatureBottom;
    private TextView forecastDayThreeWeek;
    private ImageView forecastDayThreeWeatherImage;
    private TextView forecastDayThreeTemperatureTop;
    private TextView forecastDayThreeTemperatureBottom;
    //detail
    private TextView detailTemperature;
    private TextView detailHumidity;
    private TextView detailWind;
    private TextView detailUV;
    private TextView detailClothes;
    //hour
    private TextView hourOneTime;
    private ImageView hourOneWeather;
    private TextView hourOneTemperature;
    private TextView hourTwoTime;
    private ImageView hourTwoWeather;
    private TextView hourTwoTemperature;
    private TextView hourThreeTime;
    private ImageView hourThreeWeather;
    private TextView hourThreeTemperature;
    private TextView hourFourTime;
    private ImageView hourFourWeather;
    private TextView hourFourTemperature;
    private TextView hourFiveTime;
    private ImageView hourFiveWeather;
    private TextView hourFiveTemperature;
    //pm
    private TextView pmValue;
    private TextView pmSituation;

    public WeatherFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_weather, container, false);
        //
        Bundle bundle = getArguments();
        cityName = bundle.getString("city");
        //set swipe refresh
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.refresh_one, R.color.refresh_two, R.color.refresh_three, R.color.refresh_four);
        swipeRefreshLayout.setOnRefreshListener(this);
        //init view
        initView();
        setData();
        return view;
    }

    @Override
    public void onRefresh() {
        setData();
        swipeRefreshLayout.setRefreshing(false);
    }

    public void initView() {
        //find view by id
        //head
        headCityName = (TextView) view.findViewById(R.id.weather_city);
        headPublishTime = (TextView) view.findViewById(R.id.weather_publish_time);
        //summary
        summaryWeatherText = (TextView) view.findViewById(R.id.summary_weather);
        summaryWeatherImage = (ImageView) view.findViewById(R.id.summary_weather_image);
        summaryTemperatureTop = (TextView) view.findViewById(R.id.summary_temperature_top);
        summaryTemperatureBottom = (TextView) view.findViewById(R.id.summary_temperature_bottom);
        summaryTemperature = (TextView) view.findViewById(R.id.summary_temperature);
        //forecast
        forecastDayOneWeek = (TextView) view.findViewById(R.id.forecast_day_one);
        forecastDayOneWeatherImage = (ImageView) view.findViewById(R.id.forecast_day_one_weather);
        forecastDayOneTemperatureTop = (TextView) view.findViewById(R.id.forecast_day_one_temperature_up);
        forecastDayOneTemperatureBottom = (TextView) view.findViewById(R.id.forecast_day_one_temperature_bottom);
        forecastDayTwoWeek = (TextView) view.findViewById(R.id.forecast_day_two);
        forecastDayTwoWeatherImage = (ImageView) view.findViewById(R.id.forecast_day_two_weather);
        forecastDayTwoTemperatureTop = (TextView) view.findViewById(R.id.forecast_day_two_temperature_up);
        forecastDayTwoTemperatureBottom = (TextView) view.findViewById(R.id.forecast_day_two_temperature_bottom);
        forecastDayThreeWeek = (TextView) view.findViewById(R.id.forecast_day_three);
        forecastDayThreeWeatherImage = (ImageView) view.findViewById(R.id.forecast_day_three_weather);
        forecastDayThreeTemperatureTop = (TextView) view.findViewById(R.id.forecast_day_three_temperature_up);
        forecastDayThreeTemperatureBottom = (TextView) view.findViewById(R.id.forecast_day_three_temperature_bottom);
        //detail
        detailTemperature = (TextView) view.findViewById(R.id.detail_temperature);
        detailHumidity = (TextView) view.findViewById(R.id.detail_humidity);
        detailWind = (TextView) view.findViewById(R.id.detail_wind);
        detailUV = (TextView) view.findViewById(R.id.detail_uv);
        detailClothes = (TextView) view.findViewById(R.id.detail_clothes);
        //hour
        hourOneTime = (TextView) view.findViewById(R.id.forecast_hour_one_time);
        hourOneWeather = (ImageView) view.findViewById(R.id.forecast_hour_one_weather);
        hourOneTemperature = (TextView) view.findViewById(R.id.forecast_hour_one_temperature);
        hourTwoTime = (TextView) view.findViewById(R.id.forecast_hour_two_time);
        hourTwoWeather = (ImageView) view.findViewById(R.id.forecast_hour_two_weather);
        hourTwoTemperature = (TextView) view.findViewById(R.id.forecast_hour_two_temperature);
        hourThreeTime = (TextView) view.findViewById(R.id.forecast_hour_three_time);
        hourThreeWeather = (ImageView) view.findViewById(R.id.forecast_hour_three_weather);
        hourThreeTemperature = (TextView) view.findViewById(R.id.forecast_hour_three_temperature);
        hourFourTime = (TextView) view.findViewById(R.id.forecast_hour_four_time);
        hourFourWeather = (ImageView) view.findViewById(R.id.forecast_hour_four_weather);
        hourFourTemperature = (TextView) view.findViewById(R.id.forecast_hour_four_temperature);
        hourFiveTime = (TextView) view.findViewById(R.id.forecast_hour_five_time);
        hourFiveWeather = (ImageView) view.findViewById(R.id.forecast_hour_five_weather);
        hourFiveTemperature = (TextView) view.findViewById(R.id.forecast_hour_five_temperature);
        //pm
        pmValue = (TextView) view.findViewById(R.id.summary_pm_value);
        pmSituation = (TextView) view.findViewById(R.id.summary_pm_situation);
    }

    public void setData() {
        weatherDetail = getDataWeather();
        hourDetails = getDataHour();
        pmDetail = getDataPM();
    }

    public int setWeatherImage(String weather, String summaryTime, String hourTime) {
        if (weather.equals("晴")) {
            if (summaryTime != null) {
                String[] stringTime = summaryTime.split(":");
                int intTime = Integer.parseInt(stringTime[0]);
                if (intTime < 6 || intTime > 19) {
                    return R.drawable.ic_weather_night_grey600_24dp;
                } else {
                    return R.drawable.ic_weather_sunny_grey600_24dp;
                }
            } else {
                if (hourTime != null) {
                    int time = Integer.parseInt(hourTime);
                    if (time < 6 || time > 19) {
                        return R.drawable.ic_weather_night_grey600_24dp;
                    } else {
                        return R.drawable.ic_weather_sunny_grey600_24dp;
                    }
                } else {
                    return R.drawable.ic_weather_sunny_grey600_24dp;
                }
            }
        } else if (weather.equals("多云") || weather.equals("多云转晴")) {
            return R.drawable.ic_weather_partlycloudy_grey600_24dp;
        } else if (weather.equals("阴") || weather.equals("阵雨转阴") || weather.equals("阴转多云")) {
            return R.drawable.ic_weather_cloudy_grey600_24dp;
        } else if (weather.equals("阵雨") || weather.equals("雨夹雪") || weather.equals("小雨") || weather.equals("中雨") || weather.equals("冻雨") || weather.equals("小雨-中雨")) {
            return R.drawable.ic_weather_rainy_grey600_24dp;
        } else if (weather.equals("雷阵雨伴有冰雹")) {
            return R.drawable.ic_weather_hail_grey600_24dp;
        } else if (weather.equals("大雨") || weather.equals("暴雨") || weather.equals("大暴雨") || weather.equals("特大暴雨") || weather.equals("中雨-大雨") || weather.equals("大雨-暴雨") || weather.equals("暴雨-大暴雨") || weather.equals("大暴雨-特大暴雨")) {
            return R.drawable.ic_weather_pouring_grey600_24dp;
        } else if (weather.equals("阵雪") || weather.equals("小雪") || weather.equals("中雪") || weather.equals("大雪") || weather.equals("暴雪") || weather.equals("小雪-中雪") || weather.equals("中雪-大雪") || weather.equals("大雪-暴雪")) {
            return R.drawable.ic_weather_snowy_grey600_24dp;
        } else if (weather.equals("雾") || weather.equals("霾")) {
            return R.drawable.ic_weather_fog_grey600_24dp;
        } else if (weather.equals("沙尘暴") || weather.equals("扬沙") || weather.equals("强沙尘暴")) {
            return R.drawable.ic_weather_windy_grey600_24dp;
        } else if (weather.equals("浮尘")) {
            return R.drawable.ic_weather_windy_variant_grey600_24dp;
        } else if (weather.equals("雷阵雨")) {
            return R.drawable.ic_weather_lightning_grey600_24dp;
        } else {
            return R.drawable.ic_weather_cloudy_grey600_24dp;
        }
    }



    public WeatherDetail getDataWeather() {
        Parameters parameters = new Parameters();
        parameters.add("cityname", cityName);
        parameters.add("format", 2);
        JuheData.executeWithAPI(MyApplication.getContext(), 39, "http://v.juhe.cn/weather/index", JuheData.GET, parameters, new DataCallBack() {
            @Override
            public void onSuccess(int i, String s) {
                try {
                    weatherDetailOri = new WeatherDetail();
                    JSONObject result = new JSONObject(s).getJSONObject("result");
                    JSONObject today = result.getJSONObject("today");
                    JSONObject sk = result.getJSONObject("sk");
                    //base
                    weatherDetailOri.setCityName(today.getString("city"));
                    weatherDetailOri.setPublishTime(sk.getString("time"));
                    //summary
                    weatherDetailOri.setSummaryWeather(today.getString("weather"));
                    String[] summaryTemperature = today.getString("temperature").split("~");
                    weatherDetailOri.setSummaryTemperatureTop(summaryTemperature[1]);
                    weatherDetailOri.setSummaryTemperatureBottom(summaryTemperature[0]);
                    weatherDetailOri.setSummaryTemperature(sk.getString("temp"));
                    //forecast
                    JSONArray forecast = result.getJSONArray("future");
                    List<ForecastDetail> forecastDetails = new ArrayList<ForecastDetail>();
                    for (int j = 0; j < forecast.length(); j++) {
                        ForecastDetail forecastDetail = new ForecastDetail();
                        JSONObject forecastDayDetail = forecast.getJSONObject(j);
                        forecastDetail.setForecastDay(forecastDayDetail.getString("week"));
                        String[] forecastDayTemp = forecastDayDetail.getString("temperature").split("~");
                        forecastDetail.setForecastDayTemperatureTop(forecastDayTemp[1]);
                        forecastDetail.setForecastDayTemperatureBottom(forecastDayTemp[0]);
                        forecastDetail.setForecastDayWeather(forecastDayDetail.getString("weather"));
                        forecastDetails.add(forecastDetail);
                        if (forecastDetails.size() == 4) {
                            break;
                        }
                    }
                    weatherDetailOri.setForecastList(forecastDetails);
                    //detail
                    weatherDetailOri.setDetailHumidity(sk.getString("humidity"));
                    weatherDetailOri.setDetailWind(sk.getString("wind_direction") + " " + sk.getString("wind_strength"));
                    weatherDetailOri.setDetailUV(today.getString("uv_index"));
                    weatherDetailOri.setDetailClothes(today.getString("dressing_index"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFinish() {
                if (weatherDetailOri != null) {
                    setDataWeather();
                }
                System.out.println(">>> get weather success <<<");
            }

            @Override
            public void onFailure(int i, String s, Throwable throwable) {
                if (i == 30002) {
                    Toast.makeText(MyApplication.getContext(), "没有检测到网络", Toast.LENGTH_SHORT).show();
                } else if (i == 30003) {
                    Toast.makeText(MyApplication.getContext(), "没有进行初始化", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MyApplication.getContext(), "获取数据失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return weatherDetailOri;
    }

    public void setDataWeather() {
        //set view
        //head
        headCityName.setText(weatherDetailOri.getCityName());
        headPublishTime.setText("信息于 " + weatherDetailOri.getPublishTime() + " 发布");
        //summary
        summaryWeatherText.setText(weatherDetailOri.getSummaryWeather());
        summaryWeatherImage.setImageResource(setWeatherImage(weatherDetailOri.getSummaryWeather(), weatherDetailOri.getPublishTime(), null));
        summaryTemperatureTop.setText(weatherDetailOri.getSummaryTemperatureTop());
        summaryTemperatureBottom.setText(weatherDetailOri.getSummaryTemperatureBottom());
        summaryTemperature.setText(weatherDetailOri.getSummaryTemperature() + " ℃");
        //forecast
        forecastDayOneWeek.setText(weatherDetailOri.getForecastList().get(1).getForecastDay());
        forecastDayOneWeatherImage.setImageResource(setWeatherImage(weatherDetailOri.getForecastList().get(1).getForecastDayWeather(), null, null));
        forecastDayOneTemperatureTop.setText(weatherDetailOri.getForecastList().get(1).getForecastDayTemperatureTop());
        forecastDayOneTemperatureBottom.setText(weatherDetailOri.getForecastList().get(1).getForecastDayTemperatureBottom());
        forecastDayTwoWeek.setText(weatherDetailOri.getForecastList().get(2).getForecastDay());
        forecastDayTwoWeatherImage.setImageResource(setWeatherImage(weatherDetailOri.getForecastList().get(2).getForecastDayWeather(), null, null));
        forecastDayTwoTemperatureTop.setText(weatherDetailOri.getForecastList().get(2).getForecastDayTemperatureTop());
        forecastDayTwoTemperatureBottom.setText(weatherDetailOri.getForecastList().get(2).getForecastDayTemperatureBottom());
        forecastDayThreeWeek.setText(weatherDetailOri.getForecastList().get(3).getForecastDay());
        forecastDayThreeWeatherImage.setImageResource(setWeatherImage(weatherDetailOri.getForecastList().get(3).getForecastDayWeather(), null, null));
        forecastDayThreeTemperatureTop.setText(weatherDetailOri.getForecastList().get(3).getForecastDayTemperatureTop());
        forecastDayThreeTemperatureBottom.setText(weatherDetailOri.getForecastList().get(3).getForecastDayTemperatureBottom());
        //detail
        detailTemperature.setText(weatherDetailOri.getSummaryTemperature() + " ℃");
        detailHumidity.setText(weatherDetailOri.getDetailHumidity());
        detailWind.setText(weatherDetailOri.getDetailWind());
        detailUV.setText(weatherDetailOri.getDetailUV());
        detailClothes.setText(weatherDetailOri.getDetailClothes());
    }

    public List<HourDetail> getDataHour() {
        Parameters parameters = new Parameters();
        parameters.add("cityname", cityName);
        JuheData.executeWithAPI(MyApplication.getContext(), 39, "http://v.juhe.cn/weather/forecast3h", JuheData.GET, parameters, new DataCallBack() {
            @Override
            public void onSuccess(int i, String s) {
                try {
                    hourDetailsOri = new ArrayList<HourDetail>();
                    HourDetail hourDetail;
                    JSONArray result = new JSONObject(s).getJSONArray("result");
                    for (int j = 0; j < result.length(); j++) {
                        JSONObject hour = result.getJSONObject(j);
                        hourDetail = new HourDetail();
                        //
                        hourDetail.setHourWeather(hour.getString("weather"));
                        String time = hour.getString("sfdate");
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                        Date dateGet = simpleDateFormat.parse(time);
                        if (dateGet.getTime() > Calendar.getInstance().getTimeInMillis()) {
                            hourDetail.setHourTime(time.substring(8, 10));
                        } else {
                            continue;
                        }
                        hourDetail.setHourTemperature(hour.getString("temp1"));
                        hourDetailsOri.add(hourDetail);
                        if (hourDetailsOri.size() == 5) {
                            break;
                        }
                    }
                } catch (JSONException | ParseException e) {
                    e.printStackTrace();
                } catch (NullPointerException e) {
                    System.out.println(">>> hourDetailsOri null point exception <<<");
                }
            }

            @Override
            public void onFinish() {
                if (hourDetailsOri != null) {
                    setDataHour();
                }
                System.out.println(">>> get hour success <<<");
            }

            @Override
            public void onFailure(int i, String s, Throwable throwable) {
                if (i == 30002) {
                    Toast.makeText(MyApplication.getContext(), "没有检测到网络", Toast.LENGTH_SHORT).show();
                } else if (i == 30003) {
                    Toast.makeText(MyApplication.getContext(), "没有进行初始化", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MyApplication.getContext(), "获取数据失败", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return hourDetailsOri;
    }

    public void setDataHour() {
        //hour
        hourOneTime.setText(hourDetailsOri.get(0).getHourTime() + "时");
        hourOneWeather.setImageResource(setWeatherImage(hourDetailsOri.get(0).getHourWeather(), null, hourDetailsOri.get(0).getHourTime()));
        hourOneTemperature.setText(hourDetailsOri.get(0).getHourTemperature() + " ℃");
        hourTwoTime.setText(hourDetailsOri.get(1).getHourTime() + "时");
        hourTwoWeather.setImageResource(setWeatherImage(hourDetailsOri.get(1).getHourWeather(), null, hourDetailsOri.get(1).getHourTime()));
        hourTwoTemperature.setText(hourDetailsOri.get(1).getHourTemperature() + " ℃");
        hourThreeTime.setText(hourDetailsOri.get(2).getHourTime() + "时");
        hourThreeWeather.setImageResource(setWeatherImage(hourDetailsOri.get(2).getHourWeather(), null, hourDetailsOri.get(2).getHourTime()));
        hourThreeTemperature.setText(hourDetailsOri.get(2).getHourTemperature() + " ℃");
        hourFourTime.setText(hourDetailsOri.get(3).getHourTime() + "时");
        hourFourWeather.setImageResource(setWeatherImage(hourDetailsOri.get(3).getHourWeather(), null, hourDetailsOri.get(3).getHourTime()));
        hourFourTemperature.setText(hourDetailsOri.get(3).getHourTemperature() + " ℃");
        hourFiveTime.setText(hourDetailsOri.get(4).getHourTime() + "时");
        hourFiveWeather.setImageResource(setWeatherImage(hourDetailsOri.get(4).getHourWeather(), null, hourDetailsOri.get(4).getHourTime()));
        hourFiveTemperature.setText(hourDetailsOri.get(4).getHourTemperature() + " ℃");
    }

    public PMDetail getDataPM() {
        Parameters parameters = new Parameters();
        parameters.add("city", cityName);
        JuheData.executeWithAPI(MyApplication.getContext(), 33, "http://web.juhe.cn:8080/environment/air/pm", JuheData.GET, parameters, new DataCallBack() {
            @Override
            public void onSuccess(int i, String s) {
                try {
                    pmDetailOri = new PMDetail();
                    JSONArray resultArray = new JSONObject(s).getJSONArray("result");
                    JSONObject result = resultArray.getJSONObject(0);
                    pmDetailOri.setPm(result.getString("PM2.5"));
                    pmDetailOri.setQuality(result.getString("quality"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFinish() {
                if (pmDetailOri != null) {
                    setDataPM();
                    System.out.println(">>> get pm success <<<");
                }
            }

            @Override
            public void onFailure(int i, String s, Throwable throwable) {
                if (i == 30002) {
                    Toast.makeText(MyApplication.getContext(), "没有检测到网络", Toast.LENGTH_SHORT).show();
                } else if (i == 30003) {
                    Toast.makeText(MyApplication.getContext(), "没有进行初始化", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MyApplication.getContext(), "获取数据失败", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return pmDetailOri;
    }

    public void setDataPM() {
        //pm
        pmValue.setText("空气指数 " + pmDetailOri.getPm());
        pmSituation.setText("空气质量 " + pmDetailOri.getQuality());
    }

}
