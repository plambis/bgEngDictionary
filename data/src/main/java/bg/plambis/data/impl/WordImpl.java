package bg.plambis.data.impl;

import java.io.Serializable;

import bg.plambis.data.api.Word;

/**
 * Base class for every word in the dictionary
 * 
 * @author pivanov
 */
public class WordImpl implements Word, Serializable {
	private static final long serialVersionUID = 1228511354723440744L;

	private int id = -1;
	private String word;
	private String translation;

	public WordImpl(int id, String word, String translation) {
		this.id = id;
		this.word = word;
		this.translation = translation;
	}

	public WordImpl(String word) {
		this.word = word;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getWord() {
		return word;
	}

	@Override
	public String getTranslation() {
		return translation;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((translation == null) ? 0 : translation.hashCode());
		result = prime * result + ((word == null) ? 0 : word.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		WordImpl other = (WordImpl) obj;
		if (id != other.id) {
			return false;
		}
		if (translation == null) {
			if (other.translation != null) {
				return false;
			}
		} else if (!translation.equals(other.translation)) {
			return false;
		}
		if (word == null) {
			if (other.word != null) {
				return false;
			}
		} else if (!word.equals(other.word)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "WordImpl [id=" + id + ", word=" + word + ", translation=" + translation + "]";
	}
}
