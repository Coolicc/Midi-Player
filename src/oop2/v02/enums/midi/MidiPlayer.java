package oop2.v02.enums.midi;

import static org.svetovid.Svetovid.in;
import static org.svetovid.Svetovid.out;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;

/**
 * Program koji od korisnika ucitava naziv instrumenta i niz nota, a zatim svira zadate note
 * koristeci zadati instrument.
 * 
 * @author Dejan Mitrovic
 * @author Ivan Pribela
 * @author Ognjen Kulic
 */
public class MidiPlayer {

	public static void main(String[] args) throws Exception {

		// Inicijalizacija MIDI sistema
		Synthesizer synth = MidiSystem.getSynthesizer();
		synth.open();
		MidiChannel channel = synth.getChannels()[0];

		out.print("Input file with this format: Instrument, BPM and notes, in form of 'instrument BPM note1,length,octave note2,length,octave...': ");
		String f = in.readLine();
		String[] input = in(f).readLine().split(" ");

		if (input.length < 2) {
			out.println("You need to specify an instrument and at last one note");
			return;
		}

		// Postavi ucitani instrument
		try {
			Instruments i = Instruments.valueOf(input[0].toUpperCase());
			channel.programChange(i.getPatch());
			out.println("Playing notes using instrument " + i);
		} catch (IllegalArgumentException e) {
			out.println("No such instrument - " + input[0]);
			return;
		}
		
		final int BPMMOD = (int)(60.0/Double.parseDouble(input[1]) * 1000.0);

		// Sviraj note
		Notes previous = null;
		int prevnum = 0;
		for (int i = 2; i < input.length; i++) {
			try {
				if (input[i].charAt(0)=='|') continue;
				String t[] = input[i].split(",");
				Notes n = Notes.valueOf(t[0].toUpperCase());

				// Ako smo vec odsvirali neku notu, moramo je "iskljuciti"
				if (previous != null) {
					channel.noteOff(prevnum);
				}
				
				String app = n.toString();
				
				if (n != Notes.P) {
					prevnum = n.getNoteNum()+12*Integer.parseInt(t[2]);
					channel.noteOn(prevnum, 100); // 100 - volume [0..127]
					previous = n;
					app+=t[2];
				}
				
				out.println("Playing note " + app);
				
				int sl=BPMMOD;
				switch (t[1]) {
				    case "2*1":
				    	sl=8 * BPMMOD;
				    	break;
					case "1": 
						sl = 4*BPMMOD;
						break;
					case "3/4":
						sl = 3*BPMMOD;
						break;
					case "2":
						sl = 2*BPMMOD;
						break;
					case "8":
						sl = BPMMOD/2;
						break;
					case "16":
						sl = BPMMOD/4;
						break;
					case "32":
						sl = BPMMOD/8;
						break;
					case "64":
						sl = BPMMOD/16;
						break;
				}

				// Sacekaj 1000 milisekundi pre nego sto odsviras sledecu notu
				Thread.sleep(sl);
			} catch (IllegalArgumentException e) {
				out.println("Unrecognized note - " + input[i]);
				return;
			}
		}
	}
}
