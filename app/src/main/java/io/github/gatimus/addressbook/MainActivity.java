package io.github.gatimus.addressbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity implements AddressBookFragment.OnFragmentInteractionListener {

    public static final String ROW_ID = "row_id"; // Intent extra key

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AddressBookFragment addressBookFragment = AddressBookFragment.newInstance();
        getFragmentManager().beginTransaction().replace(R.id.fragmentHolder, addressBookFragment).commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.addressbook_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // create a new Intent to launch the AddEditContact Activity
        Intent addNewContact = new Intent(getApplicationContext(), AddEditContact.class);
        startActivity(addNewContact); // start the AddEditContact Activity
        return super.onOptionsItemSelected(item); // call super's method
    }

    @Override
    public void onFragmentInteraction(long id) {
        // create an Intent to launch the ViewContact Activity
        Intent viewContact =  new Intent(getApplicationContext(), ViewContact.class);
        // pass the selected contact's row ID as an extra with the Intent
        viewContact.putExtra(ROW_ID, id);
        startActivity(viewContact); // start the ViewContact Activity
    }

}
