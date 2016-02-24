package daw2.hlc.domi.mislugares;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edicion_lugar);
        Button salir =(Button) findViewById(R.id.button4);
        salir.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.button3:
                Toast.makeText(MainActivity.this, "Se ha pulsado acerca de...", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button2:
                Toast.makeText(MainActivity.this, "Se ha pulsado preferencias", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button4:
                Toast.makeText(MainActivity.this, "Se ha pulsado preferencias", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
    public void lanzarPreferencias(View view){
        Intent i = new Intent(this, Preferencias.class);
        startActivity(i);
    }
}
