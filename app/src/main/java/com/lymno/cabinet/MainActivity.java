package com.lymno.cabinet;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int LOG_OFF = 1;
    private static final int DIARY = 10;
    private static final int TIMETABLE = 11;
    public CurrentState state = new CurrentState(10,0);
    //save our header or result
    private AccountHeader headerResult = null;
    private Drawer result = null;
    String[] kidsNames = {"test","test","test"};
    final int ACCOUNT = 2;
    SharedPreferences cache;
    private KidsDataBase db = new KidsDataBase(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Bundle bundle = new Bundle();
//        bundle.putString("edttext", "api/school/timetable?School=34&Class=9");
        // set Fragmentclass Arguments
//        Fragment1 newFragment = new Fragment1();
//        newFragment.setArguments(bundle);
//        getFragmentManager().beginTransaction().replace(R.id.frame_container, newFragment).commit();

        final ArrayList<Kid> kids = db.getKids();
        if (kids.size() == 0){
            kids.add(new Kid("dima","molotov", "ivanych", "122", "7b", "13081996", 2, "ee333"));
        }

        for (int i = 0; i < kids.size(); ++i) {
            kidsNames[i] = kids.get(i).getFirstName();
        }
        cache = getSharedPreferences("cache", MODE_PRIVATE);
        String name = cache.getString("firstName", "");
        String lastName = cache.getString("lastName", "");
        String email = cache.getString("email", "");

        //Remove line to test RTL support
        //getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        String test = kids.get(0).getFirstName();

        // Handle Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, kidsNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = (Spinner) findViewById(R.id.spinner_nav);
        spinner.setAdapter(adapter);
        // заголовок
        spinner.setPrompt("firstName");
        // выделяем элемент
        spinner.setSelection(0);
        // устанавливаем обработчик нажатия
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // показываем позиция нажатого элемента
                state.setKid(position);
                Bundle bundle = new Bundle();
                String school = kids.get(position).getSchool();
                String kidClass = kids.get(position).getClassOfSchool();
                bundle.putString("edttext", "api/school/timetable?School="+school+"&Class="+kidClass);
                // set Fragmentclass Arguments
                Fragment1 newFragment = new Fragment1();
                newFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.frame_container, newFragment).commit();

            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });



        // Create a few sample profile withIcon(getResources().getDrawable(R.drawable.profile2));
        // NOTE you have to define the loader logic too. See the CustomApplication for more details
        //final IProfile profile = new ProfileDrawerItem().withName("Mike Penz").withEmail("mikepenz@gmail.com").withIcon("https://avatars3.githubusercontent.com/u/1476232?v=3&s=460").withIdentifier(1);;
        //final IProfile profile2 = new ProfileDrawerItem().withName("Bernat Borras").withEmail("alorma@github.com").withIcon(Uri.parse("https://avatars3.githubusercontent.com/u/887462?v=3&s=460"));
        final IProfile adultProfile = new ProfileDrawerItem().withName(name + " " + lastName).withEmail(email).withIcon(getResources().getDrawable(R.drawable.profile2));


        // Create the AccountHeader
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(
                        adultProfile,
                        //don't ask but google uses 14dp for the add account icon in gmail but 20dp for the normal icons (like manage account)
                        new ProfileSettingDrawerItem().withName("Управление аккаунтом").withIcon(GoogleMaterial.Icon.gmd_settings).withIdentifier(ACCOUNT),
                        new ProfileSettingDrawerItem().withName("Выйти из аккаунта").withDescription("Add new GitHub Account").withIcon(new IconicsDrawable(this,
                                FontAwesome.Icon.faw_sign_out).actionBarSize().paddingDp(5).colorRes(R.color.material_drawer_primary_text)).withIdentifier(LOG_OFF)

                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                        //sample usage of the onProfileChanged listener
                        //if the clicked item has the identifier 1 add a new profile ;)
                        if (profile instanceof IDrawerItem && ((IDrawerItem) profile).getIdentifier() == LOG_OFF) {
                            SharedPreferences.Editor ed = cache.edit();
                            ed.putString("IDToken", "");
                            ed.putString("firstName", "");
                            ed.putString("lastName", "");
                            ed.putString("middleName", "");
                            ed.putString("email", "");
                            ed.apply();
                            Context context = MainActivity.this;
                            Intent singIntent = new Intent(context, SignInOrRegister.class);
                            singIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            singIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(singIntent);
                        }
                        else if (profile instanceof IDrawerItem && ((IDrawerItem) profile).getIdentifier() == ACCOUNT) {
//                            Intent intent = new Intent(MainActivity.this, SignIn.class);
//                            MainActivity.this.startActivity(intent);
                        }
                        //false if you have not consumed the event and it should close the drawer
                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .build();

        //Create the drawer
        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Расписание уроков").withIcon(FontAwesome.Icon.faw_book).withIdentifier(TIMETABLE).withCheckable(true),
                        new PrimaryDrawerItem().withName("Оценки и посещаемость").withIcon(FontAwesome.Icon.faw_check_square).withIdentifier(4).withCheckable(true),
                        new PrimaryDrawerItem().withName("Запись к врачу").withIcon(FontAwesome.Icon.faw_heart).withCheckable(false),
                        new DividerDrawerItem(),
//                        new PrimaryDrawerItem().withName("Дима").withIcon(R.drawable.ava_dave).withIdentifier(1).withCheckable(false),
//                        new PrimaryDrawerItem().withName("Дима младший").withIcon(R.drawable.ava_dave_y).withIdentifier(2).withCheckable(false),
//                        new PrimaryDrawerItem().withName("Настя").withIcon(R.drawable.ava_ana).withIdentifier(3).withCheckable(false),

//                        new PrimaryDrawerItem().withDescription("A more complex sample").withName(R.string.element).withIcon(GoogleMaterial.Icon.gmd_adb).withIdentifier(5).withCheckable(false),
//                        new PrimaryDrawerItem().withName(R.string.element).withIcon(GoogleMaterial.Icon.gmd_style).withIdentifier(6).withCheckable(false),
//                        new PrimaryDrawerItem().withName(R.string.element).withIcon(GoogleMaterial.Icon.gmd_battery_charging_full).withIdentifier(7).withCheckable(false),
//                        new PrimaryDrawerItem().withName(R.string.element).withIcon(GoogleMaterial.Icon.gmd_style).withIdentifier(8).withCheckable(false),
//                        new PrimaryDrawerItem().withName(R.string.element).withIcon(GoogleMaterial.Icon.gmd_my_location).withIdentifier(9).withCheckable(false),
//                        new SectionDrawerItem().withName(R.string.element),
                        new SecondaryDrawerItem().withName("Настройки").withIcon(FontAwesome.Icon.faw_gears).withIdentifier(20).withCheckable(false),
                        new SecondaryDrawerItem().withName("Помощь").withIcon(FontAwesome.Icon.faw_question_circle).withIdentifier(10).withCheckable(false)
//                        new DividerDrawerItem()
                ) // add the items we want to use with our Drawer
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                        //check if the drawerItem is set.
                        //there are different reasons for the drawerItem to be null
                        //--> click on the header
                        //--> click on the footer
                        //those items don't contain a drawerItem

                        if (drawerItem != null) {
                            Intent intent = null;
                            if (drawerItem.getIdentifier() == 1) {

                                intent = new Intent(MainActivity.this, SignIn.class);

                            } else if (drawerItem.getIdentifier() == 4) {
                                state.setKid(position);
                                Bundle bundle = new Bundle();
                                String school = kids.get(position).getSchool();
                                String kidClass = kids.get(position).getClassOfSchool();
                                bundle.putString("edttext", "/api/school/misses?id=11");
                                // set Fragmentclass Arguments
                                FragmentMarks newFragment = new FragmentMarks();
                                newFragment.setArguments(bundle);
                                getFragmentManager().beginTransaction().replace(R.id.frame_container, newFragment).commit();
                            /*
                                intent = new Intent(SimpleHeaderDrawerActivity.this, ActionBarDrawerActivity.class);
                            } else if (drawerItem.getIdentifier() == 3) {
                                intent = new Intent(SimpleHeaderDrawerActivity.this, MultiDrawerActivity.class);
                            } else if (drawerItem.getIdentifier() == 4) {
                                intent = new Intent(SimpleHeaderDrawerActivity.this, SimpleNonTranslucentDrawerActivity.class);
                            } else if (drawerItem.getIdentifier() == 5) {
                                intent = new Intent(SimpleHeaderDrawerActivity.this, ComplexHeaderDrawerActivity.class);
                            } else if (drawerItem.getIdentifier() == 6) {
                                intent = new Intent(SimpleHeaderDrawerActivity.this, SimpleFragmentDrawerActivity.class);
                            } else if (drawerItem.getIdentifier() == 7) {
                                intent = new Intent(SimpleHeaderDrawerActivity.this, EmbeddedDrawerActivity.class);
                            } else if (drawerItem.getIdentifier() == 8) {
                                intent = new Intent(SimpleHeaderDrawerActivity.this, FullscreenDrawerActivity.class);
                            } else if (drawerItem.getIdentifier() == 9) {
                                intent = new Intent(SimpleHeaderDrawerActivity.this, CustomContainerActivity.class);
                            } else if (drawerItem.getIdentifier() == 20) {
                                intent = new LibsBuilder()
                                        .withFields(R.string.class.getFields())
                                        .withActivityStyle(Libs.ActivityStyle.LIGHT_DARK_TOOLBAR)
                                        .intent(SimpleHeaderDrawerActivity.this);
                                        */
                            }

                            if (intent != null) {
                                MainActivity.this.startActivity(intent);
                            }
                        }

                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(true)
                .build();

        //only set the active selection or active profile if we do not recreate the activity
        if (savedInstanceState == null) {
            // set the selection to the item with the identifier 10
            result.setSelectionByIdentifier(TIMETABLE, false);

            //set the active profile
            headerResult.setActiveProfile(adultProfile);
        }
    }
    /*
    private OnCheckedChangeListener onCheckedChangeListener = new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(IDrawerItem drawerItem, CompoundButton buttonView, boolean isChecked) {
            if (drawerItem instanceof Nameable) {
                Log.i("material-drawer", "DrawerItem: " + ((Nameable) drawerItem).getName() + " - toggleChecked: " + isChecked);
            } else {
                Log.i("material-drawer", "toggleChecked: " + isChecked);
            }
        }
    };
    */

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //add the values which need to be saved from the drawer to the bundle
        outState = result.saveInstanceState(outState);
        //add the values which need to be saved from the accountHeader to the bundle
        outState = headerResult.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

}