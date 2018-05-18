package rbk.com.cryptfinal2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.ShareActionProvider;
import android.text.InputType;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


public class encrypt_decrypt extends Fragment {

    public Long mRowId;
    public NotesDBAdapter NBHelper;
    String outstring1,outstring2,name1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View rootView = inflater.inflate(R.layout.txtencrypt_decrypt, container, false);

        encrypt = (Button) rootView.findViewById(R.id.encrypt);
        decrypt = (Button) rootView.findViewById(R.id.decrypt);
        save=(Button) rootView.findViewById(R.id.save);
        viewPager = (ViewPager) getActivity().findViewById(R.id.pager);
        NBHelper = new NotesDBAdapter(getActivity());
        NBHelper.open();
        mRowId=null;
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
       // name1=name.getText().toString();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(name.getText().toString().trim().length()>0) {
                    saveState();

                    mRowId = null;
                }
                else
                {
                    Toast.makeText(getActivity(),"Enter the name of the note!",Toast.LENGTH_LONG).show();
                }
            }
        });


        encrypt.setOnClickListener(new View.OnClickListener() {

            //@Override
            public void onClick(View v) {
                try
                {
                    outstring1=EncryptFunction(encryptedtext.getText().toString(),keyvalue.getText().toString());
                    encryptedtext.setText(outstring1);

                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);

                    Toast.makeText(getActivity(),"Encrypted",Toast.LENGTH_SHORT).show();

                }catch(Exception e)
                {
                    Toast.makeText(getActivity(),"Error",Toast.LENGTH_SHORT).show();
                }

                /*InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                */
                //saveState();
                //mRowId=null;
                //name.setText("");
                //encryptedtext.setText("");
                //keyvalue.setText("");
                //viewPager.setCurrentItem(1);
            }
        });
        decrypt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                try {

                    outstring1 = DecryptFunction(encryptedtext.getText().toString(),keyvalue.getText().toString());
                    encryptedtext.setText(outstring1);

                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);

                    Toast.makeText(getActivity(),"Decrypted",Toast.LENGTH_SHORT).show();


                }catch (Exception e){
                    Toast.makeText(getActivity(),"Error",Toast.LENGTH_SHORT).show();
                }

            }

        });
        return rootView;
    }
    private String EncryptFunction(String Data,String pass) throws Exception
    {
        SecretKeySpec key= generateKey(pass);
        Cipher c= Cipher.getInstance("AES");
        c.init(Cipher.ENCRYPT_MODE,key);
        byte[] encval=c.doFinal(Data.getBytes());
        String encryptedValue= Base64.encodeToString(encval, Base64.DEFAULT);
        return encryptedValue;


    }

    private String DecryptFunction(String outputString, String pass) throws Exception
    {
        SecretKeySpec key = generateKey(pass);
        Cipher c=Cipher.getInstance("AES");
        c.init(Cipher.DECRYPT_MODE,key);
        byte[] decodedValue=Base64.decode(outputString,Base64.DEFAULT);
        byte[] decValue=c.doFinal(decodedValue);
        String decryptedValue= new String(decValue);
        return decryptedValue;

    }

    private SecretKeySpec generateKey(String pass) throws Exception
    {
        final MessageDigest digest= MessageDigest.getInstance("SHA-256");
        byte[] bytes=pass.getBytes("UTF-8");
        digest.update(bytes,0,bytes.length);
        byte[] key=digest.digest();
        SecretKeySpec secretKeySpec=new SecretKeySpec(key,"AES");
        return secretKeySpec;
    }






    Button encrypt, decrypt, copy, share,save;
    EditText encryptedtext, keyvalue, name;
    //private ClipboardManager myClipboard;
    //private ClipData myClip;
    private ShareActionProvider mShareActionProvider;
    ToggleButton text_show, key_show;
    ViewPager viewPager;



   /* public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.txtencrypt_decrypt, container, false);
    }
    */

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //encrypt=(Button) getActivity().findViewById(R.id.encrypt);
        encryptedtext = (EditText) getActivity().findViewById(R.id.editText);
        keyvalue = (EditText) getActivity().findViewById(R.id.editText2);
        name = (EditText) getActivity().findViewById(R.id.txtname);

        // copy=(Button) getActivity().findViewById(R.id.copy);
        //myClipboard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
        share = (Button) getActivity().findViewById(R.id.share);
        text_show = (ToggleButton) getActivity().findViewById(R.id.text_show);
        key_show = (ToggleButton) getActivity().findViewById(R.id.key_show);


        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String get;
                get = encryptedtext.getText().toString();
                Intent shareintent = new Intent(Intent.ACTION_SEND);
                shareintent.putExtra(Intent.EXTRA_TEXT, get);
                shareintent.setDataAndType(Uri.parse(""), "text/plain");
                startActivity(Intent.createChooser(shareintent, "Share"));

            }
        });

      /*  copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text;
                text = editText.getText().toString();

              myClip = ClipData.newPlainText("text", text);
                myClipboard.setPrimaryClip(myClip);

                Toast.makeText(getActivity().getApplicationContext(), "Text Copied",
                        Toast.LENGTH_SHORT).show();
            }
        });
        */
        text_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (text_show.isChecked()) {
                    text_show.setBackgroundResource(R.drawable.eye);
                    encryptedtext.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    encryptedtext.setSelection(encryptedtext.getText().length());
                } else {
                    text_show.setBackgroundResource(R.drawable.eye_off);
                    encryptedtext.setInputType(InputType.TYPE_CLASS_TEXT);
                    encryptedtext.setSelection(encryptedtext.getText().length());
                }
            }
        });

       /* key_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (key_show.isChecked()) {
                    key_show.setBackgroundResource(R.drawable.eye);
                    encryptedtext.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                    encryptedtext.setSelection(keyvalue.getText().length());
                } else {
                    key_show.setBackgroundResource(R.drawable.eye_off);
                    keyvalue.setInputType(InputType.TYPE_CLASS_NUMBER);
                    keyvalue.setSelection(keyvalue.getText().length());
                }
            }
        });
    */}


    /*encrypt.setOnClickListener(new View.OnClickListener()
    {

        @Override
        public void onClick(View v)
        {

            viewPager.setCurrentItem(1);

        }
    });
    */
    public void saveState()
    {
        if(encryptedtext!=null && encryptedtext.toString().trim().length()==0)
        {
            return;
        }
        if(keyvalue!=null && keyvalue.toString().trim().length()==0)
        {
            return;
        }
        if(name!=null && name.toString().trim().length()==0)
        {
            return;
        }
        String encrypted_text = encryptedtext.getText().toString();
        String key_value = keyvalue.getText().toString();
        String padname = name.getText().toString();



        if (mRowId== null)
        {
            long id = NBHelper.insertNotes(padname,encrypted_text,key_value);
            if (id > 0)
            {
                mRowId = id;
            }
        }
        else
        {
            NBHelper.updateNotes(mRowId,padname,encrypted_text,key_value);
        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        if (NBHelper != null)
        {
            NBHelper.close();
        }
    }
}


