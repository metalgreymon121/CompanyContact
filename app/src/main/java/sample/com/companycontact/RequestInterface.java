package sample.com.companycontact;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RequestInterface {

    @GET("BR_Android_CodingExam_2015_Server/stores.json")
    Call<JSONResponse> getJSON();
}
