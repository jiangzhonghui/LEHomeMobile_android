package my.home.domain.usecase;


import android.content.Context;
import android.util.Log;

import com.squareup.otto.Subscribe;

import java.lang.ref.WeakReference;

import my.home.common.BusProvider;
import my.home.domain.events.DLoadAutoCompleteConfEvent;
import my.home.domain.events.DShowAutoCompleteItemEvent;
import my.home.model.datasource.AutoCompleteItemDataSource;
import my.home.model.datasource.AutoCompleteItemDataSourceImpl;
import my.home.model.events.MConfAutoCompleteItemEvent;
import my.home.model.events.MGetAutoCompleteItemEvent;

/**
 * Created by legendmohe on 15/2/8.
 */
public class AutoCompleteItemUsecaseImpl implements AutoCompleteItemUsecase {

    private final AutoCompleteItemDataSource dataSource;
    private int mMode = MODE_DO_NOTHING;
    private String mInputText = "";
    private String mConfString = "";
    private WeakReference<Context> mContext;

    public AutoCompleteItemUsecaseImpl(Context context) {
        this.dataSource = AutoCompleteItemDataSourceImpl.getInstance();
        this.mContext = new WeakReference<Context>(context);
    }

    @Override
    public void execute() {
        if (mMode == MODE_DO_NOTHING) {
            Log.w(TAG, "Didn't set mode for this usecase.");
            return;
        }

        try {
            switch (this.mMode) {
                case MODE_GETITEM:
                    this.dataSource.getAutoCompleteItems(mInputText);
                    break;
                case MODE_LOAD_CONF:
                    this.dataSource.loadConf(mContext.get());
                    break;
                case MODE_SAVE_CONF:
                    this.dataSource.saveConf(mContext.get(), mConfString);
                    break;
                default:
                    break;
            }
        } catch (Exception ex) {
            Log.e(TAG, ex.toString());
        } finally {
            this.mMode = MODE_DO_NOTHING;
            BusProvider.getRestBusInstance().unregister(this);
        }
    }

    @Subscribe
    public void onGetAutoCompleteItems(MGetAutoCompleteItemEvent event) {
        BusProvider.getRestBusInstance().post(new DShowAutoCompleteItemEvent(event.getResultList()));
    }

    @Subscribe
    public void onConfAutoCompleteItems(MConfAutoCompleteItemEvent event) {
        BusProvider.getRestBusInstance().post(new DLoadAutoCompleteConfEvent(event.getReturnCode()));
    }

    @Override
    public int getMode() {
        return mMode;
    }

    @Override
    public AutoCompleteItemUsecase setMode(int mMode) {
        if (mMode != MODE_DO_NOTHING)
            BusProvider.getRestBusInstance().register(this);
        this.mMode = mMode;
        return this;
    }

    @Override
    public AutoCompleteItemUsecase setInputText(String inputText) {
        this.mInputText = inputText;
        return this;
    }

    @Override
    public String getConfString() {
        return mConfString;
    }

    @Override
    public AutoCompleteItemUsecase setConfString(String mConfString) {
        this.mConfString = mConfString;
        return this;
    }
}