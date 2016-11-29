package squaring.vitrox.paginationlist.Network;


import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;
import squaring.vitrox.paginationlist.Model.ApiResult;


public interface ApiService {

    @GET("/v3/search/images")
    Observable<ApiResult> getImageList(@Query("page")String page, @Query("phrase")String phrase);

}
