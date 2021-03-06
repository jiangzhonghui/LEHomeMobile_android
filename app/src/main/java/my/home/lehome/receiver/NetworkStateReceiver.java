/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package my.home.lehome.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import my.home.common.NetworkUtil;
import my.home.lehome.helper.LocalMsgHelper;
import my.home.lehome.helper.PushSDKManager;

/**
 * Created by legendmohe on 15/3/8.
 */
public class NetworkStateReceiver extends BroadcastReceiver {

    public static final String TAG = "NetworkStateReceiver";
    public static final String VALUE_INTENT_STOP_LOCAL_SERVER = "my.home.lehome.receiver.NetworkStateReceiver:stop";
    public static final String VALUE_INTENT_START_LOCAL_SERVER = "my.home.lehome.receiver.NetworkStateReceiver:start";

    @Override
    public void onReceive(Context context, Intent intent) {
        NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
        if (info != null) {
            Log.d(TAG, "NetworkInfo: " + info);
            if (info.isConnected()) {
                String ssid = NetworkUtil.getFormatSSID(context);
                String prefSSID = LocalMsgHelper.getLocalSSID(context);
                if (ssid.equals(prefSSID)) {
                    Log.d(TAG, "start " + "LocalMessageService " + LocalMsgHelper.startLocalMsgService(context));
                    if (LocalMsgHelper.startLocalMsgService(context)) {
                        PushSDKManager.stopPushSDKService(context);
                        Intent startIntent = new Intent(VALUE_INTENT_START_LOCAL_SERVER);
                        context.sendBroadcast(startIntent);
                    }
                } else {
                    Log.d(TAG, "stop " + "LocalMessageService");
                    LocalMsgHelper.stopLocalMsgService(context);
                    Intent stopIntent = new Intent(VALUE_INTENT_STOP_LOCAL_SERVER);
                    context.sendBroadcast(stopIntent);
                    PushSDKManager.startPushSDKService(context);
                }
            } else if (!info.isConnectedOrConnecting()) {
                Log.d(TAG, "stop " + "LocalMessageService");
                LocalMsgHelper.stopLocalMsgService(context);
                PushSDKManager.startPushSDKService(context);
                Intent stopIntent = new Intent(VALUE_INTENT_STOP_LOCAL_SERVER);
                context.sendBroadcast(stopIntent);
            }
        }
    }
}
