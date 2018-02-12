package com.example.masterx.quizapp;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private final int numberQuestions=7;
    private int scoreTotal;
    private boolean missingAnswer=false;  //is set to true if a question is not answered


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }



        /**
         * This method is called when the submit button is clicked.
         */
    public void submit(View view) {

        int scoreTotal=evaluateAnswers();
        if (scoreTotal<0) {
            Toast toastMessage=Toast.makeText(this,"Answer for question "+ (-scoreTotal) + " is missing",Toast.LENGTH_SHORT);
            toastMessage.setGravity(Gravity.BOTTOM, 0, 200);
            toastMessage.show();
        } else {            //if all questions are answered
            showResult();
        }
    }

    /**
     * Evaluates all given answers and returns the reached total score.
     *
     * @return total score value or a negative value of the first question number that is not answered
    */

    private int evaluateAnswers () {

        missingAnswer=false;
        scoreTotal=0;
        String textInput;
        RadioGroup radioGroup;
        CheckBox[] checkBoxes=new CheckBox[4];
        boolean[] correctAnswers=new boolean[4];
        RadioButton correctRadioButton;

        //Question 1
        radioGroup=findViewById(R.id.question1_radio_group);
        correctRadioButton=findViewById(R.id.question1_radio_button4);
        evaluateRadioButtonAnswer(radioGroup,correctRadioButton);
        if (missingAnswer) return -1;       //return code for future development (if ==1 then..

        //Question 2
        radioGroup=findViewById(R.id.question2_radio_group);
        correctRadioButton=findViewById(R.id.question2_radio_button1);
        evaluateRadioButtonAnswer(radioGroup,correctRadioButton);
        if (missingAnswer) return -2;      //return code for future development


        //Question 3
        radioGroup=findViewById(R.id.question3_radio_group);
        correctRadioButton=findViewById(R.id.question3_radio_button2);
        evaluateRadioButtonAnswer(radioGroup,correctRadioButton);
        if (missingAnswer) return -3;       //return code for future development

        //Question 4
        radioGroup=findViewById(R.id.question4_radio_group);
        correctRadioButton=findViewById(R.id.question4_radio_button3);
        evaluateRadioButtonAnswer(radioGroup,correctRadioButton);
        if (missingAnswer) return -4;       //return code for future development

        //Question 5
        radioGroup=findViewById(R.id.question5_radio_group);
        correctRadioButton=findViewById(R.id.question5_radio_button3);
        evaluateRadioButtonAnswer(radioGroup,correctRadioButton);
        if (missingAnswer) return -5;       //return code for future development

        //Question 6
        checkBoxes[0]=findViewById(R.id.question6_checkbox1);
        checkBoxes[1]=findViewById(R.id.question6_checkbox2);
        checkBoxes[2]=findViewById(R.id.question6_checkbox3);
        checkBoxes[3]=findViewById(R.id.question6_checkbox4);
        correctAnswers[0]=true;
        correctAnswers[1]=false;
        correctAnswers[2]=true;
        correctAnswers[3]=false;
        evaluateCheckBoxAnswer(checkBoxes, correctAnswers);
        if (missingAnswer) return -6;       //return code for future development

        //Question 7
        radioGroup=findViewById(R.id.question7_radio_group);
        correctRadioButton=findViewById(R.id.question7_radio_button4);
        evaluateRadioButtonAnswer(radioGroup,correctRadioButton);
        if (missingAnswer) return -7;       //return code for future development



        return scoreTotal;
    }

    /**
     * Evaluates the given answer in a RadioGroup
     * If one answer is found to be missing the ttoal score value -1 is returned immediately. it is not ok for retunerning -3 -4 and so on..
     *
     * @return total score value
     */
    private void evaluateRadioButtonAnswer (RadioGroup radioGroup, RadioButton correctRadioButton) {
        int givenAnswerID;
        //getCheckedRadioButtonId() returns the identifier (a int value) of the selected radio button in this group.
        //the id of the selected radio button is then compared with the id of the correct answer id
        //Upon empty selection, the returned value is -1.
        givenAnswerID=radioGroup.getCheckedRadioButtonId();
        if (correctRadioButton.getId()==givenAnswerID) {
            scoreTotal++;
        } else if (givenAnswerID==-1) {
            missingAnswer=true;
        }
    }

    /**
     * Evaluates all given answers in a CheckBox question
     *
     * @param checkBox is an array of the given answers in a CheckBox question
     * @param correctAnswer is the array of the corrects answers
     *
     * @return total score value
     */
    private void evaluateCheckBoxAnswer (CheckBox[] checkBox, boolean correctAnswer[]) {
        boolean[] givenAnswer=new boolean[4];
        givenAnswer[0]=checkBox[0].isChecked();
        givenAnswer[1]=checkBox[1].isChecked();
        givenAnswer[2]=checkBox[2].isChecked();
        givenAnswer[3]=checkBox[3].isChecked();
        boolean[] noAnswer={false,false,false,false};
        if (Arrays.equals(givenAnswer, noAnswer)){
            missingAnswer=true;
        } else if (Arrays.equals(givenAnswer,correctAnswer)) {
            scoreTotal++;
        }
    }

    /**
     *  Shows a nice dialog with the reached score and also the possibility for a reset
     */

    private void showResult() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater=LayoutInflater.from(this);
        View view1=inflater.inflate(R.layout.result_dialog,null);
        ((TextView) view1.findViewById(R.id.result_message)).setText(getString(R.string.result_message, String.valueOf(scoreTotal), String.valueOf(numberQuestions)));
        builder.setView(view1);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                restartQuiz();
            }
        });
        builder.setNegativeButton(R.string.cancelButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // do nothing
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void restartQuiz(){
        onPause();
        onStop();
        onDestroy();
        onCreate(new Bundle());
        onStart();
        onResume();
    }
}
