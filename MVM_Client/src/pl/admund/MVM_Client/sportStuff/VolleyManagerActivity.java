package pl.admund.MVM_Client.sportStuff;

import pl.admund.MVM_Client.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class VolleyManagerActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        //setContentView(R.layout.team_tactic);
        loadLayout(R.layout.tac_blo);
        
        
    }
    
    void loadLayout(int layoutID)
    {
    	if(layoutID == R.layout.tac_rec)
    	{
    		setContentView(R.layout.tac_rec);
            //recive
            fillSpinner(R.id.spinner_rec_1_qual, R.array.przyjecie_jakosc);
            fillSpinner(R.id.spinner_rec_2_qual, R.array.przyjecie_jakosc);
            fillSpinner(R.id.spinner_lib_qual, R.array.przyjecie_jakosc);
            fillSpinner(R.id.spinner_rec_1_qual, R.array.przyjecie_obszar);
            fillSpinner(R.id.spinner_rec_2_qual, R.array.przyjecie_obszar);
            fillSpinner(R.id.spinner_lib_qual, R.array.przyjecie_obszar);
    	}
    	else if(layoutID == R.layout.tac_atack)
    	{
    		setContentView(R.layout.tac_atack);
            //atack
            fillSpinner(R.id.spinner_at_str, R.array.atak_sila);
            fillSpinner(R.id.spinner_rec_1_str, R.array.atak_sila);
            fillSpinner(R.id.spinner_rec_2_str, R.array.atak_sila);
            fillSpinner(R.id.spinner_blo_1_str, R.array.atak_sila);
            fillSpinner(R.id.spinner_blo_2_str, R.array.atak_sila);
            
            fillSpinner(R.id.spinner_at_ori, R.array.atak_kierunek);
            fillSpinner(R.id.spinner_rec_1_ori, R.array.atak_kierunek);
            fillSpinner(R.id.spinner_rec_2_ori, R.array.atak_kierunek);
            fillSpinner(R.id.spinner_blo_1_ori, R.array.atak_kierunek);
            fillSpinner(R.id.spinner_blo_2_ori, R.array.atak_kierunek);
    	}
    	else if(layoutID == R.layout.tac_serw)
    	{
    		setContentView(R.layout.tac_serw);
            //serw
            fillSpinner(R.id.spinner_at_str_serw, R.array.serwis_sila);
            fillSpinner(R.id.spinner_rec_1_str_serw, R.array.serwis_sila);
            fillSpinner(R.id.spinner_rec_2_str_serw, R.array.serwis_sila);
            fillSpinner(R.id.spinner_blo_1_str_serw, R.array.serwis_sila);
            fillSpinner(R.id.spinner_blo_2_str_serw, R.array.serwis_sila);
            fillSpinner(R.id.spinner_roz_str_serw, R.array.serwis_sila);
            
            fillSpinner(R.id.spinner_at_ori_serw, R.array.serwis_kierunek);
            fillSpinner(R.id.spinner_rec_1_ori_serw, R.array.serwis_kierunek);
            fillSpinner(R.id.spinner_rec_2_ori_serw, R.array.serwis_kierunek);
            fillSpinner(R.id.spinner_blo_1_ori_serw, R.array.serwis_kierunek);
            fillSpinner(R.id.spinner_blo_2_ori_serw, R.array.serwis_kierunek);
            fillSpinner(R.id.spinner_roz_ori_serw, R.array.serwis_kierunek);
    	}
    	else if(layoutID == R.layout.tac_blo)
    	{
    		setContentView(R.layout.tac_blo);
            //block
            fillSpinner(R.id.spinner_at_3, R.array.blok_trzech);
            fillSpinner(R.id.spinner_rec_1_3, R.array.blok_trzech);
            fillSpinner(R.id.spinner_rec_2_3, R.array.blok_trzech);
            fillSpinner(R.id.spinner_blo_1_3, R.array.blok_trzech);
            fillSpinner(R.id.spinner_blo_2_3, R.array.blok_trzech);
            fillSpinner(R.id.spinner_roz_3, R.array.blok_trzech);
            
            fillSpinner(R.id.spinner_at_bop, R.array.blok_opcja);
            fillSpinner(R.id.spinner_rec_1_bop, R.array.blok_opcja);
            fillSpinner(R.id.spinner_rec_2_bop, R.array.blok_opcja);
            fillSpinner(R.id.spinner_blo_1_bop, R.array.blok_opcja);
            fillSpinner(R.id.spinner_blo_2_bop, R.array.blok_opcja);
            fillSpinner(R.id.spinner_roz_bop, R.array.blok_opcja);
    	}
    }
    
    void fillSpinner(int spinerID, int arrayID)
    {
        Spinner s1 = (Spinner) findViewById(spinerID);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, arrayID, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s1.setAdapter(adapter);
    }
}