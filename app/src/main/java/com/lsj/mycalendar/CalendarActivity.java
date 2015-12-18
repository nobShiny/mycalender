package com.lsj.mycalendar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lsj.mycalendar.data.DateInfo;
import com.lsj.mycalendar.utils.CurrentTimeUtils;
import com.lsj.mycalendar.utils.DataUtils;
import com.lsj.mycalendar.utils.TimeUtils;
import com.lsj.mycalendar.view.MyViewPager;
import com.lsj.mycalendar.view.Panel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 和viewpager相关变量
     */
    public MyViewPager viewPager = null;
    public MyPagerAdapter pagerAdapter = null;
    private int currPager = 500;
    private TextView shader;
    /**
     * 和日历gridview相关变量
     */
    private GridView gridView = null;
    public CalendarAdapter adapter = null;
    private GridView currentView = null;
    public List<DateInfo> currList = null;
    public List<DateInfo> list = null;
    public int lastSelected = 0;
    /**
     * 第一个页面的年月
     */
    private int currentYear;
    private int currentMonth;

    /**
     * 收缩展开的面板
     */
    private Panel panel;
    private List<String> signinFutureData;

    /**
     * 当前日期
     */
    private String currentYearAndMonth;
    private String currentDay;
    private String start_date;
    private String end_date;

    /**
     * 控件初始化
     */
    private RelativeLayout rl_calendar_show;
    private LinearLayout ll_tomorrow;
    private LinearLayout ll_yesterday;
    private TextView tv_calendar_show_year_and_month;
    private TextView tv_calendar_show_date;
    public TextView showYearMonth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ll_yesterday = (LinearLayout) findViewById(R.id.ll_yesterday);
        ll_tomorrow = (LinearLayout) findViewById(R.id.ll_tomorrow);
        rl_calendar_show = (RelativeLayout) findViewById(R.id.rl_calendar_show);
        tv_calendar_show_year_and_month = (TextView) findViewById(R.id.tv_calendar_show_year_and_month);
        tv_calendar_show_date = (TextView) findViewById(R.id.tv_calendar_show_date);

        start_date = CurrentTimeUtils.getTimeYear() + "-"
                + (CurrentTimeUtils.getTimeMonth() >= 10 ? CurrentTimeUtils.getTimeMonth() : "0" + CurrentTimeUtils.getTimeMonth()) + "-"
                + (CurrentTimeUtils.getTimeDay() >= 10 ? CurrentTimeUtils.getTimeDay() : ("0" + CurrentTimeUtils.getTimeDay()));
        end_date = CurrentTimeUtils.getTimeNextYear() + "-"
                + (CurrentTimeUtils.getTimeMonth() >= 10 ? CurrentTimeUtils.getTimeMonth() : "0" + CurrentTimeUtils.getTimeMonth()) + "-"
                + (CurrentTimeUtils.getTimeDay() >= 10 ? CurrentTimeUtils.getTimeDay() : ("0" + CurrentTimeUtils.getTimeDay()));
        initCalendarData();// 必须要在初始化日历视图前完成
        initCalendarView();
        setOnClickListener();
    }

    private void setOnClickListener() {
        rl_calendar_show.setOnClickListener(CalendarActivity.this);
        ll_yesterday.setOnClickListener(CalendarActivity.this);
        ll_tomorrow.setOnClickListener(CalendarActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_tomorrow:// 明天
                panel.setOpen(false, true);
                String currentYearAndMonth = tv_calendar_show_year_and_month
                        .getText().toString().trim();
                String currentDay = tv_calendar_show_date.getText().toString()
                        .trim()
                        + "日";
                setTomorrow(currentYearAndMonth + currentDay);
                break;
            case R.id.ll_yesterday:// 昨天
                panel.setOpen(false, true);
                String currentYearAndMonth1 = tv_calendar_show_year_and_month
                        .getText().toString().trim();
                String currentDay1 = tv_calendar_show_date.getText().toString()
                        .trim()
                        + "日";
                setYesterday(currentYearAndMonth1 + currentDay1);
                break;
            default:
                break;
        }
    }

    // 昨天时间
    private void setYesterday(String strYMD) {
        String dateFormat = "yyyy年MM月dd日";
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat,
                    Locale.getDefault());
            date = sdf.parse(strYMD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, -1);

        String resultYearAndMonth = c.get(Calendar.YEAR) + "年"
                + (c.get(Calendar.MONTH) + 1) + "月";
        String resultDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
        tv_calendar_show_year_and_month.setText(resultYearAndMonth);
        if (c.get(Calendar.DAY_OF_MONTH) < 10) {
            tv_calendar_show_date.setText("0" + resultDay);
        } else {
            tv_calendar_show_date.setText(resultDay);
        }

        String tomorrowStrTimestamp = c.get(Calendar.YEAR)
                + "-"
                + ((c.get(Calendar.MONTH) + 1) > 10 ? (c.get(Calendar.MONTH) + 1)
                : "0" + (c.get(Calendar.MONTH) + 1)) + "-"
                + c.get(Calendar.DAY_OF_MONTH);

        Toast.makeText(this, tomorrowStrTimestamp, Toast.LENGTH_SHORT).show();
    }

    // 明天时间
    private void setTomorrow(String strYMD) {
        String dateFormat = "yyyy年MM月dd日";
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat,
                    Locale.getDefault());
            date = sdf.parse(strYMD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, 1);

        String resultYearAndMonth = c.get(Calendar.YEAR) + "年"
                + (c.get(Calendar.MONTH) + 1) + "月";
        String resultDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
        tv_calendar_show_year_and_month.setText(resultYearAndMonth);
        if (c.get(Calendar.DAY_OF_MONTH) < 10) {
            tv_calendar_show_date.setText("0" + resultDay);
        } else {
            tv_calendar_show_date.setText(resultDay);
        }
        String tomorrowStrTimestamp = c.get(Calendar.YEAR)
                + "-"
                + ((c.get(Calendar.MONTH) + 1) > 10 ? (c.get(Calendar.MONTH) + 1)
                : "0" + (c.get(Calendar.MONTH) + 1)) + "-"
                + c.get(Calendar.DAY_OF_MONTH);
        Toast.makeText(this, tomorrowStrTimestamp, Toast.LENGTH_SHORT).show();
    }

    /**
     * 初始化view
     */
    private void initCalendarView() {
        viewPager = (MyViewPager) findViewById(R.id.viewpager);
        shader = (TextView) findViewById(R.id.main_frame_shader);
        panel = (Panel) findViewById(R.id.calendar_panel);
        panel.setOpen(false, false);
        shader.setVisibility(View.GONE);
        pagerAdapter = new MyPagerAdapter();
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(500);
        viewPager.setPageMargin(0);
        showYearMonth = (TextView) findViewById(R.id.main_year_month);
        showYearMonth.setText(String.format("%04d年%02d", currentYear,
                currentMonth));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int arg0) {
                if (arg0 == 1) {
                    shader.setText("");
                    shader.setVisibility(View.VISIBLE);
                }
                if (arg0 == 0) {
                    currentView = (GridView) viewPager.findViewById(currPager);//不用管这个位置,运行时不会出现问题
                    if (currentView != null) {
                        adapter = (CalendarAdapter) currentView.getAdapter();
                        currList = adapter.getList();
                        int pos = DataUtils.getDayFlag(currList, lastSelected);
                        adapter.setSelectedPosition(pos);
                        adapter.notifyDataSetInvalidated();
                    }
                    shader.setVisibility(View.GONE);
                }
            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            public void onPageSelected(int position) {
                int year = TimeUtils.getTimeByPosition(position, currentYear,
                        currentMonth, "year");
                int month = TimeUtils.getTimeByPosition(position, currentYear,
                        currentMonth, "month");
                showYearMonth.setText(String.format("%04d年%02d", year, month));
                currPager = position;
                shader.setText(showYearMonth.getText());
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initCalendarData() {
        currentYear = TimeUtils.getCurrentYear();
        currentMonth = TimeUtils.getCurrentMonth();
        lastSelected = TimeUtils.getCurrentDay();
        String formatDate = TimeUtils.getFormatDate(currentYear, currentMonth);
        try {
            list = TimeUtils.initCalendar(formatDate, currentMonth);
        } catch (Exception e) {
            finish();
        }

        setCurrentDate();
    }


    /**
     * viewpager的适配器，从第500页开始，最多支持0-1000页
     */
    private class MyPagerAdapter extends PagerAdapter {

        @Override
        public void setPrimaryItem(ViewGroup container, int position,
                                   Object object) {
            currentView = (GridView) object;
            adapter = (CalendarAdapter) currentView.getAdapter();
        }

        @Override
        public int getCount() {
            return 1000;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            GridView gv = initCalendarView(position);
            gv.setId(position);
            container.addView(gv);
            return gv;
        }
    }
    /**
     * 初始化日历的gridview
     */
    private GridView initCalendarView(int position) {
        int year = TimeUtils.getTimeByPosition(position, currentYear,
                currentMonth, "year");
        int month = TimeUtils.getTimeByPosition(position, currentYear,
                currentMonth, "month");
        String formatDate = TimeUtils.getFormatDate(year, month);
        try {
            list = TimeUtils.initCalendar(formatDate, month);
        } catch (Exception e) {
            finish();
        }
        gridView = new GridView(this);
        adapter = new CalendarAdapter(CalendarActivity.this, list);
        if (position == 500) {
            currList = list;
            int pos = DataUtils.getDayFlag(list, lastSelected);
            adapter.setSelectedPosition(pos);
        }
        gridView.setAdapter(adapter);
        gridView.setNumColumns(7);
        gridView.setBackgroundColor(Color.WHITE);
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gridView.setGravity(Gravity.CENTER);
        gridView.setOnItemClickListener(new OnItemClickListenerImpl(adapter,
                this));
        return gridView;
    }
    /**
     * 日历gridview适配器
     */
    public class CalendarAdapter extends BaseAdapter {

        private List<DateInfo> list = null;
        private Context context = null;
        private int selectedPosition = -1;
        private CalendarActivity activity;

        public CalendarAdapter(CalendarActivity activity, List<DateInfo> list) {
            this.context = activity;
            this.list = list;
            this.activity = activity;
        }

        public List<DateInfo> getList() {
            return list;
        }

        public int getCount() {
            return list.size();
        }

        public Object getItem(int position) {
            return list.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        /**
         * 设置选中位置
         */
        public void setSelectedPosition(int position) {
            selectedPosition = position;
        }

        /**
         * 产生一个view
         */
        public View getView(int position, View convertView, ViewGroup group) {
            // 通过viewholder做一些优化
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(context);
                convertView = inflater.inflate(
                        R.layout.sign_in_calendar_gridview_item, null);
                viewHolder.date = (TextView) convertView
                        .findViewById(R.id.item_date);
                viewHolder.signinDate = (TextView) convertView.findViewById(R.id.item_signin_date);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }


//			/*
//             * 未来签到小红点显示
//			 * 这个方法的位置必须要在最上边
//			 */
//            if (haveSignin(list.get(position).getDate())) {
//                viewHolder.signinDate.setVisibility(View.VISIBLE);
//            } else {
//                viewHolder.signinDate.setVisibility(View.GONE);
//            }

            // 根据数据源设置单元格的字体颜色、背景等
            viewHolder.date.setText(list.get(position).getDate() + "");
            // viewHolder.nongliDate.setText(list.get(position).getNongliDate());
            if (selectedPosition == position) {
                viewHolder.date.setTextColor(Color.WHITE);
                // viewHolder.nongliDate.setTextColor(Color.WHITE);
                convertView
                        .setBackgroundResource(R.drawable.sign_in_calendar_gird_item_select_bg);
            } else {
                convertView.setBackgroundResource(R.drawable.sign_in_item_bkg);
                viewHolder.date.setTextColor(Color.BLACK);
                if (list.get(position).isThisMonth() == false) {
                    viewHolder.date.setTextColor(Color.rgb(210, 210, 210));
                    viewHolder.signinDate.setVisibility(View.GONE);
                }
            }
            //            /**
            //             * 当前日期标志
            //             * 注意:这个方法的位置不可变
            //             */
            //            if(isToday(list.get(position).getDate())){
            //                viewHolder.date.setTextColor(Color.RED);
            //            }
            convertView.setLayoutParams(new GridView.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, DataUtils
                    .getScreenWidth(activity) / 7));
            return convertView;
        }

        private class ViewHolder {
            TextView date;
            TextView signinDate;
        }

    }

    /**
     * 日历的点击事件处理
     *
     * @author shiny_Jia
     */
    class OnItemClickListenerImpl implements AdapterView.OnItemClickListener {

        private CalendarAdapter adapter = null;
        private CalendarActivity activity = null;

        public OnItemClickListenerImpl(CalendarAdapter adapter,
                                       CalendarActivity activity) {
            this.adapter = adapter;
            this.activity = activity;
        }

        public void onItemClick(AdapterView<?> gridView, View view,
                                int position, long id) {
            if (activity.currList.get(position).isThisMonth() == false) {
                Toast.makeText(CalendarActivity.this, "请滑动切换到对应的月份进行查看",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            adapter.setSelectedPosition(position);
            adapter.notifyDataSetInvalidated();
            activity.lastSelected = activity.currList.get(position).getDate();

            tv_calendar_show_year_and_month.setText(showYearMonth.getText()
                    .toString() + "月");
            if (lastSelected < 10) {
                tv_calendar_show_date.setText("0" + lastSelected + "");
            } else {
                tv_calendar_show_date.setText(lastSelected + "");
            }
            Toast.makeText(CalendarActivity.this, showYearMonth.getText().toString()
                    + "-" + lastSelected + "", Toast.LENGTH_SHORT).show();
            panel.setOpen(false, true);
        }

    }

    // 默认时间设置
    private void setCurrentDate() {
        currentYearAndMonth = CurrentTimeUtils.getTimeYear() + "年"
                + (CurrentTimeUtils.getTimeMonth() < 10 ? ("0" + CurrentTimeUtils.getTimeMonth()) : CurrentTimeUtils.getTimeMonth()) + "月";
        if (CurrentTimeUtils.getTimeDay() >= 10) {
            currentDay = CurrentTimeUtils.getTimeDay() + "";
        } else {
            currentDay = "0" + CurrentTimeUtils.getTimeDay() + "";
        }
        tv_calendar_show_year_and_month.setText(currentYearAndMonth);
        tv_calendar_show_date.setText(currentDay);
        String init_req_date_format = CurrentTimeUtils.getTimeYear() + "-"
                + CurrentTimeUtils.getTimeMonth() + "-"
                + CurrentTimeUtils.getTimeDay();
        Toast.makeText(this, "默认时间"+init_req_date_format, Toast.LENGTH_SHORT).show();
    }
}
