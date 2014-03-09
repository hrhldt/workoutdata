package com.mhra.workoutdata.main;


import java.util.ArrayList;

import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.transition.Transition;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.mhra.workoutdata.R;
import com.mhra.workoutdata.drawer.NavDrawerItem;
import com.mhra.workoutdata.drawer.NavDrawerListAdapter;
import com.mhra.workoutdata.fragments.HomeFragment;
import com.mhra.workoutdata.fragments.LoginFragment;

public class MainActivity extends FragmentActivity {
	TextView textView1;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	// nav drawer title
	private CharSequence mDrawerTitle;

	// used to store app title
	private CharSequence mTitle;

	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;
	LoginFragment loginFragment;
	@Override
	public void onCreate (Bundle b){
		super.onCreate(b);
		setContentView(R.layout.page_main);

		mTitle = mDrawerTitle = getTitle();

		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
		Log.d("MAIN_ACTIVITY","navMenuTitles: " + navMenuTitles.toString());
		// nav drawer icons from resources
		//		navMenuIcons = getResources()
		//				.obtainTypedArray(R.array.nav_drawer_icons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());
		navDrawerItems = new ArrayList<NavDrawerItem>();

		// adding nav drawer items to array
		// Home
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0]));
		// Find People
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1]));
		// Photos
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2]));
		// Communities, Will add a counter here
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3]));
		// Pages
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[4]));
		// What's hot, We  will add a counter here
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[5]));


		// Recycle the typed array
		//		navMenuIcons.recycle();

		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);

		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.burger_menu96x96, //nav menu toggle icon
				R.string.app_name, // nav drawer open - description for accessibility
				R.string.app_name // nav drawer close - description for accessibility
				){
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (b == null) {
			// on first time display view for first nav item
			displayView(0);
			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(0, true);
			mDrawerList.setSelection(0);
			setTitle(navMenuTitles[0]);
			mDrawerLayout.closeDrawer(mDrawerList);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		} else {
			return false;
		}
		// Handle action bar actions click
		//		switch (item.getItemId()) {
		//		case R.id.action_settings:
		//			return true;
		//		default:
		//			return super.onOptionsItemSelected(item);
		//		}
	}
	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	private void displayView(int position) {
		// update the main content by replacing fragments
		Fragment fragment = null;
		switch (position) {
		case 0:
			fragment = new LoginFragment();
			break;
		case 1:
			fragment = new HomeFragment();
			break;
			//		case 2:
			//			fragment = new PhotosFragment();
			//			break;
			//		case 3:
			//			fragment = new CommunityFragment();
			//			break;
			//		case 4:
			//			fragment = new PagesFragment();
			//			break;
			//		case 5:
			//			fragment = new WhatsHotFragment();
			//			break;

		default:
			break;
		}

		if (fragment != null) {
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();;
//			.beginTransaction()
			

			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}
	
	
	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView(position);
		}
	}

	/***
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		//		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
}

//	public void initTextView() {
//		textView1 = (TextView)findViewById(R.id.textView1);
//		textView1.setText("Phat Party "+App.currentUser.name);
//	}
//}
