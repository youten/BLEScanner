package youten.redo.unity.blescannerplugin;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.util.Log;

import com.unity3d.player.UnityPlayer;

import youten.redo.ble.util.BleUtil;
import youten.redo.ble.util.ScannedDevice;

/**
 * BLEScanner
 */
public class BLEScanner implements BluetoothAdapter.LeScanCallback {
    private static final String TAG = "BLEScanner";
    private BluetoothAdapter mBTAdapter;
    private boolean mIsScanning;

    public BLEScanner(Context context) {
        init(context);
    }

    @Override
    public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
        ScannedDevice d = new ScannedDevice(device, rssi, scanRecord, System.currentTimeMillis());
        Log.d(TAG, "onLeScan:" + d.toCsv());
        UnityPlayer.UnitySendMessage("LeScanCallback", "onLeScan", d.toCsv());
    }

    private boolean init(Context context) {
        if (context == null) {
            return false;
        }
        // BLE check
        if (!BleUtil.isBLESupported(context)) {
            return false;
        }

        // BT check
        BluetoothManager manager = BleUtil.getManager(context);
        if (manager != null) {
            mBTAdapter = manager.getAdapter();
        }
        if (mBTAdapter == null) {
            return false;
        }

        stop();
        return true;
    }

    public void start() {
        if ((mBTAdapter == null) || (mIsScanning)) {
            return;
        }
        mBTAdapter.startLeScan(this);
        mIsScanning = true;
    }

    public void stop() {
        if (mBTAdapter != null) {
            mBTAdapter.stopLeScan(this);
        }
        mIsScanning = false;
    }

}
