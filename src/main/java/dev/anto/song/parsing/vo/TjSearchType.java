package dev.anto.song.parsing.vo;

public enum TjSearchType {
	ALL {
		public int getNumber() {
			return 0;
		}
	},
	TITLE {
		public int getNumber() {
			return 1;
		}
	},
	SINGER {
		public int getNumber() {
			return 2;
		}
	},
	LYRICIST {
		public int getNumber() {
			return 4;
		}
	},
	COMPOSER {
		public int getNumber() {
			return 8;
		}
	},
	NUMBER {
		public int getNumber() {
			return 16;
		}
	};

	public abstract int getNumber();

	public String getUrl() {
		return this.name().toLowerCase();
	}
}
