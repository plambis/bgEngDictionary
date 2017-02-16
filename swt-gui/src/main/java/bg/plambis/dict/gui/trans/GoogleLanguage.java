package bg.plambis.dict.gui.trans;

/**
 * The Enum GoogleLanguage.
 *
 * @author plambis
 */
public enum GoogleLanguage {

	/** The auto detect. */
	AUTO_DETECT("", "Auto detect"),

	/** The afrikaans. */
	AFRIKAANS("af", "Afrikaans"),

	/** The albanian. */
	ALBANIAN("sq", "Albanian"),

	/** The amharic. */
	AMHARIC("am", "Amharic"),

	/** The arabic. */
	ARABIC("ar", "Arabic"),

	/** The armenian. */
	ARMENIAN("hy", "Armenian"),

	/** The azerbaijani. */
	AZERBAIJANI("az", "Azerbaijani"),

	/** The basque. */
	BASQUE("eu", "Basque"),

	/** The belarusian. */
	BELARUSIAN("be", "Byelorussian"),

	/** The bengali. */
	BENGALI("bn", "Bengali"),

	/** The bihari. */
	BIHARI("bh", "Bihari"),

	/** The bulgarian. */
	BULGARIAN("bg", "Bulgarian"),

	/** The burmese. */
	BURMESE("my", "Burmese"),

	/** The catalan. */
	CATALAN("ca", "Catalan"),

	/** The cherokee. */
	CHEROKEE("chr", "Cherokee"),

	/** The chinese. */
	CHINESE("zh", "Chinese"),

	/** The chinese simplified. */
	CHINESE_SIMPLIFIED("zh-CN", "Chinese Simp"),

	/** The chinese traditional. */
	CHINESE_TRADITIONAL("zh-TW", "Chinese Trad"),

	/** The croatian. */
	CROATIAN("hr", "Croatian"),

	/** The czech. */
	CZECH("cs", "Czech"),

	/** The danish. */
	DANISH("da", "Danish"),

	/** The dhivehi. */
	DHIVEHI("dv", "Dhivehi"),

	/** The dutch. */
	DUTCH("nl", "Dutch"),

	/** The english. */
	ENGLISH("en", "English"),

	/** The esperanto. */
	ESPERANTO("eo", "Esperanto"),

	/** The estonian. */
	ESTONIAN("et", "Estonian"),

	/** The filipino. */
	FILIPINO("tl", "Tagalog"),

	/** The finnish. */
	FINNISH("fi", "Finnish"),

	/** The french. */
	FRENCH("fr", "French"),

	/** The galacian. */
	GALACIAN("gl", "Galician"),

	/** The georgian. */
	GEORGIAN("ka", "Georgian"),

	/** The german. */
	GERMAN("de", "German"),

	/** The greek. */
	GREEK("el", "Greek"),

	/** The guarani. */
	GUARANI("gn", "Guarani"),

	/** The gujarati. */
	GUJARATI("gu", "Gujarati"),

	/** The hebrew. */
	HEBREW("iw", "Hebrew"),

	/** The hindi. */
	HINDI("hi", "Hindi"),

	/** The hungarian. */
	HUNGARIAN("hu", "Hungarian"),

	/** The icelandic. */
	ICELANDIC("is", "Icelandic"),

	/** The indonesian. */
	INDONESIAN("id", "Indonesian"),

	/** The inuktitut. */
	INUKTITUT("iu", "Inuktitut"),

	/** The irish. */
	IRISH("ga", "Irish"),

	/** The italian. */
	ITALIAN("it", "Italian"),

	/** The japanese. */
	JAPANESE("ja", "Japanese"),

	/** The kannada. */
	KANNADA("kn", "Kannada"),

	/** The kazakh. */
	KAZAKH("kk", "Kazakh"),

	/** The khmer. */
	KHMER("km", "Cambodian"),

	/** The korean. */
	KOREAN("ko", "Korean"),

	/** The kurdish. */
	KURDISH("ku", "Kurdish"),

	/** The kyrgyz. */
	KYRGYZ("ky", "Kirghiz"),

	/** The laothian. */
	LAOTHIAN("lo", "Laothian"),

	/** The latvian. */
	LATVIAN("lv", "Latvian"),

	/** The lithuanian. */
	LITHUANIAN("lt", "Lithuanian"),

	/** The macedonian. */
	MACEDONIAN("mk", "Macedonian"),

	/** The malay. */
	MALAY("ms", "Malay"),

	/** The malayalam. */
	MALAYALAM("ml", "Malayalam"),

