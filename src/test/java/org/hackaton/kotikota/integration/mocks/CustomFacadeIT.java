package org.hackaton.kotikota.integration.mocks;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import org.hackaton.kotikota.conf.FacadeIT;
import org.hackaton.kotikota.endpoint.rest.security.auth.firebase.FirebaseAuthenticator;
import org.hackaton.kotikota.endpoint.rest.security.auth.firebase.FirebaseConf;
import org.springframework.boot.test.mock.mockito.MockBean;

public class CustomFacadeIT extends FacadeIT {
  @MockBean protected FirebaseConf firebaseConfMock;
  @MockBean protected FirebaseApp firebaseAppMock;
  @MockBean protected FirebaseAuth firebaseAuthMock;
  @MockBean protected FirebaseAuthenticator firebaseAuthenticator;
}
