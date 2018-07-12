package fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

import com.eniversity.app.R;

import java.util.ArrayList;
import java.util.HashMap;

import get.set.QuestionGetSet;

/**
 * Created by Administrator on 11/7/2016.
 */
@SuppressLint("ValidFragment")
public class AnalysisFrag extends Fragment implements OnTabChangeListener, ViewPager.OnPageChangeListener {
    String questionAndAns = "";
    Context context;
    static String[] ColorValue = new String[100];
    ArrayList<QuestionGetSet> classObject;
    ImageView previousButton;
    ImageView nextButton;
    ViewPager questionsViewPager;
    HorizontalScrollView horizontalScrollView1;

    public AnalysisFrag(Context context, String questionAndAns, ArrayList<QuestionGetSet> classObject) {
        this.context = context;
        this.questionAndAns = questionAndAns;
        this.classObject = classObject;
    }

    private HashMap<String, TabInfo> mapTabInfo = new HashMap<String, TabInfo>();

    TabHost mTabHost;
    String[] QandAns;

    private TextView textViewHeadingAnalysis;
    private ImageView imageViewBackIcon_timer;
    private TextView textViewTime;
    private ImageView logoutImageView;
    private RelativeLayout resultToolBar;
    View includeAnalysis;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View analysisView = inflater.inflate(R.layout.analysis_layout, null);
        try {
            mTabHost = (TabHost) analysisView.findViewById(android.R.id.tabhost);
            previousButton = (ImageView) analysisView.findViewById(R.id.previousButton);
            nextButton = (ImageView) analysisView.findViewById(R.id.nextButton);
            horizontalScrollView1 = (HorizontalScrollView) analysisView.findViewById(R.id.horizontalScrollView1);
            //horizontalScrollView1.setOnTouchListener(new OnTouch());
            mTabHost.setup();
            QandAns = questionAndAns.split("~");
            Log.v("questionAndAns", questionAndAns);

            questionsViewPager = (ViewPager) analysisView.findViewById(R.id.pagerAnalysis);
            questionsViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    View tabView = mTabHost.getTabWidget().getChildAt(position);
                    if (tabView != null) {
                        int x, y;
                        final int width = horizontalScrollView1.getWidth();
                        x = tabView.getLeft() - (width - tabView.getWidth()) / 2;/*tabView.getLeft();*/
                        y = tabView.getRight();
                        horizontalScrollView1.scrollTo(x, y);
                    } else {
                        horizontalScrollView1.scrollBy(positionOffsetPixels, 0);
                    }

                    if (position == 0) {
                        previousButton.setVisibility(View.GONE);
                    } else {
                        previousButton.setVisibility(View.VISIBLE);
                    }
                    if (position == (classObject.size() - 1)) {
                        nextButton.setVisibility(View.GONE);
                    } else {
                        nextButton.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onPageSelected(int position) {

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            //analysistab = (SlidingTabLayout) analysisView.findViewById(R.id.analysistab);

            //Toast.makeText(Result_Analysis.this,"COUNT IS"+count,Toast.LENGTH_SHORT).show();

            AnalysusResAdapter analysusResAdapter = new AnalysusResAdapter(context, getActivity().getSupportFragmentManager(), classObject);
            questionsViewPager.setAdapter(analysusResAdapter);
            // analysistab.setViewPager(questionsViewPager);
            //analysistab.setDistributeEvenly(true);

            //mTabHost = (TabHost)analysisView.findViewById(android.R.id.tabhost);

            initialiseTabHost(savedInstanceState);



            for (int j = 0; j < classObject.size(); j++) {
                for (int i = 0; i < QandAns.length; i++) {
                    String arra[] = QandAns[i].split("\\^");
                    if (arra[0].equals(classObject.get(j).getQuestionid())) {

                        if (arra[1].equals(classObject.get(j).getCorrectAnswer())) {
                            if (j == 0) {
                                mTabHost.getTabWidget().getChildAt(j).setBackgroundResource(R.drawable.current_tab_currect);
                            } else {
                                mTabHost.getTabWidget().getChildAt(j).setBackgroundResource(R.color.appcolor);
                            }
                            TextView tabText = (TextView) mTabHost.getTabWidget().getChildAt(j).findViewById(android.R.id.title);
                            tabText.setTextColor(Color.parseColor("#FFFFFF"));
                            tabText.setTextSize(20.0f);
                        } else if (!(arra[1].equals(classObject.get(j).getCorrectAnswer()))) {
                            if ((arra[1].equals("-1"))) {
                                if (j == 0) {
                                    mTabHost.getTabWidget().getChildAt(j).setBackgroundResource(R.drawable.current_tab_unattempt);
                                } else {
                                    mTabHost.getTabWidget().getChildAt(j).setBackgroundResource(R.color.unattemptedColor);
                                }
                                TextView tabText = (TextView) mTabHost.getTabWidget().getChildAt(j).findViewById(android.R.id.title);
                                tabText.setTextColor(Color.parseColor("#000000"));
                                tabText.setTextSize(20.0f);
                            } else {
                                if (j == 0) {
                                    mTabHost.getTabWidget().getChildAt(j).setBackgroundResource(R.drawable.current_tab_wrong);
                                } else {
                                    mTabHost.getTabWidget().getChildAt(j).setBackgroundResource(R.color.wrongColor);
                                }
                                TextView tabText = (TextView) mTabHost.getTabWidget().getChildAt(j).findViewById(android.R.id.title);
                                tabText.setTextColor(Color.parseColor("#FFFFFF"));
                                tabText.setTextSize(20.0f);
                            }
                        }

                    }

                }
            }
            questionsViewPager.addOnPageChangeListener(this);
            for (int i = 0; i < ColorValue.length; i++) {
                ColorValue[i] = "YELLOW";
            }
            previousButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    questionsViewPager.setCurrentItem(questionsViewPager.getCurrentItem() - 1, true);
                }
            });
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    questionsViewPager.setCurrentItem(questionsViewPager.getCurrentItem() + 1, true);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return analysisView;

    }

    private static void AddTab(Context context, TabHost tabHost,
                               TabHost.TabSpec tabSpec, TabInfo tabInfo) {
        tabSpec.setContent(new TabFactory(context));
        tabHost.addTab(tabSpec);
    }

    private void initialiseTabHost(Bundle args) {
        try {
            TabInfo tabInfo = null;
            for (int i = 0; i < QandAns.length; i++) {

                AnalysisFrag.AddTab(context, mTabHost, mTabHost.newTabSpec(String.valueOf(i + 1))
                                .setIndicator(String.valueOf(i + 1)),
                        (tabInfo = new TabInfo(String.valueOf(i + 1), AnswersFrag.class, args, i)));

                mTabHost.getTabWidget().setStripEnabled(false);




                mapTabInfo.put(tabInfo.tag, tabInfo);
                mTabHost.getTabWidget().getChildTabViewAt(i).setEnabled(false);


                /*******On click of tab items*********/
                TextView tabText = (TextView) mTabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
                tabText.setWidth(73);
                tabText.setFocusable(true);
                tabText.setGravity(Gravity.CENTER);
                tabText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        questionsViewPager.setCurrentItem(Integer.parseInt(((TextView) view).getText().toString().trim()) - 1);
                    }
                });
                /**************************************/
            }
            mTabHost.setOnTabChangedListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        View tabView = mTabHost.getTabWidget().getChildAt(position);
        //      = (HorizontalScrollView) findViewById(R.id.horizontalScrollView1);
        try {
            /*if (tabView != null) {
                final int width = horizontalScrollView1.getWidth();
                final int scrollPos = tabView.getLeft() - (width - tabView.getWidth()) / 2;
                horizontalScrollView1.scrollTo(scrollPos, 0);
            } else {
                horizontalScrollView1.scrollBy(positionOffsetPixels, 0);
            }*/

            if (tabView != null) {
                int x, y;
                final int width = horizontalScrollView1.getWidth();
                x = tabView.getLeft() - (width - tabView.getWidth()) / 2;/*tabView.getLeft();*/
                y = tabView.getRight();
                horizontalScrollView1.scrollTo(x, y);
            } else {
                horizontalScrollView1.scrollBy(positionOffsetPixels, 0);
            }

            int pos = questionsViewPager.getCurrentItem();
            //mTabHost.getTabWidget().getChildAt(pos).setBackgroundResource(R.drawable.current_selected_tab_currected);
            this.mTabHost.setCurrentTab(pos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPageSelected(int position) {
        for (int j = 0; j < classObject.size(); j++) {
            for (int i = 0; i < QandAns.length; i++) {
                String arra[] = QandAns[i].split("\\^");
                if (arra[0].equals(classObject.get(j).getQuestionid())) {

                    if (arra[1].equals(classObject.get(j).getCorrectAnswer())) {
                        if (j == position) {
                            mTabHost.getTabWidget().getChildAt(j).setBackgroundResource(R.drawable.current_tab_currect);
                        } else {
                            mTabHost.getTabWidget().getChildAt(j).setBackgroundResource(R.color.appcolor);
                        }
                        TextView tabText = (TextView) mTabHost.getTabWidget().getChildAt(j).findViewById(android.R.id.title);
                        tabText.setTextColor(Color.parseColor("#FFFFFF"));
                        tabText.setTextSize(20.0f);
                    } else if (!(arra[1].equals(classObject.get(j).getCorrectAnswer()))) {
                        if ((arra[1].equals("-1"))) {
                            if (j == position) {
                                mTabHost.getTabWidget().getChildAt(j).setBackgroundResource(R.drawable.current_tab_unattempt);
                            } else {
                                mTabHost.getTabWidget().getChildAt(j).setBackgroundResource(R.color.unattemptedColor);
                            }
                            TextView tabText = (TextView) mTabHost.getTabWidget()
                                    .getChildAt(j).findViewById(android.R.id.title);
                            tabText.setTextColor(Color.parseColor("#000000"));
                            tabText.setTextSize(20.0f);
                        } else {
                            if (j == position) {
                                mTabHost.getTabWidget().getChildAt(j).setBackgroundResource(R.drawable.current_tab_wrong);
                            } else {
                                mTabHost.getTabWidget().getChildAt(j).setBackgroundResource(R.color.wrongColor);
                            }
                            TextView tabText = (TextView) mTabHost.getTabWidget()
                                    .getChildAt(j).findViewById(android.R.id.title);
                            tabText.setTextColor(Color.parseColor("#FFFFFF"));
                            tabText.setTextSize(20.0f);
                        }
                    }

                }

            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onTabChanged(String tabId) {


    }

    private static class TabInfo {
        private String tag;
        private Class<?> clss;
        private Bundle args;
        int pos;

        TabInfo(String tag, Class<?> clazz, Bundle args, int pos) {
            this.tag = tag;
            this.clss = clazz;
            this.args = args;
            this.pos = pos;
        }

    }

    public static class TabFactory implements TabHost.TabContentFactory {

        private final Context mContext;

        /**
         * @param context
         */
        public TabFactory(Context context) {
            mContext = context;
        }


        @Override
        public View createTabContent(String tag) {
            View v = new View(mContext);
            v.setMinimumWidth(0);
            v.setMinimumHeight(0);
            return v;
        }
    }

    public class AnalysusResAdapter extends FragmentStatePagerAdapter {

        ArrayList<QuestionGetSet> questions;
        Context context;


        public AnalysusResAdapter(Context context, FragmentManager fm, ArrayList<QuestionGetSet> questions) {
            super(fm);
            this.context = context;
            this.questions = questions;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return String.valueOf(position + 1);
        }

        @Override
        public Fragment getItem(int position) {
            return new AnswersFrag(position, classObject, questionAndAns);
        }

        @Override
        public int getCount() {
            return questions.size();
        }
    }

    private class OnTouch implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return true;
        }
    }
}




