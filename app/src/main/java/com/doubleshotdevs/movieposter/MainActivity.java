package com.doubleshotdevs.movieposter;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private static final String KEY_CURRENT_PAGE = "current_page";

    private static final String TAG_PAGE_MAIN = "page_main";
    private static final String TAG_PAGE_FAVORITES = "page_favorites";
    private static final String TAG_PAGE_CONTACT = "page_contact";
    private static final String TAG_PAGE_STILL = "page_still";

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private BottomNavigationView bottomNavigationView;

    private int currentPageId = R.id.nav_main;

    // Sets up the toolbar, the side menu, the bottom bar and the four pages
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        MaterialToolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(item -> {
            showPage(item.getItemId());
            return true;
        });
        bottomNavigationView.setOnItemSelectedListener(item -> {
            showPage(item.getItemId());
            return true;
        });

        final AppBarLayout appBarLayout = findViewById(R.id.appBarLayout);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            appBarLayout.setPadding(bars.left, bars.top, bars.right, 0);
            v.setPadding(0, 0, 0, bars.bottom);
            return insets;
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragmentContainer, new PostersFragment(), TAG_PAGE_MAIN)
                    .add(R.id.fragmentContainer, new FavoritesFragment(), TAG_PAGE_FAVORITES)
                    .add(R.id.fragmentContainer, new ContactFragment(), TAG_PAGE_CONTACT)
                    .add(R.id.fragmentContainer, new StillFragment(), TAG_PAGE_STILL)
                    .commitNow();
        } else {
            currentPageId = savedInstanceState.getInt(KEY_CURRENT_PAGE, R.id.nav_main);
        }
        showPage(currentPageId);
    }

    // Remembers the current page so rotating the phone doesn't reset it
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_CURRENT_PAGE, currentPageId);
    }

    // Shows one page, hides the others, and updates the title and both menus
    private void showPage(int pageId) {
        currentPageId = pageId;

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        String visibleTag = tagForPage(pageId);
        for (String tag : new String[]{TAG_PAGE_MAIN, TAG_PAGE_FAVORITES, TAG_PAGE_CONTACT, TAG_PAGE_STILL}) {
            Fragment page = fragmentManager.findFragmentByTag(tag);
            if (page == null) {
                continue;
            }
            if (tag.equals(visibleTag)) {
                transaction.show(page);
            } else {
                transaction.hide(page);
            }
        }
        transaction.commit();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(titleForPage(pageId));
        }
        navigationView.setCheckedItem(pageId);
        bottomNavigationView.getMenu().findItem(pageId).setChecked(true);
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    // Returns the toolbar title for each page
    private int titleForPage(int pageId) {
        if (pageId == R.id.nav_favorite) {
            return R.string.title_favorites;
        } else if (pageId == R.id.nav_contact) {
            return R.string.title_contact;
        } else if (pageId == R.id.nav_still) {
            return R.string.title_still;
        }
        return R.string.posters;
    }

    // Returns which Fragment tag belongs to each page
    private String tagForPage(int pageId) {
        if (pageId == R.id.nav_favorite) {
            return TAG_PAGE_FAVORITES;
        } else if (pageId == R.id.nav_contact) {
            return TAG_PAGE_CONTACT;
        } else if (pageId == R.id.nav_still) {
            return TAG_PAGE_STILL;
        }
        return TAG_PAGE_MAIN;
    }
}