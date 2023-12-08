package jp.furplag.ms365dev.rv;

import java.util.List;
import java.util.Properties;

import com.azure.core.credential.TokenRequestContext;
import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.requests.GraphServiceClient;

import jp.furplag.sandbox.trebuchet.Trebuchet;
import lombok.Getter;
import okhttp3.Request;

public interface GraphClient {

  static final String defaultScope = "https://graph.microsoft.com/.default";

  static final GraphClient origin = new GraphClient() {

    @Getter
    private final ClientSecretCredential credencial;

    @Getter
    private final GraphServiceClient<Request> serviceClient;
    {
      credencial = getClientSecretCredential(getProperties(".credencials/oAuth.properties"));
      serviceClient = serviceClient(credencial);
    }
  };

  private static Properties getProperties(final String path) {
    return new Properties() {{
      try {
        load(GraphClient.class.getResourceAsStream(path));
      } catch (Exception ex) {ex.printStackTrace();}
    }};
  }

  private static ClientSecretCredential getClientSecretCredential(final Properties properties) {
    return new ClientSecretCredentialBuilder()
      .clientId(properties.getProperty("app.clientId", "nope"))
      .tenantId(properties.getProperty("app.tenantId", "nope"))
      .clientSecret(properties.getProperty("app.clientSecret", "nope"))
      .build();
  }

  private static GraphServiceClient<Request> serviceClient(final ClientSecretCredential credencial) {
    return GraphServiceClient.builder().authenticationProvider(
      new TokenCredentialAuthProvider(List.of(defaultScope), credencial)
    ).buildClient();
  }

  ClientSecretCredential getCredencial();

  GraphServiceClient<Request> getServiceClient();

  default String getToken() {
    return Trebuchet.Functions.orNot(getCredencial(), (_credencial) -> _credencial.getToken(new TokenRequestContext() {{
      addScopes(defaultScope);
    }}).block().getToken());
  }
}
