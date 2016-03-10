package com.example.domingo.androidlibros;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
/*Para utilizar nuestro action bar la cual esta obsoleta hemos extendido de appcompat activity para poder utilizarla*/
public class ActionBarAdapter extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.libro);
    }
}
