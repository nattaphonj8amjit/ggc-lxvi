package com.ball.lxvi.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translate.TranslateOption;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

@Service
public class TranslateService {

	public String translate(String txt) throws Exception {
		Translate translate =TranslateOptions.newBuilder()
        .setCredentials(ServiceAccountCredentials.fromStream(new ClassPathResource("static/LXVI.json").getInputStream()))
        .build().getService();
		

		
		Translation translation = translate.translate(txt, TranslateOption.sourceLanguage("th"),
				TranslateOption.targetLanguage("en"), TranslateOption.model("nmt"));
		return translation.getTranslatedText();
	}

}
