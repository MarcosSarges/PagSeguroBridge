package br.com.ms.pagsegurobridge;

import android.os.Environment;
import android.util.Log;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReadableMap;


import br.com.uol.pagseguro.plugpagservice.wrapper.PlugPag;
import br.com.uol.pagseguro.plugpagservice.wrapper.PlugPagPrintResult;
import br.com.uol.pagseguro.plugpagservice.wrapper.PlugPagPrinterData;
import br.com.uol.pagseguro.plugpagservice.wrapper.PlugPagPrinterListener;

public class PagSeguroBridgeModule extends ReactContextBaseJavaModule {

    private static final String TAG = "PagSeguroBridge";

    private final ReactApplicationContext mReactContext;

    public PagSeguroBridgeModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.mReactContext = reactContext;
    }

    @Override
    public String getName() {
        return "PagSeguroBridge";
    }

    @ReactMethod
    public void printFile(ReadableMap options, final Promise promise) {
        try {
            if (!options.hasKey("path")
                    || (options.hasKey("path") && options.getString("path").isEmpty())) {
                throw new Error("Path invalid");
            }

            if (!options.hasKey("printerQuality") || (options.hasKey("printerQuality") &&
                    (options.getInt("printerQuality") == 0 || options.getInt("printerQuality") > 4))) {
                throw new Error("PrinterQuality invalid");
            }
            if (!options.hasKey("marginBottom") || (options.hasKey("marginBottom")
                    && options.getInt("marginBottom") == 0)) {
                throw new Error("MarginBottom invalid");
            }


            String _path = Environment.getExternalStorageDirectory().getAbsolutePath() + options.getString("path");
            Log.v(TAG, _path);

            int printerQuality = options.getInt("printerQuality");
            int marginBottom = options.getInt("marginBottom");

            PlugPag plugPag = new PlugPag(mReactContext);

            PlugPagPrinterData plugPagPrinterData = new PlugPagPrinterData(_path, printerQuality, marginBottom);

            Log.v(TAG, plugPagPrinterData.getFilePath());

            PlugPagPrinterListener listener = new PlugPagPrinterListener() {

                @Override
                public void onSuccess(PlugPagPrintResult plugPagPrintResult) {
                    Log.v(TAG, plugPagPrintResult.getMessage());

                    promise.resolve(plugPagPrintResult);
                }

                @Override
                public void onError(PlugPagPrintResult plugPagPrintResult) {
                    String errorMessage = "Error message: " + plugPagPrintResult.getMessage();
                    String errorCode = "Error code" + plugPagPrintResult.getErrorCode();

                    Log.e(TAG, errorMessage);
                    Log.e(TAG, errorCode);

                    throw new Error(errorMessage + "\n" + errorCode);
                }
            };

            //Seta listener de impressão
            plugPag.setPrinterListener(listener);

            // Imprime arquivo
            plugPag.printFromFile(plugPagPrinterData);

        } catch (Exception e) {
            promise.reject(Integer.toString(e.hashCode()), e.getMessage(), e);
        }
    }

}
