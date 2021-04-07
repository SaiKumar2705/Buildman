package com.buildman.buildman.interfaces;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

public interface ImageApiInterface {



    @Multipart
    @POST()
    Call<String> uploadImageToWebServerNew(@Url String url,
                                           @Part("ClientId") RequestBody ClientId,
                                           @Part("FileName") RequestBody FileName,
                                           @Part("Extension") RequestBody Extension,
                                           @Part("FileType") RequestBody FileType,
                                           @Part("ProductName") RequestBody ProductName,
                                           @Part MultipartBody.Part UploadedFile,
                                           @Header("Authorization") String authHeader);
}

