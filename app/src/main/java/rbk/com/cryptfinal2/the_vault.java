package rbk.com.cryptfinal2;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;


public class the_vault extends Fragment {
    ViewPager viewPager;
    FloatingActionButton add;
    EditText encryptedtext,notes_search,keyvalue,name;
    public NotesDBAdapter NBHelper;
    private ListView noteslist;
    private Cursor nCursor;
    private String nsstring,ssstring;

    static final private int EDIT_NOTES = Menu.FIRST + 0;
    static final private int DELETE_NOTES = Menu.FIRST + 1;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.the_vault, container, false);
    }

    /*@Override
    public void onResume() {
        NotesList();
        super.onResume();
    }*/




    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        NBHelper = new NotesDBAdapter(getActivity());
        NBHelper.open();
        name = (EditText) getActivity().findViewById(R.id.txtname);
        encryptedtext = (EditText) getActivity().findViewById(R.id.editText);
        keyvalue = (EditText) getActivity().findViewById(R.id.editText2);
        add = (FloatingActionButton) getActivity().findViewById(R.id.add);
        viewPager = (ViewPager) getActivity().findViewById(R.id.pager);
        noteslist = (ListView) getActivity().findViewById(R.id.encryptedlist);
        notes_search = (EditText) getActivity().findViewById(R.id.notes_search);

   /* public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.the_vault);
        NBHelper = new NotesDBAdapter(this);
        NBHelper.open();

*/
        add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                viewPager.setCurrentItem(0);
                encryptedtext.requestFocus();
                encryptedtext.setText("");
                name.setText("");
                keyvalue.setText("");
            }


        });





        NotesList();

        registerForContextMenu(noteslist);

    /*    public void populateFields()
            {
            if (mRowId != null)
                {
                Cursor nCursor = NBHelper.fetchNotes(mRowId);
                startManagingCursor(cus);

                company_text.setText(cus.getString(cus
                        .getColumnIndexOrThrow(CustomerDBAdapter.KEY_COMPANY)));

*/

            notes_search.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable arg0) {
            }

            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
            }

            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                nsstring = notes_search.getText().toString();

                if(notes_search.getText().toString().trim().length()==0)
                    NotesList();
                else
                    NotesSearchList(nsstring);
            }
        });



        noteslist.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id)
            {

                Cursor asg=NBHelper.fetchNotes(id);
                getActivity().startManagingCursor(asg);
                String show_name=asg.getString(asg.getColumnIndexOrThrow(NotesDBAdapter.KEY_NAME));
                String show_message=asg.getString(asg.getColumnIndexOrThrow(NotesDBAdapter.KEY_ENCRYPTEDTEXT));
                String show_key=asg.getString(asg.getColumnIndexOrThrow(NotesDBAdapter.KEY_SECURITY));

                viewPager.setCurrentItem(0);

                encryptedtext.setText(show_message);
                keyvalue.setText(show_key);
                name.setText(show_name);


            }
        });
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0,EDIT_NOTES,Menu.NONE,"Edit");
        menu.add(0,DELETE_NOTES,Menu.NONE,"Delete");
    }


    private void NotesList()
    {
        nCursor = NBHelper.fetchAllNotes();
        getActivity().startManagingCursor(nCursor);

        String[] from = new String[] {
                NotesDBAdapter.KEY_NAME
        };
        int[] to = new int[] {
                R.id.notes_text
        };
        SpecialAdapter ndata = new SpecialAdapter(getActivity(),
                R.layout.mmain_row, nCursor, from, to);
        noteslist.setAdapter(ndata);
    }

    private void  NotesSearchList(String search)
    {
        nCursor = NBHelper.searchNotes(search);
        getActivity().startManagingCursor(nCursor);

        String[] from = new String[] {
                NotesDBAdapter.KEY_NAME
        };
        int[] to = new int[] {
                R.id.notes_text
        };

        SpecialAdapter ndata = new SpecialAdapter(getActivity(),
                R.layout.mmain_row, nCursor, from, to);
        noteslist.setAdapter(ndata);
    }

    public boolean onContextItemSelected(MenuItem item)
    {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId())
        {
            case EDIT_NOTES:
               Cursor asg=NBHelper.fetchNotes(info.id);
                getActivity().startManagingCursor(asg);
                String show_name=asg.getString(asg.getColumnIndexOrThrow(NotesDBAdapter.KEY_NAME));
                String show_message=asg.getString(asg.getColumnIndexOrThrow(NotesDBAdapter.KEY_ENCRYPTEDTEXT));
                String show_key=asg.getString(asg.getColumnIndexOrThrow(NotesDBAdapter.KEY_SECURITY));


                viewPager.setCurrentItem(0);
                encryptedtext.setSelection(0);
                encryptedtext.setText(show_message);
                keyvalue.setText(show_key);
                name.setText(show_name);
                return true;


            case DELETE_NOTES:
                Cursor msg = NBHelper.fetchNotes(info.id);
                getActivity().startManagingCursor(msg);
                String message = msg.getString(msg.getColumnIndexOrThrow(NotesDBAdapter.KEY_NAME));
                Context context = getActivity();
                AlertDialog.Builder ad = new AlertDialog.Builder(context);
                ad.setTitle(message);
                ad.setMessage("Are you sure you want to delete this note?");
                ad.setPositiveButton("Ok", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int arg1)
                    {
                        NBHelper.deleteNotes(info.id);
                        ssstring = notes_search.getText().toString();
                        if(notes_search.getText().toString().trim().length()==0)
                            NotesList();
                        else
                            NotesSearchList(ssstring);
                    }
                });
                ad.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int arg1)
                            {
                            }
                        }
                );
                ad.setCancelable(true);
                ad.show();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    /*    @Override
    public void onResume(){
        super.onResume();
        // put your code here...

        NotesList();

    }*/


    @Override
    public void onDestroy()
    {
        super.onDestroy();
        if (NBHelper != null)
        {
            NBHelper.close();
        }
    }



   public void onRestart() {
        NotesList();
    }

   /* public void onResume() {
        {
            NotesList();
        }
    }*/



}

