package com.messagebird;

import java.util.Map;

import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.NotFoundException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.PagedPaging;

/**
 * Created by rvt on 1/7/15.
 */
public interface MessageBirdService {
    /**
     * Execute a object by ID request. It will add the id to the request parameter and retreive a json object R back.
     *
     * @param request     path to the request, for example "/messages"
     * @param id    id of the object to request. id can be null in case request's that don't need a id, for example /balance
     * @param clazz       Class type to return
     * @return new class with returned dataset
     * @throws UnauthorizedException if client is unauthorized
     * @throws GeneralException general exception
     */
    <R> R requestByID(String request, String id, Class<R> clazz) throws UnauthorizedException, GeneralException, NotFoundException;

    <R> R requestByID(String request, String id, Map<String, Object> params, Class<R> clazz) throws UnauthorizedException, GeneralException, NotFoundException;

    /**
     * Delete a object by ID.
     *
     * @param request  path to the request, for example "/messages"
     * @param id  id of the object to delete
     * @throws UnauthorizedException if client is unauthorized
     * @throws GeneralException general exception
     * @throws NotFoundException if id not found
     */
    void deleteByID(String request, String id) throws UnauthorizedException, GeneralException, NotFoundException;

    /**
     * Request a List 'of' object.
     * Allow to request a listMessage or listViewMessages objects.
     * @see com.messagebird.objects.MessageList
     *
     * @param request request from client
     * @param offset offset of data to return
     * @param limit limit number of objects, incase you notice you pass in '1' a lot, please consider using requestByID if you know the ID of the message
     * @param clazz object type to return
     * @return base class
     * @throws UnauthorizedException if client is unauthorized
     * @throws GeneralException general exception
     */
    <R> R requestList(String request, Integer offset, Integer limit, Class<R> clazz) throws UnauthorizedException, GeneralException;

    /**
     * Request a List 'of' object.
     * Allow to request a listMessage or listViewMessages objects.
     * @see com.messagebird.objects.MessageList
     *
     * @param request request from client
     * @param pagedPaging includes page and page size
     * @param clazz object type to return
     * @return base class
     * @throws UnauthorizedException if client is unauthorized
     * @throws GeneralException general exception
     */
    <R> R requestList(String request, PagedPaging pagedPaging, Class<R> clazz) throws UnauthorizedException, GeneralException;

    /**
     * Send a payload with the POST method and receive a payload object.
     *
     * @param request path to the request, for example "/messages"
     * @param payload payload to send to the server
     * @param clazz object type to return
     * @return base class
     * @throws UnauthorizedException if client is unauthorized
     * @throws GeneralException general exception
     */
    <R, P> R sendPayLoad(String request, P payload, Class<R> clazz) throws UnauthorizedException, GeneralException;


    /**
     * Send a payload with the provided method and receive a payload object.
     *
     * @param method HTTP method to use for the request
     * @param request path to the request, for example "/messages"
     * @param payload payload to send to the server
     * @param clazz object type to return
     * @return base class
     * @throws UnauthorizedException if client is unauthorized
     * @throws GeneralException general exception
     */
    <R, P> R sendPayLoad(String method, String request, P payload, Class<R> clazz) throws UnauthorizedException, GeneralException;

    /**
     * Gets the data from the request URL and stores it to basePath/fileName
     *
     * @param request  path to the request, for example "/messages"
     * @param basePath base path for storing directory
     * @param fileName the fileName that is going to be stored.
     * @return the path that file is stored
     * @throws UnauthorizedException if client is unauthorized
     * @throws GeneralException general exception
     * @throws NotFoundException if the file is not found
     */
    String getBinaryData(String request, String basePath, String fileName) throws UnauthorizedException, GeneralException, NotFoundException;
}
