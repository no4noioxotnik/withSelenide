package com.epam.app.stepDefinition;

import com.epam.app.Helpers.MD5EncoderUtility;
import com.epam.app.share.RClShare;
import com.mashape.unirest.http.HttpMethod;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.io.IOUtils;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;

import static com.epam.app.stepDefinition.Asserts.assertContainsOrEquals;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

/**
 * Created by Rustam_Ragimov on 2/13/2018.
 */
public class RestClExp {
    private RClShare r;

    public RestClExp(RClShare r) {
        this.r = r;
    }


    @Given("^REST client with url: (http|https)://(.+):(\\d+)/?(.*)$")
    public void restClientWithUrl(String protocol, String host, int port, String endpoint)
            throws MalformedURLException {
        if (host.isEmpty() || port == 0)
            fail("URL and/or PORT cannot be empty");
        r.protocol = protocol;
        r.endpoint = endpoint;
        r.host = host;
        r.port = port;
        this.setUrlPath(r.endpoint, "");
    }

    @Given("^REST client with method: (.*) and url: (http|https)://(.+):(\\d+)/?(.*)$")
    public void restClientWithMethodAndUrl(HttpMethod method, String protocol, String host, int port, String endpoint
    ) throws MalformedURLException {
        this.restClientWithUrl(protocol, host, port, endpoint);
        r.method = method;
    }

    @And("^set url path: (.+) and save it as url_(.+)$")
    public void setUrlPath(String endpoint, String urlName) throws MalformedURLException {
        endpoint = (!endpoint.isEmpty() ? endpoint.charAt(0) != '/' ? "/" + endpoint : endpoint : "/");
        r.url = r.url == null
                ? new URL(r.protocol, r.host, r.port, endpoint)
                : new URL(r.url.getProtocol(), r.url.getHost(), r.url.getPort(), endpoint);
        if (!urlName.isEmpty()) {
            r.savedUrls.put("url_" + urlName, r.url);
        }
    }

    @And("^set basic auth: (.+):(.+)$")
    public void setBasicAuth(String login, String password) {
        r.basic = new String[]{login, password};
    }

    @And("^set http method: (GET|HEAD|POST|PUT|DELETE|PATCH|OPTIONS)$")
    public void setHttpMethod(HttpMethod method) {
        r.method = method;
    }

    @And("^set header: (.+): (.+)$")
    public void setHeader(String key, String value) {
        if (r.headers == null)
            r.headers = new HashMap<>();
        r.headers.put(key, value);
    }

    @And("^set body as text: '(.+)'$")
    public void set_body_as_plain_text(String text) {
        r.body = text.getBytes(Charset.forName("UTF-8"));
    }

    @And("^set body as file: /test_data/(.+)")
    public void setBodyAsFile(String name) throws IOException {
        r.body = MD5EncoderUtility.getFileBytesFromTestData(name);
    }

    @And("^save MD5 hashsum of the request body")
    public void saveMd5HashSumOfTheFile() {
        r.md5HashSumFileOutgoing = MD5EncoderUtility.getMD5(r.body);
    }

    private HttpRequest getClient() {
        HttpRequest request = null;
        Unirest.setHttpClient(HttpClients
                .custom()
                .build()
        );

        switch (r.method) {
            case GET:
                request = Unirest.get(r.url.toString()); break;
            case HEAD:
                request = Unirest.head(r.url.toString()); break;
            case POST:
                request = Unirest.post(r.url.toString()).body(r.body).getHttpRequest(); break;
            case PUT:
                request = Unirest.put(r.url.toString()).body(r.body).getHttpRequest(); break;
            case DELETE:
                request = Unirest.delete(r.url.toString()).body(r.body).getHttpRequest(); break;
            case PATCH:
                request = Unirest.patch(r.url.toString()).body(r.body).getHttpRequest(); break;
            case OPTIONS:
                request = Unirest.options(r.url.toString()).body(r.body).getHttpRequest(); break;
        }

        request.headers(r.headers);
        if (r.basic != null)
            request.basicAuth(r.basic[0], r.basic[1]);

        if (request instanceof HttpRequestWithBody) {
            if (r.method == HttpMethod.GET || r.method == HttpMethod.HEAD)
                fail("You shouldn't set body for the GET or HEAD http methods");
        }

        r.httpRequest = request;
        return request;
    }

    @When("^sending request with defined client and expect \"(string|json|binary)\"$")
    public void sendingRequestWithDefinedClientAndExpect(String type) {
        try {
            switch (type) {
                case "string":
                    r.httpResponse = getClient().asString();
                    break;
                case "json":
                    r.httpResponse = getClient().asJson();
                    break;
                case "binary":
                    r.httpResponse = getClient().asBinary();
                    break;
            }
        } catch (UnirestException e) {
            fail(e.getMessage());
        }
    }

    @And("^assert that response body MD5 hash equals to saved$")
    public void assertThatResponseBodyMD5hashEqualsTo() throws IOException {
        HttpResponse<InputStream> e = r.httpResponse;
        byte[] incomingBody = IOUtils.toByteArray(e.getBody());
        assertThat(MD5EncoderUtility.getMD5(incomingBody)).isEqualToIgnoringCase(r.md5HashSumFileOutgoing);
    }

    @Then("^assert that response code is \"?(\\d+)\"$")
    public void assertThatResponseCodeIs(int code) {
        assertThat(r.httpResponse.getStatus()).isEqualTo(code);
    }

    @Then("^assert that response header \"(.+)\" (equals|contains) \"(.+)\"$")
    public void assertThatResponseHeaderIs(String k, String matchType, String v) {
        String key = r.httpResponse.getHeaders().getFirst(k);
        assertContainsOrEquals(matchType, v, key);
    }
}
