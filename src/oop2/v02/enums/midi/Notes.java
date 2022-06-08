package oop2.v02.enums.midi;

/**
 * Nekoliko nota MIDI specifikacije.
 * 
 * @author Dejan Mitrovic
 * @author Ognjen Kulic
 */
public enum Notes {

	C(0, "C"),
	CS(1, "C#/Db"),
	D (2, "D"),
	DS(3, "D#/Eb"),
	E (4, "E"),
	F (5, "F"),
	FS(6, "F#/Gb"),
	G (7, "G"),
	GS(8, "G#/Ab"),
	A (9, "A"), 
	AS (10, "A#/Bb"),
	B (11, "B"),
	P (-1, "Pause");

	// Po nepisanom pravilu, sva polja enum-a bi trebalo da budu final
	private final int num; // ID note u MIDI specifikaciji
	private final String desc; // user-friendly opis

	// Konstruktor moze biti samo private
	private Notes(int num, String desc) {
		this.num = num;
		this.desc = desc;
	}

	public final int getNoteNum() {
		return num;
	}

	@Override
	public String toString() {
		return desc;
	}
}
