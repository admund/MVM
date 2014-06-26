package pl.admund.MVM_Client.Volleyballer;

import pl.admund.MVM_Client.R;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class VolleyballerInfoActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.volleyballer_info_activity);
		VolleyballerClnt tmpVolleyballer = (VolleyballerClnt)getIntent().getSerializableExtra("VOLLEYBALLER");
		
		if(tmpVolleyballer != null)
		{
			fillTextView(R.id.volleyballer_info_text_name, tmpVolleyballer.getLongName());
			fillTextView(R.id.volleyballer_info_text_height, "Wzrost: " + tmpVolleyballer.getHeigh());
			fillTextView(R.id.volleyballer_info_text_weight, "Waga: " + tmpVolleyballer.getWeight());
			fillTextView(R.id.volleyballer_info_text_age, "Wiek: 24");// + tmpVolleyballer.getAge());//TODO
			fillTextView(R.id.volleyballer_info_text_nationality, "Kraj: " + tmpVolleyballer.getNationality());
			fillTextView(R.id.volleyballer_info_text_trim, "Kondycja: " + tmpVolleyballer.getTrim());
			fillTextView(R.id.volleyballer_info_text_position, "Pozycja: " + tmpVolleyballer.getPossition());
			fillTextView(R.id.volleyballer_info_text_morale, "Morale: " + tmpVolleyballer.getMorale());
			fillTextView(R.id.volleyballer_info_text_salary, "Placa: " + tmpVolleyballer.getSalary());
			fillTextView(R.id.volleyballer_info_text_contract, "Dlugosc kontraktu: " + tmpVolleyballer.getContractLength());
			
			fillTextView(R.id.volleyballer_info_text_attack, "Atrybuty techniczne\nAtak:\t" + tmpVolleyballer.getAtack());
			fillTextView(R.id.volleyballer_info_text_block, "Blok:\t" + tmpVolleyballer.getBlock());
			fillTextView(R.id.volleyballer_info_text_reception, "Przyjecie:\t" + tmpVolleyballer.getReception());
			fillTextView(R.id.volleyballer_info_text_rozegranie, "Rozegranie:\t" + tmpVolleyballer.getRozegranie());
			fillTextView(R.id.volleyballer_info_text_serwis, "Serwis:\t" + tmpVolleyballer.getSerwis());
			fillTextView(R.id.volleyballer_info_text_technique, "Technika:\t" + tmpVolleyballer.getTechnique());
			setRating(R.id.volleyballer_info_rating_tech, tmpVolleyballer.getTalent());
			
			fillTextView(R.id.volleyballer_info_text_temper, "Atrybuty psychiczne\nOpanowanie:\t" + tmpVolleyballer.getTemper());
			fillTextView(R.id.volleyballer_info_text_ustawianie, "Ustawianie:\t" + tmpVolleyballer.getUstawianie());
			fillTextView(R.id.volleyballer_info_text_intuition, "Intuicja:\t" + tmpVolleyballer.getIntuition());
			fillTextView(R.id.volleyballer_info_text_creativity, "Kreatywnosc:\t" + tmpVolleyballer.getCreativity());
			fillTextView(R.id.volleyballer_info_text_walecznosc, "Walecznosc:\t" + tmpVolleyballer.getWalecznosc());
			fillTextView(R.id.volleyballer_info_text_charisma, "Charyzma:\t" + tmpVolleyballer.getCharisma());
			setRating(R.id.volleyballer_info_rating_psychical, tmpVolleyballer.getPsychoforce());
			
			fillTextView(R.id.volleyballer_info_text_strenght, "Atrybuty fizyczne\nSila:\t" + tmpVolleyballer.getStrenght());
			fillTextView(R.id.volleyballer_info_text_jumping, "Skocznosc:\t" + tmpVolleyballer.getJumping());
			fillTextView(R.id.volleyballer_info_text_reflex, "Refleks:\t" + tmpVolleyballer.getReflex());
			fillTextView(R.id.volleyballer_info_text_quickness, "Szybkosc:\t" + tmpVolleyballer.getQuickness());
			fillTextView(R.id.volleyballer_info_text_agility, "Zrecznosc:\t" + tmpVolleyballer.getAgility());
			fillTextView(R.id.volleyballer_info_text_stamina, "Wytrzymalosc:\t" + tmpVolleyballer.getStamina());
			setRating(R.id.volleyballer_info_rating_physical, tmpVolleyballer.getPracowitosc());
		}
		else
		{
			Log.e("DUPA", "WIELKA OBSRANA DUPA");
		}
	}
	
	private void fillTextView(int _textViewRes, String _string)
	{
		TextView tmpTextView = (TextView)findViewById(_textViewRes);
		tmpTextView.setText(_string);
	}
	
	private void setRating(int _ratingImageRes, int _rating)
	{
		int result = 1 + _rating/20;
		ImageView tmpImageView = (ImageView)findViewById(_ratingImageRes);
		if(_rating == -1)
			tmpImageView.setImageResource(R.drawable.rating_0);
		else if(result == 1)
			tmpImageView.setImageResource(R.drawable.rating_1);
		else if(result == 2)
			tmpImageView.setImageResource(R.drawable.rating_2);
		else if(result == 3)
			tmpImageView.setImageResource(R.drawable.rating_3);
		else if(result == 4)
			tmpImageView.setImageResource(R.drawable.rating_4);
		else if(result == 5)
			tmpImageView.setImageResource(R.drawable.rating_5);
		
		tmpImageView.invalidate();
	}
}