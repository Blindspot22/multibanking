/*
 * finAPI RESTful Services
 * finAPI RESTful Services
 *
 * OpenAPI spec version: v1.64.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package io.swagger.client.api;

import io.swagger.client.ApiCallback;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.ApiResponse;
import io.swagger.client.Configuration;
import io.swagger.client.Pair;
import io.swagger.client.ProgressRequestBody;
import io.swagger.client.ProgressResponseBody;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;


import io.swagger.client.model.BadCredentialsError;
import io.swagger.client.model.ErrorMessage;
import io.swagger.client.model.PageableSecurityList;
import io.swagger.client.model.Security;
import io.swagger.client.model.SecurityList;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SecuritiesApi {
    private ApiClient apiClient;

    public SecuritiesApi() {
        this(Configuration.getDefaultApiClient());
    }

    public SecuritiesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Build call for getAndSearchAllSecurities
     * @param ids A comma-separated list of security identifiers. If specified, then only securities whose identifier match any of the given identifiers will be regarded. The maximum number of identifiers is 1000. (optional)
     * @param search If specified, then only those securities will be contained in the result whose &#39;name&#39;, &#39;isin&#39; or &#39;wkn&#39; contains the given search string (the matching works case-insensitive). If no securities contain the search string in any of these fields, then the result will be an empty list. NOTE: If the given search string consists of several terms (separated by whitespace), then ALL of these terms must be contained in the searched fields in order for a security to get included into the result. (optional)
     * @param accountIds Comma-separated list of identifiers of accounts (optional)
     * @param page Result page that you want to retrieve. (optional, default to 1)
     * @param perPage Maximum number of records per page. Can be at most 500. NOTE: Due to its validation and visualization, the swagger frontend might show very low performance, or even crashes, when a service responds with a lot of data. It is recommended to use a HTTP client like Postman or DHC instead of our swagger frontend for service calls with large page sizes. (optional, default to 20)
     * @param order Determines the order of the results. You can order the results by next fields: &#39;id&#39;, &#39;name&#39;, &#39;isin&#39;, &#39;wkn&#39;, &#39;quote&#39;, &#39;quantityNominal&#39;, &#39;marketValue&#39; and &#39;entryQuote&#39;. The default order for all services is &#39;id,asc&#39;. You can also order by multiple properties. In that case the order of the parameters passed is important. The general format is: &#39;property[,asc|desc]&#39;, with &#39;asc&#39; being the default value. Please note that ordering by multiple fields is not supported in our swagger frontend, but you can test this feature with any HTTP tool of your choice (e.g. postman or DHC).  (optional)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call getAndSearchAllSecuritiesCall(List<Long> ids, String search, List<Long> accountIds, Integer page, Integer perPage, List<String> order, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/api/v1/securities";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        if (ids != null)
        localVarCollectionQueryParams.addAll(apiClient.parameterToPairs("multi", "ids", ids));
        if (search != null)
        localVarQueryParams.addAll(apiClient.parameterToPair("search", search));
        if (accountIds != null)
        localVarCollectionQueryParams.addAll(apiClient.parameterToPairs("multi", "accountIds", accountIds));
        if (page != null)
        localVarQueryParams.addAll(apiClient.parameterToPair("page", page));
        if (perPage != null)
        localVarQueryParams.addAll(apiClient.parameterToPair("perPage", perPage));
        if (order != null)
        localVarCollectionQueryParams.addAll(apiClient.parameterToPairs("multi", "order", order));

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
            
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                    .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                    .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] { "finapi_auth" };
        return apiClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }

    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call getAndSearchAllSecuritiesValidateBeforeCall(List<Long> ids, String search, List<Long> accountIds, Integer page, Integer perPage, List<String> order, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        

        com.squareup.okhttp.Call call = getAndSearchAllSecuritiesCall(ids, search, accountIds, page, perPage, order, progressListener, progressRequestListener);
        return call;

    }

    /**
     * Get and search all securities
     * Get securities of the user that is authorized by the access_token. Must pass the user&#39;s access_token. You can set optional search criteria to get only those securities that you are interested in. If you do not specify any search criteria, then this service functions as a &#39;get all&#39; service.&lt;p&gt;Note: Whenever a security account is being updated, its security positions will be internally re-created, meaning that the identifier of a security position might change over time.&lt;/p&gt;
     * @param ids A comma-separated list of security identifiers. If specified, then only securities whose identifier match any of the given identifiers will be regarded. The maximum number of identifiers is 1000. (optional)
     * @param search If specified, then only those securities will be contained in the result whose &#39;name&#39;, &#39;isin&#39; or &#39;wkn&#39; contains the given search string (the matching works case-insensitive). If no securities contain the search string in any of these fields, then the result will be an empty list. NOTE: If the given search string consists of several terms (separated by whitespace), then ALL of these terms must be contained in the searched fields in order for a security to get included into the result. (optional)
     * @param accountIds Comma-separated list of identifiers of accounts (optional)
     * @param page Result page that you want to retrieve. (optional, default to 1)
     * @param perPage Maximum number of records per page. Can be at most 500. NOTE: Due to its validation and visualization, the swagger frontend might show very low performance, or even crashes, when a service responds with a lot of data. It is recommended to use a HTTP client like Postman or DHC instead of our swagger frontend for service calls with large page sizes. (optional, default to 20)
     * @param order Determines the order of the results. You can order the results by next fields: &#39;id&#39;, &#39;name&#39;, &#39;isin&#39;, &#39;wkn&#39;, &#39;quote&#39;, &#39;quantityNominal&#39;, &#39;marketValue&#39; and &#39;entryQuote&#39;. The default order for all services is &#39;id,asc&#39;. You can also order by multiple properties. In that case the order of the parameters passed is important. The general format is: &#39;property[,asc|desc]&#39;, with &#39;asc&#39; being the default value. Please note that ordering by multiple fields is not supported in our swagger frontend, but you can test this feature with any HTTP tool of your choice (e.g. postman or DHC).  (optional)
     * @return PageableSecurityList
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public PageableSecurityList getAndSearchAllSecurities(List<Long> ids, String search, List<Long> accountIds, Integer page, Integer perPage, List<String> order) throws ApiException {
        ApiResponse<PageableSecurityList> resp = getAndSearchAllSecuritiesWithHttpInfo(ids, search, accountIds, page, perPage, order);
        return resp.getData();
    }

    /**
     * Get and search all securities
     * Get securities of the user that is authorized by the access_token. Must pass the user&#39;s access_token. You can set optional search criteria to get only those securities that you are interested in. If you do not specify any search criteria, then this service functions as a &#39;get all&#39; service.&lt;p&gt;Note: Whenever a security account is being updated, its security positions will be internally re-created, meaning that the identifier of a security position might change over time.&lt;/p&gt;
     * @param ids A comma-separated list of security identifiers. If specified, then only securities whose identifier match any of the given identifiers will be regarded. The maximum number of identifiers is 1000. (optional)
     * @param search If specified, then only those securities will be contained in the result whose &#39;name&#39;, &#39;isin&#39; or &#39;wkn&#39; contains the given search string (the matching works case-insensitive). If no securities contain the search string in any of these fields, then the result will be an empty list. NOTE: If the given search string consists of several terms (separated by whitespace), then ALL of these terms must be contained in the searched fields in order for a security to get included into the result. (optional)
     * @param accountIds Comma-separated list of identifiers of accounts (optional)
     * @param page Result page that you want to retrieve. (optional, default to 1)
     * @param perPage Maximum number of records per page. Can be at most 500. NOTE: Due to its validation and visualization, the swagger frontend might show very low performance, or even crashes, when a service responds with a lot of data. It is recommended to use a HTTP client like Postman or DHC instead of our swagger frontend for service calls with large page sizes. (optional, default to 20)
     * @param order Determines the order of the results. You can order the results by next fields: &#39;id&#39;, &#39;name&#39;, &#39;isin&#39;, &#39;wkn&#39;, &#39;quote&#39;, &#39;quantityNominal&#39;, &#39;marketValue&#39; and &#39;entryQuote&#39;. The default order for all services is &#39;id,asc&#39;. You can also order by multiple properties. In that case the order of the parameters passed is important. The general format is: &#39;property[,asc|desc]&#39;, with &#39;asc&#39; being the default value. Please note that ordering by multiple fields is not supported in our swagger frontend, but you can test this feature with any HTTP tool of your choice (e.g. postman or DHC).  (optional)
     * @return ApiResponse&lt;PageableSecurityList&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<PageableSecurityList> getAndSearchAllSecuritiesWithHttpInfo(List<Long> ids, String search, List<Long> accountIds, Integer page, Integer perPage, List<String> order) throws ApiException {
        com.squareup.okhttp.Call call = getAndSearchAllSecuritiesValidateBeforeCall(ids, search, accountIds, page, perPage, order, null, null);
        Type localVarReturnType = new TypeToken<PageableSecurityList>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Get and search all securities (asynchronously)
     * Get securities of the user that is authorized by the access_token. Must pass the user&#39;s access_token. You can set optional search criteria to get only those securities that you are interested in. If you do not specify any search criteria, then this service functions as a &#39;get all&#39; service.&lt;p&gt;Note: Whenever a security account is being updated, its security positions will be internally re-created, meaning that the identifier of a security position might change over time.&lt;/p&gt;
     * @param ids A comma-separated list of security identifiers. If specified, then only securities whose identifier match any of the given identifiers will be regarded. The maximum number of identifiers is 1000. (optional)
     * @param search If specified, then only those securities will be contained in the result whose &#39;name&#39;, &#39;isin&#39; or &#39;wkn&#39; contains the given search string (the matching works case-insensitive). If no securities contain the search string in any of these fields, then the result will be an empty list. NOTE: If the given search string consists of several terms (separated by whitespace), then ALL of these terms must be contained in the searched fields in order for a security to get included into the result. (optional)
     * @param accountIds Comma-separated list of identifiers of accounts (optional)
     * @param page Result page that you want to retrieve. (optional, default to 1)
     * @param perPage Maximum number of records per page. Can be at most 500. NOTE: Due to its validation and visualization, the swagger frontend might show very low performance, or even crashes, when a service responds with a lot of data. It is recommended to use a HTTP client like Postman or DHC instead of our swagger frontend for service calls with large page sizes. (optional, default to 20)
     * @param order Determines the order of the results. You can order the results by next fields: &#39;id&#39;, &#39;name&#39;, &#39;isin&#39;, &#39;wkn&#39;, &#39;quote&#39;, &#39;quantityNominal&#39;, &#39;marketValue&#39; and &#39;entryQuote&#39;. The default order for all services is &#39;id,asc&#39;. You can also order by multiple properties. In that case the order of the parameters passed is important. The general format is: &#39;property[,asc|desc]&#39;, with &#39;asc&#39; being the default value. Please note that ordering by multiple fields is not supported in our swagger frontend, but you can test this feature with any HTTP tool of your choice (e.g. postman or DHC).  (optional)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call getAndSearchAllSecuritiesAsync(List<Long> ids, String search, List<Long> accountIds, Integer page, Integer perPage, List<String> order, final ApiCallback<PageableSecurityList> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = getAndSearchAllSecuritiesValidateBeforeCall(ids, search, accountIds, page, perPage, order, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<PageableSecurityList>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
    /**
     * Build call for getMultipleSecurities
     * @param ids Comma-separated list of identifiers of requested securities (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @deprecated
     */
    @Deprecated
    public com.squareup.okhttp.Call getMultipleSecuritiesCall(List<Long> ids, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/api/v1/securities/{ids}"
            .replaceAll("\\{" + "ids" + "\\}", apiClient.escapeString(ids.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
            
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                    .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                    .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] { "finapi_auth" };
        return apiClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }

    @Deprecated
    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call getMultipleSecuritiesValidateBeforeCall(List<Long> ids, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        
        // verify the required parameter 'ids' is set
        if (ids == null) {
            throw new ApiException("Missing the required parameter 'ids' when calling getMultipleSecurities(Async)");
        }
        

        com.squareup.okhttp.Call call = getMultipleSecuritiesCall(ids, progressListener, progressRequestListener);
        return call;

    }

    /**
     * Get multiple securities
     * Get a list of multiple securities of the user that is authorized by the access_token. Must pass the securities&#39; identifiers and the user&#39;s access_token. Securities whose identifiers do not exist or do not relate to the authorized user will not be contained in the result (If this applies to all of the given identifiers, then the result will be an empty list). &lt;p&gt;Note: Whenever a security account is being updated, its security positions will be internally re-created, meaning that the identifier of a security position might change over time.&lt;/p&gt;&lt;p&gt;WARNING: This service is deprecated and will be removed at some point. If you want to get multiple securities, please instead use the service &#39;Get and search all securities&#39; and pass a comma-separated list of identifiers as a parameter &#39;ids&#39;.&lt;/p&gt;
     * @param ids Comma-separated list of identifiers of requested securities (required)
     * @return SecurityList
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @deprecated
     */
    @Deprecated
    public SecurityList getMultipleSecurities(List<Long> ids) throws ApiException {
        ApiResponse<SecurityList> resp = getMultipleSecuritiesWithHttpInfo(ids);
        return resp.getData();
    }

    /**
     * Get multiple securities
     * Get a list of multiple securities of the user that is authorized by the access_token. Must pass the securities&#39; identifiers and the user&#39;s access_token. Securities whose identifiers do not exist or do not relate to the authorized user will not be contained in the result (If this applies to all of the given identifiers, then the result will be an empty list). &lt;p&gt;Note: Whenever a security account is being updated, its security positions will be internally re-created, meaning that the identifier of a security position might change over time.&lt;/p&gt;&lt;p&gt;WARNING: This service is deprecated and will be removed at some point. If you want to get multiple securities, please instead use the service &#39;Get and search all securities&#39; and pass a comma-separated list of identifiers as a parameter &#39;ids&#39;.&lt;/p&gt;
     * @param ids Comma-separated list of identifiers of requested securities (required)
     * @return ApiResponse&lt;SecurityList&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @deprecated
     */
    @Deprecated
    public ApiResponse<SecurityList> getMultipleSecuritiesWithHttpInfo(List<Long> ids) throws ApiException {
        com.squareup.okhttp.Call call = getMultipleSecuritiesValidateBeforeCall(ids, null, null);
        Type localVarReturnType = new TypeToken<SecurityList>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Get multiple securities (asynchronously)
     * Get a list of multiple securities of the user that is authorized by the access_token. Must pass the securities&#39; identifiers and the user&#39;s access_token. Securities whose identifiers do not exist or do not relate to the authorized user will not be contained in the result (If this applies to all of the given identifiers, then the result will be an empty list). &lt;p&gt;Note: Whenever a security account is being updated, its security positions will be internally re-created, meaning that the identifier of a security position might change over time.&lt;/p&gt;&lt;p&gt;WARNING: This service is deprecated and will be removed at some point. If you want to get multiple securities, please instead use the service &#39;Get and search all securities&#39; and pass a comma-separated list of identifiers as a parameter &#39;ids&#39;.&lt;/p&gt;
     * @param ids Comma-separated list of identifiers of requested securities (required)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @deprecated
     */
    @Deprecated
    public com.squareup.okhttp.Call getMultipleSecuritiesAsync(List<Long> ids, final ApiCallback<SecurityList> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = getMultipleSecuritiesValidateBeforeCall(ids, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<SecurityList>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
    /**
     * Build call for getSecurity
     * @param id Security identifier (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call getSecurityCall(Long id, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/api/v1/securities/{id}"
            .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(id.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
            
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                    .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                    .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] { "finapi_auth" };
        return apiClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }

    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call getSecurityValidateBeforeCall(Long id, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling getSecurity(Async)");
        }
        

        com.squareup.okhttp.Call call = getSecurityCall(id, progressListener, progressRequestListener);
        return call;

    }

    /**
     * Get a security
     * Get a single security for a specific user. Must pass the security&#39;s identifier and the user&#39;s access_token. &lt;p&gt;Note: Whenever a security account is being updated, its security positions will be internally re-created, meaning that the identifier of a security position might change over time.&lt;/p&gt;
     * @param id Security identifier (required)
     * @return Security
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public Security getSecurity(Long id) throws ApiException {
        ApiResponse<Security> resp = getSecurityWithHttpInfo(id);
        return resp.getData();
    }

    /**
     * Get a security
     * Get a single security for a specific user. Must pass the security&#39;s identifier and the user&#39;s access_token. &lt;p&gt;Note: Whenever a security account is being updated, its security positions will be internally re-created, meaning that the identifier of a security position might change over time.&lt;/p&gt;
     * @param id Security identifier (required)
     * @return ApiResponse&lt;Security&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<Security> getSecurityWithHttpInfo(Long id) throws ApiException {
        com.squareup.okhttp.Call call = getSecurityValidateBeforeCall(id, null, null);
        Type localVarReturnType = new TypeToken<Security>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Get a security (asynchronously)
     * Get a single security for a specific user. Must pass the security&#39;s identifier and the user&#39;s access_token. &lt;p&gt;Note: Whenever a security account is being updated, its security positions will be internally re-created, meaning that the identifier of a security position might change over time.&lt;/p&gt;
     * @param id Security identifier (required)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call getSecurityAsync(Long id, final ApiCallback<Security> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = getSecurityValidateBeforeCall(id, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<Security>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
}
