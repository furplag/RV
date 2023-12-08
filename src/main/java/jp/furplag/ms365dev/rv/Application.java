package jp.furplag.ms365dev.rv;



import jp.furplag.sandbox.reflect.SavageReflection;
import jp.furplag.sandbox.trebuchet.Trebuchet;

public final class Application {

  public static void main(String[] args) {
/*
    final String userProperties = Trebuchet.Functions.orElse(GraphClient.origin, (graph) -> {
      return graph.getServiceClient().users().buildRequest().top(1).get().getCurrentPage()
        .stream().map(SavageReflection::read).map(Map::keySet).flatMap(Set::stream).distinct().filter(StringUtils::isNotBlank).collect(Collectors.joining(","));
    }, (t, ex) -> {
      ex.printStackTrace();

      return null;
    });

    System.out.println(userProperties);
*/

    Trebuchet.Consumers.orElse(GraphClient.origin, (graph) -> {
      graph.getServiceClient().users().buildRequest()
      .orderBy("displayName")
      .get()
      .getCurrentPage().stream().map(SavageReflection::read).forEach(System.out::println)
      ;
    }, (t, ex) -> ex.printStackTrace());
  }
}
