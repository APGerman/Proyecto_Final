package com.example.proyectofinal.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.proyectofinal.R;
import com.example.proyectofinal.TabCitas;
import com.example.proyectofinal.TabConfiguracion;
import com.example.proyectofinal.TabInformacion;
import com.example.proyectofinal.horarioActivity;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_informacion, R.string.tab_horario,R.string.tab_citas, R.string.tab_configuracion};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch(position){
            case 0:
                TabInformacion tabinf = new TabInformacion();
                return tabinf;
            case 1:
                horarioActivity tabhor = new horarioActivity();
                return tabhor;
            case 2:
                TabCitas tabcit = new TabCitas();
                return tabcit;
            case 3:
                TabConfiguracion tabcon = new TabConfiguracion();
                return tabcon;
        }
        return null;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 4;
    }
}