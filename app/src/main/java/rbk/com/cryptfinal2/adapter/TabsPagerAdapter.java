package rbk.com.cryptfinal2.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import rbk.com.cryptfinal2.encrypt_decrypt;
import rbk.com.cryptfinal2.the_vault;


public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

        @Override
        public Fragment getItem(int index) {

            switch (index) {
                case 0:
                    return new encrypt_decrypt();

                case 1:
                    return new the_vault();

            }

            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }


}
