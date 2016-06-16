package com.jeifbell.ektuhi;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class MainActivity extends Activity {
	
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mPlanetTitles;
    public static String[] names;
    public static ArrayList<String> catList = new ArrayList<String>();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        names = getResources().getStringArray(R.array.content_array);
        setContentView(R.layout.activity_main);

        mTitle = mDrawerTitle = getTitle();
        
        ArrayList<HashMap<String, String>> catItems = new ArrayList<HashMap<String, String>>();
    	DatabaseHandler2 db = new DatabaseHandler2(this);
    	HashMap<String, String> map1 = new HashMap<String, String>();   
		map1.put("Name", "Gurbani Tutor");
		catList.add("Home");
		catItems.add(map1);
		HashMap<String, String> map2 = new HashMap<String, String>();   
		map2.put("Name", "About us");
		catList.add("About Us");
		catItems.add(map2);
		HashMap<String, String> map4 = new HashMap<String, String>();   
		map4.put("Name", "Contact us");
		catList.add("Contact Us");
		catItems.add(map4);
        mPlanetTitles = getResources().getStringArray(R.array.planets_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mDrawerList.setAdapter(new SimpleAdapter(this, catItems, R.layout.drawer_text, new String[] {"Name"}, new int[] {R.id.text1 }));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.rgb(249, 166, 26)));

      
        mDrawerToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); 
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch(item.getItemId()) {
        
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        Fragment fragment = new ArticleFragment();
        Bundle args = new Bundle();
        args.putInt(ArticleFragment.ARG_CATEGORY_NUMBER, position);
        fragment.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
        mDrawerList.setItemChecked(position, true);
        setTitle(catList.get(position));
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    public static class ArticleFragment extends Fragment{
        public static final String ARG_CATEGORY_NUMBER = "category_number";
        public static final ArrayList<String> art_title = new ArrayList<String>();
        public ArticleFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        	View rootView = null; /*= inflater.inflate(R.layout.about, container, false);*/
        	int cat_i = getArguments().getInt(ARG_CATEGORY_NUMBER);
        	ArrayList<HashMap<String, Object>> menuItems = new ArrayList<HashMap<String, Object>>();
        	DatabaseHandler2 db = new DatabaseHandler2(getActivity());
        	ImageAdapter imageAdapter = new ImageAdapter(getActivity());
        	if((catList.get(cat_i)).equals("About Us")){
        		rootView = inflater.inflate(R.layout.about, container, false);
        		Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),
                        "fonts/EkTuhiBaania.ttf");
        		TextView txt = (TextView) rootView.findViewById(R.id.abt_t1);
        		TextView txt1 = (TextView) rootView.findViewById(R.id.abt_t4);
        		txt.setTypeface(tf);
        		txt1.setTypeface(tf);
        	}else if((catList.get(cat_i)).equals("Contact Us")){
        		rootView = inflater.inflate(R.layout.contact, container, false);
        		Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),
                        "fonts/EkTuhiBaania.ttf");
        		TextView txt = (TextView) rootView.findViewById(R.id.cnt_t1);
        		txt.setTypeface(tf);
        	}else if((catList.get(cat_i)).equals("Home")){
        		System.out.print("In Blog");
        		art_title.removeAll(art_title);
                for(int v=1;v<1431;v++) {
                    imageAdapter.mThumbIds.add("Page "+v);
                }
       		rootView = inflater.inflate(R.layout.article_list, container, false);
       		GridView articles = (GridView) rootView.findViewById(R.id.articles);
       		articles.setAdapter(imageAdapter);
       		//SimpleAdapter adapter = new SimpleAdapter(getActivity(), menuItems, R.layout.new_list, new String[] {"Title"}, new int[] {R.id.title_txt});
     		//articles.setItemsCanFocus(true);
       		//ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, numbers);
     		//articles.setAdapter(adapter);
     		articles.setOnItemClickListener(new ArticleClickListener());
        	}
        	
            return rootView;
        }
        private class ArticleClickListener implements GridView.OnItemClickListener {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int articleNum, long id) {
            	selectArticle(articleNum);
            }
        }
        
        private void selectArticle(int articleNum){
        	Intent i = new Intent(getActivity(), ArticleContent.class);
            i.putExtra("audio", articleNum);
			startActivity(i);
        } 
    }
}