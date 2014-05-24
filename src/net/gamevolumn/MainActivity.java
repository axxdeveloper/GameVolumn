package net.gamevolumn;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		final AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		final int streamMaxVolumn = audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		int currentVolumn = audio.getStreamVolume(AudioManager.STREAM_MUSIC);
		final TextView descView = (TextView) findViewById(R.id.description);
		final String defaultDesc = getResources().getString(R.string.desc_gamevolumn);
		SeekBar seekbar = (SeekBar) findViewById(R.id.selectVolumn);
		seekbar.setMax(streamMaxVolumn);
		seekbar.setProgress(currentVolumn);
		seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				descView.setText(defaultDesc + " : " + progress);
				audio.setStreamVolume(AudioManager.STREAM_MUSIC, progress, AudioManager.FLAG_ALLOW_RINGER_MODES);
				int volumn = (int)(((float)progress/(float)streamMaxVolumn)*100);
				Log.i(getClass().getName(), "progress:" + progress + ",streamMaxVolumn:" + streamMaxVolumn + ",select volumn percentage:"+ volumn);
				ToneGenerator tg = new ToneGenerator(AudioManager.STREAM_MUSIC, volumn);
			    tg.startTone(ToneGenerator.TONE_PROP_BEEP);
			    tg.release();
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		int currentVolumn = audio.getStreamVolume(AudioManager.STREAM_MUSIC);
		SeekBar seekbar = (SeekBar) findViewById(R.id.selectVolumn);
		seekbar.setProgress(currentVolumn);
		final TextView descView = (TextView) findViewById(R.id.description);
		final String defaultDesc = getResources().getString(R.string.desc_gamevolumn);
		descView.setText(defaultDesc + " : " + currentVolumn);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		boolean result = super.onTouchEvent(event);
		finish();
		return result;
	}
	
}
