package org.mariotaku.twidere.fragment.support;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;

import org.mariotaku.twidere.R;
import org.mariotaku.twidere.activity.support.MenuDialogFragment;
import org.mariotaku.twidere.menu.TwidereMenuInflater;
import org.mariotaku.twidere.model.ParcelableStatus;
import org.mariotaku.twidere.util.Utils;

import static org.mariotaku.twidere.util.Utils.setMenuForStatus;

public class StatusMenuDialogFragment extends MenuDialogFragment {

    @Override
    protected void onCreateMenu(final TwidereMenuInflater inflater, final Menu menu) {
        inflater.inflate(R.menu.action_status, menu);
        final SharedPreferences prefs = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        final Bundle args = getArguments();
        final ParcelableStatus status = args.getParcelable(EXTRA_STATUS);
        setMenuForStatus(getThemedContext(), menu, status);
        final boolean longclickToOpenMenu = prefs.getBoolean(KEY_LONG_CLICK_TO_OPEN_MENU, false);
        Utils.setMenuItemAvailability(menu, MENU_MULTI_SELECT, longclickToOpenMenu);
    }

}
