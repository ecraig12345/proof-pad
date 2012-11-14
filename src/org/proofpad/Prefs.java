package org.proofpad;

import java.awt.Font;
import java.util.prefs.Preferences;

public class Prefs {
	public static class Codes {
		public static int ASK_EVERY_TIME = 0;
		public static int ALWAYS_SEND = 1;
		public static int NEVER_SEND = 2;
	}
	static Preferences javaPrefs = Preferences.userNodeForPackage(Main.class);
	static abstract class Pref<T> {
		protected String name;
		protected T def;
		Pref(String name, T def) {
			this.name = name;
			this.def = def;
		}
		public abstract T get();
		public abstract void set(T val);
	}
	static class BooleanPref extends Pref<Boolean> {
		BooleanPref(String name, Boolean def) {
			super(name, def);
		}
		@Override public Boolean get() {
			return javaPrefs.getBoolean(name, def);
		}
		@Override public void set(Boolean val) {
			javaPrefs.putBoolean(name, val);
		}
	}
	static class IntPref extends Pref<Integer> {
		IntPref(String name, Integer def) {
			super(name, def);
		}
		@Override public Integer get() {
			return javaPrefs.getInt(name, def);
		}
		@Override public void set(Integer val) {
			javaPrefs.putInt(name, val);
		}
	}
	public static final BooleanPref showErrors = new BooleanPref("showerrors", true);
	public static final BooleanPref showOutputOnError = new BooleanPref("showoutputonerror", true);
	public static final IntPref alwaysSend = new IntPref("alwaysSend", Codes.ASK_EVERY_TIME);
	public static final IntPref fontSize = new IntPref("fontsize", 12);
	private static Font getDefaultFont() {
		String name = Main.OSX ? "Monaco" : (Main.WIN ? "Consolas" : "monospaced");
		return new Font(name, Font.PLAIN, fontSize.get());
	}
	public static final Pref<Font> font = new Pref<Font>("fontfamily", getDefaultFont()) {
		Font font;
		@Override public Font get() {
			if (font == null || !javaPrefs.get(name, def.getFamily()).equals(font.getFamily()) ||
					fontSize.get() != font.getSize()) {
				font = new Font(javaPrefs.get(name, def.getFamily()), Font.PLAIN, fontSize.get());
			}
			return font;
		}

		@Override public void set(Font val) {
			font = val;
			javaPrefs.put(name, val.getFamily());
			fontSize.set(val.getSize());
		}
	};
	protected static final BooleanPref incSearch = new BooleanPref("incsearch", true);
	public static final BooleanPref showToolbar = new BooleanPref("toolbarvisible", true);
}
