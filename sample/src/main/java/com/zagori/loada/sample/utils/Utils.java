package com.zagori.loada.sample.utils;

import java.util.List;

/*
* Here will be all the utility methods
* */
public class Utils<T> {

    /*
     * This method is a helper of the API that does not support pagination,
     * It breaks down the result from the api and return a specific portion of the result.
     *
     * @param originList is the complete result list of the api
     * @param startIndex is start point of the returned sublist. It is inclusive.
     * @param pageList is the sublist
     * */
    public List<T> getSubList(List originList, int startIndex, int pageSize){

        // if the original list
        if (startIndex >= originList.size()){
            originList.clear();
            return originList; // return an empty list
        }
        // find the end point of the sublist (exclusive)
        int endIndex = startIndex + pageSize;

        // returned what is left of the origin list
        if (endIndex >= originList.size())
            endIndex = originList.size() - 1;

        return originList.subList(startIndex, endIndex);
    }
}
