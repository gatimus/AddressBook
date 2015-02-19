package io.github.gatimus.addressbook;

import android.app.Activity;
import android.app.ListFragment;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class AddressBookFragment extends ListFragment {


    private CursorAdapter contactAdapter; // adapter for ListView
    private OnFragmentInteractionListener mListener;

    public static AddressBookFragment newInstance() {
        return new AddressBookFragment();
    }

    public AddressBookFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // map each contact's name to a TextView in the ListView layout
        String[] from = new String[]{"name"};
        int[] to = new int[]{R.id.contactTextView};
        contactAdapter = new SimpleCursorAdapter(getActivity().getApplicationContext(), R.layout.contact_list_item, null, from, to);
        setListAdapter(contactAdapter); // set contactView's adapter

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }

    }

    @Override
    public void onStart (){
        super.onStart();
        // create new GetContactsTask and execute it
        new GetContactsTask().execute((Object[]) null);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Cursor cursor = contactAdapter.getCursor(); // get current Cursor

        if (cursor != null)
            cursor.deactivate(); // deactivate it

        contactAdapter.changeCursor(null); // adapted now has no Cursor
        mListener = null;
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        if(mListener != null){
            mListener.onFragmentInteraction(id);
        }
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(long id);
    }

    // performs database query outside GUI thread
    private class GetContactsTask extends AsyncTask<Object, Object, Cursor> {
        DatabaseConnector databaseConnector = new DatabaseConnector(getActivity().getApplicationContext());

        // perform the database access
        @Override
        protected Cursor doInBackground(Object... params) {
            databaseConnector.open();

            // get a cursor containing call contacts
            return databaseConnector.getAllContacts();
        } // end method doInBackground

        // use the Cursor returned from the doInBackground method
        @Override
        protected void onPostExecute(Cursor result) {
            contactAdapter.changeCursor(result); // set the adapter's Cursor
            databaseConnector.close();
        } // end method onPostExecute
    } // end class GetContactsTask

}