	/** The maltese. */
	MALTESE("mt", "Maltese"),

	/** The marathi. */
	MARATHI("mr", "Marathi"),

	/** The mongolian. */
	MONGOLIAN("mn", "Mongolian"),

	/** The nepali. */
	NEPALI("ne", "Nepali"),

	/** The norwegian. */
	NORWEGIAN("no", "Norwegian"),

	/** The oriya. */
	ORIYA("or", "Oriya"),

	/** The pashto. */
	PASHTO("ps", "Pashto"),

	/** The persian. */
	PERSIAN("fa", "Persian"),

	/** The polish. */
	POLISH("pl", "Polish"),

	/** The portuguese. */
	PORTUGUESE("pt", "Portuguese"),

	/** The punjabi. */
	PUNJABI("pa", "Punjabi"),

	/** The romanian. */
	ROMANIAN("ro", "Romanian"),

	/** The russian. */
	RUSSIAN("ru", "Russian"),

	/** The sanskrit. */
	SANSKRIT("sa", "Sanskrit"),

	/** The serbian. */
	SERBIAN("sr", "Serbian"),

	/** The sindhi. */
	SINDHI("sd", "Sindhi"),

	/** The sinhalese. */
	SINHALESE("si", "Singhalese"),

	/** The slovak. */
	SLOVAK("sk", "Slovak"),

	/** The slovenian. */
	SLOVENIAN("sl", "Slovenian"),

	/** The spanish. */
	SPANISH("es", "Spanish"),

	/** The swahili. */
	SWAHILI("sw", "Swahili"),

	/** The swedish. */
	SWEDISH("sv", "Swedish"),

	/** The tajik. */
	TAJIK("tg", "Tajik"),

	/** The tamil. */
	TAMIL("ta", "Tamil"),

	/** The tagalog. */
	TAGALOG("tl", "Tagalog"),

	/** The telugu. */
	TELUGU("te", "Tegulu"),

	/** The thai. */
	THAI("th", "Thai"),

	/** The tibetan. */
	TIBETAN("bo", "Tibetan"),

	/** The turkish. */
	TURKISH("tr", "Turkish"),

	/** The ukranian. */
	UKRANIAN("uk", "Ukrainian"),

	/** The urdu. */
	URDU("ur", "Urdu"),

	/** The uzbek. */
	UZBEK("uz", "Uzbek"),

	/** The uighur. */
	UIGHUR("ug", "Uighur"),

	/** The vietnamese. */
	VIETNAMESE("vi", "Vietnamese"),

	/** The welsh. */
	WELSH("cy", "Welsh"),

	/** The yiddish. */
	YIDDISH("yi", "Yiddish");

	/** The lang. */
	private String lang;

	/** The alias. */
	private String alias;

	/**
	 * Instantiates a new google language.
	 *
	 * @param language the language
	 * @param alias the alias
	 */
	private GoogleLanguage(String language, String alias) {
		this.lang = language;
		this.alias = alias;
	}

	/**
	 * Validate.
	 *
	 * @param language the language
	 * @return the google language
	 */
	public static GoogleLanguage validate(String language) {
		for (GoogleLanguage item : values()) {
			if (item.lang.equalsIgnoreCase(language)) {
				return item;
			}
		}
		return GoogleLanguage.AUTO_DETECT;
	}

	/**
	 * Checks a given language is available to use with Google Translate.
	 * 
	 * @param language
	 *            The language code to check for.
	 * @return true if this language is available to use with Google Translate,
	 *         false otherwise.
	 */
	public static boolean isValidLanguage(String language) {
		return (validate(language) != null);
	}

	/**
	 * Checks a given alias is available to use with Google Translate.
	 * 
	 * @param alias
	 *            The alias to check for.
	 * @return GoogleLanguage if this language is available to use with Google
	 *         Translate, null otherwise.
	 */
	public static GoogleLanguage getByAlias(String alias) {
		for (GoogleLanguage val : values()) {
			if (val.getAlias().equals(alias)) {
				return val;
			}
		}
		return GoogleLanguage.AUTO_DETECT;
	}

	/**
	 * Return the global name of Language.
	 *
	 * @return the global name of Language
	 */
	@Override
	public String toString() {
		return lang;
	}

	/**
	 * Gets the alias.
	 *
	 * @return the alias
	 */
	public String getAlias() {
		return alias.equals("") ? name().toUpperCase().charAt(0) + name().toLowerCase().substring(1) : alias;
	}

	/**
	 * Gets the lang.
	 *
	 * @return the lang
	 */
	public String getLang() {
		return lang;
	}


}