package com.slidingdemo;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.slidingdemo.slidingmenu.MenuDrawer;

@SuppressLint("NewApi")
public class MainActivity extends Activity {

	private static final String STATE_ACTIVE_POSITION = "com.slidingDemo.activePosition";
	private static final String STATE_CONTENT_TEXT = "com.slidingDemo.contentText";
	public static Boolean IS_SLIDE_BTN_CLICKED = false;

	private MenuDrawer mMenuDrawer;

	private MenuAdapter mAdapter;
	private ListView mList;

	private int mActivePosition = -1;
	private String mContentText;
	private TextView mContentTextView;
	EditText user;
	EditText pass;


	@Override
	protected void onCreate(Bundle inState) {
		super.onCreate(inState);

		if (inState != null) {
			mActivePosition = inState.getInt(STATE_ACTIVE_POSITION);
			mContentText = inState.getString(STATE_CONTENT_TEXT);
		}

		mMenuDrawer = MenuDrawer.attach(this, MenuDrawer.MENU_DRAG_CONTENT);
		mMenuDrawer.setContentView(R.layout.main);
		
		user = (EditText)findViewById(R.id.editTextUsername);
		pass = (EditText)findViewById(R.id.editTextPassword);

		List<Object> items = new ArrayList<Object>();
		items.add(new Item("Item 1", R.drawable.ic_launcher));
		items.add(new Item("Item 2", R.drawable.ic_launcher));
		items.add(new Category("Cat 1"));
		items.add(new Item("Item 3", R.drawable.ic_launcher));
		items.add(new Item("Item 4", R.drawable.ic_launcher));
		items.add(new Category("Cat 2"));
		items.add(new Item("Item 5", R.drawable.ic_launcher));
		items.add(new Item("Item 6", R.drawable.ic_launcher));
		items.add(new Category("Cat 3"));
		items.add(new Item("Item 7", R.drawable.ic_launcher));
		items.add(new Item("Item 8", R.drawable.ic_launcher));
		items.add(new Category("Cat 4"));
		items.add(new Item("Item 9", R.drawable.ic_launcher));
		items.add(new Item("Item 10", R.drawable.ic_launcher));

		mList = new ListView(this);
		mAdapter = new MenuAdapter(items);
		mList.setAdapter(mAdapter);
		mList.setOnItemClickListener(mItemClickListener);
		mList.setOnScrollListener(new AbsListView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				mMenuDrawer.invalidate();
			}
		});

		mMenuDrawer.setMenuView(R.layout.menu_drawer);

		// mMenuDrawer.setMenuView(mList);

		/*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
	           // getActionBar().setDisplayHomeAsUpEnabled(true);
	        }*/

		mContentTextView = (TextView) findViewById(R.id.textView);
		mContentTextView.setText(mContentText);
	}

	private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			mActivePosition = position;
			mMenuDrawer.setActiveView(view, position);
			mContentTextView.setText(((TextView) view).getText());
			mMenuDrawer.closeMenu();
		}
	};

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(STATE_ACTIVE_POSITION, mActivePosition);
		outState.putString(STATE_CONTENT_TEXT, mContentText);
	}

/*	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			mMenuDrawer.toggleMenu();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}*/

	public void onSlideBtnClick(View v){
		/*if(IS_SLIDE_BTN_CLICKED){
	    		IS_SLIDE_BTN_CLICKED = false;
	    		mMenuDrawer.closeMenu();
	    	}else {
	    		IS_SLIDE_BTN_CLICKED = true;
	    		mMenuDrawer.openMenu();
	    	}*/
		mMenuDrawer.openMenu();
	}
	
	public void onLoginButtonClick(View v){
		String[] userCreds = new String[2];
		userCreds[0] = user.getText().toString();
		userCreds[1] = pass .getText().toString();
		
		new LoginTask(this).execute(userCreds);
	}

	@Override
	public void onBackPressed() {
		final int drawerState = mMenuDrawer.getDrawerState();
		if (drawerState == MenuDrawer.STATE_OPEN || drawerState == MenuDrawer.STATE_OPENING) {
			mMenuDrawer.closeMenu();
			return;
		}

		super.onBackPressed();
	}



	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}*/





	private static class Item {

		String mTitle;
		int mIconRes;

		Item(String title, int iconRes) {
			mTitle = title;
			mIconRes = iconRes;
		}
	}

	private static class Category {

		String mTitle;

		Category(String title) {
			mTitle = title;
		}
	}

	private class MenuAdapter extends BaseAdapter {

		private List<Object> mItems;

		MenuAdapter(List<Object> items) {
			mItems = items;
		}

		@Override
		public int getCount() {
			return mItems.size();
		}

		@Override
		public Object getItem(int position) {
			return mItems.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public int getItemViewType(int position) {
			return getItem(position) instanceof Item ? 0 : 1;
		}

		@Override
		public int getViewTypeCount() {
			return 2;
		}

		@Override
		public boolean isEnabled(int position) {
			return getItem(position) instanceof Item;
		}

		@Override
		public boolean areAllItemsEnabled() {
			return false;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			Object item = getItem(position);

			if (item instanceof Category) {
				if (v == null) {
					v = getLayoutInflater().inflate(R.layout.menu_row_category, parent, false);
				}

				((TextView) v).setText(((Category) item).mTitle);

			} else {
				if (v == null) {
					v = getLayoutInflater().inflate(R.layout.menu_row_item, parent, false);
				}

				TextView tv = (TextView) v;
				tv.setText(((Item) item).mTitle);
				tv.setCompoundDrawablesWithIntrinsicBounds(((Item) item).mIconRes, 0, 0, 0);
			}

			v.setTag(R.id.mdActiveViewPosition, position);

			if (position == mActivePosition) {
				mMenuDrawer.setActiveView(v, position);
			}

			return v;
		}
	}

	public void onScheduleBtnClick(View v){
		/*mActivePosition = position;
		mMenuDrawer.setActiveView(view, position);*/
		mContentTextView.setText("On Schedule Screen");
		mMenuDrawer.closeMenu();
		

	}

	public void onTextBtnClick(View v){
		
		mContentTextView.setText("On Text Message Screen");
		mMenuDrawer.closeMenu();

	}

	public void onPhotosBtnClick(View v){
		
		mContentTextView.setText("On Photos and Screen Screen");
		mMenuDrawer.closeMenu();

	}

}
