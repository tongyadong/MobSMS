package learn.tony.mobsms;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import cn.smssdk.EventHandler;
import cn.smssdk.OnSendMessageHandler;
import cn.smssdk.SMSSDK;

public class MainActivity extends AppCompatActivity {

    private EventHandler eventHandler = new EventHandler() {
        public void afterEvent(int event, int result, Object data) {
            if (data instanceof Throwable) {
                Throwable throwable = (Throwable) data;
                String msg = throwable.getMessage();
                Log.i("Main", "afterEvent: "+msg);
            } else {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        Log.i("event", "afterEvent: "+"手机号码验证通过");

                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码成功
                        Log.i("event", "code: "+result+", "+data.toString());

                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        //返回支持发送验证码的国家列表
                        Log.i("event", "countries: 1.result: "+String.valueOf(result)+"2.data: " +
                                ""+data.toString());
                    }
                }
            }
        }
    };
    private EditText edtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 注册监听器
        SMSSDK.registerEventHandler(eventHandler);
        edtext = (EditText) findViewById(R.id.code);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消注册监听器
        SMSSDK.unregisterEventHandler(eventHandler);
    }

    public void getVerifyCode(View view) {
        String country = "86";
        String phone = "15711286681";
        SMSSDK.getVerificationCode(country, phone, new OnSendMessageHandler() {
            @Override
            public boolean onSendMessage(String s, String s1) {
                Log.i("event", "onSendMessage: "+s+", "+s1);
                return false;
            }
        });
    }

    public void getSupportCountries(View view) {
        SMSSDK.getSupportedCountries();
    }


    public void submit(View view) {
        String country = "86";
        String phone = "15711286681";
        SMSSDK.submitVerificationCode(country, phone,edtext.getText().toString().trim());
    }
}
