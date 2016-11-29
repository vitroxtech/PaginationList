package squaring.vitrox.paginationlist.Network;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import retrofit.JacksonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import squaring.vitrox.paginationlist.Common.Config;
import squaring.vitrox.paginationlist.DependencyInjection.Model.ApplicationModule;

@Module(includes = ApplicationModule.class)

public class ServiceModule {

    @Provides
    @Singleton
    public ApiService apiService(OkHttpClient client) {
        String myUrl = Config.BASE_URL;
        //Jackson Mapper and deserializer
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return new Retrofit.Builder()
                .baseUrl(myUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create(mapper))
                .client(client)
                .build()
                .create(ApiService.class);
    }

    @Provides
    @Singleton
    public OkHttpClient apiClient() {

        OkHttpClient httpClient =
                new OkHttpClient();
        httpClient.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                HttpUrl originalHttpUrl = original.httpUrl();
                HttpUrl url = originalHttpUrl.newBuilder()
                        .addQueryParameter("page_size", String.valueOf(Config.PAGE_SIZE))
                        .build();
                Request.Builder requestBuilder = original.newBuilder()
                        .url(url)
                        .addHeader("Api-Key", Config.API_KEY);
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });
        return httpClient;
    }


}
