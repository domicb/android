package daw2.hlc.domi.mislugares;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;import net.sgoliver.android.preferences2.MainActivity;import java.lang.Long;import java.lang.Override;

public class VistaLugar extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_lugar);
    }
    public void lanzarVistaLugar(View view){ final EditText entrada = new EditText(this);
        entrada.setText("0"); new AlertDialog.Builder(this) .setTitle("Selección de lugar") .setMessage("indica su id:") .setView(entrada) .setPositiveButton("Ok", new DialogInterface.OnClickListener() { public void onClick(DialogInterface dialog, int whichButton) { long id = Long.parseLong(entrada.getText().toString()); Intent i = new Intent(MainActivity.this, VistaLugar.class); i.putExtra("id", id); startActivity(i); }})
            .setNegativeButton("Cancelar", null) .show(); }

}
