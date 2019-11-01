/*
 *  Copyright 2019 Nicholas Bennett & Deep Dive Coding/CNM Ingenuity
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package edu.cnm.deepdive.diceware.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.cnm.deepdive.diceware.BuildConfig;
import edu.cnm.deepdive.diceware.model.Passphrase;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import java.util.List;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 *
 */
public interface DicewareService {

  /**
   *
   * @param token
   * @return
   */
  @GET("passphrases/")
  Observable<List<Passphrase>> getAll(@Header("Authorization") String token);

  /**
   *
   * @param token
   * @param id
   * @return
   */
  @GET("passphrases/{id}")
  Single<Passphrase> get(@Header("Authorization") String token,
      @Path("id") long id);

  /**
   *
   * @param token
   * @param key
   * @return
   */
  @GET("passphrases/{key}")
  Single<Passphrase> get(@Header("Authorization") String token,
      @Path("key") String key);

  /**
   *
   * @param token
   * @param id
   * @return
   */
  @DELETE("passphrases/{id}")
  Completable delete(@Header("Authorization") String token, @Path("id") long id);

  /**
   *
   * @param token
   * @param id
   * @param passphrase
   * @return
   */
  @PUT("passphrases/{id}")
  Single<Passphrase> put(@Header("Authorization") String token, @Path("id") long id, @Body Passphrase passphrase);

  /**
   *
   * @param token
   * @param passphrase
   * @return
   */
  @POST("passphrases/")
  Single<Passphrase> post(@Header("Authorization") String token, @Body Passphrase passphrase);

  /**
   *
   * @return
   */
  static DicewareService getInstance() {
    return InstanceHolder.INSTANCE;
  }

  /**
   *
   */
  class InstanceHolder {

    private static final DicewareService INSTANCE;

    static {
      // TODO Investigate logging interceptor issues.
      Gson gson = new GsonBuilder()
          .excludeFieldsWithoutExposeAnnotation()
          .create();
      Retrofit retrofit = new Retrofit.Builder()
          .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
          .addConverterFactory(GsonConverterFactory.create(gson))
          .baseUrl(BuildConfig.BASE_URL)
          .build();
      INSTANCE = retrofit.create(DicewareService.class);
    }

  }

}
