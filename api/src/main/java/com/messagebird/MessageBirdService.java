package com.messagebird;

import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.NotFoundException;
import com.messagebird.exceptions.UnauthorizedException;

import java.io.UnsupportedEncodingException;
import java.util.Map;

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
     * @throws com.messagebird.exceptions.UnauthorizedException
     * @throws com.messagebird.exceptions.GeneralException
     */
    <R> R requestByID(String request, String id, Class<R> clazz) throws UnauthorizedException, GeneralException, NotFoundException;

    <R> R requestByID(String request, String id, Map<String, Object> params, Class<R> clazz) throws UnauthorizedException, GeneralException, NotFoundException;

    /**
     * Delete a object by ID.
     *
     * @param request  path to the request, for example "/messages"
     * @param id  id of the object to delete
     * @throws UnauthorizedException
     * @throws GeneralException
     * @throws NotFoundException
     */
    void deleteByID(String request, String id) throws UnauthorizedException, GeneralException, NotFoundException;

    /**
     * Request a List 'of' object.
     * Allow to request a listMessage or listViewMessges objects.
     * @see com.messagebird.objects.MessageListBase
     *
     * @param request
     * @param offset offset of data to return
     * @param limit limit number of objects, incase you notice you pass in '1' a lot, please consider using requestByID if you know the ID of the message
     * @param clazz object type to return
     * @return
     * @throws UnauthorizedException
     * @throws GeneralException
     */
    <R> R requestList(String request, Integer offset, Integer limit, Class<R> clazz) throws UnauthorizedException, GeneralException;

    /**
     * Send a payload and receive a payload object
     *
     * @param request path to the request, for example "/messages"
     * @param payload payload to send to the server
     * @param clazz object type to return
     * @return
     * @throws UnauthorizedException
     * @throws GeneralException
     */
    <R, P> R sendPayLoad(String request, P payload, Class<R> clazz) throws UnauthorizedException, GeneralException;

}
