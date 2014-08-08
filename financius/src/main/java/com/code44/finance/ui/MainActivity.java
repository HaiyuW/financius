package com.code44.finance.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.code44.finance.R;
import com.code44.finance.adapters.NavigationAdapter;
import com.code44.finance.services.StartupService;
import com.code44.finance.ui.accounts.AccountsFragment;
import com.code44.finance.ui.overview.OverviewFragment;
import com.code44.finance.ui.transactions.TransactionsFragment;
import com.code44.finance.ui.user.UserFragment;

public class MainActivity extends BaseActivity implements NavigationFragment.NavigationListener {
    private static final String FRAGMENT_CONTENT = "FRAGMENT_CONTENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        if (savedInstanceState == null) {
            StartupService.start(this);
        }

        if (getFragmentManager().findFragmentByTag(FRAGMENT_CONTENT) == null) {
            ((NavigationFragment) getFragmentManager().findFragmentById(R.id.navigation_F)).select(NavigationAdapter.NAV_ID_OVERVIEW);
        }
    }

    @Override
    public void onNavigationItemSelected(NavigationAdapter.NavigationItem item) {
        String title = null;
        BaseFragment baseFragment = null;
        switch (item.getId()) {
            case NavigationAdapter.NAV_ID_USER:
                baseFragment = UserFragment.newInstance();
                title = getString(R.string.user);
                break;

            case NavigationAdapter.NAV_ID_OVERVIEW:
                baseFragment = OverviewFragment.newInstance();
                title = getString(R.string.overview);
                break;

            case NavigationAdapter.NAV_ID_ACCOUNTS:
                baseFragment = AccountsFragment.newInstance(ModelListActivity.Mode.VIEW);
                title = getString(R.string.accounts);
                break;

            case NavigationAdapter.NAV_ID_TRANSACTIONS:
                baseFragment = TransactionsFragment.newInstance();
                title = getString(R.string.transactions);
                break;
        }

        toolbarHelper.setTitle(title);
        toolbarHelper.closeDrawer();
        if (baseFragment != null) {
            loadFragment(baseFragment);
        }
    }

    private void loadFragment(BaseFragment fragment) {
        if (fragment instanceof OverviewFragment) {
            toolbarHelper.setElevation(0);
        } else {
            toolbarHelper.setElevation(getResources().getDimension(R.dimen.elevation_header));
        }

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.content_V, fragment, FRAGMENT_CONTENT)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }
}
