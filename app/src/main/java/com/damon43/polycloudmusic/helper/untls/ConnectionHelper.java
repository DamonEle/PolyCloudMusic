package com.damon43.polycloudmusic.helper.untls;

import android.os.RemoteException;

import com.damon43.common.commonutils.CollectionUtils;
import com.damon43.polycloudmusic.IPolyMusicInterface;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * desc
 * Created by lenovo on 2017/12/18.
 */

public class ConnectionHelper {

    public static void helpOpen(@Nullable IPolyMusicInterface mConnection, @NotNull long[] mSongsIds,int position) {
        try {
            mConnection.open(CollectionUtils.getLongArrayFromOld(mSongsIds),position,-1,0);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }
}
