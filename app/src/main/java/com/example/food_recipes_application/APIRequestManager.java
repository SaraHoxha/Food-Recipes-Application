package com.example.food_recipes_application;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.food_recipes_application.Listeners.InstructionsListener;
import com.example.food_recipes_application.Listeners.RecipeDetailsListener;
import com.example.food_recipes_application.Models.InstructionsResponse;
import com.example.food_recipes_application.Models.RecipeDetailsResponse;
import com.example.food_recipes_application.Models.APISearchResponse;
import com.example.food_recipes_application.Listeners.APISearchResponseListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class APIRequestManager {
    Context context;
    String recipeName;

    Retrofit retrofit =new Retrofit. Builder ()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public APIRequestManager(Context context, String recipeName) {
        this.context = context;
        this.recipeName = recipeName;
    }

    public APIRequestManager(Context context) {
        this.context = context;
    }

    public void getRecipesSearchResults(APISearchResponseListener listener) {
        searchRecipes searchRecipes = retrofit.create(APIRequestManager.searchRecipes.class);
        Call<APISearchResponse> callResponse = searchRecipes.callSearchRecipesAPI(recipeName,  "20", context.getString(R.string.apiKey));

        callResponse.enqueue(new Callback<APISearchResponse>() {
            @Override
            public void onResponse(@NonNull Call<APISearchResponse> call, @NonNull Response<APISearchResponse> response) {
                if (!response.isSuccessful()) {
                    listener.didError(response.message());
                    return;
                }
                    listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(@NonNull Call<APISearchResponse> call, @NonNull Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    public void getRecipeDetails(RecipeDetailsListener listener, int id){
        CallRecipeDetails callRecipeDetails = retrofit.create(CallRecipeDetails.class);
        Call<RecipeDetailsResponse> call = callRecipeDetails.callRecipeInformation(id, context.getString(R.string.apiKey));
        call.enqueue(new Callback<RecipeDetailsResponse>() {
            @Override
            public void onResponse(@NonNull Call<RecipeDetailsResponse> call, @NonNull Response<RecipeDetailsResponse> response) {
                if (!response.isSuccessful()) {
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(@NonNull Call<RecipeDetailsResponse> call, @NonNull Throwable t) {
                listener.didError(t.getMessage());

            }
        });
    }

    public void getInstructions(InstructionsListener listener, int id){
        CallInstructions callInstructions = retrofit.create(CallInstructions.class);
        Call<List<InstructionsResponse>> call = callInstructions.callInstructions(id, context.getString(R.string.apiKey));
        call.enqueue(new Callback<List<InstructionsResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<InstructionsResponse>> call, @NonNull Response<List<InstructionsResponse>> response) {
                if(!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(@NonNull Call<List<InstructionsResponse>> call, @NonNull Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    private interface searchRecipes {
        @GET("recipes/complexSearch")
        Call<APISearchResponse> callSearchRecipesAPI(
                @Query("query") String recipeName,
                @Query("number") String numberOfResults,
                @Query("apiKey") String apiKey
        );
    }

    private interface CallRecipeDetails {
        @GET("/recipes/{id}/information")
        Call<RecipeDetailsResponse> callRecipeInformation(
                @Path("id") int id,
                @Query("apiKey") String apiKey
        );
    }

    private interface CallInstructions{
        @GET("recipes/{id}/analyzedInstructions")
        Call<List<InstructionsResponse>> callInstructions(
                @Path("id") int id,
                @Query("apiKey") String apiKey
        );
    }
}
