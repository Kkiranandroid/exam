package fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.eniversity.app.R;

import java.util.ArrayList;

import Commmon.Methods.SlidingTabLayout;
import get.set.QuestionGetSet;

/**
 * Created by Administrator on 11/7/2016.
 */
@SuppressLint("ValidFragment")
public class AnswersFrag extends Fragment {
    String queAndAns[];
    String splitby_caret[];
    String questionAndAns = "";
    int pos = 0;
    int newPos = 0;
    ArrayList<QuestionGetSet> questionGetSetArrayList;
    private SlidingTabLayout view;
    ArrayList<QuestionGetSet> classObject;
    TextView textviewQuestion;
    TextView noOfQuestionsTextView;
    TextView textviewQuestionnum;
    CheckBox textOption1;
    CheckBox textOption2;
    CheckBox textOption3;
    CheckBox textOption4;
    CheckBox textOption5;
    TextView textOption1TextView;
    TextView textOption2TextView;
    TextView textOption3TextView;
    TextView textOption4TextView;
    TextView textOption5TextView;


    public AnswersFrag(int pos, ArrayList<QuestionGetSet> classObject, String questionAndAns) {
        this.classObject = classObject;
        this.pos = pos;
        this.questionAndAns = questionAndAns;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View analysisResultView = inflater.inflate(R.layout.analysis_fragment_layout, null);
        try {
            textviewQuestion = (TextView) analysisResultView.findViewById(R.id.textviewQuestion);
            noOfQuestionsTextView = (TextView) analysisResultView.findViewById(R.id.noOfQuestionsTextView);
            textviewQuestionnum = (TextView) analysisResultView.findViewById(R.id.textviewQuestionnum);
            int newpos = pos + 1;
            textviewQuestionnum.setText(String.valueOf(newpos) + ".");
            noOfQuestionsTextView.setText(newpos + " of  " + classObject.size());

            textOption1 = (CheckBox) analysisResultView.findViewById(R.id.textOption1);
            textOption2 = (CheckBox) analysisResultView.findViewById(R.id.textOption2);
            textOption3 = (CheckBox) analysisResultView.findViewById(R.id.textOption3);
            textOption4 = (CheckBox) analysisResultView.findViewById(R.id.textOption4);
            textOption5 = (CheckBox) analysisResultView.findViewById(R.id.textOption5);

            textOption1TextView = (TextView) analysisResultView.findViewById(R.id.textOption1TextView);
            textOption2TextView = (TextView) analysisResultView.findViewById(R.id.textOption2TextView);
            textOption3TextView = (TextView) analysisResultView.findViewById(R.id.textOption3TextView);
            textOption4TextView = (TextView) analysisResultView.findViewById(R.id.textOption4TextView);
            textOption5TextView = (TextView) analysisResultView.findViewById(R.id.textOption5TextView);

            textviewQuestion.setText(classObject.get(pos).getQuestion());
            textOption1TextView.setText(classObject.get(pos).getAnswer1());
            textOption2TextView.setText(classObject.get(pos).getAnswer2());
            textOption3TextView.setText(classObject.get(pos).getAnswer3());
            textOption4TextView.setText(classObject.get(pos).getAnswer4());
            textOption5TextView.setText(classObject.get(pos).getAnswer5());

            for (int j = 0; j < classObject.size(); j++) {
                if (classObject.get(pos).getNoofchoices().equals("1")) {
                    textOption2.setVisibility(View.GONE);
                    textOption2TextView.setVisibility(View.GONE);
                    textOption3TextView.setVisibility(View.GONE);
                    textOption4TextView.setVisibility(View.GONE);
                    textOption5TextView.setVisibility(View.GONE);
                    textOption3.setVisibility(View.GONE);
                    textOption4.setVisibility(View.GONE);
                    textOption5.setVisibility(View.GONE);
                } else if (classObject.get(pos).getNoofchoices().equals("2")) {
                    textOption3.setVisibility(View.GONE);
                    textOption4.setVisibility(View.GONE);
                    textOption5.setVisibility(View.GONE);
                    textOption3TextView.setVisibility(View.GONE);
                    textOption4TextView.setVisibility(View.GONE);
                    textOption5TextView.setVisibility(View.GONE);
                } else if (classObject.get(pos).getNoofchoices().equals("3")) {
                    textOption4.setVisibility(View.GONE);
                    textOption5.setVisibility(View.GONE);
                    textOption4TextView.setVisibility(View.GONE);
                    textOption5TextView.setVisibility(View.GONE);
                } else if (classObject.get(pos).getNoofchoices().equals("4")) {
                    textOption5.setVisibility(View.GONE);
                    textOption5TextView.setVisibility(View.GONE);
                }
            }

            //toolbarMain.setVisibility(View.GONE);
            // timerLayout.setVisibility(View.VISIBLE);
            //Drawable img = getContext().getResources().getDrawable(R.drawable.ic_cheked72_green);
            //img.setBounds(0, 0, 110, 110);
            // Drawable img2 = getContext().getResources().getDrawable(R.drawable.ic_imag_wrong4);
            //img2.setBounds(0, 0, 110, 110);

            queAndAns = questionAndAns.split("~");
            newPos = pos - 1;

            // for(int m=0;m<classObject.size();m++) {
            for (int i = 0; i < queAndAns.length; i++) {
                splitby_caret = queAndAns[i].split("\\^");
                if (splitby_caret[0].equals(classObject.get(pos).getQuestionid())) {

                    if (splitby_caret[1].equals(classObject.get(pos).getCorrectAnswer())) {
                        // view.setBackgroundColor(getActivity().getResources().getColor(R.color.Green));
                        //view.getChildAt(pos).setBackgroundColor(getApplicationContext().getResources().getColor(R.color.Green));

                        if (splitby_caret[1].equals("1")) {
                            textOption1.setButtonDrawable(getActivity().getResources().getDrawable(R.drawable.ic_cheked72_green));
                            // textOption1.setBackground(getActivity().getResources().getDrawable(R.drawable.ic_cheked72_green));
                        } else if (splitby_caret[1].equals("2")) {
                            textOption2.setButtonDrawable(getActivity().getResources().getDrawable(R.drawable.ic_cheked72_green));

                        } else if (splitby_caret[1].equals("3")) {
                            textOption3.setButtonDrawable(getActivity().getResources().getDrawable(R.drawable.ic_cheked72_green));

                        } else if (splitby_caret[1].equals("4")) {
                            textOption4.setButtonDrawable(getActivity().getResources().getDrawable(R.drawable.ic_cheked72_green));
                        } else {
                            textOption5.setButtonDrawable(getActivity().getResources().getDrawable(R.drawable.ic_cheked72_green));

                        }


                    } else if (!(splitby_caret[1].equals(classObject.get(pos).getCorrectAnswer()))) {
                        if ((splitby_caret[1].equals("-1"))) {
                            //view.getChildAt(pos).setBackgroundColor(getApplicationContext().getResources().getColor(R.color.notattemptedColor));
                            if (classObject.get(pos).getCorrectAnswer().equals("1")) {
                                textOption1.setButtonDrawable(getActivity().getResources().getDrawable(R.drawable.ic_cheked72_green));
                            } else if (classObject.get(pos).getCorrectAnswer().equals("2")) {
                                textOption2.setButtonDrawable(getActivity().getResources().getDrawable(R.drawable.ic_cheked72_green));
                            } else if (classObject.get(pos).getCorrectAnswer().equals("3")) {
                                textOption3.setButtonDrawable(getActivity().getResources().getDrawable(R.drawable.ic_cheked72_green));
                            } else if (classObject.get(pos).getCorrectAnswer().equals("4")) {
                                textOption4.setButtonDrawable(getActivity().getResources().getDrawable(R.drawable.ic_cheked72_green));
                            } else {
                                textOption5.setButtonDrawable(getActivity().getResources().getDrawable(R.drawable.ic_cheked72_green));

                            }

                        } else {
                            // view.getChildAt(pos).setBackgroundColor(getApplicationContext().getResources().getColor(R.color.Green));
                            if (splitby_caret[1].equals("1")) {
                                textOption1.setButtonDrawable(getActivity().getResources().getDrawable(R.drawable.ic_imag_wrong4));
                            } else if (splitby_caret[1].equals("2")) {
                                textOption2.setButtonDrawable(getActivity().getResources().getDrawable(R.drawable.ic_imag_wrong4));

                            } else if (splitby_caret[1].equals("3")) {
                                textOption3.setButtonDrawable(getActivity().getResources().getDrawable(R.drawable.ic_imag_wrong4));

                            } else if (splitby_caret[1].equals("4")) {
                                textOption4.setButtonDrawable(getActivity().getResources().getDrawable(R.drawable.ic_imag_wrong4));
                            } else {
                                textOption5.setButtonDrawable(getActivity().getResources().getDrawable(R.drawable.ic_imag_wrong4));

                            }
                            if (classObject.get(pos).getCorrectAnswer().equals("1")) {
                                textOption1.setButtonDrawable(getActivity().getResources().getDrawable(R.drawable.ic_cheked72_green));
                            } else if (classObject.get(pos).getCorrectAnswer().equals("2")) {
                                textOption2.setButtonDrawable(getActivity().getResources().getDrawable(R.drawable.ic_cheked72_green));
                            } else if (classObject.get(pos).getCorrectAnswer().equals("3")) {
                                textOption3.setButtonDrawable(getActivity().getResources().getDrawable(R.drawable.ic_cheked72_green));
                            } else if (classObject.get(pos).getCorrectAnswer().equals("4")) {
                                textOption4.setButtonDrawable(getActivity().getResources().getDrawable(R.drawable.ic_cheked72_green));
                            } else {
                                textOption5.setButtonDrawable(getActivity().getResources().getDrawable(R.drawable.ic_cheked72_green));

                            }
                        }
                    }
                }
                //}
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return analysisResultView;
    }
}
