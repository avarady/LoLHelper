package com.team4d.lolhelper;

import java.util.Map;

import com.team4d.lolhelper.api.APIData;
import com.team4d.lolhelper.api.dto.staticdata.champion.Champion;
import com.team4d.lolhelper.api.dto.staticdata.champion.ChampionSpell;
import com.team4d.lolhelper.api.dto.staticdata.item.Item;
import com.team4d.lolhelper.api.dto.staticdata.summonerspell.SummonerSpell;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Popup {
	static View layout;
	
	//Static class
	private Popup(){
	}
	
	/*
	 * ********************************
	 * Summoner Spells
	 * ********************************
	 */
	public static View popupSummonerSpell(Activity activity, String name){
		LinearLayout view = (LinearLayout) activity.findViewById(R.id.summonerspellpopup);
		LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layout = inflater.inflate(R.layout.fragment_summoner_spell_popup, view);
		ImageView icon = (ImageView) layout.findViewById(R.id.icon);
		int resID = activity.getResources().getIdentifier(name.replaceAll("[^a-zA-Z]+", "").toLowerCase(),
				"drawable", "com.team4d.lolhelper");
		icon.setImageResource(resID);	
		
		new grabSpell().execute(name);
		return layout;
	}
	
	private static class grabSpell extends AsyncTask<String, Void, SummonerSpell>
	{
		@Override
		protected SummonerSpell doInBackground(String... spell_name)
		{
			SummonerSpell c = APIData.getSummonerSpellByName(spell_name[0]);
			// Note: This return value is passed as a parameter to onPostExecute
			return c;
		}

		@Override
		protected void onPostExecute(SummonerSpell spell)
		{
			TextView nameText = (TextView) layout.findViewById(R.id.nameDis);
			TextView effectText = (TextView) layout.findViewById(R.id.effectDis);
			TextView cooldownText = (TextView) layout.findViewById(R.id.cooldownDis);
			TextView rangeText = (TextView) layout.findViewById(R.id.rangeDis);
			TextView levelText = (TextView) layout.findViewById(R.id.levelDis); 
			
			// Setting Text for TextViews
			nameText.setText(spell.getName());
			effectText.setText("Effect: \n" + spell.getDescription());
			cooldownText.setText("Cooldown: \n" + spell.getCooldownBurn());
			rangeText.setText("Range: \n" + spell.getRangeBurn());
			levelText.setText("Level: \n" + spell.getSummonerLevel());
		}
	}
	
	/*
	 * ********************************
	 * Items
	 * ********************************
	 */
	public static View popupItem(Activity activity, String name){
		LinearLayout view = (LinearLayout) activity.findViewById(R.id.itempopup);
		LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layout = inflater.inflate(R.layout.fragment_item_popup, view);
		ImageView icon = (ImageView) layout.findViewById(R.id.icon);
		int resID = activity.getResources().getIdentifier(name.replaceAll("[^a-zA-Z]+", "").toLowerCase(),
				"drawable", "com.team4d.lolhelper");
		icon.setImageResource(resID);	
		
		new grabItem().execute(name);
		return layout;
	}
	
	private static class grabItem extends AsyncTask<String, Void, Item>
	{
		@Override
		protected Item doInBackground(String... name)
		{
			Item c = APIData.getItemByName(name[0]);
			// Note: This return value is passed as a parameter to onPostExecute
			return c;
		}

		@Override
		protected void onPostExecute(Item item)
		{
			TextView nameText = (TextView) layout.findViewById(R.id.name);
			TextView mapsText = (TextView) layout.findViewById(R.id.maps);
			TextView descriptionText = (TextView) layout.findViewById(R.id.description);
			TextView totalgoldText = (TextView) layout.findViewById(R.id.totalgold); 
			TextView sellgoldText = (TextView) layout.findViewById(R.id.sellgold); 
			
			// Setting Text for TextViews
			String str = "";
			if(item.getMaps()!=null){
				Map<String, Boolean> maps = item.getMaps();
				//Maps: 1 (SR), 10 (TT), 8 (CS), 12 (HA)
				if(!maps.containsKey("1")){
					str = str + "Summoner's Rift, ";
				}
				if(!maps.containsKey("10")){
					str = str + "Twisted Treeline, ";
				}
				if(!maps.containsKey("8")){
					str = str + "Crystal Scar, ";
				}
				if(!maps.containsKey("12")){
					str = str + "Howling Abyss, ";
				}
				str = str.substring(0, str.length()-2);
			} else {
				str = "All";
			}
			//Get rid of last ", "
			nameText.setText(item.getName());
			mapsText.setText("Maps: \n" + str);
			descriptionText.setText("Description: \n" + APIData.parseOutHtml(item.getDescription()));
			totalgoldText.setText("Total Gold: " + item.getGold().getTotal());
			sellgoldText.setText("Sells For: " + item.getGold().getSell());
		}
	}
	
	/*
	 * ********************************
	 * Champion Spells
	 * ********************************
	 */
	public static View popupChampionSpell(Activity activity, ChampionSpell spell){
		LinearLayout view = (LinearLayout) activity.findViewById(R.id.championspellpopup);
		LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layout = inflater.inflate(R.layout.fragment_champion_spell_popup, view);
		ImageView icon = (ImageView) layout.findViewById(R.id.icon);
		int resID = activity.getResources().getIdentifier(spell.getName().replaceAll("[^a-zA-Z]+", "").toLowerCase(),
				"drawable", "com.team4d.lolhelper");
		icon.setImageResource(resID);	
		
		TextView nameText = (TextView) layout.findViewById(R.id.name);
		TextView descriptionText = (TextView) layout.findViewById(R.id.description);
		TextView tooltipText = (TextView) layout.findViewById(R.id.tooltip);
		TextView cooldownText = (TextView) layout.findViewById(R.id.cooldown);
		TextView resourceText = (TextView) layout.findViewById(R.id.resource);
		TextView rangeText = (TextView) layout.findViewById(R.id.range);
		
		// Setting Text for TextViews
		nameText.setText(spell.getName());
		descriptionText.setText("Description: \n" + spell.getDescription());
		tooltipText.setText(APIData.parse(spell));
		cooldownText.setText("Cooldown: " + spell.getCooldownBurn());
		resourceText.setText("Cost: " + APIData.parseResource(spell));
		rangeText.setText("Range: " + spell.getRangeBurn());
		
		return layout;
	}
}