package org.hackaton.kotikota.endpoint.rest.security.auth.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.hackaton.kotikota.Generated;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Generated
public class FirebaseConf {

  @Value("${firebase.data.config}")
  private String FIREBASE_DATA_CONF;

  private InputStream convertConf() {
    byte[] bytes = FIREBASE_DATA_CONF.getBytes();
    return new ByteArrayInputStream(bytes);
  }

  @Bean
  public FirebaseApp firebaseApp() throws IOException {
    FirebaseOptions options =
        FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(convertConf()))
            .build();
    return FirebaseApp.initializeApp(options);
  }

  @Bean
  public FirebaseAuth firebaseAuth() throws IOException {
    return FirebaseAuth.getInstance(firebaseApp());
  }
}
